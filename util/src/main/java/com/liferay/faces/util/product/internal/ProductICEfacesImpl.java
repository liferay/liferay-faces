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

/**
 * @author  Neil Griffin
 */
public class ProductICEfacesImpl extends ProductBaseImpl {

	public ProductICEfacesImpl() {

		try {
			Class<?> productInfoClass = null;

			try {
				productInfoClass = Class.forName("org.icefaces.application.ProductInfo");
			}
			catch (ClassNotFoundException e) {
				productInfoClass = Class.forName("com.icesoft.faces.application.ProductInfo");
			}

			this.buildId = parseInt((String) productInfoClass.getDeclaredField("REVISION").get(String.class));
			this.majorVersion = parseInt((String) productInfoClass.getDeclaredField("PRIMARY").get(String.class));
			this.minorVersion = parseInt((String) productInfoClass.getDeclaredField("SECONDARY").get(String.class));
			this.revisionVersion = parseInt((String) productInfoClass.getDeclaredField("TERTIARY").get(String.class));
			this.title = (String) productInfoClass.getDeclaredField("PRODUCT").get(String.class);

			StringBuilder buf = new StringBuilder();
			buf.append(this.majorVersion);
			buf.append(".");
			buf.append(this.minorVersion);
			buf.append(".");
			buf.append(this.revisionVersion);
			this.version = buf.toString();

			if (this.majorVersion > 0) {
				this.detected = true;
			}
		}
		catch (Exception e) {
			// Ignore -- ICEfaces is likely not present.
		}
	}
}
