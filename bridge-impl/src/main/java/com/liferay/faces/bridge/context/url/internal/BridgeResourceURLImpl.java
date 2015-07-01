/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.url.internal;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.BaseURL;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeResourceURL;
import com.liferay.faces.bridge.context.url.BridgeURI;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeResourceURLImpl extends BridgeURLBase implements BridgeResourceURL {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeResourceURLImpl.class);

	// Private Constants
	private static final String ENCODED_RESOURCE_TOKEN = "javax.faces.resource=";

	// Private Pseudo-Constants
	private static Set<String> EXCLUDED_PARAMETER_NAMES = new HashSet<String>();

	static {
		EXCLUDED_PARAMETER_NAMES.add(Bridge.PORTLET_MODE_PARAMETER);
		EXCLUDED_PARAMETER_NAMES.add(Bridge.PORTLET_SECURE_PARAMETER);
		EXCLUDED_PARAMETER_NAMES.add(Bridge.PORTLET_WINDOWSTATE_PARAMETER);
	}

	// Private Data Members
	private boolean inProtocol;
	private boolean viewLink;

	public BridgeResourceURLImpl(BridgeContext bridgeContext, BridgeURI bridgeURI, String viewId) {
		super(bridgeContext, bridgeURI, viewId);
	}

	public void replaceBackLinkParameter(FacesContext facesContext) {

		String backLinkViewId = facesContext.getViewRoot().getViewId();
		String backLinkURL = facesContext.getApplication().getViewHandler().getActionURL(facesContext, backLinkViewId);
		String backLinkEncodedActionURL = "";

		try {
			ExternalContext externalContext = facesContext.getExternalContext();
			backLinkEncodedActionURL = URLEncoder.encode(externalContext.encodeActionURL(backLinkURL), "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}

		String newParamName = removeParameter(Bridge.BACK_LINK);
		setParameter(newParamName, backLinkEncodedActionURL);
	}

	@Override
	protected BaseURL toBaseURL() throws MalformedURLException {

		BaseURL baseURL;
		String uri = bridgeURI.toString();

		// If the URL is opaque, meaning it starts with something like "portlet:" or "mailto:" and
		// doesn't have the double-forward-slash like "http://" does, then
		if (bridgeURI.isOpaque()) {

			// If the specified URL starts with "portlet:", then return a BaseURL that contains the modified
			// parameters. This will be a URL that represents navigation to a different viewId.
			if (bridgeURI.isPortletScheme()) {

				// TCK TestPage005: modeViewIDTest
				// TCK TestPage042: requestRenderIgnoresScopeViaCreateViewTest
				// TCK TestPage043: requestRenderRedisplayTest
				// TCK TestPage044: requestRedisplayOutOfScopeTest
				// TCK TestPage049: renderRedirectTest
				// TCK TestPage050: ignoreCurrentViewIdModeChangeTest
				// TCK TestPage051: exceptionThrownWhenNoDefaultViewIdTest
				String portletMode = getParameter(Bridge.PORTLET_MODE_PARAMETER);
				boolean modeChanged = ((portletMode != null) && (portletMode.length() > 0));
				String security = getParameter(Bridge.PORTLET_SECURE_PARAMETER);
				String windowState = getParameter(Bridge.PORTLET_WINDOWSTATE_PARAMETER);
				String urlWithModifiedParameters = _toString(modeChanged);
				Bridge.PortletPhase urlPortletPhase = bridgeURI.getPortletPhase();

				if (urlPortletPhase == Bridge.PortletPhase.ACTION_PHASE) {
					baseURL = createActionURL(urlWithModifiedParameters);
				}
				else if (urlPortletPhase == Bridge.PortletPhase.RENDER_PHASE) {
					baseURL = createRenderURL(urlWithModifiedParameters);
				}
				else {
					baseURL = createResourceURL(urlWithModifiedParameters);
				}

				// If the URL string is self-referencing, meaning, it targets the current Faces view, then copy the
				// render parameters from the current PortletRequest to the BaseURL. NOTE: This has the added benefit of
				// copying the bridgeRequestScopeId render parameter, which will preserve the BridgeRequestScope if the
				// user clicks on the link (invokes the BaseURL).
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
				setSecureParameter(security, baseURL);
			}

			// Otherwise, return the a BaseURL string representation (unmodified value) as required by the Bridge Spec.
			else {

				// TCK TestPage128: encodeResourceURLOpaqueTest
				baseURL = new BaseURLNonEncodedStringImpl(uri, getParameterMap());
			}
		}

		// Otherwise, if the URL is identified by the ResourceHandler as a JSF2 resource URL, then
		else if ((uri != null) && (uri.contains("javax.faces.resource"))) {

			// If the URL has already been encoded, then return the URL string unmodified.
			if (uri.indexOf(ENCODED_RESOURCE_TOKEN) > 0) {

				// FACES-63: Prevent double-encoding of resource URLs
				baseURL = new BaseURLNonEncodedStringImpl(uri, getParameterMap());
			}

			// Otherwise, return a ResourceURL that can retrieve the JSF2 resource.
			else {
				baseURL = createResourceURL(uri);
			}
		}

		// Otherwise, if the URL is external, then return an encoded BaseURL string representation of the URL.
		else if (bridgeURI.isExternal()) {

			// TCK TestPage130: encodeResourceURLForeignExternalURLBackLinkTest
			baseURL = new BaseURLEncodedExternalStringImpl(uri, getParameterMap(), bridgeContext.getPortletResponse());
		}

		// Otherwise, if the URL is relative, in that it starts with "../", then return a BaseURL string representation
		// of the URL that contains the context-path.
		else if (bridgeURI.isPathRelative()) {

			// TCK TestPage131: encodeResourceURLRelativeURLTest
			// TCK TestPage132: encodeResourceURLRelativeURLBackLinkTest
			baseURL = new BaseURLRelativeStringImpl(uri, getParameterMap(), bridgeContext);
		}

		// Otherwise, if the URL originally contained the "javax.portlet.faces.ViewLink" which represents navigation
		// to a different Faces view, then
		else if (viewLink) {

			String urlWithModifiedParameters = _toString(false, EXCLUDED_PARAMETER_NAMES);
			String portletMode = getParameter(Bridge.PORTLET_MODE_PARAMETER);
			String windowState = getParameter(Bridge.PORTLET_WINDOWSTATE_PARAMETER);
			boolean secure = BooleanHelper.toBoolean(getParameter(Bridge.PORTLET_SECURE_PARAMETER));

			// If the URL targets a Faces viewId, then return a PortletURL (Action URL) that targets the view with the
			// appropriate PortletMode, WindowState, and Security settings built into the URL. For more info, see
			// JavaDoc comments for {@link Bridge#VIEW_LINK}.
			if (isFacesViewTarget()) {

				// TCK TestPage135: encodeResourceURLViewLinkTest
				// TCK TestPage136: encodeResourceURLViewLinkWithBackLinkTest
				PortletURL actionURL = createActionURL(urlWithModifiedParameters);
				baseURL = new PortletURLFacesTargetActionImpl(actionURL, portletMode, windowState, secure);
			}

			// Otherwise, return a PortletURL (Render URL) that contains the "_jsfBridgeNonFacesView" render parameter,
			// which is a signal to the GenericFacesPortlet to dispatch to this non-Faces target when the URL is
			// requested. Note that this seems to be a use-case that is contradictory with the JavaDoc for
			// Brige#VIEW_LINK which claims navigation to a different view. But there are a number of tests in the TCK
			// that utilize this (see below).
			else {

				// TCK TestPage097: encodeActionURLNonJSFViewRenderTest
				// TCK TestPage098: encodeActionURLNonJSFViewWithParamRenderTest
				// TCK TestPage099: encodeActionURLNonJSFViewWithModeRenderTest
				// TCK TestPage100: encodeActionURLNonJSFViewWithInvalidModeRenderTest
				// TCK TestPage101: encodeActionURLNonJSFViewWithWindowStateRenderTest
				// TCK TestPage102: encodeActionURLNonJSFViewWithInvalidWindowStateRenderTest
				// TCK TestPage103: encodeActionURLNonJSFViewResourceTest
				// TCK TestPage104: encodeActionURLNonJSFViewWithParamResourceTest
				// TCK TestPage105: encodeActionURLNonJSFViewWithModeResourceTest
				// TCK TestPage106: encodeActionURLNonJSFViewWithInvalidModeResourceTest
				// TCK TestPage107: encodeActionURLNonJSFViewWithWindowStateResourceTest
				// TCK TestPage108: encodeActionURLNonJSFViewWithInvalidWindowStateResourceTest
				PortletURL renderURL = createRenderURL(urlWithModifiedParameters);
				baseURL = new PortletURLNonFacesTargetRenderImpl(renderURL, portletMode, windowState, secure,
						bridgeURI.getPath());
			}
		}

		// Otherwise, if the URL targets a Faces viewId, then return a ResourceURL that targets the view.
		else if (isFacesViewTarget()) {

			// TCK TestPage073: scopeAfterRedisplayResourcePPRTest
			// TCK TestPage121: encodeActionURLJSFViewResourceTest
			// TCK TestPage122: encodeActionURLWithParamResourceTest
			// TCK TestPage123: encodeActionURLWithModeResourceTest
			// TCK TestPage124: encodeActionURLWithInvalidModeResourceTest
			// TCK TestPage125: encodeActionURLWithWindowStateResourceTest
			// TCK TestPage126: encodeActionURLWithInvalidWindowStateResourceTest
			// TCK TestPage127: encodeURLEscapingTest
			// TCK TestPage137: encodeResourceURLWithModeTest
			String urlWithModifiedParameters = _toString(false, EXCLUDED_PARAMETER_NAMES);
			baseURL = createResourceURL(urlWithModifiedParameters);
		}

		// Otherwise, if the bridge must encode the URL to satisfy "in-protocol" resource serving, then return a
		// an appropriate ResourceURL.
		else if (inProtocol) {

			// TCK TestPage071: nonFacesResourceTest
			String urlWithModifiedParameters = _toString(false);
			ResourceURL resourceURL = createResourceURL(urlWithModifiedParameters);
			String contextPath = bridgeContext.getPortletRequest().getContextPath();
			resourceURL.setResourceID(bridgeURI.getContextRelativePath(contextPath));
			baseURL = resourceURL;
		}

		// Otherwise, assume that the URL is for an resource external to the portlet context like
		// "/portalcontext/resources/foo.png" and return a BaseURL string representation of it.
		else {

			// TCK TestPage133: encodeResourceURLTest
			baseURL = new BaseURLEncodedExternalStringImpl(uri, getParameterMap(), bridgeContext.getPortletResponse());
		}

		return baseURL;
	}

	public void setInProtocol(boolean inProtocol) {
		this.inProtocol = inProtocol;
	}

	public void setViewLink(boolean viewLink) {
		this.viewLink = viewLink;
	}
}
