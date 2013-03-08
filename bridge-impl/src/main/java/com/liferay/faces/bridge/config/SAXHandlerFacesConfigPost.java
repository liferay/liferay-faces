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
package com.liferay.faces.bridge.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public abstract class SAXHandlerFacesConfigPost extends SAXHandlerBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SAXHandlerFacesConfigPost.class);

	// Protected Constants
	protected static final String FACTORY_NOT_FOUND_MSG = "Factory not found in any faces-config.xml files: [{0}]";

	// Private Data Members
	private BridgeConfigAttributeMap bridgeConfigAttributeMap;

	public SAXHandlerFacesConfigPost(boolean resolveEntities, BridgeConfigAttributeMap bridgeConfigAttributeMap) {
		super(resolveEntities);
		this.bridgeConfigAttributeMap = bridgeConfigAttributeMap;
	}

	protected Object newFactoryInstance(String className, Class<?> factoryClassType, Object wrappedFactory)
		throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Object classInstance = null;

		if (classLoader != null) {

			if (className != null) {
				Class<?> factoryClass = classLoader.loadClass(className);
				Constructor<?> wrapperConstructor = null;
				Constructor<?>[] constructors = factoryClass.getDeclaredConstructors();

				for (Constructor<?> constructor : constructors) {
					Class<?>[] parameterTypes = constructor.getParameterTypes();

					if ((parameterTypes != null) && (parameterTypes.length == 1) &&
							(parameterTypes[0] == factoryClassType)) {
						wrapperConstructor = constructor;
					}
				}

				if (wrapperConstructor == null) {
					logger.debug("Creating instance with zero-arg constructor since wrapperConstructor=null");
					classInstance = factoryClass.newInstance();
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

	protected void setupFactory(Class<?> factoryType, String factoryImplementation) {

		if ((factoryImplementation != null) && (factoryImplementation.length() > 0)) {
			String factoryTypeFQCN = factoryType.getName();

			try {
				Object wrappedFactory = bridgeConfigAttributeMap.get(factoryType.getName());
				Object factory = newFactoryInstance(factoryImplementation, factoryType, wrappedFactory);
				bridgeConfigAttributeMap.put(factoryTypeFQCN, factory);
			}
			catch (ClassNotFoundException e) {
				logger.error("{0} : factoryTypeFQCN=[{1}]", e.getClass().getName(), factoryTypeFQCN);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public abstract String getWriteBehindRenderResponseWrapper();

	public abstract String getWriteBehindResourceResponseWrapper();
}
