package com.liferay.faces.bridge.container.liferay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.BaseURL;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.util.URLParameter;
import com.liferay.faces.util.lang.StringPool;

public class ParseURLUtil {
	
	public static class ParsedBaseURL {
		private String prefix;
		private Map<String, String> parameterMap;
		private List<URLParameter> wsrpParameters;
		
		public Map<String, String> getParameterMap() {
			return parameterMap;
		}

		public String getPrefix() {
			return prefix;
		}

		public List<URLParameter> getWsrpParameters() {
			return wsrpParameters;
		}
	}
	
	public static class ParsedPortletURL extends ParsedBaseURL {
		private PortletMode portletMode;
		private WindowState windowState;
		
		public ParsedPortletURL(PortletURL portletURL) {
			this.portletMode = portletURL.getPortletMode();
			this.windowState = portletURL.getWindowState();
		}
		
		public PortletMode getPortletMode() {
			return portletMode;
		}

		public WindowState getWindowState() {
			return windowState;
		}
	}

	
	public static <T extends ParsedBaseURL> T parse(Class<T> clazz, BaseURL baseURL) {
		
		ParsedBaseURL url = null;
		
		if (baseURL instanceof PortletURL) {
			url = new ParsedPortletURL((PortletURL)baseURL);
		} else {
			url = new ParsedBaseURL();
		}
		
		String toStringValue = baseURL.toString();
		url.parameterMap = new HashMap<String, String>();
		url.wsrpParameters = new ArrayList<URLParameter>();

		String queryString = StringPool.BLANK;
		int queryPos = toStringValue.indexOf(StringPool.QUESTION);

		if (queryPos > 0) {
			url.prefix = toStringValue.substring(0, queryPos + 1);
			queryString = toStringValue.substring(queryPos + 1);
		} else {
			url.prefix = toStringValue;
		}

		String[] nameValuePairs = queryString.split(BridgeConstants.REGEX_AMPERSAND_DELIMITER);

		if (nameValuePairs != null) {

			for (String nameValuePair : nameValuePairs) {

				int equalsPos = nameValuePair.indexOf(StringPool.EQUAL);

				if (equalsPos > 0) {

					String name = nameValuePair.substring(0, equalsPos);
					String value = nameValuePair.substring(equalsPos + 1);

					if (nameValuePair.startsWith(BridgeConstants.WSRP)) {
						URLParameter urlParameter = new URLParameter(name, value);
						url.wsrpParameters.add(urlParameter);
					}
					else {
						url.parameterMap.put(name, value);
					}
				}
			}
		}
		
		
		@SuppressWarnings("unchecked")
		T result = (T)url;
		return result;
	}
}
