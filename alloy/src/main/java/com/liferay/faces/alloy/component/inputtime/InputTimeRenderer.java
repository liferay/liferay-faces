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
import java.util.ArrayList;
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

import com.liferay.faces.alloy.component.inputdate.internal.MobileBrowserSnifferUtil;
import com.liferay.faces.alloy.component.inputdatetime.InputDateTime;
import com.liferay.faces.alloy.component.inputdatetime.InputDateTimeResponseWriter;
import com.liferay.faces.alloy.component.inputdatetime.InputDateTimeUtil;
import com.liferay.faces.alloy.component.inputtext.InputText;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.js.JavaScriptFragment;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = InputTime.COMPONENT_FAMILY, rendererType = InputTime.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class InputTimeRenderer extends InputTimeRendererBase {

	// Private Constants
	private static final String BUTTON_ON_SELECT_TEMPLATE = "A.one('#{0}').set('value', event.result.text);";
	private static final Pattern TIMESTAMP_PATTERN = Pattern.compile("([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]");
	private static final String TOKEN_0 = "{0}";

	// Private Constants used in getMaskFromTimePattern()
	private static final String TOKEN_REGEX = "\\{0\\}";

	protected static String getMaskFromTimePattern(String timePattern) {

		String mask = timePattern;

		mask = mask.replaceAll("%", "%%");
		mask = mask.replaceAll(StringPool.NEW_LINE, "%n");
		mask = mask.replaceAll(StringPool.TAB, "%t");

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

		String[] timeStampArray = timeStamp.split(StringPool.COLON);
		int hours = Integer.parseInt(timeStampArray[0]);
		int minutes = Integer.parseInt(timeStampArray[1]);
		int seconds = Integer.parseInt(timeStampArray[2]);

		return ((((hours * 60) + minutes) * 60) + seconds) * 1000;
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		InputTime inputTime = (InputTime) uiComponent;
		String clientVarName = ComponentUtil.getClientVarName(facesContext, inputTime);
		String clientKey = inputTime.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (MobileBrowserSnifferUtil.isMobile(facesContext)) {

			JavaScriptFragment liferayComponent = new JavaScriptFragment("Liferay.component('" + clientKey + "')");
			String clientId = uiComponent.getClientId(facesContext);
			String inputClientId = clientId.concat(INPUT_SUFFIX);

			// Get the max and min times in the HTML5 format which does not include seconds.
			int defaultHTML5PatternLength = InputTime.DEFAULT_HTML5_TIME_PATTERN.length();
			String maxTime = inputTime.getMaxTime().substring(0, defaultHTML5PatternLength);
			String minTime = inputTime.getMinTime().substring(0, defaultHTML5PatternLength);
			RendererUtil.encodeFunctionCall(responseWriter, "LFAI.initDateTimePickerMobile", liferayComponent,
				inputClientId, maxTime, minTime);
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
	}

	protected void encodeAutocomplete(FacesContext facesContext, ResponseWriter responseWriter, InputTime inputTime,
		boolean first) throws IOException {

		encodeNonEscapedObject(responseWriter, "autocomplete", StringPool.BLANK, first);
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

						break;
					}
				}

				break;
			}
		}

		String showOn = inputTime.getShowOn();

		if ("button".equals(showOn) || (timeSelectClientBehaviorScript != null)) {

			encodeNonEscapedObject(responseWriter, "after", StringPool.BLANK, autoCompleteFirst);
			responseWriter.write(StringPool.OPEN_CURLY_BRACE);

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("function(event){");

			if ("button".equals(showOn)) {

				String clientId = inputTime.getClientId(facesContext);
				String escapedInputClientId = RendererUtil.escapeClientId(clientId.concat(INPUT_SUFFIX));
				String onSelect = BUTTON_ON_SELECT_TEMPLATE.replace(TOKEN_0, escapedInputClientId);
				stringBuilder.append(onSelect);
			}

			if (timeSelectClientBehaviorScript != null) {
				stringBuilder.append(timeSelectClientBehaviorScript);
			}

			stringBuilder.append("}");
			encodeNonEscapedObject(responseWriter, "select", stringBuilder.toString(), true);
			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			autoCompleteFirst = false;
		}

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, InputTime inputTime,
		boolean first) throws IOException {

		if (!MobileBrowserSnifferUtil.isMobile(facesContext)) {

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
	public String getAlloyClassName() {

		String alloyClassName = super.getAlloyClassName();
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (MobileBrowserSnifferUtil.isMobile(facesContext)) {
			alloyClassName = alloyClassName.concat("Native");
		}

		return alloyClassName;
	}

	@Override
	public String getButtonIconName() {
		return "time";
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
	protected InputDateTimeResponseWriter getInputDateTimeResponseWriter(ResponseWriter responseWriter,
		String inputClientId, boolean mobile) {
		return new InputTimeResponseWriter(responseWriter, StringPool.INPUT, inputClientId, mobile);
	}

	@Override
	protected String[] getModules(UIComponent uiComponent) {

		List<String> modules = new ArrayList<String>();
		String[] oldModules = super.getModules(uiComponent);
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (MobileBrowserSnifferUtil.isMobile(facesContext)) {
			String nativeAlloyModuleName = oldModules[0].concat("-native");
			modules.add(nativeAlloyModuleName);
		}
		else {

			modules.add(oldModules[0]);

			InputTime inputTime = (InputTime) uiComponent;
			String filterType = inputTime.getFilterType();

			if ((filterType != null) && (filterType.length() > 0)) {
				modules.add("autocomplete-filters");
			}

			String highlighterType = inputTime.getHighlighterType();

			if ((highlighterType != null) && (highlighterType.length() > 0)) {
				modules.add("autocomplete-highlighters");
			}
		}

		return modules.toArray(new String[] {});
	}
}
