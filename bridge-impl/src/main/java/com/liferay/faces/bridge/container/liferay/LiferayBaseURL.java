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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.portlet.BaseURL;
import javax.portlet.PortletSecurityException;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


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

	private String responseNamespace;
	private String toStringValue;

	public LiferayBaseURL(ParsedBaseURL parsedLiferayURL, String responseNamespace) {
		this.parsedLiferayURL = parsedLiferayURL;
		this.responseNamespace = responseNamespace;
		this.parameterMap = new HashMap<String, String[]>();
	}

	public void addProperty(String key, String value) {
		// Ignore
	}

	public String encode(String value) {

		String encodedValue = BridgeConstants.EMPTY;

		if (value != null) {

			if (value.length() == 0) {
				encodedValue = " ";
			}
			else {

				try {
					encodedValue = URLEncoder.encode(value, BridgeConstants.UTF8);
				}
				catch (UnsupportedEncodingException e) {
					logger.error(e);
					encodedValue = " ";
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

			// Add request parameters from the request parameter map.
			boolean firstParameter = true;
			Set<Map.Entry<String, String[]>> mapEntries = this.getParameterMap().entrySet();

			if (mapEntries != null) {

				for (Map.Entry<String, String[]> mapEntry : mapEntries) {
					String[] values = mapEntry.getValue();

					if ((values != null) && (values.length > 0)) {
						String parameterName = mapEntry.getKey();
						String parameterValue = values[0];

						if (isValidParameter(parameterValue)) {

							appendParameterToURL(firstParameter, responseNamespace + parameterName,
								encode(parameterValue), url);

							if (firstParameter) {
								firstParameter = false;
							}
						}
					}
				}
			}

			// Possibly add the p_auth parameter.
			String portalAuthToken = parsedLiferayURL.getPortalAuthToken();

			if (portalAuthToken != null) {

				appendParameterToURL(firstParameter, LiferayConstants.P_AUTH, portalAuthToken, url);

				if (firstParameter) {
					firstParameter = false;
				}
			}

			// Possibly add the p_p_auth parameter.
			String portletAuthToken = parsedLiferayURL.getPortletAuthToken();

			if (portletAuthToken != null) {

				appendParameterToURL(firstParameter, LiferayConstants.P_P_AUTH, portletAuthToken, url);

				if (firstParameter) {
					firstParameter = false;
				}
			}

			// Possibly add the p_p_col_count parameter.
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String, Object> applicationMap = facesContext.getExternalContext().getApplicationMap();
			String parameterValue = (String) applicationMap.get(responseNamespace + LiferayConstants.P_P_COL_COUNT);
			appendParameterToURL(firstParameter, LiferayConstants.P_P_COL_COUNT, parameterValue, url);

			if (firstParameter) {
				firstParameter = false;
			}

			// Always add the p_p_col_id parameter
			parameterValue = (String) applicationMap.get(responseNamespace + LiferayConstants.P_P_COL_ID);
			appendParameterToURL(firstParameter, LiferayConstants.P_P_COL_ID, parameterValue, url);

			// Always add the p_p_id parameter
			parameterValue = responseNamespace;

			if (parameterValue.startsWith(BridgeConstants.CHAR_UNDERSCORE)) {
				parameterValue = parameterValue.substring(1);
			}

			if (parameterValue.endsWith(BridgeConstants.CHAR_UNDERSCORE)) {
				parameterValue = parameterValue.substring(0, parameterValue.length() - 1);
			}

			appendParameterToURL(firstParameter, LiferayConstants.P_P_ID, parameterValue, url);

			// Add the p_p_col_pos parameter if it is greater than zero (same logic as Liferay's
			// PortletURLImpl.toString())
			parameterValue = (String) applicationMap.get(responseNamespace + LiferayConstants.P_P_COL_POS);

			if ((parameterValue != null) && (parameterValue.length() > 0)) {

				try {
					int colPos = Integer.parseInt(parameterValue);

					if (colPos > 0) {
						appendParameterToURL(firstParameter, LiferayConstants.P_P_COL_POS, parameterValue, url);
					}
				}
				catch (NumberFormatException e) {
					// ignore
				}
			}

			// Always add the p_p_lifecycle parameter.
			appendParameterToURL(firstParameter, LiferayConstants.P_P_LIFECYCLE, getPortletLifecycleId(), url);

			toStringValue = url.toString();
		}

		return toStringValue;
	}

	public void write(Writer writer) throws IOException {
		writer.write(toString());
	}

	public void write(Writer writer, boolean escapeXML) throws IOException {

		// Although we could write some code to handle the escapeXML flag, we don't support it elsewhere (like in
		// BaseURLRenderer#encodeEnd(FacesContext, UIComponent, BaseURL baseURL)) so won't do it here either.
		writer.write(toString());
	}

	protected void appendParameterToURL(boolean firstParameter, String parameterName, String parameterValue,
		StringBuilder url) {

		if (!firstParameter) {
			url.append(BridgeConstants.CHAR_AMPERSAND);
		}

		url.append(parameterName);
		url.append(BridgeConstants.CHAR_EQUALS);
		url.append(parameterValue);

		logger.debug("Appended param to URL name=[{0}] parameterValue=[{1}]", parameterName, parameterValue);
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
}
