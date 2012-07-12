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
package com.liferay.faces.bridge.context.map;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.render.ResponseStateManager;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.BridgeExt;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.bridge.util.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestParameterMapImpl extends RequestParameterMap {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RequestParameterMapImpl.class);

	// Private Constants
	private static final String JAVAX_FACES = "javax.faces";
	private static final String COM_LIFERAY_FACES_BRIDGE = "com.liferay.faces.bridge";
	private static final String PRIMEFACES_DYNAMIC_CONTENT_PARAM = "pfdrid";

	// Private Data Members
	private BridgeContext bridgeContext;
	private FacesViewParameterMap facesViewParameterMap;
	private PortletContainer portletContainer;
	private PortletRequest portletRequest;
	private Map<String, String> preservedActionParameterMap;

	public RequestParameterMapImpl(BridgeContext bridgeContext) {
		this.bridgeContext = bridgeContext;
		this.portletContainer = bridgeContext.getPortletContainer();
		this.portletRequest = bridgeContext.getPortletRequest();
	}

	/**
	 * This method is an optimization override of the superclass.
	 */
	@Override
	public boolean containsKey(Object key) {

		// Assume that they key is not found.
		boolean found = false;

		// If the specified key has a valid value, then
		if (key != null) {

			// Determine whether or not the key is present in the parameter-map within the PortletRequest. This should
			// be a quick lookup (minimal performance impact).
			Map<String, String[]> parameterMap = portletRequest.getParameterMap();
			found = parameterMap.containsKey(key);

			if (!found) {

				// NOTE: If the parameterMap.containsKey(String) method call returned true, then trust that fact and let
				// this method return true as well. Otherwise, don't trust it! This might be a Liferay WSRP producer
				// portlet in which NamespaceServletRequest.getParameterMap().containsKey(String) erroneously returns
				// false. Just in case, try again by seeing if the parameter has a value. If it does, then let this
				// method return true.
				String value = portletRequest.getParameter((String) key);
				found = ((value != null) && (value.length() > 0));
			}

			// If the key was not present in the quick lookup, then
			if (!found) {

				String keyAsString = (String) key;

				// If the key is "javax.faces.ViewState" then avoid the performance impact of the superclass delegation
				// by handling this special case here.
				if (ResponseStateManager.VIEW_STATE_PARAM.equals(keyAsString)) {

					String viewStateParam = portletRequest.getParameter(ResponseStateManager.VIEW_STATE_PARAM);

					if (viewStateParam == null) {
						BridgeRequestScope bridgeRequestScope = bridgeContext.getBridgeRequestScope();

						if (bridgeRequestScope != null) {
							viewStateParam = bridgeRequestScope.getPreservedViewStateParam();

							if (viewStateParam != null) {
								found = true;
							}
						}
					}
					else {
						found = true;
					}
				}

				// Otherwise,
				else {

					// If the key starts with "javax.faces" then the previous lookup in the parameter-map within the
					// PortletRequest is good enough. The JSF implementation (and also the PrimeFaces
					// PrimePartialViewContext) will sometimes ask for request parameters with the "javax.faces" prefix
					// in the name. This is especially the case when a ResourceRequest is looking for JSF2 resources.
					if (keyAsString.startsWith(JAVAX_FACES)) {
						// nothing to do -- just here for comments readability.
					}

					// Otherwise, delegate to the superclass. This should only be done if absolutely necessary, since
					// the superclass iterates through an enumeration of names which has a performance impact.
					else {
						found = super.containsKey(key);
					}
				}
			}
		}

		return found;
	}

	@Override
	protected AbstractPropertyMapEntry<String> createPropertyMapEntry(String name) {
		return new RequestParameterMapEntry(portletContainer, name);
	}

	@Override
	protected void removeProperty(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The FacesViewParameterMap needs to be lazy-initialized because it might throw a BridgeInvalidViewPathException
	 * exception, as in the case of the TCK portletSetsInvalidViewPathTest.
	 */
	protected FacesViewParameterMap getFacesViewParameterMap() {

		if (facesViewParameterMap == null) {
			facesViewParameterMap = new FacesViewParameterMap(bridgeContext);
		}

		return facesViewParameterMap;
	}

	/**
	 * The PreservedActionParameterMap needs to be lazy-initialized because it relies on the BridgeRequestScope which
	 * isn't available during the RESOURCE_PHASE.
	 */
	protected Map<String, String> getPreservedActionParameterMap() {

		if (preservedActionParameterMap == null) {
			BridgeRequestScope bridgeRequestScope = bridgeContext.getBridgeRequestScope();

			if (bridgeRequestScope != null) {
				preservedActionParameterMap = bridgeContext.getBridgeRequestScope().getPreservedActionParameterMap();
			}
		}

		return preservedActionParameterMap;
	}

	/**
	 * This method returns the value of the specified parameter name according to the current portlet request.
	 */
	@Override
	protected String getProperty(String name) {

		String value = null;

		if (name != null) {
			value = portletContainer.getRequestParameter(name);

			if (value == null) {

				if (ResponseStateManager.RENDER_KIT_ID_PARAM.equals(name)) {

					// If not found in the request, Section 6.9 of the Bridge spec requires that the value of the
					// ResponseStateManager.RENDER_KIT_ID_PARAM request parameter be set to the value of the
					// "javax.portlet.faces.<portletName>.defaultRenderKitId" PortletContext attribute.
					value = bridgeContext.getDefaultRenderKitId();
				}
				else if (ResponseStateManager.VIEW_STATE_PARAM.equals(name)) {

					BridgeRequestScope bridgeRequestScope = bridgeContext.getBridgeRequestScope();

					if (bridgeRequestScope != null) {
						value = bridgeRequestScope.getPreservedViewStateParam();
					}
				}
				else if (name.startsWith(COM_LIFERAY_FACES_BRIDGE)) {

					// If the value wasn't found in the PortletRequest, then it won't be found elsewhere.
					value = null;
				}
				else if (name.equals(BridgeExt.FACES_AJAX_PARAMETER)) {

					// If the value wasn't found in the PortletRequest, then it won't be found elsewhere.
					value = null;
				}
				else if (PRIMEFACES_DYNAMIC_CONTENT_PARAM.equals(name)) {

					// If the value wasn't found in the PortletRequest, then it won't be found elsewhere.
					value = null;
				}
				else {

					Map<String, String> preservedActionParamMap = getPreservedActionParameterMap();

					if (preservedActionParamMap != null) {
						value = preservedActionParamMap.get(name);
					}

					if (value == null) {
						value = getFacesViewParameterMap().get(name);
					}
				}
			}
		}

		logger.trace("{0}=[{1}]", name, value);

		return value;
	}

	@Override
	protected void setProperty(String name, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Enumeration<String> getPropertyNames() {

		// Note: This can't be cached because the caller basically wants a new enumeration to iterate over each time.
		Enumeration<String> parameterNames;

		String renderKitIdParam = portletRequest.getParameter(ResponseStateManager.RENDER_KIT_ID_PARAM);

		if (renderKitIdParam == null) {
			renderKitIdParam = bridgeContext.getDefaultRenderKitId();
		}

		List<String> requestParamerNameList = Collections.list(portletRequest.getParameterNames());

		// Section 6.9 of the Bridge spec requires that a parameter name be added to the return value of
		// ExternalContext.getRequestParameterNames() for ResponseStateManager.RENDER_KIT_ID_PARAM.
		if (renderKitIdParam != null) {
			requestParamerNameList.add(ResponseStateManager.RENDER_KIT_ID_PARAM);
		}

		// If the "javax.faces.ViewState" parameter was preserved in the BridgeRequestScope, then add it to the return
		// value list of names.
		String viewStateParam = portletRequest.getParameter(ResponseStateManager.VIEW_STATE_PARAM);

		if (viewStateParam == null) {
			BridgeRequestScope bridgeRequestScope = bridgeContext.getBridgeRequestScope();

			if (bridgeRequestScope != null) {
				viewStateParam = bridgeRequestScope.getPreservedViewStateParam();

				if (viewStateParam != null) {
					requestParamerNameList.add(ResponseStateManager.VIEW_STATE_PARAM);
				}
			}
		}

		Map<String, String> preservedActionParamMap = getPreservedActionParameterMap();

		if (preservedActionParamMap != null) {
			Set<String> keySet = preservedActionParamMap.keySet();

			for (String key : keySet) {
				requestParamerNameList.add(key);
			}
		}

		Set<String> keySet = getFacesViewParameterMap().keySet();

		for (String key : keySet) {
			requestParamerNameList.add(key);
		}

		parameterNames = Collections.enumeration(requestParamerNameList);

		return parameterNames;
	}
}
