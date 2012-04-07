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
package com.liferay.faces.bridge.config;

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * This class contains constant names for various implementation-specific contex-param entries that portlet developers
 * can use in the WEB-INF/web.xml descriptor.
 *
 * @author  Neil Griffin
 */
public class BridgeConfigConstants {

	/**
	 * Boolean indicating whether or not the portlet container has the ability to set the HTTP status code for
	 * resources. Default value is false.
	 */
	public static String PARAM_CONTAINER_ABLE_TO_SET_HTTP_STATUS_CODE1 =
		"com.liferay.faces.bridge.containerAbleToSetHttpStatusCode";
	public static String PARAM_CONTAINER_ABLE_TO_SET_HTTP_STATUS_CODE2 =
		"org.portletfaces.bridge.containerAbleToSetHttpStatusCode";

	/** Value returned by {@link BridgeContext#isBridgeRequestScopePreserved()}. Default is true. */
	public static final String PARAM_BRIDGE_REQUEST_SCOPE_PRESERVED1 =
		"com.liferay.faces.bridge.bridgeRequestScopePreserved";
	public static final String PARAM_BRIDGE_REQUEST_SCOPE_PRESERVED2 = "org.portletfaces.bridgeRequestScopePreserved";

	/**
	 * Boolean indicating whether or not the portlet namespace should be optimized (minimized) in order to provide the
	 * shortest possible rendered clientIds. Default value is true.
	 */
	public static final String PARAM_OPTIMIZE_PORTLET_NAMESPACE1 = "com.liferay.faces.bridge.optimizePortletNamespace";
	public static final String PARAM_OPTIMIZE_PORTLET_NAMESPACE2 = "org.portletfaces.bridge.optimizePortletNamespace";


	/**
	 * Boolean indicating whether or not methods annotated with the &#064;PreDestroy annotation are preferably invoked
	 * over the &#064;BridgePreDestroy annotation. Default value is true. The reason why, is because local portals like
	 * Liferay don't have a problem with PreDestroy. It really only comes into play for remote portals like Oracle
	 * WebCenter. For more info, see: http://issues.liferay.com/browse/FACES-146
	 */
	public static final String PARAM_PREFER_PRE_DESTROY1 = "com.liferay.faces.bridge.preferPreDestroy";
	public static final String PARAM_PREFER_PRE_DESTROY2 = "org.portletfaces.bridge.preferPreDestroy";

	/**
	 * Boolean indicating whether or not the render-redirect standard feature is enabled. Default value is false for
	 * performance.
	 */
	public static final String PARAM_RENDER_REDIRECT_ENABLED = "com.liferay.faces.bridge.renderRedirectEnabled";

	/**
	 * Boolean indicating whether or not XML entities are resolved when parsing faces-config.xml files. Default value is
	 * false.
	 */
	public static String PARAM_REQUIRED_TO_RESOLVE_XML_ENTITIES1 = "com.liferay.faces.bridge.resolveXMLEntities";
	public static String PARAM_REQUIRED_TO_RESOLVE_XML_ENTITIES2 = "org.portletfaces.bridge.resolveXMLEntities";

	/** Size in bytes for the buffer that is used to deliver resources back to the browser. Default value is 1024. */
	public static final String PARAM_RESOURCE_BUFFER_SIZE1 = "com.liferay.faces.bridge.resourceBufferSize";
	public static final String PARAM_RESOURCE_BUFFER_SIZE2 = "org.portletfaces.bridge.resourceBufferSize";

	/** Name of the render parameter used to encode the viewId */
	public static final String PARAM_VIEW_ID_RENDER = "com.liferay.faces.bridge.viewIdRenderParameterName";

	/** Name of the resource request parameter used to encode the viewId */
	public static final String PARAM_VIEW_ID_RESOURCE = "com.liferay.faces.bridge.viewIdResourceParameterName";
}
