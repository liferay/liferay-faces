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
package com.liferay.faces.bridge.config;

import java.util.List;

import javax.faces.webapp.FacesServlet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class SAXHandlerWebConfigImpl extends SAXHandlerBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SAXHandlerWebConfigImpl.class);

	// Private Constants
	private static final String FACES_SERVLET_FQCN = FacesServlet.class.getName();
	private static final String SERVLET = "servlet";
	private static final String SERVLET_CLASS = "servlet-class";
	private static final String SERVLET_MAPPING = "servlet-mapping";
	private static final String SERVLET_NAME = "servlet-name";
	private static final String URL_PATTERN = "url-pattern";

	// Private Data Members
	private List<ServletMapping> facesServletMappings;
	private String facesServletName = "Faces Servlet";
	private boolean parsingServlet = false;
	private boolean parsingServletClass = false;
	private boolean parsingServletMapping = false;
	private boolean parsingServletName = false;
	private boolean parsingUrlPattern = false;
	private String servletName;

	public SAXHandlerWebConfigImpl(boolean resolveEntities, List<ServletMapping> facesServletMappings) {
		super(resolveEntities);
		this.facesServletMappings = facesServletMappings;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (parsingServlet) {

			if (parsingServletClass) {
				String servletClass = content.toString().trim();

				if (servletClass.length() > 0) {

					if (FACES_SERVLET_FQCN.equals(servletClass) && (servletName.length() > 0)) {
						facesServletName = servletName;
						logger.trace("servlet-class=[{0}] servlet-name=[{1}]", FACES_SERVLET_FQCN, facesServletName);
					}
				}

				parsingServletClass = false;
			}
			else if (parsingServletName) {
				servletName = content.toString().trim();
				parsingServletName = false;
			}

			if (SERVLET.equals(qName)) {
				parsingServlet = false;
			}
		}
		else if (parsingServletMapping) {

			if (parsingServletName) {
				servletName = content.toString().trim();
				parsingServletName = false;
			}
			else if (parsingUrlPattern) {

				if ((servletName != null) && servletName.equals(facesServletName)) {
					String urlPattern = content.toString().trim();
					facesServletMappings.add(new ServletMappingImpl(urlPattern));
					logger.trace("Added urlPattern=[{0}] to facesServletMappings", urlPattern);
				}

				parsingUrlPattern = false;
			}

			if (SERVLET_MAPPING.equals(qName)) {
				parsingServletMapping = false;
			}
		}

		content = null;
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
		throws SAXException {
		logger.trace("localName=[{0}]", localName);

		content = new StringBuilder();

		if (localName.equals(SERVLET)) {
			parsingServlet = true;
		}
		else if (localName.equals(SERVLET_CLASS)) {
			parsingServletClass = true;
		}
		else if (localName.equals(SERVLET_MAPPING)) {
			parsingServletMapping = true;
		}
		else if (localName.equals(SERVLET_NAME)) {
			parsingServletName = true;
		}
		else if (localName.equals(URL_PATTERN)) {
			parsingUrlPattern = true;
		}
	}
}
