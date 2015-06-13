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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.config.WebConfig;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.xml.ConcurrentSAXParserFactory;


/**
 * @author  Neil Griffin
 */
public class ApplicationConfigInitializerImpl implements ApplicationConfigInitializer {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigInitializerImpl.class);

	// Private Data Members
	private String contextPath;
	private boolean resolveEntities;

	public ApplicationConfigInitializerImpl(String contextPath, boolean resolveEntities) {
		this.contextPath = contextPath;
		this.resolveEntities = resolveEntities;
	}

	public ApplicationConfig initialize() throws IOException {

		// Obtain the current ClassLoader
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		// Obtain a ResourceReader that is compatible with a startup ExternalContext
		ResourceReader resourceReader = newResourceReader();

		// Obtain a SAX Parser Factory.
		SAXParserFactory saxParserFactory = ConcurrentSAXParserFactory.newInstance();
		saxParserFactory.setValidating(false);
		saxParserFactory.setNamespaceAware(true);

		try {

			// Obtain a SAX Parser from the factory.
			SAXParser saxParser = saxParserFactory.newSAXParser();

			// Scan all the web.xml and web-fragment.xml descriptors in the classpath.
			WebConfigScanner webConfigScanner = newWebConfigScanner(classLoader, resourceReader, saxParser,
					resolveEntities);
			WebConfig webConfig = webConfigScanner.scan();

			// Scan all the faces-config.xml descriptors in the classpath.
			FacesConfigScanner facesConfigScanner = newFacesConfigScanner(classLoader, resourceReader, saxParser,
					resolveEntities, webConfig);
			FacesConfig facesConfig = facesConfigScanner.scan();

			return new ApplicationConfigImpl(contextPath, facesConfig, webConfig);
		}
		catch (Exception e) {

			// Log the error before throwing since the developer will need to see the stacktrace and
			// IOException(Throwable) wasn't added until Java 6.
			logger.error(e);
			throw new IOException(e.getMessage());
		}
	}

	protected FacesConfigScanner newFacesConfigScanner(ClassLoader classLoader, ResourceReader resourceReader,
		SAXParser saxParser, boolean resolveEntities, WebConfig webConfig) {
		return new FacesConfigScannerImpl(classLoader, resourceReader, saxParser, resolveEntities, webConfig);
	}

	protected ResourceReader newResourceReader() {
		FacesContext startupFacesContext = FacesContext.getCurrentInstance();
		ExternalContext startupExternalContext = startupFacesContext.getExternalContext();

		return new ResourceReaderExternalContextImpl(startupExternalContext);
	}

	protected WebConfigScanner newWebConfigScanner(ClassLoader classLoader, ResourceReader resourceReader,
		SAXParser saxParser, boolean resolveEntities) {
		return new WebConfigScannerImpl(classLoader, resourceReader, saxParser, resolveEntities);
	}

}
