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
package com.liferay.faces.alloy.component.datepicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Kyle Stiemann
 */
public class DatePickerUtil {

	// Private Constants
	private static final String D = "d";
	private static final String DD = "dd";
	private static final String DDD = "DDD";
	private static final String EEE = "EEE";
	private static final String EEEE = "EEEE";
	private static final String FF = "FF";
	private static final String M = "M";
	private static final String MM = "MM";
	private static final String MMM = "MMM";
	private static final String MMMMM = "MMMMM";
	private static final String NEW_DATE = "new Date";
	private static final String PERCENT_A_LOWER = "%a";
	private static final String PERCENT_A_UPPER = "%A";
	private static final String PERCENT_B_LOWER = "%b";
	private static final String PERCENT_B_UPPER = "%B";
	private static final String PERCENT_D_LOWER = "%d";
	private static final String PERCENT_E_LOWER = "%e";
	private static final String PERCENT_J_LOWER = "%j";
	private static final String PERCENT_M_LOWER = "%m";
	private static final String PERCENT_N_LOWER = "%n";
	private static final String PERCENT_PERCENT = "%%";
	private static final String PERCENT_T_LOWER = "%t";
	private static final String PERCENT_U_LOWER = "%u";
	private static final String PERCENT_Y_LOWER = "%y";
	private static final String PERCENT_Y_UPPER = "%Y";
	private static final String TOKEN_REGEX = "\\{0\\}";
	private static final String YY = "yy";
	private static final String YYYY = "yyyy";

	public static Locale determineLocale(FacesContext facesContext, Object objectLocale) {

		Locale locale = getObjectAsLocale(objectLocale);

		if (locale == null) {

			UIViewRoot viewRoot = facesContext.getViewRoot();
			locale = viewRoot.getLocale();
		}

		return locale;
	}

	public static String toJavascriptDateString(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		int minYear = calendar.get(Calendar.YEAR);
		int minMonth = calendar.get(Calendar.MONTH);
		int minDay = calendar.get(Calendar.DAY_OF_MONTH);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(NEW_DATE);
		stringBuilder.append(StringPool.OPEN_PARENTHESIS);
		stringBuilder.append(minYear);
		stringBuilder.append(StringPool.COMMA);
		stringBuilder.append(minMonth);
		stringBuilder.append(StringPool.COMMA);
		stringBuilder.append(minDay);
		stringBuilder.append(StringPool.CLOSE_PARENTHESIS);

		return stringBuilder.toString();
	}

	public static Date getDateAtMidnight(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);

		return calendar.getTime();
	}

	public static String getDatePatternFromMask(String mask) {

		String datePattern = mask;

		datePattern = datePattern.replaceAll(PERCENT_Y_UPPER, YYYY);
		datePattern = datePattern.replaceAll(PERCENT_Y_LOWER, YY);
		datePattern = datePattern.replaceAll(PERCENT_B_UPPER, MMMMM);
		datePattern = datePattern.replaceAll(PERCENT_B_LOWER, MMM);
		datePattern = datePattern.replaceAll(PERCENT_M_LOWER, MM);

		// Replace "%e" with a token (which doesn't contain "d"), so any "d"'s which happen to appear in front of
		// "%"'s won't be replaced when we call replaceAll("%d", "dd").
		datePattern = datePattern.replaceAll(PERCENT_E_LOWER, TOKEN_REGEX);
		datePattern = datePattern.replaceAll(PERCENT_D_LOWER, DD);
		datePattern = datePattern.replaceAll(TOKEN_REGEX, D);
		datePattern = datePattern.replaceAll(PERCENT_J_LOWER, DDD);
		datePattern = datePattern.replaceAll(PERCENT_U_LOWER, FF);
		datePattern = datePattern.replaceAll(PERCENT_A_UPPER, EEEE);
		datePattern = datePattern.replaceAll(PERCENT_A_LOWER, EEE);

		datePattern = datePattern.replaceAll(PERCENT_N_LOWER, StringPool.NEW_LINE);
		datePattern = datePattern.replaceAll(PERCENT_T_LOWER, StringPool.TAB);
		datePattern = datePattern.replaceAll(PERCENT_PERCENT, StringPool.PERCENT);

		return datePattern;
	}

	public static String getDefaultDatePattern(FacesContext facesContext, Object componentLocale) {

		Locale locale = DatePickerUtil.determineLocale(facesContext, componentLocale);

		// Note: The following usage of SimpleDateFormat is thread-safe, since only the result of the toPattern()
		// method is utilized.
		SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.MEDIUM,
				locale);

		return simpleDateFormat.toPattern();
	}

	public static String getMaskFromDatePattern(String datePattern) {

		String mask = datePattern;

		mask = mask.replaceAll(StringPool.PERCENT, PERCENT_PERCENT);
		mask = mask.replaceAll(StringPool.NEW_LINE, PERCENT_N_LOWER);
		mask = mask.replaceAll(StringPool.TAB, PERCENT_T_LOWER);

		mask = mask.replaceAll(YYYY, PERCENT_Y_UPPER);
		mask = mask.replaceAll(YY, PERCENT_Y_LOWER);
		mask = mask.replaceAll(MMMMM, PERCENT_B_UPPER);
		mask = mask.replaceAll(MMM, PERCENT_B_LOWER);
		mask = mask.replaceAll(MM, PERCENT_M_LOWER);
		mask = mask.replaceAll(M, PERCENT_M_LOWER);

		// Replace "dd" with a token (which doesn't contain "d"), so "%d" won't be replaced when we call
		// replaceAll("d", "%e").
		mask = mask.replaceAll(DD, TOKEN_REGEX);
		mask = mask.replaceAll(D, PERCENT_E_LOWER);
		mask = mask.replaceAll(TOKEN_REGEX, PERCENT_D_LOWER);
		mask = mask.replaceAll(DDD, PERCENT_J_LOWER);
		mask = mask.replaceAll(FF, PERCENT_U_LOWER);
		mask = mask.replaceAll(EEEE, PERCENT_A_UPPER);
		mask = mask.replaceAll(EEE, PERCENT_A_LOWER);

		return mask;
	}

	public static Date getObjectAsDate(Object dateAsObject, String datePattern) {
		return getObjectAsDate(dateAsObject, datePattern, null);
	}

	public static Date getObjectAsDate(Object dateAsObject, String datePattern, String mask) throws FacesException {

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

						if (mask != null) {

							String maskDatePattern = getDatePatternFromMask(mask);
							simpleDateFormat.applyPattern(maskDatePattern);
						}

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
