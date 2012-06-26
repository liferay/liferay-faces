/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.scope;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.render.ResponseStateManager;
import javax.portlet.ActionResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.annotation.ExcludeFromManagedRequestScope;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigFactory;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.bridge.util.FacesMessageWrapper;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeImpl extends ConcurrentHashMap<String, Object> implements BridgeRequestScope {

	// serialVersionUID
	private static final long serialVersionUID = 7113251688518329851L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeImpl.class);

	// Private Constants for Bridge Request Scope Attributes
	private static final String BRIDGE_REQ_SCOPE_ATTR_ACTION_PARAMS = "com.liferay.faces.bridge.actionParams";
	private static final String BRIDGE_REQ_SCOPE_ATTR_FACES_CONTEXT_ATTRIBUTES =
		"com.liferay.faces.bridge.facescontext.attributes";
	private static final String BRIDGE_REQ_SCOPE_ATTR_FACES_MESSAGES = "com.liferay.faces.bridge.faces.messages";
	private static final String BRIDGE_REQ_SCOPE_ATTR_FACES_VIEW_ROOT = "com.liferay.faces.bridge.faces.view.root";

	// Protected Constants
	protected static final String BRIDGE_REQ_SCOPE_ATTR_REQUEST_ATTRIBUTES =
		"com.liferay.faces.bridge.faces.request.attributes";

	// Private Constants for EXCLUDED namespaces listed in Section 5.1.2 of the JSR 329 Spec
	private static final String EXCLUDED_NAMESPACE_JAVAX_FACES = "javax.faces";
	private static final String EXCLUDED_NAMESPACE_JAVAX_PORTLET = "javax.portlet";
	private static final String EXCLUDED_NAMESPACE_JAVAX_PORTLET_FACES = "javax.portlet.faces";
	private static final String EXCLUCED_NAMESPACE_JAVAX_SERVLET = "javax.servlet";
	private static final String EXCLUCED_NAMESPACE_JAVAX_SERVLET_INCLUDE = "javax.servlet.include";
	private static List<String> STANDARD_EXCLUDED_REQUEST_ATTRIBUTE_NAMESPACES = new ArrayList<String>(3);

	// Other Private Constants
	private static final String JAVAX_FACES_ENCODED_URL_PARAM = "javax.faces.encodedURL";

	static {

		// Build up the static list of standard excluded request attribute namespaces.
		STANDARD_EXCLUDED_REQUEST_ATTRIBUTE_NAMESPACES.add(EXCLUDED_NAMESPACE_JAVAX_FACES);
		STANDARD_EXCLUDED_REQUEST_ATTRIBUTE_NAMESPACES.add(EXCLUDED_NAMESPACE_JAVAX_PORTLET);
		STANDARD_EXCLUDED_REQUEST_ATTRIBUTE_NAMESPACES.add(EXCLUDED_NAMESPACE_JAVAX_PORTLET_FACES);
		STANDARD_EXCLUDED_REQUEST_ATTRIBUTE_NAMESPACES.add(EXCLUCED_NAMESPACE_JAVAX_SERVLET);
		STANDARD_EXCLUDED_REQUEST_ATTRIBUTE_NAMESPACES.add(EXCLUCED_NAMESPACE_JAVAX_SERVLET_INCLUDE);
	}

	// Private Data Members
	private Map<String, Object> attributeMap;
	private Bridge.PortletPhase beganInPhase;
	private List<String> excludedAttributeNames;
	private boolean facesLifecycleExecuted;
	private Flash flash;
	private String idPrefix;
	private String idSuffix;
	private Map<String, Object> managedBeanMap;
	private boolean navigationOccurred;
	private PortletMode portletMode;
	private boolean portletModeChanged;
	private Set<String> preExistingAttributeNames;
	private boolean redirect;

	public BridgeRequestScopeImpl(PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, String idPrefix) {

		this.attributeMap = new HashMap<String, Object>();

		long timeInMillis = Calendar.getInstance().getTimeInMillis();
		this.idPrefix = idPrefix;
		this.idSuffix = Long.toString(timeInMillis);

		BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
				BridgeConfigFactory.class);
		BridgeConfig bridgeConfig = bridgeConfigFactory.getBridgeConfig();
		this.excludedAttributeNames = new ArrayList<String>();

		// Get the list of excluded BridgeRequestScope attributes from the faces-config.xml descriptors.
		Set<String> facesConfigExcludedAttributeNames = bridgeConfig.getExcludedRequestAttributes();

		// Get the list of excluded BridgeRequestScope attributes from the WEB-INF/portlet.xml descriptor.
		@SuppressWarnings("unchecked")
		List<String> portletContextExcludedAttributeNames = (List<String>) portletContext.getAttribute(
				Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() + BridgeConstants.CHAR_PERIOD +
				Bridge.EXCLUDED_REQUEST_ATTRIBUTES);

		// Combine the two lists into a single list of excluded BridgeRequestScope attributes.
		if (facesConfigExcludedAttributeNames != null) {
			this.excludedAttributeNames.addAll(facesConfigExcludedAttributeNames);
		}

		if (portletContextExcludedAttributeNames != null) {
			this.excludedAttributeNames.addAll(portletContextExcludedAttributeNames);
		}

		this.portletMode = PortletMode.VIEW;
		this.preExistingAttributeNames = getPreExistingRequestAttributeNames(portletRequest);

		this.beganInPhase = (Bridge.PortletPhase) portletRequest.getAttribute(Bridge.PORTLET_LIFECYCLE_PHASE);
	}

	/**
	 * The overrides for {@link #toString()} and {@link #hashCode()} are necessary because the {@link ConcurrentHashMap}
	 * parent class overrides them and causes debug logs to be difficult to interpret.
	 */
	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	/**
	 * Saves the state of the FacesContext as required by section 5.1.2 of the JSR 329 spec. This method is designed to
	 * be called during the ACTION_PHASE of the portlet lifecycle.
	 *
	 * @param  facesContext  The current faces context.
	 */
	public void preserve(FacesContext facesContext) {

		logger.debug("preserveScopedData(facesContext)");

		// Get the ExternalContext.
		ExternalContext externalContext = facesContext.getExternalContext();

		if ((beganInPhase == Bridge.PortletPhase.ACTION_PHASE) || (beganInPhase == Bridge.PortletPhase.EVENT_PHASE)) {

			// Save the view root.
			setAttribute(BRIDGE_REQ_SCOPE_ATTR_FACES_VIEW_ROOT, facesContext.getViewRoot());

			// If the PortletMode hasn't changed, then preserve the "javax.faces.ViewState" request parameter value.
			if (!isPortletModeChanged()) {
				PortletResponse portletResponse = (PortletResponse) facesContext.getExternalContext().getResponse();

				if (portletResponse instanceof ActionResponse) {
					String viewState = facesContext.getExternalContext().getRequestParameterMap().get(
							ResponseStateManager.VIEW_STATE_PARAM);

					if (viewState != null) {

						// NOTE: Although it is possible to save this as a render parameter, can't use that approach
						// because portlet containers like Pluto will add the "javax.faces.ViewState" parameter to any
						// ResourceURLs that are created during the RENDER_PHASE of the portlet lifecycle.
						setAttribute(ResponseStateManager.VIEW_STATE_PARAM, viewState);
					}
				}
			}

			// If specified in the WEB-INF/portlet.xml descriptor, then preserve the action parameters.
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

			if (bridgeContext.isPreserveActionParams()) {
				Map<String, String> actionRequestParameterMap = new HashMap<String, String>(
						externalContext.getRequestParameterMap());
				actionRequestParameterMap.remove(ResponseStateManager.VIEW_STATE_PARAM);
				actionRequestParameterMap.remove(JAVAX_FACES_ENCODED_URL_PARAM);
				setAttribute(BRIDGE_REQ_SCOPE_ATTR_ACTION_PARAMS, actionRequestParameterMap);
			}

			// Save the list of faces messages.
			List<FacesMessageWrapper> facesMessageWrappers = new ArrayList<FacesMessageWrapper>();
			Iterator<String> clientIds = facesContext.getClientIdsWithMessages();

			while (clientIds.hasNext()) {
				String clientId = clientIds.next();
				Iterator<FacesMessage> facesMessages = facesContext.getMessages(clientId);

				while (facesMessages.hasNext()) {
					FacesMessage facesMessage = facesMessages.next();
					FacesMessageWrapper facesMessageWrapper = new FacesMessageWrapper(clientId, facesMessage);
					facesMessageWrappers.add(facesMessageWrapper);
				}
			}

			if (facesMessageWrappers.size() > 0) {
				setAttribute(BRIDGE_REQ_SCOPE_ATTR_FACES_MESSAGES, facesMessageWrappers);
			}
			else {
				logger.trace("Not saving any faces messages");
			}

			// NOTE: PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-203 Build up a list
			// of attributes found in the FacesContext attribute map and save them. It has to be copied in this manner
			// because the Faces implementation likely calls the clear() method during the call to its
			// FacesContextImpl.release() method.
			Map<Object, Object> currentFacesContextAttributes = facesContext.getAttributes();
			int mapSize = currentFacesContextAttributes.size();
			List<FacesContextAttribute> savedFacesContextAttributes = new ArrayList<FacesContextAttribute>(mapSize);
			Iterator<Map.Entry<Object, Object>> itr = currentFacesContextAttributes.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<Object, Object> mapEntry = itr.next();
				Object name = mapEntry.getKey();
				Object value = mapEntry.getValue();
				logger.trace("Saving FacesContext attribute name=[{0}] value=[{1}]", name, value);
				savedFacesContextAttributes.add(new FacesContextAttribute(name, value));
			}

			setAttribute(BRIDGE_REQ_SCOPE_ATTR_FACES_CONTEXT_ATTRIBUTES, savedFacesContextAttributes);
		}

		if ((beganInPhase == Bridge.PortletPhase.ACTION_PHASE) || (beganInPhase == Bridge.PortletPhase.EVENT_PHASE) ||
				(beganInPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {

			// If a redirect occurred, then the non-excluded request attributes are not to be preserved. Note that for
			// Liferay, not-preserving simply isn't good enough. See
			// BridgeRequestScopeLiferayImpl#restoreScopedData(FacesContext) for more information.
			if (isRedirectOccurred()) {

				// TCK TestPage062: eventScopeNotRestoredRedirectTest
				logger.trace("Due to redirect, not saving any non-excluded request attributes");
			}

			// Otherwise, if the portlet mode hasn't changed, then save the non-excluded request attributes. This would
			// include, for example, managed-bean instances that may have been created during the ACTION_PHASE that
			// need to survive to the RENDER_PHASE.
			else if (!isPortletModeChanged()) {

				Map<String, Object> currentRequestAttributes = externalContext.getRequestMap();

				if (currentRequestAttributes != null) {
					List<RequestAttribute> savedRequestAttributes = new ArrayList<RequestAttribute>();
					Iterator<Map.Entry<String, Object>> itr = currentRequestAttributes.entrySet().iterator();

					if (itr != null) {

						while (itr.hasNext()) {
							Map.Entry<String, Object> mapEntry = itr.next();
							String name = mapEntry.getKey();
							Object value = mapEntry.getValue();

							if (isExcludedRequestAttribute(name, value)) {
								logger.trace("Not saving EXCLUDED attribute name=[{0}]", name);
							}
							else if ((value != null) &&
									(value.getClass().getAnnotation(ExcludeFromManagedRequestScope.class) != null)) {
								logger.trace(
									"Not saving EXCLUDED attribute name=[{0}] due to ExcludeFromManagedRequestScope annotation",
									name);
							}
							else {
								logger.trace("Saving non-excluded request attribute name=[{0}] value=[{1}]", name,
									value);
								savedRequestAttributes.add(new RequestAttribute(name, value));
							}
						}

						if (savedRequestAttributes.size() > 0) {
							setAttribute(BRIDGE_REQ_SCOPE_ATTR_REQUEST_ATTRIBUTES, savedRequestAttributes);
						}
						else {
							logger.trace("Not saving any non-excluded request attributes");
						}
					}
				}
				else {
					logger.trace(
						"Not saving any non-excluded request attributes because there are no request attributes!");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void restore(FacesContext facesContext) {

		boolean restoreNonExcludedRequestAttributes = ((beganInPhase == Bridge.PortletPhase.ACTION_PHASE) || (beganInPhase == Bridge.PortletPhase.EVENT_PHASE) ||
				(beganInPhase == Bridge.PortletPhase.RESOURCE_PHASE));
		
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		if (bridgeContext.getPortletRequestPhase() == Bridge.PortletPhase.RENDER_PHASE) {
			
			if (!portletMode.equals(bridgeContext.getPortletRequest().getPortletMode())) {
				setPortletModeChanged(true);
				restoreNonExcludedRequestAttributes = false;
			}
		}

		if ((beganInPhase == Bridge.PortletPhase.ACTION_PHASE) || (beganInPhase == Bridge.PortletPhase.EVENT_PHASE)) {

			// Restore the view root that may have been saved during the ACTION_PHASE of the portlet lifecycle.
			UIViewRoot uiViewRoot = (UIViewRoot) getAttribute(BRIDGE_REQ_SCOPE_ATTR_FACES_VIEW_ROOT);

			if (uiViewRoot != null) {
				facesContext.setViewRoot(uiViewRoot);
				logger.debug("Restored viewId=[{0}] uiViewRoot=[{1}]", uiViewRoot.getViewId(), uiViewRoot);
			}
			else {
				logger.debug("Did not restore uiViewRoot");
			}

			// Restore the faces messages that may have been saved during the ACTION_PHASE of the portlet lifecycle.
			List<FacesMessageWrapper> facesMessages = (List<FacesMessageWrapper>) getAttribute(
					BRIDGE_REQ_SCOPE_ATTR_FACES_MESSAGES);

			boolean restoredFacesMessages = false;

			if (facesMessages != null) {

				for (FacesMessageWrapper facesMessageWrapper : facesMessages) {
					String clientId = facesMessageWrapper.getClientId();
					FacesMessage facesMessage = facesMessageWrapper.getFacesMessage();
					facesContext.addMessage(clientId, facesMessage);
					logger.trace("Restored facesMessage=[{0}]", facesMessage.getSummary());
					restoredFacesMessages = true;
				}
			}

			if (restoredFacesMessages) {
				logger.debug("Restored facesMessages");
			}
			else {
				logger.debug("Did not restore any facesMessages");
			}

			// NOTE: PROPOSE-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-203 Restore the
			// FacesContext attributes that may have been saved during the ACTION_PHASE of the portlet lifecycle.
			List<FacesContextAttribute> savedFacesContextAttributes = (List<FacesContextAttribute>) getAttribute(
					BRIDGE_REQ_SCOPE_ATTR_FACES_CONTEXT_ATTRIBUTES);

			boolean restoredFacesContextAttibutes = false;

			if (savedFacesContextAttributes != null) {
				Map<Object, Object> currentFacesContextAttributes = facesContext.getAttributes();

				for (FacesContextAttribute facesContextAttribute : savedFacesContextAttributes) {
					Object name = facesContextAttribute.getName();

					Object value = facesContextAttribute.getValue();
					logger.trace("Restoring FacesContext attribute name=[{0}] value=[{1}]", name, value);
					currentFacesContextAttributes.put(name, value);
					restoredFacesContextAttibutes = true;
				}
			}

			if (restoredFacesContextAttibutes) {
				logger.debug("Restored FacesContext attributes");
			}
			else {
				logger.debug("Did not restore any FacesContext attributes");
			}
		}

		if (restoreNonExcludedRequestAttributes) {

			// Restore the non-excluded request attributes.
			List<RequestAttribute> savedRequestAttributes = (List<RequestAttribute>) getAttribute(
					BRIDGE_REQ_SCOPE_ATTR_REQUEST_ATTRIBUTES);

			boolean restoredNonExcludedRequestAttributes = false;

			if (savedRequestAttributes != null) {
				Map<String, Object> currentRequestAttributes = facesContext.getExternalContext().getRequestMap();

				// If a redirect did not occur, then restore the non-excluded request attributes.
				if (!isRedirectOccurred()) {

					for (RequestAttribute requestAttribute : savedRequestAttributes) {
						String name = requestAttribute.getName();
						Object value = requestAttribute.getValue();
						logger.trace("Restoring non-excluded request attribute name=[{0}] value=[{1}]", name, value);
						currentRequestAttributes.put(name, value);
						restoredNonExcludedRequestAttributes = true;
					}
				}
			}

			if (restoredNonExcludedRequestAttributes) {
				logger.debug("Restored non-excluded request attributes");
			}
			else {
				logger.debug("Did not restore any non-excluded request attributes");
			}
		}
	}

	/**
	 * The overrides for {@link #toString()} and {@link #hashCode()} are necessary because the {@link ConcurrentHashMap}
	 * parent class overrides them and causes debug logs to be difficult to interpret.
	 */
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(getClass().getName());
		buf.append(BridgeConstants.CHAR_AT);
		buf.append(Integer.toHexString(hashCode()));

		return buf.toString();
	}

	public Object getAttribute(String key) {
		return attributeMap.get(key);
	}

	public void setAttribute(String key, Object value) {
		attributeMap.put(key, value);
	}

	public Bridge.PortletPhase getBeganInPhase() {
		return beganInPhase;
	}

	public boolean isFacesLifecycleExecuted() {
		return facesLifecycleExecuted;
	}

	public boolean isNavigationOccurred() {
		return navigationOccurred;
	}

	public boolean isPortletModeChanged() {
		return portletModeChanged;
	}

	public boolean isRedirectOccurred() {
		return redirect;
	}

	protected boolean isExcludedRequestAttribute(String attributeName, Object value) {
		boolean excluded = false;

		if (!excluded) {

			if (excludedAttributeNames != null) {

				for (String excludedAttribute : excludedAttributeNames) {

					if (attributeName.equals(excludedAttribute)) {
						excluded = true;

						break;
					}
					else if (excludedAttribute.endsWith(BridgeConstants.CHAR_ASTERISK)) {

						String wildcardNamespace = excludedAttribute;
						int dotPos = wildcardNamespace.lastIndexOf(BridgeConstants.CHAR_PERIOD);

						if (dotPos > 0) {
							wildcardNamespace = wildcardNamespace.substring(0, dotPos);
						}

						if (isNamespaceMatch(attributeName, wildcardNamespace)) {
							excluded = true;

							break;
						}
					}
				}
			}
		}

		if (!excluded) {

			for (String namespace : STANDARD_EXCLUDED_REQUEST_ATTRIBUTE_NAMESPACES) {

				if (isNamespaceMatch(attributeName, namespace)) {
					excluded = true;

					break;
				}
			}
		}

		if (!excluded) {
			excluded = preExistingAttributeNames.contains(attributeName);
		}

		if (!excluded) {

			// EXCLUDED attributes listed in Section 5.1.2 of the JSR 329 Spec
			excluded = ((value != null) &&
					((value instanceof ExternalContext) || (value instanceof FacesContext) ||
						(value instanceof HttpSession) || (value instanceof PortalContext) ||
						(value instanceof PortletConfig) || (value instanceof PortletContext) ||
						(value instanceof PortletPreferences) || (value instanceof PortletRequest) ||
						(value instanceof PortletResponse) || (value instanceof PortletSession) ||
						(value instanceof ServletConfig) || (value instanceof ServletContext) ||
						(value instanceof ServletRequest) || (value instanceof ServletResponse)));
		}

		return excluded;
	}

	public void setFacesLifecycleExecuted(boolean facesLifecycleExecuted) {
		this.facesLifecycleExecuted = facesLifecycleExecuted;
	}

	public Flash getFlash() {
		return flash;
	}

	public void setFlash(Flash flash) {
		this.flash = flash;
	}

	protected boolean isNamespaceMatch(String attributeName, String namespace) {

		boolean match = false;

		String attributeNamespace = attributeName;
		int dotPos = attributeNamespace.lastIndexOf(BridgeConstants.CHAR_PERIOD);

		if (dotPos > 0) {
			attributeNamespace = attributeNamespace.substring(0, dotPos);
		}

		if (namespace.equals(attributeNamespace)) {
			match = true;
		}

		return match;
	}

	public String getId() {
		return idPrefix + idSuffix;
	}

	public void setIdPrefix(String idPrefix) {
		this.idPrefix = idPrefix;
	}

	public Map<String, Object> getManagedBeanMap() {

		if (managedBeanMap == null) {
			managedBeanMap = new HashMap<String, Object>();
		}

		return managedBeanMap;
	}

	public void setNavigationOccurred(boolean navigationOccurred) {
		this.navigationOccurred = navigationOccurred;
	}

	public PortletMode getPortletMode() {
		return portletMode;
	}

	public void setPortletMode(PortletMode portletMode) {
		this.portletMode = portletMode;
	}

	public void setPortletModeChanged(boolean portletModeChanged) {
		this.portletModeChanged = portletModeChanged;
	}

	/**
	 * According to section 5.1.2 of the JSR 329 spec, the request attributes that exist before the bridge acquires the
	 * FacesContext must not be part of the bridge request scope. Having noted that, we have to save-off a list of names
	 * of these pre-existing request attributes, so that we know to NOT restore them.
	 */
	protected Set<String> getPreExistingRequestAttributeNames(PortletRequest portletRequest) {
		Set<String> attributeNames = null;
		Enumeration<String> requestAttributeNames = portletRequest.getAttributeNames();

		if (requestAttributeNames != null) {
			attributeNames = new HashSet<String>();

			while (requestAttributeNames.hasMoreElements()) {
				String attributeName = requestAttributeNames.nextElement();
				attributeNames.add(attributeName);
				logger.trace("Saving name of pre-existing request attribute [{0}]", attributeName);
			}
		}

		return attributeNames;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getPreservedActionParameterMap() {
		return (Map<String, String>) getAttribute(BRIDGE_REQ_SCOPE_ATTR_ACTION_PARAMS);
	}

	public String getPreservedViewStateParam() {
		return (String) getAttribute(ResponseStateManager.VIEW_STATE_PARAM);
	}

	public void setRedirectOccurred(boolean redirect) {
		this.redirect = redirect;
	}
}
