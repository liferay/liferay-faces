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
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;

import com.liferay.faces.alloy.component.inputdatetime.InputDateTimeUtil;
import com.liferay.faces.alloy.component.pickdate.PickDateUtil;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.context.MessageContext;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = InputDate.COMPONENT_TYPE)
public class InputDate extends InputDateBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.inputdate.InputDate";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.inputdate.InputDateRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-input-date";

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
				Date minDate = PickDateUtil.getObjectAsDate(minimumDate, datePattern);
				Object maximumDate = getMaximumDate();
				Date maxDate = PickDateUtil.getObjectAsDate(maximumDate, datePattern);
				Date submittedDate = PickDateUtil.getObjectAsDate(newValue, datePattern);

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
					minDate = PickDateUtil.getDateAtMidnight(minDate);
					maxDate = PickDateUtil.getDateAtMidnight(maxDate);
					submittedDate = PickDateUtil.getDateAtMidnight(submittedDate);

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
							Locale locale = PickDateUtil.getObjectAsLocale(getLocale(facesContext));
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

			// Provide a default converter of DateTimeConverter if no converter is specified.
			DateTimeConverter dateTimeConverter = new DateTimeConverter();
			String datePattern = getDatePattern();
			dateTimeConverter.setPattern(datePattern);

			Object objectLocale = getLocale();
			Locale locale = PickDateUtil.getObjectAsLocale(objectLocale);
			dateTimeConverter.setLocale(locale);

			Object objectTimeZone = getTimeZone();
			TimeZone timeZone = InputDateTimeUtil.getObjectAsTimeZone(objectTimeZone);
			dateTimeConverter.setTimeZone(timeZone);
			converter = dateTimeConverter;
		}

		return converter;
	}

	@Override
	public String getDatePattern() {

		String datePattern = super.getDatePattern();

		if (datePattern == null) {

			// Provide a default datePattern based on the locale.
			Object locale = getLocale();
			datePattern = PickDateUtil.getDefaultDatePattern(locale);
		}

		return datePattern;
	}

	@Override
	public Object getLocale() {
		return getLocale(FacesContext.getCurrentInstance());
	}

	public Object getLocale(FacesContext facesContext) {
		return PickDateUtil.determineLocale(facesContext, super.getLocale());
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
