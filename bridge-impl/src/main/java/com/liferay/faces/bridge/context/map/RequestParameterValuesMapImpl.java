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
package com.liferay.faces.bridge.context.map;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.render.ResponseStateManager;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestParameterValuesMapImpl extends RequestParameterValuesMap {

	// Private Data Members
	private BridgeContext bridgeContext;
	private FacesViewParameterMap facesViewParameterMap;
	private PortletContainer portletContainer;
	private PortletRequest portletRequest;
	private Map<String, String> preservedActionParameterMap;

	public RequestParameterValuesMapImpl(BridgeContext bridgeContext) {
		this.bridgeContext = bridgeContext;
		this.portletContainer = bridgeContext.getPortletContainer();
		this.portletRequest = bridgeContext.getPortletRequest();
	}

	@Override
	protected AbstractPropertyMapEntry<String[]> createPropertyMapEntry(String name) {
		return new RequestParameterValuesMapEntry(portletContainer, name);
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
	protected String[] getProperty(String name) {

		String[] values = portletContainer.getRequestParameterValues(name);

		if (values == null) {

			if (ResponseStateManager.RENDER_KIT_ID_PARAM.equals(name)) {

				// If not found in the request, Section 6.9 of the Bridge spec requires that the value of the
				// ResponseStateManager.RENDER_KIT_ID_PARAM request parameter be set to the value of the
				// "javax.portlet.faces.<portletName>.defaultRenderKitId" PortletContext attribute.
				String defaultRenderKitId = bridgeContext.getDefaultRenderKitId();

				if (defaultRenderKitId != null) {
					values = new String[] { defaultRenderKitId };
				}
			}
			else if (ResponseStateManager.VIEW_STATE_PARAM.equals(name)) {
				BridgeRequestScope bridgeRequestScope = bridgeContext.getBridgeRequestScope();

				if (bridgeRequestScope != null) {
					String viewStateParam = bridgeRequestScope.getPreservedViewStateParam();

					if (viewStateParam != null) {
						values = new String[] { viewStateParam };
					}
				}
			}
			else {

				Map<String, String> preservedActionParamMap = getPreservedActionParameterMap();

				if (preservedActionParamMap != null) {
					String value = preservedActionParamMap.get(name);

					if (value != null) {
						values = new String[] { value };
					}
				}

				if (values == null) {
					String value = getFacesViewParameterMap().get(name);

					if (value != null) {
						values = new String[] { value };
					}
				}
			}
		}

		return values;
	}

	@Override
	protected void setProperty(String name, String[] value) {
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

		Map<String, String> preservedActionParamMap = getPreservedActionParameterMap();

		if (preservedActionParamMap != null) {
			Set<String> keySet = preservedActionParamMap.keySet();

			for (String key : keySet) {
				requestParamerNameList.add(key);
			}
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

		Set<String> keySet = getFacesViewParameterMap().keySet();

		for (String key : keySet) {
			requestParamerNameList.add(key);
		}

		parameterNames = Collections.enumeration(requestParamerNameList);

		return parameterNames;
	}
}
