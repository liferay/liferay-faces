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
package com.liferay.faces.bridge.component;

import javax.faces.component.UIParameter;


/**
 * This is a marker class which is used in the {@link BaseURLRenderer} for getting parameters related to the
 * portlet:param tag which is specified in PLT.26.6 of the JSR 286 specification.
 *
 * @author  Neil Griffin
 */

public class PortletParam extends UIParameter {

	public static final String COMPONENT_TYPE = "com.liferay.faces.bridge.component.PortletParam";

	public String getComponentType() {
		return COMPONENT_TYPE;
	}

}
