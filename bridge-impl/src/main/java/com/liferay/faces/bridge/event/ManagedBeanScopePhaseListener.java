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
package com.liferay.faces.bridge.event;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;


/**
 * This class has a purpose in JSF 2.x but serves no purpose for JSF 1.x. However, it's presence is required for
 * compilation.
 *
 * @author  Neil Griffin
 */
public class ManagedBeanScopePhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1713704308484763548L;

	public void afterPhase(PhaseEvent phaseEvent) {
		// no-op for JSF 1.x
	}

	public void beforePhase(PhaseEvent phaseEvent) {
		// no-op for JSF 1.x
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}
