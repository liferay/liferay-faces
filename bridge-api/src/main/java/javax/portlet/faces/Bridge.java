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
package javax.portlet.faces;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;


/**
 * @author  Neil Griffin
 */
public interface Bridge {

	// Public Constants
	public static final String AFTER_VIEW_CONTENT = "javax.portlet.faces.AfterViewContent";
	public static final String BACK_LINK = "javax.portlet.faces.BackLink";
	public static final String BRIDGE_EVENT_HANDLER = "bridgeEventHandler";
	public static final String BRIDGE_PACKAGE_PREFIX = "javax.portlet.faces.";
	public static final String BRIDGE_PUBLIC_RENDER_PARAMETER_HANDLER = "bridgePublicRenderParameterHandler";
	public static final String DEFAULT_RENDERKIT_ID = "defaultRenderKitId";
	public static final String DEFAULT_VIEWID_MAP = "defaultViewIdMap";
	public static final String DIRECT_LINK = "javax.portlet.faces.DirectLink";
	public static final String EXCLUDED_REQUEST_ATTRIBUTES = "excludedRequestAttributes";
	public static final String FACES_USE_CURRENT_VIEW_PARAMETER = "_jsfBridgeCurrentView";
	public static final String FACES_VIEW_ID_PARAMETER = "_jsfBridgeViewId";
	public static final String FACES_VIEW_PATH_PARAMETER = "_jsfBridgeViewPath";
	public static final String IN_PROTOCOL_RESOURCE_LINK = "javax.portlet.faces.InProtocolResourceLink";
	public static final String IS_POSTBACK_ATTRIBUTE = "javax.portlet.faces.isPostback";
	public static final String MAX_MANAGED_REQUEST_SCOPES = "javax.portlet.faces.MAX_MANAGED_REQUEST_SCOPES";
	public static final String NONFACES_TARGET_PATH_PARAMETER = "_jsfBridgeNonFacesView";
	public static final String PORTLET_LIFECYCLE_PHASE = "javax.portlet.faces.phase";
	public static final String PORTLET_MODE_PARAMETER = "javax.portlet.faces.PortletMode";
	public static final String PORTLET_NAMESPACED_RESPONSE_PROPERTY = "X-JAVAX-PORTLET-FACES-NAMESPACED-RESPONSE";
	public static final String PORTLET_SECURE_PARAMETER = "javax.portlet.faces.Secure";
	public static final String PORTLET_WINDOWSTATE_PARAMETER = "javax.portlet.faces.WindowState";
	public static final String PRESERVE_ACTION_PARAMS = "preserveActionParams";
	public static final String RENDER_CONTENT_AFTER_VIEW = "javax.portlet.faces.RenderContentAfterView";
	public static final String RENDER_POLICY = "javax.portlet.faces.RENDER_POLICY";
	public static final String SAVESTATE_FIELD_MARKER = "javax.portlet.faces.SAVESTATE_FIELD_MARKER";
	public static final String VIEW_ID = "javax.portlet.faces.viewId";
	public static final String VIEW_LINK = "javax.portlet.faces.ViewLink";
	public static final String VIEW_PATH = "javax.portlet.faces.viewPath";
	public static final String VIEWID_HISTORY = "javax.portlet.faces.viewIdHistory";

	/**
	 * @author  Neil Griffin
	 */
	public static enum PortletPhase {
		ACTION_PHASE, EVENT_PHASE, RENDER_PHASE, RESOURCE_PHASE
	}

	/**
	 * @author  Neil Griffin
	 */
	public static enum BridgeRenderPolicy {
		DEFAULT, ALWAYS_DELEGATE, NEVER_DELEGATE,
	}

	public void destroy();

	public void doFacesRequest(ActionRequest actionRequest, ActionResponse actionResponse)
		throws BridgeDefaultViewNotSpecifiedException, BridgeUninitializedException, BridgeException;

	public void doFacesRequest(EventRequest eventRequest, EventResponse eventResponse)
		throws BridgeUninitializedException, BridgeException;

	public void doFacesRequest(RenderRequest renderRequest, RenderResponse renderResponse)
		throws BridgeDefaultViewNotSpecifiedException, BridgeUninitializedException, BridgeException;

	public void doFacesRequest(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws BridgeUninitializedException, BridgeException;

	public void init(PortletConfig portletConfig) throws BridgeException;
}
