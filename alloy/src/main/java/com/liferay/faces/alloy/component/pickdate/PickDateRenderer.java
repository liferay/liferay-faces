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
import java.util.TimeZone;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.helper.StringHelper;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = PickDate.COMPONENT_FAMILY, rendererType = PickDate.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "pickdate.js")
	}
)
//J+
public class PickDateRenderer extends PickDateRendererBase {

	// Private Constants
	private static final String CALENDAR = "calendar";
	private static final String DATE_CLICK = "dateClick";
	private static final String DEFAULT_ON_DATE_CLICK_TEMPLATE = "function(event){" +
		"pickDateDefaultOnDateClick(event, A.one('{0}'));}";
	private static final String LANG = "lang";
	private static final String TOKEN = "{0}";
	private static final String NODE_EVENT_SIMULATE = "node-event-simulate";
	private static final String ON = "on";
	private static final String POPOVER = "popover";
	private static final String POPOVER_CSS_CLASS = "popoverCssClass";
	private static final String[] DATE_PICKER_MODULES = StringHelper.append(MODULES, NODE_EVENT_SIMULATE);

	@Override
	public void encodeLang(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {

		PickDate pickDate = (PickDate) uiComponent;
		Locale locale = PickDateUtil.getObjectAsLocale(pickDate.getLocale(facesContext));

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

	protected void encodeCalendar(FacesContext facesContext, ResponseWriter responseWriter, PickDate pickDate,
		boolean first) throws IOException {

		// The calendar attribute value provides the opportunity to specify dateClick events, selectionMode,
		// minimumDate, maximumDate as key:value pairs via JSON syntax.
		// For example: "calendar: {selectionMode: 'multiple'}"
		boolean calendarFirst = true;

		encodeNonEscapedObject(responseWriter, CALENDAR, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		String componentDatePattern = pickDate.getDatePattern();
		Object maximumDate = pickDate.getMaximumDate();

		if (maximumDate != null) {

			Object timeZoneObject = pickDate.getTimeZone();
			TimeZone timeZone = PickDateUtil.getObjectAsTimeZone(timeZoneObject);
			Date maxDate = PickDateUtil.getObjectAsDate(maximumDate, componentDatePattern, timeZone);
			String maxDateString = PickDateUtil.toJavascriptDateString(maxDate, timeZone);
			encodeNonEscapedObject(responseWriter, MAXIMUM_DATE, maxDateString, calendarFirst);
			calendarFirst = false;
		}

		Object minimumDate = pickDate.getMinimumDate();

		if (minimumDate != null) {

			Object timeZoneObject = pickDate.getTimeZone();
			TimeZone timeZone = PickDateUtil.getObjectAsTimeZone(timeZoneObject);
			Date minDate = PickDateUtil.getObjectAsDate(minimumDate, componentDatePattern, timeZone);
			String minDateString = PickDateUtil.toJavascriptDateString(minDate, timeZone);
			encodeNonEscapedObject(responseWriter, MINIMUM_DATE, minDateString, calendarFirst);
			calendarFirst = false;
		}

		String selectionMode = pickDate.getSelectionMode();

		if (selectionMode != null) {

			encodeString(responseWriter, SELECTION_MODE, selectionMode, calendarFirst);
			calendarFirst = false;
		}

		encodeNonEscapedObject(responseWriter, ON, StringPool.BLANK, calendarFirst);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		String onDateClick = pickDate.getOnDateClick();

		if (onDateClick == null) {

			String for_ = pickDate.getFor();
			UIComponent forComponent = pickDate.findComponent(for_);

			if (forComponent != null) {
				for_ = forComponent.getClientId(facesContext);
			}

			// The trigger is the "#" symbol followed by the forComponent's clientId.
			String trigger = StringPool.POUND + RendererUtil.escapeClientId(for_);

			// The default value of the onDateClick attribute is a script that that is read from the
			// DefaultOnDateClick.js classpath resource. The script contains a "{0}" token that needs
			// to be substituted with the trigger, which is the "#" sign followed by escaped clientId of the trigger.
			onDateClick = DEFAULT_ON_DATE_CLICK_TEMPLATE.replace(TOKEN, trigger);
		}

		encodeNonEscapedObject(responseWriter, DATE_CLICK, onDateClick, true);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		calendarFirst = false;

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, PickDate pickDate,
		boolean first) throws IOException {

		encodeCalendar(facesContext, responseWriter, pickDate, first);
		first = false;

		encodePopover(responseWriter, pickDate, first);
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

	protected void encodePopover(ResponseWriter responseWriter, PickDate pickDate, boolean first) throws IOException {

		// The popover attribute value provides the opportunity to specify a zIndex key:value pair via JSON
		// syntax. For example: "popover: {zIndex: 1}"
		encodeNonEscapedObject(responseWriter, POPOVER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		Integer zIndex = pickDate.getzIndex();
		String zIndexString = null;

		if (zIndex != null) {
			zIndexString = zIndex.toString();
		}
		else {
			zIndexString = LIFERAY_Z_INDEX_TOOLTIP;
		}

		encodeNonEscapedObject(responseWriter, Z_INDEX, zIndexString, true);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected boolean isForceInline(FacesContext facesContext, UIComponent uiComponent) {

		// In order to support the "lang" attribute of the YUI object, it is necessary to determine if the user has
		// specified a locale other than that of the server or view root. If so, then the javascript must be rendered
		// inline.
		PickDate pickDate = (PickDate) uiComponent;
		Locale locale = PickDateUtil.getObjectAsLocale(pickDate.getLocale(facesContext));
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale viewRootLocale = viewRoot.getLocale();

		return !locale.equals(viewRootLocale);
	}

	@Override
	protected String[] getModules() {
		return DATE_PICKER_MODULES;
	}
}
