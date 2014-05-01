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
package com.liferay.faces.alloy.component.aceeditor;
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
public abstract class AceEditorRendererBase extends DelegatingAlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "AceEditor";
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

		AceEditorAlloy aceEditorAlloy = (AceEditorAlloy) uiComponent;
		boolean first = true;

		String boundingBox = aceEditorAlloy.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, aceEditorAlloy, boundingBox, first);
			first = false;
		}

		String contentBox = aceEditorAlloy.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, aceEditorAlloy, contentBox, first);
			first = false;
		}

		Boolean destroyed = aceEditorAlloy.isDestroyed();

		if (destroyed != null) {

			encodeDestroyed(responseWriter, aceEditorAlloy, destroyed, first);
			first = false;
		}

		Boolean disabled = aceEditorAlloy.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, aceEditorAlloy, disabled, first);
			first = false;
		}

		Boolean focused = aceEditorAlloy.isFocused();

		if (focused != null) {

			encodeFocused(responseWriter, aceEditorAlloy, focused, first);
			first = false;
		}

		Object height = aceEditorAlloy.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, aceEditorAlloy, height, first);
			first = false;
		}

		Boolean highlightActiveLine = aceEditorAlloy.isHighlightActiveLine();

		if (highlightActiveLine != null) {

			encodeHighlightActiveLine(responseWriter, aceEditorAlloy, highlightActiveLine, first);
			first = false;
		}

		String id = aceEditorAlloy.getId();

		if (id != null) {

			encodeId(responseWriter, aceEditorAlloy, id, first);
			first = false;
		}

		Boolean initialized = aceEditorAlloy.isInitialized();

		if (initialized != null) {

			encodeInitialized(responseWriter, aceEditorAlloy, initialized, first);
			first = false;
		}

		String locale = aceEditorAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, aceEditorAlloy, locale, first);
			first = false;
		}

		String mode = aceEditorAlloy.getMode();

		if (mode != null) {

			encodeMode(responseWriter, aceEditorAlloy, mode, first);
			first = false;
		}

		Boolean readOnly = aceEditorAlloy.isReadOnly();

		if (readOnly != null) {

			encodeReadOnly(responseWriter, aceEditorAlloy, readOnly, first);
			first = false;
		}

		Boolean rendered = aceEditorAlloy.isRendered();

		if (rendered != null) {

			encodeRendered(responseWriter, aceEditorAlloy, rendered, first);
			first = false;
		}

		Boolean showPrintMargin = aceEditorAlloy.isShowPrintMargin();

		if (showPrintMargin != null) {

			encodeShowPrintMargin(responseWriter, aceEditorAlloy, showPrintMargin, first);
			first = false;
		}

		String srcNode = aceEditorAlloy.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, aceEditorAlloy, srcNode, first);
			first = false;
		}

		Object strings = aceEditorAlloy.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, aceEditorAlloy, strings, first);
			first = false;
		}

		Object tabIndex = aceEditorAlloy.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, aceEditorAlloy, tabIndex, first);
			first = false;
		}

		Object tabSize = aceEditorAlloy.getTabSize();

		if (tabSize != null) {

			encodeTabSize(responseWriter, aceEditorAlloy, tabSize, first);
			first = false;
		}

		Boolean useSoftTabs = aceEditorAlloy.isUseSoftTabs();

		if (useSoftTabs != null) {

			encodeUseSoftTabs(responseWriter, aceEditorAlloy, useSoftTabs, first);
			first = false;
		}

		Boolean useWrapMode = aceEditorAlloy.isUseWrapMode();

		if (useWrapMode != null) {

			encodeUseWrapMode(responseWriter, aceEditorAlloy, useWrapMode, first);
			first = false;
		}

		Object value = aceEditorAlloy.getValue();

		if (value != null) {

			encodeValue(responseWriter, aceEditorAlloy, value, first);
			first = false;
		}

		Boolean visible = aceEditorAlloy.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, aceEditorAlloy, visible, first);
			first = false;
		}

		Boolean widgetRender = aceEditorAlloy.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, aceEditorAlloy, widgetRender, first);
			first = false;
		}

		Object width = aceEditorAlloy.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, aceEditorAlloy, width, first);
			first = false;
		}

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterBoundingBoxChange = aceEditorAlloy.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, aceEditorAlloy, afterBoundingBoxChange, first);
			first = false;
		}

		String afterContentBoxChange = aceEditorAlloy.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, aceEditorAlloy, afterContentBoxChange, first);
			first = false;
		}

		String afterDestroyedChange = aceEditorAlloy.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, aceEditorAlloy, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = aceEditorAlloy.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, aceEditorAlloy, afterDisabledChange, first);
			first = false;
		}

		String afterFocusedChange = aceEditorAlloy.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, aceEditorAlloy, afterFocusedChange, first);
			first = false;
		}

		String afterHeightChange = aceEditorAlloy.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, aceEditorAlloy, afterHeightChange, first);
			first = false;
		}

		String afterHighlightActiveLineChange = aceEditorAlloy.getAfterHighlightActiveLineChange();

		if (afterHighlightActiveLineChange != null) {

			encodeAfterHighlightActiveLineChange(responseWriter, aceEditorAlloy, afterHighlightActiveLineChange, first);
			first = false;
		}

		String afterIdChange = aceEditorAlloy.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, aceEditorAlloy, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = aceEditorAlloy.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, aceEditorAlloy, afterInitializedChange, first);
			first = false;
		}

		String afterLocaleChange = aceEditorAlloy.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, aceEditorAlloy, afterLocaleChange, first);
			first = false;
		}

		String afterModeChange = aceEditorAlloy.getAfterModeChange();

		if (afterModeChange != null) {

			encodeAfterModeChange(responseWriter, aceEditorAlloy, afterModeChange, first);
			first = false;
		}

		String afterReadOnlyChange = aceEditorAlloy.getAfterReadOnlyChange();

		if (afterReadOnlyChange != null) {

			encodeAfterReadOnlyChange(responseWriter, aceEditorAlloy, afterReadOnlyChange, first);
			first = false;
		}

		String afterRenderChange = aceEditorAlloy.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, aceEditorAlloy, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = aceEditorAlloy.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, aceEditorAlloy, afterRenderedChange, first);
			first = false;
		}

		String afterShowPrintMarginChange = aceEditorAlloy.getAfterShowPrintMarginChange();

		if (afterShowPrintMarginChange != null) {

			encodeAfterShowPrintMarginChange(responseWriter, aceEditorAlloy, afterShowPrintMarginChange, first);
			first = false;
		}

		String afterSrcNodeChange = aceEditorAlloy.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, aceEditorAlloy, afterSrcNodeChange, first);
			first = false;
		}

		String afterStringsChange = aceEditorAlloy.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, aceEditorAlloy, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = aceEditorAlloy.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, aceEditorAlloy, afterTabIndexChange, first);
			first = false;
		}

		String afterTabSizeChange = aceEditorAlloy.getAfterTabSizeChange();

		if (afterTabSizeChange != null) {

			encodeAfterTabSizeChange(responseWriter, aceEditorAlloy, afterTabSizeChange, first);
			first = false;
		}

		String afterUseSoftTabsChange = aceEditorAlloy.getAfterUseSoftTabsChange();

		if (afterUseSoftTabsChange != null) {

			encodeAfterUseSoftTabsChange(responseWriter, aceEditorAlloy, afterUseSoftTabsChange, first);
			first = false;
		}

		String afterUseWrapModeChange = aceEditorAlloy.getAfterUseWrapModeChange();

		if (afterUseWrapModeChange != null) {

			encodeAfterUseWrapModeChange(responseWriter, aceEditorAlloy, afterUseWrapModeChange, first);
			first = false;
		}

		String afterValueChange = aceEditorAlloy.getAfterValueChange();

		if (afterValueChange != null) {

			encodeAfterValueChange(responseWriter, aceEditorAlloy, afterValueChange, first);
			first = false;
		}

		String afterVisibleChange = aceEditorAlloy.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, aceEditorAlloy, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = aceEditorAlloy.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, aceEditorAlloy, afterWidthChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onBoundingBoxChange = aceEditorAlloy.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, aceEditorAlloy, onBoundingBoxChange, first);
			first = false;
		}

		String onContentBoxChange = aceEditorAlloy.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, aceEditorAlloy, onContentBoxChange, first);
			first = false;
		}

		String onDestroyedChange = aceEditorAlloy.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, aceEditorAlloy, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = aceEditorAlloy.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, aceEditorAlloy, onDisabledChange, first);
			first = false;
		}

		String onFocusedChange = aceEditorAlloy.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, aceEditorAlloy, onFocusedChange, first);
			first = false;
		}

		String onHeightChange = aceEditorAlloy.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, aceEditorAlloy, onHeightChange, first);
			first = false;
		}

		String onHighlightActiveLineChange = aceEditorAlloy.getOnHighlightActiveLineChange();

		if (onHighlightActiveLineChange != null) {

			encodeOnHighlightActiveLineChange(responseWriter, aceEditorAlloy, onHighlightActiveLineChange, first);
			first = false;
		}

		String onIdChange = aceEditorAlloy.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, aceEditorAlloy, onIdChange, first);
			first = false;
		}

		String onInitializedChange = aceEditorAlloy.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, aceEditorAlloy, onInitializedChange, first);
			first = false;
		}

		String onLocaleChange = aceEditorAlloy.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, aceEditorAlloy, onLocaleChange, first);
			first = false;
		}

		String onModeChange = aceEditorAlloy.getOnModeChange();

		if (onModeChange != null) {

			encodeOnModeChange(responseWriter, aceEditorAlloy, onModeChange, first);
			first = false;
		}

		String onReadOnlyChange = aceEditorAlloy.getOnReadOnlyChange();

		if (onReadOnlyChange != null) {

			encodeOnReadOnlyChange(responseWriter, aceEditorAlloy, onReadOnlyChange, first);
			first = false;
		}

		String onRenderChange = aceEditorAlloy.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, aceEditorAlloy, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = aceEditorAlloy.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, aceEditorAlloy, onRenderedChange, first);
			first = false;
		}

		String onShowPrintMarginChange = aceEditorAlloy.getOnShowPrintMarginChange();

		if (onShowPrintMarginChange != null) {

			encodeOnShowPrintMarginChange(responseWriter, aceEditorAlloy, onShowPrintMarginChange, first);
			first = false;
		}

		String onSrcNodeChange = aceEditorAlloy.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, aceEditorAlloy, onSrcNodeChange, first);
			first = false;
		}

		String onStringsChange = aceEditorAlloy.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, aceEditorAlloy, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = aceEditorAlloy.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, aceEditorAlloy, onTabIndexChange, first);
			first = false;
		}

		String onTabSizeChange = aceEditorAlloy.getOnTabSizeChange();

		if (onTabSizeChange != null) {

			encodeOnTabSizeChange(responseWriter, aceEditorAlloy, onTabSizeChange, first);
			first = false;
		}

		String onUseSoftTabsChange = aceEditorAlloy.getOnUseSoftTabsChange();

		if (onUseSoftTabsChange != null) {

			encodeOnUseSoftTabsChange(responseWriter, aceEditorAlloy, onUseSoftTabsChange, first);
			first = false;
		}

		String onUseWrapModeChange = aceEditorAlloy.getOnUseWrapModeChange();

		if (onUseWrapModeChange != null) {

			encodeOnUseWrapModeChange(responseWriter, aceEditorAlloy, onUseWrapModeChange, first);
			first = false;
		}

		String onValueChange = aceEditorAlloy.getOnValueChange();

		if (onValueChange != null) {

			encodeOnValueChange(responseWriter, aceEditorAlloy, onValueChange, first);
			first = false;
		}

		String onVisibleChange = aceEditorAlloy.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, aceEditorAlloy, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = aceEditorAlloy.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, aceEditorAlloy, onWidthChange, first);
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

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterHighlightActiveLineChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterHighlightActiveLineChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIGHLIGHT_ACTIVE_LINE_CHANGE, afterHighlightActiveLineChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterModeChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MODE_CHANGE, afterModeChange, first);
	}

	protected void encodeAfterReadOnlyChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterReadOnlyChange, boolean first) throws IOException {
		encodeEvent(responseWriter, READ_ONLY_CHANGE, afterReadOnlyChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterShowPrintMarginChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterShowPrintMarginChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_PRINT_MARGIN_CHANGE, afterShowPrintMarginChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterTabSizeChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterTabSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_SIZE_CHANGE, afterTabSizeChange, first);
	}

	protected void encodeAfterUseSoftTabsChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterUseSoftTabsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_SOFT_TABS_CHANGE, afterUseSoftTabsChange, first);
	}

	protected void encodeAfterUseWrapModeChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterUseWrapModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_WRAP_MODE_CHANGE, afterUseWrapModeChange, first);
	}

	protected void encodeAfterValueChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, afterValueChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorAlloy.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorAlloy.CONTENT_BOX, contentBox, first);
	}

	protected void encodeDestroyed(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean destroyed, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.DESTROYED, destroyed, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.DISABLED, disabled, first);
	}

	protected void encodeFocused(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean focused, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.FOCUSED, focused, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Object height, boolean first) throws IOException {
		encodeNumber(responseWriter, AceEditorAlloy.HEIGHT, height, first);
	}

	protected void encodeHighlightActiveLine(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean highlightActiveLine, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.HIGHLIGHT_ACTIVE_LINE, highlightActiveLine, first);
	}

	protected void encodeId(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String id, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorAlloy.ID, id, first);
	}

	protected void encodeInitialized(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean initialized, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.INITIALIZED, initialized, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String locale, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorAlloy.LOCALE, locale, first);
	}

	protected void encodeMode(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String mode, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorAlloy.MODE, mode, first);
	}

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnHighlightActiveLineChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onHighlightActiveLineChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIGHLIGHT_ACTIVE_LINE_CHANGE, onHighlightActiveLineChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnModeChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MODE_CHANGE, onModeChange, first);
	}

	protected void encodeOnReadOnlyChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onReadOnlyChange, boolean first) throws IOException {
		encodeEvent(responseWriter, READ_ONLY_CHANGE, onReadOnlyChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnShowPrintMarginChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onShowPrintMarginChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_PRINT_MARGIN_CHANGE, onShowPrintMarginChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnTabSizeChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onTabSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_SIZE_CHANGE, onTabSizeChange, first);
	}

	protected void encodeOnUseSoftTabsChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onUseSoftTabsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_SOFT_TABS_CHANGE, onUseSoftTabsChange, first);
	}

	protected void encodeOnUseWrapModeChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onUseWrapModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_WRAP_MODE_CHANGE, onUseWrapModeChange, first);
	}

	protected void encodeOnValueChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, onValueChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
	}

	protected void encodeReadOnly(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean readOnly, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.READ_ONLY, readOnly, first);
	}

	protected void encodeRendered(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean rendered, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.RENDERED, rendered, first);
	}

	protected void encodeShowPrintMargin(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean showPrintMargin, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.SHOW_PRINT_MARGIN, showPrintMargin, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorAlloy.SRC_NODE, srcNode, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, AceEditorAlloy.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, AceEditorAlloy.TAB_INDEX, tabIndex, first);
	}

	protected void encodeTabSize(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Object tabSize, boolean first) throws IOException {
		encodeNumber(responseWriter, AceEditorAlloy.TAB_SIZE, tabSize, first);
	}

	protected void encodeUseSoftTabs(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean useSoftTabs, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.USE_SOFT_TABS, useSoftTabs, first);
	}

	protected void encodeUseWrapMode(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean useWrapMode, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.USE_WRAP_MODE, useWrapMode, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Object value, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorAlloy.VALUE, value, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.VISIBLE, visible, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorAlloy.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, AceEditorAlloy aceEditorAlloy, Object width, boolean first) throws IOException {
		encodeNumber(responseWriter, AceEditorAlloy.WIDTH, width, first);
	}
}
//J+
