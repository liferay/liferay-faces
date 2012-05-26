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
package com.liferay.faces.bridge.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import com.liferay.faces.bridge.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
public class FileUploadEvent extends FacesEvent {

	// serialVersionUID
	private static final long serialVersionUID = 1950657796301668919L;

	// Private Data Members
	private UploadedFile uploadedFile;

	public FileUploadEvent(UIComponent uiComponent, UploadedFile uploadedFile) {
		super(uiComponent);
		this.uploadedFile = uploadedFile;
	}

	@Override
	public void processListener(FacesListener facesListener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAppropriateListener(FacesListener facesListener) {
		return false;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

}
