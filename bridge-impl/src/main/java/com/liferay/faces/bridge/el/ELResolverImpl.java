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
package com.liferay.faces.bridge.el;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeUtil;
import javax.servlet.jsp.JspContext;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.bean.BeanManager;
import com.liferay.faces.bridge.bean.BeanManagerFactory;
import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.map.SessionMap;
import com.liferay.faces.bridge.filter.HttpServletRequestAdapter;
import com.liferay.faces.bridge.filter.HttpServletResponseAdapter;
import com.liferay.faces.bridge.preference.MutablePreferenceMap;
import com.liferay.faces.util.helper.BooleanHelper;


/**
 * @author  Neil Griffin
 */
public class ELResolverImpl extends ELResolverCompatImpl {

	private static final String ACTION_REQUEST = "actionRequest";
	private static final String ACTION_RESPONSE = "actionResponse";
	private static final String BRIDGE_CONTEXT = "bridgeContext";
	private static final String EVENT_REQUEST = "eventRequest";
	private static final String EVENT_RESPONSE = "eventResponse";
	private static final String FLASH = "bridgeFlash"; // http://java.net/jira/browse/JAVASERVERFACES-1964
	private static final String HTTP_SESSION_SCOPE = "httpSessionScope";
	private static final String MUTABLE_PORTLET_PREFERENCES_VALUES = "mutablePortletPreferencesValues";
	private static final String PORTLET_CONFIG = "portletConfig";
	private static final String PORTLET_SESSION = "portletSession";
	private static final String PORTLET_SESSION_SCOPE = "portletSessionScope";
	private static final String PORTLET_PREFERENCES = "portletPreferences";
	private static final String PORTLET_PREFERENCES_VALUES = "portletPreferencesValues";
	private static final String RENDER_REQUEST = "renderRequest";
	private static final String RENDER_RESPONSE = "renderResponse";
	private static final String RESOURCE_REQUEST = "resourceRequest";
	private static final String RESOURCE_RESPONSE = "resourceResponse";
	private static final ArrayList<FeatureDescriptor> FEATURE_DESCRIPTORS = new ArrayList<FeatureDescriptor>();
	private static final HashSet<String> FACES_CONTEXT_VAR_NAMES = new HashSet<String>();
	private static final HashSet<String> JSP_CONTEXT_VAR_NAMES = new HashSet<String>();

	static {

		// Initialize hash set of supported EL variable names when running in a Faces context.
		FACES_CONTEXT_VAR_NAMES.add(ACTION_REQUEST);
		FACES_CONTEXT_VAR_NAMES.add(ACTION_RESPONSE);
		FACES_CONTEXT_VAR_NAMES.add(BRIDGE_CONTEXT);
		FACES_CONTEXT_VAR_NAMES.add(EVENT_REQUEST);
		FACES_CONTEXT_VAR_NAMES.add(EVENT_RESPONSE);
		FACES_CONTEXT_VAR_NAMES.add(FLASH);
		FACES_CONTEXT_VAR_NAMES.add(HTTP_SESSION_SCOPE);
		FACES_CONTEXT_VAR_NAMES.add(MUTABLE_PORTLET_PREFERENCES_VALUES);
		FACES_CONTEXT_VAR_NAMES.add(PORTLET_CONFIG);
		FACES_CONTEXT_VAR_NAMES.add(PORTLET_SESSION);
		FACES_CONTEXT_VAR_NAMES.add(PORTLET_SESSION_SCOPE);
		FACES_CONTEXT_VAR_NAMES.add(PORTLET_PREFERENCES);
		FACES_CONTEXT_VAR_NAMES.add(PORTLET_PREFERENCES_VALUES);
		FACES_CONTEXT_VAR_NAMES.add(RENDER_REQUEST);
		FACES_CONTEXT_VAR_NAMES.add(RENDER_RESPONSE);
		FACES_CONTEXT_VAR_NAMES.add(RESOURCE_REQUEST);
		FACES_CONTEXT_VAR_NAMES.add(RESOURCE_RESPONSE);

		// Initialize hash set of supported EL variable names when running in a JSP context.
		JSP_CONTEXT_VAR_NAMES.add(HTTP_SESSION_SCOPE);
		JSP_CONTEXT_VAR_NAMES.add(MUTABLE_PORTLET_PREFERENCES_VALUES);

		// Initialize the list of static feature descriptors.
		addFeatureDescriptor(ACTION_REQUEST, String.class);
		addFeatureDescriptor(ACTION_RESPONSE, String.class);
		addFeatureDescriptor(BRIDGE_CONTEXT, String.class);
		addFeatureDescriptor(EVENT_REQUEST, String.class);
		addFeatureDescriptor(EVENT_RESPONSE, String.class);
		addFeatureDescriptor(FLASH, String.class);
		addFeatureDescriptor(HTTP_SESSION_SCOPE, String.class);
		addFeatureDescriptor(MUTABLE_PORTLET_PREFERENCES_VALUES, String.class);
		addFeatureDescriptor(PORTLET_CONFIG, String.class);
		addFeatureDescriptor(PORTLET_SESSION, String.class);
		addFeatureDescriptor(PORTLET_SESSION_SCOPE, String.class);
		addFeatureDescriptor(PORTLET_PREFERENCES, String.class);
		addFeatureDescriptor(PORTLET_PREFERENCES_VALUES, String.class);
		addFeatureDescriptor(RENDER_REQUEST, String.class);
		addFeatureDescriptor(RENDER_RESPONSE, String.class);
		addFeatureDescriptor(RESOURCE_REQUEST, String.class);
		addFeatureDescriptor(RESOURCE_RESPONSE, String.class);
	}

	protected static void addFeatureDescriptor(String featureName, Class<?> classType) {
		FeatureDescriptor featureDescriptor = new FeatureDescriptor();
		featureDescriptor.setName(featureName);
		featureDescriptor.setDisplayName(featureName);
		featureDescriptor.setShortDescription(featureName);
		featureDescriptor.setExpert(false);
		featureDescriptor.setHidden(false);
		featureDescriptor.setPreferred(true);
		featureDescriptor.setValue(ELResolver.TYPE, classType);
		featureDescriptor.setValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME, true);
		FEATURE_DESCRIPTORS.add(featureDescriptor);
	}

	protected Object resolveFacesContext(ELContext elContext, Object base, Object property) {

		Object value = null;

		if (base == null) {

			if (property instanceof String) {
				String varName = (String) property;

				if (FACES_CONTEXT_VAR_NAMES.contains(varName)) {
					value = resolveVariable(elContext, varName);
				}
			}
		}
		else {

			if (property instanceof String) {
				String propertyName = (String) property;
				value = resolveProperty(elContext, base, propertyName);
			}
		}

		if (value != null) {
			elContext.setPropertyResolved(true);
		}

		return value;
	}

	protected Object resolveJspContext(ELContext elContext, Object base, Object property) {

		Object value = null;

		if (base == null) {

			if (property instanceof String) {
				String varName = (String) property;

				if (JSP_CONTEXT_VAR_NAMES.contains(varName)) {
					value = resolveVariable(elContext, varName);
				}
			}
		}
		else {

			if (property instanceof String) {
				String propertyName = (String) property;
				value = resolveProperty(elContext, base, propertyName);
			}
		}

		if (value != null) {
			elContext.setPropertyResolved(true);
		}

		return value;
	}

	protected Object resolveProperty(ELContext elContext, Object base, String property) {
		return null;
	}

	protected Object resolveVariable(ELContext elContext, String varName) {
		Object value = null;

		if (varName != null) {

			if (varName.equals(ACTION_REQUEST)) {
				Bridge.PortletPhase portletPhase = BridgeUtil.getPortletRequestPhase();

				if (portletPhase == Bridge.PortletPhase.ACTION_PHASE) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					value = getPortletRequest(facesContext);
				}
				else {
					throw new ELException("Unable to get actionRequest during " + portletPhase);
				}
			}
			else if (varName.equals(ACTION_RESPONSE)) {
				Bridge.PortletPhase portletPhase = BridgeUtil.getPortletRequestPhase();

				if (portletPhase == Bridge.PortletPhase.ACTION_PHASE) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					value = getPortletResponse(facesContext);
				}
				else {
					throw new ELException("Unable to get actionResponse during " + portletPhase);
				}
			}
			else if (varName.equals(BRIDGE_CONTEXT)) {
				value = BridgeContext.getCurrentInstance();
			}
			else if (varName.equals(EVENT_REQUEST)) {
				Bridge.PortletPhase portletPhase = BridgeUtil.getPortletRequestPhase();

				if (portletPhase == Bridge.PortletPhase.EVENT_PHASE) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					value = getPortletRequest(facesContext);
				}
				else {
					throw new ELException("Unable to get eventRequest during " + portletPhase);
				}
			}
			else if (varName.equals(EVENT_RESPONSE)) {
				Bridge.PortletPhase portletPhase = BridgeUtil.getPortletRequestPhase();

				if (portletPhase == Bridge.PortletPhase.EVENT_PHASE) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					value = getPortletResponse(facesContext);
				}
				else {
					throw new ELException("Unable to get eventResponse during " + portletPhase);
				}
			}
			else if (varName.equals(FLASH)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				value = getFlash(facesContext);
			}
			else if (varName.equals(HTTP_SESSION_SCOPE)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				PortletSession portletSession = (PortletSession) externalContext.getSession(true);

				// Determines whether or not methods annotated with the &#064;PreDestroy annotation are preferably
				// invoked over the &#064;BridgePreDestroy annotation.
				String initParam = externalContext.getInitParameter(BridgeConfigConstants.PARAM_PREFER_PRE_DESTROY1);

				if (initParam == null) {

					// Backward compatibility
					initParam = externalContext.getInitParameter(BridgeConfigConstants.PARAM_PREFER_PRE_DESTROY2);
				}

				BeanManagerFactory beanManagerFactory = (BeanManagerFactory) BridgeFactoryFinder.getFactory(
						BeanManagerFactory.class);
				BeanManager beanManager = beanManagerFactory.getBeanManager();

				boolean preferPreDestroy = BooleanHelper.toBoolean(initParam, true);
				value = new SessionMap(portletSession, beanManager, PortletSession.APPLICATION_SCOPE, preferPreDestroy);
			}
			else if (varName.equals(MUTABLE_PORTLET_PREFERENCES_VALUES)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				PortletRequest portletRequest = getPortletRequest(facesContext);

				if (portletRequest != null) {
					value = new MutablePreferenceMap(portletRequest.getPreferences());
				}
			}
			else if (varName.equals(PORTLET_CONFIG)) {
				BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

				if (bridgeContext != null) {
					value = bridgeContext.getPortletConfig();
				}
			}
			else if (varName.equals(PORTLET_SESSION)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				value = facesContext.getExternalContext().getSession(true);
			}
			else if (varName.equals(PORTLET_SESSION_SCOPE)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				value = facesContext.getExternalContext().getSessionMap();
			}
			else if (varName.equals(PORTLET_PREFERENCES)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				PortletRequest portletRequest = getPortletRequest(facesContext);

				if (portletRequest != null) {
					value = portletRequest.getPreferences();
				}
			}
			else if (varName.equals(PORTLET_PREFERENCES_VALUES)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				PortletRequest portletRequest = getPortletRequest(facesContext);

				if (portletRequest != null) {
					value = portletRequest.getPreferences().getMap();
				}
			}
			else if (varName.equals(RENDER_REQUEST)) {
				Bridge.PortletPhase portletPhase = BridgeUtil.getPortletRequestPhase();

				if (portletPhase == Bridge.PortletPhase.RENDER_PHASE) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					value = getPortletRequest(facesContext);
				}
				else {
					throw new ELException("Unable to get renderRequest during " + portletPhase);
				}
			}
			else if (varName.equals(RENDER_RESPONSE)) {
				Bridge.PortletPhase portletPhase = BridgeUtil.getPortletRequestPhase();

				if (portletPhase == Bridge.PortletPhase.RENDER_PHASE) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					value = getPortletResponse(facesContext);
				}
				else {
					throw new ELException("Unable to get renderResponse during " + portletPhase);
				}
			}
			else if (varName.equals(RESOURCE_REQUEST)) {
				Bridge.PortletPhase portletPhase = BridgeUtil.getPortletRequestPhase();

				if (portletPhase == Bridge.PortletPhase.RESOURCE_PHASE) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					value = getPortletRequest(facesContext);
				}
				else {
					throw new ELException("Unable to get resourceRequest during " + portletPhase);
				}
			}
			else if (varName.equals(RESOURCE_RESPONSE)) {
				Bridge.PortletPhase portletPhase = BridgeUtil.getPortletRequestPhase();

				if (portletPhase == Bridge.PortletPhase.RESOURCE_PHASE) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					value = getPortletResponse(facesContext);
				}
				else {
					throw new ELException("Unable to get renderResponse during " + portletPhase);
				}
			}
		}

		return value;
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext elContext, Object base) {
		Class<?> commonPropertyType = null;

		return commonPropertyType;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext elContext, Object base) {
		return FEATURE_DESCRIPTORS.iterator();
	}

	protected PortletRequest getPortletRequest(FacesContext facesContext) {

		PortletRequest portletRequest = null;
		Object request = facesContext.getExternalContext().getRequest();

		if (request instanceof PortletRequest) {
			portletRequest = (PortletRequest) request;
		}
		else if (request instanceof HttpServletRequestAdapter) {
			HttpServletRequestAdapter httpServletRequestAdapter = (HttpServletRequestAdapter) request;
			portletRequest = httpServletRequestAdapter.getWrapped();
		}

		return portletRequest;
	}

	protected PortletResponse getPortletResponse(FacesContext facesContext) {

		PortletResponse portletResponse = null;
		Object response = facesContext.getExternalContext().getResponse();

		if (response instanceof PortletResponse) {
			portletResponse = (PortletResponse) response;
		}
		else if (response instanceof HttpServletResponseAdapter) {
			HttpServletResponseAdapter httpServletResponseAdapter = (HttpServletResponseAdapter) response;
			portletResponse = httpServletResponseAdapter.getWrapped();
		}

		return portletResponse;
	}

	@Override
	public Class<?> getType(ELContext elContext, Object base, Object property) {

		if (elContext == null) {

			// Throw an exception as directed by the JavaDoc for ELContext.
			throw new NullPointerException("elContext may not be null");
		}

		return String.class;
	}

	@Override
	public Object getValue(ELContext elContext, Object base, Object property) {

		if (elContext == null) {

			// Throw an exception as directed by the JavaDoc for ELContext.
			throw new NullPointerException();
		}
		else {

			Object value = null;

			// If running inside a JSP context, meaning evaluation of a JSP-syntax (dollar-sign prefixed) EL expression
			// like ${portletConfig} then
			if (elContext.getContext(JspContext.class) != null) {

				// Resolve according to the JSP expression requirements of Section 6.5.2.2 of the JSR 329 Spec.
				value = resolveJspContext(elContext, base, property);
			}

			// Otherwise, must be running inside a Faces context, meaning evaluation of a JSF-syntax (hash/pound
			// prefixed) EL expression like #{portletConfig}
			else {

				// Resolve according to the JSF expression requirements of Section 6.5.2.2 of the JSR 329 Spec.
				value = resolveFacesContext(elContext, base, property);
			}

			return value;
		}
	}

	@Override
	public void setValue(ELContext elContext, Object base, Object property, Object value) {

		if (elContext == null) {

			// Throw an exception as directed by the JavaDoc for ELContext.
			throw new NullPointerException("elContext may not be null");
		}
	}

	@Override
	public boolean isReadOnly(ELContext elContext, Object base, Object property) {
		return true;
	}
}
