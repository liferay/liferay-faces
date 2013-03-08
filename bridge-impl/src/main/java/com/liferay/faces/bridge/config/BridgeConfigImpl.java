/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.ViewHandler;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import com.liferay.faces.bridge.bean.BeanManagerFactory;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeConfigImpl implements BridgeConfig {

	// Private Constants
	private static final String FACES_VIEW_ID_RENDER = "_facesViewIdRender";
	private static final String FACES_VIEW_ID_RESOURCE = "_facesViewIdResource";
	private static final String FACES_CONFIG_META_INF_PATH = "META-INF/faces-config.xml";
	private static final String FACES_CONFIG_WEB_INF_PATH = "/WEB-INF/faces-config.xml";
	private static final String LIFERAY_FACES_BRIDGE = "LiferayFacesBridge";
	private static final String MOJARRA_CONFIG_PATH = "com/sun/faces/jsf-ri-runtime.xml";
	private static final String WEB_XML_PATH = "/WEB-INF/web.xml";
	private static final String WEB_XML_LIFERAY_PATH = "/WEB-INF/liferay-web.xml";
	private static final String WEB_FRAGMENT_META_INF_PATH = "META-INF/web-fragment.xml";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeConfigImpl.class);

	// Private Data Members
	private BridgeConfigAttributeMap bridgeConfigAttributeMap = new BridgeConfigAttributeMapImpl();
	private List<ConfiguredBean> configuredBeans = new ArrayList<ConfiguredBean>();
	private List<ConfiguredSystemEventListener> configuredSystemEventListeners =
		new ArrayList<ConfiguredSystemEventListener>();
	private List<String> defaultSuffixes;
	private Set<String> excludedBridgeRequestAttributes = new HashSet<String>();
	private List<ServletMapping> facesServletMappings;
	private PortletContext portletContext;
	private Map<String, String[]> publicParameterMappings = new HashMap<String, String[]>();
	private String viewIdRenderParameterName;
	private String viewIdResourceParameterName;
	private String writeBehindRenderResponseWrapper;
	private String writeBehindResourceResponseWrapper;

	public BridgeConfigImpl(PortletConfig portletConfig) {

		this.portletContext = portletConfig.getPortletContext();

		try {

			// Obtain a SAX Parser Factory.
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			boolean validating = false;
			saxParserFactory.setValidating(validating);
			saxParserFactory.setNamespaceAware(true);

			// Obtain a SAX Parser from the factory.
			SAXParser saxParser = saxParserFactory.newSAXParser();

			// Determine whether or not entities should be resolved. Due to slow performance in the
			// SAXEventHandler.resolveEntity(String, String) method it's best to set the default value of this to false.
			boolean defaultValue = false;
			String initParam = portletContext.getInitParameter(
					BridgeConfigConstants.PARAM_REQUIRED_TO_RESOLVE_XML_ENTITIES1);

			if (initParam == null) {

				// Backward compatibility
				initParam = portletContext.getInitParameter(
						BridgeConfigConstants.PARAM_REQUIRED_TO_RESOLVE_XML_ENTITIES2);
			}

			boolean resolveEntities = BooleanHelper.toBoolean(initParam, defaultValue);

			SAXHandler mojarraConfigHandler = new SAXHandlerMojarraConfigImpl(resolveEntities,
					configuredSystemEventListeners);

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
						mojarraConfigHandler.setURL(mojarraConfigURL);

						InputStream inputStream = mojarraConfigURL.openStream();

						try {
							saxParser.parse(inputStream, mojarraConfigHandler);
						}
						catch (SAXParseCompleteException e) {
							// ignore
						}
						catch (Exception e) {
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

			mojarraConfigHandler.logMissingElementErrors();

			// Define the SAX 2 callback faces-config event handlers for the SAX Parser.
			SAXHandlerFacesConfigPre facesConfigPreHandler = new SAXHandlerFacesConfigPreImpl(resolveEntities);
			SAXHandlerFacesConfigPost facesConfigPostHandler = new SAXHandlerFacesConfigPostImpl(resolveEntities,
					bridgeConfigAttributeMap, configuredBeans, excludedBridgeRequestAttributes,
					publicParameterMappings);

			// First, parse all of the META-INF/faces-config.xml files found in the classpath.
			Enumeration<URL> facesConfigURLs = classLoader.getResources(FACES_CONFIG_META_INF_PATH);

			List<FacesConfig> facesConfigList = new ArrayList<FacesConfig>();

			if (facesConfigURLs != null) {

				// Build up a semi-sorted list of faces-config.xml descriptor files, ensuring that the bridge's
				// META-INF/faces-config.xml descriptor is ordered first. (Note that the JSF 2.0 <ordering> element is
				// not yet supported.)
				while (facesConfigURLs.hasMoreElements()) {

					URL facesConfigURL = facesConfigURLs.nextElement();

					logger.debug("Pre-processing faces-config: [{0}]", facesConfigURL);
					facesConfigPreHandler.setURL(facesConfigURL);

					InputStream inputStream = facesConfigURL.openStream();

					try {
						saxParser.parse(inputStream, facesConfigPreHandler);
					}
					catch (SAXParseCompleteException e) {
						// ignore
					}
					catch (Exception e) {
						logger.error(e);
					}

					// If the name is not set, then the <name> element was omitted. In JSF 2.x the <name> element is
					// optional, and it JSF 1.x <name> element is not permitted by the XML Schema. Regardless, use the
					// URL as the name in order to uniquely identify the configuration.
					String facesConfigName = facesConfigPreHandler.getFacesConfigName();

					if (facesConfigName == null) {

						// Example #1 (JRebel ClassLoader URL):
						// file:/Projects/liferay-faces/bridge-impl/target/classes/META-INF/faces-config.xml
						// Example #2 (Typical ClassLoader URL):
						// jar:file:/Servers/liferay-portal/tomcat/webapps/WEB-INF/lib/liferay-faces-bridge-impl.jar!/META-INF/faces-config.xml
						facesConfigName = facesConfigURL.toString();
					}

					FacesConfig curFacesConfig = new FacesConfigImpl(facesConfigName, facesConfigURL);

					if (LIFERAY_FACES_BRIDGE.equals(facesConfigName) ||
							((facesConfigName.indexOf("liferay-faces") >= 0) &&
								(facesConfigName.indexOf("bridge-impl") > 0))) {
						facesConfigList.add(0, curFacesConfig);
					}
					else {
						facesConfigList.add(curFacesConfig);
					}

					facesConfigName = null;

					try {
						inputStream.close();
					}
					catch (IOException e) {
						logger.error(e);
					}
				}

				for (FacesConfig facesConfig : facesConfigList) {

					URL facesConfigURL = facesConfig.getURL();
					logger.debug("Post-processing faces-config: [{0}]", facesConfigURL);
					facesConfigPostHandler.setURL(facesConfigURL);

					InputStream inputStream = facesConfigURL.openStream();

					try {
						saxParser.parse(inputStream, facesConfigPostHandler);
					}
					catch (Exception e) {
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

			facesConfigPreHandler.logMissingElementErrors();
			facesConfigPostHandler.logMissingElementErrors();
			writeBehindRenderResponseWrapper = facesConfigPostHandler.getWriteBehindRenderResponseWrapper();
			writeBehindResourceResponseWrapper = facesConfigPostHandler.getWriteBehindResourceResponseWrapper();

			// Second, parse the WEB-INF/faces-config.xml descriptor. Any entries made here will take
			// precedence over those found previously.
			InputStream inputStream = portletContext.getResourceAsStream(FACES_CONFIG_WEB_INF_PATH);

			if (inputStream != null) {
				logger.debug("Processing faces-config: [{0}]", FACES_CONFIG_WEB_INF_PATH);
				saxParser.parse(inputStream, facesConfigPostHandler);
				inputStream.close();
			}

			// Initialize the bean-manager-factory with the list of configured beans that wre discovered.
			BeanManagerFactory beanManagerFactory = (BeanManagerFactory) bridgeConfigAttributeMap.get(
					BeanManagerFactory.class.getName());
			beanManagerFactory.setConfiguredBeans(configuredBeans);

			// Parse the Servlet 3.0 META-INF/web-fragment.xml descriptor files found in the classpath.
			facesServletMappings = new ArrayList<ServletMapping>();

			Enumeration<URL> webFragmentURLs = classLoader.getResources(WEB_FRAGMENT_META_INF_PATH);

			if (webFragmentURLs != null) {

				while (webFragmentURLs.hasMoreElements()) {
					URL webFragmentURL = webFragmentURLs.nextElement();
					inputStream = webFragmentURL.openStream();

					DefaultHandler webConfigHandler = new SAXHandlerWebConfigImpl(resolveEntities,
							facesServletMappings);

					try {
						saxParser.parse(inputStream, webConfigHandler);
						inputStream.close();
						saxParser.reset();
					}
					catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}

			// Parse the WEB-INF/web.xml descriptor.
			inputStream = portletContext.getResourceAsStream(WEB_XML_PATH);

			if (inputStream != null) {
				logger.debug("Processing web-app: [{0}]", WEB_XML_PATH);

				DefaultHandler webConfigHandler = new SAXHandlerWebConfigImpl(resolveEntities, facesServletMappings);
				saxParser.parse(inputStream, webConfigHandler);
			}

			// Parse the WEB-INF/liferay-web.xml descriptor.
			inputStream = portletContext.getResourceAsStream(WEB_XML_LIFERAY_PATH);

			if (inputStream != null) {
				logger.debug("Processing web-app: [{0}]", WEB_XML_LIFERAY_PATH);

				DefaultHandler webConfigHandler = new SAXHandlerWebConfigImpl(resolveEntities, facesServletMappings);
				saxParser.parse(inputStream, webConfigHandler);
			}

			// Get the default suffixes (configured extensions)
			String defaultSuffixParam = portletConfig.getInitParameter(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME);

			if (defaultSuffixParam == null) {
				defaultSuffixParam = ViewHandler.DEFAULT_SUFFIX;
			}

			defaultSuffixes = Arrays.asList(defaultSuffixParam.split(StringPool.SPACE));

			// If they don't exist explicitly in web.xml, then setup implicit servlet-mapping entries to the default
			// suffixes.
			for (String defaultSuffix : defaultSuffixes) {

				boolean found = false;

				for (ServletMapping explicitFacesServletMapping : facesServletMappings) {

					if (explicitFacesServletMapping.isExtensionMapped() &&
							explicitFacesServletMapping.getExtension().equals(defaultSuffix)) {
						found = true;

						break;
					}
				}

				if (!found) {
					String urlPattern = StringPool.STAR + defaultSuffix;
					ServletMapping implicitFacesServletMapping = new ServletMappingImpl(urlPattern);
					facesServletMappings.add(implicitFacesServletMapping);
					logger.debug("Added implicit extension-mapped servlet-mapping for urlPattern=[{0}]", urlPattern);
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Map<String, Object> getAttributes() {
		return bridgeConfigAttributeMap;
	}

	public List<String> getConfiguredExtensions() {
		return defaultSuffixes;
	}

	public List<ConfiguredSystemEventListener> getConfiguredSystemEventListeners() {
		return configuredSystemEventListeners;
	}

	public String getContextParameter(String name) {
		return portletContext.getInitParameter(name);
	}

	public Set<String> getExcludedRequestAttributes() {
		return excludedBridgeRequestAttributes;
	}

	public List<ServletMapping> getFacesServletMappings() {
		return facesServletMappings;
	}

	public Map<String, String[]> getPublicParameterMappings() {
		return publicParameterMappings;
	}

	public String getViewIdRenderParameterName() {

		if (viewIdRenderParameterName == null) {

			viewIdRenderParameterName = portletContext.getInitParameter(BridgeConfigConstants.PARAM_VIEW_ID_RENDER);

			if (viewIdRenderParameterName == null) {
				viewIdRenderParameterName = FACES_VIEW_ID_RENDER;
			}
		}

		return viewIdRenderParameterName;
	}

	public String getViewIdResourceParameterName() {

		if (viewIdResourceParameterName == null) {

			viewIdResourceParameterName = portletContext.getInitParameter(BridgeConfigConstants.PARAM_VIEW_ID_RESOURCE);

			if (viewIdResourceParameterName == null) {
				viewIdResourceParameterName = FACES_VIEW_ID_RESOURCE;
			}
		}

		return viewIdResourceParameterName;
	}

	public String getWriteBehindRenderResponseWrapper() {
		return writeBehindRenderResponseWrapper;
	}

	public String getWriteBehindResourceResponseWrapper() {
		return writeBehindResourceResponseWrapper;
	}
}
