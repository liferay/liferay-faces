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
package com.liferay.faces.util.context.internal;

import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextFactory;


/**
 * @author  Neil Griffin
 */
public class PartialViewContextFactoryOnChangeImpl extends PartialViewContextFactory {

	// Private Data Members
	private PartialViewContextFactory wrappedPartialViewContextFactory;

	public PartialViewContextFactoryOnChangeImpl(PartialViewContextFactory partialViewContextFactory) {
		this.wrappedPartialViewContextFactory = partialViewContextFactory;
	}

	@Override
	public PartialViewContext getPartialViewContext(FacesContext facesContext) {

		PartialViewContext partialViewContext = wrappedPartialViewContextFactory.getPartialViewContext(facesContext);
		partialViewContext = new PartialViewContextOnChangeImpl(partialViewContext, facesContext);

		return partialViewContext;
	}

	@Override
	public PartialViewContextFactory getWrapped() {
		return wrappedPartialViewContextFactory;
	}

}
