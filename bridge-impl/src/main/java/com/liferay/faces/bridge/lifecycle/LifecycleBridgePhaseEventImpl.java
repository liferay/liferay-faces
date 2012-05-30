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


/**
 * @author  Neil Griffin
 */
public class LifecycleBridgePhaseEventImpl extends LifecycleBridgePhaseBaseImpl {

	public LifecycleBridgePhaseEventImpl(Lifecycle lifecycle) {
		super(lifecycle);

		// Section 5.2.5 of the JSR 329 Spec requires that there be a phase listener registered that has the ability to
		// short-circuit the JSF lifecycle. Section 5.3.3 requires that there be a phase listener registered that
		// processes outgoing Public Render Parameters during the ACTION_PHASE and RENDER_PHASE of the portlet
		// lifecycle. Both of these requirements are handled by the IPCPhaseListener.
		addPhaseListener(new IPCPhaseListener());
	}

}
