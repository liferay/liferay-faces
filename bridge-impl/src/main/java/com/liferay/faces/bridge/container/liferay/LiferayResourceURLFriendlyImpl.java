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
package com.liferay.faces.bridge.container.liferay;

import javax.portlet.ResourceURL;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public class LiferayResourceURLFriendlyImpl extends LiferayBaseURLFriendlyImpl implements LiferayResourceURL,
	Wrapper<ResourceURL> {

	// Private Data Members
	private String responseNamespace;
	private ResourceURL wrappedLiferayResourceURL;

	public LiferayResourceURLFriendlyImpl(ResourceURL liferayResourceURL, String responseNamespace) {

		this.wrappedLiferayResourceURL = liferayResourceURL;
		this.responseNamespace = responseNamespace;
	}

	public String getCacheability() {
		return getWrapped().getCacheability();
	}

	public void setCacheability(String cacheLevel) {

		getWrapped().setCacheability(cacheLevel);
		resetToString();
	}

	@Override
	protected LiferayURLGenerator getLiferayURLGenerator() {

		String resourceURL = getWrapped().toString();

		return new LiferayURLGeneratorResourceImpl(resourceURL, responseNamespace);
	}

	public void setResourceID(String resourceID) {

		getWrapped().setResourceID(resourceID);
		resetToString();
	}

	@Override
	public ResourceURL getWrapped() {
		return wrappedLiferayResourceURL;
	}

}
