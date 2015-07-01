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
package com.liferay.faces.alloy.component.inputtime.internal;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.inputdatetime.InputDateTime;
import com.liferay.faces.alloy.component.inputdatetime.internal.InputDateTimeResponseWriter;
import com.liferay.faces.alloy.component.inputtime.InputTime;
import com.liferay.faces.alloy.render.internal.JavaScriptFragment;
import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = InputTime.COMPONENT_FAMILY, rendererType = InputTime.RENDERER_TYPE)
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
public class InputTimeRenderer extends InputTimeRendererBase {

	// Private Constants
	private static final Pattern TIMESTAMP_PATTERN = Pattern.compile("([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]");

	// Private Constants used in getMaskFromTimePattern()
	private static final String TOKEN_REGEX = "\\{0\\}";

	protected static String getMaskFromTimePattern(String timePattern) {

		String mask = timePattern;

		mask = mask.replaceAll("%", "%%");
		mask = mask.replaceAll("\n", "%n");
		mask = mask.replaceAll("\t", "%t");

		mask = mask.replaceAll("HH", TOKEN_REGEX);
		mask = mask.replaceAll("H", "%k");
		mask = mask.replaceAll(TOKEN_REGEX, "%H");
		mask = mask.replaceAll("hh", "%I");
		mask = mask.replaceAll("h", "%l");
		mask = mask.replaceAll("mm", "%M");
		mask = mask.replaceAll("m", "%M"); // Not directly supported by AlloyUI
		mask = mask.replaceAll("ss", "%S");
		mask = mask.replaceAll("s", "%S"); // Not directly supported by AlloyUI
		mask = mask.replaceAll("aaa", "%p");
		mask = mask.replaceAll("aa", "%p");
		mask = mask.replaceAll("a", "%p");
		mask = mask.replaceAll("Z", TOKEN_REGEX);
		mask = mask.replaceAll("zzzz", "%Z"); // Not directly supported by AlloyUI
		mask = mask.replaceAll("zzz", "%Z"); // Not directly supported by AlloyUI
		mask = mask.replaceAll("zz", "%Z"); // Not directly supported by AlloyUI
		mask = mask.replaceAll("z", "%Z");
		mask = mask.replaceAll(TOKEN_REGEX, "%z");

		return mask;
	}

	/**
	 * This method accepts a timeStamp String of the format "HH:mm:ss". If the String does not have this format, this
	 * method throws a ParseException.
	 */
	private static long getMillisFromTimeStamp(String timeStamp) throws ParseException {

		if (!TIMESTAMP_PATTERN.matcher(timeStamp).matches()) {

			int errorOffset;

			for (errorOffset = timeStamp.length() - 1; errorOffset > 0; errorOffset--) {
				Matcher matcher = TIMESTAMP_PATTERN.matcher(timeStamp.substring(0, errorOffset));

				if (!matcher.matches() && matcher.hitEnd()) {
					break;
				}
			}

			throw new ParseException("TimeStamp must be of the form \"HH:mm:ss\".", errorOffset);
		}

		String[] timeStampArray = timeStamp.split(":");
		int hours = Integer.parseInt(timeStampArray[0]);
		int minutes = Integer.parseInt(timeStampArray[1]);
		int seconds = Integer.parseInt(timeStampArray[2]);

		return ((((hours * 60) + minutes) * 60) + seconds) * 1000;
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		InputTime inputTime = (InputTime) uiComponent;
		String clientVarName = getClientVarName(facesContext, inputTime);
		String clientKey = inputTime.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());

		if (isNative(browserSniffer, inputTime)) {

			JavaScriptFragment liferayComponent = new JavaScriptFragment("Liferay.component('" + clientKey + "')");
			String clientId = uiComponent.getClientId(facesContext);
			String inputClientId = clientId.concat(INPUT_SUFFIX);

			// Get the max and min times in the HTML5 format which does not include seconds.
			int defaultHTML5PatternLength = InputTime.DEFAULT_HTML5_TIME_PATTERN.length();
			String maxTime = inputTime.getMaxTime().substring(0, defaultHTML5PatternLength);
			String minTime = inputTime.getMinTime().substring(0, defaultHTML5PatternLength);
			encodeFunctionCall(responseWriter, "LFAI.initDateTimePickerMobile", liferayComponent, inputClientId,
				maxTime, minTime);
		}
		else {

			encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);

			// Replace the default TimePicker._setValues() method with setValues() (defined in alloy.js) which simply
			// passes values through to the autocomplete without processing them.
			responseWriter.write(clientVarName);
			responseWriter.write("._setValues=LFAI.timePickerSetValues;");

			// Set the values of the timePicker.
			responseWriter.write(clientVarName);
			responseWriter.write(".set('values',[");

			String minTimeStamp = inputTime.getMinTime();
			String maxTimeStamp = inputTime.getMaxTime();

			long minTime;
			long maxTime;

			try {
				minTime = getMillisFromTimeStamp(minTimeStamp);
				maxTime = getMillisFromTimeStamp(maxTimeStamp);
			}
			catch (ParseException e) {
				throw new IOException(e);
			}

			if (minTime > maxTime) {
				throw new IOException("minTime must not be later than maxTime.");
			}

			String timePattern = inputTime.getPattern();
			Object objectLocale = inputTime.getLocale();
			Locale locale = inputTime.getObjectAsLocale(objectLocale);
			TimeZone timeZone = TimeZone.getTimeZone(InputDateTime.GREENWICH);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattern, locale);
			simpleDateFormat.setTimeZone(timeZone);

			Integer millisecondStep = inputTime.getStep() * 1000;
			boolean firstTimeStamp = true;

			if (millisecondStep < 1) {
				throw new IOException("step cannot be less than 1.");
			}

			// The values of the timePicker are determined by iterating from minTime to maxTime by the value of
			// step, and printing each resulting value.
			for (long milliseconds = minTime; milliseconds <= maxTime; milliseconds = milliseconds + millisecondStep) {

				if (!firstTimeStamp) {
					responseWriter.write(",");
				}
				else {
					firstTimeStamp = false;
				}

				Date time = new Date(milliseconds);
				String dateString = simpleDateFormat.format(time);
				String escapedDateString = escapeJavaScript(dateString);

				responseWriter.write("'");
				responseWriter.write(escapedDateString);
				responseWriter.write("'");
			}

			responseWriter.write("]);");
		}
	}

	protected void encodeAutocomplete(FacesContext facesContext, ResponseWriter responseWriter, InputTime inputTime,
		boolean first) throws IOException {

		encodeNonEscapedObject(responseWriter, "autocomplete", "", first);
		responseWriter.write("{");

		boolean autoCompleteFirst = true;
		boolean activateFirstItem = inputTime.isActivateFirstItem();
		encodeBoolean(responseWriter, ACTIVATE_FIRST_ITEM, activateFirstItem, autoCompleteFirst);
		autoCompleteFirst = false;

		Boolean circular = inputTime.getCircular();

		if (circular != null) {
			encodeBoolean(responseWriter, CIRCULAR, circular, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		String filterType = inputTime.getFilterType();

		if (filterType != null) {
			encodeString(responseWriter, "resultFilters", filterType, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		String height = inputTime.getHeight();

		if (height != null) {
			encodeString(responseWriter, HEIGHT, height, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		String highlighterType = inputTime.getHighlighterType();

		if (highlighterType != null) {
			encodeString(responseWriter, "resultHighlighter", highlighterType, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		Integer maxResults = inputTime.getMaxResults();

		if (maxResults != null) {
			encodeString(responseWriter, MAX_RESULTS, maxResults, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		Integer queryDelay = inputTime.getQueryDelay();

		if (queryDelay != null) {
			encodeString(responseWriter, QUERY_DELAY, queryDelay, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		Boolean scrollIntoView = inputTime.getScrollIntoView();

		if (scrollIntoView != null) {
			encodeBoolean(responseWriter, SCROLL_INTO_VIEW, scrollIntoView, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		String showOn = inputTime.getShowOn();
		boolean showOnButton = "button".equals(showOn);
		Map<String, List<ClientBehavior>> clientBehaviorMap = inputTime.getClientBehaviors();
		List<ClientBehavior> valueChangeClientBehaviors = clientBehaviorMap.get(VALUE_CHANGE);
		boolean valueChangeClientBehaviorsNotEmpty = (valueChangeClientBehaviors != null) &&
			!valueChangeClientBehaviors.isEmpty();

		if (showOnButton || valueChangeClientBehaviorsNotEmpty) {

			encodeNonEscapedObject(responseWriter, "after", "", autoCompleteFirst);
			responseWriter.write("{");

			encodeNonEscapedObject(responseWriter, "select", "", true);
			responseWriter.write("function(event){");

			String clientId = inputTime.getClientId(facesContext);
			String inputClientId = clientId.concat(INPUT_SUFFIX);
			String escapedInputClientId = escapeClientId(inputClientId);
			boolean selectable = true;
			JavaScriptFragment time = null;

			if (showOnButton) {
				time = new JavaScriptFragment("event.result.text");
			}

			encodeFunctionCall(responseWriter, "LFAI.inputDateTimePickerSelect", 'A', escapedInputClientId, selectable,
				time, valueChangeClientBehaviorsNotEmpty);
			responseWriter.append(";}");
			responseWriter.write("}");
			autoCompleteFirst = false;
		}

		responseWriter.write("}");
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, InputTime inputTime,
		boolean first) throws IOException {

		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());

		if (!isNative(browserSniffer, inputTime)) {

			encodeAutocomplete(facesContext, responseWriter, inputTime, first);
			first = false;

			encodeHiddenAttributesInputDateTime(facesContext, responseWriter, inputTime, first);
			first = false;
		}
	}

	@Override
	protected void encodeMask(ResponseWriter responseWriter, InputTime inputTime, String timePattern, boolean first)
		throws IOException {

		String timePatternMask = getMaskFromTimePattern(timePattern);
		super.encodeMask(responseWriter, inputTime, timePatternMask, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {

		String alloyClassName = super.getAlloyClassName(facesContext, uiComponent);
		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());
		InputTime inputTime = (InputTime) uiComponent;

		if (isNative(browserSniffer, inputTime)) {
			alloyClassName = alloyClassName.concat("Native");
		}

		return alloyClassName;
	}

	@Override
	public String getButtonIconName() {
		return "time";
	}

	@Override
	protected InputDateTimeResponseWriter getInputDateTimeResponseWriter(ResponseWriter responseWriter,
		String inputClientId, boolean nativeInputTime) {
		return new InputTimeResponseWriter(responseWriter, inputClientId, nativeInputTime);
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return getModules(MODULES, facesContext, uiComponent);
	}

	@Override
	protected List<String> getModules(List<String> modules, FacesContext facesContext, InputDateTime inputDateTime) {

		List<String> inputTimeModules = new ArrayList<String>(modules);
		InputTime inputTime = (InputTime) inputDateTime;
		String filterType = inputTime.getFilterType();

		if ((filterType != null) && (filterType.length() > 0)) {
			inputTimeModules.add("autocomplete-filters");
		}

		String highlighterType = inputTime.getHighlighterType();

		if ((highlighterType != null) && (highlighterType.length() > 0)) {
			inputTimeModules.add("autocomplete-highlighters");
		}

		return inputTimeModules;
	}
}
