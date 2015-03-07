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
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class SelectManyBackingBean {

	private static final Logger logger = LoggerFactory.getLogger(SelectManyBackingBean.class);

	@ManagedProperty(name = "selectManyModelBean", value = "#{selectManyModelBean}")
	private SelectManyModelBean selectManyModelBean;

	public void submit() {
		PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
		logger.info("submit: phaseId=[{0}] favoriteId=[{1}]", phaseId.toString(), selectManyModelBean.getFavoriteIds());
	}

	public void submitAnswer() {
		List<Date> selectedDates = selectManyModelBean.getDates();
		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");

		boolean correct = (selectedDates.size() > 0);

		for (Date selectedDate : selectedDates) {
			Calendar calendar = new GregorianCalendar(gmtTimeZone);
			calendar.setTime(selectedDate);

			int selectedYear = calendar.get(Calendar.YEAR);

			if ((selectedYear <= 1700) || (selectedYear > 1800)) {
				correct = false;

				break;
			}
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage;

		if (correct) {
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

	public void setSelectManyModelBean(SelectManyModelBean selectManyModelBean) {
		this.selectManyModelBean = selectManyModelBean;
	}
}
