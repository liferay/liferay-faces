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
package com.liferay.faces.alloy.component.progressbar.internal;

import java.util.Collection;
import java.util.Set;

import javax.el.ValueExpression;
import javax.faces.FacesWrapper;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHint;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.BehaviorEvent;


/**
 * @author  Kyle Stiemann
 */
public abstract class AjaxBehaviorWrapper extends AjaxBehavior implements FacesWrapper<AjaxBehavior> {

	@Override
	public void addAjaxBehaviorListener(AjaxBehaviorListener listener) {
		getWrapped().addAjaxBehaviorListener(listener);
	}

	@Override
	public void broadcast(BehaviorEvent event) throws AbortProcessingException {
		getWrapped().broadcast(event);
	}

	@Override
	public void clearInitialState() {
		getWrapped().clearInitialState();
	}

	@Override
	public void decode(FacesContext context, UIComponent component) {
		getWrapped().decode(context, component);
	}

	@Override
	public boolean initialStateMarked() {
		return getWrapped().initialStateMarked();
	}

	@Override
	public void markInitialState() {
		getWrapped().markInitialState();
	}

	@Override
	public void removeAjaxBehaviorListener(AjaxBehaviorListener listener) {
		getWrapped().removeAjaxBehaviorListener(listener);
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		getWrapped().restoreState(context, state);
	}

	@Override
	public Object saveState(FacesContext context) {
		return getWrapped().saveState(context);
	}

	@Override
	public boolean isDisabled() {
		return getWrapped().isDisabled();
	}

//  JSF 2.2+ @Override
//  public String getDelay() {
//      return getWrapped().getDelay();
//  }

//  JSF 2.2+ @Override
//  public void setDelay(String delay) {
//      getWrapped().setDelay(delay);
//  }

	@Override
	public void setDisabled(boolean disabled) {
		getWrapped().setDisabled(disabled);
	}

	@Override
	public boolean isImmediate() {
		return getWrapped().isImmediate();
	}

	@Override
	public Collection<String> getExecute() {
		return getWrapped().getExecute();
	}

	@Override
	public void setExecute(Collection<String> execute) {
		getWrapped().setExecute(execute);
	}

	@Override
	public Set<ClientBehaviorHint> getHints() {
		return getWrapped().getHints();
	}

	@Override
	public void setImmediate(boolean immediate) {
		getWrapped().setImmediate(immediate);
	}

	@Override
	public String getOnerror() {
		return getWrapped().getOnerror();
	}

	@Override
	public void setOnerror(String onerror) {
		getWrapped().setOnerror(onerror);
	}

	@Override
	public String getOnevent() {
		return getWrapped().getOnevent();
	}

	@Override
	public void setOnevent(String onevent) {
		getWrapped().setOnevent(onevent);
	}

	@Override
	public Collection<String> getRender() {
		return getWrapped().getRender();
	}

	@Override
	public void setRender(Collection<String> render) {
		getWrapped().setRender(render);
	}

	@Override
	public String getRendererType() {
		return getWrapped().getRendererType();
	}

//  JSF 2.2+ @Override
//  public void setResetValues(boolean resetValues) {
//      getWrapped().setResetValues(resetValues);
//  }

//  JSF 2.2_ @Override
//  public boolean isResetValues() {
//      return getWrapped().isResetValues();
//  }

	@Override
	public String getScript(ClientBehaviorContext behaviorContext) {
		return getWrapped().getScript(behaviorContext);
	}

	@Override
	public boolean isImmediateSet() {
		return getWrapped().isImmediateSet();
	}

//  JSF 2.2+ @Override
//  public boolean isResetValuesSet() {
//      return getWrapped().isResetValuesSet();
//  }

	@Override
	public boolean isTransient() {
		return getWrapped().isTransient();
	}

	@Override
	public void setTransient(boolean transientFlag) {
		getWrapped().setTransient(transientFlag);
	}

	@Override
	public ValueExpression getValueExpression(String name) {
		return getWrapped().getValueExpression(name);
	}

	@Override
	public void setValueExpression(String name, ValueExpression binding) {
		getWrapped().setValueExpression(name, binding);
	}

	@Override
	public abstract AjaxBehavior getWrapped();
}
