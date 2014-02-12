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
package com.liferay.util.bridges.jsf.icefaces;

import java.text.DecimalFormat;
import java.util.EventObject;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.util.bridges.jsf.common.FacesMessageUtil;


/**
 * This class is a managed bean that is designed specifically to work with ICEfaces 1.8 by utilizing the <code>
 * <ice:inputFile/></code> component.
 *
 * @deprecated  This class has been deprecated as of Liferay Faces 2.2 and will not appear in future versions.
 * @author      Neil Griffin
 */
@Deprecated
public class FileUploadManagedBean implements Renderable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FileUploadManagedBean.class);

	// Private Data Members
	private InputFile inputFile;
	private int percent;
	private RenderManager renderManager;
	private PersistentFacesState state;

	public FileUploadManagedBean() {
		state = PersistentFacesState.getInstance();
	}

	public void actionListener(ActionEvent actionEvent) {
		InputFile inputFile = (InputFile) actionEvent.getSource();

		int status = inputFile.getFileInfo().getStatus();

		try {

			if (status == InputFile.INVALID) {
				addErrorMessage("file-type-is-invalid");

				percent = 100;
			}
			else if (status == InputFile.SAVED) {
				percent = 100;
			}
			else if (status == InputFile.SIZE_LIMIT_EXCEEDED) {
				long maxFileSizeInBytes = inputFile.getSizeMax();

				DecimalFormat decimalFormat = new DecimalFormat();

				decimalFormat.setGroupingUsed(false);
				decimalFormat.setMaximumFractionDigits(2);
				decimalFormat.setMinimumFractionDigits(0);

				String maxFileSizeInMegs = decimalFormat.format((double) maxFileSizeInBytes / 1024 / 1024);

				addErrorMessage("file-size-is-larger-than-x-megabytes", maxFileSizeInMegs);

				percent = 100;
			}
			else if (status == InputFile.UNKNOWN_SIZE) {
				addErrorMessage("file-size-was-not-specified-in-the-request");

				percent = 100;
			}
		}
		catch (Exception e) {
			logger.error(e);

			addErrorMessage(e.getMessage());
		}
	}

	public void progressListener(EventObject eventObject) {
		InputFile inputFile = (InputFile) eventObject.getSource();

		percent = inputFile.getFileInfo().getPercent();

		renderManager.requestRender(this);
	}

	public void renderingException(RenderingException renderingException) {
		logger.error(renderingException.getMessage());
	}

	protected void addErrorMessage(String key) {
		addErrorMessage(key, null);
	}

	protected void addErrorMessage(String key, String argument) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (inputFile == null) {
			FacesMessageUtil.error(facesContext, key, argument);
		}
		else {
			FacesMessageUtil.error(inputFile.getClientId(facesContext), facesContext, key, argument);
		}
	}

	public boolean isComplete() {

		if (percent == 100) {
			return true;
		}
		else {
			return false;
		}
	}

	public InputFile getInputFile() {
		return inputFile;
	}

	public void setInputFile(InputFile inputFile) {
		this.inputFile = inputFile;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public void setRenderManager(RenderManager renderManager) {
		this.renderManager = renderManager;
	}

	public PersistentFacesState getState() {
		return state;
	}

}
