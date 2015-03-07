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

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@FacesComponent(value = ProgressBar.COMPONENT_TYPE)
public class ProgressBar extends ProgressBarBase implements ClientBehaviorHolder {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.alloy.component.progressbar";
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.progressbar.ProgressBar";
	public static final String RENDERER_TYPE =
		"com.liferay.faces.alloy.component.progressbar.internal.ProgressBarRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-progress-bar";

	// Private Constants
	private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList(
				"progressComplete", "poll"));

	public ProgressBar() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public void addClientBehavior(String eventName, ClientBehavior clientBehavior) {

		if (clientBehavior instanceof AjaxBehavior) {
			AjaxBehavior ajaxBehavior = (AjaxBehavior) clientBehavior;
			ajaxBehavior.addAjaxBehaviorListener(new ProgressBarBehaviorListener());
		}

		super.addClientBehavior(eventName, clientBehavior);
	}

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

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(ProgressBarPropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
