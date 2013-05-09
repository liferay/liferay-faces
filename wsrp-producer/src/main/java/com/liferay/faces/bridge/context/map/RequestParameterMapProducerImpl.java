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
package com.liferay.faces.bridge.context.map;

import java.util.Map;

import javax.faces.render.ResponseStateManager;
import javax.portlet.PortletRequest;


/**
 * @author  Neil Griffin
 */
public class RequestParameterMapProducerImpl extends RequestParameterMapWrapper {

	// Private Constants
	private static final String JAVAX_FACES = "javax.faces";

	// Private Data Members
	private PortletRequest portletRequest;
	private Map<String, String> wrappedMap;

	public RequestParameterMapProducerImpl(Map<String, String> map, PortletRequest portletRequest) {
		this.wrappedMap = map;
		this.portletRequest = portletRequest;
	}

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
				// this method return true as well. Otherwise, try again by seeing if the parameter has a value. If it
				// does, then let this method return true.
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

					if (viewStateParam != null) {
						found = true;
					}
				}

				// Otherwise,
				else {

					// If the key starts with "javax.faces" then the previous lookup in the parameter-map within the
					// PortletRequest is good enough. The JSF implementation (and also the PrimeFaces
					// PrimePartialViewContext) will sometimes ask for request parameters with the "javax.faces" prefix
					// in the name. This is especially the case when a ResourceRequest is looking for resources.
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
	public Map<String, String> getWrapped() {
		return wrappedMap;
	}
}
