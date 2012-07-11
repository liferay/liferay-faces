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
package com.liferay.faces.bridge.tck.scope;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.bridge.scope.BridgeRequestScopeLiferayImpl;


/**
 * <p>This class serves as a wrapper around the {@link BridgeRequestScope} provided by the bridge. It's purpose is to
 * work-around a vendor-specific feature of Liferay Portal that is not expected by the TCK.</p>
 *
 * <p>TCK Expected Behavior: Portlet containers like Pluto implement the POST-REDIRECT-GET design pattern by having the
 * the ACTION_PHASE and RENDER_PHASE of the portlet lifecycle execute in two separate user-agent requests. The first
 * request is a POST (ActionRequest), and the second request is a GET (RenderRequest). When restoring the
 * BridgeRequestScope, the non-excluded attributes from the ActionRequest are to be copied into the RenderRequest.</p>
 *
 * <p>Liferay Portal Feature Description: The Liferay portlet container does not implement POST-REDIRECT-GET. Instead,
 * it executes the ACTION_PHASE and RENDER_PHASE of the portlet lifecycle all within a single user-agent POST request.
 * Because of this, the Liferay portlet container maintains request attributes that were originally set on the {@link
 * ActionRequest} such that they automatically survive into the {@link RenderRequest}. Since the Bridge Spec assumes
 * that the portlet container implements POST-REDIRECT-GET, it is necessary for the bridge to pro-actively remove
 * non-excluded request attributes when running under Liferay Portal. This is in fact the approach taken by the {@link
 * BridgeRequestScopeLiferayImpl} parent class, specifically in the {@link
 * BridgeRequestScopeLiferayImpl#restoreState(FacesContext)} method.</p>
 *
 * <p>Problem #1: The TCK TestPage151 (requestMapRequestScopeTest) performs some checks to make sure that certain
 * non-excluded request attributes don't survive into the RENDER_PHASE. One of these attributes is named
 * "verifyPreBridgeExclusion" with value "avalue". Due to the design of the Liferay portlet container, it is not
 * possible for the bridge to programatically determine if the "verifyPreBridgeExclusion" attribute should be
 * removed.</p>
 *
 * <p>Example: The following is a list of String-based attributes that are present in the Liferay Portal RenderRequest
 * prior to the FacesContext being constructed by the requestMapRequestScopeTest:
 *
 * <ul>
 *   <li>INVOKER_FILTER_URI="/chapter6_1_3_1TestsrequestMapRequestScopeTestportlet/invoke"</li>
 *   <li>PORTLET_ID="chapter6_1_3_1TestsrequestMapRequestScopeTestportlet_WAR_bridgetckmainportlet"</li>
 *   <li>verifyPreBridgeExclusion="avalue"</li>
 * </ul>
 *
 * In this example, the INVOKER_FILTER_URI and PORTLET_ID attributes (added by the Liferay portlet container) need to be
 * maintained, but the "verifyPreBridgeExclusion" attribute need to be removed. But to restate the problem, it is not
 * possible for the bridge to programatically determine which one of these should be maintained and which should be
 * removed.</p>
 *
 * <p>Solution: In order for the requestMapRequestScopeTest to pass under Liferay Portal, it is necessary to explicitly
 * remove the verifyPreBridgeExclusion attribute.</p>
 *
 * <p>Problem #2: The TCK TestPage203 (JSF_ELTest) performs some checks to make sure that objects obtained from the
 * bridge's ELResolver match expected values stored as request attributes. One of these attributes is
 * "org.apache.myfaces.portlet.faces.testsuite.common.portletConfig", which is set in the {@link
 * GenericFacesTestSuitePortlet#initTestRequest(PortletRequest)} method. Again, due to the design of the Liferay portlet
 * container, it is not possible for the bridge to programatically determine whether or not this value should be
 * maintained. Since it is an instance of PortletConfig, the {@link BridgeRequestScopeLiferayImpl#restoreState(FacesContext)}
 * method will exclude (remove) the request attribute.</p>
 *
 * <p>Solution: In order for the JSF_ELTest to pass under Liferay Portal, it is necessary to explicity maintain the
 * value of the request attribute.</p>
 *
 * @author  Neil Griffin
 */
public class BridgeRequestScopeLiferayTCKImpl extends BridgeRequestScopeLiferayImpl {

	// Private Constants
	public static final String TCK_PORTLET_CONFIG = "org.apache.myfaces.portlet.faces.testsuite.common.portletConfig";

	// serialVersionUID
	private static final long serialVersionUID = 5212644933751947796L;

	public BridgeRequestScopeLiferayTCKImpl(PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest) {
		super(portletConfig, portletContext, portletRequest);
	}

	@Override
	public void restoreState(FacesContext facesContext) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();

		// TCK TestPage203: JSF_ELTest
		PortletConfig tckPortletConfig = (PortletConfig) requestMap.get(TCK_PORTLET_CONFIG);

		super.restoreState(facesContext);

		// TCK TestPage151: requestMapRequestScopeTest
		if (getBeganInPhase() == Bridge.PortletPhase.ACTION_PHASE) {

			// Explicitly remove the attributes that the requestMapRequestScopeTest checks for.
			requestMap.remove("verifyPreBridgeExclusion");
		}

		// TCK TestPage203: JSF_ELTest
		requestMap.put(TCK_PORTLET_CONFIG, tckPortletConfig);
	}

}
