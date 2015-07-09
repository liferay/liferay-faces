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

import java.util.Arrays;
import java.util.EnumMap;


/**
 * @author  Vernon Singleton
 */
public class OrderingImpl implements Ordering {

	// Private Data Members
	private EnumMap<Path, String[]> routes;

	public OrderingImpl() {
		this.routes = new EnumMap<Path, String[]>(Path.class);
		this.routes.put(Path.BEFORE, new String[0]);
		this.routes.put(Path.AFTER, new String[0]);
	}

	public boolean isOrdered() {
		return ((routes.get(Path.BEFORE).length != 0) || (routes.get(Path.AFTER).length != 0));
	}

	public boolean isBefore(String name) {

		return (Arrays.binarySearch(routes.get(Path.BEFORE), name) >= 0);
	}

	public boolean isAfter(String name) {

		return (Arrays.binarySearch(routes.get(Path.AFTER), name) >= 0);
	}

	public EnumMap<Path, String[]> getRoutes() {
		return routes;
	}

	public void setRoutes(EnumMap<Path, String[]> routes) {
		this.routes = routes;
	}

	public boolean isAfterOthers() {

		boolean value = false;

		if (routes.get(Path.AFTER) != null) {
			value = (Arrays.binarySearch(routes.get(Path.AFTER), OTHERS) >= 0);
		}

		return value;
	}

	public boolean isBeforeOthers() {

		boolean value = false;

		if (routes.get(Path.BEFORE) != null) {
			value = (Arrays.binarySearch(routes.get(Path.BEFORE), OTHERS) >= 0);
		}

		return value;
	}
}
