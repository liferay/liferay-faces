/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.url.liferay;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeResourceURLImpl;


/**
 * @author  Neil Griffin
 */
public class BridgeResourceURLLiferayImpl extends BridgeResourceURLImpl {

	// Private Data Members
	private Boolean external;

	public BridgeResourceURLLiferayImpl(String url, String currentFacesViewId, BridgeContext bridgeContext) {
		super(url, currentFacesViewId, bridgeContext);
	}

	@Override
	public boolean isExternal() {

		if (external == null) {
			external = super.isExternal();

			if (external) {

				if (url.startsWith(BridgeConstants.WSRP_REWRITE)) {
					external = Boolean.FALSE;
				}
			}
		}

		return external;
	}

}
