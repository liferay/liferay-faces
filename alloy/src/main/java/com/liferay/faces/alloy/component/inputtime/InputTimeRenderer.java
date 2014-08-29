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
package com.liferay.faces.alloy.component.inputtime;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
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
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.inputdatetime.InputDateTime;
import com.liferay.faces.alloy.component.inputdatetime.InputDateTimeUtil;
import com.liferay.faces.alloy.component.inputtext.InputText;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.helper.StringHelper;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = InputTime.COMPONENT_FAMILY, rendererType = InputTime.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
//J+
public class InputTimeRenderer extends InputTimeRendererBase {

	// Private Constants
	private static final String ACTIVATE_FIRST_ITEM = "activateFirstItem";
	private static final String AFTER = "after";
	private static final String AUTOCOMPLETE = "autocomplete";
	private static final String AUTOCOMPLETE_FILTERS = "autocomplete-filters";
	private static final String AUTOCOMPLETE_HIGLIGHTER = "autocomplete-highlighters";
	private static final String BUTTON_ON_SELECT_TEMPLATE = "A.one('#{0}').set('value', event.result.text);";
	private static final String TIMESTAMP_REGEX = "([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
	private static final Pattern TIMESTAMP_PATTERN = Pattern.compile(TIMESTAMP_REGEX);
	private static final String RESULT_FILTERS = "resultFilters";
	private static final String RESULT_HIGHLIGHTER = "resultHighlighter";
	private static final String SELECT = "select";
	private static final String TIME = "time";

	// TODO come back to this when condiftioal loading is available
	private static final String[] TIME_PICKER_MODULES = StringHelper.append(MODULES, AUTOCOMPLETE_FILTERS,
			AUTOCOMPLETE_HIGLIGHTER);
	private static final String TOKEN_0 = "{0}";

	// Private Constants used in getMaskFromTimePattern()
	private static final String HH = "HH";
	private static final String H = "H";
	private static final String HH_LOWER = "hh";
	private static final String H_LOWER = "h";
	private static final String MM = "mm";
	private static final String M = "m";
	private static final String SS_LOWER = "ss";
	private static final String S_LOWER = "s";
	private static final String AAA = "aaa";
	private static final String AA = "aa";
	private static final String A = "a";
	private static final String Z = "Z";
	private static final String ZZZZ_LOWER = "zzzz";
	private static final String ZZZ_LOWER = "zzz";
	private static final String ZZ_LOWER = "zz";
	private static final String Z_LOWER = "z";
	private static final String PERCENT_K_LOWER = "%k";
	private static final String PERCENT_L_LOWER = "%l";
	private static final String PERCENT_P_LOWER = "%p";
	private static final String PERCENT_Z_LOWER = "%z";
	private static final String PERCENT_H_UPPER = "%H";
	private static final String PERCENT_Z_UPPER = "%Z";
	private static final String PERCENT_M_UPPER = "%M";
	private static final String PERCENT_I_UPPER = "%I";
	private static final String PERCENT_S_UPPER = "%S";
	private static final String PERCENT_N_LOWER = "%n";
	private static final String PERCENT_PERCENT = "%%";
	private static final String PERCENT_T_LOWER = "%t";
	private static final String TOKEN_REGEX = "\\{0\\}";

	protected static String getMaskFromTimePattern(String timePattern) {

		String mask = timePattern;

		mask = mask.replaceAll(StringPool.PERCENT, PERCENT_PERCENT);
		mask = mask.replaceAll(StringPool.NEW_LINE, PERCENT_N_LOWER);
		mask = mask.replaceAll(StringPool.TAB, PERCENT_T_LOWER);

		mask = mask.replaceAll(HH, TOKEN_REGEX);
		mask = mask.replaceAll(H, PERCENT_K_LOWER);
		mask = mask.replaceAll(TOKEN_REGEX, PERCENT_H_UPPER);
		mask = mask.replaceAll(HH_LOWER, PERCENT_I_UPPER);
		mask = mask.replaceAll(H_LOWER, PERCENT_L_LOWER);
		mask = mask.replaceAll(MM, PERCENT_M_UPPER);
		mask = mask.replaceAll(M, PERCENT_M_UPPER); // Not directly supported by AlloyUI
		mask = mask.replaceAll(SS_LOWER, PERCENT_S_UPPER);
		mask = mask.replaceAll(S_LOWER, PERCENT_S_UPPER); // Not directly supported by AlloyUI
		mask = mask.replaceAll(AAA, PERCENT_P_LOWER);
		mask = mask.replaceAll(AA, PERCENT_P_LOWER);
		mask = mask.replaceAll(A, PERCENT_P_LOWER);
		mask = mask.replaceAll(Z, TOKEN_REGEX);
		mask = mask.replaceAll(ZZZZ_LOWER, PERCENT_Z_UPPER); // Not directly supported by AlloyUI
		mask = mask.replaceAll(ZZZ_LOWER, PERCENT_Z_UPPER); // Not directly supported by AlloyUI
		mask = mask.replaceAll(ZZ_LOWER, PERCENT_Z_UPPER); // Not directly supported by AlloyUI
		mask = mask.replaceAll(Z_LOWER, PERCENT_Z_UPPER);
		mask = mask.replaceAll(TOKEN_REGEX, PERCENT_Z_LOWER);

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

		String[] timeStampArray = timeStamp.split(StringPool.COLON);
		int hours = Integer.parseInt(timeStampArray[0]);
		int minutes = Integer.parseInt(timeStampArray[1]);
		int seconds = Integer.parseInt(timeStampArray[2]);

		return ((((hours * 60) + minutes) * 60) + seconds) * 1000;
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		InputTime inputTime = (InputTime) uiComponent;
		encodeValues(facesContext, inputTime, true);
	}

	protected void encodeAutocomplete(FacesContext facesContext, ResponseWriter responseWriter, InputTime inputTime,
		boolean first) throws IOException {

		encodeNonEscapedObject(responseWriter, AUTOCOMPLETE, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		boolean autoCompleteFirst = true;
		Boolean activateFirstItem = inputTime.isActivateFirstItem();

		if (activateFirstItem != null) {
			encodeBoolean(responseWriter, ACTIVATE_FIRST_ITEM, activateFirstItem, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		Boolean circular = inputTime.isCircular();

		if (circular != null) {
			encodeBoolean(responseWriter, CIRCULAR, circular, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		String filterType = inputTime.getFilterType();

		if (filterType != null) {
			encodeString(responseWriter, RESULT_FILTERS, filterType, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		String height = inputTime.getHeight();

		if (height != null) {
			encodeString(responseWriter, HEIGHT, height, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		String highlighterType = inputTime.getHighlighterType();

		if (highlighterType != null) {
			encodeString(responseWriter, RESULT_HIGHLIGHTER, highlighterType, autoCompleteFirst);
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

		Boolean scrollIntoView = inputTime.isScrollIntoView();

		if (scrollIntoView != null) {
			encodeBoolean(responseWriter, SCROLL_INTO_VIEW, scrollIntoView, autoCompleteFirst);
			autoCompleteFirst = false;
		}

		String timeSelectClientBehaviorScript = null;
		Map<String, List<ClientBehavior>> clientBehaviorMap = inputTime.getClientBehaviors();
		Collection<String> eventNames = inputTime.getEventNames();

		for (String eventName : eventNames) {

			if (TimeSelectEvent.TIME_SELECT.equals(eventName)) {

				List<ClientBehavior> clientBehaviorsForEvent = clientBehaviorMap.get(eventName);

				if (clientBehaviorsForEvent != null) {

					for (ClientBehavior clientBehavior : clientBehaviorsForEvent) {

						String clientId = inputTime.getClientId(facesContext);
						ClientBehaviorContext clientBehaviorContext = ClientBehaviorContext.createClientBehaviorContext(
								facesContext, inputTime, eventName, clientId, null);
						timeSelectClientBehaviorScript = clientBehavior.getScript(clientBehaviorContext);
					}
				}

				break;
			}
		}

		String showOn = inputTime.getShowOn();

		if (BUTTON.equals(showOn) || (timeSelectClientBehaviorScript != null)) {

			encodeNonEscapedObject(responseWriter, AFTER, StringPool.BLANK, autoCompleteFirst);
			responseWriter.write(StringPool.OPEN_CURLY_BRACE);

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("function(event){");

			if (BUTTON.equals(showOn)) {

				String clientId = inputTime.getClientId(facesContext);
				String escapedInputClientId = RendererUtil.escapeClientId(clientId.concat(INPUT_SUFFIX));
				String onSelect = BUTTON_ON_SELECT_TEMPLATE.replace(TOKEN_0, escapedInputClientId);
				stringBuilder.append(onSelect);
			}

			if (timeSelectClientBehaviorScript != null) {
				stringBuilder.append(timeSelectClientBehaviorScript);
			}

			stringBuilder.append("}");
			encodeNonEscapedObject(responseWriter, SELECT, stringBuilder.toString(), true);
			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			autoCompleteFirst = false;
		}

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, InputTime inputTime,
		boolean first) throws IOException {

		encodeInputDateTimeHiddenAttributes(facesContext, responseWriter, inputTime, first);
		first = false;

		encodeAutocomplete(facesContext, responseWriter, inputTime, first);
		first = false;
	}

	@Override
	protected void encodeMask(ResponseWriter responseWriter, InputTime inputTime, String timePattern, boolean first)
		throws IOException {

		String timePatternMask = getMaskFromTimePattern(timePattern);
		super.encodeMask(responseWriter, inputTime, timePatternMask, first);
	}

	protected void encodeValues(FacesContext facesContext, InputTime inputTime, boolean first) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String clientVarName = ComponentUtil.getClientVarName(facesContext, inputTime);
		String clientKey = inputTime.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);

		// Replace the default TimePicker._setValues() method with setValues() (defined in inputTime.js) which simply
		// passes values through to the autocomplete without processing them.
		responseWriter.write(clientVarName);
		responseWriter.write("._setValues=LFAI.setValues;");

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

		String timePattern = inputTime.getTimePattern();
		Object objectLocale = inputTime.getLocale();
		Locale locale = InputDateTimeUtil.getObjectAsLocale(objectLocale);
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
				responseWriter.write(StringPool.COMMA);
			}
			else {
				firstTimeStamp = false;
			}

			Date time = new Date(milliseconds);
			String dateString = simpleDateFormat.format(time);
			String escapedDateString = RendererUtil.escapeJavaScript(dateString);

			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(escapedDateString);
			responseWriter.write(StringPool.APOSTROPHE);
		}

		responseWriter.write("]);");

	}

	@Override
	public String getButtonIconName() {
		return TIME;
	}

	@Override
	public String getDelegateComponentFamily() {
		return InputText.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return InputText.DELEGATE_RENDERER_TYPE;
	}

	@Override
	protected String[] getModules() {
		return TIME_PICKER_MODULES;
	}
}
