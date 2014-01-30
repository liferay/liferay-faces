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

import java.util.Map;

import javax.faces.render.ResponseStateManager;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.util.map.AbstractPropertyMap;


/**
 * This is simply a marker abstract class that represents a map of request parameters.
 */
public abstract class RequestParameterMap extends AbstractPropertyMap<String> {

	// Public Constants
	public static final String PARAM_UPLOADED_FILES = "com.liferay.faces.bridge.UPLOADED_FILES";

	// Private Constants
	private static final String JAVAX_FACES = "javax.faces";

	// Protected Data Members
	protected BridgeContext bridgeContext;

	public RequestParameterMap(BridgeContext bridgeContext) {
		this.bridgeContext = bridgeContext;
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
			Map<String, String[]> parameterMap = getRequestParameterMap();
			found = parameterMap.containsKey(key);

			if (!found) {

				// NOTE: If the parameterMap.containsKey(String) method call returned true, then trust that fact and let
				// this method return true as well. Otherwise, don't trust it! This might be a Liferay WSRP producer
				// portlet in which NamespaceServletRequest.getParameterMap().containsKey(String) erroneously returns
				// false. Just in case, try again by seeing if the parameter has a value. If it does, then let this
				// method return true.
				String value = getRequestParameter((String) key);
				found = ((value != null) && (value.length() > 0));
			}

			// If the key was not present in the quick lookup, then
			if (!found) {

				String keyAsString = (String) key;

				// If the key is "javax.faces.ViewState" then avoid the performance impact of the superclass delegation
				// by handling this special case here.
				if (ResponseStateManager.VIEW_STATE_PARAM.equals(keyAsString)) {

					String viewStateParam = getRequestParameter(ResponseStateManager.VIEW_STATE_PARAM);

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

	protected abstract String getRequestParameter(String name);

	protected abstract Map<String, String[]> getRequestParameterMap();
}
