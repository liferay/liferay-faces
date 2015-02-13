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
package com.liferay.faces.bridge.renderkit.primefaces.internal;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.renderkit.html_basic.internal.BodyRendererBridgeImpl;
import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.client.ClientScriptFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class is a portlet-compatible version of the PrimeFaces 5.x BodyRenderer. For more information, see: FACES-1977.
 * Note that reflection is used to access the PrimeFaces RequestContext in order to avoid a compile-time dependency on a
 * specific version of PrimeFaces.
 *
 * @author  Neil Griffin
 */
public class BodyRendererPrimeFacesImpl extends BodyRendererBridgeImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BodyRendererPrimeFacesImpl.class);

	// Private Constants
	private static final Method GET_CURRENT_INSTANCE_METHOD;
	private static final Method GET_SCRIPTS_TO_EXECUTE_METHOD;

	static {

		Method getCurrentInstanceMethod = null;
		Method getScriptsToExecuteMethod = null;

		try {
			Class<?> requestContextClass = Class.forName("org.primefaces.context.RequestContext");
			getCurrentInstanceMethod = requestContextClass.getDeclaredMethod("getCurrentInstance");
			getScriptsToExecuteMethod = requestContextClass.getMethod("getScriptsToExecute");
		}
		catch (Exception e) {
			logger.error(e);
		}

		GET_CURRENT_INSTANCE_METHOD = getCurrentInstanceMethod;
		GET_SCRIPTS_TO_EXECUTE_METHOD = getScriptsToExecuteMethod;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Delegate to the superclass to render any resources with target="body" and also the closing </div> tag.
		super.encodeEnd(facesContext, uiComponent);

		// If this is not an Ajax request, then render each of the PrimeFaces scripts at the bottom of the portal page.
		if (!facesContext.getPartialViewContext().isAjaxRequest()) {

			Object requestContext = getRequestContextCurrentInstance();

			try {
				List<String> scriptsToExecute = (List<String>) GET_SCRIPTS_TO_EXECUTE_METHOD.invoke(requestContext);

				if (scriptsToExecute != null) {
					ClientScriptFactory clientScriptFactory = (ClientScriptFactory) BridgeFactoryFinder.getFactory(
						ClientScriptFactory.class);
					ClientScript clientScript = clientScriptFactory.getClientScript();

					for (String scriptToExecute : scriptsToExecute) {
						clientScript.append(scriptToExecute, null);
					}
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	protected Object getRequestContextCurrentInstance() {

		Object requestContext = null;

		try {
			requestContext = GET_CURRENT_INSTANCE_METHOD.invoke(null);
		}
		catch (Exception e) {
			logger.error(e);
		}

		return requestContext;
	}
}
