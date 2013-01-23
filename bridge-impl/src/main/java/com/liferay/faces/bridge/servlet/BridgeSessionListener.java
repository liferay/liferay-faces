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
package com.liferay.faces.bridge.servlet;

import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.bean.BeanManager;
import com.liferay.faces.bridge.bean.BeanManagerFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManager;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManagerFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * This class provides the ability to cleanup {@link SessionScoped} and {@link ViewScoped} managed-beans upon session
 * expiration.
 *
 * @author  Neil Griffin
 */
public class BridgeSessionListener implements HttpSessionListener, ServletContextListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeSessionListener.class);

	// Private Constants
	private static final String MOJARRA_INJECTION_PROVIDER_TASK =
		"com.sun.faces.config.ConfigManager_INJECTION_PROVIDER_TASK";
	private static final String MOJARRA_VIEW_SCOPE_MANAGER = "com.sun.faces.application.view.viewScopeManager";
	private static final String SESSION_DESTROYED = "sessionDestroyed";

	// Private Static Data Members
	private static Object mojarraInjectionProvider;

	// Private Data Members
	private ServletContext servletContext;

	/**
	 * Returns the Mojarra InjectionProvider instance that was discovered during startup.
	 */
	public static Object getMojarraInjectionProvider() {
		return mojarraInjectionProvider;
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		this.servletContext = null;
	}

	/**
	 * This method provides the ability to discover the Mojarra InjectionProvider at startup.
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		this.servletContext = servletContextEvent.getServletContext();

		Product jsf = ProductMap.getInstance().get(ProductConstants.JSF);

		if (jsf.isDetected() && ProductConstants.MOJARRA.equals(jsf.getTitle())) {

			// The Mojarra InjectionProvider instance is stored as a FacesContext attribute during startup, via the
			// InitFacesContext implementation.
			FacesContext facesContext = FacesContext.getCurrentInstance();

			if (facesContext != null) {
				mojarraInjectionProvider = facesContext.getAttributes().get(MOJARRA_INJECTION_PROVIDER_TASK);
			}

			if (mojarraInjectionProvider == null) {
				logger.error("Unable to determine Mojarra InjectionProvider");
			}
		}
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		// This method is required by the HttpSessionListener interface but is not used.
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

		// Cleanup instances of BridgeRequestScope that are associated with the expiring session.
		BridgeRequestScopeManagerFactory bridgeRequestScopeManagerFactory = (BridgeRequestScopeManagerFactory)
			BridgeFactoryFinder.getFactory(BridgeRequestScopeManagerFactory.class);
		BridgeRequestScopeManager bridgeRequestScopeManager =
			bridgeRequestScopeManagerFactory.getBridgeRequestScopeManager();
		HttpSession httpSession = httpSessionEvent.getSession();
		bridgeRequestScopeManager.removeBridgeRequestScopesBySession(httpSession);

		// For each session attribute:
		BeanManagerFactory beanManagerFactory = (BeanManagerFactory) BridgeFactoryFinder.getFactory(
				BeanManagerFactory.class);
		BeanManager beanManager = beanManagerFactory.getBeanManager();
		@SuppressWarnings("unchecked")
		Enumeration<String> attributeNames = (Enumeration<String>) httpSession.getAttributeNames();

		while (attributeNames.hasMoreElements()) {

			String attributeName = attributeNames.nextElement();

			// If the current session attribute name is prefixed with the standard portlet prefix, then it is an
			// attribute that was set using PortletSession.setAttribute(String, Object).
			if ((attributeName != null) && attributeName.startsWith("javax.portlet.p.")) {
				int pos = attributeName.indexOf("?");

				if (pos > 0) {
					Object attributeValue = httpSession.getAttribute(attributeName);
					httpSession.removeAttribute(attributeName);

					if (attributeValue != null) {

						// If the current session attribute value is a JSF managed-bean, then cleanup the bean by
						// invoking methods annotated with {@link PreDestroy}.
						if (beanManager.isManagedBean(attributeValue)) {
							beanManager.invokePreDestroyMethods(attributeValue, true);
						}

						// Otherwise, if the current session attribute is Mojarra-vendor-specific, then rename the
						// attribute by stripping off the standard portlet prefix. This will enable Mojarra's session
						// expiration features to find attributes that it is expecting.
						else {
							String fqcn = attributeValue.getClass().getName();

							if ((fqcn != null) && (fqcn.contains("com.sun.faces"))) {
								String nonPrefixedName = attributeName.substring(pos + 1);
								logger.debug("Renaming Mojarra session attributeName=[{0}] -> [{1}]", attributeName,
									nonPrefixedName);
								httpSession.setAttribute(nonPrefixedName, attributeValue);
								httpSession.removeAttribute(attributeName);
							}
						}
					}
				}
			}
		}

		// If Mojarra is detected, then invoke the ViewScopeManager via reflection in order to cleanup ViewScoped
		// managed-beans.
		Product jsf = ProductMap.getInstance().get(ProductConstants.JSF);

		if (jsf.getTitle().equals(ProductConstants.MOJARRA)) {
			Object viewScopeManager = servletContext.getAttribute(MOJARRA_VIEW_SCOPE_MANAGER);

			try {

				if (viewScopeManager != null) {

					Method method = viewScopeManager.getClass().getMethod(SESSION_DESTROYED,
							new Class[] { HttpSessionEvent.class });
					method.invoke(viewScopeManager, new Object[] { httpSessionEvent });

					logger.debug(
						"Invoked Mojarra ViewScopeManager.sessionDestroyed(HttpSessionEvent) method in order to cleanup @ViewScoped managed-beans");
				}
			}
			catch (Exception e) {

				if ((jsf.getMajorVersion() == 2) && (jsf.getMinorVersion() == 1)) {

					if (jsf.getRevisionVersion() < 18) {
						logger.warn(
							"Unable to cleanup ViewScoped managed-beans upon session expiration. Please upgrade to Mojarra 2.1.18 or greater.");
					}
					else {
						logger.error(e);
					}
				}
			}
		}
	}

}
