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
import javax.portlet.faces.Bridge.PortletPhase;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.config.ProductMap;
import com.liferay.faces.bridge.context.BridgeContext;
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
	private static final boolean TCK_JSR_329_DETECTED = ProductMap.getInstance().get(BridgeConstants.TCK_JSR_329)
		.isDetected();

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
		Lifecycle lifecycle = wrappedLifecycleFactory.getLifecycle(lifecycleId);

		if (LifecycleFactory.DEFAULT_LIFECYCLE.equals(lifecycleId)) {

			// TCK TestPage003: lifecycleTest
			if (TCK_JSR_329_DETECTED) {

				// Because of the multiple-lifecycle design of the bridge's LifecycleFactory implementation, the meaning
				// of LifecycleFactory.DEFAULT_LIFECYCLE must be interpreted to mean the default lifecycle for the
				// current {@link Bridge#PortletPhase}.
				BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
				PortletPhase portletRequestPhase = bridgeContext.getPortletRequestPhase();
				lifecycle = wrappedLifecycleFactory.getLifecycle(portletRequestPhase.name());
			}
			else {

				// Workaround bugs in 3rd party component suites that assume there is only one lifecycleId
				// See: http://issues.liferay.com/browse/FACES-1259
				lifecycle = new LifecycleListenerImpl(lifecycle);
			}
		}

		return lifecycle;
	}

	@Override
	public Iterator<String> getLifecycleIds() {
		return wrappedLifecycleFactory.getLifecycleIds();
	}
}
