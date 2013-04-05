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
package com.liferay.faces.portal.el;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotWritableException;

import com.liferay.faces.util.el.ELResolverBase;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ExtELResolver extends ELResolverBase {

	public static final String VAR_NAME_I18N = "i18n";

	private static final Logger logger = LoggerFactory.getLogger(ExtELResolver.class);

	static {

		// Initialize the list of static feature descriptors.
		addFeatureDescriptor(VAR_NAME_I18N, String.class);
	}

	private I18N i18n;

	@Override
	protected Object resolveProperty(ELContext elContext, Object base, String property) {

		return null;
	}

	@Override
	protected Object resolveVariable(ELContext elContext, String varName) {

		Object value = null;

		try {

			if (varName.equals(VAR_NAME_I18N)) {

				if (i18n == null) {
					i18n = new I18N();
				}

				value = i18n;
			}
		}
		catch (Exception e) {
			throw new ELException("Failed to resolve variable [" + varName + "]", e);
		}

		if (value == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("Unable to resolve variable [" + varName + "] value=" + value);
			}
		}
		else {

			if (logger.isDebugEnabled()) {
				logger.debug("Resolved variable [" + varName + "] value=" + value);
			}
		}

		return value;
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext elContext, Object base) {

		Class<?> commonPropertyType = null;

		return commonPropertyType;
	}

	@Override
	public void setValue(ELContext elContext, Object base, Object property, Object value) {

		if (elContext == null) {

			// Throw an exception as directed by the JavaDoc for ELContext.
			throw new NullPointerException("elContext may not be null");
		}

		if ((property != null) && (property instanceof String)) {
			String propertyAsString = (String) property;

			if (propertyAsString.equals(VAR_NAME_I18N)) {
				throw new PropertyNotWritableException(propertyAsString);
			}
		}
	}

	@Override
	public boolean isReadOnly(ELContext elContext, Object base, Object property) {

		return true;
	}
}
