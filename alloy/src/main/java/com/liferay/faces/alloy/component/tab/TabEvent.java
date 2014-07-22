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
package com.liferay.faces.alloy.component.tab;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;

import com.liferay.faces.util.event.NamedAjaxBehaviorEvent;


/**
 * @author  Neil Griffin
 */
public abstract class TabEvent extends NamedAjaxBehaviorEvent {

	// serialVersionUID
	private static final long serialVersionUID = 8882538970972516549L;

	// Private Data Members
	private Object rowData;
	private Tab tab;

	public TabEvent(UIComponent component, Behavior behavior, String name, Tab tab, Object rowData) {
		super(component, behavior, name);
		this.tab = tab;
		this.rowData = rowData;
	}

	public Object getRowData() {
		return rowData;
	}

	public Tab getTab() {
		return tab;
	}
}
