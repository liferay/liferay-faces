/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


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
				addLifecycleInternal(Bridge.PortletPhase.ACTION_PHASE.name(),
					new LifecycleBridgePhaseActionImpl((Lifecycle) wrappedLifecycleClass.newInstance()));
				addLifecycleInternal(Bridge.PortletPhase.EVENT_PHASE.name(),
					new LifecycleBridgePhaseEventImpl((Lifecycle) wrappedLifecycleClass.newInstance()));
				addLifecycleInternal(Bridge.PortletPhase.RENDER_PHASE.name(),
					new LifecycleBridgePhaseRenderImpl((Lifecycle) wrappedLifecycleClass.newInstance()));
				addLifecycleInternal(Bridge.PortletPhase.RESOURCE_PHASE.name(),
					new LifecycleBridgePhaseResourceImpl((Lifecycle) wrappedLifecycleClass.newInstance()));
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

	protected void addLifecycleInternal(String lifecycleId, Lifecycle lifecycle) {

		try {
			wrappedLifecycleFactory.addLifecycle(lifecycleId, lifecycle);
		}
		catch (IllegalArgumentException e) {
			// ignore -- sometimes this happens on redeploy of portlet WARs
		}
	}

	@Override
	public Lifecycle getLifecycle(String lifecycleId) {
		return wrappedLifecycleFactory.getLifecycle(lifecycleId);
	}

	@Override
	public Iterator<String> getLifecycleIds() {
		return wrappedLifecycleFactory.getLifecycleIds();
	}
}
