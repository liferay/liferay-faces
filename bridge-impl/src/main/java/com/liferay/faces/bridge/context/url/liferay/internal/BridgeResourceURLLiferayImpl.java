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
package com.liferay.faces.bridge.context.url.liferay.internal;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.internal.BridgeResourceURLImpl;
import com.liferay.faces.bridge.internal.BridgeConstants;


/**
 * @author  Neil Griffin
 */
public class BridgeResourceURLLiferayImpl extends BridgeResourceURLImpl {

	// Private Data Members
	private Boolean external;

	public BridgeResourceURLLiferayImpl(BridgeContext bridgeContext, String url, String viewId) {
		super(bridgeContext, url, viewId);
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
