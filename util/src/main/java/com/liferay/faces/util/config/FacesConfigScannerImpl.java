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
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.faces.application.ViewHandler;
import javax.faces.webapp.FacesServlet;
import javax.xml.parsers.SAXParser;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class FacesConfigScannerImpl implements FacesConfigScanner {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesConfigScannerImpl.class);

	// Private Constants
	private static final String FACES_CONFIG_META_INF_PATH = "META-INF/faces-config.xml";
	private static final String FACES_CONFIG_WEB_INF_PATH = "/WEB-INF/faces-config.xml";
	private static final String FACES_SERVLET = "Faces Servlet";
	private static final String FACES_SERVLET_FQCN = FacesServlet.class.getName();
	private static final String LIFERAY_FACES_BRIDGE = "LiferayFacesBridge";
	private static final String MOJARRA_CONFIG_PATH = "com/sun/faces/jsf-ri-runtime.xml";

	// Private Data Members
	private boolean resolveEntities;

	private ResourceReader resourceReader;
	private SAXParser saxParser;
	private WebConfig webConfig;

	public FacesConfigScannerImpl(ClassLoader classLoader, ResourceReader resourceReader, SAXParser saxParser,
		boolean resolveEntities, WebConfig webConfig) {
		this.saxParser = saxParser;
		this.resourceReader = resourceReader;
		this.resolveEntities = resolveEntities;
		this.webConfig = webConfig;
	}

	public FacesConfig scan() throws IOException {

		String configuredFacesServletName = FACES_SERVLET;

		List<ConfiguredServletMapping> facesServletMappings = new ArrayList<ConfiguredServletMapping>();

		// Determine the configured servlet-name for the FacesServlet.
		List<ConfiguredServlet> configuredServlets = webConfig.getConfiguredServlets();

		if (configuredServlets != null) {

			for (ConfiguredServlet configuredServlet : configuredServlets) {

				if (FACES_SERVLET_FQCN.equals(configuredServlet.getServletClass())) {

					configuredFacesServletName = configuredServlet.getServletName();

					break;
				}
			}
		}

		// Determine the configured servlet-mapping entries that are associated with the FacesServlet.
		List<ConfiguredServletMapping> configuredServletMappings = webConfig.getConfiguredServletMappings();

		if (configuredServletMappings != null) {

			for (ConfiguredServletMapping configuredServletMapping : configuredServletMappings) {

				if (configuredFacesServletName.equals(configuredServletMapping.getServletName())) {

					facesServletMappings.add(configuredServletMapping);
				}
			}
		}

		// Discover the suffixes/extensions that the user has specified to be associated with JSF views.
		String defaultSuffixParam = webConfig.getConfiguredContextParams().get(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME);

		if (defaultSuffixParam == null) {
			defaultSuffixParam = ViewHandler.DEFAULT_SUFFIX;
		}

		List<String> configuredSuffixes = Arrays.asList(defaultSuffixParam.split(StringPool.SPACE));

		// If they don't exist explicitly in web.xml, then setup implicit servlet-mapping entries to the default
		// suffixes.
		for (String configuredSuffix : configuredSuffixes) {

			boolean found = false;

			for (ConfiguredServletMapping explicitFacesServletMapping : facesServletMappings) {

				if (explicitFacesServletMapping.isExtensionMapped() &&
						explicitFacesServletMapping.getExtension().equals(configuredSuffix)) {
					found = true;

					break;
				}
			}

			if (!found) {
				String urlPattern = StringPool.STAR + configuredSuffix;
				ConfiguredServletMapping implicitFacesServletMapping = new ConfiguredServletMappingImpl(FACES_SERVLET,
						urlPattern);
				facesServletMappings.add(implicitFacesServletMapping);
				logger.debug("Added implicit extension-mapped servlet-mapping for urlPattern=[{0}]", urlPattern);
			}
		}

		FacesConfig facesConfig = new FacesConfigImpl(facesServletMappings, configuredSuffixes);

		try {

			// First, parse the Mojarra configuration found in the classpath.
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Enumeration<URL> mojarraConfigURLs = classLoader.getResources(MOJARRA_CONFIG_PATH);

			if (mojarraConfigURLs != null) {

				boolean processedMojarraConfig = false;

				while (mojarraConfigURLs.hasMoreElements()) {

					URL mojarraConfigURL = mojarraConfigURLs.nextElement();

					if (processedMojarraConfig) {
						logger.debug("Skipping Mojarra config: [{0}]", mojarraConfigURL);
					}
					else {

						logger.debug("Processing Mojarra config: [{0}]", mojarraConfigURL);

						FacesConfigParser mojarraConfigParser = new FacesConfigParserImpl(saxParser, resolveEntities);

						InputStream inputStream = mojarraConfigURL.openStream();

						try {
							facesConfig = mojarraConfigParser.parse(inputStream, facesConfig);
						}
						catch (IOException e) {
							logger.error(e);
						}

						try {
							inputStream.close();
						}
						catch (IOException e) {
							logger.error(e);
						}

						processedMojarraConfig = true;
					}
				}
			}

			FacesConfigDescriptorParser facesConfigDescriptorParser = newFacesConfigDescriptorParser();
			FacesConfigParser facesConfigParser = newFacesConfigParser();

			// Next, parse all of the META-INF/faces-config.xml files found in the classpath.
			Enumeration<URL> facesConfigURLs = classLoader.getResources(FACES_CONFIG_META_INF_PATH);

			List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();

			if (facesConfigURLs != null) {

				// Build up a semi-sorted list of faces-config.xml descriptor files, ensuring that the bridge's
				// META-INF/faces-config.xml descriptor is ordered first. (Note that the JSF 2.0 <ordering> element is
				// not yet supported.)
				while (facesConfigURLs.hasMoreElements()) {

					URL facesConfigURL = facesConfigURLs.nextElement();

					logger.debug("Pre-processing faces-config: [{0}]", facesConfigURL);

					InputStream inputStream = facesConfigURL.openStream();

					FacesConfigDescriptor facesConfigDescriptor = facesConfigDescriptorParser.parse(inputStream,
							facesConfigURL);

					// If the name is not set, then the <name> element was omitted. In JSF 2.x the <name> element is
					// optional, and it JSF 1.x <name> element is not permitted by the XML Schema. Regardless, use the
					// URL as the name in order to uniquely identify the configuration.
					String facesConfigName = facesConfigDescriptor.getName();

					if (facesConfigName == null) {

						// Example #1 (JRebel ClassLoader URL):
						// file:/Projects/liferay-faces/bridge-impl/target/classes/META-INF/faces-config.xml
						// Example #2 (Typical ClassLoader URL):
						// jar:file:/Servers/liferay-portal/tomcat/webapps/WEB-INF/lib/liferay-faces-bridge-impl.jar!/META-INF/faces-config.xml
						facesConfigName = facesConfigURL.toString();
					}

					if (LIFERAY_FACES_BRIDGE.equals(facesConfigName) ||
							((facesConfigName.indexOf("liferay-faces") >= 0) &&
								(facesConfigName.indexOf("bridge-impl") > 0))) {
						facesConfigDescriptors.add(0, facesConfigDescriptor);
					}
					else {
						facesConfigDescriptors.add(facesConfigDescriptor);
					}

					facesConfigName = null;

					try {
						inputStream.close();
					}
					catch (IOException e) {
						logger.error(e);
					}
				}

				for (FacesConfigDescriptor facesConfigDescriptor : facesConfigDescriptors) {

					URL facesConfigURL = facesConfigDescriptor.getURL();
					logger.debug("Post-processing faces-config: [{0}]", facesConfigURL);

					InputStream inputStream = facesConfigURL.openStream();

					try {
						facesConfig = facesConfigParser.parse(inputStream, facesConfig);
					}
					catch (IOException e) {
						logger.error(e);
					}

					try {
						inputStream.close();
					}
					catch (Exception e) {
						logger.error(e);
					}

					try {
						saxParser.reset();
					}
					catch (Exception e) {
						logger.error(e);
					}
				}
			}

			// Second, parse the WEB-INF/faces-config.xml descriptor. Any entries made here will take
			// precedence over those found previously.
			InputStream inputStream = resourceReader.getResourceAsStream(FACES_CONFIG_WEB_INF_PATH);

			if (inputStream != null) {
				logger.debug("Processing faces-config: [{0}]", FACES_CONFIG_WEB_INF_PATH);
				facesConfig = facesConfigParser.parse(inputStream, facesConfig);
				inputStream.close();
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return facesConfig;
	}

	protected FacesConfigDescriptorParser newFacesConfigDescriptorParser() {
		return new FacesConfigDescriptorParserImpl(saxParser, resolveEntities);
	}

	protected FacesConfigParser newFacesConfigParser() {
		return new FacesConfigParserImpl(saxParser, resolveEntities);
	}

	protected ResourceReader getResourceReader() {
		return resourceReader;
	}

	protected boolean isResolveEntities() {
		return resolveEntities;
	}

	protected SAXParser getSAXParser() {
		return saxParser;
	}

	protected WebConfig getWebConfig() {
		return webConfig;
	}
}
