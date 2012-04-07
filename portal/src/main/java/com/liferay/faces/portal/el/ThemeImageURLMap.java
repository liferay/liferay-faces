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
package com.liferay.faces.portal.el;

import java.util.HashMap;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ThemeImageURLMap extends HashMap<Object, String> {

	// serialVersionUID
	private static final long serialVersionUID = 4884846602753461007L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ThemeImageURLMap.class);

	// Private Data Members
	private String themeImagesURL;

	public ThemeImageURLMap(String themeImagesURL) {
		this.themeImagesURL = themeImagesURL;
	}

	@Override
	public String get(Object key) {

		String url = null;

		if (key != null) {
			url = super.get(key);

			if (url == null) {

				if (key instanceof String) {
					url = themeImagesURL + key;
				}
				else {
					logger.error("Unable to get image with object class type [{}]", key.getClass());
				}

				put(key, url);
			}
		}

		return url;
	}

}
