/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
import javax.faces.webapp.FacesServlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.BridgePhaseFactory;
import com.liferay.faces.bridge.application.view.BridgeWriteBehindResponseFactory;
import com.liferay.faces.bridge.container.PortletContainerFactory;
import com.liferay.faces.bridge.context.BridgeContextFactory;
import com.liferay.faces.bridge.context.IncongruityContextFactory;
import com.liferay.faces.bridge.context.flash.BridgeFlashFactory;
import com.liferay.faces.bridge.context.url.BridgeURLFactory;
import com.liferay.faces.bridge.model.UploadedFileFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScopeCacheFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScopeFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManagerFactory;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeConfigImpl implements BridgeConfig {

	// Private Constants
	private static final String BRIDGE_CONTEXT_FACTORY = "bridge-context-factory";
	private static final String BRIDGE_FLASH_FACTORY = "bridge-flash-factory";
	private static final String BRIDGE_PHASE_FACTORY = "bridge-phase-factory";
	private static final String BRIDGE_REQUEST_SCOPE_FACTORY = "bridge-request-scope-factory";
	private static final String BRIDGE_REQUEST_SCOPE_CACHE_FACTORY = "bridge-request-scope-cache-factory";
	private static final String BRIDGE_REQUEST_SCOPE_MANAGER_FACTORY = "bridge-request-scope-manager-factory";
	private static final String BRIDGE_WRITE_BEHIND_RESPONSE_FACTORY = "bridge-write-behind-response-factory";
	private static final String BRIDGE_URL_FACTORY = "bridge-url-factory";
	private static final String FACES_VIEW_ID_RENDER = "_facesViewIdRender";
	private static final String FACES_VIEW_ID_RESOURCE = "_facesViewIdResource";
	private static final String FACES_SERVLET_FQCN = FacesServlet.class.getName();
	private static final String FACTORY_NOT_FOUND_MSG = "Factory not found in any faces-config.xml files: [{0}]";
	private static final String INCONGRUITY_CONTEXT_FACTORY = "incongruity-context-factory";
	private static final String PORTLET_CONTAINER_FACTORY = "portlet-container-factory";
	private static final String FACES_CONFIG_META_INF_PATH = "META-INF/faces-config.xml";
	private static final String FACES_CONFIG_WEB_INF_PATH = "/WEB-INF/faces-config.xml";
	private static final String LIFERAY_FACES_BRIDGE = "LiferayFacesBridge";
	private static final String NAME = "name";
	private static final String RENDER_RESPONSE_WRAPPER_CLASS = "render-response-wrapper-class";
	private static final String RESOURCE_RESPONSE_WRAPPER_CLASS = "resource-response-wrapper-class";
	private static final String SERVLET = "servlet";
	private static final String SERVLET_CLASS = "servlet-class";
	private static final String SERVLET_MAPPING = "servlet-mapping";
	private static final String SERVLET_NAME = "servlet-name";
	private static final String UPLOADED_FILE_FACTORY = "uploaded-file-factory";
	private static final String URL_PATTERN = "url-pattern";
	private static final String WEB_XML_PATH = "/WEB-INF/web.xml";
	private static final String WEB_XML_LIFERAY_PATH = "/WEB-INF/liferay-web.xml";
	private static final String WEB_FRAGMENT_META_INF_PATH = "META-INF/web-fragment.xml";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeConfigImpl.class);

	// Private Data Members
	private BridgeConfigAttributeMap attributes;
	private BridgeContextFactory bridgeContextFactory;
	private BridgeFlashFactory bridgeFlashFactory;
	private BridgePhaseFactory bridgePhaseFactory;
	private BridgeRequestScopeFactory bridgeRequestScopeFactory;
	private BridgeRequestScopeCacheFactory bridgeRequestScopeCacheFactory;
	private BridgeRequestScopeManagerFactory bridgeRequestScopeManagerFactory;
	private BridgeWriteBehindResponseFactory bridgeWriteBehindResponseFactory;
	private BridgeURLFactory bridgeURLFactory;
	private List<String> defaultSuffixes;
	private Set<String> excludedBridgeRequestAttributes = new HashSet<String>();
	private String facesConfigName;
	private List<ServletMapping> facesServletMappings;
	private IncongruityContextFactory incongruityContextFactory;
	private PortletContainerFactory portletContainerFactory;
	private PortletContext portletContext;
	private Map<String, String[]> publicParameterMappings = new HashMap<String, String[]>();
	private UploadedFileFactory uploadedFileFactory;
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

			// Define the SAX 2 callback event handlers for the SAX Parser.
			FacesConfigPreHandler facesConfigPreHandler = new FacesConfigPreHandler(resolveEntities);
			FacesConfigPostHandler facesConfigPostHandler = new FacesConfigPostHandler(resolveEntities);

			// First, parse all of the META-INF/faces-config.xml files found in the classpath.
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
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
						logger.error(e.getMessage());
					}

					if (facesConfigName == null) {
						facesConfigName = facesConfigURL.toString();
					}

					FacesConfig facesConfig = new FacesConfig(facesConfigName, facesConfigURL);

					if (LIFERAY_FACES_BRIDGE.equals(facesConfigName)) {
						facesConfigList.add(0, facesConfig);
					}
					else {
						facesConfigList.add(facesConfig);
					}

					facesConfigName = null;
				}

				for (FacesConfig facesConfig : facesConfigList) {

					URL facesConfigURL = facesConfig.getURL();
					logger.debug("Post-processing faces-config: [{0}]", facesConfigURL);
					facesConfigPostHandler.setURL(facesConfigURL);

					InputStream inputStream = facesConfigURL.openStream();

					try {
						saxParser.parse(inputStream, facesConfigPostHandler);
						inputStream.close();
						saxParser.reset();
					}
					catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}

			// Second, parse the WEB-INF/faces-config.xml descriptor. Any entries made here will take
			// precedence over those found previously.
			InputStream inputStream = portletContext.getResourceAsStream(FACES_CONFIG_WEB_INF_PATH);

			if (inputStream != null) {
				logger.debug("Processing faces-config: [{0}]", FACES_CONFIG_WEB_INF_PATH);
				saxParser.parse(inputStream, facesConfigPostHandler);
				inputStream.close();
			}

			if (bridgeFlashFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_FLASH_FACTORY);
			}

			if (bridgePhaseFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_PHASE_FACTORY);
			}

			if (bridgeContextFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_CONTEXT_FACTORY);
			}

			if (bridgeRequestScopeFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_REQUEST_SCOPE_FACTORY);
			}

			if (bridgeRequestScopeCacheFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_REQUEST_SCOPE_CACHE_FACTORY);
			}

			if (bridgeRequestScopeManagerFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_REQUEST_SCOPE_MANAGER_FACTORY);
			}

			if (bridgeWriteBehindResponseFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_WRITE_BEHIND_RESPONSE_FACTORY);
			}

			if (bridgeURLFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_URL_FACTORY);
			}

			if (incongruityContextFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, INCONGRUITY_CONTEXT_FACTORY);
			}

			if (portletContainerFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, PORTLET_CONTAINER_FACTORY);
			}

			if (uploadedFileFactory == null) {
				logger.error(FACTORY_NOT_FOUND_MSG, UPLOADED_FILE_FACTORY);
			}

			// Parse the Servlet 3.0 META-INF/web-fragment.xml descriptor files found in the classpath.
			facesServletMappings = new ArrayList<ServletMapping>();

			Enumeration<URL> webFragmentURLs = classLoader.getResources(WEB_FRAGMENT_META_INF_PATH);

			if (webFragmentURLs != null) {

				while (webFragmentURLs.hasMoreElements()) {
					URL webFragmentURL = webFragmentURLs.nextElement();
					inputStream = webFragmentURL.openStream();

					WebAppHandler webAppHandler = new WebAppHandler(resolveEntities);

					try {
						saxParser.parse(inputStream, webAppHandler);
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

				WebAppHandler webAppHandler = new WebAppHandler(resolveEntities);
				saxParser.parse(inputStream, webAppHandler);
			}

			// Parse the WEB-INF/liferay-web.xml descriptor.
			inputStream = portletContext.getResourceAsStream(WEB_XML_LIFERAY_PATH);

			if (inputStream != null) {
				logger.debug("Processing web-app: [{0}]", WEB_XML_LIFERAY_PATH);

				WebAppHandler webAppHandler = new WebAppHandler(resolveEntities);
				saxParser.parse(inputStream, webAppHandler);
			}

			// Get the default suffixes (configured extensions)
			String defaultSuffixParam = portletConfig.getInitParameter(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME);

			if (defaultSuffixParam == null) {
				defaultSuffixParam = ViewHandler.DEFAULT_SUFFIX;
			}

			defaultSuffixes = Arrays.asList(defaultSuffixParam.split(BridgeConstants.CHAR_SPACE));

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
					String urlPattern = BridgeConstants.CHAR_ASTERISK + defaultSuffix;
					ServletMapping implicitFacesServletMapping = new ServletMappingImpl(urlPattern);
					facesServletMappings.add(implicitFacesServletMapping);
					logger.debug("Added implicit extension-mapped servlet-mapping for urlPattern=[{0}]", urlPattern);
				}
			}

			// Initialize the map of attributes
			attributes = new BridgeConfigAttributeMap();
			attributes.put(BridgeContextFactory.class.getName(), bridgeContextFactory);
			attributes.put(BridgeFlashFactory.class.getName(), bridgeFlashFactory);
			attributes.put(BridgePhaseFactory.class.getName(), bridgePhaseFactory);
			attributes.put(BridgeRequestScopeFactory.class.getName(), bridgeRequestScopeFactory);
			attributes.put(BridgeRequestScopeCacheFactory.class.getName(), bridgeRequestScopeCacheFactory);
			attributes.put(BridgeRequestScopeManagerFactory.class.getName(), bridgeRequestScopeManagerFactory);
			attributes.put(BridgeWriteBehindResponseFactory.class.getName(), bridgeWriteBehindResponseFactory);
			attributes.put(BridgeURLFactory.class.getName(), bridgeURLFactory);
			attributes.put(IncongruityContextFactory.class.getName(), incongruityContextFactory);
			attributes.put(PortletContainerFactory.class.getName(), portletContainerFactory);
			attributes.put(UploadedFileFactory.class.getName(), uploadedFileFactory);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected Object newFactoryInstance(String className, Class<?> factoryClassType, Object wrappedFactory)
		throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Object classInstance = null;

		if (classLoader != null) {

			if (className != null) {
				Class<?> factoryClass = classLoader.loadClass(className);
				Constructor<?> wrapperConstructor = null;
				Constructor<?>[] constructors = factoryClass.getDeclaredConstructors();

				for (Constructor<?> constructor : constructors) {
					Class<?>[] parameterTypes = constructor.getParameterTypes();

					if ((parameterTypes != null) && (parameterTypes.length == 1) &&
							(parameterTypes[0] == factoryClassType)) {
						wrapperConstructor = constructor;
					}
				}

				if (wrapperConstructor == null) {
					logger.debug("Creating instance with zero-arg constructor since wrapperConstructor=null");
					classInstance = factoryClass.newInstance();
				}
				else {
					logger.debug("Creating instance with one-arg constructor since wrapperConstructor=[{0}]",
						wrapperConstructor);
					classInstance = wrapperConstructor.newInstance(new Object[] { wrappedFactory });
				}
			}
		}

		return classInstance;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public List<String> getConfiguredExtensions() {
		return defaultSuffixes;
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

	protected class BaseHandler extends DefaultHandler {

		// Private Data Members
		protected StringBuilder content;
		private boolean resolveEntities = false;
		private URL url;

		public BaseHandler(boolean resolveEntities) {
			super();
			this.resolveEntities = resolveEntities;
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {

			if ((content != null) && (ch != null) && (length > 0)) {
				content.append(ch, start, length);
			}
		}

		@Override
		public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {

			InputSource inputSource = new InputSource(new StringReader(""));

			if (resolveEntities) {
				inputSource = super.resolveEntity(publicId, systemId);

				if (inputSource == null) {

					try {

						// Note: Not sure why, but following line of code has suffered terrible performance problems.
						// At times, it could take over a minute for the stream to open. This is why the web.xml
						// default for resolving entities is false.
						inputSource = new InputSource(new URL(systemId).openStream());
					}
					catch (IOException ioException) {

						// Don't bother logging this as a warning or an error, because we can't assume connectivity to
						// the Internet to download a public URL.
						logger.trace("Unable to download publicId=[{0}], systemId=[{1}], referenced-in=[{2}]",
							new Object[] { publicId, systemId, url });
					}
				}
			}

			return inputSource;
		}

		public void setURL(URL url) {
			this.url = url;
		}
	}

	protected class BridgeConfigAttributeMap extends HashMap<String, Object> {

		// serialVersionUID
		private static final long serialVersionUID = 8763346476317251569L;

		@Override
		public Object get(Object key) {
			Object value = super.get(key);

			if (value == null) {
				value = ProductMap.getInstance().get(key);
			}

			return value;
		}
	}

	protected class FacesConfig {

		private String name;
		private URL url;

		public FacesConfig(String name, URL url) {
			this.name = name;
			this.url = url;
		}

		public String getName() {
			return name;
		}

		public URL getURL() {
			return url;
		}
	}

	protected class FacesConfigPostHandler extends BaseHandler {

		// Private Constants
		private static final String EXCLUDED_ATTRIBUTE = "excluded-attribute";
		private static final String MODEL_EL = "model-el";
		private static final String PARAMETER = "parameter";

		// Private Data Members
		private boolean parsingBridgeContextFactory = false;
		private boolean parsingBridgeFlashFactory = false;
		private boolean parsingBridgePhaseFactory = false;
		private boolean parsingBridgeRequestScopeFactory = false;
		private boolean parsingBridgeRequestScopeCacheFactory = false;
		private boolean parsingBridgeRequestScopeManagerFactory = false;
		private boolean parsingBridgeWriteBehindResponseFactory = false;
		private boolean parsingBridgeURLFactory = false;
		private boolean parsingExcludedAttribute = false;
		private boolean parsingIncongruityContextFactory;
		private boolean parsingModelEL = false;
		private boolean parsingParameter = false;
		private boolean parsingPortletContainerFactory = false;
		private boolean parsingRenderResponseWrapperClass = false;
		private boolean parsingResourceResponseWrapperClass = false;
		private String parameter;
		private boolean parsingUploadedFileFactory = false;

		public FacesConfigPostHandler(boolean resolveEntities) {
			super(resolveEntities);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {

			if (parsingBridgeContextFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						BridgeContextFactory wrappedFactory = BridgeConfigImpl.this.bridgeContextFactory;
						BridgeConfigImpl.this.bridgeContextFactory = (BridgeContextFactory) newFactoryInstance(
								factoryClassName, BridgeContextFactory.class, wrappedFactory);
						logger.debug("Instantiated bridgeContextFactory=[{0}] wrappedFactory=[{1}]", factoryClassName,
							wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			else if (parsingBridgeFlashFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						BridgeFlashFactory wrappedFactory = BridgeConfigImpl.this.bridgeFlashFactory;
						BridgeConfigImpl.this.bridgeFlashFactory = (BridgeFlashFactory) newFactoryInstance(
								factoryClassName, BridgeFlashFactory.class, wrappedFactory);
						logger.debug("Instantiated bridgeFlashFactory=[{0}] wrappedFactory=[{1}]", factoryClassName,
							wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			else if (parsingBridgePhaseFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						BridgePhaseFactory wrappedFactory = BridgeConfigImpl.this.bridgePhaseFactory;
						BridgeConfigImpl.this.bridgePhaseFactory = (BridgePhaseFactory) newFactoryInstance(
								factoryClassName, BridgePhaseFactory.class, wrappedFactory);
						logger.debug("Instantiated bridgePhaseFactory=[{0}] wrappedFactory=[{1}]", factoryClassName,
							wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			else if (parsingBridgeRequestScopeFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						BridgeRequestScopeFactory wrappedFactory = BridgeConfigImpl.this.bridgeRequestScopeFactory;
						BridgeConfigImpl.this.bridgeRequestScopeFactory = (BridgeRequestScopeFactory)
							newFactoryInstance(factoryClassName, BridgeRequestScopeFactory.class, wrappedFactory);
						logger.debug("Instantiated BridgeRequestScopeFactory=[{0}] wrappedFactory=[{1}]",
							factoryClassName, wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			else if (parsingBridgeRequestScopeCacheFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						BridgeRequestScopeCacheFactory wrappedFactory =
							BridgeConfigImpl.this.bridgeRequestScopeCacheFactory;
						BridgeConfigImpl.this.bridgeRequestScopeCacheFactory = (BridgeRequestScopeCacheFactory)
							newFactoryInstance(factoryClassName, BridgeRequestScopeCacheFactory.class, wrappedFactory);
						logger.debug("Instantiated BridgeRequestScopeCacheFactory=[{0}] wrappedFactory=[{1}]",
							factoryClassName, wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			else if (parsingBridgeRequestScopeManagerFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						BridgeRequestScopeManagerFactory wrappedFactory =
							BridgeConfigImpl.this.bridgeRequestScopeManagerFactory;
						BridgeConfigImpl.this.bridgeRequestScopeManagerFactory = (BridgeRequestScopeManagerFactory)
							newFactoryInstance(factoryClassName, BridgeRequestScopeManagerFactory.class,
								wrappedFactory);
						logger.debug("Instantiated BridgeRequestScopeManagerFactory=[{0}] wrappedFactory=[{1}]",
							factoryClassName, wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			else if (parsingBridgeWriteBehindResponseFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						BridgeWriteBehindResponseFactory wrappedFactory =
							BridgeConfigImpl.this.bridgeWriteBehindResponseFactory;
						BridgeConfigImpl.this.bridgeWriteBehindResponseFactory = (BridgeWriteBehindResponseFactory)
							newFactoryInstance(factoryClassName, BridgeWriteBehindResponseFactory.class,
								wrappedFactory);
						logger.debug("Instantiated BridgeWriteBehindResponseFactory=[{0}] wrappedFactory=[{1}]",
							factoryClassName, wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			else if (parsingBridgeURLFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						BridgeURLFactory wrappedFactory = BridgeConfigImpl.this.bridgeURLFactory;
						BridgeConfigImpl.this.bridgeURLFactory = (BridgeURLFactory) newFactoryInstance(factoryClassName,
								BridgeURLFactory.class, wrappedFactory);
						logger.debug("Instantiated BridgeURLFactory=[{0}] wrappedFactory=[{1}]", factoryClassName,
							wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			else if (parsingExcludedAttribute) {
				String attributeName = content.toString().trim();

				if (attributeName.length() > 0) {
					BridgeConfigImpl.this.excludedBridgeRequestAttributes.add(attributeName);
				}
			}
			else if (parsingIncongruityContextFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						IncongruityContextFactory wrappedFactory = BridgeConfigImpl.this.incongruityContextFactory;
						BridgeConfigImpl.this.incongruityContextFactory = (IncongruityContextFactory)
							newFactoryInstance(factoryClassName, IncongruityContextFactory.class, wrappedFactory);
						logger.debug("Instantiated LifecycleIncongruityManagerFactory=[{0}] wrappedFactory=[{1}]",
							factoryClassName, wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			else if (parsingModelEL) {

				String additionalValue = content.toString().trim();

				if (additionalValue.length() > 0) {
					String[] newValue = new String[] { additionalValue };
					String[] existingValue = BridgeConfigImpl.this.publicParameterMappings.get(parameter);

					if (existingValue != null) {
						int total = existingValue.length + 1;
						newValue = new String[total];

						for (int i = 0; i < existingValue.length; i++) {
							newValue[i] = existingValue[i];
						}

						newValue[total - 1] = additionalValue;
					}

					BridgeConfigImpl.this.publicParameterMappings.put(parameter, newValue);
				}
			}
			else if (parsingParameter) {
				parameter = content.toString().trim();
			}
			else if (parsingPortletContainerFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						PortletContainerFactory wrappedFactory = BridgeConfigImpl.this.portletContainerFactory;
						BridgeConfigImpl.this.portletContainerFactory = (PortletContainerFactory) newFactoryInstance(
								factoryClassName, PortletContainerFactory.class, wrappedFactory);
						logger.debug("Instantiated PortletContainerFactory=[{0}] wrappedFactory=[{1}]",
							factoryClassName, wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			else if (parsingRenderResponseWrapperClass) {
				writeBehindRenderResponseWrapper = content.toString().trim();
				logger.debug("render-response-wrapper-class=[{0}]", writeBehindRenderResponseWrapper);
			}
			else if (parsingResourceResponseWrapperClass) {
				writeBehindResourceResponseWrapper = content.toString().trim();
				logger.debug("resource-response-wrapper-class=[{0}]", writeBehindResourceResponseWrapper);
			}
			else if (parsingUploadedFileFactory) {
				String factoryClassName = content.toString().trim();

				if (factoryClassName.length() > 0) {

					try {
						UploadedFileFactory wrappedFactory = BridgeConfigImpl.this.uploadedFileFactory;
						BridgeConfigImpl.this.uploadedFileFactory = (UploadedFileFactory) newFactoryInstance(
								factoryClassName, UploadedFileFactory.class, wrappedFactory);
						logger.debug("Instantiated UploadedFileFactory=[{0}] wrappedFactory=[{1}]", factoryClassName,
							wrappedFactory);
					}
					catch (ClassNotFoundException e) {
						logger.error("{0} : factoryClassName=[{1}]", e.getClass().getName(), factoryClassName);
					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}

			content = null;
			parsingBridgeContextFactory = false;
			parsingBridgeFlashFactory = false;
			parsingBridgePhaseFactory = false;
			parsingBridgeRequestScopeFactory = false;
			parsingBridgeRequestScopeCacheFactory = false;
			parsingBridgeRequestScopeManagerFactory = false;
			parsingBridgeWriteBehindResponseFactory = false;
			parsingBridgeURLFactory = false;
			parsingExcludedAttribute = false;
			parsingIncongruityContextFactory = false;
			parsingModelEL = false;
			parsingParameter = false;
			parsingPortletContainerFactory = false;
			parsingRenderResponseWrapperClass = false;
			parsingResourceResponseWrapperClass = false;
			parsingUploadedFileFactory = false;
		}

		@Override
		public void startElement(String uri, String localName, String elementName, Attributes attributes)
			throws SAXException {
			logger.trace("localName=[{0}]", localName);

			content = new StringBuilder();

			if (localName.equals(BRIDGE_CONTEXT_FACTORY)) {
				parsingBridgeContextFactory = true;
			}
			else if (localName.equals(BRIDGE_FLASH_FACTORY)) {
				parsingBridgeFlashFactory = true;
			}
			else if (localName.equals(BRIDGE_PHASE_FACTORY)) {
				parsingBridgePhaseFactory = true;
			}
			else if (localName.equals(BRIDGE_REQUEST_SCOPE_FACTORY)) {
				parsingBridgeRequestScopeFactory = true;
			}
			else if (localName.equals(BRIDGE_REQUEST_SCOPE_CACHE_FACTORY)) {
				parsingBridgeRequestScopeCacheFactory = true;
			}
			else if (localName.equals(BRIDGE_REQUEST_SCOPE_MANAGER_FACTORY)) {
				parsingBridgeRequestScopeManagerFactory = true;
			}
			else if (localName.equals(BRIDGE_WRITE_BEHIND_RESPONSE_FACTORY)) {
				parsingBridgeWriteBehindResponseFactory = true;
			}
			else if (localName.equals(BRIDGE_URL_FACTORY)) {
				parsingBridgeURLFactory = true;
			}
			else if (localName.equals(EXCLUDED_ATTRIBUTE)) {
				parsingExcludedAttribute = true;
			}
			else if (localName.equals(INCONGRUITY_CONTEXT_FACTORY)) {
				parsingIncongruityContextFactory = true;
			}
			else if (localName.equals(MODEL_EL)) {
				parsingModelEL = true;
			}
			else if (localName.equals(PARAMETER)) {
				parsingParameter = true;
			}
			else if (localName.equals(PORTLET_CONTAINER_FACTORY)) {
				parsingPortletContainerFactory = true;
			}
			else if (localName.equals(RENDER_RESPONSE_WRAPPER_CLASS)) {
				parsingRenderResponseWrapperClass = true;
			}
			else if (localName.equals(RESOURCE_RESPONSE_WRAPPER_CLASS)) {
				parsingResourceResponseWrapperClass = true;
			}
			else if (localName.equals(UPLOADED_FILE_FACTORY)) {
				parsingUploadedFileFactory = true;
			}
		}

	}

	protected class FacesConfigPreHandler extends BaseHandler {

		// Private Data Members
		private boolean parsingName = false;

		public FacesConfigPreHandler(boolean resolveEntities) {
			super(resolveEntities);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {

			if (parsingName) {
				facesConfigName = content.toString().trim();
			}

			content = null;
			parsingName = false;

			if (facesConfigName != null) {

				// Abort processing since the <name>...</name> has been parsed.
				throw new SAXParseCompleteException();
			}
		}

		@Override
		public void startElement(String uri, String localName, String elementName, Attributes attributes)
			throws SAXException {

			logger.trace("localName=[{0}]", localName);

			content = new StringBuilder();

			if (localName.equals(NAME)) {
				parsingName = true;
			}
		}

	}

	protected class SAXParseCompleteException extends SAXException {
		private static final long serialVersionUID = 1170538769536627765L;
	}

	protected class WebAppHandler extends BaseHandler {

		// Private Data Members
		private String facesServletName = "Faces Servlet";
		private boolean parsingServlet = false;
		private boolean parsingServletClass = false;
		private boolean parsingServletMapping = false;
		private boolean parsingServletName = false;
		private boolean parsingUrlPattern = false;
		private String servletName;

		public WebAppHandler(boolean resolveEntities) {
			super(resolveEntities);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {

			if (parsingServlet) {

				if (parsingServletClass) {
					String servletClass = content.toString().trim();

					if (servletClass.length() > 0) {

						if (FACES_SERVLET_FQCN.equals(servletClass) && (servletName.length() > 0)) {
							facesServletName = servletName;
							logger.trace("servlet-class=[{0}] servlet-name=[{1}]", FACES_SERVLET_FQCN,
								facesServletName);
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
}
