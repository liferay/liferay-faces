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
package com.liferay.faces.alloy.component.accordion.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.accordion.Accordion;
import com.liferay.faces.alloy.component.tab.Tab;
import com.liferay.faces.alloy.component.tab.TabCollapseEvent;
import com.liferay.faces.alloy.component.tab.TabExpandEvent;
import com.liferay.faces.alloy.component.tab.TabUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.helper.IntegerHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(componentFamily = Accordion.COMPONENT_FAMILY, rendererType = Accordion.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class AccordionRenderer extends AccordionRendererBase {

	// Private Constants
	private static final String ANIMATED = "animated";
	private static final String CONTAINER = "container";
	private static final String CONTENT_COLLAPSED_CLASSES = "content toggler-content toggler-content-collapsed";
	private static final String CONTENT_EXPANDED_CLASSES = "content toggler-content toggler-content-expanded";
	private static final String DOT_CONTENT = ".content";
	private static final String DOT_HEADER = ".header";
	private static final String HEADER_COLLAPSED_CLASSES = "header toggler-header toggler-header-collapsed";
	private static final String HEADER_EXPANDED_CLASSES = "header toggler-header toggler-header-expanded";
	private static final String EXPANDED = "expanded";
	private static final String EXPANDED_CHANGE = "expandedChange";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AccordionRenderer.class);

	@Override
	public void decodeClientState(FacesContext facesContext, UIComponent uiComponent) {

		// Apply the client-side state of the selected index.
		Accordion accordion = (Accordion) uiComponent;
		String hiddenFieldName = accordion.getClientId(facesContext) + "selectedIndex";
		Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
		String selectedIndex = requestParameterMap.get(hiddenFieldName);

		if (selectedIndex != null) {
			accordion.setSelectedIndex(IntegerHelper.toInteger(selectedIndex, -1));
		}
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// If iteration should take place over a data-model, then
		Accordion accordion = (Accordion) uiComponent;
		Integer selectedIndex = accordion.getSelectedIndex();
		Object value = accordion.getValue();
		String var = accordion.getVar();
		boolean iterateOverDataModel = ((value != null) && (var != null));
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (iterateOverDataModel) {

			// Get the first child tab and use it as a prototype tab.
			Tab prototypeChildTab = TabUtil.getFirstChildTab(accordion);

			if (prototypeChildTab != null) {

				// Encode a header <div> and content <div> for each row in the data-model.
				int rowCount = accordion.getRowCount();

				for (int i = 0; i < rowCount; i++) {
					accordion.setRowIndex(i);

					boolean selected = ((selectedIndex != null) && (i == selectedIndex));
					encodeHeader(facesContext, responseWriter, uiComponent, prototypeChildTab, selected);
					encodeContent(facesContext, responseWriter, uiComponent, prototypeChildTab, selected);
				}

				accordion.setRowIndex(-1);
			}
			else {
				logger.warn("Unable to iterate because alloy:accordion does not have an alloy:tab child element.");
			}
		}

		// Otherwise, encode a header <div> and content <div> for each child tab of the specified accordion.
		else {
			List<UIComponent> children = uiComponent.getChildren();
			int childCount = children.size();

			for (int i = 0; i < childCount; i++) {

				UIComponent child = children.get(i);

				if ((child instanceof Tab) && child.isRendered()) {
					Tab childTab = (Tab) child;
					boolean selected = ((selectedIndex != null) && (i == selectedIndex));
					encodeHeader(facesContext, responseWriter, uiComponent, childTab, selected);
					encodeContent(facesContext, responseWriter, uiComponent, childTab, selected);
				}
				else {
					logger.warn("Unable to render child element of alloy:accordion since it is not alloy:tab");
				}
			}
		}

		accordion.setRowIndex(-1);
	}

	@Override
	public void encodeClientState(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {

		// Encode the hidden field that contains the client-side state of the selected index.
		Accordion accordion = (Accordion) uiComponent;
		responseWriter.startElement("input", accordion);

		String hiddenFieldName = accordion.getClientId(facesContext) + "selectedIndex";
		responseWriter.writeAttribute("id", hiddenFieldName, null);
		responseWriter.writeAttribute("name", hiddenFieldName, null);
		responseWriter.writeAttribute("type", "hidden", null);
		responseWriter.writeAttribute("value", accordion.getSelectedIndex(), null);
		responseWriter.endElement("input");
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// The outermost div was initially styled with "display:none;" in order to prevent blinking when Alloy's
		// JavaScript attempts to hide the div. At this point in JavaScript execution, Alloy is done manipulating the
		// DOM and it is necessary to set the style back to "display:block;" so that the component will be visible.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.write(A_DOT_ONE);
		responseWriter.write("('");

		String clientId = uiComponent.getClientId(facesContext);
		String escapedClientId = "#" + escapeClientId(clientId);
		responseWriter.write(escapedClientId);
		responseWriter.write("')._node['style'].display='block';");

		// Encode a script that will handle the client-side state of the selected tab index, as well as submitting
		// Ajax requests to support server-side events.
		Accordion accordion = (Accordion) uiComponent;

		// var clientVarName = Liferay.component('clientKey');
		String clientVarName = getClientVarName(facesContext, accordion);
		String clientKey = accordion.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		// var clientVar = Liferay.component('clientVarName');
		encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);

		responseWriter.write("var togglers=");
		responseWriter.write(clientVarName);
		responseWriter.write(".items;");

		responseWriter.write("var totalTogglers=togglers.length;");
		
		responseWriter.write("for(var i=0;i<totalTogglers;i++){");

		StringBuilder behaviorCallback = new StringBuilder();

		//J-
		// var eventTabIndex = 0, togglers = j_idt23.items, total = togglers.length, i = 0;
		// for (i = 0; i < total; i++) {
		//	   if (togglers[i] == event.target) {
		//		   eventTabIndex = i;
		//	   }
		// };
		//J+

		behaviorCallback.append("var eventTabIndex=0,togglers=");
		behaviorCallback.append(clientVarName);
		behaviorCallback.append(".items,total=togglers.length,i=0;for(i=0;i<total;i++){if(togglers[i]==event.target){eventTabIndex=i;}};");

		// var hidden = document.getElementById('clientId:selectedIndex');
		String hiddenFieldId = clientId + "selectedIndex";
		behaviorCallback.append("var hidden=document.getElementById('");
		behaviorCallback.append(hiddenFieldId);
		behaviorCallback.append("');");

		// var prevTabIndex = hidden.value;
		behaviorCallback.append("var prevTabIndex=hidden.value;");

		//J-
		// if (event.newVal) {
		//	   hidden.value=eventTabIndex;
		// }
		//	   else if (prevTabIndex==eventTabIndex) {
		//		   hidden.value='';
		//	   }
		// }
		//J+
		behaviorCallback.append(
			"if(event.newVal){hidden.value=eventTabIndex;}else if (prevTabIndex==eventTabIndex){hidden.value='';};");

		Map<String, List<ClientBehavior>> clientBehaviorMap = accordion.getClientBehaviors();
		Collection<String> eventNames = accordion.getEventNames();

		for (String eventName : eventNames) {
			List<ClientBehavior> clientBehaviorsForEvent = clientBehaviorMap.get(eventName);

			if (clientBehaviorsForEvent != null) {

				for (ClientBehavior clientBehavior : clientBehaviorsForEvent) {

					List<ClientBehaviorContext.Parameter> parameters = null;
					UIViewRoot viewRoot = facesContext.getViewRoot();

					if (viewRoot instanceof NamingContainer) {

						String namingContainerId = viewRoot.getContainerClientId(facesContext);
						parameters = new ArrayList<ClientBehaviorContext.Parameter>();
						parameters.add(new ClientBehaviorContext.Parameter("'com.sun.faces.namingContainerId'",
								namingContainerId));
					}

					ClientBehaviorContext clientBehaviorContext = ClientBehaviorContext.createClientBehaviorContext(
							facesContext, accordion, eventName, clientId, parameters);
					String clientBehaviorScript = clientBehavior.getScript(clientBehaviorContext);

					// If <f:ajax event="tabExpanded" /> is specified in the view, then render a script that submits
					// an Ajax request.
					if (TabExpandEvent.TAB_EXPAND.equals(eventName)) {

						//J-
						// if (event.newVal) {
						//	   jsf.ajax.request(this, event, {'javax.faces.behavior.event': 'tabExpand'});
						// }
						//J+
						behaviorCallback.append("if(event.newVal){");
						behaviorCallback.append(clientBehaviorScript);
						behaviorCallback.append("}");
					}

					// Similarly, if <f:ajax event="tabCollapsed" /> is specified in the view, then render a script
					// that submits an Ajax request.
					else if (TabCollapseEvent.TAB_COLLAPSE.equals(eventName)) {

						//J-
						// if ((!event.newVal) && (prevTabIndex == eventTabIndex)) {
						//	   jsf.ajax.request(this, event, {'javax.faces.behavior.event': 'tabCollapse'});
						// }
						//J+
						behaviorCallback.append("if((!event.newVal)&&(prevTabIndex==eventTabIndex)){");
						behaviorCallback.append(clientBehaviorScript);
						behaviorCallback.append("}");
					}
				}
			}
		}

		encodeEventCallback(responseWriter, "togglers[i]", "after", EXPANDED_CHANGE, behaviorCallback.toString());

		responseWriter.write("}");
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the accordion.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("id", uiComponent.getClientId(facesContext), "id");
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing </div> element for the accordion.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
	}

	protected void encodeContent(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent,
		Tab tab, boolean selected) throws IOException {

		// Encode the starting <div> element that represents the specified tab's content.
		responseWriter.startElement("div", tab);

		// Encode the div's class attribute according to the specified tab's collapsed/expanded state.
		String contentClass = CONTENT_COLLAPSED_CLASSES;

		if (selected) {
			contentClass = CONTENT_EXPANDED_CLASSES;
		}

		// If the specified tab has a contentClass, then append it to the class attribute before encoding.
		String tabContentClass = tab.getContentClass();

		if (tabContentClass != null) {
			contentClass += " " + tabContentClass;
		}

		responseWriter.writeAttribute("class", contentClass, Styleable.STYLE_CLASS);

		// Encode the children of the specified tab as the actual content.
		tab.encodeAll(facesContext);

		// Encode the closing </div> element for the specified tab.
		responseWriter.endElement("div");
	}

	protected void encodeHeader(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent,
		Tab tab, boolean selected) throws IOException {

		// Encode the starting <div> element that represents the specified tab's header.
		responseWriter.startElement("div", tab);

		// Encode the div's class attribute according to the specified tab's collapsed/expanded state.
		String headerClass = HEADER_COLLAPSED_CLASSES;

		if (selected) {
			headerClass = HEADER_EXPANDED_CLASSES;
		}

		// If the specified tab has a headerClass, then append it to the class attribute before encoding.
		String tabHeaderClass = tab.getHeaderClass();

		if (tabHeaderClass != null) {
			headerClass += " " + tabHeaderClass;
		}

		responseWriter.writeAttribute("class", headerClass, Styleable.STYLE_CLASS);

		// If the header facet exists for the specified tab, then encode the header facet.
		UIComponent headerFacet = tab.getFacet("header");

		if (headerFacet != null) {
			headerFacet.encodeAll(facesContext);
		}

		// Otherwise, render the label of the specified tab.
		else {
			String headerText = (String) tab.getHeaderText();

			if (headerText != null) {
				responseWriter.write(headerText);
			}
		}

		// Encode the closing </div> element for the specified tab.
		responseWriter.endElement("div");
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, Accordion accordion,
		boolean first) throws IOException {

		// render: true
		encodeWidgetRender(responseWriter, first);

		// container
		first = false;
		encodeClientId(responseWriter, CONTAINER, accordion.getClientId(facesContext), first);

		// animated
		encodeBoolean(responseWriter, ANIMATED, true, first);

		// closeAllOnExpand
		boolean multiple = accordion.isMultiple();
		encodeBoolean(responseWriter, CLOSE_ALL_ON_EXPAND, !multiple, first);

		// content
		encodeString(responseWriter, "content", DOT_CONTENT, first);

		// header
		encodeString(responseWriter, "header", DOT_HEADER, first);

		// expanded: false
		encodeBoolean(responseWriter, EXPANDED, false, first);
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
