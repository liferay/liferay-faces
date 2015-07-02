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

import com.liferay.faces.util.product.ProductConstants;


/**
 * @author  Neil Griffin
 */
public class ProductSpringFrameworkImpl extends ProductBaseImpl {

	public ProductSpringFrameworkImpl() {

		try {
			Class<?> springClass = Class.forName("org.springframework.core.SpringVersion");
			this.title = ProductConstants.SPRING_FRAMEWORK;
			init(springClass, "org.springframework.core");
			this.title = ProductConstants.SPRING_FRAMEWORK;
		}
		catch (Exception e) {
			// Ignore -- Spring Framework is likely not present.
		}
	}
}
