/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.faces.annotation.BridgePreDestroy;

import com.liferay.faces.bridge.config.ConfiguredBean;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BeanManagerImpl extends BeanManagerCompatImpl {

	// Private Constants
	private static final String JAVAX_ANNOTATION_PRE_DESTROY = "javax.annotation.PreDestroy";
	private static final String JAVAX_ANNOTATION_BRIDGE_PRE_DESTROY = "javax.portlet.faces.annotation.BridgePreDestroy";
	private static final String JAVAX_PORTLET_P = "javax.portlet.p.";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BeanManagerImpl.class);

	// Private Data Members
	private Map<String, ConfiguredBean> configuredBeanSet;

	public BeanManagerImpl(List<ConfiguredBean> configuredBeans) {
		this.configuredBeanSet = new HashMap<String, ConfiguredBean>();

		if (configuredBeans != null) {

			for (ConfiguredBean configuredBean : configuredBeans) {
				configuredBeanSet.put(configuredBean.getManagedBeanName(), configuredBean);
			}
		}
	}

	public void invokePreDestroyMethods(Object managedBean, boolean preferPreDestroy) {

		if (managedBean != null) {

			Class<?> clazz = managedBean.getClass();
			Method[] methods = managedBean.getClass().getMethods();

			if (methods != null) {

				for (Method method : methods) {

					if (preferPreDestroy) {

						if (hasPreDestroyAnnotation(method)) {

							try {
								logger.debug("Invoking @PreDestroy method named [{0}] on managedBean class=[{1}]",
									method.getName(), clazz.getName());
								method.invoke(managedBean, new Object[] {});
							}
							catch (Exception e) {
								logger.error(e.getMessage(), e);
							}
						}
					}
					else {

						if (hasBridgePreDestroyAnnotation(method)) {

							try {
								logger.debug("Invoking @BridgePreDestroy method named [{0}] on managedBean class=[{1}]",
									method.getName(), clazz.getName());
								method.invoke(managedBean, new Object[] {});
							}
							catch (Exception e) {
								logger.error(e.getMessage(), e);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Determines whether or not the specified method is annotated with the {@link BridgePreDestroy} annotation. Note
	 * that the method signature must also have a void return type an zero parameters in order for this method to return
	 * true.
	 *
	 * @param   method  The method to check.
	 *
	 * @return  true if the specified method is annotated with a PreDestroy annotation.
	 */
	protected boolean hasBridgePreDestroyAnnotation(Method method) {

		if (method.getReturnType() == Void.TYPE) {
			Class<?>[] parameterTypes = method.getParameterTypes();

			if ((parameterTypes == null) || (parameterTypes.length == 0)) {
				Annotation[] annotations = method.getAnnotations();

				if (annotations != null) {

					for (Annotation annotation : annotations) {

						if (annotation.annotationType().getName().equals(JAVAX_ANNOTATION_BRIDGE_PRE_DESTROY)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * Determines whether or not the specified method is annotated with the {@link PreDestroy} annotation. Note that the
	 * method signature must also have a void return type an zero parameters in order for this method to return true.
	 *
	 * @param   method  The method to check.
	 *
	 * @return  true if the specified method is annotated with a PreDestroy annotation.
	 */
	protected boolean hasPreDestroyAnnotation(Method method) {

		if (method.getReturnType() == Void.TYPE) {
			Class<?>[] parameterTypes = method.getParameterTypes();

			if ((parameterTypes == null) || (parameterTypes.length == 0)) {
				Annotation[] annotations = method.getAnnotations();

				if (annotations != null) {

					for (Annotation annotation : annotations) {

						if (annotation.annotationType().getName().equals(JAVAX_ANNOTATION_PRE_DESTROY)) {
							return true;
						}
					}
				}
			}
		}

		return false;
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
						int pos = name.indexOf(StringPool.QUESTION);

						if (pos > 0) {
							name = name.substring(pos + 1);
						}
					}

					ConfiguredBean configuredBean = configuredBeanSet.get(name);

					if (configuredBean != null) {
						String managedBeanClass = value.getClass().getName();
						managedBean = managedBeanClass.equals(configuredBean.getManagedBeanClass());
					}
				}
			}
		}

		return managedBean;
	}
}
