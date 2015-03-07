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
package com.liferay.faces.alloy.taghandler;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

import com.liferay.faces.util.jsp.JspTagConfig;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class LoadConstants extends TagHandler {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LoadConstants.class);

	// Private Cache
	private static final Map<String, Map<String, Object>> CONSTANT_MAP_CACHE =
		new ConcurrentHashMap<String, Map<String, Object>>();

	// Private Constants
	private static final int CACHE_MAX_SIZE = 50;

	// Protected Enumerations
	protected enum PropertyKeys {
		cacheable, classType, var
	}

	// Private Data Members
	private boolean cacheable = true;
	private String classType;
	private String var;

	// Workaround for https://issues.liferay.com/browse/FACES-1576
	public LoadConstants() throws Exception {
		super(new JspTagConfig());
	}

	public LoadConstants(TagConfig config) throws Exception {

		super(config);

		TagAttribute typeAttr = getAttribute(PropertyKeys.classType.toString());

		if (typeAttr == null) {
			throw new NullPointerException("classType is required.");
		}

		this.classType = typeAttr.getValue();

		TagAttribute varAttr = getAttribute(PropertyKeys.var.toString());

		if (varAttr == null) {
			throw new NullPointerException("var is required.");
		}

		this.var = varAttr.getValue();

		TagAttribute cacheableAttr = getAttribute(PropertyKeys.cacheable.toString());

		if (cacheableAttr != null) {
			this.cacheable = Boolean.parseBoolean(cacheableAttr.getValue());
		}
	}

	@Override
	public void apply(FaceletContext facesContext, UIComponent parentUIComponent) throws IOException {

		if (cacheable && CONSTANT_MAP_CACHE.containsKey(classType)) {
			facesContext.setAttribute(var, CONSTANT_MAP_CACHE.get(classType));
		}
		else {

			Map<String, Object> constantMap = new HashMap<String, Object>();

			try {
				Class<?> clazz = Class.forName(classType);
				Field[] fields = clazz.getFields();

				for (Field field : fields) {
					int modifiers = field.getModifiers();

					if (Modifier.isStatic(modifiers)) {
						constantMap.put(field.getName(), field.get(null));
					}
				}

				if (cacheable) {

					if ((CONSTANT_MAP_CACHE.size() + 1) > CACHE_MAX_SIZE) {
						CONSTANT_MAP_CACHE.clear();
					}

					CONSTANT_MAP_CACHE.put(classType, constantMap);
				}

				facesContext.setAttribute(var, constantMap);
			}
			catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("Referenced class cannot be found.", e);
			}
			catch (IllegalAccessException e) {
				logger.error(e);
			}
		}
	}
}
