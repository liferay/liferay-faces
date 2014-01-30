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
package com.liferay.faces.bridge.tck.context;

import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;


/**
 * This class is designed to be a {@link FacesContextWrapper} around the Mojarra/MyFaces {@link FacesContext}
 * implementation. Its purpose is to fulfill the requirements of TestPage082 (facesContextFactoryServiceProviderTest).
 * For JSF 2.x, it is possible to have a portlet bridge that does not wrap the FacesContext, so this test should not be
 * necessary for JSF 2.x versions of the TCK.
 *
 * @author  Neil Griffin
 */
public class FacesContextTCKImpl extends FacesContextWrapper {

	// Private Data Members
	private FacesContext wrappedFacesContext;

	public FacesContextTCKImpl(FacesContext facesContext) {
		this.wrappedFacesContext = facesContext;
	}

	@Override
	public FacesContext getWrapped() {
		return wrappedFacesContext;
	}
}
