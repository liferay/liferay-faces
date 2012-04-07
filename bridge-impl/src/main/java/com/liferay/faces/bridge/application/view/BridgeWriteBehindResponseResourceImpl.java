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
package com.liferay.faces.bridge.application.view;

import java.util.Locale;

import javax.portlet.MimeResponse;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletResponse;


/**
 * @author  Neil Griffin
 */
public class BridgeWriteBehindResponseResourceImpl extends BridgeWriteBehindResponseMimeImpl
	implements ResourceResponse {

	public BridgeWriteBehindResponseResourceImpl(ResourceResponse resourceResponse, ServletResponse servletResponse) {
		super((MimeResponse) resourceResponse, servletResponse);
	}

	public void setCharacterEncoding(String charset) {
		getResponse().setCharacterEncoding(charset);

	}

	public void setContentLength(int len) {
		getResponse().setContentLength(len);

	}

	public void setLocale(Locale loc) {
		getResponse().setLocale(loc);
	}

	@Override
	public ResourceResponse getResponse() {
		return (ResourceResponse) super.getResponse();
	}

}
