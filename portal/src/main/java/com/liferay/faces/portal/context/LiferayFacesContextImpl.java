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
package com.liferay.faces.portal.context;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.ProjectStage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;
import javax.portlet.ActionResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.faces.portal.security.AuthorizationException;
import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.context.MessageContext;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "liferayFacesContext", eager = true)
@ApplicationScoped
public class LiferayFacesContextImpl extends LiferayFacesContext implements Serializable {

	// serialVersionUID Note: This class implements Serializable only to avoid extraneous stacktraces from being thrown
	// by Mojarra. All of the private data members are marked as transient since they are lazy-initialized.
	private static final long serialVersionUID = 905195020822157073L;

	// Private Data Members
	FacesContextHelper facesContextHelper = new FacesContextHelperPortletImpl();
	LiferayPortletHelper liferayPortletHelper = new LiferayPortletHelperImpl();
	PortletHelper portletHelper = new PortletHelperImpl();

	public LiferayFacesContextImpl() {
		setInstance(this);

		MessageContext messageContext = MessageContext.getInstance();
		MessageContext.setInstance(new MessageContextLiferayImpl(messageContext));
	}

	/**
	 * @see  FacesContextHelper#addComponentErrorMessage(String, String)
	 */
	public void addComponentErrorMessage(String clientId, String messageId) {
		facesContextHelper.addComponentErrorMessage(clientId, messageId);
	}

	/**
	 * @see  FacesContextHelper#addComponentErrorMessage(String, String, Object)
	 */
	public void addComponentErrorMessage(String clientId, String messageId, Object argument) {
		facesContextHelper.addComponentErrorMessage(clientId, messageId, argument);
	}

	/**
	 * @see  FacesContextHelper#addComponentErrorMessage(String, String, Object...)
	 */
	public void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		facesContextHelper.addComponentErrorMessage(clientId, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addComponentInfoMessage(String, String)
	 */
	public void addComponentInfoMessage(String clientId, String messageId) {
		facesContextHelper.addComponentInfoMessage(clientId, messageId);
	}

	/**
	 * @see  FacesContextHelper#addComponentInfoMessage(String, String, Object)
	 */
	public void addComponentInfoMessage(String clientId, String messageId, Object argument) {
		facesContextHelper.addComponentInfoMessage(clientId, messageId, argument);
	}

	/**
	 * @see  FacesContextHelper#addComponentInfoMessage(String, String, Object...)
	 */
	public void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		facesContextHelper.addComponentInfoMessage(clientId, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalErrorMessage(String)
	 */
	public void addGlobalErrorMessage(String messageId) {
		facesContextHelper.addGlobalErrorMessage(messageId);
	}

	/**
	 * @see  FacesContextHelper#addGlobalErrorMessage(String, Object)
	 */
	public void addGlobalErrorMessage(String messageId, Object argument) {
		facesContextHelper.addGlobalErrorMessage(messageId, argument);
	}

	/**
	 * @see  FacesContextHelper#addGlobalErrorMessage(String, Object...)
	 */
	public void addGlobalErrorMessage(String messageId, Object... arguments) {
		facesContextHelper.addGlobalErrorMessage(messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalInfoMessage(String)
	 */
	public void addGlobalInfoMessage(String messageId) {
		facesContextHelper.addGlobalInfoMessage(messageId);
	}

	/**
	 * @see  FacesContextHelper#addGlobalInfoMessage(String, Object)
	 */
	public void addGlobalInfoMessage(String messageId, Object argument) {
		facesContextHelper.addGlobalInfoMessage(messageId, argument);
	}

	/**
	 * @see  FacesContextHelper#addGlobalInfoMessage(String, Object...)
	 */
	public void addGlobalInfoMessage(String messageId, Object... arguments) {
		facesContextHelper.addGlobalInfoMessage(messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalSuccessInfoMessage()
	 */
	public void addGlobalSuccessInfoMessage() {
		facesContextHelper.addGlobalSuccessInfoMessage();
	}

	/**
	 * @see  FacesContextHelper#addGlobalUnexpectedErrorMessage()
	 */
	public void addGlobalUnexpectedErrorMessage() {
		facesContextHelper.addGlobalUnexpectedErrorMessage();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void addMessage(String clientId, FacesMessage facesMessage) {
		FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
	}

	/**
	 * @see  FacesContextHelper#addMessage(String, Severity, String)
	 */
	public void addMessage(String clientId, Severity severity, String messageId) {
		facesContextHelper.addMessage(clientId, severity, messageId);

	}

	/**
	 * @see  FacesContextHelper#addMessage(String, Severity, String, Object)
	 */
	public void addMessage(String clientId, Severity severity, String messageId, Object argument) {
		facesContextHelper.addMessage(clientId, severity, messageId, argument);

	}

	/**
	 * @see  FacesContextHelper#addMessage(String, Severity, String, Object...)
	 */
	public void addMessage(String clientId, Severity severity, String messageId, Object... arguments) {
		facesContextHelper.addMessage(clientId, severity, messageId, arguments);
	}

	/**
	 * @see  LiferayPortletHelper#checkUserPortletPermission(String)
	 */
	public void checkUserPortletPermission(String actionId) throws AuthorizationException {
		liferayPortletHelper.checkUserPortletPermission(actionId);
	}

	/**
	 * @see  PortletHelper#createActionURL()
	 */
	public PortletURL createActionURL() {
		return portletHelper.createActionURL();
	}

	/**
	 * @see  PortletHelper#createRenderURL()
	 */
	public PortletURL createRenderURL() {
		return portletHelper.createRenderURL();
	}

	/**
	 * @see  FacesContextHelper#matchComponentInHierarchy(UIComponent, String)
	 */
	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {

		return facesContextHelper.matchComponentInHierarchy(parent, partialClientId);
	}

	/**
	 * @see  FacesContextHelper#matchComponentInViewRoot(String)
	 */
	public UIComponent matchComponentInViewRoot(String partialClientId) {
		return facesContextHelper.matchComponentInViewRoot(partialClientId);
	}

	/**
	 * @see  FacesContextHelper#navigate(String, String)
	 */
	public void navigate(String fromAction, String outcome) {
		facesContextHelper.navigate(fromAction, outcome);
	}

	/**
	 * @see  FacesContextHelper#navigateTo(String)
	 */
	public void navigateTo(String outcome) {
		facesContextHelper.navigateTo(outcome);
	}

	/**
	 * @see  FacesContextHelper#recreateComponentTree()
	 */
	public void recreateComponentTree() {
		facesContextHelper.recreateComponentTree();
	}

	/**
	 * @see  FacesContextHelper#registerPhaseListener(PhaseListener)
	 */
	public void registerPhaseListener(PhaseListener phaseListener) {
		facesContextHelper.registerPhaseListener(phaseListener);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void release() {
		FacesContext.getCurrentInstance().release();
	}

	/**
	 * @see  FacesContextHelper#removeChildrenFromComponentTree(String)
	 */
	public void removeChildrenFromComponentTree(String clientId) {
		facesContextHelper.removeChildrenFromComponentTree(clientId);
	}

	/**
	 * @see  FacesContextHelper#removeMessages(String)
	 */
	public void removeMessages(String clientId) {
		facesContextHelper.removeMessages(clientId);
	}

	/**
	 * @see  FacesContextHelper#removeMessagesForImmediateComponents()
	 */
	public void removeMessagesForImmediateComponents() {
		facesContextHelper.removeMessagesForImmediateComponents();
	}

	/**
	 * @see  FacesContextHelper#removeMessagesForImmediateComponents(UIComponent)
	 */
	public void removeMessagesForImmediateComponents(UIComponent uiComponent) {
		facesContextHelper.removeMessagesForImmediateComponents(uiComponent);
	}

	/**
	 * @see  FacesContextHelper#removeParentFormFromComponentTree(UIComponent)
	 */
	public void removeParentFormFromComponentTree(UIComponent uiComponent) {
		facesContextHelper.removeParentFormFromComponentTree(uiComponent);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void renderResponse() {
		FacesContext.getCurrentInstance().renderResponse();
	}

	/**
	 * @see  FacesContextHelper#resetView()
	 */
	public void resetView() {
		facesContextHelper.resetView();
	}

	/**
	 * @see  FacesContextHelper#resetView(boolean)
	 */
	public void resetView(boolean renderResponse) {
		facesContextHelper.resetView();
	}

	/**
	 * @see  FacesContextHelper#resolveExpression(String)
	 */
	public Object resolveExpression(String elExpression) {
		return facesContextHelper.resolveExpression(elExpression);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void responseComplete() {
		FacesContext.getCurrentInstance().responseComplete();
	}

	/**
	 * @see  LiferayPortletHelper#userHasPortletPermission(String)
	 */
	public boolean userHasPortletPermission(String actionId) {
		return liferayPortletHelper.userHasPortletPermission(actionId);
	}

	/**
	 * @see  LiferayPortletHelper#userHasRole(String)
	 */
	public boolean userHasRole(String roleName) {
		return liferayPortletHelper.userHasRole(roleName);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void validationFailed() {
		FacesContext.getCurrentInstance().validationFailed();
	}

	/**
	 * @see  PortletHelper#getActionResponse()
	 */
	public ActionResponse getActionResponse() {
		return portletHelper.getActionResponse();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Application getApplication() {
		return FacesContext.getCurrentInstance().getApplication();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public Map<Object, Object> getAttributes() {
		return FacesContext.getCurrentInstance().getAttributes();
	}

	@Override
	public int getBuildNumber() {
		return ReleaseInfo.getBuildNumber();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Iterator<String> getClientIdsWithMessages() {
		return FacesContext.getCurrentInstance().getClientIdsWithMessages();
	}

	/**
	 * @see  LiferayPortletHelper#getCompanyId()
	 */
	public long getCompanyId() {
		return liferayPortletHelper.getCompanyId();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public PhaseId getCurrentPhaseId() {
		return FacesContext.getCurrentInstance().getCurrentPhaseId();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setCurrentPhaseId(PhaseId currentPhaseId) {
		FacesContext.getCurrentInstance().setCurrentPhaseId(currentPhaseId);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isValidationFailed() {
		return FacesContext.getCurrentInstance().isValidationFailed();
	}

	/**
	 * @see  PortletHelper#isWindowMaximized()
	 */
	public boolean isWindowMaximized() {
		return portletHelper.isWindowMaximized();
	}

	/**
	 * @see  LiferayPortletHelper#getDocumentLibraryURL()
	 */
	public String getDocumentLibraryURL() {
		return liferayPortletHelper.getDocumentLibraryURL();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isProjectStage(ProjectStage stage) {
		return FacesContext.getCurrentInstance().isProjectStage(stage);
	}

	/**
	 * @see  PortletHelper#isUserInRole(String)
	 */
	public boolean isUserInRole(String roleName) {
		return portletHelper.isUserInRole(roleName);
	}

	/**
	 * @since  JSF 1.2
	 */
	@Override
	public ELContext getELContext() {
		return FacesContext.getCurrentInstance().getELContext();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public ExceptionHandler getExceptionHandler() {
		return FacesContext.getCurrentInstance().getExceptionHandler();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		FacesContext.getCurrentInstance().setExceptionHandler(exceptionHandler);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * @see  FacesContextHelper#getFacesContext()
	 */
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * @see  LiferayPortletHelper#getHostGroupId()
	 */
	public long getHostGroupId() {
		return liferayPortletHelper.getHostGroupId();
	}

	/**
	 * @see  LiferayPortletHelper#getImageGalleryURL()
	 */
	public String getImageGalleryURL() {
		return liferayPortletHelper.getImageGalleryURL();
	}

	/**
	 * @see  FacesContextHelper#getJavaScriptMap()
	 */
	public Map<String, String> getJavaScriptMap() {
		return facesContextHelper.getJavaScriptMap();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isPostback() {
		return FacesContext.getCurrentInstance().isPostback();
	}

	/**
	 * @see  PortletHelper#isWindowNormal()
	 */
	public boolean isWindowNormal() {
		return portletHelper.isWindowNormal();
	}

	/**
	 * @see  LiferayPortletHelper#getLayout()
	 */
	public Layout getLayout() {
		return liferayPortletHelper.getLayout();
	}

	/**
	 * @see  FacesContextHelper#getLocale()
	 */
	public Locale getLocale() {
		Locale locale = null;

		// Try and get the current user's locale from Liferay, since they can override the locale with the Liferay
		// Language portlet.
		ThemeDisplay themeDisplay = getThemeDisplay();

		if (themeDisplay != null) {
			locale = themeDisplay.getLocale();
		}

		// If Liferay didn't return a locale, then try and get the locale from the JSF ViewRoot.
		if (locale == null) {

			locale = getViewRoot().getLocale();

		}

		// If the JSF ViewRoot didn't return a locale, then try and get it from the JSF Application.
		if (locale == null) {
			locale = getApplication().getDefaultLocale();
		}

		// Otherwise, if we couldn't determine the locale, just use the server's default value.
		if (locale == null) {
			locale = Locale.getDefault();
		}

		return locale;
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Severity getMaximumSeverity() {
		return FacesContext.getCurrentInstance().getMaximumSeverity();
	}

	/**
	 * @see  FacesContextHelper#getMessage(String)
	 */
	public String getMessage(String messageId) {
		return facesContextHelper.getMessage(messageId);
	}

	/**
	 * @see  FacesContextHelper#getMessage(String, Object...)
	 */
	public String getMessage(String messageId, Object... arguments) {
		return facesContextHelper.getMessage(messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#getMessage(Locale, String)
	 */
	public String getMessage(Locale locale, String messageId) {
		return facesContextHelper.getMessage(locale, messageId);
	}

	/**
	 * @see  FacesContextHelper#getMessage(Locale, String, Object...)
	 */
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return facesContextHelper.getMessage(locale, messageId, arguments);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public List<FacesMessage> getMessageList() {
		return FacesContext.getCurrentInstance().getMessageList();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public List<FacesMessage> getMessageList(String clientId) {
		return FacesContext.getCurrentInstance().getMessageList(clientId);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Iterator<FacesMessage> getMessages() {
		return FacesContext.getCurrentInstance().getMessages();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Iterator<FacesMessage> getMessages(String clientId) {
		return FacesContext.getCurrentInstance().getMessages(clientId);
	}

	/**
	 * @see  FacesContextHelper#getPortletNamespace()
	 */
	public String getNamespace() {
		return facesContextHelper.getNamespace();
	}

	/**
	 * @see  FacesContextHelper#getParentForm(UIComponent)
	 */
	public UIForm getParentForm(UIComponent uiComponent) {

		return facesContextHelper.getParentForm(uiComponent);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public PartialViewContext getPartialViewContext() {
		return FacesContext.getCurrentInstance().getPartialViewContext();
	}

	/**
	 * @see  LiferayPortletHelper#getPermissionChecker()
	 */
	public PermissionChecker getPermissionChecker() {
		return liferayPortletHelper.getPermissionChecker();
	}

	/**
	 * @see  LiferayPortletHelper#getPlid()
	 */
	public long getPlid() {
		return liferayPortletHelper.getPlid();
	}

	/**
	 * @see  PortletHelper#getPortalContext()
	 */
	public PortalContext getPortalContext() {
		return portletHelper.getPortalContext();
	}

	/**
	 * @see  LiferayPortletHelper#getPortalURL()
	 */
	public String getPortalURL() {
		return liferayPortletHelper.getPortalURL();
	}

	/**
	 * @see  LiferayPortletHelper#getPortlet()
	 */
	public Portlet getPortlet() {
		return liferayPortletHelper.getPortlet();
	}

	/**
	 * @see  PortletHelper#getPortletConfig()
	 */
	public PortletConfig getPortletConfig() {
		return portletHelper.getPortletConfig();
	}

	/**
	 * @see  PortletHelper#getPortletContext()
	 */
	public PortletContext getPortletContext() {
		return portletHelper.getPortletContext();
	}

	/**
	 * @see  PortletHelper#getPortletContextName()
	 */
	public String getPortletContextName() {
		return portletHelper.getPortletContextName();
	}

	/**
	 * @see  LiferayPortletHelper#getPortletInstanceId()
	 */
	public String getPortletInstanceId() {
		return liferayPortletHelper.getPortletInstanceId();
	}

	/**
	 * @see  PortletHelper#setPortletMode(PortletMode)
	 */
	public void setPortletMode(PortletMode portletMode) {
		portletHelper.setPortletMode(portletMode);
	}

	/**
	 * @see  PortletHelper#getPortletName()
	 */
	public String getPortletName() {
		return portletHelper.getPortletName();
	}

	/**
	 * @see  PortletHelper#getPortletPreference(String, Object)
	 */
	public Object getPortletPreference(String preferenceName, Object defaultValue) {
		return portletHelper.getPortletPreference(preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsBool(String, boolean)
	 */
	public boolean getPortletPreferenceAsBool(String preferenceName, boolean defaultValue) {
		return portletHelper.getPortletPreferenceAsBool(preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsInt(String, int)
	 */
	public int getPortletPreferenceAsInt(String preferenceName, int defaultValue) {
		return portletHelper.getPortletPreferenceAsInt(preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsShort(String, short)
	 */
	public short getPortletPreferenceAsShort(String preferenceName, short defaultValue) {
		return getPortletPreferenceAsShort(preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsString(String, String)
	 */
	public String getPortletPreferenceAsString(String preferenceName, String defaultValue) {
		return portletHelper.getPortletPreferenceAsString(preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferences()
	 */
	public PortletPreferences getPortletPreferences() {
		return portletHelper.getPortletPreferences();
	}

	/**
	 * @see  PortletHelper#getPortletRenderRequest()
	 */
	public RenderRequest getPortletRenderRequest() {
		return portletHelper.getPortletRenderRequest();
	}

	/**
	 * @see  PortletHelper#getPortletRenderResponse()
	 */
	public RenderResponse getPortletRenderResponse() {
		return portletHelper.getPortletRenderResponse();
	}

	/**
	 * @see  PortletHelper#getPortletRequest()
	 */
	public PortletRequest getPortletRequest() {
		return portletHelper.getPortletRequest();
	}

	/**
	 * @see  PortletHelper#getPortletResponse()
	 */
	public PortletResponse getPortletResponse() {
		return portletHelper.getPortletResponse();
	}

	/**
	 * @see  LiferayPortletHelper#getPortletRootId()
	 */
	public String getPortletRootId() {
		return liferayPortletHelper.getPortletRootId();
	}

	/**
	 * @see  PortletHelper#getPortletSession()
	 */
	public PortletSession getPortletSession() {
		return portletHelper.getPortletSession();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setProcessingEvents(boolean processingEvents) {
		FacesContext.getCurrentInstance().setProcessingEvents(processingEvents);
	}

	/**
	 * @see  PortletHelper#getRemoteUser()
	 */
	public String getRemoteUser() {
		return portletHelper.getRemoteUser();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public RenderKit getRenderKit() {
		return FacesContext.getCurrentInstance().getRenderKit();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public boolean getRenderResponse() {
		return FacesContext.getCurrentInstance().getRenderResponse();
	}

	/**
	 * @see  FacesContextHelper#getRequestAttribute(String)
	 */
	public Object getRequestAttribute(String name) {

		return facesContextHelper.getRequestAttribute(name);
	}

	/**
	 * @see  FacesContextHelper#setRequestAttribute(String, Object)
	 */
	public void setRequestAttribute(String name, Object value) {
		facesContextHelper.setRequestAttribute(name, value);

	}

	/**
	 * @see  FacesContextHelper#getRequestContextPath()
	 */
	public String getRequestContextPath() {

		return facesContextHelper.getRequestContextPath();
	}

	/**
	 * @see  FacesContextHelper#getRequestParameter(String)
	 */
	public String getRequestParameter(String name) {

		return facesContextHelper.getRequestParameter(name);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsBool(String, boolean)
	 */
	public boolean getRequestParameterAsBool(String name, boolean defaultValue) {

		return facesContextHelper.getRequestParameterAsBool(name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsInt(String, int)
	 */
	public int getRequestParameterAsInt(String name, int defaultValue) {

		return facesContextHelper.getRequestParameterAsInt(name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsLong(String, long)
	 */
	public long getRequestParameterAsLong(String name, long defaultValue) {

		return facesContextHelper.getRequestParameterAsLong(name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterFromMap(String)
	 */
	public String getRequestParameterFromMap(String name) {

		return facesContextHelper.getRequestParameterFromMap(name);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterMap()
	 */
	public Map<String, String> getRequestParameterMap() {
		return facesContextHelper.getRequestParameterMap();
	}

	/**
	 * @see  FacesContextHelper#getRequestQueryString()
	 */
	public String getRequestQueryString() {

		return facesContextHelper.getRequestQueryString();
	}

	/**
	 * @see  FacesContextHelper#getRequestQueryStringParameter(String)
	 */
	public String getRequestQueryStringParameter(String name) {

		return facesContextHelper.getRequestQueryStringParameter(name);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public boolean getResponseComplete() {
		return FacesContext.getCurrentInstance().getRenderResponse();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public ResponseStream getResponseStream() {
		return FacesContext.getCurrentInstance().getResponseStream();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setResponseStream(ResponseStream responseStream) {
		FacesContext.getCurrentInstance().setResponseStream(responseStream);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public ResponseWriter getResponseWriter() {
		return FacesContext.getCurrentInstance().getResponseWriter();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		FacesContext.getCurrentInstance().setResponseWriter(responseWriter);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isProcessingEvents() {
		return FacesContext.getCurrentInstance().isProcessingEvents();
	}

	/**
	 * @see  LiferayPortletHelper#getScopeGroup()
	 */
	public Group getScopeGroup() {
		return liferayPortletHelper.getScopeGroup();
	}

	/**
	 * @see  LiferayPortletHelper#getScopeGroupId()
	 */
	public long getScopeGroupId() {
		return liferayPortletHelper.getScopeGroupId();
	}

	/**
	 * @see  LiferayPortletHelper#getScopeGroupUser()
	 */
	public User getScopeGroupUser() {
		return liferayPortletHelper.getScopeGroupUser();
	}

	/**
	 * @see  LiferayPortletHelper#getServiceContext()
	 */
	public ServiceContext getServiceContext() {
		return liferayPortletHelper.getServiceContext();
	}

	/**
	 * @see  FacesContextHelper#getSession(boolean)
	 */
	public Object getSession(boolean create) {

		return facesContextHelper.getSession(create);
	}

	/**
	 * @see  FacesContextHelper#getSessionAttribute(String)
	 */
	public Object getSessionAttribute(String name) {

		return facesContextHelper.getSessionAttribute(name);
	}

	/**
	 * @see  FacesContextHelper#setSessionAttribute(String, Object)
	 */
	public void setSessionAttribute(String name, Object value) {

		facesContextHelper.setSessionAttribute(name, value);
	}

	/**
	 * @see  PortletHelper#getSessionSharedAttribute(String)
	 */
	public Object getSessionSharedAttribute(String name) {
		return portletHelper.getSessionSharedAttribute(name);
	}

	/**
	 * @see  PortletHelper#setSessionSharedAttribute(String, Object)
	 */
	public void setSessionSharedAttribute(String name, Object value) {
		portletHelper.setSessionSharedAttribute(name, value);
	}

	/**
	 * @see  PortletHelper#isPortletEnvironment()
	 */
	public boolean isPortletEnvironment() {
		return portletHelper.isPortletEnvironment();
	}

	/**
	 * @see  LiferayPortletHelper#getTheme()
	 */
	public Theme getTheme() {
		return liferayPortletHelper.getTheme();
	}

	/**
	 * @see  LiferayPortletHelper#getThemeDisplay()
	 */
	public ThemeDisplay getThemeDisplay() {
		return liferayPortletHelper.getThemeDisplay();
	}

	/**
	 * @see  LiferayPortletHelper#getThemeImagesURL()
	 */
	public String getThemeImagesURL() {
		return liferayPortletHelper.getThemeImagesURL();
	}

	/**
	 * @see  LiferayPortletHelper#getUser()
	 */
	public User getUser() {
		return liferayPortletHelper.getUser();
	}

	/**
	 * @see  LiferayPortletHelper#getUserId()
	 */
	public long getUserId() {
		return liferayPortletHelper.getUserId();
	}

	/**
	 * @see  LiferayPortletHelper#getUserRoles()
	 */
	public List<Role> getUserRoles() throws SystemException {
		return liferayPortletHelper.getUserRoles();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public UIViewRoot getViewRoot() {
		return FacesContext.getCurrentInstance().getViewRoot();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setViewRoot(UIViewRoot viewRoot) {
		FacesContext.getCurrentInstance().setViewRoot(viewRoot);
	}

	/**
	 * @see  PortletHelper#getWindowState()
	 */
	public WindowState getWindowState() {
		return portletHelper.getWindowState();
	}

	/**
	 * @see  PortletHelper#setWindowState(WindowState)
	 */
	public void setWindowState(WindowState windowState) {
		portletHelper.setWindowState(windowState);
	}

	/**
	 * @see  PortletHelper#setWindowStateToMaximized()
	 */
	public void setWindowStateToMaximized() {
		portletHelper.setWindowStateToMaximized();
	}

	/**
	 * @see  PortletHelper#setWindowStateToNormal()
	 */
	public void setWindowStateToNormal() {
		portletHelper.setWindowStateToNormal();
	}
}
