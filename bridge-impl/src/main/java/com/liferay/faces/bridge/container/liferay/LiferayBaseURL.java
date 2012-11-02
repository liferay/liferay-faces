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
import java.io.Writer;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.portlet.BaseURL;
import javax.portlet.PortletMode;
import javax.portlet.PortletSecurityException;
import javax.portlet.WindowState;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.util.FacesURLEncoder;
import com.liferay.faces.bridge.util.URLParameter;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * Liferay's PortletURLImpl#toString() method returns an value that is totally unusable to the bridge. It adds all kinds
 * of request parameters and isn't designed to take frameworks like JSF 2 into account. For this reason, all we can rely
 * on it to do for us is to provide the prefix of the URL, meaning: protocol://hostname:port/path and nothing else.
 * Therefore it was necessary to create implementations here in the bridge for ActionURL, RenderURL, and ResourceURL.
 *
 * @author  Neil Griffin
 */
public abstract class LiferayBaseURL implements BaseURL {
	
	private static final String [] SYSTEM_PARAMETERS = new String[] {
		LiferayConstants.P_AUTH,
		LiferayConstants.P_P_AUTH,
		LiferayConstants.P_P_ID,
		LiferayConstants.P_P_STATE_RCV,
		LiferayConstants.P_P_CACHEABILITY,
		LiferayConstants.P_P_COL_ID,
		LiferayConstants.P_P_COL_COUNT,
		LiferayConstants.P_P_COL_POS,
		LiferayConstants.P_O_P_ID,
		LiferayConstants.DO_AS_USER_ID,
		LiferayConstants.DO_AS_USER_LANGUAGE_ID,
		LiferayConstants.DO_AS_GROUP_ID,
		LiferayConstants.REFERER_PLID,
		LiferayConstants.CONTROL_PANEL_CATEGORY,
		
		// This parameter used only in ResourceURL, but it is better to process it here
		LiferayConstants.P_P_RESOURCE_ID
	};

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayBaseURL.class);

	// Private Data Members
	private final Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
	private final BaseURL baseURL;
	private String toStringValue;

	// Protected Data Members
	protected String responseNamespace;

	public LiferayBaseURL(BaseURL baseURL, String responseNamespace) {
		this.baseURL = baseURL;
		this.responseNamespace = responseNamespace;
	}

	public void addProperty(String key, String value) {
		// Ignore
	}

	public String encode(String value) {

		String encodedValue = StringPool.BLANK;

		if (value != null) {

			if (value.length() == 0) {
				encodedValue = StringPool.SPACE;
			}
			else {

				try {
					encodedValue = URLEncoder.encode(value, StringPool.UTF8);
				}
				catch (UnsupportedEncodingException e) {
					logger.error(e);
					encodedValue = StringPool.SPACE;
				}
			}
		}

		return encodedValue;
	}

	public void resetToString() {
		toStringValue = null;
	}

	@Override
	public String toString() {

		if (toStringValue == null) {

			StringBuilder url = new StringBuilder();
			
			// Build up a new URL string based on the one returned by Liferay, but discard everything after the
			// question mark because it's filled with all kinds of unnecessary stuff.
			ParseURLUtil.ParsedBaseURL parsedLiferayURL = ParseURLUtil.parse(ParseURLUtil.ParsedBaseURL.class, baseURL);
			url.append(parsedLiferayURL.getPrefix());

			// Parameters can be removed by friendlyUrlMapper - all parameters are optional to addition
			boolean firstParameter = true;
			Map<String, String> parsedLiferayURLParameterMap = parsedLiferayURL.getParameterMap();
			
			// Mandatory parameters
			if (isPortletLifecycleIdRequired() || parsedLiferayURLParameterMap.containsKey(LiferayConstants.P_P_LIFECYCLE)) {
				appendParameterToURL(firstParameter, LiferayConstants.P_P_LIFECYCLE, getPortletLifecycleId(), url);
				firstParameter = false;
			}
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String, Object> applicationMap = facesContext.getExternalContext().getApplicationMap();
			String parameterValue = null;

			if (isWindowStateRequired()) {
				WindowState windowState = getWindowState();

				if (windowState == null) {
					parameterValue = (String) applicationMap.get(getResponseNamespace() + LiferayConstants.P_P_STATE);
				}
				else {
					parameterValue = windowState.toString();
				}

				appendParameterToURL(LiferayConstants.P_P_STATE, parameterValue, url);
			}
			
			if (isPortletModeRequired()) {
				PortletMode portletMode = getPortletMode();

				if (portletMode == null) {
					parameterValue = (String) applicationMap.get(getResponseNamespace() + LiferayConstants.P_P_MODE);
				}
				else {
					parameterValue = portletMode.toString();
				}

				appendParameterToURL(LiferayConstants.P_P_MODE, parameterValue, url);
			}
			
			// Optional system parameters
			for (String name : SYSTEM_PARAMETERS) {
				String value = parsedLiferayURLParameterMap.get(name);
				if (value != null) {
					appendParameterToURL(firstParameter, name, value, url);
					firstParameter = false;
				}
			}

			// Add request parameters from the request parameter map.
			String namespace = responseNamespace;

			if (namespace.startsWith(BridgeConstants.WSRP)) {
				namespace = StringPool.BLANK;
			}


			for (Map.Entry<String, String[]> mapEntry : this.getParameterMap().entrySet()) {
				String[] values = mapEntry.getValue();
				String fullName = namespace + mapEntry.getKey();
				String value = parsedLiferayURLParameterMap.get(fullName);
				
				if (value != null) {
					for (String s : values) {
						appendParameterToURL(firstParameter, fullName, encode(s), url);
						firstParameter = false;
					}
				}
			}

			// Add WSRP URL parameters
			List<URLParameter> wsrpParameters = parsedLiferayURL.getWsrpParameters();

			for (URLParameter wsrpParameter : wsrpParameters) {

				appendParameterToURL(firstParameter, wsrpParameter.getName(), wsrpParameter.getValue(), url);
				firstParameter = false;
			}

			toStringValue = url.toString();
		}

		return toStringValue;
	}

	public void write(Writer writer) throws IOException {
		writer.write(toString());
	}

	public void write(Writer writer, boolean escapeXML) throws IOException {

		String toStringValue = toString();

		if (escapeXML) {
			toStringValue = FacesURLEncoder.encode(toStringValue, StringPool.UTF8);
		}

		writer.write(toStringValue);
	}

	protected void appendParameterToURL(String parameterName, String parameterValue, StringBuilder url) {
		appendParameterToURL(false, parameterName, parameterValue, url);
	}

	protected void appendParameterToURL(boolean firstParameter, String parameterName, String parameterValue,
		StringBuilder url) {
		
		if (!firstParameter) {
			url.append(StringPool.AMPERSAND);
		}

		url.append(parameterName);
		url.append(StringPool.EQUAL);
		url.append(parameterValue);

		logger.debug("Appended param to URL name=[{0}] parameterValue=[{1}]", parameterName, parameterValue);
	}

	public boolean isPortletModeRequired() {
		return getPortletMode() != PortletMode.VIEW;
	}

	public boolean isWindowStateRequired() {
		return getWindowState() != WindowState.NORMAL;
	}
	
	public boolean isPortletLifecycleIdRequired() {
		return getPortletLifecycleId().equals(LiferayConstants.LIFECYCLE_RENDER_PHASE_ID);
	}

	public void setParameter(String name, String value) {
		parameterMap.put(name, new String[] {value});
		baseURL.setParameter(name, value);
		resetToString();
	}

	public void setParameter(String name, String[] values) {
		parameterMap.put(name, values);
		baseURL.setParameter(name, values);
		resetToString();
	}

	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}

	public void setParameters(Map<String, String[]> parameters) {
		parameterMap.putAll(parameters);
		baseURL.setParameters(parameters);
		resetToString();
	}

	public abstract String getPortletLifecycleId();

	public PortletMode getPortletMode() {
		return PortletMode.VIEW;
	}

	public void setProperty(String key, String value) {
		// Ignore
	}

	protected boolean isValidParameter(String parameterValue) {
		return (parameterValue != null);
	}

	public String getResponseNamespace() {
		return responseNamespace;
	}

	public void setSecure(boolean secure) throws PortletSecurityException {
		// Ignore
	}

	public WindowState getWindowState() {
		return WindowState.NORMAL;
	}
}
