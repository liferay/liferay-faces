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

import java.io.IOException;
import java.util.Locale;

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
@FacesRenderer(
	componentFamily = DatePicker.COMPONENT_FAMILY,
	rendererType = "com.liferay.faces.alloy.component.datepicker.DatePickerRenderer"
)
public class DatePickerRenderer extends DatePickerRendererBase {

	// Private Constants
	private static final String LANG = "lang";
	private static final String NODE_EVENT_SIMULATE = "node-event-simulate";
	private static final String[] DATE_PICKER_MODULES = StringHelper.append(MODULES, NODE_EVENT_SIMULATE);

	@Override
	protected void encodeAfterDateClick(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy,
		String afterDateClick, boolean first) throws IOException {
		// This is a no-op because the dateClick event belongs to the internal calendar rather than the datePicker
		// itself, so it should not be rendered like a normal attribute. Instead, afterDateClick is handled by
		// DatePicker.getCalendar().
	}

	@Override
	protected void encodeDatePattern(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy,
		String datePattern, boolean first) throws IOException {

		// The mask attribute takes precedence.
		if (datePickerAlloy.getMask() == null) {

			String datePatternMask = DatePickerUtil.getMaskFromDatePattern(datePattern);
			encodeMask(responseWriter, datePickerAlloy, datePatternMask, first);
		}
	}

	@Override
	protected void encodeFor(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String for_,
		boolean first) throws IOException {

		// The trigger attribute takes precedence.
		if (datePickerAlloy.getTrigger() == null) {

			UIComponent datePicker = (UIComponent) datePickerAlloy;
			UIComponent forComponent = datePicker.findComponent(for_);
			String forComponentClientId = ComponentUtil.escapeClientId(forComponent.getClientId());
			String forNode = StringPool.POUND + forComponentClientId;

			encodeTrigger(responseWriter, datePickerAlloy, forNode, first);
		}
	}

	@Override
	protected void encodeLang(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {

		DatePickerAlloy datePickerAlloy = (DatePickerAlloy) uiComponent;
		Locale locale = DatePickerUtil.determineLocale(facesContext, datePickerAlloy.getLocale());

		// RFC 1766 requires the subtags of locales to be delimited by hyphens rather than underscores.
		// http://www.faqs.org/rfcs/rfc1766.html
		String localeString = locale.toString().replaceAll(StringPool.UNDERLINE, StringPool.DASH);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		encodeString(responseWriter, LANG, localeString, true);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected void encodeLocale(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object locale,
		boolean first) throws IOException {
		// This is a no-op because locale is not an attribute of the datePicker. Instead, locale is used in the
		// DatePickerUtil.determineLocale(FacesContext facesContext, Object objectLocale) method to determine date
		// formats and the lang attribute.
	}

	@Override
	protected void encodeMaximumDate(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy,
		Object maximumDate, boolean first) throws IOException {
		// This is a no-op because maximumDate belongs to the internal calendar rather than the datePicker itself, so it
		// should not be rendered like a normal attribute. Instead, maximumDate is handled by DatePicker.getCalendar().
	}

	@Override
	protected void encodeMinimumDate(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy,
		Object minimumDate, boolean first) throws IOException {
		// This is a no-op because minimumDate belongs to the internal calendar rather than the datePicker itself, so it
		// should not be rendered like a normal attribute. Instead, minimumDate is handled by DatePicker.getCalendar().
	}

	@Override
	protected void encodeOnDateClick(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy,
		String onDateClick, boolean first) throws IOException {
		// This is a no-op because the dateClick event belongs to the internal calendar rather than the datePicker
		// itself, so it should not be rendered like a normal attribute. Instead, onDateClick is handled by
		// DatePicker.getCalendar().
	}

	@Override
	protected void encodeSelectionMode(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy,
		String selectionMode, boolean first) throws IOException {
		// This is a no-op because selectionMode belongs to the internal calendar rather than the datePicker itself, so
		// it should not be rendered like a normal attribute. Instead, selectionMode is handled by
		// DatePicker.getCalendar().
	}

	@Override
	protected void encodeZIndex(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object zIndex,
		boolean first) throws IOException {
		// This is a no-op because zIndex belongs to the internal popover rather than the datePicker itself, so it
		// should not be rendered like a normal attribute. Instead, zIndex is handled by DatePicker.getPopover().
	}

	@Override
	protected boolean isForceInline(FacesContext facesContext, UIComponent uiComponent) {

		// In order to support the lang attribute of the YUI object, it is neccessary to determine if the user has
		// specified a locale other than that of the server or view root. If so, then the javascript must be rendered
		// inline.
		DatePickerAlloy datePickerAlloy = (DatePickerAlloy) uiComponent;
		Locale locale = DatePickerUtil.determineLocale(facesContext, datePickerAlloy.getLocale());
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale viewRootLocale = viewRoot.getLocale();

		return !locale.equals(viewRootLocale);
	}

	@Override
	protected String[] getModules() {
		return DATE_PICKER_MODULES;
	}
}
