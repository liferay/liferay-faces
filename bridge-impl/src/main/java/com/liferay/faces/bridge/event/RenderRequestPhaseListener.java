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
package com.liferay.faces.bridge.event;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * Section 5.2.6 of the Spec indicates that the bridge must proactively ensure that only the RESTORE_VIEW phase
 * executes, and Section 6.4 indicates that a PhaseListener must be used.
 *
 * @author  Neil Griffin
 */
public class RenderRequestPhaseListener extends RenderRequestPhaseListenerCompat implements PhaseListener {

	// serialVersionUID
	private static final long serialVersionUID = 8470095938465172618L;

	// Protected (Lazy-Initialized) Constants
	protected static Boolean VIEW_PARAMETERS_ENABLED;

	// Private Data Members
	private PhaseId phaseId = PhaseId.RESTORE_VIEW;

	public void afterPhase(PhaseEvent phaseEvent) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		if (VIEW_PARAMETERS_ENABLED == null) {

			synchronized (phaseId) {

				if (VIEW_PARAMETERS_ENABLED == null) {
					VIEW_PARAMETERS_ENABLED = isViewParametersEnabled(bridgeContext);
				}
			}
		}

		// If the JSF 2 "View Parameters" feature is not enabled, then ensure that only the RESTORE_VIEW phase executes.
		if (!VIEW_PARAMETERS_ENABLED && (bridgeContext.getPortletRequestPhase() == Bridge.PortletPhase.RENDER_PHASE)) {
			phaseEvent.getFacesContext().renderResponse();
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {
		// This method is required by the PhaseListener interfaces but is not used.
	}

	public PhaseId getPhaseId() {
		return phaseId;
	}

}
