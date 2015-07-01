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
package com.liferay.faces.alloy.component.inputdatetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
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
		Date submittedDate = getObjectAsDate(newValue, pattern, timeZone);

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
					Locale locale = getObjectAsLocale(getLocale(facesContext));
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

	@Override
	public Converter getConverter() {
		Converter converter = super.getConverter();

		if (converter == null) {

			// Provide a default converter of DateTimeConverter if no converter is specified.
			DateTimeConverter dateTimeConverter = new DateTimeConverter();
			String pattern = getPattern();
			dateTimeConverter.setPattern(pattern);

			Object objectLocale = getLocale();
			Locale locale = getObjectAsLocale(objectLocale);
			dateTimeConverter.setLocale(locale);

			String timeZoneString = getTimeZone();
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
			dateTimeConverter.setTimeZone(timeZone);
			converter = dateTimeConverter;
		}

		return converter;
	}

	@Override
	public abstract boolean isNativeWhenMobile();

	protected boolean isNative() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());

		return browserSniffer.isMobile() && isNativeWhenMobile();
	}

	@Override
	public Object getLocale() {
		return getLocale(FacesContext.getCurrentInstance());
	}

	public Object getLocale(FacesContext facesContext) {

		Object locale = super.getLocale();

		if (locale == null) {

			UIViewRoot viewRoot = facesContext.getViewRoot();
			locale = viewRoot.getLocale();
		}

		return locale;
	}

	protected MessageContext getMessageContext() {

		MessageContextFactory messageContextFactory = (MessageContextFactory) FactoryExtensionFinder.getFactory(
				MessageContextFactory.class);

		return messageContextFactory.getMessageContext();
	}

	public final Date getObjectAsDate(Object dateAsObject, String datePattern, TimeZone timeZone)
		throws FacesException {

		Date date = null;

		if (dateAsObject != null) {

			if (dateAsObject instanceof Date) {
				date = (Date) dateAsObject;
			}
			else if (dateAsObject instanceof String) {

				String dateAsString = (String) dateAsObject;

				if (dateAsString.length() > 0) {

					try {

						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
						simpleDateFormat.setTimeZone(timeZone);
						date = simpleDateFormat.parse(dateAsString);
					}
					catch (ParseException e) {

						FacesException facesException = new FacesException(e);
						throw facesException;
					}
				}
			}
			else {

				String message = "Unable to convert value to Date.";
				FacesException facesException = new FacesException(message);
				throw facesException;
			}
		}

		return date;
	}

	public final Locale getObjectAsLocale(Object localeAsObject) throws FacesException {

		Locale locale = null;

		if (localeAsObject != null) {

			if (localeAsObject instanceof Locale) {
				locale = (Locale) localeAsObject;
			}
			else if (localeAsObject instanceof String) {

				String localeAsString = (String) localeAsObject;

				if (localeAsString.length() > 0) {
					String[] locales = localeAsString.split("-");

					if (locales.length > 1) {
						locale = new Locale(locales[0], locales[1]);
					}
					else {
						locale = new Locale(locales[0]);
					}
				}
			}
			else {

				String message = "Unable to convert value to locale.";
				FacesException facesException = new FacesException(message);
				throw facesException;
			}
		}

		return locale;
	}

	public abstract String getPattern();

	@Override
	public String getShowOn() {
		return (String) getStateHelper().eval(InputDateTimePropertyKeys.showOn, FOCUS);
	}

	@Override
	public String getTimeZone() {
		return (String) getStateHelper().eval(InputDateTimePropertyKeys.timeZone, GREENWICH);
	}
}
