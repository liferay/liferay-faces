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
package com.liferay.faces.bridge.tck.lifecycle;

import java.util.Iterator;

import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.portlet.faces.Bridge.PortletPhase;

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public class LifecycleFactoryTCKImpl extends LifecycleFactory {

	// Private Data Members
	private LifecycleFactory wrappedLifecycleFactory;

	public LifecycleFactoryTCKImpl(LifecycleFactory lifecycleFactory) {
		this.wrappedLifecycleFactory = lifecycleFactory;
	}

	@Override
	public void addLifecycle(String lifecycleId, Lifecycle lifecycle) {
		wrappedLifecycleFactory.addLifecycle(lifecycleId, lifecycle);
	}

	@Override
	public Lifecycle getLifecycle(String lifecycleId) {

		Lifecycle lifecycle = wrappedLifecycleFactory.getLifecycle(lifecycleId);

		if (LifecycleFactory.DEFAULT_LIFECYCLE.equals(lifecycleId)) {

			// TCK TestPage003: lifecycleTest
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

			if (bridgeContext != null) {

				// Because of the multiple-lifecycle design of the bridge's LifecycleFactory implementation, the meaning
				// of LifecycleFactory.DEFAULT_LIFECYCLE must be interpreted to mean the default lifecycle for the
				// current {@link Bridge#PortletPhase}.
				PortletPhase portletRequestPhase = bridgeContext.getPortletRequestPhase();
				lifecycle = wrappedLifecycleFactory.getLifecycle(portletRequestPhase.name());
			}
		}

		return lifecycle;
	}

	@Override
	public Iterator<String> getLifecycleIds() {
		return wrappedLifecycleFactory.getLifecycleIds();
	}

}
