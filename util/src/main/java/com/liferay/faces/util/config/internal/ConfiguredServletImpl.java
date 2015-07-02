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

import com.liferay.faces.util.config.ConfiguredServlet;
import com.liferay.faces.util.config.MultiPartConfig;


/**
 * @author  Neil Griffin
 */
public class ConfiguredServletImpl implements ConfiguredServlet {

	// Private Data Members
	private MultiPartConfig multiPartConfig;
	private String servletClass;
	private String servletName;

	public ConfiguredServletImpl(String servletName, String servletClass, MultiPartConfig multiPartConfig) {
		this.servletName = servletName;
		this.servletClass = servletClass;
		this.multiPartConfig = multiPartConfig;
	}

	@Override
	public MultiPartConfig getMultiPartConfig() {
		return multiPartConfig;
	}

	public String getServletClass() {
		return servletClass;
	}

	public String getServletName() {
		return servletName;
	}
}
