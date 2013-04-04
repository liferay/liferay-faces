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
package com.liferay.faces.bridge.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.util.lang.StringPool;
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
				queryString = queryString.replaceAll(StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);

				if ((queryString != null) && (queryString.length() > 0)) {

					String[] queryParameters = queryString.split(BridgeConstants.REGEX_AMPERSAND_DELIMITER);

					for (String queryParameter : queryParameters) {
						String[] nameValueArray = queryParameter.split("[=]");

						if (nameValueArray != null) {

							String name = nameValueArray[0];
							String[] existingValues = parameterMapValuesArray.get(name);

							if (nameValueArray.length == 1) {
								String[] newValues = null;

								if (existingValues == null) {
									newValues = new String[] { StringPool.BLANK };
								}
								else {
									newValues = Arrays.copyOf(existingValues, existingValues.length + 1);
									newValues[existingValues.length] = StringPool.BLANK;
								}

								parameterMapValuesArray.put(name, newValues);
							}
							else if (nameValueArray.length == 2) {
								String[] newValues = null;

								if (existingValues == null) {
									newValues = new String[] { nameValueArray[1] };
								}
								else {
									newValues = Arrays.copyOf(existingValues, existingValues.length + 1);
									newValues[existingValues.length] = nameValueArray[1];
								}

								parameterMapValuesArray.put(name, newValues);
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
