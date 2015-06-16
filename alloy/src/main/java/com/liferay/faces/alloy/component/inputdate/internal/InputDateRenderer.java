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
package com.liferay.faces.alloy.component.inputdate.internal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.inputdate.InputDate;
import com.liferay.faces.alloy.component.inputdatetime.InputDateTime;
import com.liferay.faces.alloy.component.inputdatetime.internal.InputDateTimeResponseWriter;
import com.liferay.faces.alloy.render.internal.JavaScriptFragment;
import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = InputDate.COMPONENT_FAMILY, rendererType = InputDate.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class InputDateRenderer extends InputDateRendererBase {

	// Private Constants used in getMaskFromDatePattern()
	private static final String REGEX_TOKEN = "\\{0\\}";

	protected static String getMaskFromDatePattern(String datePattern) {

		String mask = datePattern;

		mask = mask.replaceAll("%", "%%");
		mask = mask.replaceAll(StringPool.NEW_LINE, "%n");
		mask = mask.replaceAll(StringPool.TAB, "%t");

		mask = mask.replaceAll("yyyy", "%Y");
		mask = mask.replaceAll("yy", "%y");
		mask = mask.replaceAll("MMMMM", "%B");
		mask = mask.replaceAll("MMM", "%b");
		mask = mask.replaceAll("MM", "%m");
		mask = mask.replaceAll("M", "%m");

		// Replace "dd" with a token (which doesn't contain "d"), so "%d" won't be replaced when we call
		// replaceAll("d", "%e").
		mask = mask.replaceAll("dd", REGEX_TOKEN);
		mask = mask.replaceAll("d", "%e");
		mask = mask.replaceAll(REGEX_TOKEN, "%d");
		mask = mask.replaceAll("DDD", "%j");
		mask = mask.replaceAll("FF", "%u");
		mask = mask.replaceAll("EEEE", "%A");
		mask = mask.replaceAll("EEE", "%a");

		return mask;
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());
		InputDate inputDate = (InputDate) uiComponent;
		String showOn = inputDate.getShowOn();

		if ((browserSniffer.isMobile() && inputDate.isResponsive()) || "button".equals(showOn)) {

			String clientVarName = ComponentUtil.getClientVarName(facesContext, inputDate);
			String clientKey = inputDate.getClientKey();

			if (clientKey == null) {
				clientKey = clientVarName;
			}

			JavaScriptFragment liferayComponent = new JavaScriptFragment("Liferay.component('" + clientKey + "')");
			ResponseWriter responseWriter = facesContext.getResponseWriter();

			if (browserSniffer.isMobile() && inputDate.isResponsive()) {

				String clientId = uiComponent.getClientId(facesContext);
				String inputClientId = clientId.concat(INPUT_SUFFIX);

				Object maxDateObject = inputDate.getMaxDate();
				Object minDateObject = inputDate.getMinDate();
				String maxDateString = null;
				String minDateString = null;

				if ((maxDateObject != null) || (minDateObject != null)) {

					String datePattern = inputDate.getPattern();
					String timeZoneString = inputDate.getTimeZone();
					TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(InputDate.DEFAULT_HTML5_DATE_PATTERN,
							Locale.ENGLISH);

					if (maxDateObject != null) {
						Date maxDate = inputDate.getObjectAsDate(maxDateObject, datePattern, timeZone);
						maxDateString = simpleDateFormat.format(maxDate);
					}

					if (maxDateObject != null) {
						Date minDate = inputDate.getObjectAsDate(minDateObject, datePattern, timeZone);
						minDateString = simpleDateFormat.format(minDate);
					}
				}

				encodeFunctionCall(responseWriter, "LFAI.initDateTimePickerMobile", liferayComponent, inputClientId,
					maxDateString, minDateString);
			}
			else if ("button".equals(showOn)) {

				String clientId = inputDate.getClientId(facesContext);
				String inputClientId = clientId.concat(INPUT_SUFFIX);
				String escapedInputClientId = escapeClientId(inputClientId);

				encodeFunctionCall(responseWriter, "LFAI.initDatePickerShowOnButton", 'A', escapedInputClientId,
					liferayComponent);
			}
		}
	}

	protected void encodeCalendar(FacesContext facesContext, ResponseWriter responseWriter, InputDate inputDate,
		boolean first) throws IOException {

		// The calendar attribute value provides the opportunity to specify dateClick events, minDate, maxDate as
		// key:value pairs via JSON syntax. For example: "calendar: {minDate: new Date(2015,1,1,0,0,0,0)}"
		boolean calendarFirst = true;

		encodeNonEscapedObject(responseWriter, "calendar", StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		Object maxDateObject = inputDate.getMaxDate();

		if (maxDateObject != null) {

			encodeDate(responseWriter, inputDate, "maximumDate", maxDateObject, calendarFirst);
			calendarFirst = false;
		}

		Object minDateObject = inputDate.getMinDate();

		if (minDateObject != null) {

			encodeDate(responseWriter, inputDate, "minimumDate", minDateObject, calendarFirst);
			calendarFirst = false;
		}

		String showOn = inputDate.getShowOn();
		boolean showOnButton = "button".equals(showOn);
		Map<String, List<ClientBehavior>> clientBehaviorMap = inputDate.getClientBehaviors();
		List<ClientBehavior> valueChangeClientBehaviors = clientBehaviorMap.get(VALUE_CHANGE);
		boolean valueChangeClientBehaviorsNotEmpty = (valueChangeClientBehaviors != null) &&
			!valueChangeClientBehaviors.isEmpty();

		if (showOnButton || valueChangeClientBehaviorsNotEmpty) {

			encodeNonEscapedObject(responseWriter, "on", StringPool.BLANK, calendarFirst);
			responseWriter.write(StringPool.OPEN_CURLY_BRACE);

			encodeNonEscapedObject(responseWriter, "dateClick", StringPool.BLANK, true);
			responseWriter.write("function(event){");

			String clientId = inputDate.getClientId(facesContext);
			String inputClientId = clientId.concat(INPUT_SUFFIX);
			String escapedInputClientId = escapeClientId(inputClientId);
			JavaScriptFragment selectable = new JavaScriptFragment("this._canBeSelected(event.date)");
			JavaScriptFragment date = null;

			if (showOnButton) {

				// Each time a date is clicked, the input must be updated via javascript because the datePicker is not
				// attached to the input.
				String datePattern = inputDate.getPattern();
				String mask = getMaskFromDatePattern(datePattern);
				String escapedMask = escapeJavaScript(mask);
				date = new JavaScriptFragment("A.Date.format(event.date,{format:'".concat(escapedMask).concat("'})"));
			}

			encodeFunctionCall(responseWriter, "LFAI.inputDateTimePickerSelect", 'A', escapedInputClientId, selectable,
				date, valueChangeClientBehaviorsNotEmpty);
			responseWriter.append(";}");
			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			calendarFirst = false;
		}

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	protected void encodeDate(ResponseWriter responseWriter, InputDate inputDate, String attributeName,
		Object dateObject, boolean first) throws IOException {

		String datePattern = inputDate.getPattern();
		String timeZoneString = inputDate.getTimeZone();
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
		Date date = inputDate.getObjectAsDate(dateObject, datePattern, timeZone);

		// Note: The JavaScript date object expects zero-based month numbers, so it is necessary to offset the month
		// by 1.
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("'new Date'(yyyy,MM-1,dd,0,0,0,0)");
		simpleDateFormat.setTimeZone(timeZone);

		String dateString = simpleDateFormat.format(date);
		encodeNonEscapedObject(responseWriter, attributeName, dateString, first);
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, InputDate inputDate,
		boolean first) throws IOException {

		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());

		if (!(browserSniffer.isMobile() && inputDate.isResponsive())) {

			encodeCalendar(facesContext, responseWriter, inputDate, first);
			first = false;

			encodeHiddenAttributesInputDateTime(facesContext, responseWriter, inputDate, first);
			first = false;
		}
	}

	@Override
	protected void encodeMask(ResponseWriter responseWriter, InputDate inputDate, String datePattern, boolean first)
		throws IOException {

		String datePatternMask = getMaskFromDatePattern(datePattern);

		super.encodeMask(responseWriter, inputDate, datePatternMask, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {

		String alloyClassName = super.getAlloyClassName(facesContext, uiComponent);
		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());
		InputDate inputDate = (InputDate) uiComponent;

		if (browserSniffer.isMobile() && inputDate.isResponsive()) {
			alloyClassName = alloyClassName.concat("Native");
		}

		return alloyClassName;
	}

	@Override
	public String getButtonIconName() {
		return "calendar";
	}

	@Override
	protected InputDateTimeResponseWriter getInputDateTimeResponseWriter(ResponseWriter responseWriter,
		String inputClientId, boolean mobile, boolean responsive) {
		return new InputDateResponseWriter(responseWriter, StringPool.INPUT, inputClientId, mobile, responsive);
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return getModules(MODULES, facesContext, uiComponent);
	}

	@Override
	protected List<String> getModules(List<String> modules, FacesContext facesContext, InputDateTime inputDateTime) {

		InputDate inputDate = (InputDate) inputDateTime;
		String showOn = inputDate.getShowOn();
		List<String> modulesList = new ArrayList<String>(modules);

		if ("button".equals(showOn)) {
			modulesList.add("aui-datatype-date-parse");
		}

		return modulesList;
	}
}
