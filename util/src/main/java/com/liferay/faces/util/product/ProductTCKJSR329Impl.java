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

/**
 * @author  Neil Griffin
 */
public class ProductTCKJSR329Impl extends ProductBaseImpl {

	public ProductTCKJSR329Impl() {

		try {

			// Note: The JSR 329 TCK was only ever released as version 1.0.0 so there is no specific version detection
			// necessary.
			Class.forName("org.apache.myfaces.portlet.faces.testsuite.annotation.BridgeTest");
			this.buildId = 100;
			this.majorVersion = 1;
			this.minorVersion = 0;
			this.revisionVersion = 0;
			this.title = ProductConstants.TCK_JSR_329;
			this.version = "1.0.0";
			this.detected = true;
		}
		catch (Exception e) {
			// Ignore -- Bridge TCK is likely not present.
		}
	}
}
