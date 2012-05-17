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

import com.liferay.faces.bridge.BridgeConstants;

/**
 * @author  Neil Griffin
 */
public class ProductLiferayFacesAlloyImpl extends ProductBaseImpl {

	public ProductLiferayFacesAlloyImpl() {

		try {
			this.title = BridgeConstants.LIFERAY_FACES_ALLOY;
			Class<?> auiPanelClass = Class.forName("com.liferay.faces.alloy.component.AUIPanel");
			init(auiPanelClass.getPackage());
		}
		catch (Exception e) {
			// Ignore -- ICEfaces is likely not present.
		}
	}
}
