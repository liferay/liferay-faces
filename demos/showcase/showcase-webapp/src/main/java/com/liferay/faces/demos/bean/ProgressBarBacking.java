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
package com.liferay.faces.demos.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;

import com.liferay.faces.alloy.component.progressbar.ProgressCompleteEvent;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ViewScoped
@ManagedBean
public class ProgressBarBacking implements Serializable {

	private static final long serialVersionUID = 1014691234870238235L;

	private static final Logger logger = LoggerFactory.getLogger(ProgressBarBacking.class);

	private Integer progress = 0;

	public void incrementProgress() {
		progress = progress + 10;
	}

	public void progressCompleteListener(AjaxBehaviorEvent ajaxBehaviorEvent) {

		// When using JSF 2.2, this cast is unnecessary, and the method can take the ProgressCompleteEvent directly.
		ProgressCompleteEvent progressCompleteEvent = (ProgressCompleteEvent) ajaxBehaviorEvent;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("progressCompleteListener: phaseId=[{0}]", phaseId.toString());

		String phaseName = phaseId.getName();
		FacesMessage facesMessage = new FacesMessage("The progressCompleteListener method was called during the " +
				phaseName + " phase of the JSF lifecycle.");
		facesContext.addMessage(null, facesMessage);
	}

	public void resetProgress() {
		progress = 0;
	}

	public int getProgress() {
		return progress;
	}

}
