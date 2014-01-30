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
package com.liferay.faces.bridge.filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.portlet.MimeResponse;
import javax.portlet.PortletResponse;
import javax.servlet.ServletOutputStream;


/**
 * This class provides an {@link ServletOutputStream} adapter/wrapper around the {@link OutputStream} provided by the
 * current {@link PortletResponse}. This is necessary in order to hack around a Servlet-API dependencies in the Mojarra
 * implementation of JSF. Refer to usage in {@link HttpServletResponseAdapter#getOutputStream()} for more details.
 *
 * @author  Neil Griffin
 */
public class ServletOutputStreamAdapter extends ServletOutputStream {

	PortletResponse portletResponse;

	public ServletOutputStreamAdapter(PortletResponse portletResponse) {
		this.portletResponse = portletResponse;
	}

	@Override
	public void write(int b) throws IOException {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.getPortletOutputStream().write(b);
		}
	}

}
