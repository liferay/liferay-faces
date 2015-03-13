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
package com.liferay.faces.alloy.client.internal;

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.client.ClientScriptFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class ClientScriptFactoryAlloyImpl extends ClientScriptFactory {

	// Private Constants
	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_PORTAL).isDetected();

	// Private Members
	private ClientScriptFactory wrappedClientScriptFactory;

	public ClientScriptFactoryAlloyImpl(ClientScriptFactory wrappedClientScriptFactory) {
		this.wrappedClientScriptFactory = wrappedClientScriptFactory;
	}

	@Override
	public ClientScript getClientScript() throws FacesException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		ClientScript clientScript = (ClientScript) requestMap.get(ClientScriptFactory.class.getName());

		if (clientScript == null) {

			if (LIFERAY_PORTAL_DETECTED && LIFERAY_FACES_BRIDGE_DETECTED) {
				clientScript = wrappedClientScriptFactory.getClientScript();
			}
			else {
				clientScript = new ClientScriptAlloyImpl();
			}

			requestMap.put(ClientScriptFactory.class.getName(), clientScript);
		}

		return clientScript;
	}

	@Override
	public ClientScriptFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return wrappedClientScriptFactory;
	}

}
