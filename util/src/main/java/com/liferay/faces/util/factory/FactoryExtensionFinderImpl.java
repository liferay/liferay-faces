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
package com.liferay.faces.util.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class FactoryExtensionFinderImpl extends FactoryExtensionFinder {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FactoryExtensionFinderImpl.class);

	// Private Data Members
	private Map<Class<? extends FactoryExtension<?>>, FactoryExtension<?>> factoryExtensionCache =
		new HashMap<Class<? extends FactoryExtension<?>>, FactoryExtension<?>>();

	@Override
	@SuppressWarnings("unchecked")
	public void registerFactory(ConfiguredElement configuredFactoryExtension) {

		if (configuredFactoryExtension != null) {

			String factoryClassFQCN = configuredFactoryExtension.getValue();

			try {
				Class<?> factoryClass = Class.forName(factoryClassFQCN);

				if (FactoryExtension.class.isAssignableFrom(factoryClass)) {

					Class<FactoryExtension<?>> factoryExtensionClass = (Class<FactoryExtension<?>>) factoryClass;
					Class<FactoryExtension<?>> baseFactoryExtensionClass = getBaseFactoryExtensionClass(
							factoryExtensionClass);
					FactoryExtension<?> existingFactoryInstance = getFactoryInstance(baseFactoryExtensionClass);
					FactoryExtension<?> factoryInstance = (FactoryExtension<?>) newFactoryInstance(
							factoryExtensionClass, baseFactoryExtensionClass, existingFactoryInstance);

					factoryExtensionCache.put(baseFactoryExtensionClass, factoryInstance);
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	protected Object newFactoryInstance(Class<FactoryExtension<?>> factoryExtensionClass,
		Class<FactoryExtension<?>> baseFactoryExtensionClass, Object wrappedFactory) throws ClassNotFoundException,
		InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Object classInstance = null;

		if (classLoader != null) {

			if (factoryExtensionClass != null) {
				Constructor<?> wrapperConstructor = null;
				Constructor<?>[] constructors = factoryExtensionClass.getDeclaredConstructors();

				for (Constructor<?> constructor : constructors) {
					Class<?>[] parameterTypes = constructor.getParameterTypes();

					if ((parameterTypes != null) && (parameterTypes.length == 1) &&
							(parameterTypes[0].getName().equals(baseFactoryExtensionClass.getName()))) {
						wrapperConstructor = constructor;
					}
				}

				if (wrapperConstructor == null) {
					logger.debug("Creating instance with zero-arg constructor since wrapperConstructor=null");
					classInstance = factoryExtensionClass.newInstance();
				}
				else {
					logger.debug("Creating instance with one-arg constructor since wrapperConstructor=[{0}]",
						wrapperConstructor);
					classInstance = wrapperConstructor.newInstance(new Object[] { wrappedFactory });
				}
			}
		}

		return classInstance;
	}

	@SuppressWarnings("unchecked")
	protected Class<FactoryExtension<?>> getBaseFactoryExtensionClass(Class<FactoryExtension<?>> factoryClass) {

		Class<FactoryExtension<?>> baseFactoryExtensionClass = factoryClass;

		Class<?> factorySuperclass = factoryClass.getSuperclass();

		if (FactoryExtension.class.isAssignableFrom(factorySuperclass)) {
			Class<FactoryExtension<?>> superClassAsFactoryExtension = (Class<FactoryExtension<?>>) factorySuperclass;
			baseFactoryExtensionClass = getBaseFactoryExtensionClass(superClassAsFactoryExtension);
		}

		return baseFactoryExtensionClass;
	}

	@Override
	public FactoryExtension<?> getFactoryInstance(Class<? extends FactoryExtension<?>> clazz) {
		FactoryExtension<?> factory = null;

		if (clazz != null) {
			factory = factoryExtensionCache.get(clazz);
		}

		return factory;
	}
}
