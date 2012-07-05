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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.ActionRequest;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.faces.Bridge;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import com.liferay.faces.bridge.BridgeExt;
import com.liferay.faces.bridge.container.liferay.LiferayConstants;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeLiferayImpl extends BridgeRequestScopeImpl {

	// Private Constants
	private static final String JAVAX_PORTLET_CONFIG = "javax.portlet.config";
	private static final String JAVAX_PORTLET_PORTLET = "javax.portlet.portlet";
	private static final String JAVAX_PORTLET_REQUEST = "javax.portlet.request";
	private static final String JAVAX_PORTLET_RESPONSE = "javax.portlet.response";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeLiferayImpl.class);

	// serialVersionUID
	private static final long serialVersionUID = 1814762389345663517L;

	public BridgeRequestScopeLiferayImpl(PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, String idPrefix) {
		super(portletConfig, portletContext, portletRequest, idPrefix);
	}

	/**
	 * Unlike Pluto, Liferay will preserve/copy request attributes that were originally set on an {@link ActionRequest}
	 * into the {@link RenderRequest}. However, the Bridge Spec assumes that they will not be preserved. Therefore is
	 * necessary to remove these request attributes when running under Liferay.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void restore(FacesContext facesContext) {

		super.restore(facesContext);

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> currentRequestAttributes = externalContext.getRequestMap();

		if (isRedirectOccurred() || isPortletModeChanged()) {

			// TCK TestPage062: eventScopeNotRestoredRedirectTest
			// TCK TestPage063: eventScopeNotRestoredModeChangedTest
			List<String> nonExcludedAttributeNames = (List<String>) getAttribute(
					BRIDGE_REQ_SCOPE_NON_EXCLUDED_ATTR_NAMES);

			if (nonExcludedAttributeNames != null) {

				for (String attributeName : nonExcludedAttributeNames) {
					currentRequestAttributes.remove(attributeName);

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
		List<String> attributesToRemove = new ArrayList<String>();
		Set<java.util.Map.Entry<String, Object>> entrySet = currentRequestAttributes.entrySet();

		// TCK TestPage045: excludedAttributesTest
		// TCK TestPage151: requestMapRequestScopeTest
		for (Map.Entry<String, Object> mapEntry : entrySet) {
			String attributeName = mapEntry.getKey();
			Object attributeValue = mapEntry.getValue();

			if (isExcludedRequestAttributeByConfig(attributeName, attributeValue) ||
					isExcludedRequestAttributeByAnnotation(attributeValue) ||
					isExcludedRequestAttributeByInstance(attributeName, attributeValue) ||
					isExcludedRequestAttributeByNamespace(attributeName)) {

				attributesToRemove.add(attributeName);
			}
		}

		// Remove the attributes.
		for (String attributeName : attributesToRemove) {
			currentRequestAttributes.remove(attributeName);
		}
	}

	/**
	 * This is a method-override that provides specific behavior for Liferay Portal. Specifically, since Liferay Portal
	 * does not implement the POST-REDIRECT-GET design pattern, not all instance types listed in Section 5.1.2 of the
	 * Spec can be candidates for exclusion.
	 */
	@Override
	protected boolean isExcludedRequestAttributeByInstance(String attributeName, Object attributeValue) {

		boolean excluded = false;

		if (attributeValue != null) {

			if ((attributeValue instanceof ExternalContext) || (attributeValue instanceof FacesContext)) {

				// Always safe to exclude when running under Liferay Portal.
				excluded = true;
			}
			else if (attributeValue instanceof PortletConfig) {

				// Liferay Portal includes request attribute named "javax.portlet.config" that must not be excluded. It
				// also includes an attribute named "javax.portlet.portlet" that is the current GenericFacesPortlet
				// (which extends GenericPortlet). But since GenericPortlet implements the PortletConfig interface, need
				// to prevent it from being excluded as well.
				if (!JAVAX_PORTLET_CONFIG.equals(attributeName) && !JAVAX_PORTLET_PORTLET.equals(attributeName)) {
					excluded = true;
				}
			}
			else if (attributeValue instanceof PortletRequest) {

				// Liferay Portal includes request attribute named "javax.portlet.request" that must not be excluded.
				if (!JAVAX_PORTLET_REQUEST.equals(attributeName)) {
					excluded = true;
				}
			}
			else if (attributeValue instanceof PortletResponse) {

				// Liferay Portal includes request attribute named "javax.portlet.response" that must not be excluded.
				if (!JAVAX_PORTLET_RESPONSE.equals(attributeName)) {
					excluded = true;
				}
			}
			else if ((attributeValue instanceof PortalContext) || (attributeValue instanceof PortletContext) ||
					(attributeValue instanceof PortletPreferences) || (attributeValue instanceof PortletSession)) {

				// Can only exclude attributes that are not Liferay objects. For example, Liferay Portal includes
				// a request attribute named "javax.portlet.request" that must not be excluded.
				if (!attributeValue.getClass().getName().startsWith(LiferayConstants.PACKAGE_NAMESPACE)) {
					excluded = true;
				}
			}
			else if ((attributeValue instanceof HttpSession) || (attributeValue instanceof ServletConfig) ||
					(attributeValue instanceof ServletContext) || (attributeValue instanceof ServletRequest) ||
					(attributeValue instanceof ServletResponse)) {

				// Can only exclude attributes that are not Liferay objects. For example, Liferay Portal includes
				// a request attribute named "com.liferay.portal.kernel.servlet.PortletServletRequest" that must not be
				// excluded.
				if (!attributeValue.getClass().getName().startsWith(LiferayConstants.PACKAGE_NAMESPACE)) {
					excluded = true;
				}
			}
		}

		return excluded;
	}

	/**
	 * This is a method-override that provides specific behavior for Liferay Portal. Specifically, since Liferay Portal
	 * does not implement the POST-REDIRECT-GET design pattern, not all instance types listed in Section 5.1.2 of the
	 * Spec can be candidates for exclusion.
	 */
	@Override
	@SuppressWarnings("deprecation")
	protected boolean isExcludedRequestAttributeByNamespace(String attributeName) {

		boolean excluded = false;

		if (isNamespaceMatch(attributeName, EXCLUDED_NAMESPACE_JAVAX_FACES) ||
				isNamespaceMatch(attributeName, EXCLUCED_NAMESPACE_JAVAX_SERVLET) ||
				isNamespaceMatch(attributeName, EXCLUCED_NAMESPACE_JAVAX_SERVLET_INCLUDE)) {

			// Always safe to exclude when running under Liferay Portal.
			excluded = true;
		}
		else if (isNamespaceMatch(attributeName, EXCLUDED_NAMESPACE_JAVAX_PORTLET_FACES)) {

			if (!Bridge.PORTLET_LIFECYCLE_PHASE.equals(attributeName) &&
					!BridgeExt.BRIDGE_CONTEXT_ATTRIBUTE.equals(attributeName)) {

				// The "javax.portlet.faces.phase" request attribute must never be excluded, as it is required by {@link
				// BridgeUtil#getPortletRequestPhase()}. And although the "javax.portlet.faces.bridgeContext" is
				// deprecated, it must not be removed either.
				excluded = true;
			}
		}
		else if (isNamespaceMatch(attributeName, EXCLUDED_NAMESPACE_JAVAX_PORTLET)) {

			// Never safe to exclude when running under Liferay Portal. For example, Liferay Portal includes
			// a request attribute named "javax.portlet.request" that must not be excluded.
			excluded = false;
		}

		return excluded;
	}
}
