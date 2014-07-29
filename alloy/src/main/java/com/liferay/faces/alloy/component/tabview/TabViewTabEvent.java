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
package com.liferay.faces.alloy.component.tabview;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;

import com.liferay.faces.alloy.component.tab.Tab;
import com.liferay.faces.alloy.component.tab.TabEvent;


/**
 * @author  Vernon Singleton
 */
public class TabViewTabEvent extends TabEvent {

	// Public Constants
	public static final String TAB_SELECTED = "tabSelected";

	// serialVersionUID
	private static final long serialVersionUID = 1724270210370353014L;

	public TabViewTabEvent(UIComponent uiComponent, Behavior behavior, String name, Tab tab, Object rowData) {
		super(uiComponent, behavior, name, tab, rowData);
	}
}
