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
package com.liferay.faces.bridge.context.map.internal;

import java.util.List;
import java.util.Map;

import javax.portlet.ClientDataRequest;
import javax.portlet.PortletConfig;

import com.liferay.faces.util.context.map.FacesRequestParameterMap;
import com.liferay.faces.util.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
public interface MultiPartFormDataProcessor {

	/**
	 * Processes the specified client data request by populating the specified namespaced parameter map and returning a
	 * map of uploaded files. This method pre-supposes that the user agent submitted an HTML form with
	 * enctype="multipart/form-data".
	 *
	 * @param   clientDataRequest         The client data request that is to be processed.
	 * @param   portletConfig             The portlet configuration.
	 * @param   facesRequestParameterMap  The mutable namespaced paramter map that is to be populated.
	 *
	 * @return  The map of uploaded files.
	 */
	public Map<String, List<UploadedFile>> process(ClientDataRequest clientDataRequest, PortletConfig portletConfig,
		FacesRequestParameterMap facesRequestParameterMap);
}
