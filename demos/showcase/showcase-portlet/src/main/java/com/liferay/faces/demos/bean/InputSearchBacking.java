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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@RequestScoped
public class InputSearchBacking {

	private static final Logger logger = LoggerFactory.getLogger(InputSearchBacking.class);

	private String searchText;

	public void searchActionListener(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("searchActionListener: phaseId=[{0}]", phaseId.toString());

		String phaseName = phaseId.toString();
		FacesMessage facesMessage = new FacesMessage("The searchActionListener method was called during the " +
				phaseName + " phase of the JSF lifecycle.");
		facesContext.addMessage(null, facesMessage);
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
