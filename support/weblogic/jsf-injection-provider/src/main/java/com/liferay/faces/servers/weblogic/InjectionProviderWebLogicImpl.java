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
package com.liferay.faces.servers.weblogic;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;


/**
 * This class serves as a workaround for <a href="http://issues.liferay.com/browse/FACES-1662">FACES-1662</a> which is a
 * portlet-incompatibility that manifests itself when Mojarra is used with WebLogic.
 *
 * @author  Neil Griffin
 */
public class InjectionProviderWebLogicImpl implements InjectionProvider {

	// Logger
	private static final Logger logger = Logger.getLogger(InjectionProviderWebLogicImpl.class.getName());

	// Private Constants
	private static final String WEB_LOGIC_INJECTION_PROVIDER_FQCN = "com.bea.faces.WeblogicInjectionProvider";
	private static final String WEB_CONTAINER_INJECTION_PROVIDER_FQCN =
		"com.sun.faces.vendor.WebContainerInjectionProvider";
	private static Method INJECT_METHOD;
	private static Class<?> INJECTION_PROVIDER_CLASS;
	private static Method INVOKE_POST_CONSTRUCT_METHOD;
	private static Method INVOKE_PRE_DESTROY_METHOD;

	static {

		try {
			INJECTION_PROVIDER_CLASS = Class.forName(WEB_LOGIC_INJECTION_PROVIDER_FQCN);
			INJECT_METHOD = INJECTION_PROVIDER_CLASS.getMethod("inject", new Class[] { Object.class });
			INVOKE_POST_CONSTRUCT_METHOD = INJECTION_PROVIDER_CLASS.getMethod("invokePostConstruct",
					new Class[] { Object.class });
			INVOKE_PRE_DESTROY_METHOD = INJECTION_PROVIDER_CLASS.getMethod("invokePreDestroy",
					new Class[] { Object.class });
		}
		catch (Exception e) {
			logger.log(Level.WARNING, "Unable to discover [" + WEB_LOGIC_INJECTION_PROVIDER_FQCN + "]");
		}

		if (INJECT_METHOD == null) {

			try {
				INJECTION_PROVIDER_CLASS = Class.forName("com.sun.faces.vendor.WebContainerInjectionProvider");
				INJECT_METHOD = INJECTION_PROVIDER_CLASS.getMethod("inject", new Class[] { Object.class });
				INVOKE_POST_CONSTRUCT_METHOD = INJECTION_PROVIDER_CLASS.getMethod("invokePostConstruct",
						new Class[] { Object.class });
				INVOKE_PRE_DESTROY_METHOD = INJECTION_PROVIDER_CLASS.getMethod("invokePreDestroy",
						new Class[] { Object.class });

				logger.log(Level.WARNING, "Falling back to [" + WEB_CONTAINER_INJECTION_PROVIDER_FQCN + "]");
			}
			catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	// Private Data Members
	private InjectionProvider wrappedInjectionProvider;

	public InjectionProviderWebLogicImpl() {

		try {
			wrappedInjectionProvider = (InjectionProvider) INJECTION_PROVIDER_CLASS.newInstance();
			logger.log(Level.INFO, "Wrapping InjectionProvider=" + wrappedInjectionProvider);
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * See {@link InjectionProvider#inject(Object)}
	 */
	public void inject(Object managedBean) throws InjectionProviderException {

		if ((wrappedInjectionProvider != null) && (managedBean != null)) {

			try {
				INJECT_METHOD.invoke(wrappedInjectionProvider, new Object[] { managedBean });
			}
			catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new InjectionProviderException(e);
			}
		}
	}

	/**
	 * See {@link InjectionProvider#invokePostConstruct(Object)}
	 */
	public void invokePostConstruct(Object managedBean) throws InjectionProviderException {

		if ((wrappedInjectionProvider != null) && (managedBean != null)) {

			try {
				INVOKE_POST_CONSTRUCT_METHOD.invoke(wrappedInjectionProvider, new Object[] { managedBean });
			}
			catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new InjectionProviderException(e);
			}
		}
	}

	/**
	 * See {@link InjectionProvider#invokePreDestroy(Object)}
	 */
	public void invokePreDestroy(Object managedBean) throws InjectionProviderException {

		if ((wrappedInjectionProvider != null) && (managedBean != null)) {

			try {
				INVOKE_PRE_DESTROY_METHOD.invoke(wrappedInjectionProvider, new Object[] { managedBean });
			}
			catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new InjectionProviderException(e);
			}
		}
	}

}
