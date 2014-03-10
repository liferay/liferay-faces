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
import java.util.List;

import javax.faces.event.AbortProcessingException;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
public class ApplicationConfigUtil {

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
}
