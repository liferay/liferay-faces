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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.event.PreRenderComponentEvent;


/** This component would render same markup as Liferay portal's inputSearch tag, but with
 * some JSF features (action, actionListener and AJAX events)
 * 
 * @author  Juan Gonzalez
 */
@FacesComponent(value = InputSearch.COMPONENT_TYPE)
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
public class InputSearch extends InputSearchBase implements ClientBehaviorHolder {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.inputsearch.InputSearch";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.inputsearch.InputSearchRenderer";
	public static final String CHILD_COMPONENTS_ADDED = "childComponentsAdded";

	private Collection<String> eventNames;

	public InputSearch() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
		super.processEvent(event);

		//
		//Before rendering the component we should create some "wrapped" child JSF components.
		//We do this for maintaining the same AJAX (f:ajax)/Non AJAX (action, actionListener) behaviour 
		//In RENDER_PHASE we will move the needed attributes accordingly from the JSF markup to the portal markup
				
		Boolean added = (Boolean) this.getAttributes().get(CHILD_COMPONENTS_ADDED);

		if (!(event instanceof PreRenderComponentEvent) || ((added != null) && added.booleanValue())) {
			return;
		}

		HtmlInputText inputText = createHtmlInputText(getFacesContext());

		HtmlCommandButton button = createHtmlCommandButton(getFacesContext(), getAction(), getActionListener());

		Map<String, List<ClientBehavior>> clientBehaviours = this.getClientBehaviors();

		Set<String> client = clientBehaviours.keySet();

		for (String key : client) {

			List<ClientBehavior> clients = clientBehaviours.get(key);

			if (!key.equals("search")) {

				for (ClientBehavior clientBehavior : clients) {
					inputText.addClientBehavior(key, clientBehavior);
				}
			}
			else {
				// If event is "search" (default) then if would act as button "action"

				for (ClientBehavior clientBehavior : clients) {

					if (clientBehavior instanceof AjaxBehavior) {

						// As the JSF tag is an inputText and the action tag is a button,
						// we have to add the inputText as another element to execute
						AjaxBehavior ajaxBehavior = (AjaxBehavior) clientBehavior;
						String id = this.getId();
						Collection<String> execute = new ArrayList<String>();
						execute.addAll(ajaxBehavior.getExecute());

						if (execute.contains("@this") || !execute.contains(id)) {
							execute.add(id);
							ajaxBehavior.setExecute(execute);
						}

						// As the JSF tag is an inputText and the action tag is a button,
						// we have to add the inputText as another element to render, only
						// in case of @this
						Collection<String> render = new ArrayList<String>();
						render.addAll(ajaxBehavior.getRender());

						if (render.contains("@this")) {
							render.remove("@this");
							render.add(id);
							ajaxBehavior.setRender(render);
						}
					}

					button.addClientBehavior("action", clientBehavior);
				}
			}
		}

		this.getChildren().add(inputText);
		this.getChildren().add(button);
	}

	protected HtmlCommandButton createHtmlCommandButton(FacesContext facesContext, MethodExpression action,
		MethodExpression listener) {
		HtmlCommandButton button = (HtmlCommandButton) facesContext.getApplication().createComponent(
				HtmlCommandButton.COMPONENT_TYPE);

		if (action != null) {
			button.setActionExpression(action);
		}

		if (listener != null) {
			button.addActionListener(new MethodExpressionActionListener(listener));
		}

		return button;
	}

	protected HtmlInputText createHtmlInputText(final FacesContext facesContext) {
		HtmlInputText inputText = (HtmlInputText) facesContext.getApplication().createComponent(
				HtmlInputText.COMPONENT_TYPE);		
		return inputText;
	}

	public String getDefaultEventName() {
		return "search";
	}

	@Override
	public Collection<String> getEventNames() {

		if (eventNames == null) {
			eventNames = new ArrayList<String>();
			eventNames.addAll(new HtmlInputText().getEventNames());
			eventNames.add("search");
			eventNames = Collections.unmodifiableCollection(eventNames);
		}

		return eventNames;
	}
}
