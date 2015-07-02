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
package com.liferay.faces.portlet.component.property;

// JSF 2: import javax.faces.component.FacesComponent;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentStateHelper;
import com.liferay.faces.util.component.StateHelper;


/**
 * @author  Neil Griffin
 */
// JSF 2: @FacesComponent(value = Property.COMPONENT_TYPE)
public class Property extends PropertyBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.portlet.component.property";

	// Private Data Members
	private StateHelper stateHelper;

	@Override
	public void restoreState(FacesContext facesContext, Object state) {

		Object[] values = (Object[]) state;
		super.restoreState(facesContext, values[0]);
		getStateHelper().restoreState(facesContext, values[1]);
	}

	@Override
	public Object saveState(FacesContext facesContext) {

		Object[] values = new Object[2];
		values[0] = super.saveState(facesContext);
		values[1] = getStateHelper().saveState(facesContext);

		return values;
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public StateHelper getStateHelper() {

		if (stateHelper == null) {
			stateHelper = new ComponentStateHelper(this);
		}

		return stateHelper;
	}
}
