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
package com.liferay.faces.util.event.internal;

import java.io.IOException;
import java.util.EventObject;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;

import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.config.WebConfigParam;
import com.liferay.faces.util.config.internal.ApplicationConfigInitializer;
import com.liferay.faces.util.config.internal.ApplicationConfigInitializerImpl;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
public class ApplicationStartupListener extends ApplicationStartupListenerCompat_2_2 {

	@Override
	public void processSystemEvent(EventObject systemEvent) throws AbortProcessingException {

		Application application = (Application) systemEvent.getSource();
		FacesContext initFacesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = initFacesContext.getExternalContext();
		Map<String, Object> applicationMap = externalContext.getApplicationMap();
		String appConfigAttrName = ApplicationConfig.class.getName();
		ApplicationConfig applicationConfig = (ApplicationConfig) applicationMap.get(appConfigAttrName);

		if (applicationConfig == null) {

			boolean resolveEntities = WebConfigParam.ResolveXMLEntities.getBooleanValue(externalContext);

			String contextPath = getApplicationContextPath(externalContext);
			ApplicationConfigInitializer applicationConfigInitializer = new ApplicationConfigInitializerImpl(
					contextPath, resolveEntities);

			try {
				applicationConfig = applicationConfigInitializer.initialize();
				applicationMap.put(appConfigAttrName, applicationConfig);

				// Register the configured factories with the factory extension finder.
				FacesConfig facesConfig = applicationConfig.getFacesConfig();
				List<ConfiguredElement> configuredFactoryExtensions = facesConfig.getConfiguredFactoryExtensions();

				if (configuredFactoryExtensions != null) {

					FactoryExtensionFinder factoryExtensionFinder = FactoryExtensionFinder.getInstance();

					for (ConfiguredElement configuredFactoryExtension : configuredFactoryExtensions) {
						factoryExtensionFinder.registerFactory(configuredFactoryExtension);
					}
				}
			}
			catch (IOException e) {
				throw new AbortProcessingException(e);
			}

			publishEvent(application, initFacesContext, applicationConfig);
		}
	}
}
