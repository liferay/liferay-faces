/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.event.internal;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.container.PortletContainerWrapper;
import com.liferay.faces.bridge.context.BridgeContext;


/**
 * This class serves as a thread-safe {@link PhaseListener} that acts as a proxy to the {@link PortletContainer}
 * implementation that might need to listen to phase events.
 *
 * @author  Neil Griffin
 */
public class PortletContainerProxyPhaseListener implements PhaseListener {

	// serialVersionUID
	private static final long serialVersionUID = 3383713726298508807L;

	public void afterPhase(PhaseEvent phaseEvent) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletContainer portletContainer = bridgeContext.getPortletContainer();
		PhaseListener phaseListener = getPortletContainerPhaseListener(portletContainer);

		if (phaseListener != null) {

			PhaseId phaseId = phaseListener.getPhaseId();

			if ((phaseId != null) && ((phaseId == PhaseId.ANY_PHASE) || (phaseId == phaseEvent.getPhaseId()))) {
				phaseListener.afterPhase(phaseEvent);
			}
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletContainer portletContainer = bridgeContext.getPortletContainer();
		PhaseListener phaseListener = getPortletContainerPhaseListener(portletContainer);

		if (phaseListener != null) {
			PhaseId phaseId = phaseListener.getPhaseId();

			if ((phaseId != null) && ((phaseId == PhaseId.ANY_PHASE) || (phaseId == phaseEvent.getPhaseId()))) {
				phaseListener.beforePhase(phaseEvent);
			}
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	protected PhaseListener getPortletContainerPhaseListener(PortletContainer portletContainer) {

		if (portletContainer instanceof PhaseListener) {
			return (PhaseListener) portletContainer;
		}
		else if (portletContainer instanceof PortletContainerWrapper) {
			PortletContainerWrapper portletContainerWrapper = (PortletContainerWrapper) portletContainer;

			return getPortletContainerPhaseListener(portletContainerWrapper.getWrapped());
		}
		else {
			return null;
		}
	}

}
