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
package com.liferay.faces.bridge.container;

/**
 * @author  Neil Griffin
 */
public class PortletContainerDetector {

	// Private Constants
	private static final String OBJECT_NAME_PREFIX_LIFERAY = "com.liferay";
	private static final String OBJECT_NAME_PREFIX_PLUTO = "org.apache.pluto";

	/**
	 * Determines whether or not the specified object is one created by Liferay Portal.
	 *
	 * @param   portletURL  The portletURL that may have been created by Liferay Portal.
	 *
	 * @return  true if the specified portletURL was created by Liferay Portal.
	 */
	public static boolean isLiferayObject(Object obj) {

		if (obj != null) {
			return obj.getClass().getName().startsWith(OBJECT_NAME_PREFIX_LIFERAY);
		}
		else {
			return false;
		}
	}

	/**
	 * Determines whether or not the specified object is one created by Pluto.
	 *
	 * @param   portletURL  The portletURL that may have been created by Pluto.
	 *
	 * @return  true if the specified portletURL was created by Pluto.
	 */
	public static boolean isPlutoObject(Object obj) {

		if (obj != null) {
			return obj.getClass().getName().startsWith(OBJECT_NAME_PREFIX_PLUTO);
		}
		else {
			return false;
		}
	}
}
