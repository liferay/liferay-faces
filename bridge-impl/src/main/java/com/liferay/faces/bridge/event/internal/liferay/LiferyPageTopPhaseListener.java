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
package com.liferay.faces.bridge.event.internal.liferay;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class LiferyPageTopPhaseListener implements PhaseListener {

	// serialVersionUID
	private static final long serialVersionUID = 802045175543260096L;

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	// Private Data Members
	private PhaseListener delegatePhaseListener;

	public LiferyPageTopPhaseListener() {

		if (LIFERAY_PORTAL_DETECTED) {
			delegatePhaseListener = new LiferyPageTopPhaseListenerCompat();
		}
	}

	@Override
	public void afterPhase(PhaseEvent phaseEvent) {

		if (delegatePhaseListener != null) {
			delegatePhaseListener.afterPhase(phaseEvent);
		}
	}

	@Override
	public void beforePhase(PhaseEvent phaseEvent) {

		if (delegatePhaseListener != null) {
			delegatePhaseListener.beforePhase(phaseEvent);
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}
