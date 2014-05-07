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
package com.liferay.faces.alloy.component.inputdate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;

import com.liferay.faces.alloy.component.datepicker.DatePickerUtil;
import com.liferay.faces.util.context.MessageContext;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = InputDate.COMPONENT_TYPE)
public class InputDate extends InputDateBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.inputdate.InputDate";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.inputdate.InputDateRenderer";

	// Private Constants
	private static final String CALENDAR = "calendar";

	public InputDate() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	protected void validateValue(FacesContext facesContext, Object newValue) {

		super.validateValue(facesContext, newValue);

		if (isValid() && (newValue != null)) {

			try {

				// Get all necessary dates.
				String datePattern = getDatePattern();
				Object minimumDate = getMinimumDate();
				Date minDate = DatePickerUtil.getObjectAsDate(minimumDate, datePattern);
				Object maximumDate = getMaximumDate();
				Date maxDate = DatePickerUtil.getObjectAsDate(maximumDate, datePattern);
				Date submittedDate = DatePickerUtil.getObjectAsDate(newValue, datePattern);

				if ((minDate == null) && (maxDate == null)) {
					setValid(true);
				}
				else {

					if (minDate == null) {
						minDate = new Date(Long.MIN_VALUE);
					}
					else if (maxDate == null) {
						maxDate = new Date(Long.MAX_VALUE);
					}

					// Set the times to midnight for comparison purposes.
					minDate = DatePickerUtil.getDateAtMidnight(minDate);
					maxDate = DatePickerUtil.getDateAtMidnight(maxDate);
					submittedDate = DatePickerUtil.getDateAtMidnight(submittedDate);

					// To determine if the submitted value is valid, check if it falls between the minimum date and
					// the maximum date.
					if (submittedDate.before(minDate) || submittedDate.after(maxDate)) {

						setValid(false);

						String validatorMessage = getValidatorMessage();
						FacesMessage facesMessage;

						if (validatorMessage != null) {
							facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessage,
									validatorMessage);
						}
						else {
							MessageContext messageContext = MessageContext.getInstance();
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
							String minDateString = simpleDateFormat.format(minDate);
							String maxDateString = simpleDateFormat.format(maxDate);
							Object objectLocale = getLocale();
							Locale locale = DatePickerUtil.determineLocale(facesContext, objectLocale);
							String message = messageContext.getMessage(locale, "please-enter-a-value-between-x-and-x",
									minDateString, maxDateString);
							facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
						}

						String clientId = getClientId(facesContext);
						facesContext.addMessage(clientId, facesMessage);
					}
					else {
						setValid(true);
					}
				}
			}
			catch (FacesException e) {

				setValid(false);

				String message = e.getMessage();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
				String clientId = getClientId(facesContext);
				facesContext.addMessage(clientId, facesMessage);
			}
		}
	}

	@Override
	public String getButtonIconName() {
		return (String) getStateHelper().eval(BUTTON_ICON, CALENDAR);
	}

	@Override
	public Converter getConverter() {
		Converter converter = super.getConverter();

		if (converter == null) {

			// Even if converter is null, the component may have a converter child.
			List<UIComponent> children = getChildren();
			boolean found = false;

			for (UIComponent child : children) {

				if (child instanceof Converter) {
					found = true;

					break;
				}
			}

			if (!found) {

				// Provide a default converter of DateTimeConverter if no converter is specified.
				DateTimeConverter dateTimeConverter = new DateTimeConverter();
				String datePattern = getDatePattern();
				dateTimeConverter.setPattern(datePattern);

				// DateTimeConverter has a default time zone of GMT. Set the time zone to null to ensure that the time
				// zone is not involved in Date conversion.
				dateTimeConverter.setTimeZone(null);
				converter = dateTimeConverter;
			}
		}

		return converter;
	}

	@Override
	public String getDatePattern() {

		String datePattern = super.getDatePattern();

		if (datePattern == null) {

			// Provide a default datePattern based on the locale.
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Object locale = getLocale();
			datePattern = DatePickerUtil.getDefaultDatePattern(facesContext, locale);
		}

		return datePattern;
	}
}
