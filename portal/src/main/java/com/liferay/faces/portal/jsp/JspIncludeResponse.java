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
package com.liferay.faces.portal.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/**
 * This class wraps an HttpServletResponse in order to buffer output in a String, rather than having the output go to
 * the response. This provides a mechanism for capturing the rendered HTML of a JSP fragment for inclusion within a
 * rendered Facelet view.
 *
 * @author  Neil Griffin
 */
public class JspIncludeResponse extends HttpServletResponseWrapper {

	private StringWriter stringWriter;
	private PrintWriter printWriter;

	public JspIncludeResponse(HttpServletResponse response) {
		super(response);
	}

	public String getBufferedResponse() {
		String bufferedResponse = null;

		if (stringWriter != null) {
			bufferedResponse = stringWriter.getBuffer().toString();
		}

		return bufferedResponse;
	}

	@Override
	public void setContentType(String type) {
		// NOTE: This method is deliberately overridden and does nothing. If not overridden, then in the case of Ajax,
		// the JSP engine would end up setting the content-type of the HttpServletResponse to something other than
		// text/xml which causes the DOM replacements to fail in the browser.
	}

	@Override
	public PrintWriter getWriter() throws IOException {

		if (printWriter == null) {
			stringWriter = new StringWriter();
			printWriter = new PrintWriter(stringWriter);
		}

		return printWriter;
	}
}
