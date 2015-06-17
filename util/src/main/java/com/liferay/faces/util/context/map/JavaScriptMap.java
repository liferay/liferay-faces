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
package com.liferay.faces.util.context.map;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.map.AbstractPropertyMap;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * This class serves as a layer of abstraction for a {@link Map} of strings, each representing a separate fragment of
 * JavaScript. If ICEfaces is detected, then the implementation delegates to the ICEfaces {@link
 * org.icefaces.util.JavaScriptRunner} so that the ICEfaces DOMPartialViewContext class can have a chance to render
 * JavaScript fragments. Otherwise, the implementation stores the map as a request attribute.
 *
 * @author      Neil Griffin
 * @deprecated  Call {@link FacesRequestContext#addScript(com.liferay.faces.util.client.Script)} instead.
 */
@Deprecated
public class JavaScriptMap extends AbstractPropertyMap<String> {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(JavaScriptMap.class);

	/**
	 * @deprecated  Call {@link FacesRequestContext#addScript(com.liferay.faces.util.client.Script)} instead.
	 */
	@Deprecated
	@Override
	public String put(String key, String value) {

		FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
		facesRequestContext.addScript(value);

		return super.put(key, value);
	}

	/**
	 * @deprecated  Call {@link FacesRequestContext#addScript(com.liferay.faces.util.client.Script)} instead.
	 */
	@Deprecated
	@Override
	public void putAll(Map<? extends String, ? extends String> map) {

		FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();

		for (Entry<? extends String, ? extends String> entry : map.entrySet()) {
			facesRequestContext.addScript(entry.getValue());
		}

		super.putAll(map);
	}

	@Override
	public String remove(Object key) {

		logger.warn("Removing Script from JavaScriptMap. Script will still be rendered to the response.");

		return super.remove(key);
	}

	@Override
	protected AbstractPropertyMapEntry<String> createPropertyMapEntry(String name) {
		return new JavaScriptMapEntry(this, name);
	}

	@Override
	protected void removeProperty(String name) {
		getRequestAttribute().remove(name);
	}

	@Override
	protected String getProperty(String name) {
		return getRequestAttribute().get(name);
	}

	@Override
	protected void setProperty(String name, String value) {
		Map<String, String> javaScriptMap = getRequestAttribute();
		javaScriptMap.put(name, value);
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
