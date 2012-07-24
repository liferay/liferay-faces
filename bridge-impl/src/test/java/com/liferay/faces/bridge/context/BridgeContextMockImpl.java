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
package com.liferay.faces.bridge.context;

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
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.faces.Bridge.PortletPhase;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.url.BridgeActionURL;
import com.liferay.faces.bridge.context.url.BridgePartialActionURL;
import com.liferay.faces.bridge.context.url.BridgeRedirectURL;
import com.liferay.faces.bridge.context.url.BridgeResourceURL;
import com.liferay.faces.bridge.scope.BridgeRequestScope;


/**
 * @author  Neil Griffin
 */
public class BridgeContextMockImpl extends BridgeContext {

	// Private Data Members
	private BridgeConfig bridgeConfig;
	private String facesViewId;
	private PortletRequest portletRequest;

	public BridgeContextMockImpl(BridgeConfig bridgeConfig, PortletRequest portletRequest, String facesViewId) {
		this.bridgeConfig = bridgeConfig;
		this.portletRequest = portletRequest;
		this.facesViewId = facesViewId;
		setCurrentInstance(this);
	}

	@Override
	public void dispatch(String path) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BridgeActionURL encodeActionURL(String url) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BridgePartialActionURL encodePartialActionURL(String url) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BridgeRedirectURL encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BridgeResourceURL encodeResourceURL(String url) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void redirect(String url) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void release() {
		this.bridgeConfig = null;
		this.portletRequest = null;
		this.facesViewId = null;
		setCurrentInstance(null);
	}

	@Override
	public Map<String, Object> getAttributes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public BridgeConfig getBridgeConfig() {
		return bridgeConfig;
	}

	@Override
	public BridgeRequestScope getBridgeRequestScope() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isBridgeRequestScopePreserved() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDefaultRenderKitId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String> getDefaultViewIdMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFacesViewId() {
		return facesViewId;
	}

	@Override
	public String getFacesViewIdFromPath(String viewPath) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFacesViewIdFromPath(String viewPath, boolean mustExist) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFacesViewQueryString() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRenderRedirectAfterDispatch() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IncongruityContext getIncongruityContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getInitParameter(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PortletConfig getPortletConfig() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PortletContainer getPortletContainer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PortletContext getPortletContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PortletRequest getPortletRequest() {
		return portletRequest;
	}

	@Override
	public void setPortletRequest(PortletRequest portletRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PortletPhase getPortletRequestPhase() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PortletResponse getPortletResponse() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPortletResponse(PortletResponse portletResponse) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getPreFacesRequestAttrNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String[]> getPreservedActionParams() {
		return null;
	}

	@Override
	public void setProcessingAfterViewContent(boolean processingAfterViewContent) {
		throw new UnsupportedOperationException();
	}

	public void setRenderRedirect(boolean renderRedirect) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRenderRedirectAfterDispatch(boolean renderRedirectAfterDispatch) {
		throw new UnsupportedOperationException();
	}

	public String getRenderRedirectQueryString() {
		throw new UnsupportedOperationException();
	}

	public void setRenderRedirectQueryString(String renderRedirectQueryString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BridgeRedirectURL getRenderRedirectURL() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRenderRedirectURL(BridgeRedirectURL renderRedirectURL) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String> getRequestHeaderMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String[]> getRequestHeaderValuesMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String> getRequestParameterMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String[]> getRequestParameterValuesMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRequestPathInfo() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRequestServletPath() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getResponseNamespace() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Writer getResponseOutputWriter() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPreserveActionParams() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSavedViewState() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSavedViewState(String savedViewState) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isProcessingAfterViewContent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRenderRedirect() {
		throw new UnsupportedOperationException();
	}

	public void setViewHistory(PortletMode portletMode, String viewId) {
		throw new UnsupportedOperationException();
	}
}
