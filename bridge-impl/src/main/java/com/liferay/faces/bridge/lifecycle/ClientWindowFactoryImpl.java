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
package com.liferay.faces.bridge.lifecycle;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.ClientWindow;
import javax.faces.lifecycle.ClientWindowFactory;

import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class ClientWindowFactoryImpl extends ClientWindowFactory {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	// Private Data Members
	private ClientWindowFactory wrappedClientWindowFactory;

	public ClientWindowFactoryImpl(ClientWindowFactory clientWindowFactory) {
		this.wrappedClientWindowFactory = clientWindowFactory;
	}

	@Override
	public ClientWindow getClientWindow(FacesContext facesContext) {

		ClientWindow wrappedClientWindow = wrappedClientWindowFactory.getClientWindow(facesContext);

		if (LIFERAY_PORTAL_DETECTED) {
			return new ClientWindowLiferayImpl(wrappedClientWindow);
		}
		else {
			return wrappedClientWindow;
		}
	}
}
