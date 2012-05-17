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

	public ProductMap() {

		// Initialize the map with the known products, except with null values so that they can be retrieved in a lazy
		// manner for better performance.
		put(BridgeConstants.ICEFACES, null);
		put(BridgeConstants.LIFERAY_FACES_ALLOY, null);
		put(BridgeConstants.LIFERAY_FACES_BRIDGE, null);
		put(BridgeConstants.LIFERAY_FACES_PORTAL, null);
		put(BridgeConstants.LIFERAY_PORTAL, null);
		put(BridgeConstants.PRIMEFACES, null);
		put(BridgeConstants.RICHFACES, null);
	}

	@Override
	public Product get(Object key) {

		String name = (String) key;
		Product value = super.get(name);

		if (BridgeConstants.RICHFACES.equals(name)) {
			value = null;
		}
		if (value == null) {

			if (BridgeConstants.ICEFACES.equals(name)) {
				value = new ProductICEfacesImpl();
				put(name, value);
			}
			else if (BridgeConstants.LIFERAY_FACES_ALLOY.equals(name)) {
				value = new ProductLiferayFacesAlloyImpl();
				put(name, value);
			}
			else if (BridgeConstants.LIFERAY_FACES_BRIDGE.equals(name)) {
				value = new ProductLiferayFacesBridgeImpl();
				put(name, value);
			}
			else if (BridgeConstants.LIFERAY_FACES_PORTAL.equals(name)) {
				value = new ProductLiferayFacesPortalImpl();
				put(name, value);
			}
			else if (BridgeConstants.LIFERAY_PORTAL.equals(name)) {
				value = new ProductLiferayPortalImpl();
				put(name, value);
			}
			else if (BridgeConstants.PRIMEFACES.equals(name)) {
				value = new ProductPrimeFacesImpl();
				put(name, value);
			}
			else if (BridgeConstants.RICHFACES.equals(name)) {
				value = new ProductRichFacesImpl();
				put(name, value);
			}
		}

		return value;
	}

}
