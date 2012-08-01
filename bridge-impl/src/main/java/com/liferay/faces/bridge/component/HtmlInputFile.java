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
package com.liferay.faces.bridge.component;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import com.liferay.faces.bridge.event.FileUploadEvent;
import com.liferay.faces.bridge.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
public class HtmlInputFile extends HtmlInputFileCompat {

	// Private Data Members
	private UploadedFile uploadedFile;

	public HtmlInputFile() {
		super();
		setRendererType("javax.faces.InputFile");
	}

	@Override
	public void broadcast(FacesEvent facesEvent) throws AbortProcessingException {
		super.broadcast(facesEvent);

		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			MethodExpression methodExpression = getFileUploadListener();

			if ((methodExpression != null) && (facesEvent instanceof FileUploadEvent)) {
				methodExpression.invoke(facesContext.getELContext(), new Object[] { facesEvent });
			}
		}
		catch (Exception e) {
			throw new AbortProcessingException(e);
		}
	}

	/**
	 * @deprecated  Instead of calling this method which only returns the first uploaded file, create a value-expression
	 *              to a model bean like &lt;bridge:inputFile value="#{modelBean.uploadedFiles} /&gt; or use the
	 *              event-based approach: &lt;bridge:inputFile fileUploadListener="#{backingBean.handleFileUpload} /&gt;
	 *
	 * @return      The first uploaded file.
	 */
	@Deprecated
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

}
