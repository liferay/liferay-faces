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

/**
 * @author  Neil Griffin
 */
public interface BridgeExt {

	// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-206

	// NOTE: Changing the value of BRIDGE_CONTEXT_ATTRIBUTE will have a negative impact on the ICEfaces
	// FileEntryPhaseListener#setPortletRequestWrapper(Object) method.
	public static final String BRIDGE_CONTEXT_ATTRIBUTE = "javax.portlet.faces.bridgeContext";

	public static final String FACES_AJAX_PARAMETER = "_jsfBridgeAjax";
}
