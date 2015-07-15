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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class SelectOneBackingBean {

	private static final Logger logger = LoggerFactory.getLogger(SelectOneBackingBean.class);

	@ManagedProperty(name = "selectOneModelBean", value = "#{selectOneModelBean}")
	private SelectOneModelBean selectOneModelBean;

	public void submit() {
		PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
		logger.info("submit: phaseId=[{0}] favoriteId=[{1}]", phaseId.toString(), selectOneModelBean.getFavoriteId());
	}

	public void submitAnswer() {

		Date selectedDate = selectOneModelBean.getDate();

		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar calendar = new GregorianCalendar(gmtTimeZone);

		if (selectedDate != null) {
			calendar.setTime(selectedDate);
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage;

		if ((selectedDate != null) && (calendar.get(Calendar.MONTH) == 6) && (calendar.get(Calendar.DATE) == 4) &&
				(calendar.get(Calendar.YEAR) == 1776)) {
			facesMessage = new FacesMessage("Correct!");
			facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
		}
		else {
			facesMessage = new FacesMessage("Incorrect!");
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		}

		facesContext.addMessage(null, facesMessage);
	}

	public void valueChangeListener(ValueChangeEvent valueChangeEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("valueChangeListener: phaseId=[{0}]", phaseId.toString());

		String phaseName = phaseId.getName();
		FacesMessage facesMessage = new FacesMessage("The valueChangeListener method was called during the " +
				phaseName + " phase of the JSF lifecycle.");
		facesContext.addMessage(null, facesMessage);
	}

	public SelectItem[] getSelectItems() {
		SelectItem[] selectItems = new SelectItem[3];

		for (int i = 0; i < 3; i++) {
			SelectItem item = new SelectItem();
			item.setLabel("Item " + (i + 1));
			item.setValue(i + 1);
			selectItems[i] = item;
		}

		return selectItems;
	}

	public void setSelectOneModelBean(SelectOneModelBean selectOneModelBean) {
		this.selectOneModelBean = selectOneModelBean;
	}
}
