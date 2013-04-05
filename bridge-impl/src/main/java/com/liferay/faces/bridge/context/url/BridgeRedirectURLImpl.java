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
package com.liferay.faces.bridge.context.url;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.portlet.BaseURL;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public class BridgeRedirectURLImpl extends BridgeResponseURLImpl implements BridgeRedirectURL {

	// Private Data Members
	Map<String, List<String>> parameters;

	public BridgeRedirectURLImpl(String url, Map<String, List<String>> parameters, String currentFacesViewId,
		BridgeContext bridgeContext) {
		super(url, currentFacesViewId, bridgeContext);
		this.parameters = parameters;
	}

	@Override
	protected BaseURL toBaseURL() throws MalformedURLException {

		BaseURL baseURL = null;

		// If this is executing during the ACTION_PHASE of the portlet lifecycle, then
		if (BridgeUtil.getPortletRequestPhase() == Bridge.PortletPhase.ACTION_PHASE) {

			// TCK NOTE: The only use-case in which the TCK will invoke this method is
			// TestPage039-requestNoScopeOnRedirectTest. During the test, this method will be called when a <redirect/>
			// is encountered in a navigation-rule during the ACTION_PHASE of the portlet lifecycle. When this happens,
			// the navigation-handler will attempt to call ViewHandler#getResourceURL(String). The Mojarra
			// MultiViewHandler.getResourceURL(String) method is implemented in such a way that it calls
			// ExternalContext.encodeActionURL(ExternalContext.encodeResourceURL(url)). The return value of those calls
			// will ultimately be passed to the ExternalContext.redirect(String) method. For this reason, need to return
			// a simple string-based representation of the URL.
			baseURL = new BaseURLNonEncodedStringImpl(url, getParameterMap());
		}

		// Otherwise,
		else {

			// So far, under all circumstances it seems appropriate to return a simple string-based representation of
			// the URL. This is the same code as above but keep it this way for now for TCK documentation purposes.
			baseURL = new BaseURLNonEncodedStringImpl(url, getParameterMap());
		}

		return baseURL;
	}

}
