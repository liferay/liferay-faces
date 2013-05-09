/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.Iterator;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;


/**
 * @author  Neil Griffin
 */
public abstract class FacesContextWrapper extends FacesContext {

	@Override
	public void addMessage(String clientId, FacesMessage message) {
		getWrapped().addMessage(clientId, message);
	}

	@Override
	public void release() {
		getWrapped().release();
	}

	@Override
	public void renderResponse() {
		getWrapped().renderResponse();
	}

	@Override
	public void responseComplete() {
		getWrapped().responseComplete();
	}

	@Override
	public Application getApplication() {
		return getWrapped().getApplication();
	}

	@Override
	public Iterator<String> getClientIdsWithMessages() {
		return getWrapped().getClientIdsWithMessages();
	}

	@Override
	public ExternalContext getExternalContext() {
		return getWrapped().getExternalContext();
	}

	@Override
	public Severity getMaximumSeverity() {
		return getWrapped().getMaximumSeverity();
	}

	@Override
	public Iterator<FacesMessage> getMessages() {
		return getWrapped().getMessages();
	}

	@Override
	public Iterator<FacesMessage> getMessages(String clientId) {
		return getWrapped().getMessages(clientId);
	}

	@Override
	public RenderKit getRenderKit() {
		return getWrapped().getRenderKit();
	}

	@Override
	public boolean getRenderResponse() {
		return getWrapped().getRenderResponse();
	}

	@Override
	public boolean getResponseComplete() {
		return getWrapped().getResponseComplete();
	}

	@Override
	public ResponseStream getResponseStream() {
		return getWrapped().getResponseStream();
	}

	@Override
	public void setResponseStream(ResponseStream responseStream) {
		getWrapped().setResponseStream(responseStream);
	}

	@Override
	public ResponseWriter getResponseWriter() {
		return getWrapped().getResponseWriter();
	}

	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		getWrapped().setResponseWriter(responseWriter);
	}

	@Override
	public UIViewRoot getViewRoot() {
		return getWrapped().getViewRoot();
	}

	@Override
	public void setViewRoot(UIViewRoot uiViewRoot) {
		getWrapped().setViewRoot(uiViewRoot);
	}

	public abstract FacesContext getWrapped();

}
