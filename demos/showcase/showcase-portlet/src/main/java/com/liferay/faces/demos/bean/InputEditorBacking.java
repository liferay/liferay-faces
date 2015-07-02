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

// JSF 2: import javax.faces.bean.ManagedBean;
// JSF 2: import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.liferay.faces.demos.dto.Applicant;
import com.liferay.faces.demos.event.CurrentPhaseListener;
import com.liferay.faces.demos.util.ViewParamUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
// JSF 2: @ManagedBean
// JSF 2: @RequestScoped
public class InputEditorBacking {

	private static final Logger logger = LoggerFactory.getLogger(InputEditorBacking.class);

	private Applicant applicant;
	private String editorImpl;
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
		String phaseId = (String) facesContext.getExternalContext().getRequestMap().get(CurrentPhaseListener.PHASE_ID);
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

	public String getEditorImpl() {

		if (editorImpl == null) {

			Product liferayPortal = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);

			if (liferayPortal.getMajorVersion() == 6) {

				editorImpl = "com.liferay.faces.demos.showcase.ckeditor_bbcode";

				if ((liferayPortal.getMinorVersion() == 0) && (liferayPortal.getRevisionVersion() < 12)) {
					editorImpl = "ckeditor";
				}
			}
			else {
				editorImpl = "unsupported-liferay-version-" + liferayPortal.getVersion();
			}
		}

		return editorImpl;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}
}
