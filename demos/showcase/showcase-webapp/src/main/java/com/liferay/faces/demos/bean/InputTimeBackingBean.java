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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.liferay.faces.alloy.component.inputtime.InputTime;
import com.liferay.faces.alloy.component.inputtime.TimeSelectEvent;


/**
 * @author  Kyle Stiemann
 */
@ManagedBean
@RequestScoped
public class InputTimeBackingBean {

	public void timeSelectListener(TimeSelectEvent timeSelectEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Date selectedTime = timeSelectEvent.getTime();
		InputTime inputTime = (InputTime) timeSelectEvent.getComponent();
		String timePattern = inputTime.getPattern();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattern);
		String timeZoneString = inputTime.getTimeZone();
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
		simpleDateFormat.setTimeZone(timeZone);

		String selectedTimeFormatted = simpleDateFormat.format(selectedTime);
		FacesMessage facesMessage = new FacesMessage("Received 'timeSelectEvent' for date with value '" +
				selectedTimeFormatted + "' in the " + timeSelectEvent.getPhaseId().getName() + " phase.");
		facesContext.addMessage(null, facesMessage);
	}
}
