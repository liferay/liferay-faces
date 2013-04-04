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
package com.liferay.faces.portal.el;

import java.util.HashMap;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public class PortraitURLMap extends HashMap<Object, String> {

	// serialVersionUID
	private static final long serialVersionUID = 8436757896582374186L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortraitURLMap.class);

	// Private Data Members
	private String liferayImageURL;

	public PortraitURLMap(String liferayImageURL) {
		this.liferayImageURL = liferayImageURL;
	}

	@Override
	public String get(Object key) {

		String url = null;

		if (key != null) {
			url = super.get(key);

			if (url == null) {

				if (key instanceof User) {
					url = getByUser((User) key);
				}
				else if (key instanceof Long) {
					url = getByPortraitId((Long) key);
				}
				else {
					logger.error("Unable to get portrait with object class type [{}]", key.getClass());
				}

				put(key, url);
			}
		}

		return url;
	}

	protected String getByPortraitId(Long portraitId) {
		return liferayImageURL + "/user_portrait?img_id=" + portraitId;
	}

	protected String getByUser(User user) {
		String url = null;

		if (isMale(user)) {
			url = liferayImageURL + "/user_male_portrait?img_id=" + user.getPortraitId();
		}
		else {
			url = liferayImageURL + "/user_female_portrait?img_id=" + user.getPortraitId();
		}

		return url;
	}

	protected boolean isMale(User user) {

		boolean male = true;

		try {
			male = user.isMale();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return male;
	}
}
