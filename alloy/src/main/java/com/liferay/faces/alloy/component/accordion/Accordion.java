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
package com.liferay.faces.alloy.component.accordion;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;

import com.liferay.faces.alloy.component.tab.Tab;
import com.liferay.faces.alloy.component.tab.TabCollapseEvent;
import com.liferay.faces.alloy.component.tab.TabExpandEvent;
import com.liferay.faces.alloy.component.tab.TabUtil;
import com.liferay.faces.util.helper.IntegerHelper;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = Accordion.COMPONENT_TYPE)
public class Accordion extends AccordionBase implements ClientBehaviorHolder {

	// Private Constants
	private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList(
				TabCollapseEvent.TAB_COLLAPSE, TabExpandEvent.TAB_EXPAND));

	@Override
	public void queueEvent(FacesEvent facesEvent) {

		// This method is called by the AjaxBehavior renderer's decode() method. If the specified event is an ajax
		// behavior event that indicates a tab being collapsed/expanded, then
		if (facesEvent instanceof AjaxBehaviorEvent) {

			// Determine the client-side state of the selected tab index.
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
			String clientId = getClientId(facesContext);
			int selectedIndex = IntegerHelper.toInteger(requestParameterMap.get(clientId + "selectedIndex"));

			// If iterating over a data model, then determine the row data and tab associated with the data model
			// iteration.
			Object rowData = null;
			Tab tab = null;
			String var = getVar();

			if (var != null) {
				setRowIndex(selectedIndex);
				rowData = getRowData();
				tab = TabUtil.getFirstChildTab(this);
				setRowIndex(-1);
			}

			// Otherwise, determine the tab associated with the client-side state of the selected tab index.
			else {
				List<Tab> childTabs = TabUtil.getChildTabs(this);

				if (childTabs.size() >= (selectedIndex + 1)) {
					tab = childTabs.get(selectedIndex);
				}
			}

			// Queue an accordion tab event rather than the specified faces event.
			AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) facesEvent;
			Behavior behavior = behaviorEvent.getBehavior();
			String eventName = requestParameterMap.get("javax.faces.behavior.event");

			if (TabCollapseEvent.TAB_COLLAPSE.equals(eventName)) {
				TabCollapseEvent tabCollapseEvent = new TabCollapseEvent(this, behavior, tab, rowData);
				super.queueEvent(tabCollapseEvent);
			}
			else if (TabExpandEvent.TAB_EXPAND.equals(eventName)) {
				TabExpandEvent tabExpandEvent = new TabExpandEvent(this, behavior, tab, rowData);
				super.queueEvent(tabExpandEvent);
			}
		}

		// Otherwise, queue the specified faces event.
		else {
			super.queueEvent(facesEvent);
		}
	}

	@Override
	public String getDefaultEventName() {
		return TabExpandEvent.TAB_EXPAND;
	}

	@Override
	public Collection<String> getEventNames() {
		return EVENT_NAMES;
	}

	@Override
	public String getStyle() {

		String style = super.getStyle();

		// Initially style the outermost <div> with "display:none;" in order to prevent blinking when Alloy's
		// JavaScript attempts to hide the contentBox.
		if (style == null) {
			style = "display:none;";
		}
		else {
			style = style + ";" + "display:none;";
		}

		return style;
	}
}
