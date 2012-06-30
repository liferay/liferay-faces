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
package javax.portlet.faces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;


/**
 * @author  Neil Griffin
 */
public class GenericFacesPortlet extends GenericPortlet {

	// Public Constants
	public static final String BRIDGE_CLASS = "javax.portlet.faces.BridgeImplClass";
	public static final String BRIDGE_SERVICE_CLASSPATH = "META-INF/services/javax.portlet.faces.Bridge";
	public static final String DEFAULT_VIEWID = "javax.portlet.faces.defaultViewId";

	// Private Constants
	private static final String CHAR_COMMA = ",";
	private static final String CHAR_DOT = ".";
	private static final String BRIDGE_AUTO_DISPATCH_EVENTS = "javax.portlet.faces.autoDispatchEvents";

	// Private Data Members
	private Boolean autoDispatchEvents;
	private Bridge bridge;
	private String bridgeClassName;
	private BridgeEventHandler bridgeEventHandler;
	private BridgePublicRenderParameterHandler bridgePublicRenderParameterHandler;
	private Map<String, String> defaultViewIdMap;
	private List<String> excludedRequestAttributes;
	private String portletName;
	private Boolean preserveActionParameters;

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {

		this.portletName = portletConfig.getPortletName();

		// Initialize the bridge according to the requirements set forth in Section 3.2 of the JSR 329 Spec. Begin
		// this process by delegating preliminary initialization to the parent class.
		super.init(portletConfig);

		// Initialize the bridge implementation instance.
		getBridge().init(portletConfig);

		// Save the default JSF views specified as WEB-INF/portlet.xml init-param value(s) as a portlet context
		// attribute with name "javax.portlet.faces.<portlet-name>.defaultViewIdMap"
		PortletContext portletContext = portletConfig.getPortletContext();
		String attributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletName + CHAR_DOT + Bridge.DEFAULT_VIEWID_MAP;
		portletContext.setAttribute(attributeName, getDefaultViewIdMap());

		// Save the "javax.portlet.faces.excludedRequestAttributes" init-param value(s) as a portlet context attribute
		// with name "javax.portlet.faces.<portlet-name>.excludedRequestAttributes"
		attributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletName + CHAR_DOT + Bridge.EXCLUDED_REQUEST_ATTRIBUTES;
		portletContext.setAttribute(attributeName, getExcludedRequestAttributes());

		// Save the "javax.portlet.faces.preserveActionParams" init-param value as a portlet context attribute with name
		// "javax.portlet.faces.<portlet-name>.preserveActionParams"
		attributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletName + CHAR_DOT + Bridge.PRESERVE_ACTION_PARAMS;
		portletContext.setAttribute(attributeName, new Boolean(isPreserveActionParameters()));

		// If a javax.portlet.faces.bridgeEventHandler is registered as an init-param in portlet.xml, then obtain an
		// instance of the handler and save it as a portlet context attribute as required by Section 3.2 of the JSR 329
		// Spec.
		BridgeEventHandler bridgeEventHandlerInstance = getBridgeEventHandler();

		if (bridgeEventHandlerInstance != null) {

			// Attribute name format: javax.portlet.faces.{portlet-name}.bridgeEventHandler
			attributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() + CHAR_DOT +
				Bridge.BRIDGE_EVENT_HANDLER;
			portletContext.setAttribute(attributeName, bridgeEventHandlerInstance);
		}

		// If a javax.portlet.faces.bridgePublicRenderParameterHandler is registered as an init-param in portlet.xml,
		// then obtain an instance of the handler and save it as a portlet context attribute as required by Section 3.2
		// of the JSR 329 Spec.
		BridgePublicRenderParameterHandler bridgePublicRenderParameterHandlerInstance =
			getBridgePublicRenderParameterHandler();

		if (bridgePublicRenderParameterHandlerInstance != null) {

			// Attribute name format: javax.portlet.faces.{portlet-name}.bridgePublicRenderParameterHandler
			String bridgeEventHandlerAttributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() +
				CHAR_DOT + Bridge.BRIDGE_PUBLIC_RENDER_PARAMETER_HANDLER;
			portletContext.setAttribute(bridgeEventHandlerAttributeName, bridgePublicRenderParameterHandlerInstance);
		}

		// If a javax.portlet.faces.defaultRenderKitId is specified as an init-param in WEB-INF/portlet.xml then
		// save it as a portlet context attribute as required by Section 4.2.16 of the JSR 329 Spec.
		String defaultRenderKitId = getDefaultRenderKitId();

		if (defaultRenderKitId != null) {
			portletContext.setAttribute(Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() + CHAR_DOT +
				Bridge.DEFAULT_RENDERKIT_ID, defaultRenderKitId);
		}
	}

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException,
		IOException {
		Bridge bridge = getFacesBridge(actionRequest, actionResponse);

		bridge.doFacesRequest(actionRequest, actionResponse);
	}

	@Override
	public void processEvent(EventRequest eventRequest, EventResponse eventResponse) throws PortletException,
		IOException {

		// If events are to be auto-dispatched to the bridge, then invoke the bridge implementation's handling of the
		// EVENT_PHASE.
		if (isAutoDispatchEvents()) {

			Bridge bridge = getFacesBridge(eventRequest, eventResponse);
			bridge.doFacesRequest(eventRequest, eventResponse);
		}

		// Otherwise, call the superclass method in GenericPortlet in order to let subclasses of GenericFacesPortlet
		// take advantage of the @ProcessEvent Portlet API annotation if desired.
		else {
			super.processEvent(eventRequest, eventResponse);
		}
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException, IOException {
		Bridge bridge = getFacesBridge(resourceRequest, resourceResponse);
		bridge.doFacesRequest(resourceRequest, resourceResponse);
	}

	@Override
	protected void doDispatch(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		String nonFacesTargetPath = renderRequest.getParameter(Bridge.NONFACES_TARGET_PATH_PARAMETER);

		if (nonFacesTargetPath != null) {
			PortletContext portletContext = getPortletContext();
			String responseContentType = renderRequest.getResponseContentType();

			// TCK TestPage017: requestProcessingNonFacesTest
			renderResponse.setContentType(responseContentType);

			PortletRequestDispatcher portletRequestDispatcher = portletContext.getRequestDispatcher(nonFacesTargetPath);

			try {
				portletRequestDispatcher.forward(renderRequest, renderResponse);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected void doEdit(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {
		Bridge bridge = getFacesBridge(renderRequest, renderResponse);
		bridge.doFacesRequest(renderRequest, renderResponse);
	}

	@Override
	protected void doHeaders(RenderRequest renderRequest, RenderResponse renderResponse) {

		try {

			// Streaming portals like WebSphere (as opposed to buffered portals like Liferay) will set the
			// javax.portlet.render_part request attribute to a value of "RENDER_HEADERS" which will cause
			// javax.portlet.GenericPortlet (the superclass of this class) to call this doHeaders(RenderRequest,
			// RenderResponse) method, but will not in turn call GenericPortlet.doDispatch(RenderRequest,
			// RenderResponse). That also means that that the doView(RenderRequest, RenderResponse) will not be called
			// in this class. So if the attribute is set, we call the Bridge.doFacesRequest(RenderRequest,
			// RenderResponse) method here, so that the Faces lifecycle can be run, and resources added to
			// h:head can be retrieved. Note that it is the responsibility of the bridge to check for this
			// attribute as well, because at this point the bridge should not render any JSF views to the response.
			Object renderPartAttribute = renderRequest.getAttribute(RenderRequest.RENDER_PART);

			if ((renderPartAttribute != null) && renderPartAttribute.equals(RenderRequest.RENDER_HEADERS)) {
				Bridge bridge = getFacesBridge(renderRequest, renderResponse);
				bridge.doFacesRequest(renderRequest, renderResponse);
			}
		}
		catch (PortletException e) {

			// Unfortunately the signature for GenericPortlet.doHeaders(RenderRequest, RenderResponse) does not throw
			// an exception, so we have no choice but to simply report any exceptions by printing the stacktrace.
			e.printStackTrace();
		}
	}

	@Override
	protected void doHelp(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {
		Bridge bridge = getFacesBridge(renderRequest, renderResponse);
		bridge.doFacesRequest(renderRequest, renderResponse);
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {
		Bridge bridge = getFacesBridge(renderRequest, renderResponse);
		bridge.doFacesRequest(renderRequest, renderResponse);
	}

	protected Bridge getBridge() throws PortletException {

		if (bridge == null) {
			String bridgeClassName = getBridgeClassName();

			if (bridgeClassName != null) {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

				try {
					Class<?> bridgeClass = classLoader.loadClass(bridgeClassName);
					bridge = (Bridge) bridgeClass.newInstance();
				}
				catch (Exception e) {
					throw new PortletException(e);
				}
			}
		}

		return bridge;
	}

	public String getBridgeClassName() {

		if (bridgeClassName == null) {

			// TCK TestPage016: initMethodTest
			bridgeClassName = getPortletConfig().getInitParameter(BRIDGE_CLASS);

			if (bridgeClassName == null) {
				bridgeClassName = getClassPathResourceAsString(BRIDGE_SERVICE_CLASSPATH);
			}
		}

		return bridgeClassName;
	}

	protected BridgeEventHandler getBridgeEventHandler() throws PortletException {

		if (bridgeEventHandler == null) {

			// TCK TestPage016: initMethodTest
			String initParamName = Bridge.BRIDGE_PACKAGE_PREFIX + Bridge.BRIDGE_EVENT_HANDLER;
			String className = getPortletConfig().getInitParameter(initParamName);

			if (className != null) {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

				try {
					Class<?> clazz = classLoader.loadClass(className);
					bridgeEventHandler = (BridgeEventHandler) clazz.newInstance();
				}
				catch (Exception e) {
					throw new PortletException(e);
				}
			}
		}

		return bridgeEventHandler;
	}

	protected BridgePublicRenderParameterHandler getBridgePublicRenderParameterHandler() throws PortletException {

		if (bridgePublicRenderParameterHandler == null) {

			// TCK TestPage016: initMethodTest
			String initParamName = Bridge.BRIDGE_PACKAGE_PREFIX + Bridge.BRIDGE_PUBLIC_RENDER_PARAMETER_HANDLER;
			String className = getPortletConfig().getInitParameter(initParamName);

			if (className != null) {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

				try {
					Class<?> clazz = classLoader.loadClass(className);
					bridgePublicRenderParameterHandler = (BridgePublicRenderParameterHandler) clazz.newInstance();
				}
				catch (Exception e) {
					throw new PortletException(e);
				}
			}
		}

		return bridgePublicRenderParameterHandler;
	}

	protected String getClassPathResourceAsString(String resourcePath) {
		String classPathResourceAsString = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		if (classLoader != null) {
			InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				try {
					classPathResourceAsString = bufferedReader.readLine();
				}
				catch (IOException e) {

					// Since the API can't use a logging system like SLF4J the best we can do is print to stderr.
					System.err.println("Unable to read contents of resourcePath=[" + resourcePath + "]");
				}
				finally {

					try {
						bufferedReader.close();
						inputStreamReader.close();
						inputStream.close();
					}
					catch (IOException e) {
						; // ignore
					}
				}
			}
		}

		return classPathResourceAsString;
	}

	public String getDefaultRenderKitId() {

		// TCK TestPage016: initMethodTest
		return getPortletConfig().getInitParameter(Bridge.BRIDGE_PACKAGE_PREFIX + Bridge.DEFAULT_RENDERKIT_ID);
	}

	public Map<String, String> getDefaultViewIdMap() {

		if (defaultViewIdMap == null) {

			// TCK TestPage015: portletInitializationParametersTest
			defaultViewIdMap = new HashMap<String, String>();

			Enumeration<String> initParameterNames = getPortletConfig().getInitParameterNames();

			if (initParameterNames != null) {
				int defaultViewIdLength = DEFAULT_VIEWID.length();
				int portletModeIndex = defaultViewIdLength + 1;

				while (initParameterNames.hasMoreElements()) {
					String initParameterName = initParameterNames.nextElement();

					if ((initParameterName != null) && initParameterName.startsWith(DEFAULT_VIEWID) &&
							(initParameterName.length() > defaultViewIdLength)) {
						String initParameterValue = getPortletConfig().getInitParameter(initParameterName);
						String portletMode = initParameterName.substring(portletModeIndex);
						defaultViewIdMap.put(portletMode, initParameterValue);
					}
				}
			}
		}

		return defaultViewIdMap;
	}

	public List<String> getExcludedRequestAttributes() {

		if (excludedRequestAttributes == null) {

			// Note: Not able to perform lazy-init operation with an empty ArrayList due to
			// TCK TestPage022: getExcludedRequestAttributesMethodNotSetTest
			String initParamName = Bridge.BRIDGE_PACKAGE_PREFIX + Bridge.EXCLUDED_REQUEST_ATTRIBUTES;
			String initParamValue = getPortletConfig().getInitParameter(initParamName);

			// TCK TestPage016: initMethodTest
			if (initParamValue != null) {

				excludedRequestAttributes = new ArrayList<String>();

				String[] values = initParamValue.split(CHAR_COMMA);

				for (String value : values) {
					excludedRequestAttributes.add(value);
				}
			}
		}

		return excludedRequestAttributes;
	}

	public Bridge getFacesBridge(PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		String viewId = portletRequest.getParameter(Bridge.FACES_VIEW_ID_PARAMETER);

		if (viewId != null) {
			portletRequest.setAttribute(Bridge.VIEW_ID, viewId);
		}
		else {
			String viewPath = portletRequest.getParameter(Bridge.FACES_VIEW_PATH_PARAMETER);

			if (viewPath != null) {
				portletRequest.setAttribute(Bridge.VIEW_PATH, viewPath);
			}
		}

		return getBridge();
	}

	@Override
	public String getPortletName() {
		return portletName;
	}

	/**
	 * @deprecated  as of JSR 329 Spec version 1.0
	 */
	@Deprecated
	public String getResponseCharacterSetEncoding(PortletRequest portletRequest) {

		// PROPOSED-FOR-STANDARD: https://issues.apache.org/jira/browse/PORTLETBRIDGE-210
		// Since this is deprecated, proposal is to remove it entirely for the Bridge 3.0.0 Spec.
		return null;
	}

	/**
	 * @deprecated  as of JSR 329 Spec version 1.0
	 */
	@Deprecated
	public String getResponseContentType(PortletRequest portletRequest) {

		// PROPOSED-FOR-STANDARD: https://issues.apache.org/jira/browse/PORTLETBRIDGE-210
		// Since this is deprecated, proposal is to remove it entirely for the Bridge 3.0.0 Spec.
		return portletRequest.getResponseContentType();
	}

	public boolean isAutoDispatchEvents() {

		if (autoDispatchEvents == null) {

			// TCK TestPage034: isAutoDispatchEventsSetTest
			String initParamValue = getPortletConfig().getInitParameter(BRIDGE_AUTO_DISPATCH_EVENTS);

			if (initParamValue != null) {
				autoDispatchEvents = Boolean.parseBoolean(initParamValue);
			}
			else {
				autoDispatchEvents = Boolean.TRUE;
			}
		}

		return autoDispatchEvents;
	}

	public boolean isPreserveActionParameters() {

		if (preserveActionParameters == null) {

			// TCK TestPage016: initMethodTest
			String initParamName = Bridge.BRIDGE_PACKAGE_PREFIX + Bridge.PRESERVE_ACTION_PARAMS;
			String initParamValue = getPortletConfig().getInitParameter(initParamName);

			if (initParamValue != null) {
				preserveActionParameters = Boolean.parseBoolean(initParamValue);
			}
			else {
				preserveActionParameters = Boolean.FALSE;
			}
		}

		return preserveActionParameters;
	}
}
