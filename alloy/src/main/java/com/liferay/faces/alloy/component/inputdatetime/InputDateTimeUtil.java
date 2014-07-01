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

import java.util.TimeZone;

import javax.faces.FacesException;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Kyle Stiemann
 */
public class InputDateTimeUtil {

	public static String getInputIdSuffix(FacesContext facesContext) {
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);

		return separatorChar + StringPool.INPUT;
	}

	public static TimeZone getObjectAsTimeZone(Object timeZoneAsObject) throws FacesException {

		TimeZone timeZone = null;

		if (timeZoneAsObject != null) {

			if (timeZoneAsObject instanceof TimeZone) {
				timeZone = (TimeZone) timeZoneAsObject;
			}
			else if (timeZoneAsObject instanceof String) {

				String timeZoneAsString = (String) timeZoneAsObject;

				if (timeZoneAsString.length() > 0) {

					// Note: The following usage of TimeZone is thread-safe, since only the result of the getTimeZone()
					// method is utilized.
					timeZone = TimeZone.getTimeZone(timeZoneAsString);
				}
			}
			else {

				String message = "Unable to convert value to TimeZone.";
				FacesException facesException = new FacesException(message);
				throw facesException;
			}
		}

		return timeZone;
	}

}
