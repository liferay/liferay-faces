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
package com.liferay.faces.bridge.scope;

import javax.faces.FacesException;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeManagerFactoryImpl extends BridgeRequestScopeManagerFactory {

	@Override
	public BridgeRequestScopeManager getBridgeRequestScopeManager() throws FacesException {

		BridgeRequestScopeManager bridgeRequestScopeManager = null;

		if (bridgeRequestScopeManager == null) {
			bridgeRequestScopeManager = new BridgeRequestScopeManagerImpl();
		}

		return bridgeRequestScopeManager;
	}

	public BridgeRequestScopeManagerFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
