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
package com.liferay.faces.bridge.context.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.liferay.faces.bridge.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
public class MultiPartFormDataImpl implements MultiPartFormData, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3882809202740249227L;

	// Private Data Members
	private FacesRequestParameterMap facesRequestParameterMap;
	private Map<String, Collection<UploadedFile>> uploadedFileMap;

	public MultiPartFormDataImpl(FacesRequestParameterMap facesRequestParameterMap,
		Map<String, Collection<UploadedFile>> uploadedFileMap) {
		this.facesRequestParameterMap = facesRequestParameterMap;
		this.uploadedFileMap = uploadedFileMap;
	}

	@Override
	public FacesRequestParameterMap getFacesRequestParameterMap() {
		return facesRequestParameterMap;
	}

	@Override
	public Map<String, Collection<UploadedFile>> getUploadedFileMap() {
		return uploadedFileMap;
	}
}
