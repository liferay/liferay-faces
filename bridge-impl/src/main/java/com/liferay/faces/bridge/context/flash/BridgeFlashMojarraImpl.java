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
package com.liferay.faces.bridge.context.flash;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;


/**
 * The purpose of this class is to workaround http://java.net/jira/browse/JAVASERVERFACES-1987
 *
 * @author  Neil Griffin
 */
public class BridgeFlashMojarraImpl extends BridgeFlashWrapper {

	private boolean mojarraServletDependencyActive;
	private Flash wrappedFlash;

	public BridgeFlashMojarraImpl(Flash flash) {
		this.wrappedFlash = flash;
	}

	@Override
	public void doPostPhaseActions(FacesContext facesContext) {

		mojarraServletDependencyActive = true;
		getWrapped().doPostPhaseActions(facesContext);
		mojarraServletDependencyActive = false;
	}

	@Override
	public Object put(String key, Object value) {

		mojarraServletDependencyActive = true;

		Object putValue = getWrapped().put(key, value);
		mojarraServletDependencyActive = false;

		return putValue;
	}

	@Override
	public boolean isServletResponseRequired() {
		return mojarraServletDependencyActive;
	}

	@Override
	public Flash getWrapped() {
		return wrappedFlash;
	}

}
