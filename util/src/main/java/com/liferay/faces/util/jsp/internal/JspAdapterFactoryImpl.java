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
package com.liferay.faces.util.jsp.internal;

import javax.el.ELContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import com.liferay.faces.util.jsp.JspAdapterFactory;
import com.liferay.faces.util.jsp.StringJspWriter;


/**
 * @author  Kyle Stiemann
 */
public class JspAdapterFactoryImpl extends JspAdapterFactory {

	@Override
	public BodyContent getBodyContent(StringJspWriter stringJspWriter) {
		return new StringBodyContentImpl(stringJspWriter);
	}

	@Override
	public PageContext getPageContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		ELContext elContext, StringJspWriter stringJspWriter) {
		return new PageContextAdapterImpl(httpServletRequest, httpServletResponse, elContext, stringJspWriter);
	}

	@Override
	public StringJspWriter getStringJspWriter() {
		return new StringJspWriterImpl();
	}

	@Override
	public JspAdapterFactory getWrapped() {
		return null;
	}
}
