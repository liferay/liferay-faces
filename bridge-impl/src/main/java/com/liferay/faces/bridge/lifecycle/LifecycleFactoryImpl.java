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
package com.liferay.faces.bridge.lifecycle;

import java.util.Iterator;

import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class LifecycleFactoryImpl extends LifecycleFactory {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LifecycleFactoryImpl.class);

	// Private Constants
	private static final String MOJARRA_LIFECYLE_FQCN = "com.sun.faces.lifecycle.LifecycleImpl";
	private static final String MYFACES_LIFECYCLE_FQCN = "org.apache.myfaces.lifecycle.LifecycleImpl";

	// Private Data Members
	private LifecycleFactory wrappedLifecycleFactory;
	private Lifecycle defaultLifecycle;

	public LifecycleFactoryImpl(LifecycleFactory lifecycleFactory) {
		this.wrappedLifecycleFactory = lifecycleFactory;

		Class<?> wrappedLifecycleClass = null;

		try {
			wrappedLifecycleClass = Class.forName(MOJARRA_LIFECYLE_FQCN);
		}
		catch (ClassNotFoundException e1) {

			try {
				wrappedLifecycleClass = Class.forName(MYFACES_LIFECYCLE_FQCN);
			}
			catch (ClassNotFoundException e2) {
				logger.error(e2);
			}
		}

		if (wrappedLifecycleClass != null) {

			try {
				defaultLifecycle = new LifecycleImpl((Lifecycle) wrappedLifecycleClass.newInstance());
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	@Override
	public void addLifecycle(String lifecycleId, Lifecycle lifecycle) {
		wrappedLifecycleFactory.addLifecycle(lifecycleId, lifecycle);
	}

	@Override
	public Lifecycle getLifecycle(String lifecycleId) {

		if (LifecycleFactory.DEFAULT_LIFECYCLE.equals(lifecycleId)) {
			return defaultLifecycle;
		}
		else {
			return wrappedLifecycleFactory.getLifecycle(lifecycleId);
		}
	}

	@Override
	public Iterator<String> getLifecycleIds() {
		return wrappedLifecycleFactory.getLifecycleIds();
	}
}
