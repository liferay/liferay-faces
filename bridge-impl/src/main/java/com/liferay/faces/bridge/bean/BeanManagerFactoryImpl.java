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
package com.liferay.faces.bridge.bean;

import java.util.List;

import com.liferay.faces.bridge.config.ConfiguredBean;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class BeanManagerFactoryImpl extends BeanManagerFactory {

	// Private Constants
	private static final boolean MOJARRA_DETECTED = ProductMap.getInstance().get(ProductConstants.JSF).getTitle()
		.equals(ProductConstants.MOJARRA);

	// Private Data Members
	List<ConfiguredBean> configuredBeans;

	@Override
	public BeanManager getBeanManager() {

		BeanManager beanManager = null;

		if (MOJARRA_DETECTED) {
			beanManager = new BeanManagerMojarraImpl(configuredBeans);
		}
		else {
			beanManager = new BeanManagerImpl(configuredBeans);
		}

		return beanManager;
	}

	@Override
	public void setConfiguredBeans(List<ConfiguredBean> configuredBeans) {
		this.configuredBeans = configuredBeans;
	}

	public BeanManagerFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
