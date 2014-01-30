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
/**
 * Copyright (c) 2011 tritonsvc.com All rights reserved.
 */
package com.liferay.faces.alloy.el;

import java.lang.reflect.Method;


/**
 * @author  Neil Griffin
 */
public class AUI {

	// Private Constants
	private static final String FQCN_RELEASE_INFO = "com.liferay.portal.kernel.util.ReleaseInfo";
	private static final String METHOD_GET_BUILD_NUMBER = "getBuildNumber";

	// Singleton Instance
	private static AUI instance = new AUI();

	// Private Data Members
	private Boolean provided;
	private Integer liferayBuildNumber;

	public AUI() {

		liferayBuildNumber = new Integer(0);

		try {
			Class<?> clazz = Class.forName(FQCN_RELEASE_INFO);
			Method method = clazz.getMethod(METHOD_GET_BUILD_NUMBER, (Class[]) null);
			liferayBuildNumber = (Integer) method.invoke(clazz, (Object[]) null);
		}
		catch (NoClassDefFoundError e) {
			// ignore -- must not be running in Liferay
		}
		catch (ClassNotFoundException e) {
			// ignore -- must not be running in Liferay
		}
		catch (NoSuchMethodException e) {
			// ignore -- must not be running in Liferay
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static AUI getInstance() {

		if (instance == null) {
			instance = new AUI();
		}

		return instance;
	}

	public boolean isProvided() {

		if (provided == null) {
			provided = (liferayBuildNumber >= 6000);
		}

		return provided;
	}

	public int getLiferayBuildNumber() {
		return liferayBuildNumber.intValue();
	}
}
