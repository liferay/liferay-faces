/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.url.internal;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.render.ResponseStateManager;
import javax.portlet.BaseURL;
import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.internal.ExternalContextImpl;
import com.liferay.faces.bridge.context.url.BridgeURI;
import com.liferay.faces.bridge.context.url.BridgeURL;
import com.liferay.faces.bridge.helper.internal.WindowStateHelper;
import com.liferay.faces.bridge.internal.BridgeConstants;
import com.liferay.faces.bridge.util.internal.RequestParameter;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This is a utility class used only by {@link ExternalContextImpl} that represents a URL with attributes that the
 * Bridge Spec is concerned with. The getter methods in this class make heavy use of lazy-initialization for performance
 * reasons, because it is unlikely that every method will be called.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeURLBase implements BridgeURL {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeURLBase.class);

	protected static final String RELATIVE_PATH_PREFIX = "../";

	// Private Data Members
	private Boolean facesViewTarget;
	private boolean selfReferencing;
	private Map<String, String[]> parameterMap;
	private boolean secure;
	String viewIdRenderParameterName;
	String viewIdResourceParameterName;

	// Protected Data Members
	protected BridgeURI bridgeURI;
	protected String viewId;

	// Protected Data Members
	protected BridgeContext bridgeContext;

	public BridgeURLBase(BridgeContext bridgeContext, BridgeURI bridgeURI, String viewId) {
		this.bridgeContext = bridgeContext;
		this.bridgeURI = bridgeURI;
		this.viewId = viewId;

		BridgeConfig bridgeConfig = bridgeContext.getBridgeConfig();
		this.viewIdRenderParameterName = bridgeConfig.getViewIdRenderParameterName();
		this.viewIdResourceParameterName = bridgeConfig.getViewIdResourceParameterName();
	}

	@Override
	public String toString() {

		String stringValue = null;

		try {

			// Ask the Portlet Container for a BaseURL that contains the modified parameters.
			BaseURL baseURL = toBaseURL();

			// If the URL string has escaped characters (like %20 for space, etc) then ask the
			// portlet container to create an escaped representation of the URL string.
			if (bridgeURI.isEscaped()) {
				StringWriter urlWriter = new StringWriter();

				try {
					baseURL.write(urlWriter, true);
				}
				catch (IOException e) {
					logger.error(e);
					stringValue = baseURL.toString();
				}

				stringValue = urlWriter.toString();
			}

			// Otherwise, ask the portlet container to create a normal (non-escaped) string
			// representation of the URL string.
			else {
				stringValue = baseURL.toString();
			}
		}
		catch (MalformedURLException e) {
			logger.error(e);
		}

		return stringValue;
	}

	/**
	 * Returns a {@link BaseURL} representation of the bridge URL.
	 *
	 * @throws  MalformedURLException
	 */
	protected abstract BaseURL toBaseURL() throws MalformedURLException;

	protected String _toString(boolean modeChanged) {
		return _toString(modeChanged, null);
	}

	protected String _toString(boolean modeChanged, Set<String> excludedParameterNames) {

		StringBuilder buf = new StringBuilder();

		String uri = bridgeURI.toString();

		int endPos = uri.indexOf(StringPool.QUESTION);

		if (endPos < 0) {
			endPos = uri.length();
		}

		if (bridgeURI.isPortletScheme()) {
			Bridge.PortletPhase urlPortletPhase = bridgeURI.getPortletPhase();

			if (urlPortletPhase == Bridge.PortletPhase.ACTION_PHASE) {
				buf.append(uri.substring("portlet:action".length(), endPos));
			}
			else if (urlPortletPhase == Bridge.PortletPhase.RENDER_PHASE) {
				buf.append(uri.substring("portlet:render".length(), endPos));
			}
			else {
				buf.append(uri.substring("portlet:resource".length(), endPos));
			}
		}
		else {
			buf.append(uri.subSequence(0, endPos));
		}

		boolean firstParam = true;

		buf.append(StringPool.QUESTION);

		Set<String> parameterNames = getParameterMap().keySet();
		boolean foundFacesViewIdParam = false;
		boolean foundFacesViewPathParam = false;

		for (String parameterName : parameterNames) {

			boolean addParameter = false;
			String parameterValue = getParameter(parameterName);

			if (Bridge.PORTLET_MODE_PARAMETER.equals(parameterName)) {

				// Only add the "javax.portlet.faces.PortletMode" parameter if it has a valid value.
				if (parameterValue != null) {
					addParameter = bridgeContext.getPortletRequest().isPortletModeAllowed(new PortletMode(
								parameterValue));
				}
			}
			else if (Bridge.PORTLET_SECURE_PARAMETER.equals(parameterName)) {
				addParameter = BooleanHelper.isBooleanToken(parameterValue);
			}
			else if (Bridge.PORTLET_WINDOWSTATE_PARAMETER.equals(parameterName)) {
				addParameter = WindowStateHelper.isValid(parameterValue);
			}
			else {

				if (!foundFacesViewIdParam) {
					foundFacesViewIdParam = Bridge.FACES_VIEW_ID_PARAMETER.equals(parameterName);
				}

				if (!foundFacesViewPathParam) {
					foundFacesViewPathParam = Bridge.FACES_VIEW_PATH_PARAMETER.equals(parameterName);
				}

				addParameter = true;
			}

			if ((addParameter) &&
					((excludedParameterNames == null) || !excludedParameterNames.contains(parameterName))) {

				if (firstParam) {
					firstParam = false;
				}
				else {
					buf.append(StringPool.AMPERSAND);
				}

				buf.append(parameterName);
				buf.append(StringPool.EQUAL);
				buf.append(parameterValue);
			}
		}

		// If the "_jsfBridgeViewId" and "_jsfBridgeViewPath" parameters are not present in the URL, then add a
		// parameter that indicates the target Faces viewId.
		if (!foundFacesViewIdParam && !foundFacesViewPathParam && isFacesViewTarget()) {

			if (!bridgeURI.isPortletScheme()) {

				// Note that if the "javax.portlet.faces.PortletMode" parameter is specified, then a mode change is
				// being requested and the target Faces viewId parameter must NOT be added.
				if (!modeChanged) {

					if (!firstParam) {
						buf.append(StringPool.AMPERSAND);
					}

					buf.append(getViewIdParameterName());
					buf.append(StringPool.EQUAL);

					String contextPath = bridgeContext.getPortletRequest().getContextPath();
					String contextRelativePath = bridgeURI.getContextRelativePath(contextPath);
					buf.append(contextRelativePath);
				}
			}
		}

		return buf.toString();
	}

	/**
	 * Copies any query paramters present in the specified "from" URL to the specified "to" URL.
	 */
	protected void copyParameters(String fromURL, BaseURL toURL) throws MalformedURLException {
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

	protected PortletURL createActionURL(String fromURL) throws MalformedURLException {

		try {
			logger.debug("createActionURL fromURL=[" + fromURL + "]");

			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			MimeResponse mimeResponse = (MimeResponse) bridgeContext.getPortletResponse();
			PortletURL actionURL = mimeResponse.createActionURL();
			copyParameters(fromURL, actionURL);

			return actionURL;
		}
		catch (ClassCastException e) {
			throw new MalformedURLException(e.getMessage());
		}
	}

	protected ResourceURL createPartialActionURL(String fromURL) throws MalformedURLException {
		logger.debug("createPartialActionURL fromURL=[" + fromURL + "]");

		return createResourceURL(fromURL);
	}

	protected PortletURL createRenderURL(String fromURL) throws MalformedURLException {
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		Bridge.PortletPhase portletRequestPhase = bridgeContext.getPortletRequestPhase();

		if ((portletRequestPhase == Bridge.PortletPhase.RENDER_PHASE) ||
				(portletRequestPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {

			try {
				logger.debug("createRenderURL fromURL=[" + fromURL + "]");

				MimeResponse mimeResponse = (MimeResponse) bridgeContext.getPortletResponse();
				PortletURL renderURL = mimeResponse.createRenderURL();
				copyParameters(fromURL, renderURL);

				return renderURL;
			}
			catch (ClassCastException e) {
				throw new MalformedURLException(e.getMessage());
			}
		}
		else {
			throw new MalformedURLException("Unable to create a RenderURL during " + portletRequestPhase.toString());
		}

	}

	protected ResourceURL createResourceURL(String fromURL) throws MalformedURLException {

		try {
			logger.debug("createResourceURL fromURL=[" + fromURL + "]");

			// Ask the portlet container to create a portlet resource URL.
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			MimeResponse mimeResponse = (MimeResponse) bridgeContext.getPortletResponse();
			ResourceURL resourceURL = mimeResponse.createResourceURL();

			// If the "javax.faces.resource" token is found in the URL, then
			int tokenPos = fromURL.indexOf("javax.faces.resource");

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
					logger.error("There is no slash after the [{0}] token in resourceURL=[{1}]", "javax.faces.resource",
						fromURL);
				}

				resourceURL.setParameter("javax.faces.resource", resourceName);
				logger.debug("Added parameter to portletURL name=[{0}] value=[{1}]", "javax.faces.resource",
					resourceName);
			}

			// Copy the request parameters to the portlet resource URL.
			copyParameters(fromURL, resourceURL);

			return resourceURL;
		}
		catch (ClassCastException e) {
			throw new MalformedURLException(e.getMessage());
		}
	}

	/**
	 * Determines whether or not the specified files have the same path (prefix) and extension (suffix).
	 *
	 * @param   file1  The first file to compare.
	 * @param   file2  The second file to compare.
	 *
	 * @return  <code>true</code> if the specified files have the same path (prefix) and extension (suffix), otherwise
	 *          <code>false</code>.
	 */
	protected boolean matchPathAndExtension(String file1, String file2) {

		boolean match = false;

		String path1 = null;
		int lastSlashPos = file1.lastIndexOf(StringPool.FORWARD_SLASH);

		if (lastSlashPos > 0) {
			path1 = file1.substring(0, lastSlashPos);
		}

		String path2 = null;
		lastSlashPos = file2.lastIndexOf(StringPool.FORWARD_SLASH);

		if (lastSlashPos > 0) {
			path2 = file2.substring(0, lastSlashPos);
		}

		if (((path1 == null) && (path2 == null)) || ((path1 != null) && (path2 != null) && path1.equals(path2))) {

			String ext1 = null;
			int lastDotPos = file1.indexOf(StringPool.PERIOD);

			if (lastDotPos > 0) {
				ext1 = file1.substring(lastDotPos);
			}

			String ext2 = null;
			lastDotPos = file2.indexOf(StringPool.PERIOD);

			if (lastDotPos > 0) {
				ext2 = file2.substring(lastDotPos);
			}

			if (((ext1 == null) && (ext2 == null)) || ((ext1 != null) && (ext2 != null) && ext1.equals(ext2))) {
				match = true;
			}
		}

		return match;
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

				if (queryString.length() > 0) {
					requestParameters = new ArrayList<RequestParameter>();

					String[] queryParameters = queryString.split(BridgeConstants.REGEX_AMPERSAND_DELIMITER);

					for (String queryParameter : queryParameters) {
						String[] nameValueArray = queryParameter.split("[=]");

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

		return requestParameters;
	}

	protected String removeParameter(String name) {
		String[] values = getParameterMap().remove(name);
		String value = null;

		if ((values != null) && (values.length > 0)) {
			value = values[0];
		}

		return value;
	}

	public boolean isSecure() {
		return secure;
	}

	public boolean isSelfReferencing() {
		return selfReferencing;
	}

	public String getParameter(String name) {
		String value = null;
		Map<String, String[]> parameterMap = getParameterMap();
		String[] values = parameterMap.get(name);

		if (values == null) {
			PortletResponse portletResponse = bridgeContext.getPortletResponse();
			String responseNamespace = portletResponse.getNamespace();
			values = parameterMap.get(responseNamespace + name);
		}

		if ((values != null) && (values.length > 0)) {
			value = values[0];
		}

		return value;
	}

	public void setParameter(String name, String[] value) {
		getParameterMap().put(name, value);
	}

	public void setParameter(String name, String value) {
		getParameterMap().put(name, new String[] { value });
	}

	public Map<String, String[]> getParameterMap() {

		if (parameterMap == null) {
			parameterMap = new LinkedHashMap<String, String[]>(bridgeURI.getParameterMap());
		}

		return parameterMap;
	}

	protected void setPortletModeParameter(String portletMode, PortletURL portletURL) {

		if (portletMode != null) {

			try {
				PortletMode candidatePortletMode = new PortletMode(portletMode);

				if (bridgeContext.getPortletRequest().isPortletModeAllowed(candidatePortletMode)) {
					portletURL.setPortletMode(candidatePortletMode);
				}
				else {
					// TestPage118: encodeActionURLWithInvalidModeRenderTest
				}
			}
			catch (PortletModeException e) {
				logger.error(e);
			}
		}
	}

	protected void setRenderParameters(BaseURL baseURL) {

		// Get the modified parameter map.
		Map<String, String[]> urlParameterMap = getParameterMap();

		// Copy the public render parameters of the current view to the BaseURL.
		Map<String, String[]> preservedActionParams = bridgeContext.getPreservedActionParams();
		PortletRequest portletRequest = bridgeContext.getPortletRequest();
		Map<String, String[]> publicParameterMap = portletRequest.getPublicParameterMap();

		if (publicParameterMap != null) {
			Set<Entry<String, String[]>> publicParamterMapEntrySet = publicParameterMap.entrySet();

			for (Entry<String, String[]> mapEntry : publicParamterMapEntrySet) {
				String publicParameterName = mapEntry.getKey();

				// Note that preserved action parameters, parameters that already exist in the URL string,
				// and "javax.faces.ViewState" must not be copied.
				if (!ResponseStateManager.VIEW_STATE_PARAM.equals(publicParameterName) &&
						!preservedActionParams.containsKey(publicParameterName) &&
						!urlParameterMap.containsKey(publicParameterName)) {
					baseURL.setParameter(publicParameterName, mapEntry.getValue());
				}
			}
		}

		// Copy the private render parameters of the current view to the BaseURL.
		Map<String, String[]> privateParameterMap = portletRequest.getPrivateParameterMap();

		if (privateParameterMap != null) {
			Set<Entry<String, String[]>> privateParameterMapEntrySet = privateParameterMap.entrySet();

			for (Entry<String, String[]> mapEntry : privateParameterMapEntrySet) {
				String privateParameterName = mapEntry.getKey();

				// Note that preserved action parameters, parameters that already exist in the URL string,
				// and "javax.faces.ViewState" must not be copied.
				if (!ResponseStateManager.VIEW_STATE_PARAM.equals(privateParameterName) &&
						!preservedActionParams.containsKey(privateParameterName) &&
						!urlParameterMap.containsKey(privateParameterName)) {
					baseURL.setParameter(privateParameterName, mapEntry.getValue());
				}
			}
		}
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	protected void setSecureParameter(String secure, BaseURL baseURL) {

		if (secure != null) {

			try {
				baseURL.setSecure(BooleanHelper.toBoolean(secure));
			}
			catch (PortletSecurityException e) {
				logger.error(e);
			}
		}
	}

	public void setSelfReferencing(boolean selfReferencing) {
		this.selfReferencing = selfReferencing;
	}

	public boolean isFacesViewTarget() {

		if (facesViewTarget == null) {

			String contextPath = bridgeContext.getPortletRequest().getContextPath();
			String contextRelativePath = bridgeURI.getContextRelativePath(contextPath);
			String potentialFacesViewId = contextRelativePath;

			if ((viewId != null) && (viewId.equals(potentialFacesViewId))) {
				facesViewTarget = Boolean.TRUE;
			}
			else {

				// If the context relative view path maps to an actual Faces View due to a serlvet-mapping entry, then
				// return true.
				potentialFacesViewId = bridgeContext.getFacesViewIdFromPath(potentialFacesViewId, false);

				if (potentialFacesViewId != null) {
					facesViewTarget = Boolean.TRUE;
				}

				// Otherwise,
				else {

					// NOTE: It might be (as in the case of the TCK) that a navigation-rule has fired, and the developer
					// specified something like <to-view-id>/somepath/foo.jsp</to-view-id> instead of using the
					// appropriate extension mapped suffix like <to-view-id>/somepath/foo.jsf</to-view-id>.
					if (contextRelativePath == null) {
						potentialFacesViewId = viewId;
					}
					else {
						potentialFacesViewId = contextRelativePath;
					}

					if ((viewId != null) && (matchPathAndExtension(viewId, potentialFacesViewId))) {
						logger.debug(
							"Regarding path=[{0}] as a Faces view since it has the same path and extension as the current viewId=[{1}]",
							potentialFacesViewId, viewId);
						facesViewTarget = Boolean.TRUE;
					}
					else {
						facesViewTarget = Boolean.FALSE;
					}
				}
			}

		}

		return facesViewTarget;
	}

	protected String getViewIdParameterName() {

		if (bridgeURI.isPortletScheme() && (bridgeURI.getPortletPhase() == Bridge.PortletPhase.RESOURCE_PHASE)) {
			return viewIdResourceParameterName;
		}
		else {
			return viewIdRenderParameterName;
		}
	}

	protected void setWindowStateParameter(String windowState, PortletURL portletURL) {

		if (windowState != null) {

			try {
				WindowState candidateWindowState = new WindowState(windowState);

				if (bridgeContext.getPortletRequest().isWindowStateAllowed(candidateWindowState)) {
					portletURL.setWindowState(candidateWindowState);
				}
				else {
					// TestPage120: encodeActionURLWithInvalidWindowStateRenderTest
				}
			}
			catch (WindowStateException e) {
				logger.error(e);
			}
		}

	}

}
