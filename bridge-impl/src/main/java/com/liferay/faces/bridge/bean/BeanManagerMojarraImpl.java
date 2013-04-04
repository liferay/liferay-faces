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
package com.liferay.faces.bridge.bean;

import java.lang.reflect.Method;
import java.util.List;

import com.liferay.faces.bridge.config.ConfiguredBean;
import com.liferay.faces.bridge.servlet.BridgeSessionListener;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * Mojarra has a vendor-specific Service Provider Interface (SPI) for dependency injection called the InjectionProvider.
 * This class provides the ability to leverage the InjectionProvider instance for various operations.
 *
 * @author  Neil Griffin
 */
public class BeanManagerMojarraImpl extends BeanManagerImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BeanManagerMojarraImpl.class);

	// Private Constants
	private static final String INVOKE_PRE_DESTROY = "invokePreDestroy";

	public BeanManagerMojarraImpl(List<ConfiguredBean> configuredBeans) {
		super(configuredBeans);
	}

	@Override
	public void invokePreDestroyMethods(Object managedBean, boolean preferPreDestroy) {

		if (preferPreDestroy) {
			Object mojarraInjectionProvider = BridgeSessionListener.getMojarraInjectionProvider();

			if (mojarraInjectionProvider == null) {
				super.invokePreDestroyMethods(managedBean, preferPreDestroy);
			}
			else {

				try {
					Method invokePreDestroyMethod = mojarraInjectionProvider.getClass().getMethod(INVOKE_PRE_DESTROY,
							new Class[] { Object.class });

					logger.debug("Calling invokePreDestroy for mojarraInjectionProvider=[{0}] managedBean=[{1}]",
						mojarraInjectionProvider, managedBean);
					invokePreDestroyMethod.invoke(mojarraInjectionProvider, managedBean);
				}
				catch (Exception e) {
					logger.error(e);
				}
			}

		}
		else {
			super.invokePreDestroyMethods(managedBean, preferPreDestroy);
		}
	}

}
