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

import javax.faces.FactoryFinder;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.portlet.faces.Bridge;


/**
 * This class helps workaround bugs in 3rd party component suites that assume there is only one lifecycleId. For more
 * information, see: http://issues.liferay.com/browse/FACES-1259
 *
 * @author  Neil Griffin
 */
public class LifecycleListenerImpl extends LifecycleWrapper {

	// Private Data Members
	private Lifecycle wrappedLifecycle;

	public LifecycleListenerImpl(Lifecycle lifecycle) {
		this.wrappedLifecycle = lifecycle;
	}

	@Override
	public void addPhaseListener(PhaseListener phaseListener) {

		// Add the specified PhaseListener to the wrapped Lifecycle
		super.addPhaseListener(phaseListener);

		// And also add the specified PhaseListener to each of the PortletPhase lifecycles:
		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(
				FactoryFinder.LIFECYCLE_FACTORY);

		for (Bridge.PortletPhase portletPhase : Bridge.PortletPhase.values()) {
			String lifecycleId = portletPhase.name();
			Lifecycle portletPhaseLifecycle = lifecycleFactory.getLifecycle(lifecycleId);

			try {
				portletPhaseLifecycle.addPhaseListener(phaseListener);
			}
			catch (IllegalArgumentException e) {
				// ignore -- can happen if it's already been added
			}
		}
	}

	public Lifecycle getWrapped() {
		return wrappedLifecycle;
	}

}
