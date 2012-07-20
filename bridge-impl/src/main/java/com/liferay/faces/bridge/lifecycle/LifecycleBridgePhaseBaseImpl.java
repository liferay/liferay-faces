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

import com.liferay.faces.bridge.event.IPCPhaseListener;
import com.liferay.faces.bridge.event.PortletContainerProxyPhaseListener;


/**
 * @author  Neil Griffin
 */
public class LifecycleBridgePhaseBaseImpl extends LifecycleWrapper {

	// Private Data Members
	private Lifecycle wrappedLifecycle;

	public LifecycleBridgePhaseBaseImpl(Lifecycle lifecycle) {
		this.wrappedLifecycle = lifecycle;

		// Section 5.2.4 of the JSR 329 Spec requires that a phase listener be registered in order to handle Portlet 2.0
		// Public Render Parameters after the RESTORE_VIEW phase of the JSF lifecycle executes. The IPCPhaseListener
		// satisfies this requirement.
		addPhaseListener(new IPCPhaseListener());

		// Add the PortletContainerProxyPhaseListener so that the PortletContainer will be able to listen to the phases.
		addPhaseListener(new PortletContainerProxyPhaseListener());
	}

	public Lifecycle getWrapped() {
		return wrappedLifecycle;
	}
}
