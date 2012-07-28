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
package com.liferay.faces.bridge.context.url;

import java.net.MalformedURLException;

import javax.portlet.BaseURL;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.Bridge.PortletPhase;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.helper.BooleanHelper;


/**
 * @author  Neil Griffin
 */
public class BridgeActionURLImpl extends BridgeResponseURLImpl implements BridgeActionURL {

	// Private Data Members
	private PortletContainer portletContainer;
	private PortletRequest portletRequest;

	public BridgeActionURLImpl(String url, String currentFacesViewId, BridgeContext bridgeContext) {
		super(url, currentFacesViewId, bridgeContext);
		this.portletRequest = bridgeContext.getPortletRequest();
		this.portletContainer = bridgeContext.getPortletContainer();
	}

	@Override
	protected BaseURL toBaseURL() throws MalformedURLException {

		BaseURL baseURL = null;

		// If this is executing during the ACTION_PHASE of the portlet lifecycle, then
		PortletPhase portletRequestPhase = BridgeUtil.getPortletRequestPhase();
		if (portletRequestPhase == Bridge.PortletPhase.ACTION_PHASE) {

			// The Mojarra MultiViewHandler.getResourceURL(String) method is implemented in such a way that it calls
			// ExternalContext.encodeActionURL(ExternalContext.encodeResourceURL(url)). The return value of those calls
			// will ultimately be passed to the ExternalContext.redirect(String) method. For this reason, need to return
			// a simple string-based representation of the URL.
			baseURL = new BaseURLNonEncodedStringImpl(url, getParameterMap());
		}

		// Otherwise,
		else {

			// If the URL string starts with "http" then assume that it has already been encoded and simply return the
			// URL string.
			if (url.startsWith("http")) {
				baseURL = new BaseURLNonEncodedStringImpl(url, getParameterMap());
			}

			// Otherwise, if the URL string starts with a "#" character, or it's an absolute URL that is external to
			// this portlet, then simply return the URL string as required by the Bridge Spec.
			else if (url.startsWith(BridgeConstants.CHAR_POUND) || (isAbsolute() && isExternal())) {
				
				// TCK TestPage084: encodeActionURLPoundCharTest
				baseURL = new BaseURLNonEncodedStringImpl(url, getParameterMap());
			}

			// Otherwise, if the URL string has a "javax.portlet.faces.DirectLink" parameter with a value of "true",
			// then return an absolute path (to the path in the URL string) as required by the Bridge Spec.
			else if (isExternal() || BooleanHelper.isTrueToken(getParameter(Bridge.DIRECT_LINK))) {
				baseURL = new BaseURLDirectStringImpl(url, getParameterMap(), getURI().getPath(), portletRequest);
			}
			else {
				String portletMode = removeParameter(Bridge.PORTLET_MODE_PARAMETER);
				boolean modeChanged = ((portletMode != null) && (portletMode.length() > 0));
				String secure = removeParameter(Bridge.PORTLET_SECURE_PARAMETER);
				String windowState = removeParameter(Bridge.PORTLET_WINDOWSTATE_PARAMETER);

				// Note: If the URL string starts with "portlet:", then the type of URL the portlet container creates is
				// determined by what follows the scheme, such as "portlet:action" "portlet:render" and
				// "portlet:resource".
				if (isPortletScheme()) {
					String urlWithModifiedParameters = _toString(modeChanged);
					Bridge.PortletPhase urlPortletPhase = getPortletPhase();

					if (urlPortletPhase == Bridge.PortletPhase.ACTION_PHASE) {
						baseURL = portletContainer.createActionURL(urlWithModifiedParameters);
					}
					else if (urlPortletPhase == Bridge.PortletPhase.RENDER_PHASE) {
						baseURL = portletContainer.createRenderURL(urlWithModifiedParameters);
					}
					else {
						baseURL = portletContainer.createResourceURL(urlWithModifiedParameters);
					}
				}
				else {
					String urlWithModifiedParameters = _toString(modeChanged);
					
					if (portletRequestPhase == Bridge.PortletPhase.EVENT_PHASE) {
						baseURL = new BaseURLNonEncodedStringImpl(urlWithModifiedParameters);
					}
					else {
						baseURL = portletContainer.createActionURL(urlWithModifiedParameters);
					}
				}

				// If the URL string is self-referencing, meaning, it targets the current Faces view, then copy the
				// render parameters from the current PortletRequest to the BaseURL.
				if (isSelfReferencing()) {
					setRenderParameters(baseURL);
				}

				// If the portlet container created a PortletURL, then apply the PortletMode and WindowState to the
				// PortletURL.
				if (baseURL instanceof PortletURL) {

					PortletURL portletURL = (PortletURL) baseURL;
					setPortletModeParameter(portletMode, portletURL);
					setWindowStateParameter(windowState, portletURL);
				}

				// Apply the security.
				setSecureParameter(secure, baseURL);
			}
		}

		return baseURL;
	}

}
