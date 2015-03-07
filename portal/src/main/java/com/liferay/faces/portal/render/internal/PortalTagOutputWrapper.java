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
package com.liferay.faces.portal.render.internal;

import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class PortalTagOutputWrapper implements PortalTagOutput, FacesWrapper<PortalTagOutput> {

	@Override
	public String getMarkup() {
		return getWrapped().getMarkup();
	}

	@Override
	public String getScripts() {
		return getWrapped().getScripts();
	}

	public abstract PortalTagOutput getWrapped();
}
