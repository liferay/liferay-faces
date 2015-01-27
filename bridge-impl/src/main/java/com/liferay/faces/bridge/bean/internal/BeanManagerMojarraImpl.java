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
package com.liferay.faces.bridge.bean.internal;

import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.config.ConfiguredManagedBean;
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

	public BeanManagerMojarraImpl(List<ConfiguredManagedBean> configuredManagedBeans) {
		super(configuredManagedBeans);
	}

	@Override
	public void invokePreDestroyMethods(Object managedBean, boolean preferPreDestroy) {

		if (preferPreDestroy) {

			// Get the Mojarra InjectionProvider singleton instance that was hopefully discovered at startup.
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			MojarraInjectionProvider mojarraInjectionProvider = (MojarraInjectionProvider) applicationMap.get(
					MojarraInjectionProvider.class.getName());

			if (mojarraInjectionProvider == null) {

				logger.debug(
					"Directly invoking managedBean=[{0}] methods annotated with @PreDestroy since Mojarra InjectionProvider was not discovered",
					managedBean);

				super.invokePreDestroyMethods(managedBean, preferPreDestroy);
			}
			else {

				try {

					if (logger.isDebugEnabled()) {
						logger.debug("Calling invokePreDestroy for injectionProvider=[{0}] managedBean=[{1}]",
							mojarraInjectionProvider, managedBean);
					}

					mojarraInjectionProvider.invokePreDestroy(managedBean);
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
