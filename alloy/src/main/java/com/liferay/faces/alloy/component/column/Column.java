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
package com.liferay.faces.alloy.component.column;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = Column.COMPONENT_TYPE)
public class Column extends ColumnBase implements ClientBehaviorHolder {

	// Public Constants
	public static final int COLUMNS = 12;

	// Private Constants
	private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("sortBy"));

	@Override
	public String getDefaultEventName() {
		return "sortBy";
	}

	@Override
	public Collection<String> getEventNames() {
		return EVENT_NAMES;
	}

	@Override
	public String getExecute() {

		String execute = super.getExecute();

		if ((execute != null) && execute.contains("@parent")) {

			UIComponent parent = getParent();
			String parentId = parent.getId();
			execute = execute.replace("@parent", parentId);
		}

		return execute;
	}

	@Override
	public String getRender() {

		String render = super.getRender();

		if ((render != null) && render.contains("@parent")) {

			UIComponent parent = getParent();
			String parentId = parent.getId();
			render = render.replace("@parent", parentId);
		}

		return render;
	}

	@Override
	public Integer getSpan() {
		return (Integer) getStateHelper().eval(ColumnPropertyKeys.span, COLUMNS);
	}
}
