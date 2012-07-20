/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.bridge.container.liferay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.context.ResponseWriter;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.Product;
import com.liferay.faces.bridge.config.ProductMap;
import com.liferay.faces.bridge.container.PortletContainerImpl;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriter;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriterLiferayImpl;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class PortletContainerLiferayImpl extends PortletContainerImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortletContainerLiferayImpl.class);

	// Private Constants
	private static final String REQ_PARAM_LIFERAY_BROWSERID = "?browserId=";
	private static final String RENDER_PORTLET_RESOURCE = "RENDER_PORTLET_RESOURCE";

	// Private Pseudo-Constants Initialized at Construction-Time
	private String NAMESPACED_P_P_COL_ID;
	private String NAMESPACED_P_P_COL_POS;
	private String NAMESPACED_P_P_COL_COUNT;
	private String NAMESPACED_P_P_MODE;
	private String NAMESPACED_P_P_STATE;

	// Private Data Members
	private boolean ableToAddScriptResourceToHead;
	private boolean ableToAddScriptTextToHead;
	private boolean ableToAddStyleSheetResourceToHead;
	private boolean ableToSetHttpStatusCode;
	private int liferayBuildNumber;
	private LiferayPortletRequest liferayPortletRequest;
	private ParsedPortletURL parsedLiferayActionURL;
	private ParsedPortletURL parsedLiferayRenderURL;
	private ParsedBaseURL parsedLiferayResourceURL;
	private String portletResponseNamespace;
	private String requestURL;
	private String responseNamespace;
	private String[] userAgentHeader;

	public PortletContainerLiferayImpl(PortletRequest portletRequest, PortletResponse portletResponse,
		PortletContext portletContext, BridgeConfig bridgeConfig) {

		// Initialize the superclass.
		super(portletRequest, bridgeConfig);

		try {

			// Initialize the private data members.
			this.portletResponseNamespace = portletResponse.getNamespace();

			LiferayPortletRequest liferayPortletRequest = new LiferayPortletRequest(portletRequest);
			LiferayThemeDisplay liferayThemeDisplay = liferayPortletRequest.getLiferayThemeDisplay();
			this.liferayPortletRequest = liferayPortletRequest;

			// Initialize the pseudo-constants.
			NAMESPACED_P_P_COL_ID = portletResponseNamespace + LiferayConstants.P_P_COL_ID;
			NAMESPACED_P_P_COL_POS = portletResponseNamespace + LiferayConstants.P_P_COL_POS;
			NAMESPACED_P_P_COL_COUNT = portletResponseNamespace + LiferayConstants.P_P_COL_COUNT;
			NAMESPACED_P_P_MODE = portletResponseNamespace + LiferayConstants.P_P_MODE;
			NAMESPACED_P_P_STATE = portletResponseNamespace + LiferayConstants.P_P_STATE;

			// Save the render attributes.
			if (portletRequest instanceof RenderRequest) {
				PortletMode portletMode = portletRequest.getPortletMode();
				WindowState windowState = portletRequest.getWindowState();
				saveRenderAttributes(portletMode, windowState, liferayThemeDisplay, responseNamespace, portletContext);
			}

			// Determine the Liferay version number.
			Product liferayPortal = ProductMap.getInstance().get(BridgeConstants.LIFERAY_PORTAL);
			liferayBuildNumber = liferayPortal.getBuildId();

			if (logger.isDebugEnabled()) {
				logger.debug("Detected Liferay build number {0}", Long.toString(liferayBuildNumber));
			}

			// Note that Liferay didn't support the Portlet 2.0 ResourceResponse.HTTP_STATUS_CODE feature until Liferay
			// 6.0.6 CE and 6.0.11 EE. See: http://issues.liferay.com/browse/LPS-9145
			// Also note that Liferay 6.0 EE version numbering begins with 6.0.10 (6010).
			boolean defaultValue = false;

			if ((liferayBuildNumber >= 6011) || ((liferayBuildNumber >= 6005) && (liferayBuildNumber <= 6010))) {
				defaultValue = true;
			}

			// Determine whether or not the portlet was added via $theme.runtime(...)
			Boolean renderPortletResource = (Boolean) portletRequest.getAttribute(RENDER_PORTLET_RESOURCE);
			boolean runtimePortlet = (renderPortletResource != null) && renderPortletResource.booleanValue();

			// If this is a runtime portlet, then it is not possible to add resources to the head section since
			// top_head.jsp is included prior to the runtime portlet being invoked.
			if (runtimePortlet) {
				this.ableToAddScriptResourceToHead = false;
				this.ableToAddScriptTextToHead = false;
				this.ableToAddStyleSheetResourceToHead = false;
			}

			// Otherwise,
			else {

				// If this portlet is running via WSRP, then it is not possible to add resources to the head section
				// because Liferay doesn't support that feature with WSRP.
				if (BooleanHelper.isTrueToken(portletRequest.getParameter(BridgeConstants.WSRP))) {
					this.ableToAddScriptResourceToHead = false;
					this.ableToAddScriptTextToHead = false;
					this.ableToAddStyleSheetResourceToHead = false;
				}

				// Otherwise, Liferay is able to add resources to the head section, albeit with a vendor-specific
				// (non-standard) way.
				else {
					this.ableToAddScriptResourceToHead = true;
					this.ableToAddScriptTextToHead = true;
					this.ableToAddStyleSheetResourceToHead = true;
				}
			}

			this.ableToSetHttpStatusCode = getContextParamAbleToSetHttpStatusCode(defaultValue);

			logger.debug("User-Agent requested URL=[{0}]", getRequestURL());

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public PortletURL createRedirectURL(String fromURL, Map<String, List<String>> parameters)
		throws MalformedURLException {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		LiferayPortletResponse liferayPortletResponse = new LiferayPortletResponse(bridgeContext.getPortletResponse());

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		copyRequestParameters(fromURL, redirectURL);

		if (parameters != null) {
			Set<String> parameterNames = parameters.keySet();

			for (String parameterName : parameterNames) {
				List<String> parameterValues = parameters.get(parameterName);
				String[] parameterValuesArray = parameterValues.toArray(new String[parameterValues.size()]);
				redirectURL.setParameter(parameterName, parameterValuesArray);
			}
		}

		return redirectURL;
	}

	/**
	 * There is a bug in some versions of Liferay's PortalImpl.getStaticResourceURL(...) method in which it appends
	 * request parameters with a question-mark instead of an ampersand. This method is a hack-fix for that bug.
	 *
	 * @param   value  The request parameter value that may need to be fixed.
	 *
	 * @return  The fixed request parameter value.
	 */
	@Override
	public String fixRequestParameterValue(String value) {

		if (value != null) {
			int pos = value.indexOf(REQ_PARAM_LIFERAY_BROWSERID);

			if (pos > 0) {
				value = value.substring(0, pos);
			}
		}

		return value;
	}

	/**
	 * The Liferay {@link EventResponseImpl#setRenderParameters(EventRequest)} method is unimplemented. This method
	 * provides an opportunity to work-around that limitation.
	 */
	@Override
	public void maintainRenderParameters(EventRequest eventRequest, EventResponse eventResponse) {

		Map<String, String[]> publicParameterMap = eventRequest.getPublicParameterMap();

		if (publicParameterMap != null) {
			Set<Entry<String, String[]>> entrySet = publicParameterMap.entrySet();

			for (Map.Entry<String, String[]> mapEntry : entrySet) {
				String key = mapEntry.getKey();
				String[] values = mapEntry.getValue();
				eventResponse.setRenderParameter(key, values);
				logger.trace("Maintaining public render parameter name=[{0}] values=[{1}]", key, values);
			}
		}

		Map<String, String[]> privateParameterMap = eventRequest.getPrivateParameterMap();

		if (privateParameterMap != null) {
			Set<Entry<String, String[]>> entrySet = privateParameterMap.entrySet();

			for (Map.Entry<String, String[]> mapEntry : entrySet) {
				String key = mapEntry.getKey();
				String[] values = mapEntry.getValue();
				eventResponse.setRenderParameter(key, values);
				logger.trace("Maintaining private render parameter name=[{0}] values=[{1}]", key, values);
			}
		}
	}

	@Override
	public void postRenderResponse() {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		PortletRequest portletRequest = bridgeContext.getPortletRequest();

		StringBuilder stringBundler = (StringBuilder) portletRequest.getAttribute(
				LiferayConstants.LIFERAY_SHARED_PAGE_TOP);

		if (stringBundler != null) {

			LiferaySharedPageTop liferaySharedPageTop = new LiferaySharedPageTop(stringBundler);
			liferaySharedPageTop.removeDuplicates();
			stringBundler = liferaySharedPageTop.toStringBundler();
			portletRequest.setAttribute(LiferayConstants.LIFERAY_SHARED_PAGE_TOP, stringBundler);
		}
	}

	@Override
	public void redirect(String url) throws IOException {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		PortletResponse portletResponse = bridgeContext.getPortletResponse();

		if (portletResponse instanceof ActionResponse) {
			LiferayPortletResponse liferayActionResponse = new LiferayPortletResponse(portletResponse);
			liferayActionResponse.sendRedirect(url);
		}
		else {
			super.redirect(url);
		}
	}

	@Override
	protected PortletURL createActionURL(MimeResponse mimeResponse) {
		return new LiferayActionURL(getParsedLiferayActionURL(mimeResponse), portletResponseNamespace);
	}

	@Override
	protected PortletURL createRenderURL(MimeResponse mimeResponse) {
		return new LiferayRenderURL(getParsedLiferayRenderURL(mimeResponse), portletResponseNamespace);
	}

	@Override
	protected ResourceURL createResourceURL(MimeResponse mimeResponse) {
		return new LiferayResourceURL(getParsedLiferayResourceURL(mimeResponse), portletResponseNamespace,
				liferayBuildNumber);
	}

	/**
	 * Liferay Hack: Need to save some stuff that's only available at RenderRequest time in order to have
	 * getResourceURL() work properly later.
	 *
	 * @param  renderRequest      The current RenderRequest.
	 * @param  responseNamespace  The current response namespace.
	 * @param  applicationMap     The current ApplicationMap.
	 */
	protected void saveRenderAttributes(PortletMode portletMode, WindowState windowState,
		LiferayThemeDisplay themeDisplay, String responseNamespace, PortletContext portletContext) {

		try {

			// Get the PortletDisplay from the ThemeDisplay.
			LiferayPortletDisplay portletDisplay = themeDisplay.getLiferayPortletDisplay();

			// Get the p_p_col_id and save it.
			portletContext.setAttribute(NAMESPACED_P_P_COL_ID, portletDisplay.getColumnId());

			// Get the p_p_col_pos and save it.
			portletContext.setAttribute(NAMESPACED_P_P_COL_POS, portletDisplay.getColumnPos());

			// Get the p_p_col_count and save it.
			portletContext.setAttribute(NAMESPACED_P_P_COL_COUNT, portletDisplay.getColumnCount());

			// Get the p_p_mode and save it.
			if (portletMode != null) {
				portletContext.setAttribute(NAMESPACED_P_P_MODE, portletMode.toString());
			}

			// Get the p_p_state and save it.
			if (windowState != null) {
				portletContext.setAttribute(NAMESPACED_P_P_STATE, windowState.toString());
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean isAbleToAddScriptResourceToHead() {

		return ableToAddScriptResourceToHead;
	}

	@Override
	public boolean isAbleToAddScriptTextToHead() {

		return ableToAddScriptTextToHead;
	}

	@Override
	public boolean isAbleToAddStyleSheetResourceToHead() {

		return ableToAddStyleSheetResourceToHead;
	}

	/**
	 * Liferay Portal does not implement the POST-REDIRECT-GET design pattern. Rather, the ACTION_PHASE and RENDER_PHASE
	 * are both part of a single HTTP POST request.
	 *
	 * @return  <code>false</code> since Liferay Portal does not implement the POST-REDIRECT-GET design pattern.
	 */
	@Override
	public boolean isPostRedirectGetSupported() {
		return false;
	}

	/**
	 * Determines whether or not the portlet container supports the standard Portlet 2.0 mechanism for adding resources
	 * to the <head>...</head> section of the rendered portal page. Section PLT.12.5.4 of the Portlet 2.0 spec indicates
	 * that this is an "optional" feature for vendors to implement. Liferay Portal added support for this feature in
	 * v6.0.3 but a bug prevented it from working, even in v6.0.5. So as of now this method returns false for Liferay.
	 *
	 * @see     <a href="http://issues.liferay.com/browse/LPE-2729">LPE-2729</a>
	 * @see     <a href="http://issues.liferay.com/browse/LPS-11767">LPS-11767</a>
	 *
	 * @return  <code>false</code> since Liferay doesn't support it reliably.
	 */
	@Override
	protected boolean isMarkupHeadElementSupported() {
		return false;
	}

	@Override
	public boolean isAbleToSetHttpStatusCode() {
		return ableToSetHttpStatusCode;
	}

	protected String getEncodedRequestParameterValue(PortletRequest portletRequest, String parameterName) {

		String encodedRequestParameterValue = portletRequest.getParameter(parameterName);

		if (encodedRequestParameterValue != null) {

			try {
				encodedRequestParameterValue = URLEncoder.encode(encodedRequestParameterValue, "UTF-8");
			}
			catch (UnsupportedEncodingException e) {
				// Ignore as this will never happen.
			}
		}

		return encodedRequestParameterValue;
	}

	@Override
	public boolean isAbleToForwardOnDispatch() {
		return false;
	}

	@Override
	public HeadResponseWriter getHeadResponseWriter(ResponseWriter wrappableResponseWriter) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		HeadResponseWriter headResponseWriter = new HeadResponseWriterLiferayImpl(bridgeContext,
				wrappableResponseWriter);

		return headResponseWriter;
	}

	@Override
	public long getHttpServletRequestDateHeader(String name) {
		return liferayPortletRequest.getDateHeader(name);
	}

	protected ParsedPortletURL getParsedLiferayActionURL(MimeResponse mimeResponse) {

		if (parsedLiferayActionURL == null) {
			PortletURL liferayActionURL = mimeResponse.createActionURL();
			parsedLiferayActionURL = new ParsedPortletURL(liferayActionURL);
		}

		return parsedLiferayActionURL;
	}

	protected ParsedPortletURL getParsedLiferayRenderURL(MimeResponse mimeResponse) {

		if (parsedLiferayRenderURL == null) {
			PortletURL liferayRenderURL = mimeResponse.createRenderURL();
			parsedLiferayRenderURL = new ParsedPortletURL(liferayRenderURL);
		}

		return parsedLiferayRenderURL;
	}

	protected ParsedBaseURL getParsedLiferayResourceURL(MimeResponse mimeResponse) {

		if (parsedLiferayResourceURL == null) {
			ResourceURL liferayResourceURL = mimeResponse.createResourceURL();
			parsedLiferayResourceURL = new ParsedBaseURL(liferayResourceURL);
		}

		return parsedLiferayResourceURL;
	}

	@Override
	public String getRequestURL() {

		if (requestURL == null) {
			StringBuilder buf = new StringBuilder();
			LiferayThemeDisplay themeDisplay = liferayPortletRequest.getLiferayThemeDisplay();
			buf.append(themeDisplay.getURLPortal());
			buf.append(themeDisplay.getURLCurrent());
			requestURL = buf.toString();
		}

		return requestURL;
	}

	@Override
	public String getResponseNamespace() {

		if (responseNamespace == null) {

			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			responseNamespace = bridgeContext.getPortletResponse().getNamespace();

			if (responseNamespace.startsWith(BridgeConstants.WSRP_REWRITE)) {
				responseNamespace = LiferayPortalUtil.getPortletId(bridgeContext.getPortletRequest());
			}
		}

		return responseNamespace;
	}

	@Override
	public String[] getUserAgentHeader() {

		if (userAgentHeader == null) {
			userAgentHeader = liferayPortletRequest.getUserAgentHeader();
		}

		return userAgentHeader;
	}

}
