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

import java.util.List;

import com.liferay.faces.util.config.internal.FacesConfigDescriptor;


/**
 * @author  Vernon Singleton
 */
public class AbsoluteOrdering {

	protected String name;
	protected String[] names;

	public static void checkForSpecExceptions(List<FacesConfigDescriptor> configs) throws Exception {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String[] getOrder(List<FacesConfigDescriptor> configs) throws Exception {

		checkForSpecExceptions(configs);

		return this.names;
	}

}
