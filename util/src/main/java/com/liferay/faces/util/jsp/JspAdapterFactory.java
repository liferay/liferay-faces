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
package com.liferay.faces.util.jsp;

import javax.el.ELContext;
import javax.faces.FacesWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;


/**
 * This factory provides methods for creating instances of classes associated with the JavaServer Pages (JSP) API that
 * operate with Strings rather than JSPs. This provides the ability to run the lifecycle of a JSP tag so that the JSP
 * tag markup is captured in a String rather than written as output from a JSP.
 *
 * @author  Kyle Stiemann
 */
public abstract class JspAdapterFactory implements FacesWrapper<JspAdapterFactory> {

	public abstract BodyContent getStringBodyContent(JspWriter stringJspWriter);

	public abstract JspWriter getStringJspWriter();

	public abstract PageContext getStringPageContext(HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, ELContext elContext, JspWriter stringJspWriter);
}
