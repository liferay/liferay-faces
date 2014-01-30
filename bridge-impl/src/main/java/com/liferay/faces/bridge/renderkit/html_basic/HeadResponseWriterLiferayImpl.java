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
package com.liferay.faces.bridge.renderkit.html_basic;

import java.io.IOException;
import java.util.EmptyStackException;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.BodyContent;

import org.w3c.dom.Element;

import com.liferay.faces.bridge.taglib.liferay.HtmlTopTag;
import com.liferay.faces.util.jsp.PageContextAdapter;
import com.liferay.faces.util.jsp.StringBodyContent;
import com.liferay.faces.util.jsp.StringJspWriter;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.util.PortalUtil;


/**
 * Custom {@link ResponseWriter} that has the ability to write to the <head>...</head> section of the portal page via
 * the Liferay vendor-specific mechanism.
 *
 * @author  Neil Griffin
 */
public class HeadResponseWriterLiferayImpl extends HeadResponseWriter {

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
	public void endElement(String name) throws IOException {

		try {
			ElementWriter elementWriter = elementWriterStack.pop();
			Element element = elementWriter.getElement();
			String nodeName = element.getNodeName();
			logger.trace("POPPED element name=[{0}]", nodeName);

			if (!ELEMENT_HEAD.equals(element.getNodeName())) {

				// Get the underlying HttpServletRequest and HttpServletResponse
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
				HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
				PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
				HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
				ELContext elContext = facesContext.getELContext();

				// Invoke the Liferay HtmlTopTag class directly (rather than using liferay-util:html-top from a JSP).
				StringJspWriter stringJspWriter = new StringJspWriter();
				BodyContent bodyContent = new StringBodyContent(stringJspWriter);
				String elementAsString = element.toString();
				HtmlTopTag htmlTopTag = new HtmlTopTag();
				PageContextAdapter pageContextAdapter = new PageContextAdapter(httpServletRequest, httpServletResponse,
						elContext, stringJspWriter);
				htmlTopTag.setPageContext(pageContextAdapter);
				htmlTopTag.doStartTag();
				bodyContent.print(elementAsString);
				htmlTopTag.setBodyContent(bodyContent);

				try {
					htmlTopTag.doEndTag();
				}
				catch (Exception e) {
					throw new IOException(e.getMessage());
				}

				logger.debug("Added resource to Liferay's <head>...</head> section, element=[{0}]", elementAsString);
			}
		}
		catch (EmptyStackException e) {
			throw new IOException(EmptyStackException.class.getSimpleName());
		}
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		Element element = createElement(name);
		ElementWriter elementWriter = new ElementWriter(element);
		elementWriterStack.push(elementWriter);
		logger.trace("PUSHED element name=[{0}]", name);
	}

}
