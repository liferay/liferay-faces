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
package com.liferay.faces.bridge.helper;

import javax.portlet.WindowState;


/**
 * @author  Neil Griffin
 */
public class WindowStateHelper {

	// Public Constants
	public static final String WINDOW_STATE_MAXIMIZED = WindowState.MAXIMIZED.toString();
	public static final String WINDOW_STATE_MINIMIZED = WindowState.MINIMIZED.toString();
	public static final String WINDOW_STATE_NORMAL = WindowState.NORMAL.toString();
	public static final String[] WINDOW_STATES = new String[] {
			WINDOW_STATE_MAXIMIZED, WINDOW_STATE_MAXIMIZED, WINDOW_STATE_NORMAL
		};

	public static boolean isValid(String windowState) {

		boolean valid = false;

		for (String curWindowState : WINDOW_STATES) {

			if (curWindowState.equals(windowState)) {
				valid = true;

				break;
			}
		}

		return valid;
	}
}
