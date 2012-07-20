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

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class LifecycleWrapper extends Lifecycle implements Wrapper<Lifecycle> {

	@Override
	public void addPhaseListener(PhaseListener listener) {
		getWrapped().addPhaseListener(listener);
	}

	@Override
	public void execute(FacesContext context) throws FacesException {
		getWrapped().execute(context);
	}

	@Override
	public void removePhaseListener(PhaseListener listener) {
		getWrapped().removePhaseListener(listener);
	}

	@Override
	public void render(FacesContext context) throws FacesException {
		getWrapped().render(context);
	}

	@Override
	public PhaseListener[] getPhaseListeners() {
		return getWrapped().getPhaseListeners();
	}

}
