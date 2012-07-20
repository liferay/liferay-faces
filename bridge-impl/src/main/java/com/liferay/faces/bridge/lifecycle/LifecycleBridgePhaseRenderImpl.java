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
import com.liferay.faces.bridge.event.RenderRequestPhaseListener;


/**
 * @author  Neil Griffin
 */
public class LifecycleBridgePhaseRenderImpl extends LifecycleBridgePhaseBaseImpl {

	public LifecycleBridgePhaseRenderImpl(Lifecycle lifecycle) {
		super(lifecycle);

		// Section 5.2.6 also indicates that the bridge must proactively ensure that only the
		// RESTORE_VIEW phase executes, and Section 6.4 indicates that a PhaseListener must be used. The
		// RenderRequestPhaseListener satisfies this requirement.
		addPhaseListener(new RenderRequestPhaseListener());

		// Add the ManagedBeanScopePhaseListener so that after the RENDER_RESPONSE phase, the
		// managed-beans in bridgeRequestScope will go out-of-scope which will in turn cause any annotated PreDestroy
		// methods to be called.
		addPhaseListener(new ManagedBeanScopePhaseListener());
	}
}
