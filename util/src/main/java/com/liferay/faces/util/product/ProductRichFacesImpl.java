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
package com.liferay.faces.util.product;

import java.lang.reflect.Method;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class ProductRichFacesImpl extends ProductBaseImpl {

	public ProductRichFacesImpl() {

		try {
			this.title = ProductConstants.RICHFACES;
			Class<?> versionBeanClass = Class.forName("org.richfaces.VersionBean");
			Object versionObj = versionBeanClass.getDeclaredField("VERSION").get(Object.class);
			Method method = versionObj.getClass().getMethod("getVersion", new Class[] {});
			String version = (String) method.invoke(versionObj, (Object[]) null);
			if (version != null) {
				version = version.replaceFirst("[^0-9]*", StringPool.BLANK);
				initVersionInfo(version);
			}
			if (this.majorVersion > 0) {
				this.detected = true;
			}
		}
		catch (Exception e) {
			// Ignore -- RichFaces is likely not present.
		}
	}
}
