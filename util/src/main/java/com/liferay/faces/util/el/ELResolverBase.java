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
package com.liferay.faces.util.el;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;


/**
 * @author  Neil Griffin
 */
public abstract class ELResolverBase extends ELResolver {

	private static final ArrayList<FeatureDescriptor> FEATURE_DESCRIPTORS = new ArrayList<FeatureDescriptor>();

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

	protected abstract Object resolveProperty(ELContext elContext, Object base, String property);

	protected abstract Object resolveVariable(ELContext elContext, String varName);

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext elContext, Object base) {

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
					value = resolveVariable(elContext, varName);
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
