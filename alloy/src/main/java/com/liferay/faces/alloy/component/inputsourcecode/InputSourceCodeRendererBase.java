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

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterBoundingBoxChange = inputSourceCodeAlloy.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, inputSourceCodeAlloy, afterBoundingBoxChange, first);
			first = false;
		}

		String afterContentBoxChange = inputSourceCodeAlloy.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, inputSourceCodeAlloy, afterContentBoxChange, first);
			first = false;
		}

		String afterDestroyedChange = inputSourceCodeAlloy.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, inputSourceCodeAlloy, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = inputSourceCodeAlloy.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, inputSourceCodeAlloy, afterDisabledChange, first);
			first = false;
		}

		String afterFocusedChange = inputSourceCodeAlloy.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, inputSourceCodeAlloy, afterFocusedChange, first);
			first = false;
		}

		String afterHeightChange = inputSourceCodeAlloy.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, inputSourceCodeAlloy, afterHeightChange, first);
			first = false;
		}

		String afterHighlightActiveLineChange = inputSourceCodeAlloy.getAfterHighlightActiveLineChange();

		if (afterHighlightActiveLineChange != null) {

			encodeAfterHighlightActiveLineChange(responseWriter, inputSourceCodeAlloy, afterHighlightActiveLineChange, first);
			first = false;
		}

		String afterIdChange = inputSourceCodeAlloy.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, inputSourceCodeAlloy, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = inputSourceCodeAlloy.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, inputSourceCodeAlloy, afterInitializedChange, first);
			first = false;
		}

		String afterLocaleChange = inputSourceCodeAlloy.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, inputSourceCodeAlloy, afterLocaleChange, first);
			first = false;
		}

		String afterModeChange = inputSourceCodeAlloy.getAfterModeChange();

		if (afterModeChange != null) {

			encodeAfterModeChange(responseWriter, inputSourceCodeAlloy, afterModeChange, first);
			first = false;
		}

		String afterReadOnlyChange = inputSourceCodeAlloy.getAfterReadOnlyChange();

		if (afterReadOnlyChange != null) {

			encodeAfterReadOnlyChange(responseWriter, inputSourceCodeAlloy, afterReadOnlyChange, first);
			first = false;
		}

		String afterRenderChange = inputSourceCodeAlloy.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, inputSourceCodeAlloy, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = inputSourceCodeAlloy.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, inputSourceCodeAlloy, afterRenderedChange, first);
			first = false;
		}

		String afterShowPrintMarginChange = inputSourceCodeAlloy.getAfterShowPrintMarginChange();

		if (afterShowPrintMarginChange != null) {

			encodeAfterShowPrintMarginChange(responseWriter, inputSourceCodeAlloy, afterShowPrintMarginChange, first);
			first = false;
		}

		String afterSrcNodeChange = inputSourceCodeAlloy.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, inputSourceCodeAlloy, afterSrcNodeChange, first);
			first = false;
		}

		String afterStringsChange = inputSourceCodeAlloy.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, inputSourceCodeAlloy, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = inputSourceCodeAlloy.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, inputSourceCodeAlloy, afterTabIndexChange, first);
			first = false;
		}

		String afterTabSizeChange = inputSourceCodeAlloy.getAfterTabSizeChange();

		if (afterTabSizeChange != null) {

			encodeAfterTabSizeChange(responseWriter, inputSourceCodeAlloy, afterTabSizeChange, first);
			first = false;
		}

		String afterUseSoftTabsChange = inputSourceCodeAlloy.getAfterUseSoftTabsChange();

		if (afterUseSoftTabsChange != null) {

			encodeAfterUseSoftTabsChange(responseWriter, inputSourceCodeAlloy, afterUseSoftTabsChange, first);
			first = false;
		}

		String afterUseWrapModeChange = inputSourceCodeAlloy.getAfterUseWrapModeChange();

		if (afterUseWrapModeChange != null) {

			encodeAfterUseWrapModeChange(responseWriter, inputSourceCodeAlloy, afterUseWrapModeChange, first);
			first = false;
		}

		String afterValueChange = inputSourceCodeAlloy.getAfterValueChange();

		if (afterValueChange != null) {

			encodeAfterValueChange(responseWriter, inputSourceCodeAlloy, afterValueChange, first);
			first = false;
		}

		String afterVisibleChange = inputSourceCodeAlloy.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, inputSourceCodeAlloy, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = inputSourceCodeAlloy.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, inputSourceCodeAlloy, afterWidthChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onBoundingBoxChange = inputSourceCodeAlloy.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, inputSourceCodeAlloy, onBoundingBoxChange, first);
			first = false;
		}

		String onContentBoxChange = inputSourceCodeAlloy.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, inputSourceCodeAlloy, onContentBoxChange, first);
			first = false;
		}

		String onDestroyedChange = inputSourceCodeAlloy.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, inputSourceCodeAlloy, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = inputSourceCodeAlloy.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, inputSourceCodeAlloy, onDisabledChange, first);
			first = false;
		}

		String onFocusedChange = inputSourceCodeAlloy.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, inputSourceCodeAlloy, onFocusedChange, first);
			first = false;
		}

		String onHeightChange = inputSourceCodeAlloy.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, inputSourceCodeAlloy, onHeightChange, first);
			first = false;
		}

		String onHighlightActiveLineChange = inputSourceCodeAlloy.getOnHighlightActiveLineChange();

		if (onHighlightActiveLineChange != null) {

			encodeOnHighlightActiveLineChange(responseWriter, inputSourceCodeAlloy, onHighlightActiveLineChange, first);
			first = false;
		}

		String onIdChange = inputSourceCodeAlloy.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, inputSourceCodeAlloy, onIdChange, first);
			first = false;
		}

		String onInitializedChange = inputSourceCodeAlloy.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, inputSourceCodeAlloy, onInitializedChange, first);
			first = false;
		}

		String onLocaleChange = inputSourceCodeAlloy.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, inputSourceCodeAlloy, onLocaleChange, first);
			first = false;
		}

		String onModeChange = inputSourceCodeAlloy.getOnModeChange();

		if (onModeChange != null) {

			encodeOnModeChange(responseWriter, inputSourceCodeAlloy, onModeChange, first);
			first = false;
		}

		String onReadOnlyChange = inputSourceCodeAlloy.getOnReadOnlyChange();

		if (onReadOnlyChange != null) {

			encodeOnReadOnlyChange(responseWriter, inputSourceCodeAlloy, onReadOnlyChange, first);
			first = false;
		}

		String onRenderChange = inputSourceCodeAlloy.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, inputSourceCodeAlloy, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = inputSourceCodeAlloy.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, inputSourceCodeAlloy, onRenderedChange, first);
			first = false;
		}

		String onShowPrintMarginChange = inputSourceCodeAlloy.getOnShowPrintMarginChange();

		if (onShowPrintMarginChange != null) {

			encodeOnShowPrintMarginChange(responseWriter, inputSourceCodeAlloy, onShowPrintMarginChange, first);
			first = false;
		}

		String onSrcNodeChange = inputSourceCodeAlloy.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, inputSourceCodeAlloy, onSrcNodeChange, first);
			first = false;
		}

		String onStringsChange = inputSourceCodeAlloy.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, inputSourceCodeAlloy, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = inputSourceCodeAlloy.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, inputSourceCodeAlloy, onTabIndexChange, first);
			first = false;
		}

		String onTabSizeChange = inputSourceCodeAlloy.getOnTabSizeChange();

		if (onTabSizeChange != null) {

			encodeOnTabSizeChange(responseWriter, inputSourceCodeAlloy, onTabSizeChange, first);
			first = false;
		}

		String onUseSoftTabsChange = inputSourceCodeAlloy.getOnUseSoftTabsChange();

		if (onUseSoftTabsChange != null) {

			encodeOnUseSoftTabsChange(responseWriter, inputSourceCodeAlloy, onUseSoftTabsChange, first);
			first = false;
		}

		String onUseWrapModeChange = inputSourceCodeAlloy.getOnUseWrapModeChange();

		if (onUseWrapModeChange != null) {

			encodeOnUseWrapModeChange(responseWriter, inputSourceCodeAlloy, onUseWrapModeChange, first);
			first = false;
		}

		String onValueChange = inputSourceCodeAlloy.getOnValueChange();

		if (onValueChange != null) {

			encodeOnValueChange(responseWriter, inputSourceCodeAlloy, onValueChange, first);
			first = false;
		}

		String onVisibleChange = inputSourceCodeAlloy.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, inputSourceCodeAlloy, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = inputSourceCodeAlloy.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, inputSourceCodeAlloy, onWidthChange, first);
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

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterHighlightActiveLineChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterHighlightActiveLineChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIGHLIGHT_ACTIVE_LINE_CHANGE, afterHighlightActiveLineChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterModeChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MODE_CHANGE, afterModeChange, first);
	}

	protected void encodeAfterReadOnlyChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterReadOnlyChange, boolean first) throws IOException {
		encodeEvent(responseWriter, READ_ONLY_CHANGE, afterReadOnlyChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterShowPrintMarginChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterShowPrintMarginChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_PRINT_MARGIN_CHANGE, afterShowPrintMarginChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterTabSizeChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterTabSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_SIZE_CHANGE, afterTabSizeChange, first);
	}

	protected void encodeAfterUseSoftTabsChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterUseSoftTabsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_SOFT_TABS_CHANGE, afterUseSoftTabsChange, first);
	}

	protected void encodeAfterUseWrapModeChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterUseWrapModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_WRAP_MODE_CHANGE, afterUseWrapModeChange, first);
	}

	protected void encodeAfterValueChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, afterValueChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
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

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnHighlightActiveLineChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onHighlightActiveLineChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIGHLIGHT_ACTIVE_LINE_CHANGE, onHighlightActiveLineChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnModeChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MODE_CHANGE, onModeChange, first);
	}

	protected void encodeOnReadOnlyChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onReadOnlyChange, boolean first) throws IOException {
		encodeEvent(responseWriter, READ_ONLY_CHANGE, onReadOnlyChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnShowPrintMarginChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onShowPrintMarginChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_PRINT_MARGIN_CHANGE, onShowPrintMarginChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnTabSizeChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onTabSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_SIZE_CHANGE, onTabSizeChange, first);
	}

	protected void encodeOnUseSoftTabsChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onUseSoftTabsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_SOFT_TABS_CHANGE, onUseSoftTabsChange, first);
	}

	protected void encodeOnUseWrapModeChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onUseWrapModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_WRAP_MODE_CHANGE, onUseWrapModeChange, first);
	}

	protected void encodeOnValueChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, onValueChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, InputSourceCodeAlloy inputSourceCodeAlloy, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
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
