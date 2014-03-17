/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.alloy.component;

import com.liferay.faces.bridge.component.ComponentResource;
import com.liferay.faces.bridge.component.ComponentResourceWrapper;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * This class provides Liferay Faces Alloy with the opportunity to inform Liferay Faces Bridge that certain JavaScript
 * resources should not be rendered when running in Liferay Portal, because they are already present by default in the
 * portal page.
 *
 * @author  Neil Griffin
 */
public class ComponentResourceAlloyImpl extends ComponentResourceWrapper {

	// Private Constants
	private static final String LIFERAY_JS_RESOURCE_ID = "liferay-faces-alloy:liferay.js";
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	// Private Data Members
	private ComponentResource wrappedComponentResource;

	public ComponentResourceAlloyImpl(ComponentResource componentResource) {
		this.wrappedComponentResource = componentResource;
	}

	@Override
	public boolean isRenderable() {

		boolean renderable = super.isRenderable();

		if (LIFERAY_PORTAL_DETECTED) {
			String id = super.getId();

			if (LIFERAY_JS_RESOURCE_ID.equals(id)) {
				renderable = false;
			}
		}

		return renderable;
	}

	@Override
	public ComponentResource getWrapped() {
		return wrappedComponentResource;
	}
}
