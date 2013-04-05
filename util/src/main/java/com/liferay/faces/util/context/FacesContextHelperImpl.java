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
package com.liferay.faces.util.context;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.ViewHandler;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.liferay.faces.util.context.map.JavaScriptMap;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.helper.IntegerHelper;
import com.liferay.faces.util.helper.LongHelper;


/**
 * @author  Neil Griffin
 */
public class FacesContextHelperImpl implements FacesContextHelper, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1336108097295590097L;

	// Private Constants
	private static final String UNEXPECTED_ERROR_MSG_ID = "an-unexpected-error-occurred";
	private static final String SUCCESS_INFO_MSG_ID = "your-request-processed-successfully";

	// Private Data Members
	private transient Map<String, String> javaScriptMap;

	public void addComponentErrorMessage(String clientId, String key) {

		addMessage(clientId, FacesMessage.SEVERITY_ERROR, key);
	}

	public void addComponentErrorMessage(String clientId, String key, Object argument) {

		addMessage(clientId, FacesMessage.SEVERITY_ERROR, key, argument);
	}

	public void addComponentErrorMessage(String clientId, String key, Object... arguments) {

		addMessage(clientId, FacesMessage.SEVERITY_ERROR, key, arguments);
	}

	public void addComponentInfoMessage(String clientId, String key) {

		addMessage(clientId, FacesMessage.SEVERITY_INFO, key);
	}

	public void addComponentInfoMessage(String clientId, String key, Object argument) {

		addMessage(clientId, FacesMessage.SEVERITY_INFO, key, argument);
	}

	public void addComponentInfoMessage(String clientId, String key, Object... arguments) {

		addMessage(clientId, FacesMessage.SEVERITY_INFO, key, arguments);
	}

	public void addGlobalErrorMessage(String key) {
		addComponentErrorMessage(null, key);
	}

	public void addGlobalErrorMessage(String key, Object argument) {

		addComponentErrorMessage(null, key, argument);
	}

	public void addGlobalErrorMessage(String key, Object... arguments) {

		addComponentErrorMessage(null, key, arguments);
	}

	public void addGlobalInfoMessage(String key) {
		addComponentInfoMessage(null, key);
	}

	public void addGlobalInfoMessage(String key, Object argument) {

		addComponentInfoMessage(null, key, argument);
	}

	public void addGlobalInfoMessage(String key, Object... arguments) {

		addComponentInfoMessage(null, key, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalSuccessInfoMessage()
	 */
	public void addGlobalSuccessInfoMessage() {
		addGlobalInfoMessage(SUCCESS_INFO_MSG_ID);
	}

	/**
	 * @see  FacesContextHelper#addGlobalUnexpectedErrorMessage()
	 */
	public void addGlobalUnexpectedErrorMessage() {
		addGlobalErrorMessage(UNEXPECTED_ERROR_MSG_ID);
	}

	public void addMessage(String clientId, Severity severity, String key) {

		Locale locale = getLocale();
		FacesMessage facesMessage = MessageContextImpl.getInstance().newFacesMessage(locale, severity, key);
		FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
	}

	public void addMessage(String clientId, Severity severity, String key, Object argument) {

		Locale locale = getLocale();
		FacesMessage facesMessage = MessageContextImpl.getInstance().newFacesMessage(locale, severity, key, argument);
		FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
	}

	public void addMessage(String clientId, Severity severity, String key, Object... arguments) {

		Locale locale = getLocale();
		FacesMessage facesMessage = MessageContextImpl.getInstance().newFacesMessage(locale, severity, key, arguments);
		FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
	}

	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		UIComponent uiComponent = null;

		if (parent != null) {

			String parentClientId = parent.getClientId(getFacesContext());

			if ((parentClientId != null) && (parentClientId.indexOf(partialClientId) >= 0)) {
				uiComponent = parent;
			}
			else {
				Iterator<UIComponent> itr = parent.getFacetsAndChildren();

				if (itr != null) {

					while (itr.hasNext()) {
						UIComponent child = itr.next();
						uiComponent = matchComponentInHierarchy(child, partialClientId);

						if (uiComponent != null) {
							break;
						}
					}
				}
			}
		}

		return uiComponent;
	}

	public UIComponent matchComponentInViewRoot(String partialClientId) {
		return matchComponentInHierarchy(FacesContext.getCurrentInstance().getViewRoot(), partialClientId);
	}

	public void navigate(String fromAction, String outcome) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, fromAction, outcome);
	}

	public void navigateTo(String outcome) {
		navigate(null, outcome);
	}

	public void recreateComponentTree() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(facesContext, facesContext.getViewRoot().getViewId());
		facesContext.setViewRoot(viewRoot);
		facesContext.renderResponse();
	}

	public void registerPhaseListener(PhaseListener phaseListener) throws IllegalStateException {
		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(
				FactoryFinder.LIFECYCLE_FACTORY);

		for (Iterator<String> lifecycleIds = lifecycleFactory.getLifecycleIds(); lifecycleIds.hasNext();) {
			String lifecycleId = lifecycleIds.next();
			lifecycleFactory.getLifecycle(lifecycleId).addPhaseListener(phaseListener);
		}
	}

	public void removeChildrenFromComponentTree(String clientId) {
		UIComponent comp = FacesContext.getCurrentInstance().getViewRoot().findComponent(clientId);

		if (comp != null) {
			comp.getChildren().clear();
			comp.getFacets().clear();
		}
	}

	public void removeMessages(String clientId) {
		Iterator<FacesMessage> facesMessags = FacesContext.getCurrentInstance().getMessages(clientId);

		while (facesMessags.hasNext()) {
			facesMessags.next();
			facesMessags.remove();
		}
	}

	public void removeMessagesForImmediateComponents() {
		removeMessagesForImmediateComponents(FacesContext.getCurrentInstance().getViewRoot());
	}

	public void removeMessagesForImmediateComponents(UIComponent uiComponent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (uiComponent instanceof ActionSource) {
			ActionSource actionSource = (ActionSource) uiComponent;

			if (actionSource.isImmediate()) {
				removeMessages(uiComponent.getClientId(facesContext));
			}
		}
		else if (uiComponent instanceof EditableValueHolder) {
			EditableValueHolder editableValueHolder = (EditableValueHolder) uiComponent;

			if (editableValueHolder.isImmediate()) {
				removeMessages(uiComponent.getClientId(facesContext));
			}
		}

		List<UIComponent> childComponents = uiComponent.getChildren();

		for (UIComponent childComponent : childComponents) {
			removeMessagesForImmediateComponents(childComponent);
		}
	}

	public void removeParentFormFromComponentTree(final UIComponent uiComponent) {
		UIComponent form = getParentForm(uiComponent);

		if (form != null) {
			form.getChildren().clear();
			form.getFacets().clear();
		}
	}

	public void resetView() {
		resetView(true);
	}

	public void resetView(boolean renderResponse) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
		UIViewRoot emptyView = viewHandler.createView(facesContext, facesContext.getViewRoot().getViewId());
		facesContext.setViewRoot(emptyView);

		if (renderResponse) {
			facesContext.renderResponse();
		}
	}

	public Object resolveExpression(String elExpression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		return facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, elExpression);
	}

	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public Map<String, String> getJavaScriptMap() {

		if (javaScriptMap == null) {
			javaScriptMap = new JavaScriptMap();
		}

		return javaScriptMap;
	}

	public Locale getLocale() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = facesContext.getViewRoot().getLocale();

		if (locale == null) {
			locale = facesContext.getApplication().getDefaultLocale();
		}

		return (locale);
	}

	public String getMessage(String messageId) {
		return getMessage(getLocale(), messageId);
	}

	public String getMessage(String messageId, Object... arguments) {
		return MessageContext.getInstance().getMessage(getLocale(), messageId, arguments);
	}

	public String getMessage(Locale locale, String messageId) {
		return MessageContext.getInstance().getMessage(locale, messageId);
	}

	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return MessageContext.getInstance().getMessage(locale, messageId, arguments);
	}

	public String getNamespace() {
		return FacesContext.getCurrentInstance().getExternalContext().encodeNamespace("");
	}

	public UIForm getParentForm(final UIComponent uiComponent) {
		UIComponent parent = uiComponent;

		while ((parent != null) && !(parent instanceof UIForm)) {
			parent = parent.getParent();
		}

		return (UIForm) parent;
	}

	public Object getRequestAttribute(String name) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();

		return httpServletRequest.getAttribute(name);
	}

	public void setRequestAttribute(String name, Object value) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();
		httpServletRequest.setAttribute(name, value);
	}

	public String getRequestContextPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}

	public String getRequestParameter(String name) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance()
			.getExternalContext().getRequest();

		return httpServletRequest.getParameter(name);
	}

	public boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		return BooleanHelper.toBoolean(getRequestParameter(name), defaultValue);
	}

	public int getRequestParameterAsInt(String name, int defaultValue) {
		return IntegerHelper.toInteger(getRequestParameter(name), defaultValue);
	}

	public long getRequestParameterAsLong(String name, long defaultValue) {
		return LongHelper.toLong(getRequestParameter(name), defaultValue);
	}

	public String getRequestParameterFromMap(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}

	public Map<String, String> getRequestParameterMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	}

	public String getRequestQueryString() {
		return (String) getRequestAttribute("javax.servlet.forward.query_string");
	}

	public String getRequestQueryStringParameter(String name) {
		String value = null;
		String queryString = getRequestQueryString();

		if (queryString != null) {
			String[] queryStringTokens = queryString.split("&");
			boolean found = false;

			for (int i = 0; (!found && (i < queryStringTokens.length)); i++) {
				String nameValuePair = queryStringTokens[i];
				String[] nameValuePairArray = nameValuePair.split("=");
				found = nameValuePairArray[0].equals(name);

				if (found && (nameValuePairArray.length > 1)) {
					value = nameValuePairArray[1];
				}
			}
		}

		return value;
	}

	public Object getSession(boolean create) {
		return FacesContext.getCurrentInstance().getExternalContext().getSession(create);
	}

	public Object getSessionAttribute(String name) {
		Object value = null;
		HttpSession httpSession = (HttpSession) getSession(false);

		if (httpSession != null) {
			value = httpSession.getAttribute(name);
		}

		return value;
	}

	public void setSessionAttribute(String name, Object value) {
		HttpSession httpSession = (HttpSession) getSession(true);

		if (httpSession != null) {
			httpSession.setAttribute(name, value);
		}
	}
}
