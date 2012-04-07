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
package com.liferay.faces.bridge.container;

import javax.portlet.BaseURL;
import javax.portlet.ResourceURL;


/**
 * @author  Neil Griffin
 */
public class ResourceURLWrapper extends BaseURLWrapper implements ResourceURL {

	// Protected Data Members
	protected ResourceURL wrappedResourceURL;

	/**
	 * Constructor for building a ResourceURLWrapper instance.
	 *
	 * @param  resourceURL  The resource URL that is to be wrapped by this wrapper.
	 */
	public ResourceURLWrapper(ResourceURL resourceURL) {
		this.wrappedResourceURL = resourceURL;
	}

	public String getCacheability() {
		ResourceURL wrappedResourceURL = (ResourceURL) getWrapped();

		return wrappedResourceURL.getCacheability();
	}

	public void setCacheability(String cacheLevel) {
		ResourceURL wrappedResourceURL = (ResourceURL) getWrapped();
		wrappedResourceURL.setCacheability(cacheLevel);
	}

	public void setResourceID(String resourceID) {
		ResourceURL wrappedResourceURL = (ResourceURL) getWrapped();
		wrappedResourceURL.setResourceID(resourceID);
	}

	public BaseURL getWrapped() {
		return wrappedResourceURL;
	}

}
