/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.component.icefaces;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;

import com.liferay.faces.util.helper.Wrapper;


/**
 * This class is part of a workaround for <a href="http://jira.icesoft.org/browse/ICE-6398">ICE-6398</a>.
 *
 * @author  Neil Griffin
 */
@SuppressWarnings("deprecation")
public abstract class DataPaginatorWrapper extends DataPaginator implements Wrapper<Object> {

	// Private Data Members
	private String rendererType;

	public void addActionListener(ActionListener listener) {
		((ActionSource) getWrapped()).addActionListener(listener);
	}

	@Override
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		((UIComponentBase) getWrapped()).broadcast(event);
	}

	@Override
	public void decode(FacesContext context) {
		super.decode(context);
	}

	@Override
	public void encodeAll(FacesContext context) throws IOException {
		((UIComponent) getWrapped()).encodeAll(context);
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		((UIComponentBase) getWrapped()).encodeBegin(context);
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		((UIComponentBase) getWrapped()).encodeChildren(context);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		((UIComponentBase) getWrapped()).encodeEnd(context);
	}

	@Override
	public UIComponent findComponent(String expr) {
		return ((UIComponentBase) getWrapped()).findComponent(expr);
	}

	@Override
	public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback)
		throws FacesException {
		return ((UIComponentBase) getWrapped()).invokeOnComponent(context, clientId, callback);
	}

	@Override
	public void processDecodes(FacesContext context) {
		super.processDecodes(context);
	}

	@Override
	public void processRestoreState(FacesContext context, Object state) {
		((UIComponentBase) getWrapped()).processRestoreState(context, state);
	}

	@Override
	public Object processSaveState(FacesContext context) {
		return ((UIComponentBase) getWrapped()).processSaveState(context);
	}

	@Override
	public void processUpdates(FacesContext context) {
		((UIComponentBase) getWrapped()).processUpdates(context);
	}

	@Override
	public void processValidators(FacesContext context) {
		((UIComponentBase) getWrapped()).processValidators(context);
	}

	@Override
	public void queueEvent(FacesEvent event) {
		((UIComponentBase) getWrapped()).queueEvent(event);
	}

	public void removeActionListener(ActionListener listener) {
		((ActionSource) getWrapped()).removeActionListener(listener);
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		((UIComponentBase) getWrapped()).restoreState(context, state);
	}

	@Override
	public Object saveState(FacesContext context) {
		return ((UIComponentBase) getWrapped()).saveState(context);
	}

	public MethodBinding getAction() {
		return ((ActionSource) getWrapped()).getAction();
	}

	public void setAction(MethodBinding action) {
		((ActionSource) getWrapped()).setAction(action);
	}

	public MethodBinding getActionListener() {
		return ((ActionSource) getWrapped()).getActionListener();
	}

	public void setActionListener(MethodBinding actionListener) {
		((ActionSource) getWrapped()).setActionListener(actionListener);
	}

	public ActionListener[] getActionListeners() {
		return ((ActionSource) getWrapped()).getActionListeners();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return ((UIComponentBase) getWrapped()).getAttributes();
	}

	@Override
	public int getChildCount() {
		return ((UIComponentBase) getWrapped()).getChildCount();
	}

	@Override
	public List<UIComponent> getChildren() {
		return ((UIComponentBase) getWrapped()).getChildren();
	}

	@Override
	public String getClientId(FacesContext context) {
		return ((UIComponentBase) getWrapped()).getClientId(context);
	}

	@Override
	public String getContainerClientId(FacesContext context) {
		return ((UIComponent) getWrapped()).getContainerClientId(context);
	}

	@Override
	public boolean isRendered() {
		return ((UIComponentBase) getWrapped()).isRendered();
	}

	public boolean isImmediate() {
		return ((ActionSource) getWrapped()).isImmediate();
	}

	@Override
	public UIComponent getFacet(String name) {
		return ((UIComponentBase) getWrapped()).getFacet(name);
	}

	@Override
	public int getFacetCount() {
		return ((UIComponentBase) getWrapped()).getFacetCount();
	}

	@Override
	public Map<String, UIComponent> getFacets() {
		return ((UIComponentBase) getWrapped()).getFacets();
	}

	@Override
	public Iterator<UIComponent> getFacetsAndChildren() {
		return ((UIComponentBase) getWrapped()).getFacetsAndChildren();
	}

	@Override
	public String getFamily() {
		return ((UIPanel) getWrapped()).getFamily();
	}

	@Override
	public String getId() {
		return ((UIComponentBase) getWrapped()).getId();
	}

	@Override
	public void setId(String id) {
		((UIComponentBase) getWrapped()).setId(id);
	}

	public void setImmediate(boolean immediate) {
		((ActionSource) getWrapped()).setImmediate(immediate);
	}

	@Override
	public String getLayout() {
		return ((HtmlPanelGroup) getWrapped()).getLayout();
	}

	@Override
	public void setLayout(String layout) {
		((HtmlPanelGroup) getWrapped()).setLayout(layout);
	}

	@Override
	public UIComponent getParent() {
		return ((UIComponentBase) getWrapped()).getParent();
	}

	@Override
	public void setParent(UIComponent parent) {
		((UIComponentBase) getWrapped()).setParent(parent);
	}

	@Override
	public void setRendered(boolean rendered) {
		((UIComponentBase) getWrapped()).setRendered(rendered);
	}

	@Override
	public String getRendererType() {
		UIComponentBase wrappedUIComponentBase = (UIComponentBase) getWrapped();

		if (wrappedUIComponentBase != null) {
			return wrappedUIComponentBase.getRendererType();
		}
		else {
			return rendererType;
		}
	}

	@Override
	public void setRendererType(String rendererType) {

		UIComponentBase wrappedUIComponentBase = (UIComponentBase) getWrapped();

		if (wrappedUIComponentBase != null) {
			wrappedUIComponentBase.setRendererType(rendererType);
		}
		else {

			// Note: This method gets called from the UIInput constructor at which time there is not yet a wrapped
			// UIInput.
			this.rendererType = rendererType;
		}
	}

	@Override
	public boolean getRendersChildren() {
		return ((UIComponentBase) getWrapped()).getRendersChildren();
	}

	@Override
	public String getStyle() {
		return ((HtmlPanelGroup) getWrapped()).getStyle();
	}

	@Override
	public void setStyle(String style) {
		((HtmlPanelGroup) getWrapped()).setStyle(style);
	}

	@Override
	public String getStyleClass() {
		return ((HtmlPanelGroup) getWrapped()).getStyleClass();
	}

	@Override
	public void setStyleClass(String styleClass) {
		((HtmlPanelGroup) getWrapped()).setStyleClass(styleClass);
	}

	@Override
	public boolean isTransient() {
		return ((UIComponentBase) getWrapped()).isTransient();
	}

	@Override
	public void setTransient(boolean transientFlag) {
		((UIComponentBase) getWrapped()).setTransient(transientFlag);
	}

	@Override
	public ValueBinding getValueBinding(String name) {
		return ((UIComponentBase) getWrapped()).getValueBinding(name);
	}

	@Override
	public void setValueBinding(String name, ValueBinding binding) {
		((UIComponentBase) getWrapped()).setValueBinding(name, binding);
	}

	@Override
	public ValueExpression getValueExpression(String name) {
		return ((UIComponent) getWrapped()).getValueExpression(name);
	}

	@Override
	public void setValueExpression(String name, ValueExpression binding) {
		((UIComponent) getWrapped()).setValueExpression(name, binding);
	}

	public abstract Object getWrapped();
}
