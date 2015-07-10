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
package com.liferay.faces.alloy.component.inputtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.FacesException;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@FacesComponent(value = InputTime.COMPONENT_TYPE)
public class InputTime extends InputTimeBase {

	// Public Constants
	public static final String DEFAULT_HTML5_TIME_PATTERN = "HH:mm";

	// Private Constants
	private static final String MIN_MAX_TIME_PATTERN = "HH:mm:ss";

	@Override
	protected void validateValue(FacesContext facesContext, Object newValue) {

		super.validateValue(facesContext, newValue);

		if (isValid() && (newValue != null)) {

			// Determine if the specified value falls between the values of the minTime and maxTime attributes.
			String timeZoneAsString = getTimeZone();
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneAsString);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MIN_MAX_TIME_PATTERN);
			simpleDateFormat.setTimeZone(timeZone);

			String minTimeString = getMinTime();
			String maxTimeString = getMaxTime();

			try {
				Date minTime = simpleDateFormat.parse(minTimeString);
				Date maxTime = simpleDateFormat.parse(maxTimeString);
				super.validateValue(facesContext, newValue, minTime, maxTime, timeZone);
			}
			catch (ParseException e) {

				FacesException facesException = new FacesException(e);
				throw facesException;
			}
		}
	}

	@Override
	public String getPattern() {

		String timePattern;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());

		if (browserSniffer.isMobile() && isNativeWhenMobile()) {
			timePattern = DEFAULT_HTML5_TIME_PATTERN;
		}
		else {
			timePattern = super.getPattern();
		}

		return timePattern;
	}
}
