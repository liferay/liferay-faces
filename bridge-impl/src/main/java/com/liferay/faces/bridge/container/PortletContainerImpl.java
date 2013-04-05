/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.container;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.portlet.ActionResponse;
import javax.portlet.BaseURL;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.Bridge.PortletPhase;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.util.RequestParameter;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class PortletContainerImpl extends PortletContainerCompatImpl {

	// serialVersionUID
	private static final long serialVersionUID = 293072155166625509L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortletContainerImpl.class);

	/** Portlet-API request attribute that contains an instance of javax.portlet.PortletRequest */
	private static final String REQUEST_ATTR_PORTLET_REQUEST = "javax.portlet.request";

	/** Servlet-API request attribute that indicates the query part of the URL requested by the user-agent */
	private static final String REQUEST_ATTR_QUERY_STRING = "javax.servlet.forward.query_string";

	// Private Data Members
	private Boolean ableToSetHttpStatusCode;
	private Map<String, PortletURL> actionURLCache;
	private BridgeConfig bridgeConfig;
	private boolean markupHeadElementSupported;
	private Map<String, PortletURL> renderURLCache;
	private String requestQueryString;
	private String requestURL;
	private String responseNamespace;
	private Map<String, ResourceURL> resourceURLCache;

	public PortletContainerImpl(PortletRequest portletRequest, BridgeConfig bridgeConfig) {
		String portalVendorClaim = portletRequest.getPortalContext().getProperty(
				PortalContext.MARKUP_HEAD_ELEMENT_SUPPORT);
		this.bridgeConfig = bridgeConfig;
		this.markupHeadElementSupported = (portalVendorClaim != null);
		this.actionURLCache = new HashMap<String, PortletURL>();
		this.renderURLCache = new HashMap<String, PortletURL>();
		this.resourceURLCache = new HashMap<String, ResourceURL>();
	}

	public void afterPhase(PhaseEvent phaseEvent) {
		// no-op
	}

	public void beforePhase(PhaseEvent event) {
		// no-op
	}

	public PortletURL createActionURL(String fromURL) throws MalformedURLException {
		PortletURL actionURL = actionURLCache.get(fromURL);

		if (actionURL == null) {

			try {
				logger.debug("createActionURL fromURL=[" + fromURL + "]");

				BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
				MimeResponse mimeResponse = (MimeResponse) bridgeContext.getPortletResponse();
				actionURL = createActionURL(mimeResponse);
				copyRequestParameters(fromURL, actionURL);
				actionURLCache.put(fromURL, actionURL);
			}
			catch (ClassCastException e) {
				throw new MalformedURLException(e.getMessage());
			}
		}

		return actionURL;
	}

	public ResourceURL createPartialActionURL(String fromURL) throws MalformedURLException {
		logger.debug("createPartialActionURL fromURL=[" + fromURL + "]");

		return createResourceURL(fromURL);
	}

	/**
	 * Note that this default method implementation doesn't help when a <redirect /> is present in the navigation-rule.
	 * That's because the JSF implementation will end up calling this method during the Portlet 2.0 ACTION_PHASE, and
	 * it's impossible for us to get a redirect URL (really, a render URL) from an ActionResponse. This method will need
	 * to be overridden for each portlet container and handled in a container-dependent way.
	 */
	public PortletURL createRedirectURL(String fromURL, Map<String, List<String>> parameters)
		throws MalformedURLException {

		PortletURL redirectURL = null;

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletPhase portletRequestPhase = bridgeContext.getPortletRequestPhase();

		if ((portletRequestPhase == Bridge.PortletPhase.RENDER_PHASE) ||
				(portletRequestPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {

			try {
				logger.debug("createRedirectURL fromURL=[" + fromURL + "]");

				MimeResponse mimeResponse = (MimeResponse) bridgeContext.getPortletResponse();
				redirectURL = mimeResponse.createRenderURL();
				copyRequestParameters(fromURL, redirectURL);

				if (parameters != null) {
					Set<String> parameterNames = parameters.keySet();

					for (String parameterName : parameterNames) {
						List<String> parameterValues = parameters.get(parameterName);
						String[] parameterValuesArray = parameterValues.toArray(new String[parameterValues.size()]);
						redirectURL.setParameter(parameterName, parameterValuesArray);
					}
				}
			}
			catch (ClassCastException e) {
				throw new MalformedURLException(e.getMessage());
			}
		}
		else {
			throw new UnsupportedOperationException("Unable to create a redirectURL (renderURL) during " +
				portletRequestPhase + " from URL=[" + fromURL + "]");
		}

		return redirectURL;
	}

	public PortletURL createRenderURL(String fromURL) throws MalformedURLException {
		PortletURL renderURL = renderURLCache.get(fromURL);

		if (renderURL == null) {

			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			PortletPhase portletRequestPhase = bridgeContext.getPortletRequestPhase();

			if ((portletRequestPhase == Bridge.PortletPhase.RENDER_PHASE) ||
					(portletRequestPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {

				try {
					logger.debug("createRenderURL fromURL=[" + fromURL + "]");

					MimeResponse mimeResponse = (MimeResponse) bridgeContext.getPortletResponse();
					renderURL = createRenderURL(mimeResponse);
					copyRequestParameters(fromURL, renderURL);
					renderURLCache.put(fromURL, renderURL);
				}
				catch (ClassCastException e) {
					throw new MalformedURLException(e.getMessage());
				}
			}
		}

		return renderURL;
	}

	public ResourceURL createResourceURL(String fromURL) throws MalformedURLException {
		ResourceURL resourceURL = resourceURLCache.get(fromURL);

		if (resourceURL == null) {

			try {
				logger.debug("createResourceURL fromURL=[" + fromURL + "]");

				// Ask the portlet container to create a portlet resource URL.
				BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
				MimeResponse mimeResponse = (MimeResponse) bridgeContext.getPortletResponse();
				resourceURL = createResourceURL(mimeResponse);

				// If the "javax.faces.resource" token is found in the URL, then
				int tokenPos = fromURL.indexOf(BridgeConstants.JAVAX_FACES_RESOURCE);

				if (tokenPos >= 0) {

					// Parse-out the resourceId
					String resourceId = fromURL.substring(tokenPos);

					// Parse-out the resourceName and convert it to a URL parameter on the portlet resource URL.
					int queryStringPos = resourceId.indexOf('?');

					String resourceName = resourceId;

					if (queryStringPos > 0) {
						resourceName = resourceName.substring(0, queryStringPos);
					}

					int slashPos = resourceName.indexOf('/');

					if (slashPos > 0) {
						resourceName = resourceName.substring(slashPos + 1);
					}
					else {
						logger.error("There is no slash after the [{0}] token in resourceURL=[{1}]",
							BridgeConstants.JAVAX_FACES_RESOURCE, fromURL);
					}

					resourceURL.setParameter(BridgeConstants.JAVAX_FACES_RESOURCE, resourceName);
					logger.debug("Added parameter to portletURL name=[{0}] value=[{1}]",
						BridgeConstants.JAVAX_FACES_RESOURCE, resourceName);
				}

				// Copy the request parameters to the portlet resource URL.
				copyRequestParameters(fromURL, resourceURL);

				resourceURLCache.put(fromURL, resourceURL);
			}
			catch (ClassCastException e) {
				throw new MalformedURLException(e.getMessage());
			}
		}

		return resourceURL;
	}

	public String fixRequestParameterValue(String value) {

		// In this default implementation, there's nothing to fix, so just return the specified value.
		return value;
	}

	/**
	 * FACES-1453: Since {@link EventResponse#setRenderParameters(EventRequest)} would end up clobbering existing
	 * public/private render parameters, it is necessary to iterate through all of them and only maintain the ones that
	 * don't already exist in {@link EventResponse#getRenderParameterMap()}.
	 */
	public void maintainRenderParameters(EventRequest eventRequest, EventResponse eventResponse) {

		Map<String, String[]> existingResponseRenderParameterMap = eventResponse.getRenderParameterMap();

		// Maintain the public render parameters.
		Map<String, String[]> publicParameterMap = eventRequest.getPublicParameterMap();

		if (publicParameterMap != null) {

			Set<Entry<String, String[]>> entrySet = publicParameterMap.entrySet();

			for (Map.Entry<String, String[]> mapEntry : entrySet) {
				String key = mapEntry.getKey();

				boolean alreadyExists = false;

				if (existingResponseRenderParameterMap != null) {
					alreadyExists = existingResponseRenderParameterMap.containsKey(key);
				}

				if (alreadyExists) {

					// FACES-1453: Avoid clobbering existing public render parameters set on the EventResponse.
					if (logger.isTraceEnabled()) {
						String[] existingValues = existingResponseRenderParameterMap.get(key);

						logger.trace(
							"Not maintaining public render parameter name=[{0}] values=[{1}] because it already exists",
							key, existingValues);
					}
				}
				else {
					String[] values = mapEntry.getValue();
					eventResponse.setRenderParameter(key, values);
					logger.trace("Maintaining public render parameter name=[{0}] values=[{1}]", key, values);
				}
			}
		}

		// Maintain the private render parameters.
		Map<String, String[]> privateParameterMap = eventRequest.getPrivateParameterMap();

		if (privateParameterMap != null) {
			Set<Entry<String, String[]>> entrySet = privateParameterMap.entrySet();

			for (Map.Entry<String, String[]> mapEntry : entrySet) {
				String key = mapEntry.getKey();
				boolean alreadyExists = false;

				if (existingResponseRenderParameterMap != null) {
					alreadyExists = existingResponseRenderParameterMap.containsKey(key);
				}

				if (alreadyExists) {

					// FACES-1453: Avoid clobbering existing private render parameters set on the EventResponse.
					if (logger.isTraceEnabled()) {
						String[] existingValues = existingResponseRenderParameterMap.get(key);

						logger.trace(
							"Not maintaining private render parameter name=[{0}] values=[{1}] because it already exists",
							key, existingValues);
					}
				}
				else {
					String[] values = mapEntry.getValue();
					eventResponse.setRenderParameter(key, values);
					logger.trace("Maintaining private render parameter name=[{0}] values=[{1}]", key, values);
				}
			}
		}
	}

	public void redirect(String url) throws IOException {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletResponse portletResponse = bridgeContext.getPortletResponse();

		if (portletResponse instanceof ActionResponse) {
			ActionResponse actionResponse = (ActionResponse) portletResponse;
			actionResponse.sendRedirect(url);
		}
		else if (portletResponse instanceof ResourceResponse) {
			FacesContext facesContext = FacesContext.getCurrentInstance();

			if (isJSF2PartialRequest(facesContext)) {
				redirectJSF2PartialResponse(facesContext, (ResourceResponse) portletResponse, url);
			}
			else {
				throw new UnsupportedEncodingException(
					"Can only redirect during RESOURCE_PHASE if a JSF partial/Ajax request has been triggered");
			}
		}
		else {
			throw new UnsupportedEncodingException("Unable to redirect during " +
				bridgeContext.getPortletRequestPhase());
		}
	}

	/**
	 * Copies any query paramters present in the specified "from" URL to the specified "to" URL.
	 *
	 * @param   fromURL  The String-based URL to copy query parameters from.
	 * @param   toURL    The portlet-based URL to copy query parameters to.
	 *
	 * @throws  MalformedURLException
	 */
	protected void copyRequestParameters(String fromURL, BaseURL toURL) throws MalformedURLException {
		List<RequestParameter> requestParameters = parseRequestParameters(fromURL);

		if (requestParameters != null) {

			for (RequestParameter requestParameter : requestParameters) {
				String name = requestParameter.getName();
				String value = requestParameter.getValue();
				toURL.setParameter(name, value);
				logger.debug("Copied parameter to portletURL name=[{0}] value=[{1}]", name, value);
			}
		}
	}

	protected PortletURL createActionURL(MimeResponse mimeResponse) {
		return mimeResponse.createActionURL();
	}

	protected PortletURL createRenderURL(MimeResponse mimeResponse) {
		return mimeResponse.createRenderURL();
	}

	protected ResourceURL createResourceURL(MimeResponse mimeResponse) {
		return mimeResponse.createResourceURL();
	}

	/**
	 * Parses the specified URL and returns a list of query parameters that are found.
	 *
	 * @param   url  The URL to parse.
	 *
	 * @return  The list of query parameters found.
	 *
	 * @throws  MalformedURLException
	 */
	protected List<RequestParameter> parseRequestParameters(String url) throws MalformedURLException {

		List<RequestParameter> requestParameters = null;

		if (url != null) {
			int pos = url.indexOf("?");

			if (pos >= 0) {
				String queryString = url.substring(pos + 1);

				if ((queryString != null) && (queryString.length() > 0)) {
					requestParameters = new ArrayList<RequestParameter>();

					String[] queryParameters = queryString.split(BridgeConstants.REGEX_AMPERSAND_DELIMITER);

					for (String queryParameter : queryParameters) {
						String[] nameValueArray = queryParameter.split("[=]");

						if (nameValueArray != null) {

							if (nameValueArray.length == 2) {
								String name = nameValueArray[0];
								String value = nameValueArray[1];
								requestParameters.add(new RequestParameter(name, value));
							}
							else {
								throw new MalformedURLException("invalid name/value pair: " + queryParameter);
							}
						}
					}
				}
			}
		}

		return requestParameters;
	}

	protected boolean getContextParamAbleToSetHttpStatusCode(boolean defaultValue) {
		String contextParamValue = bridgeConfig.getContextParameter(
				BridgeConfigConstants.PARAM_CONTAINER_ABLE_TO_SET_HTTP_STATUS_CODE1);

		if (contextParamValue == null) {

			// Backwards compatibility
			contextParamValue = bridgeConfig.getContextParameter(
					BridgeConfigConstants.PARAM_CONTAINER_ABLE_TO_SET_HTTP_STATUS_CODE2);
		}

		return BooleanHelper.toBoolean(contextParamValue, defaultValue);
	}

	public boolean isAbleToAddScriptResourceToHead() {
		return isMarkupHeadElementSupported();
	}

	public boolean isAbleToAddScriptTextToHead() {
		return isMarkupHeadElementSupported();
	}

	public boolean isAbleToAddStyleSheetResourceToHead() {
		return isMarkupHeadElementSupported();
	}

	public boolean isPostRedirectGetSupported() {
		return true;
	}

	/**
	 * Determines whether or not the portlet container supports the standard Portlet 2.0 mechanism for adding resources
	 * to the <head>...</head> section of the rendered portal page. Section PLT.12.5.4 of the Portlet 2.0 spec indicates
	 * that this is an "optional" feature for vendors to implement.
	 *
	 * @return  True if the portlet container supports the standard Portlet 2.0 mechanism for adding resources.
	 */
	protected boolean isMarkupHeadElementSupported() {
		return markupHeadElementSupported;
	}

	public boolean isAbleToSetHttpStatusCode() {

		if (ableToSetHttpStatusCode == null) {

			// Although it's not the most performant option, it's safest to assume that the portlet container has not
			// implemented this feature. That way, the ResourceHandlerImpl will always deliver stuff like jsf.js back
			// to the browser. As we support more portlet containers in the future (Pluto, etc.) we can create
			// implementations that override this.
			boolean defaultValue = false;
			ableToSetHttpStatusCode = getContextParamAbleToSetHttpStatusCode(defaultValue);
		}

		return ableToSetHttpStatusCode;
	}

	public boolean isAbleToSetResourceResponseBufferSize() {
		return true;
	}

	public boolean isAbleToForwardOnDispatch() {
		return true;
	}

	public String[] getHeader(String name) {
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletRequest portletRequest = bridgeContext.getPortletRequest();
		Enumeration<String> properties = portletRequest.getProperties(name);
		List<String> propertyList = new ArrayList<String>();

		while (properties.hasMoreElements()) {
			propertyList.add(properties.nextElement());
		}

		return propertyList.toArray(new String[propertyList.size()]);
	}

	public long getHttpServletRequestDateHeader(String name) {

		// Unsupported by default implementation.
		return -1L;
	}

	public void setMimeResponseContentType(MimeResponse mimeResponse, String contentType) {
		mimeResponse.setContentType(contentType);
	}

	public PhaseId getPhaseId() {

		// no-op
		return null;
	}

	public String getRequestParameter(String name) {
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return fixRequestParameterValue(bridgeContext.getPortletRequest().getParameter(name));
	}

	public String[] getRequestParameterValues(String name) {
		String[] values = null;
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		String[] originalValues = bridgeContext.getPortletRequest().getParameterValues(name);

		if (originalValues != null) {
			values = new String[originalValues.length];

			for (int i = 0; i < originalValues.length; i++) {
				values[i] = fixRequestParameterValue(originalValues[i]);
			}
		}

		return values;
	}

	public String getRequestQueryString() {

		if (requestQueryString == null) {
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			PortletRequest portletRequest = bridgeContext.getPortletRequest();
			requestQueryString = (String) portletRequest.getAttribute(REQUEST_ATTR_QUERY_STRING);

			if (requestQueryString == null) {

				// Some portlet bridges wrap the portal's PortletRequest implementation instance (which prevents us from
				// getting the query_string). As a workaround, we might still be able to get  the original
				// PortletRequest instance, because the Portlet spec says it must be stored in the
				// "javax.portlet.request" attribute.
				Object portletRequestAsObject = portletRequest.getAttribute(REQUEST_ATTR_PORTLET_REQUEST);

				if ((portletRequestAsObject != null) && (portletRequestAsObject instanceof PortletRequest)) {
					portletRequest = (PortletRequest) portletRequestAsObject;
					requestQueryString = (String) portletRequest.getAttribute(REQUEST_ATTR_QUERY_STRING);
				}
			}
		}

		return requestQueryString;
	}

	public String getRequestURL() {

		if (requestURL == null) {

			// Note that this is an approximation (best guess) of the original URL.
			StringBuilder buf = new StringBuilder();
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			PortletRequest portletRequest = bridgeContext.getPortletRequest();
			buf.append(portletRequest.getScheme());
			buf.append(StringPool.COLON);
			buf.append(StringPool.FORWARD_SLASH);
			buf.append(StringPool.FORWARD_SLASH);
			buf.append(portletRequest.getServerName());
			buf.append(StringPool.COLON);
			buf.append(portletRequest.getServerPort());
			buf.append(portletRequest.getContextPath());
			buf.append(StringPool.QUESTION);
			buf.append(getRequestQueryString());
			requestURL = buf.toString();
		}

		return requestURL;
	}

	public String getResponseNamespace() {

		if (responseNamespace == null) {

			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			responseNamespace = bridgeContext.getPortletResponse().getNamespace();

			if (BridgeConstants.WSRP_REWRITE.equals(responseNamespace)) {
				responseNamespace = bridgeContext.getPortletConfig().getPortletName() +
					bridgeContext.getPortletContext().getPortletContextName();
			}
		}

		return responseNamespace;
	}
}
