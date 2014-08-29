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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.inputdatetime.InputDateTimeUtil;
import com.liferay.faces.alloy.component.inputtext.InputText;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = InputDate.COMPONENT_FAMILY, rendererType = InputDate.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
//J+
public class InputDateRenderer extends InputDateRendererBase {

	// Private Constants
	private static final String CALENDAR = "calendar";

	// This is a javascript function that sets the value of the input to the new date. It is used when
	// showOn="button".
	private static final String BUTTON_ON_DATE_CLICK_TEMPLATE = "var input=A.one('#{0}');" +
		"input.set('value',A.Date.format(event.date,{format:'{1}'}));";
	private static final String DATE_CLICK = "dateClick";

	// NOTE: The JavaScript date object expects zero-based month numbers, so it is necessary to offset the month by 1.
	private static final String JAVASCRIPT_NEW_DATE_PATTERN = "'new Date'(yyyy,MM-1,dd,0,0,0,0)";
	protected static final String MAXIMUM_DATE = "maximumDate";
	protected static final String MINIMUM_DATE = "minimumDate";
	private static final String ON = "on";
	private static final String TOKEN_0 = "{0}";
	private static final String TOKEN_1 = "{1}";

	// Private Constants used in getMaskFromDatePattern()
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
	private static final String REGEX_TOKEN = "\\{0\\}";
	private static final String YY = "yy";
	private static final String YYYY = "yyyy";

	protected static String getMaskFromDatePattern(String datePattern) {

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
		mask = mask.replaceAll(DD, REGEX_TOKEN);
		mask = mask.replaceAll(D, PERCENT_E_LOWER);
		mask = mask.replaceAll(REGEX_TOKEN, PERCENT_D_LOWER);
		mask = mask.replaceAll(DDD, PERCENT_J_LOWER);
		mask = mask.replaceAll(FF, PERCENT_U_LOWER);
		mask = mask.replaceAll(EEEE, PERCENT_A_UPPER);
		mask = mask.replaceAll(EEE, PERCENT_A_LOWER);

		return mask;
	}

	protected void encodeCalendar(FacesContext facesContext, ResponseWriter responseWriter, InputDate inputDate,
		boolean first) throws IOException {

		// The calendar attribute value provides the opportunity to specify dateClick events, selectionMode,
		// minDate, maxDate as key:value pairs via JSON syntax.
		// For example: "calendar: {selectionMode: 'multiple'}"
		boolean calendarFirst = true;

		encodeNonEscapedObject(responseWriter, CALENDAR, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		Object maxDateObject = inputDate.getMaxDate();

		if (maxDateObject != null) {

			encodeDate(responseWriter, inputDate, MAXIMUM_DATE, maxDateObject, calendarFirst);
			calendarFirst = false;
		}

		Object minDateObject = inputDate.getMinDate();

		if (minDateObject != null) {

			encodeDate(responseWriter, inputDate, MINIMUM_DATE, minDateObject, calendarFirst);
			calendarFirst = false;
		}

		String dateSelectClientBehaviorScript = null;
		Map<String, List<ClientBehavior>> clientBehaviorMap = inputDate.getClientBehaviors();
		Collection<String> eventNames = inputDate.getEventNames();

		for (String eventName : eventNames) {

			if (DateSelectEvent.DATE_SELECT.equals(eventName)) {

				List<ClientBehavior> clientBehaviorsForEvent = clientBehaviorMap.get(eventName);

				if (clientBehaviorsForEvent != null) {

					for (ClientBehavior clientBehavior : clientBehaviorsForEvent) {

						String clientId = inputDate.getClientId(facesContext);
						ClientBehaviorContext clientBehaviorContext = ClientBehaviorContext.createClientBehaviorContext(
								facesContext, inputDate, eventName, clientId, null);
						dateSelectClientBehaviorScript = clientBehavior.getScript(clientBehaviorContext);
					}
				}

				break;
			}
		}

		String showOn = inputDate.getShowOn();

		if (BUTTON.equals(showOn) || (dateSelectClientBehaviorScript != null)) {

			encodeNonEscapedObject(responseWriter, ON, StringPool.BLANK, calendarFirst);
			responseWriter.write(StringPool.OPEN_CURLY_BRACE);

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("function(event){if(this._canBeSelected(event.date)){");

			if (BUTTON.equals(showOn)) {

				// Each time a date is clicked, the input must be updated via javascript because the datePicker is not
				// attached to the input.
				String clientId = inputDate.getClientId(facesContext);
				String escapedInputClientId = RendererUtil.escapeClientId(clientId.concat(INPUT_SUFFIX));
				String buttonOnDateClick = BUTTON_ON_DATE_CLICK_TEMPLATE.replace(TOKEN_0, escapedInputClientId);
				String datePattern = inputDate.getDatePattern();
				String mask = getMaskFromDatePattern(datePattern);
				String onDateClick = buttonOnDateClick.replace(TOKEN_1, mask);
				stringBuilder.append(onDateClick);
			}

			if (dateSelectClientBehaviorScript != null) {
				stringBuilder.append(dateSelectClientBehaviorScript);
			}

			stringBuilder.append("}}");
			encodeNonEscapedObject(responseWriter, DATE_CLICK, stringBuilder.toString(), true);
			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			calendarFirst = false;
		}

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	protected void encodeDate(ResponseWriter responseWriter, InputDate inputDate, String attributeName,
		Object dateObject, boolean first) throws IOException {

		String datePattern = inputDate.getDatePattern();
		String timeZoneString = inputDate.getTimeZone();
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
		Date date = InputDateTimeUtil.getObjectAsDate(dateObject, datePattern, timeZone);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(JAVASCRIPT_NEW_DATE_PATTERN);
		simpleDateFormat.setTimeZone(timeZone);

		String dateString = simpleDateFormat.format(date);
		encodeNonEscapedObject(responseWriter, attributeName, dateString, first);
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, InputDate inputDate,
		boolean first) throws IOException {

		encodeInputDateTimeHiddenAttributes(facesContext, responseWriter, inputDate, first);
		first = false;

		encodeCalendar(facesContext, responseWriter, inputDate, first);
		first = false;
	}

	@Override
	protected void encodeMask(ResponseWriter responseWriter, InputDate inputDate, String datePattern, boolean first)
		throws IOException {

		String datePatternMask = getMaskFromDatePattern(datePattern);

		super.encodeMask(responseWriter, inputDate, datePatternMask, first);
	}

	@Override
	public String getButtonIconName() {
		return CALENDAR;
	}

	@Override
	public String getDelegateComponentFamily() {
		return InputText.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return InputText.DELEGATE_RENDERER_TYPE;
	}
}
