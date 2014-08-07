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
package com.liferay.faces.jrebel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * @author  Neil Griffin
 */
public class RendererReloader {

	public static void reloadTemplates(Object renderer, Object facesContext) throws Exception {

		// Call the specified renderer's processEvent(SystemEvent) method in order to refresh the templates.
		Class<?> systemEventClass = Class.forName("javax.faces.event.SystemEvent");
		Class<?> systemEventListenerInterface = Class.forName("javax.faces.event.SystemEventListener");
		Class<?> postConstructApplicationEventClass = Class.forName("javax.faces.event.PostConstructApplicationEvent");
		Class<?> applicationClass = Class.forName("javax.faces.application.Application");
		Method processEventMethod = systemEventListenerInterface.getDeclaredMethod("processEvent",
				new Class[] { systemEventClass });
		Method getApplicationMethod = facesContext.getClass().getDeclaredMethod("getApplication", new Class[] {});
		Object application = getApplicationMethod.invoke(facesContext, new Object[] {});
		Constructor<?> postConstructApplicationEventConstructor =
			postConstructApplicationEventClass.getDeclaredConstructor(applicationClass);
		Object postConstructApplicationEvent = postConstructApplicationEventConstructor.newInstance(application);
		processEventMethod.invoke(renderer, new Object[] { postConstructApplicationEvent });
	}
}
