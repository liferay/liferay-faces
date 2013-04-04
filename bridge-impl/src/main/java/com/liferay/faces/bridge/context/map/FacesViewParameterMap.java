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
package com.liferay.faces.bridge.context.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class represents a {@link Map} of URL parameters that are found in the query-string of a Faces view.
 *
 * @author  Neil Griffin
 */
public class FacesViewParameterMap extends HashMap<String, String> {

	// serialVersionUID
	private static final long serialVersionUID = 3213871316191406286L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesViewParameterMap.class);

	// Private Static Data Members
	private static Set<String> excludedParameterNames;

	static {
		excludedParameterNames = new HashSet<String>(3);
		excludedParameterNames.add(Bridge.PORTLET_MODE_PARAMETER);
		excludedParameterNames.add(Bridge.PORTLET_SECURE_PARAMETER);
		excludedParameterNames.add(Bridge.PORTLET_WINDOWSTATE_PARAMETER);
	}

	public FacesViewParameterMap(BridgeContext bridgeContext) {

		String facesViewQueryString = bridgeContext.getFacesViewQueryString();

		if ((facesViewQueryString != null) && (facesViewQueryString.length() > 0)) {

			String[] queryParameters = facesViewQueryString.split(BridgeConstants.REGEX_AMPERSAND_DELIMITER);

			for (String queryParameter : queryParameters) {
				String[] nameValueArray = queryParameter.split("[=]");

				if (nameValueArray != null) {

					if (nameValueArray.length == 2) {

						String parameterName = nameValueArray[0];
						String parameterValue = nameValueArray[1];

						if (excludedParameterNames.contains(parameterName)) {
							logger.debug("Excluding parameterName=[{0}]", parameterName);
						}
						else {
							logger.debug("Adding parameterName=[{0}] parameterValue=[{1}]", parameterName,
								parameterValue);
							put(parameterName, parameterValue);
						}
					}
					else {
						logger.error("Invalid name=value pair=[{0}] in facesViewQueryString=[{1}]", nameValueArray,
							facesViewQueryString);
					}
				}
			}
		}
	}

}
