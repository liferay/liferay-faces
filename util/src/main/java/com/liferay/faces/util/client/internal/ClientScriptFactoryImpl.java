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

package com.liferay.faces.util.client.internal;

import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.client.ClientScriptFactory;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


/**
 * @author  Kyle Stiemann
 */
public class ClientScriptFactoryImpl extends ClientScriptFactory {

	@Override
	public ClientScript getClientScript() throws FacesException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		ClientScript clientScript = (ClientScript) requestMap.get(ClientScriptFactory.class.getName());

		if (clientScript == null) {
			clientScript = new ClientScriptImpl();
			requestMap.put(ClientScriptFactory.class.getName(), clientScript);
		}

		return clientScript;
	}

	// Java 1.6+ @Override
	public ClientScriptFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
