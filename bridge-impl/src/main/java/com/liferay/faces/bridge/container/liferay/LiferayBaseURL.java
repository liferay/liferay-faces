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
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.portlet.BaseURL;
import javax.portlet.PortletMode;
import javax.portlet.PortletSecurityException;
import javax.portlet.ResourceURL;
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

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayBaseURL.class);

	// Private Data Members
	Map<String, String[]> parameterMap;
	private ParsedBaseURL parsedLiferayURL;
	private String toStringValue;

	// Protected Data Members
	protected String responseNamespace;

	public LiferayBaseURL(ParsedBaseURL parsedLiferayURL, String responseNamespace) {
		this.parsedLiferayURL = parsedLiferayURL;
		this.responseNamespace = responseNamespace;
		this.parameterMap = new LinkedHashMap<String, String[]>();
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
			url.append(parsedLiferayURL.getPrefix());

			// Possibly add the p_auth parameter.
			boolean firstParameter = true;
			Map<String, String> parsedLiferayURLParameterMap = parsedLiferayURL.getParameterMap();
			String portalAuthToken = parsedLiferayURLParameterMap.get(LiferayConstants.P_AUTH);

			if (portalAuthToken != null) {

				appendParameterToURL(firstParameter, LiferayConstants.P_AUTH, portalAuthToken, url);
				firstParameter = false;
			}

			// Possibly add the p_p_auth parameter.
			String portletAuthToken = parsedLiferayURLParameterMap.get(LiferayConstants.P_P_AUTH);

			if (portletAuthToken != null) {

				appendParameterToURL(firstParameter, LiferayConstants.P_P_AUTH, portletAuthToken, url);
				firstParameter = false;
			}

			// Always add the p_p_id parameter
			String parameterValue = responseNamespace;

			if (parameterValue.startsWith(StringPool.UNDERLINE)) {
				parameterValue = parameterValue.substring(1);
			}

			if (parameterValue.endsWith(StringPool.UNDERLINE)) {
				parameterValue = parameterValue.substring(0, parameterValue.length() - 1);
			}

			appendParameterToURL(firstParameter, LiferayConstants.P_P_ID, parameterValue, url);

			firstParameter = false;

			// Always add the p_p_lifecycle parameter.
			appendParameterToURL(LiferayConstants.P_P_LIFECYCLE, getPortletLifecycleId(), url);

			// Possibly add the p_p_state parameter.
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String, Object> applicationMap = facesContext.getExternalContext().getApplicationMap();

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

			// Possibly add the p_p_state_rcv parameter.
			String stateRestoreCurrentView = parsedLiferayURLParameterMap.get(LiferayConstants.P_P_STATE_RCV);

			if (stateRestoreCurrentView != null) {
				appendParameterToURL(LiferayConstants.P_P_STATE_RCV, stateRestoreCurrentView, url);
			}

			// Possibly add the p_p_mode parameter.
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

			// Possibly add the p_p_cacheability parameter
			if (this instanceof ResourceURL) {
				String cacheability = parsedLiferayURLParameterMap.get(LiferayConstants.P_P_CACHEABILITY);

				if (cacheability != null) {
					appendParameterToURL(LiferayConstants.P_P_CACHEABILITY, cacheability, url);
				}
			}

			// Always add the p_p_col_id parameter
			parameterValue = (String) applicationMap.get(responseNamespace + LiferayConstants.P_P_COL_ID);
			appendParameterToURL(LiferayConstants.P_P_COL_ID, parameterValue, url);

			// Possibly add the p_p_col_count parameter.
			parameterValue = (String) applicationMap.get(responseNamespace + LiferayConstants.P_P_COL_COUNT);
			appendParameterToURL(LiferayConstants.P_P_COL_COUNT, parameterValue, url);

			// Add the p_p_col_pos parameter if it is greater than zero (same logic as Liferay's
			// PortletURLImpl.toString())
			parameterValue = (String) applicationMap.get(responseNamespace + LiferayConstants.P_P_COL_POS);

			if ((parameterValue != null) && (parameterValue.length() > 0)) {

				try {
					int colPos = Integer.parseInt(parameterValue);

					if (colPos > 0) {
						appendParameterToURL(LiferayConstants.P_P_COL_POS, parameterValue, url);
					}
				}
				catch (NumberFormatException e) {
					// ignore
				}
			}

			// Possibly add the p_o_p_id parameter.
			String outerPortletId = parsedLiferayURLParameterMap.get(LiferayConstants.P_O_P_ID);

			if (outerPortletId != null) {
				appendParameterToURL(LiferayConstants.P_O_P_ID, parameterValue, url);
			}

			// Possibly add the doAsUserId parameter.
			String doAsUserId = parsedLiferayURLParameterMap.get(LiferayConstants.DO_AS_USER_ID);

			if (doAsUserId != null) {
				appendParameterToURL(LiferayConstants.DO_AS_USER_ID, doAsUserId, url);
			}

			// Possibly add the doAsUserLanguageId parameter.
			String doAsUserLanguageId = parsedLiferayURLParameterMap.get(LiferayConstants.DO_AS_USER_LANGUAGE_ID);

			if (doAsUserLanguageId != null) {
				appendParameterToURL(LiferayConstants.DO_AS_USER_LANGUAGE_ID, doAsUserLanguageId, url);
			}

			// Possibly add the doAsGroupId parameter.
			String doAsGroupId = parsedLiferayURLParameterMap.get(LiferayConstants.DO_AS_GROUP_ID);

			if (doAsGroupId != null) {
				appendParameterToURL(LiferayConstants.DO_AS_GROUP_ID, doAsGroupId, url);
			}

			// Possibly add the refererPlid parameter.
			String refererPlid = parsedLiferayURLParameterMap.get(LiferayConstants.REFERER_PLID);

			if (refererPlid != null) {
				appendParameterToURL(LiferayConstants.REFERER_PLID, refererPlid, url);
			}

			// Possibly add the controlPanelCategory parameter.
			String controlPanelCategory = parsedLiferayURLParameterMap.get(LiferayConstants.CONTROL_PANEL_CATEGORY);

			if (controlPanelCategory != null) {
				appendParameterToURL(LiferayConstants.CONTROL_PANEL_CATEGORY, controlPanelCategory, url);
			}

			// Add request parameters from the request parameter map.
			String namespace = responseNamespace;

			if (namespace.startsWith(BridgeConstants.WSRP)) {
				namespace = StringPool.BLANK;
			}

			Set<Map.Entry<String, String[]>> mapEntries = this.getParameterMap().entrySet();

			if (mapEntries != null) {

				for (Map.Entry<String, String[]> mapEntry : mapEntries) {
					String[] values = mapEntry.getValue();

					if ((values != null) && (values.length > 0)) {
						String parameterName = mapEntry.getKey();
						parameterValue = values[0];

						if (isValidParameter(parameterValue)) {

							appendParameterToURL(namespace + parameterName, encode(parameterValue), url);
						}
					}
				}
			}

			// Add WSRP URL parameters
			List<URLParameter> wsrpParameters = parsedLiferayURL.getWsrpParameters();

			for (URLParameter wsrpParameter : wsrpParameters) {

				appendParameterToURL(wsrpParameter.getName(), wsrpParameter.getValue(), url);
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
		return false;
	}

	public boolean isWindowStateRequired() {
		return false;
	}

	public void setParameter(String name, String value) {
		parameterMap.put(name, new String[] { value });
		resetToString();
	}

	public void setParameter(String name, String[] values) {
		parameterMap.put(name, values);
		resetToString();
	}

	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}

	public void setParameters(Map<String, String[]> parameters) {
		parameterMap.putAll(parameters);
		resetToString();
	}

	public abstract String getPortletLifecycleId();

	public PortletMode getPortletMode() {
		return null;
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
		return null;
	}
}
