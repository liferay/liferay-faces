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

import com.liferay.faces.util.config.ConfiguredSystemEventListener;


/**
 * @author  Neil Griffin
 */
public class ConfiguredSystemEventListenerImpl implements ConfiguredSystemEventListener {

	// Private Data Members
	private String sourceClass;
	private String systemEventClass;
	private String systemEventListenerClass;

	public ConfiguredSystemEventListenerImpl(String sourceClass, String systemEventClass,
		String systemEventListenerClass) {
		this.sourceClass = sourceClass;
		this.systemEventClass = systemEventClass;
		this.systemEventListenerClass = systemEventListenerClass;
	}

	public String getSourceClass() {
		return sourceClass;
	}

	public String getSystemEventClass() {
		return systemEventClass;
	}

	public String getSystemEventListenerClass() {
		return systemEventListenerClass;
	}

}
