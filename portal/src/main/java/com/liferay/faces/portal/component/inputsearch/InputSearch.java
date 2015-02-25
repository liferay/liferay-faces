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
package com.liferay.faces.portal.component.inputsearch;

import java.util.Collection;

import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.ListenersFor;
import javax.faces.event.PostRestoreStateEvent;
import javax.faces.event.PreRenderComponentEvent;
import javax.faces.render.Renderer;

import com.liferay.faces.util.event.PostRestoreStateEventListener;
import com.liferay.faces.util.event.PreRenderComponentEventListener;


/**
 * @author  Juan Gonzalez
 */
@FacesComponent(value = InputSearch.COMPONENT_TYPE)
@ListenersFor(
	{
		@ListenerFor(systemEventClass = PreRenderComponentEvent.class),
		@ListenerFor(systemEventClass = PostRestoreStateEvent.class)
	}
)
public class InputSearch extends InputSearchBase implements ClientBehaviorHolder {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.inputsearch.InputSearch";
	public static final String RENDERER_TYPE =
		"com.liferay.faces.portal.component.inputsearch.internal.InputSearchRenderer";

	// Private Data Members
	private String defaultEventName;
	private Collection<String> eventNames;

	public InputSearch() {
		super();
		setRendererType(RENDERER_TYPE);

		HtmlCommandButton htmlCommandButton = new HtmlCommandButton();
		this.defaultEventName = htmlCommandButton.getDefaultEventName();
		this.eventNames = htmlCommandButton.getEventNames();
	}

	@Override
	public void processEvent(ComponentSystemEvent componentSystemEvent) throws AbortProcessingException {

		super.processEvent(componentSystemEvent);

		if (componentSystemEvent instanceof PostRestoreStateEvent) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			Renderer renderer = getRenderer(facesContext);

			if (renderer instanceof PostRestoreStateEventListener) {

				PostRestoreStateEventListener postRestoreStateEventListener = (PostRestoreStateEventListener) renderer;
				postRestoreStateEventListener.processEvent((PostRestoreStateEvent) componentSystemEvent);
			}
		}
		else if (componentSystemEvent instanceof PreRenderComponentEvent) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			Renderer renderer = getRenderer(facesContext);

			if (renderer instanceof PreRenderComponentEventListener) {

				PreRenderComponentEventListener preRenderComponentEventListener = (PreRenderComponentEventListener)
					renderer;
				preRenderComponentEventListener.processEvent((PreRenderComponentEvent) componentSystemEvent);
			}
		}
	}

	@Override
	public String getDefaultEventName() {
		return defaultEventName;
	}

	@Override
	public Collection<String> getEventNames() {
		return eventNames;
	}
}
