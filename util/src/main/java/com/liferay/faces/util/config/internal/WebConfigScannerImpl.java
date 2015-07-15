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
package com.liferay.faces.util.config.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import javax.xml.parsers.SAXParser;

import com.liferay.faces.util.config.WebConfig;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class WebConfigScannerImpl implements WebConfigScanner {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(WebConfigScannerImpl.class);

	// Private Constants
	private static final String WEB_XML_PATH = "/WEB-INF/web.xml";
	private static final String WEB_XML_LIFERAY_PATH = "/WEB-INF/liferay-web.xml";
	private static final String WEB_FRAGMENT_META_INF_PATH = "META-INF/web-fragment.xml";

	// Private Data Members
	private ClassLoader classLoader;
	private boolean resolveEntities;
	private ResourceReader resourceReader;
	private SAXParser saxParser;

	public WebConfigScannerImpl(ClassLoader classLoader, ResourceReader resourceReader, SAXParser saxParser,
		boolean resolveEntities) {
		this.classLoader = classLoader;
		this.resourceReader = resourceReader;
		this.saxParser = saxParser;
		this.resolveEntities = resolveEntities;
	}

	public WebConfig scan() throws IOException {

		// Parse the Servlet 3.0 META-INF/web-fragment.xml descriptor files found in the classpath.
		Enumeration<URL> webFragmentURLs = classLoader.getResources(WEB_FRAGMENT_META_INF_PATH);

		WebConfig webConfig = new WebConfigImpl();

		InputStream inputStream = null;

		if (webFragmentURLs != null) {

			while (webFragmentURLs.hasMoreElements()) {
				URL webFragmentURL = webFragmentURLs.nextElement();
				inputStream = webFragmentURL.openStream();

				WebConfigParser webConfigParser = newWebConfigParser();

				try {
					webConfig = webConfigParser.parse(inputStream, webConfig);
					inputStream.close();
				}
				catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}

		// Parse the WEB-INF/web.xml descriptor.
		inputStream = resourceReader.getResourceAsStream(WEB_XML_PATH);

		if (inputStream != null) {
			logger.debug("Processing web-app: [{0}]", WEB_XML_PATH);

			WebConfigParser webConfigParser = newWebConfigParser();

			try {
				webConfig = webConfigParser.parse(inputStream, webConfig);
			}
			catch (IOException e) {
				logger.error(e);
				throw new IOException(e.getMessage());
			}
		}

		// Parse the WEB-INF/liferay-web.xml descriptor.
		inputStream = resourceReader.getResourceAsStream(WEB_XML_LIFERAY_PATH);

		if (inputStream != null) {
			logger.debug("Processing web-app: [{0}]", WEB_XML_LIFERAY_PATH);

			WebConfigParser webConfigParser = newWebConfigParser();

			try {
				webConfigParser.parse(inputStream, webConfig);
			}
			catch (IOException e) {
				logger.error(e);
				throw new IOException(e.getMessage());
			}
		}

		return webConfig;
	}

	protected WebConfigParser newWebConfigParser() {
		return new WebConfigParserImpl(saxParser, resolveEntities);
	}

}
