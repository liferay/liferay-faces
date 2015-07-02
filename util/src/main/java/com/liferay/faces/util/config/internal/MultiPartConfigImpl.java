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
package com.liferay.faces.util.config.internal;

import com.liferay.faces.util.config.MultiPartConfig;


/**
 * @author  Neil Griffin
 */
public class MultiPartConfigImpl implements MultiPartConfig {

	// Private Data Members
	private String location;
	private long maxFileSize;

	public MultiPartConfigImpl(String location, long maxFileSize) {
		this.location = location;
		this.maxFileSize = maxFileSize;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public long getMaxFileSize() {
		return maxFileSize;
	}
}
