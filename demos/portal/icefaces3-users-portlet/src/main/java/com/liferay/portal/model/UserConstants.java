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
package com.liferay.portal.model;

import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.util.StringBundler;


/**
 * This class provides a backport of the {@link #getPortraitURL(String, boolean, long)} method that was introduced in
 * Liferay 6.0.12.
 *
 * @author  Neil Griffin
 */
public class UserConstants {

	public static String getPortraitURL(String imagePath, boolean male, long portraitId) {

		StringBundler sb = new StringBundler(7);

		sb.append(imagePath);
		sb.append("/user_");

		if (male) {
			sb.append("male");
		}
		else {
			sb.append("female");
		}

		sb.append("_portrait?img_id=");
		sb.append(portraitId);
		sb.append("&t=");
		sb.append(ImageServletTokenUtil.getToken(portraitId));

		return sb.toString();
	}
}
