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
package com.liferay.faces.bridge.config.internal;

import javax.portlet.PortletConfig;

import com.liferay.faces.util.config.ConfigParam;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * This enumeration contains constant names for various implementation-specific contex-param entries that portlet
 * developers can use in the WEB-INF/web.xml descriptor.
 *
 * @author  Neil Griffin
 */
public enum PortletConfigParam implements ConfigParam<PortletConfig> {

	/**
	 * Flag indicating whether or not the bridge should manage BridgeRequestScope during the RESOURCE_PHASE of the
	 * portlet lifecycle. Default value is false. Set value to true in order to enable JSR 329 default behavior.
	 */
	BridgeRequestScopeAjaxEnabled("com.liferay.faces.bridge.bridgeRequestScopeAjaxEnabled", false),

	/**
	 * Flag indicating whether or not the bridge request scope is preserved after the RENDER_PHASE completes. Default
	 * value is false. Set value to true in order to enable JSR 329 default behavior.
	 */
	BridgeRequestScopePreserved("com.liferay.faces.bridge.bridgeRequestScopePreserved",
		"org.portletfaces.bridgeRequestScopePreserved", false),

	/**
	 * Flag indicating whether or not the portlet container has the ability to set the HTTP status code for resources.
	 * Default value is false.
	 */
	ContainerAbleToSetHttpStatusCode("com.liferay.faces.bridge.containerAbleToSetHttpStatusCode",
		"org.portletfaces.bridge.containerAbleToSetHttpStatusCode", false),

	/**
	 * Flag indicating whether or not JSF {@link javax.faces.bean.ManagedBean} classes annotated with {@link
	 * javax.faces.bean.RequestScoped} should be distinct for each portlet. Default value is false.
	 */
	DistinctRequestScopedManagedBeans("com.liferay.faces.bridge.distinctRequestScopedManagedBeans", false),

	/**
	 * Flag indicating whether or not the bridge should manage incongruities between the JSF lifecycle and the Portlet
	 * lifecycle. The default is true.
	 */
	ManageIncongruities("com.liferay.faces.bridge.manageIncongruities", true),

	/**
	 * Flag indicating whether or not the portlet namespace should be optimized (minimized) in order to provide the
	 * shortest possible rendered clientIds. Default value is true unless running on Liferay Portal 6.2 (or newer) which
	 * is not compatible with the bridge's namespace optimization feature.
	 */
	OptimizePortletNamespace("com.liferay.faces.bridge.optimizePortletNamespace",
		"org.portletfaces.bridge.optimizePortletNamespace",
		(Liferay.PORTAL.isDetected() && (Liferay.PORTAL.getMajorVersion() <= 6) &&
			(Liferay.PORTAL.getMinorVersion() <= 1)) || !Liferay.PORTAL.isDetected()),

	/**
	 * Flag indicating whether or not methods annotated with the &#064;PreDestroy annotation are preferably invoked over
	 * the &#064;BridgePreDestroy annotation. Default value is true.For more info, see:
	 * http://issues.liferay.com/browse/FACES-146
	 */
	PreferPreDestroy("com.liferay.faces.bridge.preferPreDestroy", "org.portletfaces.bridge.preferPreDestroy", true),

	/**
	 * Flag indicating whether or not the render-redirect standard feature is enabled. Default value is false for the
	 * sake of performance.
	 */
	RenderRedirectEnabled("com.liferay.faces.bridge.renderRedirectEnabled", false),

	/** Size in bytes for the buffer that is used to deliver resources back to the browser. Default value is 1024. */
	ResourceBufferSize("com.liferay.faces.bridge.resourceBufferSize", "org.portletfaces.bridge.resourceBufferSize",
		1024),

	/**
	 * Absolute path to a directory (folder) in which the uploaded file data should be written to. Default value is the
	 * value of the system property "java.io.tmpdir".
	 */
	UploadedFilesDir("com.liferay.faces.bridge.uploadedFilesDir", "javax.faces.UPLOADED_FILES_DIR",
		System.getProperty("java.io.tmpdir")),

	/** Maximum file size for an uploaded file. Default is 104857600 bytes (100MB) */
	UploadedFileMaxSize("com.liferay.faces.bridge.uploadedFileMaxSize", "javax.faces.UPLOADED_FILE_MAX_SIZE", 104857600),

	/** Name of the render parameter used to encode the viewId. Default value is "_facesViewIdRender". */
	ViewIdRenderParameterName("com.liferay.faces.bridge.viewIdRenderParameterName", "_facesViewIdRender"),

	/** Name of the resource request parameter used to encode the viewId Default value is "_facesViewIdResource" */
	ViewIdResourceParameterName("com.liferay.faces.bridge.viewIdResourceParameterName", "_facesViewIdResource"),

	/** Flag indicating whether or not the JSF 2 "View Parameters" feature is enabled. Default value is true. */
	ViewParametersEnabled("com.liferay.faces.bridge.viewParametersEnabled", true);

	// Private Data Members
	private String alternateName;
	private boolean defaultBooleanValue;
	private String defaultStringValue;
	private int defaultIntegerValue;
	private String name;

	private PortletConfigParam(String name, String defaultStringValue) {
		this(name, null, defaultStringValue);
	}

	private PortletConfigParam(String name, boolean defaultBooleanValue) {
		this(name, null, defaultBooleanValue);
	}

	private PortletConfigParam(String name, String alternateName, int defaultIntegerValue) {
		this.name = name;
		this.alternateName = alternateName;
		this.defaultBooleanValue = (defaultIntegerValue != 0);
		this.defaultIntegerValue = defaultIntegerValue;
		this.defaultStringValue = Integer.toString(defaultIntegerValue);
	}

	private PortletConfigParam(String name, String alternateName, String defaultStringValue) {
		this.name = name;
		this.alternateName = alternateName;

		if (BooleanHelper.isTrueToken(defaultStringValue)) {
			this.defaultBooleanValue = true;
			this.defaultIntegerValue = 1;
		}
		else {
			this.defaultBooleanValue = false;
			this.defaultIntegerValue = 0;
		}

		this.defaultStringValue = defaultStringValue;
	}

	private PortletConfigParam(String name, String alternateName, boolean defaultBooleanValue) {
		this.name = name;
		this.alternateName = alternateName;
		this.defaultBooleanValue = defaultBooleanValue;

		if (defaultBooleanValue) {
			this.defaultIntegerValue = 1;
			this.defaultStringValue = Boolean.TRUE.toString();
		}
		else {
			this.defaultIntegerValue = 0;
			this.defaultStringValue = Boolean.FALSE.toString();
		}
	}

	public String getAlternateName() {
		return alternateName;
	}

	// Java 1.6+ @Override
	public boolean getBooleanValue(PortletConfig portletConfig) {
		return PortletConfigParamUtil.getBooleanValue(portletConfig, name, alternateName, defaultBooleanValue);
	}

	// Java 1.6+ @Override
	public String getConfiguredValue(PortletConfig portletConfig) {
		return PortletConfigParamUtil.getConfiguredValue(portletConfig, name, alternateName);
	}

	// Java 1.6+ @Override
	public boolean isConfigured(PortletConfig portletConfig) {
		return PortletConfigParamUtil.isSpecified(portletConfig, name, alternateName);
	}

	public boolean getDefaultBooleanValue() {
		return defaultBooleanValue;
	}

	public int getDefaultIntegerValue() {
		return defaultIntegerValue;
	}

	public String getDefaultStringValue() {
		return defaultStringValue;
	}

	// Java 1.6+ @Override
	public int getIntegerValue(PortletConfig portletConfig) {
		return PortletConfigParamUtil.getIntegerValue(portletConfig, name, alternateName, defaultIntegerValue);
	}

	public String getName() {
		return name;
	}

	// Java 1.6+ @Override
	public String getStringValue(PortletConfig portletConfig) {
		return PortletConfigParamUtil.getStringValue(portletConfig, name, alternateName, defaultStringValue);
	}

	protected static class Liferay {
		private static final Product PORTAL = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);
	}
}
