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
import java.util.List;


/**
 * @author  Vernon Singleton
 */
public class OrderingCircularDependencyException extends Exception {


	public OrderingCircularDependencyException(Ordering.Path path, List<FacesConfigDescriptor> facesConfigs) {
		super(createMessage(path, facesConfigs));
	}

	private static String createMessage(Ordering.Path path, List<FacesConfigDescriptor> facesConfigs) {

		StringBuilder message = new StringBuilder();
		message.append("Circular dependencies detected when traversing '");

		message.append(path.name());
		message.append("' declarations:");

		for (FacesConfigDescriptor facesConfigDescriptor : facesConfigs) {
			Ordering someOrdering = facesConfigDescriptor.getOrdering();
			EnumMap<Ordering.Path,String[]> someRoutes = someOrdering.getRoutes();
			String[] someNames = someRoutes.get(path);

			if (someNames.length != 0) {
				message.append(" ");
				message.append(facesConfigDescriptor.getName());
				message.append(" ");
				message.append(path.name());
				message.append(": ");
				message.append(Arrays.asList(someNames).toString());
				message.append("\n");
			}
		}

		return message.toString();

	}
}
