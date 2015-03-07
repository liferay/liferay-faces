/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.lifecycle;

import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class DebugPhaseListener implements PhaseListener {

	// serialVersionUID
	private static final long serialVersionUID = 5431973221176870776L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DebugPhaseListener.class);

	public void afterPhase(PhaseEvent phaseEvent) {

		if (logger.isDebugEnabled()) {
			PhaseId phaseId = phaseEvent.getPhaseId();

			String viewId = null;
			UIViewRoot uiViewRoot = phaseEvent.getFacesContext().getViewRoot();

			if (uiViewRoot != null) {
				viewId = uiViewRoot.getViewId();
			}

			logger.debug("AFTER phaseId=[{0}] viewId=[{1}]", phaseId.toString(), viewId);
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {

		if (logger.isDebugEnabled()) {
			PhaseId phaseId = phaseEvent.getPhaseId();

			String viewId = null;
			UIViewRoot uiViewRoot = phaseEvent.getFacesContext().getViewRoot();

			if (uiViewRoot != null) {
				viewId = uiViewRoot.getViewId();
			}

			logger.debug("BEFORE phaseId=[{0}] viewId=[{1}]", phaseId.toString(), viewId);
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
