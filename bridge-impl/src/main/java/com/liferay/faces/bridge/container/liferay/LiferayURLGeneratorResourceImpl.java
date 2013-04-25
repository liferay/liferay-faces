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
package com.liferay.faces.bridge.container.liferay;

/**
 * See class-level JavaDoc for {@link LiferayURLGeneratorBaseImpl}.
 *
 * @author  Neil Griffin
 */
public class LiferayURLGeneratorResourceImpl extends LiferayURLGeneratorBaseImpl {

	public LiferayURLGeneratorResourceImpl(String resourceURL, String responseNamespace) {
		super(resourceURL, responseNamespace);
	}

	public String getPortletLifecycleId() {
		return LiferayConstants.LIFECYCLE_RESOURCE_PHASE_ID;
	}

}
