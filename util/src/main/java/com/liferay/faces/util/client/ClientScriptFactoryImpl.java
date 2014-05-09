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
package com.liferay.faces.util.client;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;


/**
 * @author  Neil Griffin
 */
public class ClientScriptFactoryImpl extends ClientScriptFactory {

	@Override
	public ClientScript getClientScript(ExternalContext externalContext) throws FacesException {

		// Liferay 5.2 does not have the ability to render AUI ScriptData at the bottom of the portal page.
		return null;
	}

	public ClientScriptFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
