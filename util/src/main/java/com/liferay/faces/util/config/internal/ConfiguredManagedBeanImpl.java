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
package com.liferay.faces.util.config.internal;

import com.liferay.faces.util.config.ConfiguredManagedBean;


/**
 * @author  Neil Griffin
 */
public class ConfiguredManagedBeanImpl implements ConfiguredManagedBean {

	// Private Data Members
	private String managedBeanClass;
	private String managedBeanName;
	private String managedBeanScope;

	public ConfiguredManagedBeanImpl(String managedBeanClass, String managedBeanName, String managedBeanScope) {
		this.managedBeanClass = managedBeanClass;
		this.managedBeanName = managedBeanName;
		this.managedBeanScope = managedBeanScope;
	}

	public String getManagedBeanClass() {
		return managedBeanClass;
	}

	public String getManagedBeanName() {
		return managedBeanName;
	}

	public String getManagedBeanScope() {
		return managedBeanScope;
	}

}
