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
package com.liferay.faces.bridge.client.internal;

import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextWrapper;
import javax.servlet.http.HttpServletRequest;


/**
 * @author  Neil Griffin
 */
public class ExternalContextBrowserSnifferImpl extends ExternalContextWrapper {

	// Private Data Members
	private ExternalContext wrappedExternalContext;
	private HttpServletRequest httpServletRequest;

	public ExternalContextBrowserSnifferImpl(ExternalContext externalContext, HttpServletRequest httpServletRequest) {
		this.wrappedExternalContext = externalContext;
		this.httpServletRequest = httpServletRequest;
	}

	@Override
	public Object getRequest() {
		return httpServletRequest;
	}

	@Override
	public ExternalContext getWrapped() {
		return wrappedExternalContext;
	}
}
