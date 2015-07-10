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
package com.liferay.faces.demos.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.liferay.faces.alloy.component.inputdate.InputDate;


/**
 * @author  Kyle Stiemann
 */
@FacesValidator("com.liferay.faces.demos.validator.WeekdayValidator")
public class WeekdayValidator implements Validator {

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {

		if (object != null) {

			Date date;
			InputDate inputDate = (InputDate) uiComponent;
			String timeZoneString = inputDate.getTimeZone();
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);

			if (object instanceof Date) {
				date = (Date) object;
			}
			else {

				String dateString = object.toString();
				String datePattern = inputDate.getPattern();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
				simpleDateFormat.setTimeZone(timeZone);

				try {
					date = simpleDateFormat.parse(dateString);
				}
				catch (ParseException e) {

					String message = ValidatorHelper.getMessage(facesContext, inputDate, "invalid-selection");
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
					throw new ValidatorException(facesMessage, e);
				}
			}

			Calendar calendar = new GregorianCalendar(timeZone);
			calendar.setTime(date);

			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

			if ((dayOfWeek == Calendar.SATURDAY) || (dayOfWeek == Calendar.SUNDAY)) {

				String message = ValidatorHelper.getMessage(facesContext, inputDate, "sat-and-sun-are-not-valid");
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
				throw new ValidatorException(facesMessage);
			}
		}
	}
}
