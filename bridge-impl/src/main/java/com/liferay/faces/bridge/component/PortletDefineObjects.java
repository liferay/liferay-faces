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

import javax.faces.component.UIComponentBase;


/**
 * This has been implemented for the sake of completeness of portlet:defineObjects tag from JSR 286 specification.
 * Please refer to {@link ELResolverImpl} for more information and section 6.5.1 of the JSR 329 specification.
 *
 * @author  Neil Griffin
 */
public class PortletDefineObjects extends UIComponentBase {

	public static final String COMPONENT_TYPE = "com.liferay.faces.bridge.component.PortletDefineObjects";

	@Override
	public String getFamily() {
		return COMPONENT_TYPE;
	}

}
