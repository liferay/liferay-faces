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

import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.config.WebConfig;


/**
 * @author  Neil Griffin
 */
public class ApplicationConfigImpl implements ApplicationConfig {

	// Private Data Members
	private String contextPath;
	private FacesConfig facesConfig;
	private WebConfig webConfig;

	public ApplicationConfigImpl(String contextPath, FacesConfig facesConfig, WebConfig webConfig) {
		this.contextPath = contextPath;
		this.facesConfig = facesConfig;
		this.webConfig = webConfig;
	}

	public String getContextPath() {
		return contextPath;
	}

	public FacesConfig getFacesConfig() {
		return facesConfig;
	}

	public WebConfig getWebConfig() {
		return webConfig;
	}
}
