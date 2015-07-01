/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.product.internal;

import java.lang.reflect.Method;

import com.liferay.faces.util.product.ProductConstants;


/**
 * @author  Neil Griffin
 */
public class ProductRichFacesImpl extends ProductBaseImpl {

	public ProductRichFacesImpl() {

		try {
			this.title = ProductConstants.RICHFACES;

			try {
				Class<?> versionBeanClass = Class.forName("org.richfaces.VersionBean");
				Object versionObj = versionBeanClass.getDeclaredField("VERSION").get(Object.class);
				Method method = versionObj.getClass().getMethod("getVersion", new Class[] {});
				String version = (String) method.invoke(versionObj, (Object[]) null);

				if (version != null) {
					version = version.replaceFirst("[^0-9]*", "");
					initVersionInfo(version);
				}

				if (this.majorVersion > 0) {
					this.detected = true;
				}
			}
			catch (SecurityException e) {

				// Workaround for https://issues.jboss.org/browse/RF-12805
				Class<?> utilClass = Class.forName("org.richfaces.util.Util");
				init(utilClass, "RichFaces Core Implementation");
				this.title = ProductConstants.RICHFACES;
			}
		}
		catch (Exception e) {
			// Ignore -- RichFaces is likely not present.
		}
	}
}
