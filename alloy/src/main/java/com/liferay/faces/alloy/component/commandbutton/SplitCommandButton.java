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
package com.liferay.faces.alloy.component.commandbutton;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.TransientStateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionListener;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Vernon Singleton
 */
public class SplitCommandButton extends SplitCommandButtonCompat {

	// Public Constants
	public static final String RENDERER_TYPE =
		"com.liferay.faces.alloy.component.button.internal.SplitCommandButtonRenderer";

	private String rendererType;

	public SplitCommandButton() {
		setRendererType(RENDERER_TYPE);
	}

	public SplitCommandButton(CommandButton commandButton) {
		this();
		this.wrappedCommandButton = commandButton;
	}

	@Override
	public void addActionListener(ActionListener listener) {
		wrappedCommandButton.addActionListener(listener);
	}

	@Override
	public void addClientBehavior(String eventName, ClientBehavior behavior) {
		wrappedCommandButton.addClientBehavior(eventName, behavior);
	}

	@Override
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		wrappedCommandButton.broadcast(event);
	}

	@Override
	public void clearInitialState() {
		wrappedCommandButton.clearInitialState();
	}

	@Override
	public void decode(FacesContext context) {
		wrappedCommandButton.decode(context);
	}

	@Override
	public void encodeAll(FacesContext context) throws IOException {
		wrappedCommandButton.encodeAll(context);
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		wrappedCommandButton.encodeBegin(context);
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		wrappedCommandButton.encodeChildren(context);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		wrappedCommandButton.encodeEnd(context);
	}

	@Override
	public UIComponent findComponent(String expr) {
		return wrappedCommandButton.findComponent(expr);
	}

	@Override
	public boolean initialStateMarked() {
		return wrappedCommandButton.initialStateMarked();
	}

	@Override
	public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback)
		throws FacesException {
		return wrappedCommandButton.invokeOnComponent(context, clientId, callback);
	}

	@Override
	public void markInitialState() {
		wrappedCommandButton.markInitialState();
	}

	@Override
	public void processDecodes(FacesContext context) {
		wrappedCommandButton.processDecodes(context);
	}

	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
		wrappedCommandButton.processEvent(event);
	}

	@Override
	public void processRestoreState(FacesContext context, Object state) {
		wrappedCommandButton.processRestoreState(context, state);
	}

	@Override
	public Object processSaveState(FacesContext context) {
		return wrappedCommandButton.processSaveState(context);
	}

	@Override
	public void processUpdates(FacesContext context) {
		wrappedCommandButton.processUpdates(context);
	}

	@Override
	public void processValidators(FacesContext context) {
		wrappedCommandButton.processValidators(context);
	}

	@Override
	public void queueEvent(FacesEvent event) {
		wrappedCommandButton.queueEvent(event);
	}

	@Override
	public void removeActionListener(ActionListener listener) {
		wrappedCommandButton.removeActionListener(listener);
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		wrappedCommandButton.restoreState(context, state);
	}

	@Override
	public void restoreTransientState(FacesContext context, Object state) {
		wrappedCommandButton.restoreTransientState(context, state);
	}

	@Override
	public Object saveState(FacesContext context) {
		return wrappedCommandButton.saveState(context);
	}

	@Override
	public Object saveTransientState(FacesContext context) {
		return wrappedCommandButton.saveTransientState(context);
	}

	@Override
	public void subscribeToEvent(Class<? extends SystemEvent> eventClass,
		ComponentSystemEventListener componentListener) {
		wrappedCommandButton.subscribeToEvent(eventClass, componentListener);
	}

	@Override
	public void unsubscribeFromEvent(Class<? extends SystemEvent> eventClass,
		ComponentSystemEventListener componentListener) {
		wrappedCommandButton.unsubscribeFromEvent(eventClass, componentListener);
	}

	@Override
	public boolean visitTree(VisitContext context, VisitCallback callback) {
		return wrappedCommandButton.visitTree(context, callback);
	}

	@Override
	public String getAccesskey() {
		return wrappedCommandButton.getAccesskey();
	}

	@Override
	public void setAccesskey(String accesskey) {
		wrappedCommandButton.setAccesskey(accesskey);
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.faces.el.MethodBinding getAction() {
		return wrappedCommandButton.getAction();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setAction(javax.faces.el.MethodBinding action) {
		wrappedCommandButton.setAction(action);
	}

	@Override
	public MethodExpression getActionExpression() {
		return wrappedCommandButton.getActionExpression();
	}

	@Override
	public void setActionExpression(MethodExpression actionExpression) {
		wrappedCommandButton.setActionExpression(actionExpression);
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.faces.el.MethodBinding getActionListener() {
		return wrappedCommandButton.getActionListener();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setActionListener(javax.faces.el.MethodBinding actionListener) {
		wrappedCommandButton.setActionListener(actionListener);
	}

	@Override
	public ActionListener[] getActionListeners() {
		return wrappedCommandButton.getActionListeners();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return wrappedCommandButton.getAttributes();
	}

	@Override
	public String getCharset() {
		return null;
	}

	@Override
	public void setCharset(String charset) {
		// no-op
	}

	@Override
	public int getChildCount() {
		return wrappedCommandButton.getChildCount();
	}

	@Override
	public List<UIComponent> getChildren() {
		return wrappedCommandButton.getChildren();
	}

	@Override
	public Map<String, List<ClientBehavior>> getClientBehaviors() {
		return wrappedCommandButton.getClientBehaviors();
	}

	@Override
	public String getClientId() {
		return wrappedCommandButton.getClientId();
	}

	@Override
	public String getClientId(FacesContext context) {
		return wrappedCommandButton.getClientId();
	}

	public String getClientKey() {
		return null;
	}

	public void setClientKey(String clientKey) {
		// no-op
	}

	@Override
	public String getContainerClientId(FacesContext context) {
		return wrappedCommandButton.getContainerClientId(context);
	}

	@Override
	public String getCoords() {
		return null;
	}

	@Override
	public void setCoords(String coords) {
		// no-op
	}

	@Override
	public boolean isDisabled() {
		return wrappedCommandButton.isDisabled();
	}

	@Override
	public boolean isRendered() {
		return wrappedCommandButton.isRendered();
	}

	@Override
	public String getDefaultEventName() {
		return wrappedCommandButton.getDefaultEventName();
	}

	@Override
	public String getDir() {
		return wrappedCommandButton.getDir();
	}

	@Override
	public void setDir(String dir) {
		wrappedCommandButton.setDir(dir);
	}

	@Override
	public void setDisabled(boolean disabled) {
		wrappedCommandButton.setDisabled(disabled);
	}

	@Override
	public boolean isImmediate() {
		return wrappedCommandButton.isImmediate();
	}

	@Override
	public Collection<String> getEventNames() {
		return wrappedCommandButton.getEventNames();
	}

	@Override
	public UIComponent getFacet(String name) {
		return wrappedCommandButton.getFacet(name);
	}

	@Override
	public int getFacetCount() {
		return wrappedCommandButton.getFacetCount();
	}

	@Override
	public Map<String, UIComponent> getFacets() {
		return wrappedCommandButton.getFacets();
	}

	@Override
	public Iterator<UIComponent> getFacetsAndChildren() {
		return wrappedCommandButton.getFacetsAndChildren();
	}

	@Override
	public String getFamily() {
		return wrappedCommandButton.getFamily();
	}

	@Override
	public String getHreflang() {
		return null;
	}

	@Override
	public void setHreflang(String hreflang) {
		// no-op
	}

	@Override
	public String getId() {
		return wrappedCommandButton.getId();
	}

	@Override
	public void setId(String id) {
		wrappedCommandButton.setId(id);
	}

	@Override
	public void setImmediate(boolean immediate) {
		wrappedCommandButton.setImmediate(immediate);
	}

	@Override
	public void setInView(boolean isInView) {
		wrappedCommandButton.setInView(isInView);
	}

	@Override
	public String getLang() {
		return wrappedCommandButton.getLang();
	}

	@Override
	public void setLang(String lang) {
		wrappedCommandButton.setLang(lang);
	}

	@Override
	public List<SystemEventListener> getListenersForEventClass(Class<? extends SystemEvent> eventClass) {
		return wrappedCommandButton.getListenersForEventClass(eventClass);
	}

	@Override
	public UIComponent getNamingContainer() {
		return wrappedCommandButton.getNamingContainer();
	}

	@Override
	public String getOnblur() {
		return wrappedCommandButton.getOnblur();
	}

	@Override
	public void setOnblur(String onblur) {
		wrappedCommandButton.setOnblur(onblur);
	}

	@Override
	public String getOnclick() {
		return wrappedCommandButton.getOnclick();
	}

	@Override
	public void setOnclick(String onclick) {
		wrappedCommandButton.setOnclick(onclick);
	}

	@Override
	public String getOndblclick() {
		return wrappedCommandButton.getOndblclick();
	}

	@Override
	public void setOndblclick(String ondblclick) {
		wrappedCommandButton.setOndblclick(ondblclick);
	}

	@Override
	public String getOnfocus() {
		return wrappedCommandButton.getOnfocus();
	}

	@Override
	public void setOnfocus(String onfocus) {
		wrappedCommandButton.setOnfocus(onfocus);
	}

	@Override
	public String getOnkeydown() {
		return wrappedCommandButton.getOnkeydown();
	}

	@Override
	public void setOnkeydown(String onkeydown) {
		wrappedCommandButton.setOnkeydown(onkeydown);
	}

	@Override
	public String getOnkeypress() {
		return wrappedCommandButton.getOnkeypress();
	}

	@Override
	public void setOnkeypress(String onkeypress) {
		wrappedCommandButton.setOnkeypress(onkeypress);
	}

	@Override
	public String getOnkeyup() {
		return wrappedCommandButton.getOnkeyup();
	}

	@Override
	public void setOnkeyup(String onkeyup) {
		wrappedCommandButton.setOnkeyup(onkeyup);
	}

	@Override
	public String getOnmousedown() {
		return wrappedCommandButton.getOnmousedown();
	}

	@Override
	public void setOnmousedown(String onmousedown) {
		wrappedCommandButton.setOnmousedown(onmousedown);
	}

	@Override
	public String getOnmousemove() {
		return wrappedCommandButton.getOnmousemove();
	}

	@Override
	public void setOnmousemove(String onmousemove) {
		wrappedCommandButton.setOnmousemove(onmousemove);
	}

	@Override
	public String getOnmouseout() {
		return wrappedCommandButton.getOnmouseout();
	}

	@Override
	public void setOnmouseout(String onmouseout) {
		wrappedCommandButton.setOnmouseout(onmouseout);
	}

	@Override
	public String getOnmouseover() {
		return wrappedCommandButton.getOnmouseover();
	}

	@Override
	public void setOnmouseover(String onmouseover) {
		wrappedCommandButton.setOnmouseover(onmouseover);
	}

	@Override
	public String getOnmouseup() {
		return wrappedCommandButton.getOnmouseup();
	}

	@Override
	public void setOnmouseup(String onmouseup) {
		wrappedCommandButton.setOnmouseup(onmouseup);
	}

	@Override
	public UIComponent getParent() {
		return wrappedCommandButton.getParent();
	}

	@Override
	public void setParent(UIComponent parent) {
		wrappedCommandButton.setParent(parent);
	}

	@Override
	public String getRel() {
		return null;
	}

	@Override
	public void setRel(String rel) {
		// no-op
	}

	@Override
	public void setRendered(boolean rendered) {
		wrappedCommandButton.setRendered(rendered);
	}

	@Override
	public String getRendererType() {
		return rendererType;
	}

	@Override
	public void setRendererType(String rendererType) {
		this.rendererType = rendererType;
	}

	@Override
	public boolean getRendersChildren() {
		return wrappedCommandButton.getRendersChildren();
	}

	@Override
	public Map<String, String> getResourceBundleMap() {
		return wrappedCommandButton.getResourceBundleMap();
	}

	@Override
	public String getRev() {
		return null;
	}

	@Override
	public void setRev(String rev) {
		// no-op
	}

	@Override
	public String getShape() {
		return null;
	}

	@Override
	public void setShape(String shape) {
		// no-op
	}

	@Override
	public String getStyle() {
		return wrappedCommandButton.getStyle();
	}

	@Override
	public void setStyle(String style) {
		wrappedCommandButton.setStyle(style);
	}

	@Override
	public String getStyleClass() {

		String styleClass = wrappedCommandButton.getStyleClass();

		if (styleClass == null) {
			styleClass = "btn-default";
		}

		String defaultCommandButtonClass = null;

		if (!styleClass.contains("btn-")) {
			defaultCommandButtonClass = "btn-default";
		}

		String disabledClass = null;
		boolean disabled = isDisabled();

		if (disabled) {
			disabledClass = "disabled";
		}

		styleClass = ComponentUtil.concatCssClasses("btn", defaultCommandButtonClass, disabledClass, styleClass);

		return styleClass;
	}

	@Override
	public void setStyleClass(String styleClass) {
		wrappedCommandButton.setStyleClass(styleClass);
	}

	@Override
	public boolean isTransient() {
		return wrappedCommandButton.isTransient();
	}

	@Override
	public String getTabindex() {
		return wrappedCommandButton.getTabindex();
	}

	@Override
	public void setTabindex(String tabindex) {
		wrappedCommandButton.setTabindex(tabindex);
	}

	@Override
	public String getTarget() {
		return null;
	}

	@Override
	public void setTarget(String target) {
		// no-op
	}

	@Override
	public String getTitle() {
		return wrappedCommandButton.getTitle();
	}

	@Override
	public void setTitle(String title) {
		wrappedCommandButton.setTitle(title);
	}

	@Override
	public void setTransient(boolean newTransientValue) {
		wrappedCommandButton.setTransient(newTransientValue);
	}

	@Override
	public TransientStateHelper getTransientStateHelper(boolean create) {
		return wrappedCommandButton.getTransientStateHelper(create);
	}

	@Override
	public String getType() {
		return wrappedCommandButton.getType();
	}

	@Override
	public void setType(String type) {
		wrappedCommandButton.setType(type);
	}

	@Override
	public Object getValue() {
		return wrappedCommandButton.getValue();
	}

	@Override
	public void setValue(Object value) {
		wrappedCommandButton.setValue(value);
	}

	@SuppressWarnings("deprecation")
	@Override
	public javax.faces.el.ValueBinding getValueBinding(String name) {
		return wrappedCommandButton.getValueBinding(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setValueBinding(String name, javax.faces.el.ValueBinding binding) {
		wrappedCommandButton.setValueBinding(name, binding);
	}

	@Override
	public ValueExpression getValueExpression(String name) {
		return wrappedCommandButton.getValueExpression(name);
	}

	@Override
	public void setValueExpression(String name, ValueExpression binding) {
		wrappedCommandButton.setValueExpression(name, binding);
	}

	@Override
	public boolean isInView() {
		return wrappedCommandButton.isInView();
	}

	public CommandButton getWrappedCommandButton() {
		return wrappedCommandButton;
	}

	public void setWrappedCommandButton(CommandButton commandButton) {
		this.wrappedCommandButton = commandButton;
	}
}
