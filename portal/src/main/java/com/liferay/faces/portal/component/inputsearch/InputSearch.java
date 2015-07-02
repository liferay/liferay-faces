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
import javax.faces.event.PreRenderComponentEvent;
import javax.faces.render.Renderer;

import com.liferay.faces.util.event.PreRenderComponentEventListener;


/**
 * @author  Juan Gonzalez
 */
@FacesComponent(value = InputSearch.COMPONENT_TYPE)
@ListenersFor({ @ListenerFor(systemEventClass = PreRenderComponentEvent.class) })
public class InputSearch extends InputSearchBase implements ClientBehaviorHolder {

	// Private Data Members
	private String defaultEventName;
	private Collection<String> eventNames;

	public InputSearch() {
		super();

		HtmlCommandButton htmlCommandButton = new HtmlCommandButton();
		this.defaultEventName = htmlCommandButton.getDefaultEventName();
		this.eventNames = htmlCommandButton.getEventNames();
	}

	@Override
	public void processEvent(ComponentSystemEvent componentSystemEvent) throws AbortProcessingException {

		super.processEvent(componentSystemEvent);

		if (componentSystemEvent instanceof PreRenderComponentEvent) {

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
