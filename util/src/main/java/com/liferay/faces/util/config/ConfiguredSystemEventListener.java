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
package com.liferay.faces.util.config;

/**
 * This interface defines the properties for a configured system-event-listener that is discovered in
 * META-INF/faces-config.xml or WEB-INF/faces-config.xml descriptors.
 *
 * @author  Neil Griffin
 */
public interface ConfiguredSystemEventListener {

	/**
	 * Returns the value of the source-class element of the configured system-event-listener.
	 */
	public String getSourceClass();

	/**
	 * Returns the value of the system-event-class element of the configured system-event-listener.
	 */
	public String getSystemEventClass();

	/**
	 * Returns the value of the system-event-listener-class element of the configured system-event-listener.
	 */
	public String getSystemEventListenerClass();
}
