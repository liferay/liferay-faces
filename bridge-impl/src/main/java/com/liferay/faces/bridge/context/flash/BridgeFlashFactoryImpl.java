/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.flash;

import java.lang.reflect.Method;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * <p>This class provides a factory-style way of obtaining the JSF {@link Flash} scope instance provided by the JSF
 * runtime (Mojarra or MyFaces).</p>
 *
 * <p>Background: The JSF 2.0/2.1 API does not currently provide a factory-style way of obtaining {@link Flash} scope
 * instances. Instead, the {@link ExternalContext#getFlash()} method inside the JSF runtime is responsible for acting as
 * a pseudo-factory for creating instances. If the bridge were to use that approach for obtaining the {@link Flash}
 * scope instance, it would require the bridge to create instances of the JSF runtime's {@link ExternalContext}. Such an
 * operation would impose a big cost/overhead/expense, and since there are Servlet-API dependencies involved, it would
 * require the bridge to provide hack implementations of the {@link javax.servlet.ServletContext}, {@link
 * javax.servlet.http.HttpServletRequest}, and {@link javax.servlet.http.HttpServletResponse}. All this would be
 * required, simply to call the {@link ExternalContext#getFlash()} method.</p>
 *
 * <p>As an alternative approach, this class simply uses Java reflection to obtain the JSF {@link Flash} scope instance
 * from the {@link ClassLoader}. While it is true that Java reflection is expensive, it is certainly less expensive than
 * the {@link ExternalContext#getFlash()} approach described above. Hopefully we can get a factory-style way of
 * obtaining {@link Flash} scope instances in the JSF 2.2 API, which would make this class obsolete.</p>
 *
 * @author  Neil Griffin
 */
public class BridgeFlashFactoryImpl extends BridgeFlashFactory {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeFlashFactory.class);

	// Private Constants
	private static final String MOJARRA_FLASH_FQCN = "com.sun.faces.context.flash.ELFlash";
	private static final String MOJARRA_GET_FLASH_METHOD_NAME = "getFlash";
	private static final String MYFACES_FLASH_FQCN_NEW = "org.apache.myfaces.shared.context.flash.FlashImpl";
	private static final String MYFACES_FLASH_FQCN_OLD = "org.apache.myfaces.shared_impl.context.flash.FlashImpl";
	private static final String MYFACES_GET_FLASH_METHOD_NAME = "getCurrentInstance";
	private static final String WARNING_MSG = "Unable to create an instance of [{0}]; falling back to [{1}]";
	private static final Method mojarraGetFlashMethod;
	private static final Method myFacesGetFlashMethod;

	static {

		Method mojarraMethod = null;
		Method myFacesMethod = null;

		try {
			Class<?> mojarraFlashClass = Class.forName(MOJARRA_FLASH_FQCN);
			mojarraMethod = mojarraFlashClass.getMethod(MOJARRA_GET_FLASH_METHOD_NAME, new Class[] {});
		}
		catch (Exception e1) {

			try {
				Class<?> myFacesFlashClass = Class.forName(MYFACES_FLASH_FQCN_NEW);
				myFacesMethod = myFacesFlashClass.getMethod(MYFACES_GET_FLASH_METHOD_NAME,
						new Class[] { ExternalContext.class });
			}
			catch (Exception e2) {

				try {
					Class<?> myFacesFlashClass = Class.forName(MYFACES_FLASH_FQCN_OLD);
					myFacesMethod = myFacesFlashClass.getMethod(MYFACES_GET_FLASH_METHOD_NAME,
							new Class[] { ExternalContext.class });
				}
				catch (Exception e3) {
					logger.error("Classloader unable to find either the Mojarra or MyFaces Flash implementations");
				}
			}
		}

		mojarraGetFlashMethod = mojarraMethod;
		myFacesGetFlashMethod = myFacesMethod;
	}

	public BridgeFlashFactoryImpl() {
	}

	@Override
	public BridgeFlash getBridgeFlash() {

		BridgeFlash bridgeFlash = null;

		if (mojarraGetFlashMethod != null) {
			Flash mojarraFlash;

			try {
				mojarraFlash = (Flash) mojarraGetFlashMethod.invoke(null, new Object[] {});
				bridgeFlash = new BridgeFlashMojarraImpl(mojarraFlash);
			}
			catch (Exception e) {
				logger.warn(WARNING_MSG, MOJARRA_FLASH_FQCN, BridgeFlashFallbackImpl.class.getName());
				bridgeFlash = new BridgeFlashFallbackImpl();
			}
		}
		else if (myFacesGetFlashMethod != null) {
			Flash myFacesFlash;

			try {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				myFacesFlash = (Flash) myFacesGetFlashMethod.invoke(null, externalContext);
				bridgeFlash = new BridgeFlashMyFacesImpl(myFacesFlash);
			}
			catch (Exception e) {
				logger.warn(WARNING_MSG, MYFACES_FLASH_FQCN_NEW, BridgeFlashFallbackImpl.class.getName());
				bridgeFlash = new BridgeFlashFallbackImpl();
			}
		}

		return bridgeFlash;
	}

	public BridgeFlashFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
