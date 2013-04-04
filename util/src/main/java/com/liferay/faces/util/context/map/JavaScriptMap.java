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
package com.liferay.faces.util.context.map;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.map.AbstractPropertyMap;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * This class serves as a layer of abstraction for a {@link Map} of strings, each representing a separate fragment of
 * JavaScript. If ICEfaces is detected, then the implementation delegates to the ICEfaces {@link
 * org.icefaces.util.JavaScriptRunner} so that the ICEfaces DOMPartialViewContext class can have a chance to render
 * JavaScript fragments. Otherwise, the implementation stores the map as a request attribute.
 *
 * @author  Neil Griffin
 */
public class JavaScriptMap extends AbstractPropertyMap<String> {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(JavaScriptMap.class);

	// Private Constants
	private static final String FQCN_ICEFACES_JS_RUNNER = "org.icefaces.util.JavaScriptRunner";
	private static final boolean ICEFACES_DETECTED = ProductMap.getInstance().get(ProductConstants.ICEFACES)
		.isDetected();
	private static final String METHOD_RUNSCRIPT = "runScript";

	@Override
	protected AbstractPropertyMapEntry<String> createPropertyMapEntry(String name) {
		return new JavaScriptMapEntry(this, name);
	}

	@Override
	protected void removeProperty(String name) {

		if (!ICEFACES_DETECTED) {
			getRequestAttribute().remove(name);
		}
	}

	@Override
	protected String getProperty(String name) {

		if (ICEFACES_DETECTED) {
			return null;
		}
		else {
			return getRequestAttribute().get(name);
		}
	}

	@Override
	protected void setProperty(String name, String value) {

		if (ICEFACES_DETECTED) {

			try {
				Class<?> jsRunnerClass = Class.forName(FQCN_ICEFACES_JS_RUNNER);
				Method runScriptMethod = jsRunnerClass.getMethod(METHOD_RUNSCRIPT,
						new Class[] { FacesContext.class, String.class });
				FacesContext facesContext = FacesContext.getCurrentInstance();
				runScriptMethod.invoke(null, new Object[] { facesContext, value });
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
		else {
			Map<String, String> javaScriptMap = getRequestAttribute();
			javaScriptMap.put(name, value);
		}
	}

	@Override
	protected Enumeration<String> getPropertyNames() {
		return Collections.enumeration(getRequestAttribute().keySet());
	}

	protected Map<String, String> getRequestAttribute() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
		String key = JavaScriptMap.class.getName();
		@SuppressWarnings("unchecked")
		Map<String, String> javaScriptMap = (Map<String, String>) requestMap.get(key);

		if (javaScriptMap == null) {
			javaScriptMap = new HashMap<String, String>();
			requestMap.put(key, javaScriptMap);
		}

		return javaScriptMap;
	}
}
