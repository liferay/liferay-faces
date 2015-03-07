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
package com.liferay.faces.bridge.renderkit.primefaces.internal;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;


/**
 * @author  Neil Griffin
 */
public class FacesContextPrimeFacesHeadImpl extends FacesContextWrapper {

	// Private Data Members
	private FacesContext wrappedFacesContext;
	private UIViewRoot viewRoot;

	public FacesContextPrimeFacesHeadImpl(FacesContext facesContext) {
		this.wrappedFacesContext = facesContext;
	}

	@Override
	public UIViewRoot getViewRoot() {

		if (viewRoot == null) {
			return super.getViewRoot();
		}
		else {
			return viewRoot;
		}
	}

	@Override
	public void setViewRoot(UIViewRoot viewRoot) {

		// Override the setViewRoot method in order to prevent the JSF implementation from clearing all the view
		// scoped managed beans.
		this.viewRoot = viewRoot;
	}

	@Override
	public FacesContext getWrapped() {
		return wrappedFacesContext;
	}
}
