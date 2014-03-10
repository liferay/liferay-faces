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
package com.liferay.faces.bridge.component;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.ValueChangeListener;
import javax.faces.render.Renderer;
import javax.faces.validator.Validator;


/**
 * @author  Neil Griffin
 */
public abstract class UIInputWrapper extends UIInput implements FacesWrapper<UIInput> {

	// Private Data Members
	private String rendererType;

	@Override
	public void addValidator(Validator validator) {
		getWrapped().addValidator(validator);
	}

	@Override
	public void addValueChangeListener(ValueChangeListener listener) {

		getWrapped().addValueChangeListener(listener);
	}

	@Override
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		getWrapped().broadcast(event);
	}

	@Override
	public void clearInitialState() {
		getWrapped().clearInitialState();
	}

	@Override
	public void decode(FacesContext facesContext) {
		getWrapped().decode(facesContext);
	}

	@Override
	public void encodeBegin(FacesContext facesContext) throws IOException {
		getWrapped().encodeBegin(facesContext);
	}

	@Override
	public void encodeChildren(FacesContext facesContext) throws IOException {
		getWrapped().encodeChildren(facesContext);
	}

	@Override
	public void encodeEnd(FacesContext facesContext) throws IOException {
		getWrapped().encodeEnd(facesContext);
	}

	@Override
	public UIComponent findComponent(String expr) {
		return getWrapped().findComponent(expr);
	}

	@Override
	public void markInitialState() {
		getWrapped().markInitialState();
	}

	@Override
	public void processDecodes(FacesContext facesContext) {
		getWrapped().processDecodes(facesContext);
	}

	@Override
	public void processRestoreState(FacesContext facesContext, Object state) {
		getWrapped().processRestoreState(facesContext, state);
	}

	@Override
	public Object processSaveState(FacesContext facesContext) {
		return getWrapped().processSaveState(facesContext);
	}

	@Override
	public void processUpdates(FacesContext facesContext) {
		getWrapped().processUpdates(facesContext);
	}

	@Override
	public void processValidators(FacesContext facesContext) {

		getWrapped().processValidators(facesContext);
	}

	@Override
	public void queueEvent(FacesEvent event) {
		getWrapped().queueEvent(event);
	}

	@Override
	public void removeValidator(Validator validator) {

		getWrapped().removeValidator(validator);
	}

	@Override
	public void removeValueChangeListener(ValueChangeListener listener) {
		getWrapped().removeValueChangeListener(listener);
	}

	@Override
	public void resetValue() {
		getWrapped().resetValue();
	}

	@Override
	public void restoreState(FacesContext facesContext, Object state) {
		getWrapped().restoreState(facesContext, state);
	}

	@Override
	public Object saveState(FacesContext facesContext) {
		return getWrapped().saveState(facesContext);
	}

	@Override
	public void updateModel(FacesContext facesContext) {
		getWrapped().updateModel(facesContext);
	}

	@Override
	public void validate(FacesContext facesContext) {

		getWrapped().validate(facesContext);
	}

	@Override
	protected void addFacesListener(FacesListener listener) {

		// Unable to call call protected method.
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean compareValues(Object previous, Object value) {

		// Unable to call call protected method.
		throw new UnsupportedOperationException();
	}

	@Override
	protected void removeFacesListener(FacesListener listener) {

		// Unable to call call protected method.
		throw new UnsupportedOperationException();
	}

	@Override
	protected void validateValue(FacesContext facesContext, Object newValue) {

		// Unable to call call protected method.
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return getWrapped().getAttributes();
	}

	@Override
	public int getChildCount() {
		return getWrapped().getChildCount();
	}

	@Override
	public List<UIComponent> getChildren() {
		return getWrapped().getChildren();
	}

	@Override
	public String getClientId(FacesContext facesContext) {
		return getWrapped().getClientId(facesContext);
	}

	@Override
	protected Object getConvertedValue(FacesContext facesContext, Object newSubmittedValue) throws ConverterException {

		// Unable to call call protected method.
		throw new UnsupportedOperationException();
	}

	@Override
	public String getConverterMessage() {
		return getWrapped().getConverterMessage();
	}

	@Override
	public void setConverterMessage(String message) {
		getWrapped().setConverterMessage(message);
	}

	@Override
	public boolean isRendered() {
		return getWrapped().isRendered();
	}

	@Override
	public boolean isRequired() {
		return getWrapped().isRequired();
	}

	@Override
	public boolean isValid() {
		return getWrapped().isValid();
	}

	@Override
	public boolean isImmediate() {
		return getWrapped().isImmediate();
	}

	@Override
	protected FacesContext getFacesContext() {

		// Unable to call call protected method.
		throw new UnsupportedOperationException();
	}

	@Override
	protected FacesListener[] getFacesListeners(@SuppressWarnings("rawtypes") Class clazz) {

		// Unable to call call protected method.
		throw new UnsupportedOperationException();
	}

	@Override
	public UIComponent getFacet(String name) {
		return getWrapped().getFacet(name);
	}

	@Override
	public Map<String, UIComponent> getFacets() {
		return getWrapped().getFacets();
	}

	@Override
	public Iterator<UIComponent> getFacetsAndChildren() {
		return getWrapped().getFacetsAndChildren();
	}

	@Override
	public String getFamily() {
		return getWrapped().getFamily();
	}

	@Override
	public String getId() {
		return getWrapped().getId();
	}

	@Override
	public void setId(String id) {
		getWrapped().setId(id);
	}

	@Override
	public void setImmediate(boolean immediate) {
		getWrapped().setImmediate(immediate);
	}

	@Override
	public void setLocalValueSet(boolean localValueSet) {
		getWrapped().setLocalValueSet(localValueSet);
	}

	@Override
	public UIComponent getParent() {
		return getWrapped().getParent();
	}

	@Override
	public void setParent(UIComponent parent) {
		getWrapped().setParent(parent);
	}

	@Override
	public void setRendered(boolean rendered) {
		getWrapped().setRendered(rendered);
	}

	@Override
	protected Renderer getRenderer(FacesContext facesContext) {

		// Unable to call call protected method.
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRendererType() {

		UIInput wrappedUIInput = getWrapped();

		if (wrappedUIInput != null) {
			return wrappedUIInput.getRendererType();
		}
		else {
			return rendererType;
		}
	}

	@Override
	public void setRendererType(String rendererType) {

		UIInput wrappedUIInput = getWrapped();

		if (wrappedUIInput != null) {
			getWrapped().setRendererType(rendererType);
		}
		else {

			// Note: This method gets called from the UIInput constructor at which time there is not yet a wrapped
			// UIInput.
			this.rendererType = rendererType;
		}
	}

	@Override
	public boolean getRendersChildren() {
		return getWrapped().getRendersChildren();
	}

	@Override
	public void setRequired(boolean required) {
		getWrapped().setRequired(required);
	}

	@Override
	public String getRequiredMessage() {
		return getWrapped().getRequiredMessage();
	}

	@Override
	public void setRequiredMessage(String message) {
		getWrapped().setRequiredMessage(message);
	}

	@Override
	public Object getSubmittedValue() {
		return getWrapped().getSubmittedValue();
	}

	@Override
	public void setSubmittedValue(Object submittedValue) {
		getWrapped().setSubmittedValue(submittedValue);
	}

	@Override
	public boolean isLocalValueSet() {
		return getWrapped().isLocalValueSet();
	}

	@Override
	public boolean isTransient() {
		return getWrapped().isTransient();
	}

	@Override
	public void setTransient(boolean newTransientValue) {
		getWrapped().setTransient(newTransientValue);
	}

	@Override
	public void setValid(boolean valid) {
		getWrapped().setValid(valid);
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.faces.el.MethodBinding getValidator() {
		return getWrapped().getValidator();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setValidator(javax.faces.el.MethodBinding validatorBinding) {
		getWrapped().setValidator(validatorBinding);
	}

	@Override
	public String getValidatorMessage() {
		return getWrapped().getValidatorMessage();
	}

	@Override
	public void setValidatorMessage(String message) {
		getWrapped().setValidatorMessage(message);
	}

	@Override
	public Validator[] getValidators() {

		return getWrapped().getValidators();
	}

	@Override
	public void setValue(Object value) {
		getWrapped().setValue(value);
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.faces.el.ValueBinding getValueBinding(String name) {
		return getWrapped().getValueBinding(name);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setValueBinding(String name, javax.faces.el.ValueBinding binding) {
		getWrapped().setValueBinding(name, binding);
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.faces.el.MethodBinding getValueChangeListener() {
		return getWrapped().getValueChangeListener();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setValueChangeListener(javax.faces.el.MethodBinding valueChangeListener) {
		getWrapped().setValueChangeListener(valueChangeListener);
	}

	@Override
	public ValueChangeListener[] getValueChangeListeners() {

		return getWrapped().getValueChangeListeners();
	}

	public abstract UIInput getWrapped();
}
