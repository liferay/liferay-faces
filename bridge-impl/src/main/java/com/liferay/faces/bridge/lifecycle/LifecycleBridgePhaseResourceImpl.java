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

import javax.faces.lifecycle.Lifecycle;

import com.liferay.faces.bridge.event.ManagedBeanScopePhaseListener;


/**
 * @author  Neil Griffin
 */
public class LifecycleBridgePhaseResourceImpl extends LifecycleBridgePhaseBaseImpl {

	public LifecycleBridgePhaseResourceImpl(Lifecycle lifecycle) {
		super(lifecycle);

		// Also need to add the ManagedBeanScopePhaseListener so that after the RENDER_RESPONSE phase, the managed-beans
		// in request scope will go out-of-scope which will in turn cause any annotated PreDestroy methods to be called.
		// Note that there is no concept of "bridgeRequestScope" in a ResourceRequest, so we refer to it as plain-old
		// "request" scope.
		addPhaseListener(new ManagedBeanScopePhaseListener());
	}
}
