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
package com.liferay.faces.bridge;

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public interface BridgeExt {

	/**
	 * The name of the {@link PortletRequest} attribute that contains the {@link BridgeContext} instance. NOTE that
	 * changing the value of BRIDGE_CONTEXT_ATTRIBUTE will have a negative impact on the ICEfaces {@link
	 * FileEntryPhaseListener#setPortletRequestWrapper(Object)} method.
	 *
	 * @deprecated  The new technique for acquiring the BridgeContext instance is to call {@link
	 *              BridgeContext#getCurrentInstance()}.
	 */
	@Deprecated
	public static final String BRIDGE_CONTEXT_ATTRIBUTE = "javax.portlet.faces.bridgeContext";

	public static final String FACES_AJAX_PARAMETER = "_jsfBridgeAjax";
}
