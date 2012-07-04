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

import java.util.ArrayList;
import java.util.List;

import javax.portlet.BaseURL;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.util.URLParameter;


/**
 * @author  Neil Griffin
 */
public class ParsedBaseURL {

	// Private Data Members
	private String cacheability;
	private String prefix;
	private String portalAuthToken;
	private String portletAuthToken;
	private String doAsUserIdToken;
	private String doAsUserLanguageIdToken;
	private String doAsGroupIdToken;	
	private String resourceId;
	private List<URLParameter> wsrpParameters;

	public ParsedBaseURL(BaseURL baseURL) {

		String toStringValue = baseURL.toString();
		wsrpParameters = new ArrayList<URLParameter>();

		String queryString = toStringValue;
		int queryPos = toStringValue.indexOf(BridgeConstants.CHAR_QUESTION_MARK);

		if (queryPos > 0) {
			prefix = toStringValue.substring(0, queryPos + 1);
			queryString = toStringValue.substring(queryPos + 1);
		}

		String[] nameValuePairs = queryString.split("[&]");

		if (nameValuePairs != null) {

			for (String nameValuePair : nameValuePairs) {

				if ((cacheability == null) && nameValuePair.startsWith(LiferayConstants.P_P_CACHEABILITY)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						cacheability = nameValuePair.substring(equalsPos + 1);
					}
				}

				else if ((portalAuthToken == null) && nameValuePair.startsWith(LiferayConstants.P_AUTH)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						portalAuthToken = nameValuePair.substring(equalsPos + 1);
					}
				}

				else if ((portletAuthToken == null) && nameValuePair.startsWith(LiferayConstants.P_P_AUTH)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						portletAuthToken = nameValuePair.substring(equalsPos + 1);
					}
				}

				else if (nameValuePair.startsWith(BridgeConstants.WSRP)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						String name = nameValuePair.substring(0, equalsPos);
						String value = nameValuePair.substring(equalsPos + 1);
						URLParameter urlParameter = new URLParameter(name, value);
						wsrpParameters.add(urlParameter);
					}
				}
				
				if ((doAsUserIdToken == null) && nameValuePair.startsWith(LiferayConstants.DO_AS_USER_ID)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						doAsUserIdToken = nameValuePair.substring(equalsPos + 1);
					}
				}
				
				if ((doAsUserLanguageIdToken == null) && nameValuePair.startsWith(LiferayConstants.DO_AS_USER_LANGUAGE_ID)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						doAsUserLanguageIdToken = nameValuePair.substring(equalsPos + 1);
					}
				}
				
				if ((doAsGroupIdToken == null) && nameValuePair.startsWith(LiferayConstants.DO_AS_GROUP_ID)) {
					int equalsPos = nameValuePair.indexOf(BridgeConstants.CHAR_EQUALS);

					if (equalsPos > 0) {
						doAsGroupIdToken = nameValuePair.substring(equalsPos + 1);
					}
				}
			}
		}
	}

	public String getCacheability() {
		return cacheability;
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

	public String getDoAsUserIdToken() {
		return doAsUserIdToken;
	}

	public void setDoAsUserIdToken(String doAsUserIdToken) {
		this.doAsUserIdToken = doAsUserIdToken;
	}

	public String getDoAsUserLanguageIdToken() {
		return doAsUserLanguageIdToken;
	}

	public void setDoAsUserLanguageIdToken(String doAsUserLanguageIdToken) {
		this.doAsUserLanguageIdToken = doAsUserLanguageIdToken;
	}

	public String getDoAsGroupIdToken() {
		return doAsGroupIdToken;
	}

	public void setDoAsGroupIdToken(String doAsGroupIdToken) {
		this.doAsGroupIdToken = doAsGroupIdToken;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getResourceId() {
		return resourceId;
	}

	public List<URLParameter> getWsrpParameters() {
		return wsrpParameters;
	}
}
