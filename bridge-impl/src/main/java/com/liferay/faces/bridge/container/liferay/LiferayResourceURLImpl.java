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
 * @author  Neil Griffin
 */
public class LiferayResourceURLImpl extends LiferayPortletURLImpl implements LiferayResourceURL {

	// Private Data Members
	private String cacheLevel;

	public LiferayResourceURLImpl(LiferayURLGenerator liferayURLGenerator) {
		super(liferayURLGenerator);
	}

	public String getCacheability() {
		return cacheLevel;
	}

	public void setCacheability(String cacheLevel) {
		this.cacheLevel = cacheLevel;
	}

	public void setResourceID(String resourceID) {
		// no-op
	}

}
