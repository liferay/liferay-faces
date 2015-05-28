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

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import com.liferay.faces.demos.dto.Applicant;
import com.liferay.faces.demos.util.ViewParamUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class InputRichTextBacking {

	private static final Logger logger = LoggerFactory.getLogger(InputRichTextBacking.class);

	private Applicant applicant;
	private boolean resizable = true;

	@PostConstruct
	public void init() {

		applicant = new Applicant();

		if (ViewParamUtil.getUsage().equals("default-value")) {
			applicant.setComments(
				"<p>This is some <strong>bold</strong> text\nand this is some <em>italic</em> text.</p>");
		}
	}

	public void submit() {
		logger.info("You entered comments: " + applicant.getComments());
	}

	public void valueChangeListener(ValueChangeEvent valueChangeEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("valueChangeListener: phaseId=[{0}]", phaseId.toString());

		String phaseName = phaseId.toString();
		FacesMessage facesMessage = new FacesMessage("The valueChangeListener method was called during the " +
				phaseName + " phase of the JSF lifecycle.");
		facesContext.addMessage(null, facesMessage);
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}
}
