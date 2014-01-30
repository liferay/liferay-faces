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
package com.liferay.faces.bridge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.portlet.PortletConfig;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeException;
import javax.portlet.faces.GenericFacesPortlet;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeFactoryFinder {

	// Private Static Members
	private static BridgeFactoryFinder staticInstance;
	private static PortletConfig staticPortletConfig;

	private static String getClassPathResourceAsString(String resourcePath) {
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

	public static FactoryWrapper<?> getFactory(Class<? extends FactoryWrapper<?>> clazz) {
		return getInstance().getFactoryInstance(clazz);
	}

	public static BridgeFactoryFinder getInstance() {

		if (staticInstance == null) {

			if (staticPortletConfig == null) {
				throw new BridgeException("Must first call setPortletConfig(PortletConfig)");
			}
			else {

				synchronized (staticPortletConfig) {

					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

					try {
						String bridgeFactoryFinderService =
							"META-INF/services/com.liferay.faces.bridge.BridgeFactoryFinder";
						String bridgeFactoryFinderClassName = getClassPathResourceAsString(bridgeFactoryFinderService);

						if (bridgeFactoryFinderClassName != null) {
							Class<?> bridgeFactoryFinderClass = classLoader.loadClass(bridgeFactoryFinderClassName);
							staticInstance = (BridgeFactoryFinder) bridgeFactoryFinderClass.newInstance();
							staticInstance.init(staticPortletConfig);
						}
						else {
							throw new BridgeException("Unable to load resource=[" + bridgeFactoryFinderService + "]");
						}
					}
					catch (Exception e) {
						throw new BridgeException(e);
					}
				}
			}
		}

		return staticInstance;
	}

	/**
	 * Returns the {@link PortletConfig} for this portlet application.
	 */
	public static PortletConfig getPortletConfig() {
		return staticPortletConfig;
	}

	/**
	 * Sets the {@link PortletConfig} for this portlet application. Note that this is called by the {@link
	 * GenericFacesPortlet} before it acquires the {@link Bridge} instance from the {@link BridgePhaseFactory}.
	 */
	public static synchronized void setPortletConfig(PortletConfig portletConfig) {
		staticPortletConfig = portletConfig;
	}

	public abstract void init(PortletConfig portletConfig);

	public abstract FactoryWrapper<?> getFactoryInstance(Class<? extends FactoryWrapper<?>> clazz);

}
