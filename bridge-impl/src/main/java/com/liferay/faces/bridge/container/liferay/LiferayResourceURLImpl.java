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
public class LiferayResourceURLImpl extends LiferayBaseURLImpl implements LiferayResourceURL {

	// Private Data Members
	private String cacheLevel;
	private String resourceId;
	private String toStringValue;

	public LiferayResourceURLImpl(LiferayURLGenerator liferayURLGenerator) {
		super(liferayURLGenerator);
	}

	@Override
	public String toString() {

		if (toStringValue == null) {
			toStringValue = getLiferayURLGenerator().generateURL(getParameterMap(), resourceId);
		}

		return toStringValue;
	}

	@Override
	protected void resetToString() {
		this.toStringValue = null;
	}

	public String getCacheability() {
		return cacheLevel;
	}

	public void setCacheability(String cacheLevel) {
		this.cacheLevel = cacheLevel;
	}

	public void setResourceID(String resourceID) {
		this.resourceId = resourceID;
		resetToString();
	}

}
