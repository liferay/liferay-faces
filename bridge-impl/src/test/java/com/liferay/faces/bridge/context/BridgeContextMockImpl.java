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
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeActionURL;
import com.liferay.faces.bridge.context.url.BridgePartialActionURL;
import com.liferay.faces.bridge.context.url.BridgeRedirectURL;
import com.liferay.faces.bridge.context.url.BridgeResourceURL;
import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManager;


/**
 * @author  Neil Griffin
 */
public class BridgeContextMockImpl implements BridgeContext {

	// Private Data Members
	private BridgeConfig bridgeConfig;
	private String facesViewId;
	private PortletRequest portletRequest;

	public BridgeContextMockImpl(BridgeConfig bridgeConfig, PortletRequest portletRequest, String facesViewId) {
		this.bridgeConfig = bridgeConfig;
		this.portletRequest = portletRequest;
		this.facesViewId = facesViewId;
	}

	public void dispatch(String path) throws IOException {
		throw new UnsupportedOperationException();
	}

	public BridgeActionURL encodeActionURL(String url) {
		throw new UnsupportedOperationException();
	}

	public BridgePartialActionURL encodePartialActionURL(String url) {
		throw new UnsupportedOperationException();
	}

	public BridgeRedirectURL encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters) {
		throw new UnsupportedOperationException();
	}

	public BridgeResourceURL encodeResourceURL(String url) {
		throw new UnsupportedOperationException();
	}

	public void redirect(String url) throws IOException {
		throw new UnsupportedOperationException();
	}

	public Map<String, Object> getAttributes() {
		throw new UnsupportedOperationException();
	}

	public BridgeConfig getBridgeConfig() {
		return bridgeConfig;
	}

	public BridgeRequestScope getBridgeRequestScope() {
		throw new UnsupportedOperationException();
	}

	public BridgeRequestScopeManager getBridgeRequestScopeManager() {
		throw new UnsupportedOperationException();
	}

	public boolean isBridgeRequestScopePreserved() {
		throw new UnsupportedOperationException();
	}

	public String getDefaultRenderKitId() {
		throw new UnsupportedOperationException();
	}

	public Map<String, String> getDefaultViewIdMap() {
		throw new UnsupportedOperationException();
	}

	public String getFacesViewId() {
		return facesViewId;
	}

	public String getFacesViewIdFromPath(String viewPath) {
		throw new UnsupportedOperationException();
	}

	public String getFacesViewQueryString() {
		throw new UnsupportedOperationException();
	}

	public boolean isRenderRedirectAfterDispatch() {
		throw new UnsupportedOperationException();
	}

	public String getInitParameter(String name) {
		throw new UnsupportedOperationException();
	}

	public PortletConfig getPortletConfig() {
		throw new UnsupportedOperationException();
	}

	public PortletContainer getPortletContainer() {
		throw new UnsupportedOperationException();
	}

	public PortletContext getPortletContext() {
		throw new UnsupportedOperationException();
	}

	public PortletRequest getPortletRequest() {
		return portletRequest;
	}

	public void setPortletRequest(PortletRequest portletRequest) {
		throw new UnsupportedOperationException();
	}

	public PortletPhase getPortletRequestPhase() {
		throw new UnsupportedOperationException();
	}

	public PortletResponse getPortletResponse() {
		throw new UnsupportedOperationException();
	}

	public void setPortletResponse(PortletResponse portletResponse) {
		throw new UnsupportedOperationException();
	}

	public List<String> getPreFacesRequestAttrNames() {
		throw new UnsupportedOperationException();
	}

	public Map<String, String[]> getPreservedActionParams() {
		return null;
	}

	public void setRenderRedirect(boolean renderRedirect) {
		throw new UnsupportedOperationException();
	}

	public void setRenderRedirectAfterDispatch(boolean renderRedirectAfterDispatch) {
		throw new UnsupportedOperationException();
	}

	public String getRenderRedirectQueryString() {
		throw new UnsupportedOperationException();
	}

	public void setRenderRedirectQueryString(String renderRedirectQueryString) {
		throw new UnsupportedOperationException();
	}

	public BridgeRedirectURL getRenderRedirectURL() {
		throw new UnsupportedOperationException();
	}

	public void setRenderRedirectURL(BridgeRedirectURL renderRedirectURL) {
		throw new UnsupportedOperationException();
	}

	public String getRequestPathInfo() {
		throw new UnsupportedOperationException();
	}

	public String getRequestServletPath() {
		throw new UnsupportedOperationException();
	}

	public ResponseOutputWriter getResponseOutputWriter() throws IOException {
		throw new UnsupportedOperationException();
	}

	public boolean isPreserveActionParams() {
		throw new UnsupportedOperationException();
	}

	public String getSavedViewState() {
		throw new UnsupportedOperationException();
	}

	public void setSavedViewState(String savedViewState) {
		throw new UnsupportedOperationException();
	}

	public boolean isRenderRedirect() {
		throw new UnsupportedOperationException();
	}

	public void setViewHistory(PortletMode portletMode, String viewId) {
		throw new UnsupportedOperationException();
	}

}
