/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
		instance.put(ProductConstants.LIFERAY_PORTAL, new ProductLiferayPortalImpl());
		instance.put(ProductConstants.PRIMEFACES, new ProductPrimeFacesImpl());
		instance.put(ProductConstants.RICHFACES, new ProductRichFacesImpl());
	}

	public static ProductMap getInstance() {
		return instance;
	}

	public static void setInstance(ProductMap productMap) {
		instance = productMap;
	}

}
