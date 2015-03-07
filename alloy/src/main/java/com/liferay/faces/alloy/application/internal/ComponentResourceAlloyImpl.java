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
package com.liferay.faces.alloy.application.internal;

import java.util.HashSet;
import java.util.Set;

import com.liferay.faces.util.application.ComponentResource;
import com.liferay.faces.util.application.ComponentResourceWrapper;
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
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();
	private static final Set<String> PORTAL_RESOURCE_IDS = new HashSet<String>();

	static {
		PORTAL_RESOURCE_IDS.add("liferay-faces-reslib:build/aui/aui-min.js");
		PORTAL_RESOURCE_IDS.add("liferay-faces-reslib:build/aui-css/css/bootstrap.min.css");
		PORTAL_RESOURCE_IDS.add("liferay-faces-reslib:build/aui-css/css/bootstrap-responsive.min.css");
		PORTAL_RESOURCE_IDS.add("liferay-faces-reslib:liferay.js");
	}

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

			if (PORTAL_RESOURCE_IDS.contains(id)) {
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
