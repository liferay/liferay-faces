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
package com.liferay.faces.bridge.application;

import java.lang.reflect.Method;

import javax.faces.context.ExternalContext;

import com.liferay.faces.bridge.bean.MojarraInjectionProvider;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class MojarraApplicationAssociate {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MojarraApplicationAssociate.class);

	// Private Constants
	private static Method getInstanceMethod = null;

	static {

		try {
			Class<?> mojarraApplicationAssociateClass = Class.forName("com.sun.faces.application.ApplicationAssociate");
			getInstanceMethod = mojarraApplicationAssociateClass.getMethod("getInstance",
					new Class[] { ExternalContext.class });
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public static MojarraInjectionProvider getInjectionProvider(ExternalContext externalContext) {

		try {

			Object applicationAssociate = getInstanceMethod.invoke(null, externalContext);

			// If the ApplicationAssociate instance is available, then return the InjectionProvider that it knows about.
			if (applicationAssociate != null) {

				// Note that the ApplicationAssociate instance will be available during startup if the Mojarra
				// ConfigureListener executes prior to the BridgeSessionListener. It will also be available during
				// execution of the JSF lifecycle.
				Method getInjectionProviderMethod = applicationAssociate.getClass().getMethod("getInjectionProvider",
						new Class[] {});
				Object injectionProvider = getInjectionProviderMethod.invoke(applicationAssociate, new Object[] {});
				logger.debug("Mojarra injectionProvider=[{0}]", injectionProvider);

				return new MojarraInjectionProvider(injectionProvider);
			}

			// Otherwise, return null.
			else {

				// Note that the ApplicationAssociate instance will be null if this method is called during startup and
				// the BridgeSessionListener executes prior to the Mojarra ConfigureListener. This can be remedied by
				// explicitly specifying com.sun.faces.config.ConfigureListener as a listener in the WEB-INF/web.xml
				// descriptor.
				return null;
			}
		}
		catch (Exception e) {
			logger.error(e);

			return null;
		}
	}
}
