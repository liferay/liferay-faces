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

import javax.portlet.faces.BridgeException;

import com.liferay.faces.util.factory.FactoryExtension;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @deprecated  Use {@link FactoryExtensionFinder} instead.
 * @author      Neil Griffin
 */
public abstract class BridgeFactoryFinder {

	// Private Static Members
	private static BridgeFactoryFinder instance;

	/**
	 * @deprecated  Call {@link FactoryExtensionFinder#getFactory(Class)} instead.
	 */
	@Deprecated
	public static FactoryExtension<?> getFactory(Class<? extends FactoryWrapper<?>> clazz) {
		return getInstance().getFactoryInstance(clazz);
	}

	/**
	 * @deprecated  Call {@link FactoryExtensionFinder#getInstance()}
	 */
	@Deprecated
	public static BridgeFactoryFinder getInstance() {

		if (instance == null) {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

			try {
				String bridgeFactoryFinderService = "META-INF/services/com.liferay.faces.bridge.BridgeFactoryFinder";
				String bridgeFactoryFinderClassName = FactoryExtensionFinder.getClassPathResourceAsString(
						bridgeFactoryFinderService);

				if (bridgeFactoryFinderClassName != null) {
					Class<?> bridgeFactoryFinderClass = classLoader.loadClass(bridgeFactoryFinderClassName);
					instance = (BridgeFactoryFinder) bridgeFactoryFinderClass.newInstance();
				}
				else {
					throw new BridgeException("Unable to load resource=[" + bridgeFactoryFinderService + "]");
				}
			}
			catch (Exception e) {
				throw new BridgeException(e);
			}
		}

		return instance;
	}

	/**
	 * @deprecated  Call {@link FactoryExtensionFinder#getFactoryInstance(Class)} instead.
	 */
	@Deprecated
	public abstract FactoryWrapper<?> getFactoryInstance(Class<? extends FactoryWrapper<?>> clazz);
}
