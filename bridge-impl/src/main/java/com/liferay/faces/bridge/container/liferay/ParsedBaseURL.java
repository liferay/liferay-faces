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

import javax.portlet.BaseURL;

import com.liferay.faces.bridge.BridgeConstants;


/**
 * @author  Neil Griffin
 */
public class ParsedBaseURL {

	// Private Data Members
	private String prefix;
	private String portalAuthToken;
	private String portletAuthToken;

	public ParsedBaseURL(BaseURL baseURL) {

		String toStringValue = baseURL.toString();
		String queryString = toStringValue;
		int queryPos = toStringValue.indexOf(BridgeConstants.CHAR_QUESTION_MARK);

		if (queryPos > 0) {
			prefix = toStringValue.substring(0, queryPos + 1);
			queryString = toStringValue.substring(queryPos + 1);
		}

		String[] nameValuePairs = queryString.split("[&]");

		if (nameValuePairs != null) {

			for (String nameValuePair : nameValuePairs) {

				if ((portalAuthToken == null) && nameValuePair.startsWith(LiferayConstants.P_AUTH)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						portalAuthToken = nameValuePair.substring(equalsPos + 1);
					}
				}

				if ((portletAuthToken == null) && nameValuePair.startsWith(LiferayConstants.P_P_AUTH)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						portletAuthToken = nameValuePair.substring(equalsPos + 1);
					}
				}

				if ((portalAuthToken != null) && (portletAuthToken != null)) {
					break;
				}
			}
		}
	}

	public String getPortalAuthToken() {
		return portalAuthToken;
	}

	public void setPortalAuthToken(String portalAuthToken) {
		this.portalAuthToken = portalAuthToken;
	}

	public String getPortletAuthToken() {
		return portletAuthToken;
	}

	public void setPortletAuthToken(String portletAuthToken) {
		this.portletAuthToken = portletAuthToken;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
