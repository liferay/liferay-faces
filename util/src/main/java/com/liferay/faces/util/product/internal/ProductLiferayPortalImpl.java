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
public class ProductLiferayPortalImpl extends ProductBaseImpl {

	public ProductLiferayPortalImpl() {

		try {
			this.title = ProductConstants.LIFERAY_PORTAL;

			Class<?> releaseInfoClass = Class.forName("com.liferay.portal.kernel.util.ReleaseInfo");
			Class<?>[] emptyClassArray = new Class[] {};
			Object[] emptyObjectArray = new Object[] {};
			Method method = releaseInfoClass.getMethod("getBuildNumber", emptyClassArray);
			this.buildId = (Integer) method.invoke(null, emptyObjectArray);
			method = releaseInfoClass.getMethod("getVersion", emptyClassArray);
			initVersionInfo((String) method.invoke(null, emptyObjectArray));

			if (this.majorVersion > 0) {
				this.detected = true;
			}
		}
		catch (Exception e) {
			// Ignore -- Liferay Portal is likely not present.
		}

	}
}
