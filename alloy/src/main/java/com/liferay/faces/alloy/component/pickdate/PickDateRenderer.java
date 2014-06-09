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
package com.liferay.faces.alloy.component.pickdate;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.helper.StringHelper;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Kyle Stiemann
 */
@FacesRenderer(componentFamily = PickDate.COMPONENT_FAMILY, rendererType = PickDate.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "pickdate.js")
	}
)
public class PickDateRenderer extends PickDateRendererBase {

	// Private Constants
	private static final String CALENDAR = "calendar";
	private static final String DATE_CLICK = "dateClick";
	private static final String DEFAULT_ON_DATE_CLICK_TEMPLATE =
		"pickDateDefaultOnDateClick(event.date, A.one('{0}'), this);";
	private static final String LANG = "lang";
	private static final String TOKEN = "{0}";
	private static final String NODE_EVENT_SIMULATE = "node-event-simulate";
	private static final String ON = "on";
	private static final String POPOVER_CSS_CLASS = "popoverCssClass";
	private static final String[] DATE_PICKER_MODULES = StringHelper.append(MODULES, NODE_EVENT_SIMULATE);

	@Override
	public void encodeLang(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {

		PickDate pickDate = (PickDate) uiComponent;
		Locale locale = PickDateUtil.determineLocale(facesContext, pickDate.getLocale());

		// RFC 1766 requires the subtags of locales to be delimited by hyphens rather than underscores.
		// http://www.faqs.org/rfcs/rfc1766.html
		String localeString = locale.toString().replaceAll(StringPool.UNDERLINE, StringPool.DASH);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		encodeString(responseWriter, LANG, localeString, true);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// This is a no-op since the DataPicker does not manifest any markup.
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// This is a no-op since the DataPicker does not manifest any markup.
	}

	protected void encodeCalendar(ResponseWriter responseWriter, PickDate pickDate, boolean first) throws IOException {

		// The calendar attribute value provides the opportunity to specify dateClick events, selectionMode,
		// minimumDate, maximumDate as key:value pairs via JSON syntax.
		// For example: "calendar: {selectionMode: 'multiple'}"
		boolean calendarFirst = true;

		encodeObject(responseWriter, CALENDAR, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		String componentDatePattern = pickDate.getDatePattern();
		Object maximumDate = pickDate.getMaximumDate();

		if (maximumDate != null) {

			Date maxDate = PickDateUtil.getObjectAsDate(maximumDate, componentDatePattern);
			String maxDateString = PickDateUtil.toJavascriptDateString(maxDate);
			encodeObject(responseWriter, PickDate.MAXIMUM_DATE, maxDateString, calendarFirst);
			calendarFirst = false;
		}

		Object minimumDate = pickDate.getMinimumDate();

		if (minimumDate != null) {

			Date minDate = PickDateUtil.getObjectAsDate(minimumDate, componentDatePattern);
			String minDateString = PickDateUtil.toJavascriptDateString(minDate);
			encodeObject(responseWriter, PickDate.MINIMUM_DATE, minDateString, calendarFirst);
			calendarFirst = false;
		}

		String selectionMode = pickDate.getSelectionMode();

		if (selectionMode != null) {

			encodeString(responseWriter, PickDate.SELECTION_MODE, selectionMode, calendarFirst);
			calendarFirst = false;
		}

		encodeObject(responseWriter, ON, StringPool.BLANK, calendarFirst);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		String onDateClick = pickDate.getOnDateClick();

		if (onDateClick == null) {

			String for_ = pickDate.getFor();
			UIComponent forComponent = pickDate.findComponent(for_);

			if (forComponent != null) {

				String forComponentClientId = forComponent.getClientId();
				for_ = ComponentUtil.escapeClientId(forComponentClientId);
			}

			// The trigger is the "#" symbol followed by the forComponent's clientId.
			String trigger = StringPool.POUND + for_;

			// The default value of the onDateClick attribute is a script that that is read from the
			// DefaultOnDateClick.js classpath resource. The script contains a "{0}" token that needs
			// to be substituted with the trigger, which is the "#" sign followed by escaped clientId of the trigger.
			onDateClick = DEFAULT_ON_DATE_CLICK_TEMPLATE;
			onDateClick = onDateClick.replace(TOKEN, trigger);
		}

		encodeEvent(responseWriter, DATE_CLICK, onDateClick, true);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		calendarFirst = false;

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected void encodeHiddenAttributes(ResponseWriter responseWriter, PickDate pickDate, boolean first)
		throws IOException {

		encodeCalendar(responseWriter, pickDate, first);
		first = false;

		String styleClass = pickDate.getStyleClass();

		if (styleClass != null) {
			encodeString(responseWriter, POPOVER_CSS_CLASS, styleClass, first);
			first = false;
		}
	}

	@Override
	protected void encodeMask(ResponseWriter responseWriter, PickDate pickDate, String datePattern, boolean first)
		throws IOException {

		String datePatternMask = PickDateUtil.getMaskFromDatePattern(datePattern);

		super.encodeMask(responseWriter, pickDate, datePatternMask, first);
	}

	@Override
	protected void encodePopover(ResponseWriter responseWriter, PickDate pickDate, Object zIndex, boolean first)
		throws IOException {

		// The popover attribute value provides the opportunity to specify a zIndex key:value pair via JSON
		// syntax. For example: "popover: {zIndex: 1}"
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(StringPool.OPEN_CURLY_BRACE);
		stringBuilder.append(PickDate.Z_INDEX);
		stringBuilder.append(StringPool.COLON);
		stringBuilder.append(zIndex);
		stringBuilder.append(StringPool.CLOSE_CURLY_BRACE);

		super.encodePopover(responseWriter, pickDate, stringBuilder.toString(), first);
	}

	@Override
	protected void encodeTrigger(ResponseWriter responseWriter, PickDate pickDate, String for_, boolean first)
		throws IOException {

		UIComponent forComponent = pickDate.findComponent(for_);

		if (forComponent != null) {

			String forComponentClientId = forComponent.getClientId();
			for_ = ComponentUtil.escapeClientId(forComponentClientId);
		}

		String trigger = StringPool.POUND + for_;

		super.encodeTrigger(responseWriter, pickDate, trigger, first);
	}

	@Override
	protected boolean isForceInline(FacesContext facesContext, UIComponent uiComponent) {

		// In order to support the "lang" attribute of the YUI object, it is necessary to determine if the user has
		// specified a locale other than that of the server or view root. If so, then the javascript must be rendered
		// inline.
		PickDate pickDate = (PickDate) uiComponent;
		Object componentLocale = pickDate.getLocale();
		Locale locale = PickDateUtil.determineLocale(facesContext, componentLocale);
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale viewRootLocale = viewRoot.getLocale();

		return !locale.equals(viewRootLocale);
	}

	@Override
	protected String[] getModules() {
		return DATE_PICKER_MODULES;
	}
}
