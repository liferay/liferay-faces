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
/**
 * Copyright (c) 2011 tritonsvc.com All rights reserved.
 */
package com.liferay.faces.alloy.el;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;


/**
 * @author  Neil Griffin
 */
public class AUIELResolver extends ELResolver {

	// Private Constants
	private static final String VAR_NAME_AUI = "aui";
	private static final ArrayList<FeatureDescriptor> FEATURE_DESCRIPTORS = new ArrayList<FeatureDescriptor>();
	private static final HashSet<String> VAR_NAMES = new HashSet<String>();

	static {

		// Initialize hash set of supported EL variable names.
		VAR_NAMES.add(VAR_NAME_AUI);

		// Initialize the list of static feature descriptors.
		addFeatureDescriptor(VAR_NAME_AUI, String.class);
	}

	protected static void addFeatureDescriptor(String featureName, Class<?> classType) {
		FeatureDescriptor featureDescriptor = new FeatureDescriptor();
		featureDescriptor.setName(featureName);
		featureDescriptor.setDisplayName(featureName);
		featureDescriptor.setShortDescription(featureName);
		featureDescriptor.setExpert(false);
		featureDescriptor.setHidden(false);
		featureDescriptor.setPreferred(true);
		featureDescriptor.setValue(ELResolver.TYPE, classType);
		featureDescriptor.setValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME, true);
		FEATURE_DESCRIPTORS.add(featureDescriptor);
	}

	protected Object resolveProperty(ELContext elContext, Object base, String property) {
		return null;
	}

	protected Object resolveVariable(ELContext elContext, String varName) {
		Object value = null;

		if (varName != null) {

			if (varName.equals(VAR_NAME_AUI)) {
				value = AUI.getInstance();
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
	public Iterator<?> getFeatureDescriptors(ELContext elContext, Object base) {
		return FEATURE_DESCRIPTORS.iterator();
	}

	@Override
	public Class<?> getType(ELContext elContext, Object base, Object property) {

		if (elContext == null) {

			// Throw an exception as directed by the JavaDoc for ELContext.
			throw new NullPointerException("elContext may not be null");
		}

		return String.class;
	}

	@Override
	public Object getValue(ELContext elContext, Object base, Object property) {

		if (elContext == null) {

			// Throw an exception as directed by the JavaDoc for ELContext.
			throw new NullPointerException("invalid ELContext");
		}
		else {
			Object value = null;

			if (base == null) {

				if (property instanceof String) {
					String varName = (String) property;

					if (VAR_NAMES.contains(varName)) {
						value = resolveVariable(elContext, varName);
					}
				}
			}
			else {

				if (property instanceof String) {
					String propertyName = (String) property;
					value = resolveProperty(elContext, base, propertyName);
				}
			}

			if (value != null) {
				elContext.setPropertyResolved(true);
			}

			return value;
		}
	}

	@Override
	public void setValue(ELContext elContext, Object base, Object property, Object value) {

		if (elContext == null) {

			// Throw an exception as directed by the JavaDoc for ELContext.
			throw new NullPointerException("elContext may not be null");
		}
	}

	@Override
	public boolean isReadOnly(ELContext elContext, Object base, Object property) {
		return true;
	}

}
