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
package com.liferay.faces.bridge.bean;

import java.util.List;

import com.liferay.faces.bridge.FactoryWrapper;
import com.liferay.faces.bridge.config.ConfiguredBean;


/**
 * @author  Neil Griffin
 */
public abstract class BeanManagerFactory implements FactoryWrapper<BeanManagerFactory> {

	public abstract BeanManager getBeanManager();

	/**
	 * This method informs the factory of the managed-bean entries that are discovered in META-INF/faces-config.xml
	 * WEB-INF/faces-config.xml descriptors. It is designed to be called during startup.
	 */
	public abstract void setConfiguredBeans(List<ConfiguredBean> configuredBeans);
}
