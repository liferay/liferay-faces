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
package com.liferay.faces.bridge.servlet;

import java.util.Enumeration;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.application.MojarraApplicationAssociate;
import com.liferay.faces.bridge.bean.BeanManager;
import com.liferay.faces.bridge.bean.BeanManagerFactory;
import com.liferay.faces.bridge.bean.MojarraInjectionProvider;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManager;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManagerFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * This class provides the ability to cleanup session-scoped and view-scoped managed-beans upon session expiration.
 *
 * @author  Neil Griffin
 */
public class BridgeSessionListener implements HttpSessionListener, ServletContextListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeSessionListener.class);

	// Private Constants
	private static final String MOJARRA_ACTIVE_VIEW_MAPS = "com.sun.faces.application.view.activeViewMaps";
	private static final String MOJARRA_PACKAGE_PREFIX = "com.sun.faces";
	private static final String MOJARRA_VIEW_SCOPE_MANAGER = "com.sun.faces.application.view.viewScopeManager";

	// Private Static Data Members
	private static MojarraInjectionProvider mojarraInjectionProvider;

	// Private Data Members
	private ServletContext servletContext;

	/**
	 * Returns the Mojarra InjectionProvider instance that was discovered during startup.
	 */
	public static MojarraInjectionProvider getMojarraInjectionProvider() {
		return mojarraInjectionProvider;
	}

	public static synchronized void setMojarraInjectionProvider(MojarraInjectionProvider injectionProvider) {
		mojarraInjectionProvider = injectionProvider;
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		this.servletContext = null;
	}

	/**
	 * This method provides the ability to discover the Mojarra InjectionProvider at startup.
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		this.servletContext = servletContextEvent.getServletContext();

		if (servletContext.getAttribute(BridgeSessionListener.class.getName()) == null) {

			logger.info("Context initialized for contextPath=[{0}]", servletContext.getContextPath());

			// Prevent multiple-instantiation of this listener.
			servletContext.setAttribute(BridgeSessionListener.class.getName(), Boolean.TRUE);

			// If the Mojarra InjectionProvider hasn't been discovered by a prior portlet instance in this context, then
			// attempt to discover it.
			if (mojarraInjectionProvider == null) {

				Product jsf = ProductMap.getInstance().get(ProductConstants.JSF);

				if (jsf.isDetected() && ProductConstants.MOJARRA.equals(jsf.getTitle())) {

					FacesContext facesContext = FacesContext.getCurrentInstance();

					if (facesContext != null) {
						ExternalContext externalContext = facesContext.getExternalContext();
						mojarraInjectionProvider = MojarraApplicationAssociate.getInjectionProvider(externalContext);

						if (mojarraInjectionProvider == null) {
							logger.debug("Unable to discover Mojarra InjectionProvider during startup");
						}
						else {
							logger.debug("Mojarra injectionProvider=[{0}]", mojarraInjectionProvider);
						}
					}
				}
			}
		}
		else {
			logger.debug("Preventing multiple instantiation for contextPath=[{0}]", servletContext.getContextPath());
		}
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		// This method is required by the HttpSessionListener interface but is not used.
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

		// Determine which JSF implementation is being used (Mojarra/MyFaces).
		ProductMap productMap = ProductMap.getInstance();
		Product jsf = productMap.get(ProductConstants.JSF);
		boolean mojarraAbleToCleanup = true;

		if (jsf.getTitle().equals(ProductConstants.MOJARRA) && (jsf.getMajorVersion() == 2) &&
				(jsf.getMinorVersion() == 1)) {

			if (jsf.getRevisionVersion() < 18) {
				mojarraAbleToCleanup = false;

				boolean logWarning = true;
				Product iceFaces = productMap.get(ProductConstants.ICEFACES);

				if (iceFaces.isDetected()) {

					if ((iceFaces.getMajorVersion() == 2) ||
							((iceFaces.getMajorVersion() == 3) && (iceFaces.getMinorVersion() <= 2))) {

						// Versions of ICEfaces prior to 3.3 can only go as high as Mojarra 2.1.6 so don't bother to
						// log the warning.
						logWarning = false;
					}
				}

				if (logWarning) {
					logger.warn("Unable to cleanup ViewScoped managed-beans upon session expiration. " +
						"Please upgrade to Mojarra 2.1.18 or newer. " +
						"For more info, see: http://issues.liferay.com/browse/FACES-1470");
				}
			}
		}

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

			// If the current session attribute name is namespaced with the standard portlet prefix, then it is an
			// attribute that was set using PortletSession.setAttribute(String, Object).
			if ((attributeName != null) && attributeName.startsWith("javax.portlet.p.")) {
				int pos = attributeName.indexOf("?");

				if (pos > 0) {
					Object attributeValue = httpSession.getAttribute(attributeName);
					httpSession.removeAttribute(attributeName);

					if (attributeValue != null) {

						// If the current session attribute value is a JSF managed-bean, then cleanup the bean by
						// invoking methods annotated with {@link PreDestroy}. Note that in a webapp/servlet
						// environment, the cleanup is handled by the Mojarra
						// WebappLifecycleListener.sessionDestroyed(HttpSessionEvent) method. But in a portlet
						// environment, Mojarra fails to recognize the session attribute as managed-bean because the
						// attribute name contains the standard portlet prefix. An alternative approach would be to have
						// the bridge rename the attribute (by stripping off the standard portlet prefix) so that
						// Mojarra could find it. But this would not a good solution, because multiple instances of the
						// same portlet would have the same session attribute names for managed-beans, and only the last
						// one would get cleaned-up by Mojarra.
						if (beanManager.isManagedBean(attributeName, attributeValue)) {
							beanManager.invokePreDestroyMethods(attributeValue, true);
						}

						// Otherwise,
						else {

							// If the current session attribute is Mojarra-vendor-specific, then
							String fqcn = attributeValue.getClass().getName();

							if ((fqcn != null) && (fqcn.contains(MOJARRA_PACKAGE_PREFIX))) {

								// Rename the namespaced attribute by stripping off the standard portlet prefix. This
								// will enable Mojarra's session expiration features to find attributes that it is
								// expecting.
								String nonPrefixedName = attributeName.substring(pos + 1);
								logger.debug("Renaming Mojarra session attributeName=[{0}] -> [{1}]", attributeName,
									nonPrefixedName);
								httpSession.setAttribute(nonPrefixedName, attributeValue);

								// If this is the attribute that contains all of the active view maps, then
								if (MOJARRA_ACTIVE_VIEW_MAPS.equals(nonPrefixedName)) {

									if (mojarraAbleToCleanup) {

										// Invoke the Mojarra ViewScopeManager.sessionDestroyed(HttpSessionEvent) method
										// in order to cleanup the active view maps. Rather than waiting for the servlet
										// container to call the method during session expiration, it is important to
										// call it directly within this while-loop for two reasons: 1) If the developer
										// did not explicitly specify the order of the Mojarra ConfigureListener and
										// this BridgeSessionListener in WEB-IN/web.xml descriptor (FACES-1483) then
										// there is no guarantee that the method would get called. 2) In the case of
										// multiple portlet instances, each instance has its own namespaced attribute in
										// the session. Renaming each namespaced attribute to
										// "com.sun.faces.application.view.activeViewMaps" would only enable Mojarra to
										// cleanup the last one.
										HttpSessionListener viewScopeManager = (HttpSessionListener)
											servletContext.getAttribute(MOJARRA_VIEW_SCOPE_MANAGER);

										if (viewScopeManager != null) {

											try {
												logger.debug(
													"Asking Mojarra ViewScopeManager to cleanup @ViewScoped managed-beans");
												viewScopeManager.sessionDestroyed(httpSessionEvent);
											}
											catch (Exception e) {
												logger.error(e);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
