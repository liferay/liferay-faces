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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.FacesException;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Kyle Stiemann
 */
public class InputDateTimeUtil {

	public static Date getObjectAsDate(Object dateAsObject, String datePattern, TimeZone timeZone)
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

	public static Locale getObjectAsLocale(Object localeAsObject) throws FacesException {

		Locale locale = null;

		if (localeAsObject != null) {

			if (localeAsObject instanceof Locale) {
				locale = (Locale) localeAsObject;
			}
			else if (localeAsObject instanceof String) {

				String localeAsString = (String) localeAsObject;

				if (localeAsString.length() > 0) {
					String[] locales = localeAsString.split(StringPool.DASH);

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
}
