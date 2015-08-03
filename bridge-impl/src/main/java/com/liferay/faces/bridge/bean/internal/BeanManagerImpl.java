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
package com.liferay.faces.bridge.bean.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.faces.util.config.ConfiguredManagedBean;


/**
 * @author  Neil Griffin
 */
public class BeanManagerImpl extends BeanManagerCompatImpl {

	// Private Constants
	private static final String JAVAX_PORTLET_P = "javax.portlet.p.";

	// Private Data Members
	private Map<String, ConfiguredManagedBean> configuredManagedBeanSet;

	public BeanManagerImpl(List<ConfiguredManagedBean> configuredManagedBeans) {

		this.configuredManagedBeanSet = new HashMap<String, ConfiguredManagedBean>();

		if (configuredManagedBeans != null) {

			for (ConfiguredManagedBean configuredManagedBean : configuredManagedBeans) {
				this.configuredManagedBeanSet.put(configuredManagedBean.getManagedBeanName(), configuredManagedBean);
			}
		}
	}

	public boolean isManagedBean(String name, Object value) {

		boolean managedBean = false;

		if (value != null) {

			if (hasManagedBeanAnnotation(value)) {
				managedBean = true;
			}
			else {

				if (name != null) {

					// Section PLT.18.3 of the Portlet 2.0 Specification titled "Binding Attributes into a Session"
					// requires that PortletSession attribute names be namespaced/prefixed with the
					// "javax.portlet.p.<ID>?" pattern. In order to determine if the specified name is a SessionScoped
					// managed-bean, it is necessary to first strip the pattern from it.
					if (name.startsWith(JAVAX_PORTLET_P)) {
						int pos = name.indexOf("?");

						if (pos > 0) {
							name = name.substring(pos + 1);
						}
					}

					ConfiguredManagedBean configuredManagedBean = configuredManagedBeanSet.get(name);

					if (configuredManagedBean != null) {
						String managedBeanClass = value.getClass().getName();
						managedBean = managedBeanClass.equals(configuredManagedBean.getManagedBeanClass());
					}
				}
			}
		}

		return managedBean;
	}
}
