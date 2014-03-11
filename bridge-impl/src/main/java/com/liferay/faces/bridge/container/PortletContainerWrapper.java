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
package com.liferay.faces.bridge.container;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class PortletContainerWrapper implements PortletContainer, Wrapper<PortletContainer> {

	// serialVersionUID
	private static final long serialVersionUID = 528379462567078842L;

	public void afterPhase(PhaseEvent event) {
		getWrapped().afterPhase(event);
	}

	public void beforePhase(PhaseEvent event) {
		getWrapped().beforePhase(event);
	}

	public PortletURL createActionURL(String fromURL) throws MalformedURLException {
		return getWrapped().createActionURL(fromURL);
	}

	public ResourceURL createPartialActionURL(String fromURL) throws MalformedURLException {
		return getWrapped().createPartialActionURL(fromURL);
	}

	public PortletURL createRedirectURL(String fromURL, Map<String, List<String>> parameters)
		throws MalformedURLException {
		return getWrapped().createRedirectURL(fromURL, parameters);
	}

	public PortletURL createRenderURL(String fromURL) throws MalformedURLException {
		return getWrapped().createRenderURL(fromURL);
	}

	public ResourceURL createResourceURL(String fromURL) throws MalformedURLException {
		return getWrapped().createResourceURL(fromURL);
	}

	public String fixRequestParameterValue(String value) {
		return getWrapped().fixRequestParameterValue(value);
	}

	public void maintainRenderParameters(EventRequest eventRequest, EventResponse eventResponse) {
		getWrapped().maintainRenderParameters(eventRequest, eventResponse);
	}

	public void redirect(String url) throws IOException {
		getWrapped().redirect(url);
	}

	public boolean isAbleToAddScriptResourceToHead() {
		return getWrapped().isAbleToAddScriptResourceToHead();
	}

	public boolean isAbleToAddScriptTextToHead() {
		return getWrapped().isAbleToAddScriptTextToHead();
	}

	public boolean isAbleToAddStyleSheetResourceToHead() {
		return getWrapped().isAbleToAddStyleSheetResourceToHead();
	}

	public boolean isPostRedirectGetSupported() {
		return getWrapped().isPostRedirectGetSupported();
	}

	public boolean isAbleToSetHttpStatusCode() {
		return getWrapped().isAbleToSetHttpStatusCode();
	}

	public boolean isAbleToSetResourceResponseBufferSize() {
		return getWrapped().isAbleToSetResourceResponseBufferSize();
	}

	public boolean isAbleToForwardOnDispatch() {
		return getWrapped().isAbleToForwardOnDispatch();
	}

	public String[] getHeader(String name) {
		return getWrapped().getHeader(name);
	}

	public ResponseWriter getHeadResponseWriter(ResponseWriter wrappableResponseWriter) {
		return getWrapped().getHeadResponseWriter(wrappableResponseWriter);
	}

	public long getHttpServletRequestDateHeader(String name) {
		return getWrapped().getHttpServletRequestDateHeader(name);
	}

	public void setMimeResponseContentType(MimeResponse mimeResponse, String contentType) {
		getWrapped().setMimeResponseContentType(mimeResponse, contentType);
	}

	public PhaseId getPhaseId() {
		return getWrapped().getPhaseId();
	}

	public String getRequestParameter(String name) {
		return getWrapped().getRequestParameter(name);
	}

	public String[] getRequestParameterValues(String name) {
		return getWrapped().getRequestParameterValues(name);
	}

	public String getRequestQueryString() {
		return getWrapped().getRequestQueryString();
	}

	public String getRequestURL() {
		return getWrapped().getRequestURL();
	}

	public String getResponseNamespace() {
		return getWrapped().getResponseNamespace();
	}

	public boolean isNamespacedParameters() {
		return getWrapped().isNamespacedParameters();
	}

	public abstract PortletContainer getWrapped();
}
