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
package com.liferay.faces.bridge.config;

/**
 * This class contains constant names for various implementation-specific contex-param entries that portlet developers
 * can use in the WEB-INF/web.xml descriptor.
 *
 * @author      Neil Griffin
 * @deprecated  Use enumeration values in {@link PortletConfigParam} or {@link BridgeConfigLegacyParam} instead.
 */
@Deprecated
public class BridgeConfigConstants {

	@Deprecated
	public static final String PARAM_BRIDGE_REQUEST_SCOPE_AJAX_ENABLED =
		PortletConfigParam.BridgeRequestScopeAjaxEnabled.getName();

	@Deprecated
	public static String PARAM_CONTAINER_ABLE_TO_SET_HTTP_STATUS_CODE1 =
		PortletConfigParam.ContainerAbleToSetHttpStatusCode.getName();

	@Deprecated
	public static String PARAM_CONTAINER_ABLE_TO_SET_HTTP_STATUS_CODE2 =
		PortletConfigParam.ContainerAbleToSetHttpStatusCode.getAlternateName();

	@Deprecated
	public static String PARAM_DISTINCT_REQUEST_SCOPED_MANAGED_BEANS =
		PortletConfigParam.DistinctRequestScopedManagedBeans.getName();

	@Deprecated
	public static final String PARAM_BRIDGE_REQUEST_SCOPE_PRESERVED1 = PortletConfigParam.BridgeRequestScopePreserved
		.getName();

	@Deprecated
	public static final String PARAM_BRIDGE_REQUEST_SCOPE_PRESERVED2 = PortletConfigParam.BridgeRequestScopePreserved
		.getAlternateName();

	@Deprecated
	public static final String PARAM_MANAGE_INCONGRUITIES = PortletConfigParam.ManageIncongruities.getName();

	@Deprecated
	public static final String PARAM_OPTIMIZE_PORTLET_NAMESPACE1 = PortletConfigParam.OptimizePortletNamespace
		.getName();

	@Deprecated
	public static final String PARAM_OPTIMIZE_PORTLET_NAMESPACE2 = PortletConfigParam.OptimizePortletNamespace
		.getAlternateName();

	@Deprecated
	public static final String PARAM_PREFER_PRE_DESTROY1 = PortletConfigParam.PreferPreDestroy.getName();

	@Deprecated
	public static final String PARAM_PREFER_PRE_DESTROY2 = PortletConfigParam.PreferPreDestroy.getAlternateName();

	@Deprecated
	public static final String PARAM_RENDER_REDIRECT_ENABLED = PortletConfigParam.RenderRedirectEnabled.getName();

	@Deprecated
	public static String PARAM_REQUIRED_TO_RESOLVE_XML_ENTITIES1 = PortletConfigParam.ResolveXMLEntities.getName();

	@Deprecated
	public static String PARAM_REQUIRED_TO_RESOLVE_XML_ENTITIES2 = PortletConfigParam.ResolveXMLEntities
		.getAlternateName();

	@Deprecated
	public static final String PARAM_RESOURCE_BUFFER_SIZE1 = PortletConfigParam.ResourceBufferSize.getName();

	@Deprecated
	public static final String PARAM_RESOURCE_BUFFER_SIZE2 = PortletConfigParam.ResourceBufferSize.getAlternateName();

	@Deprecated
	public static final String PARAM_VIEW_ID_RENDER = PortletConfigParam.ViewIdRenderParameterName.getName();

	@Deprecated
	public static final String PARAM_VIEW_ID_RESOURCE = PortletConfigParam.ViewIdResourceParameterName.getName();

	@Deprecated
	public static String PARAM_VIEW_PARAMTERS_ENABLED = PortletConfigParam.ViewParametersEnabled.getName();
}
