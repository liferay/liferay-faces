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
package com.liferay.faces.demos.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class SelectOneRadioBackingBean {

	private static final Logger logger = LoggerFactory.getLogger(SelectOneRadioBackingBean.class);

	@ManagedProperty(name = "selectOneRadioModelBean", value = "#{selectOneRadioModelBean}")
	private SelectOneRadioModelBean selectOneRadioModelBean;

	public void submit() {
		PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
		logger.info("submit: phaseId=[{0}] favoriteId=[{1}]", phaseId.toString(), selectOneRadioModelBean.getFavoriteId());
	}

	public void setSelectOneRadioModelBean(SelectOneRadioModelBean selectOneRadioModelBean) {
		this.selectOneRadioModelBean = selectOneRadioModelBean;
	}
	
	public void valueChange() {
		PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
		logger.debug("valueChange: phaseId=[{0}]", phaseId.toString());
		selectOneRadioModelBean.setPhase("valueChange called during " + phaseId.toString());
	}
}
