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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import com.liferay.faces.demos.dto.Country;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@RequestScoped
public class ViewMetadataBackingBean {

	private String viewActionText;
	private Country viewCountry;

	public String viewAction() {
		PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();

		String phaseName = phaseId.toString();

		this.viewActionText = "View action was executed in phase " + phaseName;

		return null;
	}

	public String getViewActionText() {
		return viewActionText;
	}

	public void setViewActionText(String viewActionText) {
		this.viewActionText = viewActionText;
	}

	public Country getViewCountry() {
		return viewCountry;
	}

	public void setViewCountry(Country viewCountry) {
		this.viewCountry = viewCountry;
	}
}
