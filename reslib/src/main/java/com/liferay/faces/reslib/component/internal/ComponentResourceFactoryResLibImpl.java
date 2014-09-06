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
package com.liferay.faces.reslib.component.internal;

import javax.faces.component.UIComponent;

import com.liferay.faces.util.component.ComponentResource;
import com.liferay.faces.util.component.ComponentResourceFactory;


/**
 * @author  Neil Griffin
 */
public class ComponentResourceFactoryResLibImpl extends ComponentResourceFactory {

	// Private Data Members
	private ComponentResourceFactory wrapppedComponentResourceFactory;

	public ComponentResourceFactoryResLibImpl(ComponentResourceFactory componentResourceFactory) {
		this.wrapppedComponentResourceFactory = componentResourceFactory;
	}

	@Override
	public ComponentResource getComponentResource(UIComponent uiComponentResource) {

		ComponentResource wrappedComponentResource = getWrapped().getComponentResource(uiComponentResource);

		return new ComponentResourceResLibImpl(wrappedComponentResource);
	}

	@Override
	public ComponentResourceFactory getWrapped() {
		return wrapppedComponentResourceFactory;
	}
}
