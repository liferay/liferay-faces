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
package com.liferay.faces.bridge.context;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;


/**
 * @author  Neil Griffin
 */
public class FacesContextProducerImpl extends FacesContextWrapper {

	// Private Data Members
	private ExternalContext externalContext;
	private PortletRequest portletRequest;
	private FacesContext wrappedFacesContext;

	public FacesContextProducerImpl(FacesContext facesContext, PortletRequest portletRequest) {
		this.wrappedFacesContext = facesContext;
		this.portletRequest = portletRequest;
		setCurrentInstance(this);
	}

	@Override
	public ExternalContext getExternalContext() {

		if (externalContext == null) {
			externalContext = new ExternalContextProducerImpl(getWrapped().getExternalContext(), portletRequest);
		}

		return externalContext;
	}

	@Override
	public FacesContext getWrapped() {
		return wrappedFacesContext;
	}

}
