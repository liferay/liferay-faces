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
package com.liferay.faces.util.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.xml.SAXHandlerBase;


/**
 * @author  Neil Griffin
 */
public class WebConfigParserImpl extends SAXHandlerBase implements WebConfigParser {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(WebConfigParserImpl.class);

	// Private Constants
	private static final String SERVLET = "servlet";
	private static final String SERVLET_CLASS = "servlet-class";
	private static final String SERVLET_MAPPING = "servlet-mapping";
	private static final String SERVLET_NAME = "servlet-name";
	private static final String URL_PATTERN = "url-pattern";

	// Private Data Members
	private Map<String, String> configuredContextParams;
	private List<ConfiguredServlet> configuredServlets;
	private List<ConfiguredServletMapping> configuredServletMappings;
	private boolean parsingServlet;
	private boolean parsingServletClass;
	private boolean parsingServletMapping;
	private boolean parsingServletName;
	private boolean parsingUrlPattern;
	private SAXParser saxParser;
	private String servletClass;
	private String servletName;

	public WebConfigParserImpl(SAXParser saxParser, boolean resolveEntities) {
		super(resolveEntities);
		this.saxParser = saxParser;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (parsingServlet) {

			if (parsingServletClass) {
				servletClass = content.toString().trim();
				parsingServletClass = false;
			}
			else if (parsingServletName) {
				servletName = content.toString().trim();
				parsingServletName = false;
			}

			if (SERVLET.equals(qName)) {
				ConfiguredServlet configuredServlet = new ConfiguredServletImpl(servletName, servletClass);
				configuredServlets.add(configuredServlet);
				parsingServlet = false;
			}
		}
		else if (parsingServletMapping) {

			if (parsingServletName) {
				servletName = content.toString().trim();
				parsingServletName = false;
			}
			else if (parsingUrlPattern) {

				String urlPattern = content.toString().trim();
				ConfiguredServletMapping configuredServletMapping = new ConfiguredServletMappingImpl(servletName,
						urlPattern);
				configuredServletMappings.add(configuredServletMapping);
				logger.trace("Added servletName=[{0}] urlPattern=[{1}] to configuredServletMappings", servletName,
					urlPattern);
				parsingUrlPattern = false;
			}

			if (SERVLET_MAPPING.equals(qName)) {
				parsingServletMapping = false;
			}
		}

		content = null;
	}

	public WebConfig parse(InputStream inputStream, WebConfig webConfig) throws IOException {

		Map<String, String> configuredContextParams = webConfig.getConfiguredContextParams();
		this.configuredContextParams = new HashMap<String, String>(configuredContextParams);

		List<ConfiguredServlet> configuredServlets = webConfig.getConfiguredServlets();
		this.configuredServlets = new ArrayList<ConfiguredServlet>(configuredServlets);

		List<ConfiguredServletMapping> configuredServletMappings = webConfig.getConfiguredServletMappings();
		this.configuredServletMappings = new ArrayList<ConfiguredServletMapping>(configuredServletMappings);

		try {
			saxParser.parse(inputStream, this);
			webConfig = new WebConfigImpl(this.configuredContextParams, this.configuredServlets,
					this.configuredServletMappings);
			saxParser.reset();

			return webConfig;
		}
		catch (SAXException e) {
			logger.error(e);
			throw new IOException(e.getMessage());
		}
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
