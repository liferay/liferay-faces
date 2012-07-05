/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.tck.context;

import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.BridgeContextWrapper;


/**
 * This class is intended to be used as a {@link BridgeContextWrapper} around the default BridgeContext implementation.
 * Its purpose is to enable compatibility between the optimization features of Liferay Faces Bridge and the TCK.
 *
 * @author  Neil Griffin
 */
public class BridgeContextTCKImpl extends BridgeContextWrapper {

	// Private Constants
	private static final String LIFERAY_NAMESPACE_PREFIX_HACK = "A_";

	// Private Data Members
	private BridgeContext wrappedBridgeContext;

	public BridgeContextTCKImpl(BridgeContext bridgeContext) {
		this.wrappedBridgeContext = bridgeContext;
		setCurrentInstance(this);
	}

	@Override
	public boolean isBridgeRequestScopePreserved() {
		return true;
	}

	/**
	 * This method override will enable/disable features of the bridge, so that they do not have to be specified within
	 * the WEB-INF/portlet.xml descriptor.
	 */
	@Override
	public String getInitParameter(String name) {

		String value = super.getInitParameter(name);

		if (BridgeConfigConstants.PARAM_BRIDGE_REQUEST_SCOPE_AJAX_ENABLED.equals(name)) {
			value = Boolean.TRUE.toString();
		}
		else if (BridgeConfigConstants.PARAM_OPTIMIZE_PORTLET_NAMESPACE1.equals(name) ||
				(BridgeConfigConstants.PARAM_OPTIMIZE_PORTLET_NAMESPACE2.equals(name))) {
			value = Boolean.FALSE.toString();
		}
		else if (BridgeConfigConstants.PARAM_MANAGE_INCONGRUITIES.equals(name)) {
			value = Boolean.FALSE.toString();
		}
		else if (BridgeConfigConstants.PARAM_RENDER_REDIRECT_ENABLED.equals(name)) {
			value = Boolean.TRUE.toString();
		}

		return value;
	}

	/**
	 * This method override provides compatibility between TestPage182 (encodeNamespaceTest) and Liferay Portal. For
	 * more information, see: https://issues.apache.org/jira/browse/PORTLETBRIDGE-112
	 */
	@Override
	public String getResponseNamespace() {
		String responseNamespace = super.getResponseNamespace();
		PortletRequest portletRequest = super.getPortletRequest();
		String portletId = (String) portletRequest.getAttribute("PORTLET_ID");

		if ((portletId != null) && (portletId.indexOf("encodeNamespaceTest") > 0)) {

			if (responseNamespace.startsWith(LIFERAY_NAMESPACE_PREFIX_HACK)) {
				responseNamespace = responseNamespace.substring(LIFERAY_NAMESPACE_PREFIX_HACK.length() - 1);
			}
		}

		return responseNamespace;
	}

	@Override
	public BridgeContext getWrapped() {
		return wrappedBridgeContext;
	}

}
