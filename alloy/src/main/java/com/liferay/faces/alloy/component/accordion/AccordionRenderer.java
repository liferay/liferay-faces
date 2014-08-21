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
package com.liferay.faces.alloy.component.accordion;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.tab.Tab;
import com.liferay.faces.alloy.component.tab.TabEvent;
import com.liferay.faces.alloy.component.tab.TabUtil;
import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.helper.IntegerHelper;
import com.liferay.faces.util.lang.StringPool;
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
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
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
		String hiddenFieldName = accordion.getClientId(facesContext) + SELECTED_INDEX;
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

				if (child instanceof Tab) {
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
		responseWriter.startElement(StringPool.INPUT, accordion);

		String hiddenFieldName = accordion.getClientId(facesContext) + SELECTED_INDEX;
		responseWriter.writeAttribute(StringPool.ID, hiddenFieldName, null);
		responseWriter.writeAttribute(StringPool.NAME, hiddenFieldName, null);
		responseWriter.writeAttribute(StringPool.TYPE, StringPool.HIDDEN, null);
		responseWriter.writeAttribute(StringPool.VALUE, accordion.getSelectedIndex(), null);
		responseWriter.endElement(StringPool.INPUT);
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// The outermost div was initially styled with "display:none;" in order to prevent blinking when Alloy's
		// JavaScript attempts to hide the div. At this point in JavaScript execution, Alloy is done manipulating the
		// DOM and it is necessary to set the style back to "display:block;" so that the component will be visible.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.write(AlloyRendererUtil.A_DOT_ONE);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);

		String clientId = uiComponent.getClientId(facesContext);
		String escapedClientId = StringPool.POUND + RendererUtil.escapeClientId(clientId);
		responseWriter.write(escapedClientId);
		responseWriter.write("')._node['style'].display='block';");

		// Encode a script that will handle the client-side state of the selected tab index, as well as submitting
		// Ajax requests to support server-side events.
		Accordion accordion = (Accordion) uiComponent;

		// var clientVarName = Liferay.component('clientKey');
		String clientVarName = ComponentUtil.getClientVarName(facesContext, accordion);
		String clientKey = accordion.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		// var clientVar = Liferay.component('clientVarName');
		encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);

		// var toggglers = clientVarName.items;
		responseWriter.write(StringPool.VAR);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write("togglers");
		responseWriter.write(StringPool.EQUAL);
		responseWriter.write(clientVarName);
		responseWriter.write(StringPool.PERIOD);
		responseWriter.write("items");
		responseWriter.write(StringPool.SEMICOLON);

		// var totalTogglers = togglers.length;
		responseWriter.write(StringPool.VAR);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write("totalTogglers");
		responseWriter.write(StringPool.EQUAL);
		responseWriter.write("togglers.length;");

		// for (var i=0; i < totalTogglers ;i++) {
		responseWriter.write("for(var i=0;i<totalTogglers;i++)");
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		// var eventTabIndex = LFAI.getAccordionEventTabIndex(event,'clientKey');
		StringBuffer behaviorCallback = new StringBuffer();
		behaviorCallback.append("var eventTabIndex=LFAI.getAccordionEventTabIndex(event,'");
		behaviorCallback.append(clientKey);
		behaviorCallback.append(StringPool.APOSTROPHE);
		behaviorCallback.append(StringPool.CLOSE_PARENTHESIS);
		behaviorCallback.append(StringPool.SEMICOLON);

		// var hidden = document.getElementById('clientId:selectedIndex');
		String hiddenFieldId = clientId + SELECTED_INDEX;
		behaviorCallback.append("var hidden=document.getElementById('");
		behaviorCallback.append(hiddenFieldId);
		behaviorCallback.append(StringPool.APOSTROPHE);
		behaviorCallback.append(StringPool.CLOSE_PARENTHESIS);
		behaviorCallback.append(StringPool.SEMICOLON);

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
			"if(event.newVal){hidden.value=eventTabIndex;}else if (prevTabIndex==eventTabIndex){hidden.value=''};");

		Map<String, List<ClientBehavior>> clientBehaviorMap = accordion.getClientBehaviors();
		Collection<String> eventNames = accordion.getEventNames();

		for (String eventName : eventNames) {
			List<ClientBehavior> clientBehaviorsForEvent = clientBehaviorMap.get(eventName);

			if (clientBehaviorsForEvent != null) {

				for (ClientBehavior clientBehavior : clientBehaviorsForEvent) {

					ClientBehaviorContext clientBehaviorContext = ClientBehaviorContext.createClientBehaviorContext(
							facesContext, accordion, eventName, clientId, null);
					String clientBehaviorScript = clientBehavior.getScript(clientBehaviorContext);

					// If <f:ajax event="tabExpanded" /> is specified in the view, then render a script that submits
					// an Ajax request.
					if (TabEvent.TAB_EXPANDED.equals(eventName)) {

						//J-
						// if (event.newVal) {
						//	   jsf.ajax.request(this, event, {'javax.faces.behavior.event': 'tabExpanded'});
						// }
						//J+
						behaviorCallback.append("if(event.newVal)");
						behaviorCallback.append(StringPool.OPEN_CURLY_BRACE);
						behaviorCallback.append(clientBehaviorScript);
						behaviorCallback.append(StringPool.CLOSE_CURLY_BRACE);
					}

					// Similarly, if <f:ajax event="tabCollapsed" /> is specified in the view, then render a script
					// that submits an Ajax request.
					else if (TabEvent.TAB_COLLAPSED.equals(eventName)) {

						//J-
						// if ((!event.newVal) && (prevTabIndex == eventTabIndex)) {
						//	   jsf.ajax.request(this, event, {'javax.faces.behavior.event': 'tabCollapsed'});
						// }
						//J+
						behaviorCallback.append("if((!event.newVal)&&(prevTabIndex==eventTabIndex))");
						behaviorCallback.append(StringPool.OPEN_CURLY_BRACE);
						behaviorCallback.append(clientBehaviorScript);
						behaviorCallback.append(StringPool.CLOSE_CURLY_BRACE);
					}
				}
			}
		}

		encodeEventCallback(responseWriter, "togglers[i]", StringPool.AFTER, EXPANDED_CHANGE,
			behaviorCallback.toString());

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the accordion.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, uiComponent.getClientId(facesContext), StringPool.ID);
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing </div> element for the accordion.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.DIV);
	}

	protected void encodeContent(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent,
		Tab tab, boolean selected) throws IOException {

		// Encode the starting <div> element that represents the specified tab's content.
		responseWriter.startElement(StringPool.DIV, tab);

		// Encode the div's class attribute according to the specified tab's collapsed/expanded state.
		String contentClass = CONTENT_COLLAPSED_CLASSES;

		if (selected) {
			contentClass = CONTENT_EXPANDED_CLASSES;
		}

		// If the specified tab has a contentClass, then append it to the class attribute before encoding.
		String tabContentClass = tab.getContentClass();

		if (tabContentClass != null) {
			contentClass += StringPool.SPACE + tabContentClass;
		}

		responseWriter.writeAttribute(StringPool.CLASS, contentClass, Styleable.STYLE_CLASS);

		// Encode the children of the specified tab as the actual content.
		tab.encodeAll(facesContext);

		// Encode the closing </div> element for the specified tab.
		responseWriter.endElement(StringPool.DIV);
	}

	protected void encodeHeader(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent,
		Tab tab, boolean selected) throws IOException {

		// Encode the starting <div> element that represents the specified tab's header.
		responseWriter.startElement(StringPool.DIV, tab);

		// Encode the div's class attribute according to the specified tab's collapsed/expanded state.
		String headerClass = HEADER_COLLAPSED_CLASSES;

		if (selected) {
			headerClass = HEADER_EXPANDED_CLASSES;
		}

		// If the specified tab has a headerClass, then append it to the class attribute before encoding.
		String tabHeaderClass = tab.getHeaderClass();

		if (tabHeaderClass != null) {
			headerClass += StringPool.SPACE + tabHeaderClass;
		}

		responseWriter.writeAttribute(StringPool.CLASS, headerClass, Styleable.STYLE_CLASS);

		// If the header facet exists for the specified tab, then encode the header facet.
		UIComponent headerFacet = tab.getFacet(StringPool.HEADER);

		if (headerFacet != null) {
			headerFacet.encodeAll(facesContext);
		}

		// Otherwise, render the label of the specified tab.
		else {
			String label = (String) tab.getLabel();

			if (label == null) {
				label = StringPool.LABEL;
			}

			responseWriter.write(label);
		}

		// Encode the closing </div> element for the specified tab.
		responseWriter.endElement(StringPool.DIV);
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
		Boolean multiple = accordion.isMultiple();
		encodeBoolean(responseWriter, CLOSE_ALL_ON_EXPAND, !multiple, first);

		// content
		encodeString(responseWriter, StringPool.CONTENT, DOT_CONTENT, first);

		// header
		encodeString(responseWriter, StringPool.HEADER, DOT_HEADER, first);

		// expanded: false
		encodeBoolean(responseWriter, EXPANDED, false, first);
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
