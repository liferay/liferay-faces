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
package com.liferay.faces.bridge.application.internal;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.application.ResourceValidator;
import com.liferay.faces.util.application.ResourceValidatorWrapper;
import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.ConfiguredServlet;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.WebConfig;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public abstract class ResourceValidatorBaseImpl extends ResourceValidatorWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceValidatorBaseImpl.class);

	// Private Data Members
	private ResourceValidator wrappedResourceValidator;

	public ResourceValidatorBaseImpl(ResourceValidator resourceValidator) {
		this.wrappedResourceValidator = resourceValidator;
	}

	@Override
	public boolean isSelfReferencing(FacesContext facesContext, String resourceId) {

		// If the delegation chain indicates that the specified resource is not self-referencing, then
		boolean selfReferencing = super.isSelfReferencing(facesContext, resourceId);

		if ((!selfReferencing) && (resourceId != null)) {

			// Process the configured servlet entries in order to determine which ones are portlet invokers.
			Set<String> invokerServletNames = new HashSet<String>();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			ApplicationConfig applicationConfig = (ApplicationConfig) applicationMap.get(ApplicationConfig.class
					.getName());
			WebConfig webConfig = applicationConfig.getWebConfig();
			List<ConfiguredServlet> configuredServlets = webConfig.getConfiguredServlets();

			for (ConfiguredServlet configuredServlet : configuredServlets) {

				String configuredServletClass = configuredServlet.getServletClass();

				if (isInvokerServletClass(configuredServletClass)) {
					invokerServletNames.add(configuredServlet.getServletName());
				}
			}

			// For each of the servlet-mapping entries:
			List<ConfiguredServletMapping> configuredServletMappings = webConfig.getConfiguredServletMappings();

			for (ConfiguredServletMapping configuredServletMapping : configuredServletMappings) {

				// Determine whether or not the current servlet-mapping is mapped to a portlet invoker servlet-class.
				if (invokerServletNames.contains(configuredServletMapping.getServletName())) {

					if (configuredServletMapping.isMatch(resourceId)) {
						selfReferencing = true;

						break;
					}
				}
			}
		}

		return selfReferencing;
	}

	protected abstract String getInvokerServletFQCN();

	protected boolean isInvokerServletClass(String servletClassFQCN) {

		boolean invokerServletClass = false;

		String invokerServletFQCN = getInvokerServletFQCN();

		if (invokerServletFQCN.equals(servletClassFQCN)) {

			invokerServletClass = true;
		}
		else {

			try {
				Class<?> invokerServletClazz = Class.forName(invokerServletFQCN);

				try {
					Class<?> servletClazz = Class.forName(servletClassFQCN);
					invokerServletClass = invokerServletClazz.isAssignableFrom(servletClazz);
				}
				catch (Throwable t) {
					logger.error("Unable to load servletClassFQCN=[{0}] error=[{1}]", servletClassFQCN, t.getMessage());
				}

			}
			catch (Throwable t) {
				logger.error("Unable to load invokerServletFQCN=[{0}] error=[{1}]", invokerServletFQCN, t.getMessage());
			}
		}

		return invokerServletClass;
	}

	@Override
	public ResourceValidator getWrapped() {
		return wrappedResourceValidator;
	}
}
