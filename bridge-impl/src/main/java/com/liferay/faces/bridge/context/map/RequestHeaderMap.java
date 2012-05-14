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
package com.liferay.faces.bridge.context.map;

import java.util.Map;
import java.util.Set;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class RequestHeaderMap extends CaseInsensitiveHashMap<String> {

	// serialVersionUID
	private static final long serialVersionUID = 7916822183626170352L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RequestHeaderMap.class);

	public RequestHeaderMap(Map<String, String[]> requestHeaderValuesMap) {
		Set<Map.Entry<String, String[]>> entrySet = requestHeaderValuesMap.entrySet();

		if (entrySet != null) {

			for (Map.Entry<String, String[]> mapEntry : entrySet) {
				String key = mapEntry.getKey();
				String[] value = mapEntry.getValue();

				if ((value != null) && (value.length > 0)) {
					super.put(key, value[0]);
					logger.debug("Adding {0}=[{1}] to header map", key, value);
				}
				else {
					super.put(key, null);
					logger.debug("Adding {0}=[null] to header map", key);
				}
			}
		}
	}

}
