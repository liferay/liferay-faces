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
package com.liferay.faces.util.event;

import javax.faces.event.SystemEvent;

import com.liferay.faces.util.config.ApplicationConfig;


/**
 * <p>This event is published after all application configuration resources have been scanned, parsed, and processed at
 * startup. This provides the ability for listeners to safely call {@link
 * com.liferay.faces.util.factory.FactoryExtensionFinder#getFactory(Class)} or to get the application configuration by
 * calling {@link #getApplicationConfig()}.</p>
 *
 * @author  Neil Griffin
 */
public class PostConstructApplicationConfigEvent extends SystemEvent {

	// serialVersionUID
	private static final long serialVersionUID = 8134490708842412398L;

	public PostConstructApplicationConfigEvent(ApplicationConfig applicationConfig) {
		super(applicationConfig);
	}

	/**
	 * Returns the source {@link ApplicationConfig} that sent this event.
	 */
	public ApplicationConfig getApplicationConfig() {
		return (ApplicationConfig) getSource();
	}
}
