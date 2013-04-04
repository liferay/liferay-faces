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
package com.liferay.faces.bridge.application;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;


/**
 * @author  Neil Griffin
 */
public class ApplicationFactoryImpl extends ApplicationFactory {

	private ApplicationFactory wrappedFactory;

	public ApplicationFactoryImpl(ApplicationFactory applicationFactory) {
		wrappedFactory = applicationFactory;
	}

	@Override
	public Application getApplication() {
		return new ApplicationImpl(wrappedFactory.getApplication());
	}

	@Override
	public void setApplication(Application application) {
		wrappedFactory.setApplication(application);
	}

	@Override
	public ApplicationFactory getWrapped() {
		return wrappedFactory;
	}
}
