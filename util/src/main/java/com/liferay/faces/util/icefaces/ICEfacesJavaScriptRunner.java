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
package com.liferay.faces.util.icefaces;

import java.lang.reflect.Method;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ICEfacesJavaScriptRunner {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ICEfacesJavaScriptRunner.class);

	// Private Constants
	private static final String FQCN_ICEFACES_JS_RUNNER = "com.icesoft.faces.context.effects.JavascriptContext";
	private static final String METHOD_RUNSCRIPT = "addJavascriptCall";

	public static final void runScript(FacesContext facesContext, String script) {

		try {
			Class<?> jsRunnerClass = Class.forName(FQCN_ICEFACES_JS_RUNNER);
			Method runScriptMethod = jsRunnerClass.getMethod(METHOD_RUNSCRIPT,
					new Class[] { FacesContext.class, String.class });
			runScriptMethod.invoke(null, new Object[] { facesContext, script });
		}
		catch (Exception e) {
			logger.error(e);
		}

	}
}
