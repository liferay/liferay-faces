/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.client.internal;

import java.lang.reflect.Method;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.internal.RendererUtil;


/**
 * This class is an ICEfaces-specific implementation of the {@link ClientScript} interface. Note that reflection is used
 * in order to avoid a compile-time dependency on ICEfaces.
 *
 * @author  Kyle Stiemann
 */
public class ClientScriptICEfacesImpl implements ClientScript {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ClientScriptICEfacesImpl.class);

	// Private Constants
	private static final String FQCN_ICEFACES_JS_RUNNER = "org.icefaces.util.JavaScriptRunner";
	private static final String METHOD_RUNSCRIPT = "runScript";
	private static final String METHOD_COLLATESCRIPTS = "collateScripts";

	@Override
	public void append(String content, String use) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		StringBuilder stringBuilder = new StringBuilder();

		if ((use == null) || (use.trim().length() == 0)) {
			stringBuilder.append(content);
		}
		else {

			String[] useArray = use.split(StringPool.COMMA);
			stringBuilder.append(RendererUtil.getAlloyBeginScript(facesContext, useArray));
			stringBuilder.append(content);
			stringBuilder.append("});");
		}

		try {
			Class<?> jsRunnerClass = Class.forName(FQCN_ICEFACES_JS_RUNNER);
			Method runScriptMethod = jsRunnerClass.getMethod(METHOD_RUNSCRIPT,
					new Class[] { FacesContext.class, String.class });
			runScriptMethod.invoke(null, new Object[] { facesContext, stringBuilder.toString() });
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void clear() {
		// no-op
	}

	@Override
	public String toString() {

		String toString = null;

		try {
			Class<?> jsRunnerClass = Class.forName(FQCN_ICEFACES_JS_RUNNER);
			Method collateScriptsMethod = jsRunnerClass.getMethod(METHOD_COLLATESCRIPTS,
					new Class[] { FacesContext.class });
			FacesContext facesContext = FacesContext.getCurrentInstance();
			toString = (String) collateScriptsMethod.invoke(null, new Object[] { facesContext });
		}
		catch (Exception e) {
			logger.error(e);
		}

		return toString;
	}
}
