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
package com.liferay.faces.bridge.container.liferay;

import javax.portlet.ResourceURL;

/**
 * @author  Neil Griffin
 */
public class LiferayResourceURL extends LiferayBaseURL implements ResourceURL {


	private final ResourceURL resourceURL;

	public LiferayResourceURL(ResourceURL liferayURL, String responseNamespace, int liferayBuildNumber) {
		super(liferayURL, responseNamespace);
		resourceURL = liferayURL;
	}

	public String getCacheability() {
		return resourceURL.getCacheability();
	}

	public void setCacheability(String cacheLevel) {
		resourceURL.setCacheability(cacheLevel);
		resetToString();
	}

	@Override
	public String getPortletLifecycleId() {
		return LiferayConstants.LIFECYCLE_RESOURCE_PHASE_ID;
	}

	public void setResourceID(String resourceId) {
		resourceURL.setResourceID(resourceId);
		resetToString();
	}

}
