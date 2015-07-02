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

import java.io.InputStream;
import java.util.Properties;

import com.liferay.faces.util.product.ProductConstants;


/**
 * @author  Neil Griffin
 */
public class ProductPrimeFacesImpl extends ProductBaseImpl {

	public ProductPrimeFacesImpl() {

		try {

			Class<?> constantsClass = Class.forName("org.primefaces.util.Constants");
			String version = (String) constantsClass.getDeclaredField("VERSION").get(String.class);
			initVersionInfo(version);
			this.buildId = (this.majorVersion * 100) + (this.minorVersion * 10) + this.revisionVersion;
			this.title = ProductConstants.PRIMEFACES;

			if (this.majorVersion > 0) {
				this.detected = true;
			}
		}
		catch (NoSuchFieldException e) {

			try {
				Properties pomProperties = new Properties();
				Class<?> constantsClass = Class.forName("org.primefaces.util.Constants");
				ClassLoader classLoader = constantsClass.getClassLoader();
				InputStream inputStream = classLoader.getResourceAsStream(
						"META-INF/maven/org.primefaces/primefaces/pom.properties");

				if (inputStream != null) {
					pomProperties.load(inputStream);
					inputStream.close();

					version = pomProperties.getProperty("version");
					initVersionInfo(version);
					this.buildId = (this.majorVersion * 100) + (this.minorVersion * 10) + this.revisionVersion;
					this.title = ProductConstants.PRIMEFACES;

					if (this.majorVersion > 0) {
						this.detected = true;
					}
				}
			}
			catch (Exception e2) {
				// Ignore -- PrimeFaces is likely not present.
			}
		}
		catch (Exception e) {
			// Ignore -- PrimeFaces is likely not present.
		}

	}
}
