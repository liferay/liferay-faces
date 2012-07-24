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

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.faces.Bridge.PortletPhase;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeInvalidViewPathException;

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
public abstract class BridgeContextWrapper extends BridgeContext implements FacesWrapper<BridgeContext> {

	@Override
	public void dispatch(String path) throws IOException {
		getWrapped().dispatch(path);
	}

	@Override
	public BridgeActionURL encodeActionURL(String url) {
		return getWrapped().encodeActionURL(url);
	}

	@Override
	public BridgePartialActionURL encodePartialActionURL(String url) {
		return getWrapped().encodePartialActionURL(url);
	}

	@Override
	public BridgeRedirectURL encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters) {
		return getWrapped().encodeRedirectURL(baseUrl, parameters);
	}

	@Override
	public BridgeResourceURL encodeResourceURL(String url) {
		return getWrapped().encodeResourceURL(url);
	}

	@Override
	public void redirect(String url) throws IOException {
		getWrapped().redirect(url);
	}

	@Override
	public void release() {
		getWrapped().release();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return getWrapped().getAttributes();
	}

	@Override
	public BridgeConfig getBridgeConfig() {
		return getWrapped().getBridgeConfig();
	}

	@Override
	public BridgeRequestScope getBridgeRequestScope() {
		return getWrapped().getBridgeRequestScope();
	}

	@Override
	public boolean isBridgeRequestScopePreserved() {
		return getWrapped().isBridgeRequestScopePreserved();
	}

	@Override
	public String getDefaultRenderKitId() {
		return getWrapped().getDefaultRenderKitId();
	}

	@Override
	public Map<String, String> getDefaultViewIdMap() {
		return getWrapped().getDefaultViewIdMap();
	}

	@Override
	public String getFacesViewId() throws BridgeDefaultViewNotSpecifiedException, BridgeInvalidViewPathException {
		return getWrapped().getFacesViewId();
	}

	@Override
	public String getFacesViewIdFromPath(String viewPath) {
		return getWrapped().getFacesViewIdFromPath(viewPath);
	}

	@Override
	public String getFacesViewIdFromPath(String viewPath, boolean mustExist) {
		return getWrapped().getFacesViewIdFromPath(viewPath, mustExist);
	}

	@Override
	public String getFacesViewQueryString() {
		return getWrapped().getFacesViewQueryString();
	}

	@Override
	public boolean isRenderRedirectAfterDispatch() {
		return getWrapped().isRenderRedirectAfterDispatch();
	}

	@Override
	public IncongruityContext getIncongruityContext() {
		return getWrapped().getIncongruityContext();
	}

	@Override
	public String getInitParameter(String name) {
		return getWrapped().getInitParameter(name);
	}

	@Override
	public PortletConfig getPortletConfig() {
		return getWrapped().getPortletConfig();
	}

	@Override
	public PortletContainer getPortletContainer() {
		return getWrapped().getPortletContainer();
	}

	@Override
	public PortletContext getPortletContext() {
		return getWrapped().getPortletContext();
	}

	@Override
	public PortletRequest getPortletRequest() {
		return getWrapped().getPortletRequest();
	}

	@Override
	public void setPortletRequest(PortletRequest portletRequest) {
		getWrapped().setPortletRequest(portletRequest);
	}

	@Override
	public PortletPhase getPortletRequestPhase() {
		return getWrapped().getPortletRequestPhase();
	}

	@Override
	public PortletResponse getPortletResponse() {
		return getWrapped().getPortletResponse();
	}

	@Override
	public void setPortletResponse(PortletResponse portletResponse) {
		getWrapped().setPortletResponse(portletResponse);
	}

	@Override
	public List<String> getPreFacesRequestAttrNames() {
		return getWrapped().getPreFacesRequestAttrNames();
	}

	@Override
	public Map<String, String[]> getPreservedActionParams() {
		return getWrapped().getPreservedActionParams();
	}

	@Override
	public void setProcessingAfterViewContent(boolean processingAfterViewContent) {
		getWrapped().setProcessingAfterViewContent(processingAfterViewContent);
	}

	@Override
	public void setRenderRedirectAfterDispatch(boolean renderRedirectAfterDispatch) {
		getWrapped().setRenderRedirectAfterDispatch(renderRedirectAfterDispatch);
	}

	@Override
	public BridgeRedirectURL getRenderRedirectURL() {
		return getWrapped().getRenderRedirectURL();
	}

	@Override
	public void setRenderRedirectURL(BridgeRedirectURL renderRedirectURL) {
		getWrapped().setRenderRedirectURL(renderRedirectURL);
	}

	@Override
	public Map<String, String> getRequestHeaderMap() {
		return getWrapped().getRequestHeaderMap();
	}

	@Override
	public Map<String, String[]> getRequestHeaderValuesMap() {
		return getWrapped().getRequestHeaderValuesMap();
	}

	@Override
	public Map<String, String> getRequestParameterMap() {
		return getWrapped().getRequestParameterMap();
	}

	@Override
	public Map<String, String[]> getRequestParameterValuesMap() {
		return getWrapped().getRequestParameterValuesMap();
	}

	@Override
	public String getRequestPathInfo() {
		return getWrapped().getRequestPathInfo();
	}

	@Override
	public String getRequestServletPath() {
		return getWrapped().getRequestServletPath();
	}

	@Override
	public String getResponseNamespace() {
		return getWrapped().getResponseNamespace();
	}

	@Override
	public Writer getResponseOutputWriter() throws IOException {
		return getWrapped().getResponseOutputWriter();
	}

	@Override
	public boolean isPreserveActionParams() {
		return getWrapped().isPreserveActionParams();
	}

	@Override
	public String getSavedViewState() {
		return getWrapped().getSavedViewState();
	}

	@Override
	public void setSavedViewState(String savedViewState) {
		getWrapped().setSavedViewState(savedViewState);
	}

	@Override
	public boolean isProcessingAfterViewContent() {
		return getWrapped().isProcessingAfterViewContent();
	}

	@Override
	public boolean isRenderRedirect() {
		return getWrapped().isRenderRedirect();
	}

	public abstract BridgeContext getWrapped();
}
