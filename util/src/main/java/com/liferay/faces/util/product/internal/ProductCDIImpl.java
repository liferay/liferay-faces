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
public class ProductCDIImpl extends ProductBaseImpl {

	public ProductCDIImpl() {

		try {
			Class<?> cdiImplClass;

			try {
				cdiImplClass = Class.forName("org.jboss.weld.util.Types");
				this.title = ProductConstants.WELD;
				init(cdiImplClass, "Weld Servlet (Uber Jar)");

				if (!isDetected()) {
					init(cdiImplClass, "Weld Implementation");
				}

				if (isDetected()) {

					Package pkg = cdiImplClass.getPackage();

					if ((pkg != null) && (pkg.getSpecificationVersion() != null)) {

						// The precise version of Weld is found in the Specification-Version rather than the
						// Implementation-Version in META-INF/MANIFEST.MF
						initVersionInfo(pkg.getSpecificationVersion());
					}
				}
			}
			catch (ClassNotFoundException e) {
				cdiImplClass = Class.forName("org.apache.webbeans.util.WebBeansConstants");
				this.title = ProductConstants.OPEN_WEB_BEANS;
				init(cdiImplClass, "OpenWebBeans Core");
			}
		}
		catch (Exception e) {
			// Ignore -- CDI implementation is likely not present.
		}
	}
}
