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
package com.liferay.faces.bridge.container;

import javax.faces.FacesWrapper;
import javax.portlet.ResourceURL;


/**
 * @author  Neil Griffin
 */
public abstract class ResourceURLWrapper extends BaseURLWrapper implements ResourceURL, FacesWrapper<ResourceURL> {

	public String getCacheability() {
		return getWrapped().getCacheability();
	}

	public void setCacheability(String cacheLevel) {
		getWrapped().setCacheability(cacheLevel);
	}

	public void setResourceID(String resourceID) {
		getWrapped().setResourceID(resourceID);
	}

	@Override
	public abstract ResourceURL getWrapped();

}
