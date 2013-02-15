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

package com.liferay.faces.portal.el;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;
import javax.portlet.faces.InvalidArgumentException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class ImportConstants extends TagHandler {

	// Tag Attribute Names
	private static final String ATTRIBUTE_NAME_CACHEABLE = "cacheable";
	private static final String ATTRIBUTE_NAME_CLASS_TYPE = "classType";
	private static final String ATTRIBUTE_NAME_INCLUDE_NON_PUBLIC = "includeNonPublic";
	private static final String ATTRIBUTE_NAME_VAR = "var";

	// Constant Map Cache Size
	private static final int CACHE_SIZE = 20;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ImportConstants.class);

	private static Map<String, Map<String, Object>> constantMapCache;

	private boolean cacheable = true;
	private String classType;
	private boolean includeNonPublic = false;
	private String var;

	public ImportConstants(TagConfig config) throws Exception {
		super(config);

		TagAttribute classTypeAttr = getAttribute(ATTRIBUTE_NAME_CLASS_TYPE);
		TagAttribute varAttr = getAttribute(ATTRIBUTE_NAME_VAR);

		if (varAttr == null || classTypeAttr == null) {
			throw new InvalidArgumentException("Attributes var and classType cannot be null");
		}

		var = varAttr.getValue();
		classType = classTypeAttr.getValue();

		TagAttribute cacheableAttr = getAttribute(ATTRIBUTE_NAME_CACHEABLE);
		if (cacheableAttr != null) {
			cacheable = Boolean.parseBoolean(cacheableAttr.getValue());
		}

		TagAttribute includeNonPublicAttr = getAttribute(ATTRIBUTE_NAME_INCLUDE_NON_PUBLIC);
		if (includeNonPublicAttr != null) {
			includeNonPublic = Boolean.parseBoolean(includeNonPublicAttr.getValue());
		}
	}

	@Override
	public void apply(FaceletContext context, UIComponent parent) throws IOException {
		if (cacheable && getConstantMapCache().containsKey(classType)) {
			context.setAttribute(var, getConstantMapCache().get(classType));

			return;
		}

		Map<String, Object> constantMap = new HashMap<String, Object>();

		try {
			Class clazz = Class.forName(classType);
			Field[] fields = clazz.getDeclaredFields();

			for (Field field : fields) {
				int modifiers = field.getModifiers();

				if (Modifier.isStatic(modifiers)) {
					if (includeNonPublic && !Modifier.isPublic(modifiers)) {
						field.setAccessible(true);
					}

					if (includeNonPublic || Modifier.isPublic(modifiers)) {
						constantMap.put(field.getName(), field.get(null));
					}
				}
			}

			if (cacheable) {
				if ((getConstantMapCache().size() + 1) > CACHE_SIZE) {
					getConstantMapCache().clear();
				}

				getConstantMapCache().put(classType, constantMap);
			}

			context.setAttribute(var, constantMap);
		}
		catch (ClassNotFoundException cnfe) {
			throw new InvalidArgumentException("Referenced class cannot be found");
		}
		catch (IllegalAccessException iae) {
			throw new InvalidArgumentException("One of the fields of the specified class cannot be accessed");
		}
	}

	protected Map<String, Map<String, Object>> getConstantMapCache() {
		if (constantMapCache == null) {
			constantMapCache = new HashMap<String, Map<String, Object>>();
		}

		return constantMapCache;
	}

}
