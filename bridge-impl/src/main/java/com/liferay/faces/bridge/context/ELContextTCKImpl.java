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
package com.liferay.faces.bridge.context;

import javax.el.ELContext;
import javax.el.ELResolver;


/**
 * This class is designed to be an {@link ELContextWrapper} around the Mojarra/MyFaces {@link ELContext} implementation.
 * Its purpose is to handle special cases of the TCK.
 *
 * @author  Neil Griffin
 */
public class ELContextTCKImpl extends ELContextWrapper {

	// Private Data Members
	private ELResolver elResolver;
	private ELContext wrappedELContext;

	public ELContextTCKImpl(ELContext elContext) {
		this.wrappedELContext = elContext;
	}

	/**
	 * This method wraps the Mojarra/MyFaces {@link ELResolver} with an implementation that handles a special case of
	 * the TCK.
	 */
	@Override
	public ELResolver getELResolver() {

		if (elResolver == null) {
			elResolver = new ELResolverTCKImpl(super.getELResolver());
		}

		return elResolver;
	}

	@Override
	public ELContext getWrapped() {
		return wrappedELContext;
	}

}
