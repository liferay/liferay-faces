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
package com.liferay.faces.alloy.component.button;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.TransientStateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Neil Griffin
 */
public class SplitButton extends SplitButtonCompat {

	// Public Constants
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.button.internal.SplitButtonRenderer";

	// Private Members
	private String rendererType;

	public SplitButton() {
		setRendererType(RENDERER_TYPE);
	}

	public SplitButton(Button button) {
		this();
		this.wrappedButton = button;
	}

	@Override
	public void addClientBehavior(String eventName, ClientBehavior behavior) {
		wrappedButton.addClientBehavior(eventName, behavior);
	}

	@Override
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		wrappedButton.broadcast(event);
	}

	@Override
	public void clearInitialState() {
		wrappedButton.clearInitialState();
	}

	@Override
	public void decode(FacesContext context) {
		wrappedButton.decode(context);
	}

	@Override
	public void encodeAll(FacesContext context) throws IOException {
		wrappedButton.encodeAll(context);
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		wrappedButton.encodeBegin(context);
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		wrappedButton.encodeChildren(context);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		wrappedButton.encodeEnd(context);
	}

	@Override
	public UIComponent findComponent(String expr) {
		return wrappedButton.findComponent(expr);
	}

	@Override
	public boolean initialStateMarked() {
		return wrappedButton.initialStateMarked();
	}

	@Override
	public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback)
		throws FacesException {
		return wrappedButton.invokeOnComponent(context, clientId, callback);
	}

	@Override
	public void markInitialState() {
		wrappedButton.markInitialState();
	}

	@Override
	public void processDecodes(FacesContext context) {
		wrappedButton.processDecodes(context);
	}

	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
		wrappedButton.processEvent(event);
	}

	@Override
	public void processRestoreState(FacesContext context, Object state) {
		wrappedButton.processRestoreState(context, state);
	}

	@Override
	public Object processSaveState(FacesContext context) {
		return wrappedButton.processSaveState(context);
	}

	@Override
	public void processUpdates(FacesContext context) {
		wrappedButton.processUpdates(context);
	}

	@Override
	public void processValidators(FacesContext context) {
		wrappedButton.processValidators(context);
	}

	@Override
	public void queueEvent(FacesEvent event) {
		wrappedButton.queueEvent(event);
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		wrappedButton.restoreState(context, state);
	}

	@Override
	public void restoreTransientState(FacesContext context, Object state) {
		wrappedButton.restoreTransientState(context, state);
	}

	@Override
	public Object saveState(FacesContext context) {
		return wrappedButton.saveState(context);
	}

	@Override
	public Object saveTransientState(FacesContext context) {
		return wrappedButton.saveTransientState(context);
	}

	@Override
	public void subscribeToEvent(Class<? extends SystemEvent> eventClass,
		ComponentSystemEventListener componentListener) {
		wrappedButton.subscribeToEvent(eventClass, componentListener);
	}

	@Override
	public void unsubscribeFromEvent(Class<? extends SystemEvent> eventClass,
		ComponentSystemEventListener componentListener) {
		wrappedButton.unsubscribeFromEvent(eventClass, componentListener);
	}

	@Override
	public boolean visitTree(VisitContext context, VisitCallback callback) {
		return wrappedButton.visitTree(context, callback);
	}

	@Override
	public String getAccesskey() {
		return wrappedButton.getAccesskey();
	}

	@Override
	public void setAccesskey(String accesskey) {
		wrappedButton.setAccesskey(accesskey);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return wrappedButton.getAttributes();
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
		return wrappedButton.getChildCount();
	}

	@Override
	public List<UIComponent> getChildren() {
		return wrappedButton.getChildren();
	}

	@Override
	public Map<String, List<ClientBehavior>> getClientBehaviors() {
		return wrappedButton.getClientBehaviors();
	}

	@Override
	public String getClientId() {
		return wrappedButton.getClientId();
	}

	@Override
	public String getClientId(FacesContext context) {
		return wrappedButton.getClientId();
	}

	public String getClientKey() {
		return null;
	}

	public void setClientKey(String clientKey) {
		// no-op
	}

	@Override
	public String getContainerClientId(FacesContext context) {
		return wrappedButton.getContainerClientId(context);
	}

	@Override
	public Converter getConverter() {
		return wrappedButton.getConverter();
	}

	@Override
	public void setConverter(Converter converter) {
		wrappedButton.setConverter(converter);
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
		return wrappedButton.isDisabled();
	}

	@Override
	public boolean isRendered() {
		return wrappedButton.isRendered();
	}

	@Override
	public String getDefaultEventName() {
		return wrappedButton.getDefaultEventName();
	}

	@Override
	public String getDir() {
		return wrappedButton.getDir();
	}

	@Override
	public void setDir(String dir) {
		wrappedButton.setDir(dir);
	}

	@Override
	public void setDisabled(boolean disabled) {
		wrappedButton.setDisabled(disabled);
	}

	@Override
	public Collection<String> getEventNames() {
		return wrappedButton.getEventNames();
	}

	@Override
	public UIComponent getFacet(String name) {
		return wrappedButton.getFacet(name);
	}

	@Override
	public int getFacetCount() {
		return wrappedButton.getFacetCount();
	}

	@Override
	public Map<String, UIComponent> getFacets() {
		return wrappedButton.getFacets();
	}

	@Override
	public Iterator<UIComponent> getFacetsAndChildren() {
		return wrappedButton.getFacetsAndChildren();
	}

	@Override
	public String getFamily() {
		return wrappedButton.getFamily();
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
		return wrappedButton.getId();
	}

	@Override
	public void setId(String id) {
		wrappedButton.setId(id);
	}

	@Override
	public void setIncludeViewParams(boolean includeViewParams) {
		wrappedButton.setIncludeViewParams(includeViewParams);
	}

	@Override
	public void setInView(boolean isInView) {
		wrappedButton.setInView(isInView);
	}

	@Override
	public String getLang() {
		return wrappedButton.getLang();
	}

	@Override
	public void setLang(String lang) {
		wrappedButton.setLang(lang);
	}

	@Override
	public List<SystemEventListener> getListenersForEventClass(Class<? extends SystemEvent> eventClass) {
		return wrappedButton.getListenersForEventClass(eventClass);
	}

	@Override
	public Object getLocalValue() {
		return wrappedButton.getLocalValue();
	}

	@Override
	public UIComponent getNamingContainer() {
		return wrappedButton.getNamingContainer();
	}

	@Override
	public String getOnblur() {
		return wrappedButton.getOnblur();
	}

	@Override
	public void setOnblur(String onblur) {
		wrappedButton.setOnblur(onblur);
	}

	@Override
	public String getOnclick() {
		return wrappedButton.getOnclick();
	}

	@Override
	public void setOnclick(String onclick) {
		wrappedButton.setOnclick(onclick);
	}

	@Override
	public String getOndblclick() {
		return wrappedButton.getOndblclick();
	}

	@Override
	public void setOndblclick(String ondblclick) {
		wrappedButton.setOndblclick(ondblclick);
	}

	@Override
	public String getOnfocus() {
		return wrappedButton.getOnfocus();
	}

	@Override
	public void setOnfocus(String onfocus) {
		wrappedButton.setOnfocus(onfocus);
	}

	@Override
	public String getOnkeydown() {
		return wrappedButton.getOnkeydown();
	}

	@Override
	public void setOnkeydown(String onkeydown) {
		wrappedButton.setOnkeydown(onkeydown);
	}

	@Override
	public String getOnkeypress() {
		return wrappedButton.getOnkeypress();
	}

	@Override
	public void setOnkeypress(String onkeypress) {
		wrappedButton.setOnkeypress(onkeypress);
	}

	@Override
	public String getOnkeyup() {
		return wrappedButton.getOnkeyup();
	}

	@Override
	public void setOnkeyup(String onkeyup) {
		wrappedButton.setOnkeyup(onkeyup);
	}

	@Override
	public String getOnmousedown() {
		return wrappedButton.getOnmousedown();
	}

	@Override
	public void setOnmousedown(String onmousedown) {
		wrappedButton.setOnmousedown(onmousedown);
	}

	@Override
	public String getOnmousemove() {
		return wrappedButton.getOnmousemove();
	}

	@Override
	public void setOnmousemove(String onmousemove) {
		wrappedButton.setOnmousemove(onmousemove);
	}

	@Override
	public String getOnmouseout() {
		return wrappedButton.getOnmouseout();
	}

	@Override
	public void setOnmouseout(String onmouseout) {
		wrappedButton.setOnmouseout(onmouseout);
	}

	@Override
	public String getOnmouseover() {
		return wrappedButton.getOnmouseover();
	}

	@Override
	public void setOnmouseover(String onmouseover) {
		wrappedButton.setOnmouseover(onmouseover);
	}

	@Override
	public String getOnmouseup() {
		return wrappedButton.getOnmouseup();
	}

	@Override
	public void setOnmouseup(String onmouseup) {
		wrappedButton.setOnmouseup(onmouseup);
	}

	@Override
	public String getOutcome() {
		return wrappedButton.getOutcome();
	}

	@Override
	public void setOutcome(String outcome) {
		wrappedButton.setOutcome(outcome);
	}

	@Override
	public UIComponent getParent() {
		return wrappedButton.getParent();
	}

	@Override
	public void setParent(UIComponent parent) {
		wrappedButton.setParent(parent);
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
		wrappedButton.setRendered(rendered);
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
		return wrappedButton.getRendersChildren();
	}

	@Override
	public Map<String, String> getResourceBundleMap() {
		return wrappedButton.getResourceBundleMap();
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
	public boolean isIncludeViewParams() {
		return wrappedButton.isIncludeViewParams();
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
		return wrappedButton.getStyle();
	}

	@Override
	public void setStyle(String style) {
		wrappedButton.setStyle(style);
	}

	@Override
	public String getStyleClass() {

		String styleClass = wrappedButton.getStyleClass();

		if (styleClass == null) {
			styleClass = "btn-default";
		}

		String defaultButtonClass = null;

		if (!styleClass.contains("btn-")) {
			defaultButtonClass = "btn-default";
		}

		String disabledClass = null;
		boolean disabled = isDisabled();

		if (disabled) {
			disabledClass = "disabled";
		}

		styleClass = ComponentUtil.concatCssClasses("btn", defaultButtonClass, disabledClass, styleClass);

		return styleClass;
	}

	@Override
	public void setStyleClass(String styleClass) {
		wrappedButton.setStyleClass(styleClass);
	}

	@Override
	public boolean isTransient() {
		return wrappedButton.isTransient();
	}

	@Override
	public String getTabindex() {
		return wrappedButton.getTabindex();
	}

	@Override
	public void setTabindex(String tabindex) {
		wrappedButton.setTabindex(tabindex);
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
		return wrappedButton.getTitle();
	}

	@Override
	public void setTitle(String title) {
		wrappedButton.setTitle(title);
	}

	@Override
	public void setTransient(boolean newTransientValue) {
		wrappedButton.setTransient(newTransientValue);
	}

	@Override
	public TransientStateHelper getTransientStateHelper(boolean create) {
		return wrappedButton.getTransientStateHelper(create);
	}

	@Override
	public String getType() {
		return wrappedButton.getType();
	}

	@Override
	public void setType(String type) {
		wrappedButton.setType(type);
	}

	@Override
	public Object getValue() {
		return wrappedButton.getValue();
	}

	@Override
	public void setValue(Object value) {
		wrappedButton.setValue(value);
	}

	@SuppressWarnings("deprecation")
	@Override
	public javax.faces.el.ValueBinding getValueBinding(String name) {
		return wrappedButton.getValueBinding(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setValueBinding(String name, javax.faces.el.ValueBinding binding) {
		wrappedButton.setValueBinding(name, binding);
	}

	@Override
	public ValueExpression getValueExpression(String name) {
		return wrappedButton.getValueExpression(name);
	}

	@Override
	public void setValueExpression(String name, ValueExpression binding) {
		wrappedButton.setValueExpression(name, binding);
	}

	@Override
	public boolean isInView() {
		return wrappedButton.isInView();
	}

	public Button getWrappedButton() {
		return wrappedButton;
	}

	public void setWrappedButton(Button button) {
		this.wrappedButton = button;
	}
}
