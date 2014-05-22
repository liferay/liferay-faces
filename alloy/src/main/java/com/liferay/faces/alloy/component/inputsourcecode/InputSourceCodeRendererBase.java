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

		InputSourceCodeAlloy InputSourceCodeAlloy = (InputSourceCodeAlloy) uiComponent;
		boolean first = true;

		String boundingBox = InputSourceCodeAlloy.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, InputSourceCodeAlloy, boundingBox, first);
			first = false;
		}

		String contentBox = InputSourceCodeAlloy.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, InputSourceCodeAlloy, contentBox, first);
			first = false;
		}

		Boolean disabled = InputSourceCodeAlloy.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, InputSourceCodeAlloy, disabled, first);
			first = false;
		}

		Object height = InputSourceCodeAlloy.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, InputSourceCodeAlloy, height, first);
			first = false;
		}

		Boolean highlightActiveLine = InputSourceCodeAlloy.isHighlightActiveLine();

		if (highlightActiveLine != null) {

			encodeHighlightActiveLine(responseWriter, InputSourceCodeAlloy, highlightActiveLine, first);
			first = false;
		}

		String locale = InputSourceCodeAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, InputSourceCodeAlloy, locale, first);
			first = false;
		}

		String mode = InputSourceCodeAlloy.getMode();

		if (mode != null) {

			encodeMode(responseWriter, InputSourceCodeAlloy, mode, first);
			first = false;
		}

		Boolean readOnly = InputSourceCodeAlloy.isReadOnly();

		if (readOnly != null) {

			encodeReadOnly(responseWriter, InputSourceCodeAlloy, readOnly, first);
			first = false;
		}

		Boolean showPrintMargin = InputSourceCodeAlloy.isShowPrintMargin();

		if (showPrintMargin != null) {

			encodeShowPrintMargin(responseWriter, InputSourceCodeAlloy, showPrintMargin, first);
			first = false;
		}

		String srcNode = InputSourceCodeAlloy.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, InputSourceCodeAlloy, srcNode, first);
			first = false;
		}

		Object strings = InputSourceCodeAlloy.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, InputSourceCodeAlloy, strings, first);
			first = false;
		}

		Object tabIndex = InputSourceCodeAlloy.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, InputSourceCodeAlloy, tabIndex, first);
			first = false;
		}

		Object tabSize = InputSourceCodeAlloy.getTabSize();

		if (tabSize != null) {

			encodeTabSize(responseWriter, InputSourceCodeAlloy, tabSize, first);
			first = false;
		}

		Boolean useSoftTabs = InputSourceCodeAlloy.isUseSoftTabs();

		if (useSoftTabs != null) {

			encodeUseSoftTabs(responseWriter, InputSourceCodeAlloy, useSoftTabs, first);
			first = false;
		}

		Boolean useWrapMode = InputSourceCodeAlloy.isUseWrapMode();

		if (useWrapMode != null) {

			encodeUseWrapMode(responseWriter, InputSourceCodeAlloy, useWrapMode, first);
			first = false;
		}

		Object value = InputSourceCodeAlloy.getValue();

		if (value != null) {

			encodeValue(responseWriter, InputSourceCodeAlloy, value, first);
			first = false;
		}

		Boolean visible = InputSourceCodeAlloy.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, InputSourceCodeAlloy, visible, first);
			first = false;
		}

		String widgetId = InputSourceCodeAlloy.getWidgetId();

		if (widgetId != null) {

			encodeWidgetId(responseWriter, InputSourceCodeAlloy, widgetId, first);
			first = false;
		}

		Boolean widgetRender = InputSourceCodeAlloy.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, InputSourceCodeAlloy, widgetRender, first);
			first = false;
		}

		Object width = InputSourceCodeAlloy.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, InputSourceCodeAlloy, width, first);
			first = false;
		}

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterBoundingBoxChange = InputSourceCodeAlloy.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, InputSourceCodeAlloy, afterBoundingBoxChange, first);
			first = false;
		}

		String afterContentBoxChange = InputSourceCodeAlloy.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, InputSourceCodeAlloy, afterContentBoxChange, first);
			first = false;
		}

		String afterDestroyedChange = InputSourceCodeAlloy.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, InputSourceCodeAlloy, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = InputSourceCodeAlloy.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, InputSourceCodeAlloy, afterDisabledChange, first);
			first = false;
		}

		String afterFocusedChange = InputSourceCodeAlloy.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, InputSourceCodeAlloy, afterFocusedChange, first);
			first = false;
		}

		String afterHeightChange = InputSourceCodeAlloy.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, InputSourceCodeAlloy, afterHeightChange, first);
			first = false;
		}

		String afterHighlightActiveLineChange = InputSourceCodeAlloy.getAfterHighlightActiveLineChange();

		if (afterHighlightActiveLineChange != null) {

			encodeAfterHighlightActiveLineChange(responseWriter, InputSourceCodeAlloy, afterHighlightActiveLineChange, first);
			first = false;
		}

		String afterIdChange = InputSourceCodeAlloy.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, InputSourceCodeAlloy, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = InputSourceCodeAlloy.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, InputSourceCodeAlloy, afterInitializedChange, first);
			first = false;
		}

		String afterLocaleChange = InputSourceCodeAlloy.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, InputSourceCodeAlloy, afterLocaleChange, first);
			first = false;
		}

		String afterModeChange = InputSourceCodeAlloy.getAfterModeChange();

		if (afterModeChange != null) {

			encodeAfterModeChange(responseWriter, InputSourceCodeAlloy, afterModeChange, first);
			first = false;
		}

		String afterReadOnlyChange = InputSourceCodeAlloy.getAfterReadOnlyChange();

		if (afterReadOnlyChange != null) {

			encodeAfterReadOnlyChange(responseWriter, InputSourceCodeAlloy, afterReadOnlyChange, first);
			first = false;
		}

		String afterRenderChange = InputSourceCodeAlloy.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, InputSourceCodeAlloy, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = InputSourceCodeAlloy.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, InputSourceCodeAlloy, afterRenderedChange, first);
			first = false;
		}

		String afterShowPrintMarginChange = InputSourceCodeAlloy.getAfterShowPrintMarginChange();

		if (afterShowPrintMarginChange != null) {

			encodeAfterShowPrintMarginChange(responseWriter, InputSourceCodeAlloy, afterShowPrintMarginChange, first);
			first = false;
		}

		String afterSrcNodeChange = InputSourceCodeAlloy.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, InputSourceCodeAlloy, afterSrcNodeChange, first);
			first = false;
		}

		String afterStringsChange = InputSourceCodeAlloy.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, InputSourceCodeAlloy, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = InputSourceCodeAlloy.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, InputSourceCodeAlloy, afterTabIndexChange, first);
			first = false;
		}

		String afterTabSizeChange = InputSourceCodeAlloy.getAfterTabSizeChange();

		if (afterTabSizeChange != null) {

			encodeAfterTabSizeChange(responseWriter, InputSourceCodeAlloy, afterTabSizeChange, first);
			first = false;
		}

		String afterUseSoftTabsChange = InputSourceCodeAlloy.getAfterUseSoftTabsChange();

		if (afterUseSoftTabsChange != null) {

			encodeAfterUseSoftTabsChange(responseWriter, InputSourceCodeAlloy, afterUseSoftTabsChange, first);
			first = false;
		}

		String afterUseWrapModeChange = InputSourceCodeAlloy.getAfterUseWrapModeChange();

		if (afterUseWrapModeChange != null) {

			encodeAfterUseWrapModeChange(responseWriter, InputSourceCodeAlloy, afterUseWrapModeChange, first);
			first = false;
		}

		String afterValueChange = InputSourceCodeAlloy.getAfterValueChange();

		if (afterValueChange != null) {

			encodeAfterValueChange(responseWriter, InputSourceCodeAlloy, afterValueChange, first);
			first = false;
		}

		String afterVisibleChange = InputSourceCodeAlloy.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, InputSourceCodeAlloy, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = InputSourceCodeAlloy.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, InputSourceCodeAlloy, afterWidthChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onBoundingBoxChange = InputSourceCodeAlloy.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, InputSourceCodeAlloy, onBoundingBoxChange, first);
			first = false;
		}

		String onContentBoxChange = InputSourceCodeAlloy.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, InputSourceCodeAlloy, onContentBoxChange, first);
			first = false;
		}

		String onDestroyedChange = InputSourceCodeAlloy.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, InputSourceCodeAlloy, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = InputSourceCodeAlloy.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, InputSourceCodeAlloy, onDisabledChange, first);
			first = false;
		}

		String onFocusedChange = InputSourceCodeAlloy.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, InputSourceCodeAlloy, onFocusedChange, first);
			first = false;
		}

		String onHeightChange = InputSourceCodeAlloy.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, InputSourceCodeAlloy, onHeightChange, first);
			first = false;
		}

		String onHighlightActiveLineChange = InputSourceCodeAlloy.getOnHighlightActiveLineChange();

		if (onHighlightActiveLineChange != null) {

			encodeOnHighlightActiveLineChange(responseWriter, InputSourceCodeAlloy, onHighlightActiveLineChange, first);
			first = false;
		}

		String onIdChange = InputSourceCodeAlloy.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, InputSourceCodeAlloy, onIdChange, first);
			first = false;
		}

		String onInitializedChange = InputSourceCodeAlloy.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, InputSourceCodeAlloy, onInitializedChange, first);
			first = false;
		}

		String onLocaleChange = InputSourceCodeAlloy.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, InputSourceCodeAlloy, onLocaleChange, first);
			first = false;
		}

		String onModeChange = InputSourceCodeAlloy.getOnModeChange();

		if (onModeChange != null) {

			encodeOnModeChange(responseWriter, InputSourceCodeAlloy, onModeChange, first);
			first = false;
		}

		String onReadOnlyChange = InputSourceCodeAlloy.getOnReadOnlyChange();

		if (onReadOnlyChange != null) {

			encodeOnReadOnlyChange(responseWriter, InputSourceCodeAlloy, onReadOnlyChange, first);
			first = false;
		}

		String onRenderChange = InputSourceCodeAlloy.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, InputSourceCodeAlloy, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = InputSourceCodeAlloy.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, InputSourceCodeAlloy, onRenderedChange, first);
			first = false;
		}

		String onShowPrintMarginChange = InputSourceCodeAlloy.getOnShowPrintMarginChange();

		if (onShowPrintMarginChange != null) {

			encodeOnShowPrintMarginChange(responseWriter, InputSourceCodeAlloy, onShowPrintMarginChange, first);
			first = false;
		}

		String onSrcNodeChange = InputSourceCodeAlloy.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, InputSourceCodeAlloy, onSrcNodeChange, first);
			first = false;
		}

		String onStringsChange = InputSourceCodeAlloy.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, InputSourceCodeAlloy, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = InputSourceCodeAlloy.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, InputSourceCodeAlloy, onTabIndexChange, first);
			first = false;
		}

		String onTabSizeChange = InputSourceCodeAlloy.getOnTabSizeChange();

		if (onTabSizeChange != null) {

			encodeOnTabSizeChange(responseWriter, InputSourceCodeAlloy, onTabSizeChange, first);
			first = false;
		}

		String onUseSoftTabsChange = InputSourceCodeAlloy.getOnUseSoftTabsChange();

		if (onUseSoftTabsChange != null) {

			encodeOnUseSoftTabsChange(responseWriter, InputSourceCodeAlloy, onUseSoftTabsChange, first);
			first = false;
		}

		String onUseWrapModeChange = InputSourceCodeAlloy.getOnUseWrapModeChange();

		if (onUseWrapModeChange != null) {

			encodeOnUseWrapModeChange(responseWriter, InputSourceCodeAlloy, onUseWrapModeChange, first);
			first = false;
		}

		String onValueChange = InputSourceCodeAlloy.getOnValueChange();

		if (onValueChange != null) {

			encodeOnValueChange(responseWriter, InputSourceCodeAlloy, onValueChange, first);
			first = false;
		}

		String onVisibleChange = InputSourceCodeAlloy.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, InputSourceCodeAlloy, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = InputSourceCodeAlloy.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, InputSourceCodeAlloy, onWidthChange, first);
			first = false;
		}

		// End encoding "on" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterHighlightActiveLineChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterHighlightActiveLineChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIGHLIGHT_ACTIVE_LINE_CHANGE, afterHighlightActiveLineChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterModeChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MODE_CHANGE, afterModeChange, first);
	}

	protected void encodeAfterReadOnlyChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterReadOnlyChange, boolean first) throws IOException {
		encodeEvent(responseWriter, READ_ONLY_CHANGE, afterReadOnlyChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterShowPrintMarginChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterShowPrintMarginChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_PRINT_MARGIN_CHANGE, afterShowPrintMarginChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterTabSizeChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterTabSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_SIZE_CHANGE, afterTabSizeChange, first);
	}

	protected void encodeAfterUseSoftTabsChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterUseSoftTabsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_SOFT_TABS_CHANGE, afterUseSoftTabsChange, first);
	}

	protected void encodeAfterUseWrapModeChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterUseWrapModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_WRAP_MODE_CHANGE, afterUseWrapModeChange, first);
	}

	protected void encodeAfterValueChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, afterValueChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.CONTENT_BOX, contentBox, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.DISABLED, disabled, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Object height, boolean first) throws IOException {
		encodeNumber(responseWriter, InputSourceCodeAlloy.HEIGHT, height, first);
	}

	protected void encodeHighlightActiveLine(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Boolean highlightActiveLine, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.HIGHLIGHT_ACTIVE_LINE, highlightActiveLine, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String locale, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.LOCALE, locale, first);
	}

	protected void encodeMode(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String mode, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.MODE, mode, first);
	}

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnHighlightActiveLineChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onHighlightActiveLineChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIGHLIGHT_ACTIVE_LINE_CHANGE, onHighlightActiveLineChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnModeChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MODE_CHANGE, onModeChange, first);
	}

	protected void encodeOnReadOnlyChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onReadOnlyChange, boolean first) throws IOException {
		encodeEvent(responseWriter, READ_ONLY_CHANGE, onReadOnlyChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnShowPrintMarginChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onShowPrintMarginChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_PRINT_MARGIN_CHANGE, onShowPrintMarginChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnTabSizeChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onTabSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_SIZE_CHANGE, onTabSizeChange, first);
	}

	protected void encodeOnUseSoftTabsChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onUseSoftTabsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_SOFT_TABS_CHANGE, onUseSoftTabsChange, first);
	}

	protected void encodeOnUseWrapModeChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onUseWrapModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_WRAP_MODE_CHANGE, onUseWrapModeChange, first);
	}

	protected void encodeOnValueChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, onValueChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
	}

	protected void encodeReadOnly(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Boolean readOnly, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.READ_ONLY, readOnly, first);
	}

	protected void encodeShowPrintMargin(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Boolean showPrintMargin, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.SHOW_PRINT_MARGIN, showPrintMargin, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.SRC_NODE, srcNode, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, InputSourceCodeAlloy.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, InputSourceCodeAlloy.TAB_INDEX, tabIndex, first);
	}

	protected void encodeTabSize(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Object tabSize, boolean first) throws IOException {
		encodeNumber(responseWriter, InputSourceCodeAlloy.TAB_SIZE, tabSize, first);
	}

	protected void encodeUseSoftTabs(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Boolean useSoftTabs, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.USE_SOFT_TABS, useSoftTabs, first);
	}

	protected void encodeUseWrapMode(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Boolean useWrapMode, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.USE_WRAP_MODE, useWrapMode, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Object value, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.VALUE, value, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.VISIBLE, visible, first);
	}

	protected void encodeWidgetId(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, String widgetId, boolean first) throws IOException {
		encodeString(responseWriter, InputSourceCodeAlloy.WIDGET_ID, widgetId, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, InputSourceCodeAlloy.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, InputSourceCodeAlloy InputSourceCodeAlloy, Object width, boolean first) throws IOException {
		encodeNumber(responseWriter, InputSourceCodeAlloy.WIDTH, width, first);
	}
}
//J+
