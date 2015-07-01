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
package com.liferay.faces.alloy.component.tabview.internal;

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

import com.liferay.faces.alloy.component.tab.Tab;
import com.liferay.faces.alloy.component.tab.TabSelectEvent;
import com.liferay.faces.alloy.component.tab.TabUtil;
import com.liferay.faces.alloy.component.tabview.TabView;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.helper.IntegerHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.RendererUtil;


/**
 * This class is a JSF {@link javax.faces.render.Renderer} for the aui:tabView component.
 *
 * @author  Neil Griffin
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(componentFamily = TabView.COMPONENT_FAMILY, rendererType = TabView.RENDERER_TYPE)
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
public class TabViewRenderer extends TabViewRendererBase {

	// Private Constants
	private static final String NAV_NAV_TABS = "nav nav-tabs";
	private static final String SELECTED_TAB_HEADER_CLASSES = "tab yui3-widget active tab-selected";
	private static final String SELECTION_CHANGE = "selectionChange";
	private static final String SRC_NODE = "srcNode";
	private static final String TAB_CONTENT = "tab-content";
	private static final String UNSELECTED_TAB_HEADER_CLASSES = "tab yui3-widget";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(TabViewRenderer.class);

	@Override
	public void decodeClientState(FacesContext facesContext, UIComponent uiComponent) {

		// Apply the client-side state of the selected index.
		TabView tabView = (TabView) uiComponent;
		String hiddenFieldName = tabView.getClientId(facesContext) + "selectedIndex";
		Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
		String selectedIndex = requestParameterMap.get(hiddenFieldName);

		if (selectedIndex != null) {
			tabView.setSelectedIndex(IntegerHelper.toInteger(selectedIndex, -1));
		}
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Get the "value" and "var" attributes of the TabView component and determine if iteration should take place
		// using a prototype child tab.
		TabView tabView = (TabView) uiComponent;
		Integer selectedIndex = tabView.getSelectedIndex();
		Object value = tabView.getValue();
		String var = tabView.getVar();
		boolean iterateOverDataModel = ((value != null) && (var != null));
		Tab prototypeChildTab = null;

		if (uiComponent instanceof TabView) {
			prototypeChildTab = TabUtil.getFirstChildTab(tabView);
		}

		// Encode the starting <ul> unordered list element that represents the list of clickable tabs.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("ul", tabView);
		RendererUtil.encodeStyleable(responseWriter, tabView, NAV_NAV_TABS);

		if (iterateOverDataModel) {

			if (prototypeChildTab != null) {

				int rowCount = tabView.getRowCount();

				for (int i = 0; i < rowCount; i++) {
					tabView.setRowIndex(i);

					boolean selected = ((selectedIndex != null) && (i == selectedIndex));
					encodeTabListItem(facesContext, responseWriter, prototypeChildTab, selected);
				}

				tabView.setRowIndex(-1);

			}
			else {
				logger.warn("Unable to iterate because aui:tabView does not have an aui:tab child element.");
			}
		}
		else {
			List<UIComponent> children = uiComponent.getChildren();
			int childCount = children.size();

			for (int i = 0; i < childCount; i++) {
				UIComponent child = children.get(i);

				if ((child instanceof Tab) && child.isRendered()) {
					Tab childTab = (Tab) child;
					boolean selected = ((selectedIndex != null) && (i == selectedIndex));
					encodeTabListItem(facesContext, responseWriter, childTab, selected);
				}
				else {
					logger.warn("Unable to render child element of alloy:tabView since it is not alloy:tab");
				}
			}
		}

		responseWriter.endElement("ul");

		// Encode the starting <div> element that represents the content for the selected tab.
		responseWriter.startElement("div", uiComponent);
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent, TAB_CONTENT);

		// Encode the content for each tab.
		if ((iterateOverDataModel) && (prototypeChildTab != null)) {
			int rowCount = tabView.getRowCount();

			for (int i = 0; i < rowCount; i++) {
				tabView.setRowIndex(i);
				prototypeChildTab.encodeAll(facesContext);
			}
		}
		else {
			List<UIComponent> children = tabView.getChildren();

			for (int i = 0; i < children.size(); i++) {
				UIComponent child = children.get(i);

				if (child.isRendered()) {
					child.encodeAll(facesContext);
				}
			}
		}

		tabView.setRowIndex(-1);

		// Encode the closing </div> element for the content.
		responseWriter.endElement("div");
	}

	@Override
	public void encodeClientState(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {

		// Encode the hidden field that contains the client-side state of the selected index.
		TabView tabView = (TabView) uiComponent;
		responseWriter.startElement("input", tabView);

		String hiddenFieldName = tabView.getClientId(facesContext) + "selectedIndex";
		responseWriter.writeAttribute("id", hiddenFieldName, null);
		responseWriter.writeAttribute("name", hiddenFieldName, null);
		responseWriter.writeAttribute("type", "hidden", null);
		responseWriter.writeAttribute("value", tabView.getSelectedIndex(), null);
		responseWriter.endElement("input");

	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// The outermost div was initially styled with "display:none;" in order to prevent blinking when Alloy's
		// JavaScript attempts to hide the div. At this point in JavaScript execution, Alloy is done manipulating the
		// DOM and it is necessary to set the style back to "display:block;" so that the component will be visible.

		// A.one('#tabViewExample\x5c\x3atabViewForm\x5c\x3aj\x5fidt73')._node['style'].display = 'block';
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.write(A_DOT_ONE);
		responseWriter.write("('");

		String clientId = uiComponent.getClientId(facesContext);
		String escapedClientId = "#" + escapeClientId(clientId);
		responseWriter.write(escapedClientId);
		responseWriter.write("')._node['style'].display='block';");

		// Encode a script that will handle the client-side state of the selected tab index, as well as submitting
		// Ajax requests to support server-side events.
		TabView tabView = (TabView) uiComponent;

		// var clientVarName = Liferay.component('clientKey');
		String clientVarName = getClientVarName(facesContext, tabView);
		String clientKey = tabView.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		// var clientVar = Liferay.component('clientVarName');
		// var tabViewExample_tabViewForm_j_idt73 = Liferay.component('tabViewExample\x5ftabViewForm\x5fj\x5fidt73');
		encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);

		// clientIdselectedIndex
		String hiddenFieldId = clientId + "selectedIndex";

		// tabViewExample_tabViewForm_j_idt73.after('selectionChange', function(event){
		responseWriter.write(clientVarName);
		responseWriter.write(".after('"); // begin call to "after" method
		responseWriter.write(SELECTION_CHANGE);
		responseWriter.write("', function(event){ "); // begin function to call after selectionChange

		// var hidden = document.getElementById('tabViewExample:tabViewForm:j_idt73selectedIndex');
		responseWriter.write("var hidden=document.getElementById('");
		responseWriter.write(hiddenFieldId);
		responseWriter.write("'");
		responseWriter.append(");");

		responseWriter.write(
			"var prevTabIndex=hidden.value;if(event.newVal){hidden.value=event.newVal.get('index');}else if (prevTabIndex==event.newVal.get('index')){hidden.value='';};");

		Map<String, List<ClientBehavior>> clientBehaviorMap = tabView.getClientBehaviors();
		Collection<String> eventNames = tabView.getEventNames();

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
							facesContext, tabView, eventName, clientId, parameters);
					String clientBehaviorScript = clientBehavior.getScript(clientBehaviorContext);

					// If <f:ajax event="tabSelected" /> is specified in the view, then render a script that submits
					// an Ajax request.
					if (TabSelectEvent.TAB_SELECT.equals(eventName)) {

						//J-
						// if (event.newVal) {
						//	   jsf.ajax.request(this, event, {'javax.faces.behavior.event': 'tabExpanded'});
						// }
						//J+
						responseWriter.write("if(event.newVal){");
						responseWriter.write(clientBehaviorScript);
						responseWriter.write("}");
					}
				}
			}
		}

		responseWriter.write("});"); // end function to call after selectionChange + end call to "after" method

		int tabIndex = 0;
		List<UIComponent> children = tabView.getChildren();

		for (UIComponent child : children) {

			if ((child instanceof Tab) && child.isRendered()) {
				Tab childTab = (Tab) child;

				if (childTab.isDisabled()) {
					responseWriter.write(clientVarName);
					responseWriter.write(".disableTab(");
					responseWriter.write(Integer.toString(tabIndex));
					responseWriter.write(");");
				}
				else {
					responseWriter.write(clientVarName);
					responseWriter.write(".enableTab(");
					responseWriter.write(Integer.toString(tabIndex));
					responseWriter.write(");");
				}

				tabIndex++;
			}
		}
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the component.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("id", uiComponent.getClientId(facesContext), "id");
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing </div> element.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, TabView tabView,
		boolean first) throws IOException {

		encodeWidgetRender(responseWriter, first);

		first = false;

		encodeClientId(responseWriter, SRC_NODE, tabView.getClientId(facesContext), first);
	}

	protected void encodeTabListItem(FacesContext facesContext, ResponseWriter responseWriter, Tab tab,
		boolean selected) throws IOException {

		responseWriter.startElement("li", tab);

		// Encode the div's class attribute according to the specified tab's selected/un-selected state.
		String tabClasses = UNSELECTED_TAB_HEADER_CLASSES;

		if (selected) {
			tabClasses = SELECTED_TAB_HEADER_CLASSES;
		}

		// If the specified tab has a headerClass, then append it to the class attribute before encoding.
		String tabHeaderClass = tab.getHeaderClass();

		if (tabHeaderClass != null) {
			tabClasses += " " + tabHeaderClass;
		}

		responseWriter.writeAttribute("class", tabClasses, Styleable.STYLE_CLASS);
		responseWriter.startElement("a", tab);

		if (tab.isDisabled()) {
			responseWriter.writeAttribute("disabled", "disabled", null);
		}

		responseWriter.writeAttribute("href", "#" + tab.getClientId(facesContext), null);

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

		responseWriter.endElement("a");
		responseWriter.endElement("li");
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
