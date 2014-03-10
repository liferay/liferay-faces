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
package com.liferay.faces.util.event;

import java.io.IOException;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.ApplicationConfigInitializer;
import com.liferay.faces.util.config.ApplicationConfigInitializerImpl;
import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.helper.BooleanHelper;


/**
 * @author  Neil Griffin
 */
public class ApplicationStartupListener implements SystemEventListener {

	// Private Constants
	public static final String INIT_PARAM_RESOLVE_XML_ENTITIES = "com.liferay.faces.util.resolveXMLEntities";

	// Private Static Data Members
	private static ApplicationConfig applicationConfig;

	public static void initializeApplicationConfig(boolean resolveEntities) {

		ApplicationConfigInitializer applicationConfigInitializer = new ApplicationConfigInitializerImpl(
				resolveEntities);

		try {
			applicationConfig = applicationConfigInitializer.initialize();

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
	}

	public static ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {

		if (systemEvent instanceof PostConstructApplicationEvent) {

			if (applicationConfig == null) {
				FacesContext initFacesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = initFacesContext.getExternalContext();
				String initParam = externalContext.getInitParameter(INIT_PARAM_RESOLVE_XML_ENTITIES);
				boolean resolveEntities = BooleanHelper.toBoolean(initParam, false);
				initializeApplicationConfig(resolveEntities);
			}
		}
	}

	public boolean isListenerForSource(Object source) {

		if ((source != null) && (source instanceof Application)) {
			return true;
		}
		else {
			return false;
		}
	}
}
