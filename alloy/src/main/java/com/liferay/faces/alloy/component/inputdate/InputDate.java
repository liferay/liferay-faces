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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;

import com.liferay.faces.alloy.component.inputdatetime.InputDateTimeUtil;
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
	private static final String GREENWICH = "Greenwich";
	private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList(
				DateSelectEvent.DATE_SELECT));

	public InputDate() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	protected static String getDefaultDatePattern(Object componentLocale) {

		Locale locale = InputDateTimeUtil.getObjectAsLocale(componentLocale);

		// Note: The following usage of SimpleDateFormat is thread-safe, since only the result of the toPattern()
		// method is utilized.
		SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.MEDIUM,
				locale);

		return simpleDateFormat.toPattern();
	}

	@Override
	public void addClientBehavior(String eventName, ClientBehavior clientBehavior) {

		if (clientBehavior instanceof AjaxBehavior) {
			AjaxBehavior ajaxBehavior = (AjaxBehavior) clientBehavior;
			ajaxBehavior.addAjaxBehaviorListener(new InputDateBehaviorListener());
		}

		super.addClientBehavior(eventName, clientBehavior);
	}

	@Override
	public void queueEvent(FacesEvent facesEvent) {

		if (facesEvent instanceof AjaxBehaviorEvent) {

			AjaxBehaviorEvent ajaxBehaviorEvent = (AjaxBehaviorEvent) facesEvent;

			UIComponent component = ajaxBehaviorEvent.getComponent();
			Behavior behavior = ajaxBehaviorEvent.getBehavior();

			FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
			String clientId = getClientId(facesContext);
			String selectedDateString = requestParameterMap.get(clientId);

			Date selectedDate = null;

			if ((selectedDateString != null) && (selectedDateString.length() > 0)) {

				Converter dateTimeConverter = getConverter();
				selectedDate = (Date) dateTimeConverter.getAsObject(facesContext, component, selectedDateString);
			}

			facesEvent = new DateSelectEvent(component, behavior, selectedDate);
		}

		super.queueEvent(facesEvent);
	}

	@Override
	protected void validateValue(FacesContext facesContext, Object newValue) {

		super.validateValue(facesContext, newValue);

		if (isValid() && (newValue != null)) {

			try {

				// Get all necessary dates.
				String datePattern = getDatePattern();
				Object minimumDate = getMinimumDate();
				String timeZoneString = getTimeZone();
				TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);

				Date minDate = InputDateTimeUtil.getObjectAsDate(minimumDate, datePattern, timeZone);
				Object maximumDate = getMaximumDate();
				Date maxDate = InputDateTimeUtil.getObjectAsDate(maximumDate, datePattern, timeZone);
				Date submittedDate = InputDateTimeUtil.getObjectAsDate(newValue, datePattern, timeZone);

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
					minDate = getDateAtMidnight(minDate, timeZone);
					maxDate = getDateAtMidnight(maxDate, timeZone);

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
							simpleDateFormat.setTimeZone(timeZone);

							String minDateString = simpleDateFormat.format(minDate);
							String maxDateString = simpleDateFormat.format(maxDate);
							Locale locale = InputDateTimeUtil.getObjectAsLocale(getLocale(facesContext));
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
		return CALENDAR;
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
			Locale locale = InputDateTimeUtil.getObjectAsLocale(objectLocale);
			dateTimeConverter.setLocale(locale);

			String timeZoneString = getTimeZone();
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
			dateTimeConverter.setTimeZone(timeZone);
			converter = dateTimeConverter;
		}

		return converter;
	}

	protected Date getDateAtMidnight(Date date, TimeZone timeZone) {

		Calendar calendar = new GregorianCalendar(timeZone);
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	@Override
	public String getDatePattern() {

		String datePattern = super.getDatePattern();

		if (datePattern == null) {

			// Provide a default datePattern based on the locale.
			Object locale = getLocale();
			datePattern = getDefaultDatePattern(locale);
		}

		return datePattern;
	}

	@Override
	public String getDefaultEventName() {
		return DateSelectEvent.DATE_SELECT;
	}

	@Override
	public Collection<String> getEventNames() {

		List<String> eventNames = new ArrayList<String>();
		eventNames.addAll(super.getEventNames());
		eventNames.addAll(EVENT_NAMES);

		return Collections.unmodifiableList(eventNames);
	}

	@Override
	public Object getLocale() {
		return getLocale(FacesContext.getCurrentInstance());
	}

	public Object getLocale(FacesContext facesContext) {
		return InputDateTimeUtil.determineLocale(facesContext, super.getLocale());
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}

	@Override
	public String getTimeZone() {
		return (String) getStateHelper().eval(InputDatePropertyKeys.timeZone, GREENWICH);
	}
}
