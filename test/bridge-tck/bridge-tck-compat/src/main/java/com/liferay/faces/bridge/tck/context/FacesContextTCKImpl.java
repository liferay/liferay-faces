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
package com.liferay.faces.bridge.tck.context;

import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.tck.el.ELContextTCKImpl;
import com.liferay.faces.util.context.FacesContextWrapper;


/**
 * This class is designed to be a {@link FacesContextWrapper} around the Mojarra/MyFaces {@link FacesContext}
 * implementation. Its purpose is to handle special cases of the TCK.
 *
 * @author  Neil Griffin
 */
public class FacesContextTCKImpl extends FacesContextWrapper {

	// Private Data Members
	private ELContext elContext;
	private ExternalContext externalContext;
	private FacesContext wrappedFacesContext;

	public FacesContextTCKImpl(FacesContext facesContext) {
		this.wrappedFacesContext = facesContext;
	}

	/**
	 * This method wraps the Mojarra/MyFaces {@link ELContext} with an implementation that handles a special case of the
	 * TCK.
	 */
	@Override
	public ELContext getELContext() {

		if (elContext == null) {
			elContext = super.getELContext();

			if (!(elContext instanceof ELContextTCKImpl)) {
				elContext = new ELContextTCKImpl(elContext);
			}
		}

		return elContext;
	}

	@Override
	public ExternalContext getExternalContext() {

		if (externalContext == null) {
			externalContext = new ExternalContextTCKImpl(wrappedFacesContext.getExternalContext());
		}

		return externalContext;
	}

	@Override
	public FacesContext getWrapped() {
		return wrappedFacesContext;
	}
}
