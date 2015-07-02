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
package com.liferay.faces.util.product;

import java.util.HashMap;

import com.liferay.faces.util.product.internal.ProductCDIImpl;
import com.liferay.faces.util.product.internal.ProductICEfacesImpl;
import com.liferay.faces.util.product.internal.ProductJSFImpl;
import com.liferay.faces.util.product.internal.ProductLiferayFacesAlloyImpl;
import com.liferay.faces.util.product.internal.ProductLiferayFacesBridgeImpl;
import com.liferay.faces.util.product.internal.ProductLiferayFacesPortalImpl;
import com.liferay.faces.util.product.internal.ProductLiferayFacesUtilImpl;
import com.liferay.faces.util.product.internal.ProductLiferayPortalImpl;
import com.liferay.faces.util.product.internal.ProductPrimeFacesImpl;
import com.liferay.faces.util.product.internal.ProductResinImpl;
import com.liferay.faces.util.product.internal.ProductRichFacesImpl;
import com.liferay.faces.util.product.internal.ProductSpringFrameworkImpl;
import com.liferay.faces.util.product.internal.ProductWildFlyImpl;


/**
 * @author  Neil Griffin
 */
public class ProductMap extends HashMap<String, Product> {

	// serialVersionUID
	private static final long serialVersionUID = 8267676322108651138L;

	// Singleton Instance
	private static ProductMap instance = new ProductMap();

	static {
		instance.put(ProductConstants.CDI, new ProductCDIImpl());
		instance.put(ProductConstants.ICEFACES, new ProductICEfacesImpl());
		instance.put(ProductConstants.JSF, new ProductJSFImpl());
		instance.put(ProductConstants.LIFERAY_FACES_ALLOY, new ProductLiferayFacesAlloyImpl());
		instance.put(ProductConstants.LIFERAY_FACES_BRIDGE, new ProductLiferayFacesBridgeImpl());
		instance.put(ProductConstants.LIFERAY_FACES_PORTAL, new ProductLiferayFacesPortalImpl());
		instance.put(ProductConstants.LIFERAY_FACES_UTIL, new ProductLiferayFacesUtilImpl());
		instance.put(ProductConstants.LIFERAY_PORTAL, new ProductLiferayPortalImpl());
		instance.put(ProductConstants.PRIMEFACES, new ProductPrimeFacesImpl());
		instance.put(ProductConstants.RESIN, new ProductResinImpl());
		instance.put(ProductConstants.RICHFACES, new ProductRichFacesImpl());
		instance.put(ProductConstants.SPRING_FRAMEWORK, new ProductSpringFrameworkImpl());
		instance.put(ProductConstants.WILDFLY, new ProductWildFlyImpl());
	}

	public static ProductMap getInstance() {
		return instance;
	}

	public static void setInstance(ProductMap productMap) {
		instance = productMap;
	}

}
