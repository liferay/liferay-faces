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
public class ProductJSFImpl extends ProductBaseImpl {

	// Private Constants
	private static final String SNAPSHOT = "-SNAPSHOT";

	// Private Data Members
	private boolean mojarra;
	private String toStringValue;

	public ProductJSFImpl() {

		try {
			Class<?> jsfImplClass;

			try {
				this.title = ProductConstants.MOJARRA;
				jsfImplClass = Class.forName("com.sun.faces.RIConstants");
				init(jsfImplClass, ProductConstants.MOJARRA);
				mojarra = true;

			}
			catch (ClassNotFoundException e) {
				this.title = ProductConstants.MYFACES;
				jsfImplClass = Class.forName("org.apache.myfaces.util.ContainerUtils");
				init(jsfImplClass, ProductConstants.MYFACES);
			}
		}
		catch (Exception e) {
			// Ignore -- JSF implementation is likely not present.
		}
	}

	@Override
	public String toString() {

		if (toStringValue == null) {
			toStringValue = super.toString();

			// Some versions of Mojarra are mislabeled "-SNAPSHOT" (i.e.: "1.2_15-20100816-SNAPSHOT")
			if (mojarra && (toStringValue != null)) {
				int pos = toStringValue.indexOf(SNAPSHOT);

				if (pos > 0) {
					toStringValue = toStringValue.substring(0, pos);
				}
			}
		}

		return toStringValue;

	}

}
