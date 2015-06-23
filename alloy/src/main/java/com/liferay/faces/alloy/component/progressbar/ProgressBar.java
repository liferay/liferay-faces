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
package com.liferay.faces.alloy.component.progressbar;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;



/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@FacesComponent(value = ProgressBar.COMPONENT_TYPE)
public class ProgressBar extends ProgressBarBase implements ClientBehaviorHolder {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.alloy.component.progressbar";

	// Private Constants
	private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList(
				"progressComplete", "poll"));

	@Override
	public void queueEvent(FacesEvent facesEvent) {

		if (facesEvent instanceof AjaxBehaviorEvent) {

			AjaxBehaviorEvent ajaxBehaviorEvent = (AjaxBehaviorEvent) facesEvent;
			UIComponent component = ajaxBehaviorEvent.getComponent();
			Behavior behavior = ajaxBehaviorEvent.getBehavior();
			facesEvent = new ProgressCompleteEvent(component, behavior);
		}

		super.queueEvent(facesEvent);
	}

	@Override
	public String getDefaultEventName() {
		return "poll";
	}

	@Override
	public Collection<String> getEventNames() {
		return EVENT_NAMES;
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
}
