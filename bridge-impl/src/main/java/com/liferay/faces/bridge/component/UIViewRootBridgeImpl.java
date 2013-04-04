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
package com.liferay.faces.bridge.component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.portlet.faces.BridgeUtil;
import javax.portlet.faces.component.PortletNamingContainerUIViewRoot;


/**
 * This class overrides the behavior of the {@link PortletNamingContainerUIViewRoot} standard class.
 *
 * @author  Neil Griffin
 */
public class UIViewRootBridgeImpl extends PortletNamingContainerUIViewRoot {

	// serialVersionUID
	private static final long serialVersionUID = 1523062041951774729L;

	/**
	 * <p>This method fixes a problem with {@link UIComponent#findComponent(String)} where sometimes it is unable to
	 * find components due to incorrect clientId values. For more info, see the following issues:
	 *
	 * <ul>
	 *   <li>http://issues.liferay.com/browse/FACES-198</li>
	 *   <li>http://jira.icesoft.org/browse/ICE-6659</li>
	 *   <li>http://jira.icesoft.org/browse/ICE-6667</li>
	 * </ul>
	 * </p>
	 */
	@Override
	public void setId(String id) {

		if (BridgeUtil.isPortletRequest()) {
			super.setId(getContainerClientId(FacesContext.getCurrentInstance()));
		}
	}

}
