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
package com.liferay.faces.alloy.component.inputdate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = InputDate.COMPONENT_TYPE)
public class InputDate extends InputDateBase {

	// Public Constants
	public static final String DEFAULT_HTML5_DATE_PATTERN = "yyyy-MM-dd";

	@Override
	protected void validateValue(FacesContext facesContext, Object newValue) {

		super.validateValue(facesContext, newValue);

		if (isValid() && (newValue != null)) {

			// Get all necessary dates.
			String datePattern = getPattern();
			Object minDateObject = getMinDate();
			String timeZoneString = getTimeZone();
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);

			Date minDate = getObjectAsDate(minDateObject, datePattern, timeZone);
			Object maxDateObject = getMaxDate();
			Date maxDate = getObjectAsDate(maxDateObject, datePattern, timeZone);

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

				super.validateValue(facesContext, newValue, minDate, maxDate, timeZone);
			}
		}
	}

	private Date getDateAtMidnight(Date date, TimeZone timeZone) {

		Calendar calendar = new GregorianCalendar(timeZone);
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	private String getDefaultDatePattern(Object componentLocale) {

		Locale locale = getObjectAsLocale(componentLocale);

		// Note: The following usage of SimpleDateFormat is thread-safe, since only the result of the toPattern()
		// method is utilized.
		SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.MEDIUM,
				locale);

		return simpleDateFormat.toPattern();
	}

	@Override
	public String getPattern() {

		String datePattern;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());

		if (browserSniffer.isMobile() && isNativeWhenMobile()) {
			datePattern = DEFAULT_HTML5_DATE_PATTERN;
		}
		else {

			datePattern = super.getPattern();

			if (datePattern == null) {

				// Provide a default datePattern based on the locale.
				Object locale = getLocale();
				datePattern = getDefaultDatePattern(locale);
			}
		}

		return datePattern;
	}
}
