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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@RequestScoped
public class RuntimeBacking {

	private static final Logger logger = LoggerFactory.getLogger(RuntimeBacking.class);

	@ManagedProperty(value = "#{runtimeModelBean}")
	private RuntimeModelBean runtimeModelBean;

	public void togglePortletBorder(ActionEvent actionEvent) {

		long plid = LiferayFacesContext.getInstance().getPlid();

		try {
			PortletPreferencesLocalServiceUtil.deletePortletPreferencesByPlid(plid);
		}
		catch (SystemException e) {
			logger.error(e);
		}

		this.runtimeModelBean.setShowBorders(!runtimeModelBean.isShowBorders());

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("togglePortletBorder: phaseId=[{0}]", phaseId.toString());

		FacesMessage facesMessage = new FacesMessage("Show borders:" + this.runtimeModelBean.isShowBorders());
		facesContext.addMessage(null, facesMessage);
	}

	public RuntimeModelBean getRuntimeModelBean() {
		return runtimeModelBean;
	}

	public void setRuntimeModelBean(RuntimeModelBean runtimeModelBean) {
		this.runtimeModelBean = runtimeModelBean;
	}
}
