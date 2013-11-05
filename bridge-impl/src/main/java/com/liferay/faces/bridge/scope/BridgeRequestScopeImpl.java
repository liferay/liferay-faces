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
package com.liferay.faces.bridge.scope;

import java.io.Serializable;
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
import javax.faces.render.ResponseStateManager;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.Bridge.PortletPhase;
import javax.portlet.faces.annotation.ExcludeFromManagedRequestScope;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigFactory;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.IncongruityContext;
import com.liferay.faces.bridge.util.FacesMessageWrapper;
import com.liferay.faces.bridge.util.NameValuePair;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeImpl extends BridgeRequestScopeCompat_2_2_Impl implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 7113251688518329851L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeImpl.class);

	// Private Constants for Bridge Request Scope Attributes
	private static final String BRIDGE_REQ_SCOPE_ATTR_ACTION_PARAMS = "com.liferay.faces.bridge.actionParams";
	private static final String BRIDGE_REQ_SCOPE_ATTR_FACES_MESSAGES = "com.liferay.faces.bridge.faces.messages";
	private static final String BRIDGE_REQ_SCOPE_ATTR_FACES_VIEW_ROOT = "com.liferay.faces.bridge.faces.view.root";
	private static final String BRIDGE_REQ_SCOPE_ATTR_INCONGRUITY_CONTEXT_ATTRIBUTES =
		"com.liferay.faces.bridge.incongruitycontext.attributes";
	private static final String BRIDGE_REQ_SCOPE_ATTR_REQUEST_ATTRIBUTES =
		"com.liferay.faces.bridge.faces.request.attributes";

	// Protected Constants
	protected static final String BRIDGE_REQ_SCOPE_NON_EXCLUDED_ATTR_NAMES =
		"com.liferay.faces.bridge.nonExcludedAttributeNames";

	// Protected Constants for EXCLUDED namespaces listed in Section 5.1.2 of the JSR 329 Spec
	protected static final String EXCLUDED_NAMESPACE_JAVAX_FACES = "javax.faces";
	protected static final String EXCLUDED_NAMESPACE_JAVAX_PORTLET = "javax.portlet";
	protected static final String EXCLUDED_NAMESPACE_JAVAX_PORTLET_FACES = "javax.portlet.faces";
	protected static final String EXCLUCED_NAMESPACE_JAVAX_SERVLET = "javax.servlet";
	protected static final String EXCLUCED_NAMESPACE_JAVAX_SERVLET_INCLUDE = "javax.servlet.include";

	// Other Private Constants
	private static final String JAVAX_FACES_ENCODED_URL_PARAM = "javax.faces.encodedURL";

	// Private Data Members
	private Bridge.PortletPhase beganInPhase;
	private long dateCreated;
	private List<String> excludedAttributeNames;
	private boolean facesLifecycleExecuted;
	private String idPrefix;
	private String idSuffix;
	private Map<String, Object> managedBeanMap;
	private boolean navigationOccurred;
	private PortletMode portletMode;
	private String portletName;
	private boolean portletModeChanged;
	private Set<String> preExistingAttributeNames;
	private boolean redirect;

	public BridgeRequestScopeImpl(PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest) {

		this.dateCreated = Calendar.getInstance().getTimeInMillis();

		portletName = portletConfig.getPortletName();

		PortletSession portletSession = portletRequest.getPortletSession();
		String sessionId = portletSession.getId();
		this.idPrefix = portletName + ":::" + sessionId + ":::";
		this.idSuffix = Long.toString(Calendar.getInstance().getTimeInMillis());

		BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
				BridgeConfigFactory.class);
		BridgeConfig bridgeConfig = bridgeConfigFactory.getBridgeConfig();
		this.excludedAttributeNames = new ArrayList<String>();

		// Get the list of excluded BridgeRequestScope attributes from the faces-config.xml descriptors.
		Set<String> facesConfigExcludedAttributeNames = bridgeConfig.getExcludedRequestAttributes();

		// Get the list of excluded BridgeRequestScope attributes from the WEB-INF/portlet.xml descriptor.
		@SuppressWarnings("unchecked")
		List<String> portletContextExcludedAttributeNames = (List<String>) portletContext.getAttribute(
				Bridge.BRIDGE_PACKAGE_PREFIX + portletName + StringPool.PERIOD + Bridge.EXCLUDED_REQUEST_ATTRIBUTES);

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
	 * Unlike Pluto, Liferay will preserve/copy request attributes that were originally set on an {@link ActionRequest}
	 * into the {@link RenderRequest}. However, the Bridge Spec assumes that they will not be preserved. Therefore is
	 * necessary to remove these request attributes when running under Liferay.
	 */
	public void removeExcludedAttributes(RenderRequest renderRequest) {

		if (isRedirectOccurred() || isPortletModeChanged()) {

			// TCK TestPage062: eventScopeNotRestoredRedirectTest
			// TCK TestPage063: eventScopeNotRestoredModeChangedTest
			@SuppressWarnings("unchecked")
			List<String> nonExcludedAttributeNames = (List<String>) getAttribute(
					BRIDGE_REQ_SCOPE_NON_EXCLUDED_ATTR_NAMES);

			if (nonExcludedAttributeNames != null) {

				for (String attributeName : nonExcludedAttributeNames) {

					renderRequest.removeAttribute(attributeName);

					if (logger.isTraceEnabled()) {

						if (isRedirectOccurred()) {
							logger.trace(
								"Due to redirect, removed request attribute name=[{0}] that had been preserved in the ACTION_PHASE or EVENT_PHASE",
								attributeName);
						}
						else {
							logger.trace(
								"Due to PortletMode change, removed request attribute name=[{0}] that had been preserved in the ACTION_PHASE or EVENT_PHASE",
								attributeName);
						}
					}
				}
			}
		}

		// Iterate through all of the request attributes and build up a list of those that are to be removed.
		Enumeration<String> attributeNames = renderRequest.getAttributeNames();

		// TCK TestPage037: requestScopeContentsTest
		// TCK TestPage045: excludedAttributesTest
		// TCK TestPage151: requestMapRequestScopeTest
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			Object attributeValue = renderRequest.getAttribute(attributeName);

			if (preExistingAttributeNames.contains(attributeName)) {

				if (isExcludedRequestAttributeByConfig(attributeName, attributeValue)) {

					// TCK TestPage151 (requestMapRequestScopeTest) remove "verifyPreBridgeExclusion"
					renderRequest.removeAttribute(attributeName);
					logger.debug("Removed request attribute name=[{0}] since it was specified for removal.",
						attributeName);
				}
				else {
					logger.debug(
						"Kept request attribute name=[{0}] since it existed prior to the FacesContext being created.",
						attributeName);
				}
			}
			else {

				if (isExcludedRequestAttributeByConfig(attributeName, attributeValue) ||
						isExcludedRequestAttributeByAnnotation(attributeValue) ||
						isExcludedRequestAttributeByInstance(attributeName, attributeValue) ||
						isExcludedRequestAttributeByNamespace(attributeName)) {

					renderRequest.removeAttribute(attributeName);

					logger.debug(
						"Removed request attribute name=[{0}] that had been preserved in the ACTION_PHASE or EVENT_PHASE",
						attributeName);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void restoreState(FacesContext facesContext) {

		logger.debug("restoreState(facesContext)");

		boolean restoreNonExcludedRequestAttributes = ((beganInPhase == Bridge.PortletPhase.ACTION_PHASE) ||
				(beganInPhase == Bridge.PortletPhase.EVENT_PHASE) ||
				(beganInPhase == Bridge.PortletPhase.RESOURCE_PHASE));

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		PortletPhase portletRequestPhase = bridgeContext.getPortletRequestPhase();

		if (portletRequestPhase == Bridge.PortletPhase.RENDER_PHASE) {

			if (!portletMode.equals(bridgeContext.getPortletRequest().getPortletMode())) {
				setPortletModeChanged(true);
				restoreNonExcludedRequestAttributes = false;
			}
		}

		if ((beganInPhase == Bridge.PortletPhase.ACTION_PHASE) || (beganInPhase == Bridge.PortletPhase.EVENT_PHASE) ||
				(beganInPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {

			// Restore the view root that may have been saved during the action/event/render phase of the portlet
			// lifecycle.
			UIViewRoot uiViewRoot = (UIViewRoot) getAttribute(BRIDGE_REQ_SCOPE_ATTR_FACES_VIEW_ROOT);

			if (uiViewRoot != null) {
				facesContext.setViewRoot(uiViewRoot);
				logger.debug("Restored viewId=[{0}] uiViewRoot=[{1}]", uiViewRoot.getViewId(), uiViewRoot);
			}
			else {
				logger.debug("Did not restore uiViewRoot");
			}

			// Restore the faces messages that may have been saved during the action/event/render phase of the portlet
			// lifecycle.
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
			restoreJSF2FacesContextAttributes(facesContext);
		}

		if (restoreNonExcludedRequestAttributes) {

			// Restore the non-excluded request attributes.
			List<RequestAttribute> savedRequestAttributes = (List<RequestAttribute>) getAttribute(
					BRIDGE_REQ_SCOPE_ATTR_REQUEST_ATTRIBUTES);

			boolean restoredNonExcludedRequestAttributes = false;

			if (savedRequestAttributes != null) {
				ExternalContext externalContext = facesContext.getExternalContext();
				Map<String, Object> currentRequestAttributes = externalContext.getRequestMap();

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

		// If running in the RENDER_PHASE, then the Flash scope must be restored.
		if (portletRequestPhase == Bridge.PortletPhase.RENDER_PHASE) {

			// NOTE: PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-201
			// Restore the flash scope.
			ExternalContext externalContext = facesContext.getExternalContext();
			restoreFlashState(externalContext);

			// PROPOSE-FOR-BRIDGE3-API
			restoreClientWindow(facesContext.getExternalContext());
		}

		// If running in the RENDER_PHASE, then the incongruity context must be restored.
		if (((beganInPhase == Bridge.PortletPhase.ACTION_PHASE) || (beganInPhase == Bridge.PortletPhase.EVENT_PHASE)) &&
				(portletRequestPhase == Bridge.PortletPhase.RENDER_PHASE)) {

			List<IncongruityAttribute> savedIncongruityAttributes = (List<IncongruityAttribute>) getAttribute(
					BRIDGE_REQ_SCOPE_ATTR_INCONGRUITY_CONTEXT_ATTRIBUTES);

			if (savedIncongruityAttributes != null) {

				IncongruityContext incongruityContext = bridgeContext.getIncongruityContext();
				Map<String, Object> incongruityContextAttributes = incongruityContext.getAttributes();

				for (IncongruityAttribute incongruityAttribute : savedIncongruityAttributes) {
					String key = incongruityAttribute.getName();
					Object value = incongruityAttribute.getValue();
					incongruityContextAttributes.put(key, value);
				}
			}
		}
	}

	/**
	 * Saves the state of the FacesContext as required by section 5.1.2 of the JSR 329 spec. This method is designed to
	 * be called during the ACTION_PHASE of the portlet lifecycle.
	 *
	 * @param  facesContext  The current faces context.
	 */
	public void saveState(FacesContext facesContext) {

		logger.debug("saveState(facesContext)");

		// Get the ExternalContext and PortletResponse.
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletResponse portletResponse = (PortletResponse) facesContext.getExternalContext().getResponse();

		if ((beganInPhase == Bridge.PortletPhase.ACTION_PHASE) || (beganInPhase == Bridge.PortletPhase.EVENT_PHASE) ||
				(beganInPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {

			// Save the view root.
			setAttribute(BRIDGE_REQ_SCOPE_ATTR_FACES_VIEW_ROOT, facesContext.getViewRoot());

			// If the PortletMode hasn't changed, then preserve the "javax.faces.ViewState" request parameter value.
			if (!isPortletModeChanged()) {

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
			saveJSF2FacesContextAttributes(facesContext);
			
			boolean saveNonExcludedAttributes = true;

			// If a redirect occurred, then indicate that the non-excluded request attributes are not to be preserved.
			if (isRedirectOccurred()) {

				// TCK TestPage062: eventScopeNotRestoredRedirectTest
				logger.trace("Due to redirect, not saving any non-excluded request attributes");
				saveNonExcludedAttributes = false;
			}

			// Otherwise, if the portlet mode has changed, then indicate that the non-exluded request attributes are
			// not to be preserved.
			else if (isPortletModeChanged()) {
				logger.trace("Due to PortletMode change, not saving any non-excluded request attributes");
				saveNonExcludedAttributes = false;
			}

			// If appropriate, save the non-excluded request attributes. This would include, for example, managed-bean
			// instances that may have been created during the ACTION_PHASE that need to survive to the RENDER_PHASE.
			Map<String, Object> currentRequestAttributes = externalContext.getRequestMap();

			if (currentRequestAttributes != null) {
				List<RequestAttribute> savedRequestAttributes = new ArrayList<RequestAttribute>();
				List<String> nonExcludedAttributeNames = new ArrayList<String>();
				Iterator<Map.Entry<String, Object>> itr = currentRequestAttributes.entrySet().iterator();

				if (itr != null) {

					while (itr.hasNext()) {
						Map.Entry<String, Object> mapEntry = itr.next();
						String attributeName = mapEntry.getKey();
						Object attributeValue = mapEntry.getValue();

						if (isExcludedRequestAttributeByConfig(attributeName, attributeValue) ||
								isExcludedRequestAttributeByAnnotation(attributeValue) ||
								isExcludedRequestAttributeByNamespace(attributeName) ||
								isExcludedRequestAttributeByInstance(attributeName, attributeValue) ||
								isExcludedRequestAttributeByPreExisting(attributeName)) {

							logger.trace("NOT saving EXCLUDED attribute name=[{0}]", attributeName);
						}
						else {

							if (saveNonExcludedAttributes) {
								logger.trace("SAVING non-excluded request attribute name=[{0}] value=[{1}]",
									attributeName, attributeValue);
								savedRequestAttributes.add(new RequestAttribute(attributeName, attributeValue));
							}

							nonExcludedAttributeNames.add(attributeName);
						}
					}

					if (savedRequestAttributes.size() > 0) {
						setAttribute(BRIDGE_REQ_SCOPE_ATTR_REQUEST_ATTRIBUTES, savedRequestAttributes);
					}
					else {
						logger.trace("Not saving any non-excluded request attributes");
					}

					setAttribute(BRIDGE_REQ_SCOPE_NON_EXCLUDED_ATTR_NAMES, nonExcludedAttributeNames);
				}
			}
			else {
				logger.trace("Not saving any non-excluded request attributes because there are no request attributes!");
			}
		}

		// If running in the ACTION_PHASE or EVENT_PHASE, then the Flash scope must be saved as well so that it can be
		// restored.
		Bridge.PortletPhase portletRequestPhase = bridgeContext.getPortletRequestPhase();

		if ((portletRequestPhase == Bridge.PortletPhase.ACTION_PHASE) ||
				(portletRequestPhase == Bridge.PortletPhase.EVENT_PHASE)) {

			// PROPOSED-FOR-JSR344-API: http://java.net/jira/browse/JAVASERVERFACES_SPEC_PUBLIC-1070
			// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-201
			saveFlashState(externalContext);
			
			// PROPOSE-FOR-BRIDGE3-API
			saveClientWindow(externalContext);
		}

		// If running in the ACTION_PHASE or EVENT_PHASE, then the incongruity context must be saved as well so that it
		// can be restored.
		if ((portletRequestPhase == Bridge.PortletPhase.ACTION_PHASE) ||
				(portletRequestPhase == Bridge.PortletPhase.EVENT_PHASE)) {

			IncongruityContext incongruityContext = bridgeContext.getIncongruityContext();
			Map<String, Object> incongruityAttributeMap = incongruityContext.getAttributes();
			int mapSize = incongruityAttributeMap.size();
			List<IncongruityAttribute> savedIncongruityAttributes = new ArrayList<IncongruityAttribute>(mapSize);
			Iterator<Map.Entry<String, Object>> itr = incongruityAttributeMap.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> mapEntry = itr.next();
				String name = mapEntry.getKey();
				Object value = mapEntry.getValue();
				logger.trace("Saving IncongruityContext attribute name=[{0}] value=[{1}]", name, value);
				savedIncongruityAttributes.add(new IncongruityAttribute(name, value));
			}

			setAttribute(BRIDGE_REQ_SCOPE_ATTR_INCONGRUITY_CONTEXT_ATTRIBUTES, savedIncongruityAttributes);
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
		buf.append(StringPool.AT);
		buf.append(Integer.toHexString(hashCode()));

		return buf.toString();
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

	public long getDateCreated() {
		return dateCreated;
	}

	protected boolean isExcludedRequestAttributeByInstance(String attributeName, Object attributeValue) {

		// EXCLUDED attributes listed in Section 5.1.2 of the JSR 329 Spec
		return ((attributeValue != null) &&
				((attributeValue instanceof ExternalContext) || (attributeValue instanceof FacesContext) ||
					(attributeValue instanceof HttpSession) || (attributeValue instanceof PortalContext) ||
					(attributeValue instanceof PortletConfig) || (attributeValue instanceof PortletContext) ||
					(attributeValue instanceof PortletPreferences) || (attributeValue instanceof PortletRequest) ||
					(attributeValue instanceof PortletResponse) || (attributeValue instanceof PortletSession) ||
					(attributeValue instanceof ServletConfig) || (attributeValue instanceof ServletContext) ||
					(attributeValue instanceof ServletRequest) || (attributeValue instanceof ServletResponse)));
	}

	protected boolean isExcludedRequestAttributeByNamespace(String attributeName) {

		if (isNamespaceMatch(attributeName, EXCLUDED_NAMESPACE_JAVAX_FACES) ||
				isNamespaceMatch(attributeName, EXCLUDED_NAMESPACE_JAVAX_PORTLET) ||
				isNamespaceMatch(attributeName, EXCLUDED_NAMESPACE_JAVAX_PORTLET_FACES) ||
				isNamespaceMatch(attributeName, EXCLUCED_NAMESPACE_JAVAX_SERVLET) ||
				isNamespaceMatch(attributeName, EXCLUCED_NAMESPACE_JAVAX_SERVLET_INCLUDE)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setFacesLifecycleExecuted(boolean facesLifecycleExecuted) {
		this.facesLifecycleExecuted = facesLifecycleExecuted;
	}

	protected boolean isExcludedRequestAttributeByConfig(String attributeName, Object attributeValue) {

		boolean excluded = false;

		if (excludedAttributeNames != null) {

			for (String excludedAttribute : excludedAttributeNames) {

				if (attributeName.equals(excludedAttribute)) {
					excluded = true;

					break;
				}
				else if (excludedAttribute.endsWith(StringPool.STAR)) {

					String wildcardNamespace = excludedAttribute;
					int dotPos = wildcardNamespace.lastIndexOf(StringPool.PERIOD);

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

		return excluded;
	}

	protected boolean isExcludedRequestAttributeByPreExisting(String attributeName) {
		return preExistingAttributeNames.contains(attributeName);
	}

	protected boolean isNamespaceMatch(String attributeName, String namespace) {

		boolean match = false;

		String attributeNamespace = attributeName;
		int dotPos = attributeNamespace.lastIndexOf(StringPool.PERIOD);

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

	protected boolean isExcludedRequestAttributeByAnnotation(Object attributeValue) {

		boolean excluded = false;

		if ((attributeValue != null) &&
				(attributeValue.getClass().getAnnotation(ExcludeFromManagedRequestScope.class) != null)) {
			excluded = true;
		}

		return excluded;
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

	protected class IncongruityAttribute extends NameValuePair<String, Object> {

		public IncongruityAttribute(String name, Object value) {
			super(name, value);
		}
	}

	protected class RequestAttribute extends NameValuePair<String, Object> {

		public RequestAttribute(String name, Object value) {
			super(name, value);
		}
	}
}
