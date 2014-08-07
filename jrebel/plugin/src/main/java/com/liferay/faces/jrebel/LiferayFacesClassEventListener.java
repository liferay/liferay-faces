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

import org.zeroturnaround.javarebel.ClassEventListener;
import org.zeroturnaround.javarebel.LoggerFactory;


/**
 * This class currently serves as an example for how to listen to class reload events.
 *
 * @author  Neil Griffin
 */
public class LiferayFacesClassEventListener implements ClassEventListener {

	@SuppressWarnings("rawtypes")
	public void onClassEvent(int eventType, Class clazz) {

		try {
			Class<?> rendererClass = Class.forName("javax.faces.render.Renderer");
			Class<?> systemEventListenerInterface = Class.forName("javax.faces.event.SystemEventListener");

			if (rendererClass.isAssignableFrom(clazz) && systemEventListenerInterface.isAssignableFrom(clazz)) {
				System.out.println("Renderer class was reloaded!");
			}
		}
		catch (Exception e) {
			LoggerFactory.getInstance().error(e);
			System.err.println(e);
		}
	}

	public int priority() {
		return 0;
	}
}
