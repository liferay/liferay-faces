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

import java.util.Date;

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
@FacesComponent(value = DatePicker.COMPONENT_TYPE)
public class DatePicker extends DatePickerBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.alloy.component.datepicker";
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.datepicker.DatePicker";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.datepicker.DatePickerRenderer";

	// Private Constants
	private static final String DATE_CLICK = "dateClick";
	private static final String DEFAULT_ON_DATE_CLICK_TEMPLATE =
		"datePickerDefaultOnDateClick(event.date, A.one('{0}'), this);";
	private static final boolean LIFERAY_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();
	private static final String LIFERAY_Z_INDEX_TOOLTIP = "Liferay.zIndex.TOOLTIP";
	private static final String TOKEN = "{0}";

	// Private Members
	private String onDateClick;

	public DatePicker() {

		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public Object getzIndex() {

		Object zIndex = super.getzIndex();

		if ((zIndex == null) && LIFERAY_DETECTED) {
			zIndex = LIFERAY_Z_INDEX_TOOLTIP;
		}

		return zIndex;
	}

	protected void appendJSONAttribute(StringBuilder stringBuilder, String attributeName, String attributeValue,
		boolean first) {

		if (!first) {
			stringBuilder.append(StringPool.COMMA);
			stringBuilder.append(StringPool.NEW_LINE);
		}

		stringBuilder.append(attributeName);
		stringBuilder.append(StringPool.COLON);
		stringBuilder.append(attributeValue);
	}

	protected void appendJSONEvent(StringBuilder stringBuilder, String eventType, String eventName,
		String eventFunction, boolean first) {

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

			// The calendar attribute value provides the opportunity to specify dateClick events, selectionMode,
			// minimumDate, maximumDate as key:value pairs via JSON syntax.
			// For example: "calendar: {selectionMode: 'multiple'}"
			boolean first = true;
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(StringPool.OPEN_CURLY_BRACE);

			String componentDatePattern = getDatePattern();
			String componetMask = getMask();
			Object maximumDate = getMaximumDate();

			if (maximumDate != null) {

				Date maxDate = DatePickerUtil.getObjectAsDate(maximumDate, componentDatePattern, componetMask);
				String maxDateString = DatePickerUtil.toJavascriptDateString(maxDate);
				appendJSONAttribute(stringBuilder, MAXIMUM_DATE, maxDateString, first);
				first = false;
			}

			Object minimumDate = getMinimumDate();

			if (minimumDate != null) {

				Date minDate = DatePickerUtil.getObjectAsDate(minimumDate, componentDatePattern, componetMask);
				String minDateString = DatePickerUtil.toJavascriptDateString(minDate);
				appendJSONAttribute(stringBuilder, MINIMUM_DATE, minDateString, first);
				first = false;
			}

			String selectionMode = getSelectionMode();

			if (selectionMode != null) {

				selectionMode = StringPool.QUOTE + selectionMode + StringPool.QUOTE;
				appendJSONAttribute(stringBuilder, SELECTION_MODE, selectionMode, first);
				first = false;
			}

			String onDateClickString = getOnDateClick();

			if (onDateClickString != null) {

				appendJSONEvent(stringBuilder, AlloyConstants.ON, DATE_CLICK, onDateClickString, first);
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

			// Provide a default datePattern based on the locale.
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Object locale = getLocale();
			datePattern = DatePickerUtil.getDefaultDatePattern(facesContext, locale);
		}

		return datePattern;
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public String getOnDateClick() {

		String onDateClickString = null;
		UIComponent parent = getParent();

		// If the parent is not null, then the component is in the component tree. So if the component is in the
		// component tree OR if onDateClick has no value, use the default value for onDateClick.
		if ((parent != null) || (onDateClick == null)) {
			String trigger = getTrigger();

			// If trigger is null, use for to determine the trigger.
			if (trigger == null) {

				String forId = getFor();
				UIComponent forComponent = (UIComponent) findComponent(forId);
				trigger = forId;

				// If there is a forComponent, the trigger is the "#" symbol followed by the forComponent's
				// escapedClientId.
				if (forComponent != null) {
					String forClientId = forComponent.getClientId();
					String escapedForClientId = ComponentUtil.escapeClientId(forClientId);
					trigger = StringPool.POUND + escapedForClientId;
				}
			}

			// The default value of the onDateClick attribute is a script that that is read from the
			// DefaultOnDateClick.js classpath resource. The script contains a "{0}" token that needs
			// to be substituted with the trigger, which is the "#" sign followed by escaped clientId of the trigger.
			String defaultOnDateClick = DEFAULT_ON_DATE_CLICK_TEMPLATE;
			defaultOnDateClick = defaultOnDateClick.replace(TOKEN, trigger);
			onDateClickString = defaultOnDateClick;
		}

		// Otherwise, if the parent is null, the component is not in the component tree. So it is threadsafe to access
		// private members.
		else if ((parent == null) && (onDateClick != null)) {
			onDateClickString = onDateClick;
		}

		return onDateClickString;
	}

	public void setOnDateClick(String onDateClick) {
		this.onDateClick = onDateClick;
	}

	@Override
	public Object getPopover() {

		Object popover = super.getPopover();

		if (popover == null) {

			Object zIndex = getzIndex();

			if (zIndex != null) {

				// The popover attribute value provides the opportunity to specify a zIndex key:value pair via JSON
				// syntax. For example: "popover: {zIndex: 1}"
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(StringPool.OPEN_CURLY_BRACE);
				appendJSONAttribute(stringBuilder, Z_INDEX, zIndex.toString(), true);
				stringBuilder.append(StringPool.CLOSE_CURLY_BRACE);
				popover = stringBuilder.toString();
			}
		}

		return popover;
	}

	@Override
	public String getPopoverCssClass() {

		String popoverCssClass = super.getPopoverCssClass();
		popoverCssClass = ComponentUtil.concatAllCssClasses(this, popoverCssClass);

		return popoverCssClass;
	}
}
