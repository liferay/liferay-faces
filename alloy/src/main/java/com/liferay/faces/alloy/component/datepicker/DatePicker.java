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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.alloy.util.AlloyConstants;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = "com.liferay.faces.alloy.component.datepicker.DatePicker")
public class DatePicker extends DatePickerBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.alloy.component.datepicker";
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.datepicker.DatePicker";

	// Private Constants
	private static final String DATE_CLICK = "dateClick";
	private static final String DEFAULT_ON_DATE_CLICK = DatePickerUtil.getResourceText("DefaultOnDateClick.js");
	private static final boolean LIFERAY_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();
	private static final String LIFERAY_Z_INDEX_TOOLTIP = "Liferay.zIndex.TOOLTIP";
	private static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.datepicker.DatePickerRenderer";
	private static final String TOKEN = "{0}";

	public DatePicker() {

		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public Object getzIndex() {

		String defaultValue = null;

		if (LIFERAY_DETECTED) {
			defaultValue = LIFERAY_Z_INDEX_TOOLTIP;
		}

		return getStateHelper().eval(Z_INDEX, defaultValue);
	}

	protected void appendAttribute(StringBuilder stringBuilder, String attributeName, String attributeValue,
		boolean first) {

		if (!first) {
			stringBuilder.append(StringPool.COMMA);
			stringBuilder.append(StringPool.NEW_LINE);
		}

		stringBuilder.append(attributeName);
		stringBuilder.append(StringPool.COLON);
		stringBuilder.append(attributeValue);
	}

	protected void appendEvent(StringBuilder stringBuilder, String eventType, String eventName, String eventFunction,
		boolean first) {

		if (!first) {
			stringBuilder.append(StringPool.COMMA);
			stringBuilder.append(StringPool.NEW_LINE);
		}

		stringBuilder.append(eventType);
		stringBuilder.append(StringPool.COLON);
		stringBuilder.append(StringPool.OPEN_CURLY_BRACE);
		stringBuilder.append(StringPool.NEW_LINE);
		stringBuilder.append(eventName);
		stringBuilder.append(StringPool.COLON);
		stringBuilder.append(AlloyConstants.FUNCTION_EVENT);
		stringBuilder.append(StringPool.SPACE);
		stringBuilder.append(StringPool.OPEN_CURLY_BRACE);
		stringBuilder.append(eventFunction);
		stringBuilder.append(StringPool.CLOSE_CURLY_BRACE);
		stringBuilder.append(StringPool.NEW_LINE);
		stringBuilder.append(StringPool.CLOSE_CURLY_BRACE);
		stringBuilder.append(StringPool.COMMA);
		stringBuilder.append(StringPool.NEW_LINE);
	}

	@Override
	public Object getCalendar() {

		Object calendar = super.getCalendar();

		if (calendar == null) {

			boolean first = true;
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(StringPool.OPEN_CURLY_BRACE);

			Object maximumDate = getMaximumDate();

			if (maximumDate != null) {

				Date maxDate = DatePickerUtil.getObjectAsDate(maximumDate, this);
				String maxDateString = DatePickerUtil.toJavascriptDateString(maxDate);
				appendAttribute(stringBuilder, MAXIMUM_DATE, maxDateString, first);
				first = false;
			}

			Object minimumDate = getMinimumDate();

			if (minimumDate != null) {

				Date minDate = DatePickerUtil.getObjectAsDate(minimumDate, this);
				String minDateString = DatePickerUtil.toJavascriptDateString(minDate);
				appendAttribute(stringBuilder, MINIMUM_DATE, minDateString, first);
				first = false;
			}

			String selectionMode = getSelectionMode();

			if (selectionMode != null) {

				selectionMode = StringPool.QUOTE + selectionMode + StringPool.QUOTE;
				appendAttribute(stringBuilder, SELECTION_MODE, selectionMode, first);
				first = false;
			}

			String afterDateClick = getAfterDateClick();

			if (afterDateClick != null) {

				appendEvent(stringBuilder, AlloyConstants.AFTER, DATE_CLICK, afterDateClick, first);
				first = false;
			}

			String onDateClick = getOnDateClick();

			if (onDateClick != null) {

				appendEvent(stringBuilder, AlloyConstants.ON, DATE_CLICK, onDateClick, first);
				first = false;
			}

			stringBuilder.append(StringPool.CLOSE_CURLY_BRACE);
			calendar = stringBuilder.toString();
		}

		return calendar;
	}

	@Override
	public String getDatePattern() {

		String datePattern = super.getDatePattern();

		if (datePattern == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			Locale locale = DatePickerUtil.determineLocale(facesContext, getLocale());

			// Note: the following usage of SimpleDateFormat is thread-safe, since only the result of toPattern is
			// needed.
			SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.SHORT,
					locale);
			datePattern = simpleDateFormat.toPattern();
		}

		return datePattern;
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public String getOnDateClick() {

		String onDateClick = super.getOnDateClick();

		if (onDateClick == null) {

			UIComponent uiComponent = (UIComponent) findComponent(getFor());

			if (uiComponent != null) {

				String escapedClientId = ComponentUtil.escapeClientId(uiComponent.getClientId());
				onDateClick = DEFAULT_ON_DATE_CLICK.replace(TOKEN, escapedClientId);
			}
		}

		return onDateClick;
	}

	@Override
	public Object getPopover() {

		Object popover = super.getPopover();

		if (popover == null) {

			Object zIndex = getzIndex();

			if (getzIndex() != null) {

				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(StringPool.OPEN_CURLY_BRACE);
				appendAttribute(stringBuilder, Z_INDEX, zIndex.toString(), true);
				stringBuilder.append(StringPool.CLOSE_CURLY_BRACE);
				popover = stringBuilder.toString();
			}
		}

		return popover;
	}
}
