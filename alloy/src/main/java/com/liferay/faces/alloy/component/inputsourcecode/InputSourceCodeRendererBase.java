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
package com.liferay.faces.alloy.component.inputsourcecode;
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
public abstract class InputSourceCodeRendererBase extends DelegatingAlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "InputSourceCode";
	private static final String ALLOY_MODULE_NAME = "aui-ace-editor";
	private static final String BOUNDING_BOX_CHANGE = "boundingBoxChange";
	private static final String CONTENT_BOX_CHANGE = "contentBoxChange";
	private static final String DESTROYED_CHANGE = "destroyedChange";
	private static final String DISABLED_CHANGE = "disabledChange";
	private static final String FOCUSED_CHANGE = "focusedChange";
	private static final String HEIGHT_CHANGE = "heightChange";
	private static final String HIGHLIGHT_ACTIVE_LINE_CHANGE = "highlightActiveLineChange";
	private static final String ID_CHANGE = "idChange";
	private static final String INITIALIZED_CHANGE = "initializedChange";
	private static final String LOCALE_CHANGE = "localeChange";
	private static final String MODE_CHANGE = "modeChange";
	private static final String READ_ONLY_CHANGE = "readOnlyChange";
	private static final String RENDER_CHANGE = "renderChange";
	private static final String RENDERED_CHANGE = "renderedChange";
	private static final String SHOW_PRINT_MARGIN_CHANGE = "showPrintMarginChange";
	private static final String SRC_NODE_CHANGE = "srcNodeChange";
	private static final String STRINGS_CHANGE = "stringsChange";
	private static final String TAB_INDEX_CHANGE = "tabIndexChange";
	private static final String TAB_SIZE_CHANGE = "tabSizeChange";
	private static final String USE_SOFT_TABS_CHANGE = "useSoftTabsChange";
	private static final String USE_WRAP_MODE_CHANGE = "useWrapModeChange";
	private static final String VALUE_CHANGE = "valueChange";
	private static final String VISIBLE_CHANGE = "visibleChange";
	private static final String WIDTH_CHANGE = "widthChange";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		InputSourceCodeAlloy inputSourceCodeAlloy = (InputSourceCodeAlloy) uiComponent;
		boolean first = true;

		String boundingBox = inputSourceCodeAlloy.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, inputSourceCodeAlloy, boundingBox, first);
			first = false;
		}

		String contentBox = inputSourceCodeAlloy.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, inputSourceCodeAlloy, contentBox, first);
			first = false;
		}

		Boolean disabled = inputSourceCodeAlloy.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, inputSourceCodeAlloy, disabled, first);
			first = false;
		}

		Object height = inputSourceCodeAlloy.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, inputSourceCodeAlloy, height, first);
			first = false;
		}

		Boolean highlightActiveLine = inputSourceCodeAlloy.isHighlightActiveLine();

		if (highlightActiveLine != null) {

			encodeHighlightActiveLine(responseWriter, inputSourceCodeAlloy, highlightActiveLine, first);
			first = false;
		}

		String locale = inputSourceCodeAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, inputSourceCodeAlloy, locale, first);
			first = false;
		}

		String mode = inputSourceCodeAlloy.getMode();

		if (mode != null) {

			encodeMode(responseWriter, inputSourceCodeAlloy, mode, first);
			first = false;
		}

		Boolean readOnly = inputSourceCodeAlloy.isReadOnly();

		if (readOnly != null) {

			encodeReadOnly(responseWriter, inputSourceCodeAlloy, readOnly, first);
			first = false;
		}

		Boolean showPrintMargin = inputSourceCodeAlloy.isShowPrintMargin();

		if (showPrintMargin != null) {

			encodeShowPrintMargin(responseWriter, inputSourceCodeAlloy, showPrintMargin, first);
			first = false;
		}

		String srcNode = inputSourceCodeAlloy.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, inputSourceCodeAlloy, srcNode, first);
			first = false;
		}

		Object strings = inputSourceCodeAlloy.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, inputSourceCodeAlloy, strings, first);
			first = false;
		}

		Object tabIndex = inputSourceCodeAlloy.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, inputSourceCodeAlloy, tabIndex, first);
			first = false;
		}

		Object tabSize = inputSourceCodeAlloy.getTabSize();

		if (tabSize != null) {

			encodeTabSize(responseWriter, inputSourceCodeAlloy, tabSize, first);
			first = false;
		}

		Boolean useSoftTabs = inputSourceCodeAlloy.isUseSoftTabs();

		if (useSoftTabs != null) {

			encodeUseSoftTabs(responseWriter, inputSourceCodeAlloy, useSoftTabs, first);
			first = false;
		}

		Boolean useWrapMode = inputSourceCodeAlloy.isUseWrapMode();

		if (useWrapMode != null) {

			encodeUseWrapMode(responseWriter, inputSourceCodeAlloy, useWrapMode, first);
			first = false;
		}

		Object value = inputSourceCodeAlloy.getValue();

		if (value != null) {

			encodeValue(responseWriter, inputSourceCodeAlloy, value, first);
			first = false;
		}

		Boolean visible = inputSourceCodeAlloy.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, inputSourceCodeAlloy, visible, first);
			first = false;
		}

		String widgetId = inputSourceCodeAlloy.getWidgetId();

		if (widgetId != null) {

			encodeWidgetId(responseWriter, inputSourceCodeAlloy, widgetId, first);
			first = false;
		}

		Boolean widgetRender = inputSourceCodeAlloy.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, inputSourceCodeAlloy, widgetRender, first);
			first = false;
		}

		Object width = inputSourceCodeAlloy.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, inputSourceCodeAlloy, width, first);
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

	protected void encodeBoundingBox(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.CONTENT_BOX, contentBox, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.DISABLED, disabled, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Object height, boolean first) throws IOException {
		encodeNumber(responseWriter, InputSourceCodeAlloy.HEIGHT, height, first);
	}

	protected void encodeHighlightActiveLine(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Boolean highlightActiveLine, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.HIGHLIGHT_ACTIVE_LINE, highlightActiveLine, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String locale, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.LOCALE, locale, first);
	}

	protected void encodeMode(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String mode, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.MODE, mode, first);
	}

	protected void encodeReadOnly(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Boolean readOnly, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.READ_ONLY, readOnly, first);
	}

	protected void encodeShowPrintMargin(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Boolean showPrintMargin, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.SHOW_PRINT_MARGIN, showPrintMargin, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.SRC_NODE, srcNode, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, InputSourceCodeAlloy.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, InputSourceCodeAlloy.TAB_INDEX, tabIndex, first);
	}

	protected void encodeTabSize(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Object tabSize, boolean first) throws IOException {
		encodeNumber(responseWriter, InputSourceCodeAlloy.TAB_SIZE, tabSize, first);
	}

	protected void encodeUseSoftTabs(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Boolean useSoftTabs, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.USE_SOFT_TABS, useSoftTabs, first);
	}

	protected void encodeUseWrapMode(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Boolean useWrapMode, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.USE_WRAP_MODE, useWrapMode, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Object value, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.VALUE, value, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.VISIBLE, visible, first);
	}

	protected void encodeWidgetId(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String widgetId, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.WIDGET_ID, widgetId, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, Object width, boolean first) throws IOException {
		encodeNumber(responseWriter, InputSourceCodeAlloy.WIDTH, width, first);
	}
}
//J+
