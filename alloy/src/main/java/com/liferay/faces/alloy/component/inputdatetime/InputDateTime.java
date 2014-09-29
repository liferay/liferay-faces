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
package com.liferay.faces.alloy.component.inputdatetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;

import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 */
public abstract class InputDateTime extends InputDateTimeBase implements ClientComponent {

	// Public Constants
	public static final String FOCUS = "focus";
	public static final String GREENWICH = "Greenwich";

	protected void validateValue(FacesContext facesContext, Object newValue, Date minDate, Date maxDate,
		TimeZone timeZone) {

		String pattern = getPattern();
		Date submittedDate = InputDateTimeUtil.getObjectAsDate(newValue, pattern, timeZone);

		try {

			// To determine if the submitted value is valid, check if it falls between the minimum date and
			// the maximum date.
			if (submittedDate.before(minDate) || submittedDate.after(maxDate)) {

				setValid(false);

				String validatorMessage = getValidatorMessage();
				FacesMessage facesMessage;

				if (validatorMessage != null) {
					facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessage, validatorMessage);
				}
				else {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					simpleDateFormat.setTimeZone(timeZone);

					String minDateString = simpleDateFormat.format(minDate);
					String maxDateString = simpleDateFormat.format(maxDate);
					Locale locale = InputDateTimeUtil.getObjectAsLocale(getLocale(facesContext));
					String message = getMessageContext().getMessage(locale, "please-enter-a-value-between-x-and-x",
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
		catch (FacesException e) {

			setValid(false);

			String message = e.getMessage();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
			String clientId = getClientId(facesContext);
			facesContext.addMessage(clientId, facesMessage);
		}
	}

	protected MessageContext getMessageContext() {

		MessageContextFactory messageContextFactory = (MessageContextFactory) FactoryExtensionFinder.getFactory(
			MessageContextFactory.class);

		return messageContextFactory.getMessageContext();
	}

	@Override
	public Converter getConverter() {
		Converter converter = super.getConverter();

		if (converter == null) {

			// Provide a default converter of DateTimeConverter if no converter is specified.
			DateTimeConverter dateTimeConverter = new DateTimeConverter();
			String pattern = getPattern();
			dateTimeConverter.setPattern(pattern);

			Object objectLocale = getLocale();
			Locale locale = InputDateTimeUtil.getObjectAsLocale(objectLocale);
			dateTimeConverter.setLocale(locale);

			String timeZoneString = getTimeZone();
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
			dateTimeConverter.setTimeZone(timeZone);
			converter = dateTimeConverter;
		}

		return converter;
	}

	@Override
	public abstract boolean isResponsive();

	@Override
	public Object getLocale() {
		return getLocale(FacesContext.getCurrentInstance());
	}

	public Object getLocale(FacesContext facesContext) {
		return InputDateTimeUtil.determineLocale(facesContext, super.getLocale());
	}

	protected abstract String getPattern();

	@Override
	public String getShowOn() {
		return (String) getStateHelper().eval(InputDateTimePropertyKeys.showOn, FOCUS);
	}

	@Override
	public String getTimeZone() {
		return (String) getStateHelper().eval(InputDateTimePropertyKeys.timeZone, GREENWICH);
	}
}
