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
package com.liferay.faces.util.application.internal;

import java.util.EventObject;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

import com.liferay.faces.util.event.internal.ApplicationStartupListener;
import com.liferay.faces.util.helper.Wrapper;


/**
 * This class is registered in META-INF/faces-config.xml as an application-factory. When it is initialized during
 * startup, it sends an event to the {@link ApplicationStartupListener} in order to trigger classpath scanning of
 * application configuration files.
 *
 * @author  Neil Griffin
 */
public class ApplicationFactoryImpl extends ApplicationFactory implements Wrapper<ApplicationFactory> {

	// Private Data Members
	private ApplicationFactory wrappedFactory;

	public ApplicationFactoryImpl(ApplicationFactory applicationFactory) {

		wrappedFactory = applicationFactory;

		ApplicationStartupListener applicationStartupListener = new ApplicationStartupListener();
		EventObject systemEvent = new PostConstructApplicationFactoryEvent(getApplication());
		applicationStartupListener.processSystemEvent(systemEvent);
	}

	@Override
	public Application getApplication() {
		return wrappedFactory.getApplication();
	}

	@Override
	public void setApplication(Application application) {
		wrappedFactory.setApplication(application);
	}

	@Override
	public ApplicationFactory getWrapped() {
		return wrappedFactory;
	}

	protected class PostConstructApplicationFactoryEvent extends EventObject {

		// serialVersionUID
		private static final long serialVersionUID = 8843150652809856957L;

		public PostConstructApplicationFactoryEvent(Application application) {
			super(application);
		}
	}
}
