/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.internal;

import java.util.Iterator;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;


/**
 * This class is an implementation of {@link FacesContext} that can be used during session expiration.
 *
 * @author  Neil Griffin
 */
public class FacesContextExpirationImpl extends FacesContext {

	// Private Data Members
	ExternalContext externalContext;

	public FacesContextExpirationImpl(ExternalContext externalContext) {
		this.externalContext = externalContext;

		setCurrentInstance(this);
	}

	@Override
	public void addMessage(String clientId, FacesMessage message) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void release() {
		externalContext = null;
		setCurrentInstance(null);
	}

	@Override
	public void renderResponse() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void responseComplete() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Application getApplication() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<String> getClientIdsWithMessages() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExternalContext getExternalContext() {
		return externalContext;
	}

	@Override
	public FacesMessage.Severity getMaximumSeverity() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<FacesMessage> getMessages() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<FacesMessage> getMessages(String clientId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public RenderKit getRenderKit() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getRenderResponse() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getResponseComplete() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResponseStream getResponseStream() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setResponseStream(ResponseStream responseStream) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResponseWriter getResponseWriter() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UIViewRoot getViewRoot() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setViewRoot(UIViewRoot root) {
		throw new UnsupportedOperationException();
	}
}
