/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portlet.component.defineobjects;

import javax.faces.component.FacesComponent;


/**
 * This has been implemented for the sake of completeness of the portlet: tags from the JSR 286 specification. Please
 * refer to {@link ELResolverImpl} and section 6.5.1 of the JSR 329 specification for more information.
 *
 * @author  Neil Griffin
 */
@FacesComponent(value = DefineObjects.COMPONENT_TYPE)
public class DefineObjects extends DefineObjectsBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.portlet.component.defineobjects";

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
}
