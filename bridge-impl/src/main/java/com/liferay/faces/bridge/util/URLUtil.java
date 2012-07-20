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
package com.liferay.faces.bridge.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class URLUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(URLUtil.class);

	public static Map<String, String[]> parseParameterMapValuesArray(String url) {
		Map<String, String[]> parameterMapValuesArray = new LinkedHashMap<String, String[]>();

		if (url != null) {
			int pos = url.indexOf("?");

			if (pos > 0) {
				String queryString = url.substring(pos + 1);
				queryString = queryString.replaceAll(BridgeConstants.ENCODED_AMERSAND, BridgeConstants.CHAR_AMPERSAND);

				if ((queryString != null) && (queryString.length() > 0)) {

					String[] queryParameters = queryString.split(BridgeConstants.REGEX_AMPERSAND_DELIMITER);

					for (String queryParameter : queryParameters) {
						String[] nameValueArray = queryParameter.split("[=]");

						if (nameValueArray != null) {

							if (nameValueArray.length == 1) {
								String name = nameValueArray[0];
								String value = BridgeConstants.EMPTY;
								parameterMapValuesArray.put(name, new String[] { value });
							}
							else if (nameValueArray.length == 2) {
								String name = nameValueArray[0];
								String value = nameValueArray[1];
								parameterMapValuesArray.put(name, new String[] { value });
							}
							else {
								logger.error("Invalid name=value pair=[{0}] in URL=[{1}]", nameValueArray, url);
							}
						}
					}
				}
			}
		}

		return parameterMapValuesArray;
	}

	public static Map<String, List<String>> parseParameterMapValuesList(String url) {

		Map<String, List<String>> parameterMapValuesList = new LinkedHashMap<String, List<String>>();
		Map<String, String[]> parameterMapValuesArray = parseParameterMapValuesArray(url);
		Set<Entry<String, String[]>> entrySet = parameterMapValuesArray.entrySet();

		for (Map.Entry<String, String[]> mapEntry : entrySet) {
			String[] mapEntryValues = mapEntry.getValue();
			List<String> values = new ArrayList<String>(mapEntryValues.length);

			for (int i = 0; i < mapEntryValues.length; i++) {
				values.add(mapEntryValues[i]);
			}

			parameterMapValuesList.put(mapEntry.getKey(), values);
		}

		return parameterMapValuesList;
	}
}
