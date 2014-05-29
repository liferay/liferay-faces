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
package com.liferay.faces.alloy.component.selectstarrating;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.DelegatingAlloyRendererBase;
import com.liferay.faces.alloy.util.AlloyConstants;
import com.liferay.faces.util.lang.StringPool;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class SelectStarRatingRendererBase extends DelegatingAlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "SelectStarRating";
	private static final String ALLOY_MODULE_NAME = "aui-rating";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		SelectStarRatingAlloy selectStarRatingAlloy = (SelectStarRatingAlloy) uiComponent;
		boolean first = true;

		String boundingBox = selectStarRatingAlloy.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, selectStarRatingAlloy, boundingBox, first);
			first = false;
		}

		Boolean canReset = selectStarRatingAlloy.isCanReset();

		if (canReset != null) {

			encodeCanReset(responseWriter, selectStarRatingAlloy, canReset, first);
			first = false;
		}

		String contentBox = selectStarRatingAlloy.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, selectStarRatingAlloy, contentBox, first);
			first = false;
		}

		String cssClass = selectStarRatingAlloy.getCssClass();

		if (cssClass != null) {

			encodeCssClass(responseWriter, selectStarRatingAlloy, cssClass, first);
			first = false;
		}

		Object cssClasses = selectStarRatingAlloy.getCssClasses();

		if (cssClasses != null) {

			encodeCssClasses(responseWriter, selectStarRatingAlloy, cssClasses, first);
			first = false;
		}

		Object defaultSelected = selectStarRatingAlloy.getDefaultSelected();

		if (defaultSelected != null) {

			encodeDefaultSelected(responseWriter, selectStarRatingAlloy, defaultSelected, first);
			first = false;
		}

		Boolean disabled = selectStarRatingAlloy.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, selectStarRatingAlloy, disabled, first);
			first = false;
		}

		Object height = selectStarRatingAlloy.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, selectStarRatingAlloy, height, first);
			first = false;
		}

		String hiddenInput = selectStarRatingAlloy.getHiddenInput();

		if (hiddenInput != null) {

			encodeHiddenInput(responseWriter, selectStarRatingAlloy, hiddenInput, first);
			first = false;
		}

		String hideClass = selectStarRatingAlloy.getHideClass();

		if (hideClass != null) {

			encodeHideClass(responseWriter, selectStarRatingAlloy, hideClass, first);
			first = false;
		}

		String inputName = selectStarRatingAlloy.getInputName();

		if (inputName != null) {

			encodeInputName(responseWriter, selectStarRatingAlloy, inputName, first);
			first = false;
		}

		String label = selectStarRatingAlloy.getLabel();

		if (label != null) {

			encodeLabel(responseWriter, selectStarRatingAlloy, label, first);
			first = false;
		}

		String labelNode = selectStarRatingAlloy.getLabelNode();

		if (labelNode != null) {

			encodeLabelNode(responseWriter, selectStarRatingAlloy, labelNode, first);
			first = false;
		}

		String locale = selectStarRatingAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, selectStarRatingAlloy, locale, first);
			first = false;
		}

		Object selectedIndex = selectStarRatingAlloy.getSelectedIndex();

		if (selectedIndex != null) {

			encodeSelectedIndex(responseWriter, selectStarRatingAlloy, selectedIndex, first);
			first = false;
		}

		Boolean showTitle = selectStarRatingAlloy.isShowTitle();

		if (showTitle != null) {

			encodeShowTitle(responseWriter, selectStarRatingAlloy, showTitle, first);
			first = false;
		}

		Object size = selectStarRatingAlloy.getSize();

		if (size != null) {

			encodeSize(responseWriter, selectStarRatingAlloy, size, first);
			first = false;
		}

		String srcNode = selectStarRatingAlloy.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, selectStarRatingAlloy, srcNode, first);
			first = false;
		}

		Object strings = selectStarRatingAlloy.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, selectStarRatingAlloy, strings, first);
			first = false;
		}

		Object tabIndex = selectStarRatingAlloy.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, selectStarRatingAlloy, tabIndex, first);
			first = false;
		}

		String title = selectStarRatingAlloy.getTitle();

		if (title != null) {

			encodeTitle(responseWriter, selectStarRatingAlloy, title, first);
			first = false;
		}

		Boolean useARIA = selectStarRatingAlloy.isUseARIA();

		if (useARIA != null) {

			encodeUseARIA(responseWriter, selectStarRatingAlloy, useARIA, first);
			first = false;
		}

		Object value = selectStarRatingAlloy.getValue();

		if (value != null) {

			encodeValue(responseWriter, selectStarRatingAlloy, value, first);
			first = false;
		}

		Boolean visible = selectStarRatingAlloy.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, selectStarRatingAlloy, visible, first);
			first = false;
		}

		String widgetId = selectStarRatingAlloy.getWidgetId();

		if (widgetId != null) {

			encodeWidgetId(responseWriter, selectStarRatingAlloy, widgetId, first);
			first = false;
		}

		Boolean widgetRender = selectStarRatingAlloy.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, selectStarRatingAlloy, widgetRender, first);
			first = false;
		}

		Object width = selectStarRatingAlloy.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, selectStarRatingAlloy, width, first);
			first = false;
		}
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeCanReset(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean canReset, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.CAN_RESET, canReset, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.CONTENT_BOX, contentBox, first);
	}

	protected void encodeCssClass(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String cssClass, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.CSS_CLASS, cssClass, first);
	}

	protected void encodeCssClasses(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object cssClasses, boolean first) throws IOException {
		encodeObject(responseWriter, SelectStarRatingAlloy.CSS_CLASSES, cssClasses, first);
	}

	protected void encodeDefaultSelected(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object defaultSelected, boolean first) throws IOException {
		encodeNumber(responseWriter, SelectStarRatingAlloy.DEFAULT_SELECTED, defaultSelected, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.DISABLED, disabled, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object height, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, SelectStarRatingAlloy.HEIGHT, height, first);
	}

	protected void encodeHiddenInput(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String hiddenInput, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.HIDDEN_INPUT, hiddenInput, first);
	}

	protected void encodeHideClass(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String hideClass, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.HIDE_CLASS, hideClass, first);
	}

	protected void encodeInputName(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String inputName, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.INPUT_NAME, inputName, first);
	}

	protected void encodeLabel(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String label, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.LABEL, label, first);
	}

	protected void encodeLabelNode(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String labelNode, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.LABEL_NODE, labelNode, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String locale, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.LOCALE, locale, first);
	}

	protected void encodeSelectedIndex(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object selectedIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, SelectStarRatingAlloy.SELECTED_INDEX, selectedIndex, first);
	}

	protected void encodeShowTitle(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean showTitle, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.SHOW_TITLE, showTitle, first);
	}

	protected void encodeSize(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object size, boolean first) throws IOException {
		encodeNumber(responseWriter, SelectStarRatingAlloy.SIZE, size, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.SRC_NODE, srcNode, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, SelectStarRatingAlloy.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, SelectStarRatingAlloy.TAB_INDEX, tabIndex, first);
	}

	protected void encodeTitle(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String title, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.TITLE, title, first);
	}

	protected void encodeUseARIA(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean useARIA, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.USE_ARIA, useARIA, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object value, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.VALUE, value, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.VISIBLE, visible, first);
	}

	protected void encodeWidgetId(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String widgetId, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.WIDGET_ID, widgetId, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object width, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, SelectStarRatingAlloy.WIDTH, width, first);
	}
}
//J+
