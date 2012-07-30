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
package com.liferay.faces.bridge.config;

import java.util.HashMap;

import com.liferay.faces.bridge.BridgeConstants;


/**
 * @author  Neil Griffin
 */
public class ProductMap extends HashMap<String, Product> {

	// serialVersionUID
	private static final long serialVersionUID = 8267676322108651138L;

	// Singleton Instance
	private static ProductMap instance = new ProductMap();

	static {
		instance.put(BridgeConstants.ICEFACES, new ProductICEfacesImpl());
		instance.put(BridgeConstants.JSF, new ProductJSFImpl());
		instance.put(BridgeConstants.LIFERAY_FACES_ALLOY, new ProductLiferayFacesAlloyImpl());
		instance.put(BridgeConstants.LIFERAY_FACES_BRIDGE, new ProductLiferayFacesBridgeImpl());
		instance.put(BridgeConstants.LIFERAY_FACES_PORTAL, new ProductLiferayFacesPortalImpl());
		instance.put(BridgeConstants.LIFERAY_PORTAL, new ProductLiferayPortalImpl());
		instance.put(BridgeConstants.PRIMEFACES, new ProductPrimeFacesImpl());
		instance.put(BridgeConstants.RICHFACES, new ProductRichFacesImpl());
		instance.put(BridgeConstants.TCK_JSR_329, new ProductTCKJSR329Impl());
	}

	public static ProductMap getInstance() {
		return instance;
	}

	public static void setInstance(ProductMap productMap) {
		instance = productMap;
	}

}
