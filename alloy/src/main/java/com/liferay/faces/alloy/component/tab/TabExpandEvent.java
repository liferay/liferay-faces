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
package com.liferay.faces.alloy.component.tab;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;


/**
 * @author  Neil Griffin
 */
public class TabExpandEvent extends AjaxBehaviorEvent {

	// Public Constants
	public static final String TAB_EXPAND = "tabExpand";

	// serialVersionUID
	private static final long serialVersionUID = 7530214208373071187L;

	// Private Data Members
	private Object rowData;
	private Tab tab;

	public TabExpandEvent(UIComponent component, Behavior behavior, Tab tab, Object rowData) {
		super(component, behavior);
		this.tab = tab;
		this.rowData = rowData;
		setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
	}

	public Object getRowData() {
		return rowData;
	}

	public Tab getTab() {
		return tab;
	}
}
