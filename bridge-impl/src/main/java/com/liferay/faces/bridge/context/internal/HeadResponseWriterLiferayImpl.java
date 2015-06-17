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

import java.io.IOException;

import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.w3c.dom.Element;

import com.liferay.faces.bridge.renderkit.html_basic.internal.ElementImpl;
import com.liferay.faces.bridge.taglib.liferay.internal.HtmlTopTag;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.jsp.JspAdapterFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.util.PortalUtil;


/**
 * Custom {@link ResponseWriter} that has the ability to write to the <head>...</head> section of the portal page via
 * the Liferay vendor-specific mechanism.
 *
 * @author  Neil Griffin
 */
public class HeadResponseWriterLiferayImpl extends HeadResponseWriterBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(HeadResponseWriterLiferayImpl.class);

	public HeadResponseWriterLiferayImpl(ResponseWriter wrappedResponseWriter) {
		super(wrappedResponseWriter);
	}

	@Override
	public Element createElement(String name) {
		return new ElementImpl(name);
	}

	@Override
	protected void addResourceToHeadSection(Element element, String nodeName) throws IOException {

		// Get the underlying HttpServletRequest and HttpServletResponse
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
		ELContext elContext = facesContext.getELContext();

		// Invoke the Liferay HtmlTopTag class directly (rather than using liferay-util:html-top from a JSP).
		JspAdapterFactory jspAdapterFactory = (JspAdapterFactory) FactoryExtensionFinder.getFactory(
				JspAdapterFactory.class);
		JspWriter stringJspWriter = jspAdapterFactory.getStringJspWriter();
		BodyContent stringBodyContent = jspAdapterFactory.getStringBodyContent(stringJspWriter);
		String elementAsString = element.toString();
		HtmlTopTag htmlTopTag = new HtmlTopTag();
		PageContext stringPageContext = jspAdapterFactory.getStringPageContext(httpServletRequest, httpServletResponse,
			elContext, stringJspWriter);
		htmlTopTag.setPageContext(stringPageContext);
		htmlTopTag.doStartTag();
		stringBodyContent.print(elementAsString);
		htmlTopTag.setBodyContent(stringBodyContent);

		try {
			htmlTopTag.doEndTag();
		}
		catch (Exception e) {
			throw new IOException(e.getMessage());
		}

		logger.debug(ADDED_RESOURCE_TO_HEAD, "Liferay", nodeName);
	}
}
