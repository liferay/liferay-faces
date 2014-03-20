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
import java.util.List;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.AUIRendererBase;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Widget;
import com.liferay.faces.util.lang.StringPool;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class AceEditorRendererBase extends AUIRendererBase {

	// Private Constants
	private static final String A_CLASS_NAME = "A.AceEditor";
	private static final String AUI_MODULE_NAME = "aui-ace-editor";
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

	protected void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		AceEditorComponent aceEditorComponent = (AceEditorComponent) uiComponent;
		String widgetVar = ComponentUtil.resolveWidgetVar(facesContext, (Widget) aceEditorComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		responseWriter.write(VAR);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(LIFERAY_COMPONENT);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.COMMA);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(FUNCTION);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(IF);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.EXCLAMATION);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.EQUAL);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(NEW);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(A_CLASS_NAME);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		boolean first = true;

		Boolean aceEditorDisabled = aceEditorComponent.isAceEditorDisabled();

		if (aceEditorDisabled != null) {

			encodeAceEditorDisabled(responseWriter, aceEditorComponent, aceEditorDisabled, first);
			first = false;
		}

		String aceEditorId = aceEditorComponent.getAceEditorId();

		if (aceEditorId != null) {

			encodeAceEditorId(responseWriter, aceEditorComponent, aceEditorId, first);
			first = false;
		}

		Boolean aceEditorRendered = aceEditorComponent.isAceEditorRendered();

		if (aceEditorRendered != null) {

			encodeAceEditorRendered(responseWriter, aceEditorComponent, aceEditorRendered, first);
			first = false;
		}

		String aceEditorValue = aceEditorComponent.getAceEditorValue();

		if (aceEditorValue != null) {

			encodeAceEditorValue(responseWriter, aceEditorComponent, aceEditorValue, first);
			first = false;
		}

		String boundingBox = aceEditorComponent.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, aceEditorComponent, boundingBox, first);
			first = false;
		}

		String contentBox = aceEditorComponent.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, aceEditorComponent, contentBox, first);
			first = false;
		}

		Boolean destroyed = aceEditorComponent.isDestroyed();

		if (destroyed != null) {

			encodeDestroyed(responseWriter, aceEditorComponent, destroyed, first);
			first = false;
		}

		Boolean focused = aceEditorComponent.isFocused();

		if (focused != null) {

			encodeFocused(responseWriter, aceEditorComponent, focused, first);
			first = false;
		}

		Object height = aceEditorComponent.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, aceEditorComponent, height, first);
			first = false;
		}

		Boolean highlightActiveLine = aceEditorComponent.isHighlightActiveLine();

		if (highlightActiveLine != null) {

			encodeHighlightActiveLine(responseWriter, aceEditorComponent, highlightActiveLine, first);
			first = false;
		}

		Boolean initialized = aceEditorComponent.isInitialized();

		if (initialized != null) {

			encodeInitialized(responseWriter, aceEditorComponent, initialized, first);
			first = false;
		}

		String locale = aceEditorComponent.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, aceEditorComponent, locale, first);
			first = false;
		}

		String mode = aceEditorComponent.getMode();

		if (mode != null) {

			encodeMode(responseWriter, aceEditorComponent, mode, first);
			first = false;
		}

		Boolean readOnly = aceEditorComponent.isReadOnly();

		if (readOnly != null) {

			encodeReadOnly(responseWriter, aceEditorComponent, readOnly, first);
			first = false;
		}

		Object render = aceEditorComponent.getRender();

		if (render != null) {

			encodeRender(responseWriter, aceEditorComponent, render, first);
			first = false;
		}

		Boolean showPrintMargin = aceEditorComponent.isShowPrintMargin();

		if (showPrintMargin != null) {

			encodeShowPrintMargin(responseWriter, aceEditorComponent, showPrintMargin, first);
			first = false;
		}

		String srcNode = aceEditorComponent.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, aceEditorComponent, srcNode, first);
			first = false;
		}

		Object strings = aceEditorComponent.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, aceEditorComponent, strings, first);
			first = false;
		}

		Object tabIndex = aceEditorComponent.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, aceEditorComponent, tabIndex, first);
			first = false;
		}

		Object tabSize = aceEditorComponent.getTabSize();

		if (tabSize != null) {

			encodeTabSize(responseWriter, aceEditorComponent, tabSize, first);
			first = false;
		}

		Boolean useSoftTabs = aceEditorComponent.isUseSoftTabs();

		if (useSoftTabs != null) {

			encodeUseSoftTabs(responseWriter, aceEditorComponent, useSoftTabs, first);
			first = false;
		}

		Boolean useWrapMode = aceEditorComponent.isUseWrapMode();

		if (useWrapMode != null) {

			encodeUseWrapMode(responseWriter, aceEditorComponent, useWrapMode, first);
			first = false;
		}

		Boolean visible = aceEditorComponent.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, aceEditorComponent, visible, first);
			first = false;
		}

		Object width = aceEditorComponent.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, aceEditorComponent, width, first);
			first = false;
		}
		if (!first) {
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write(StringPool.NEW_LINE);

		responseWriter.write(AFTER);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		first = true;

		String afterBoundingBoxChange = aceEditorComponent.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, aceEditorComponent, afterBoundingBoxChange, first);
			first = false;
		}

		String afterContentBoxChange = aceEditorComponent.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, aceEditorComponent, afterContentBoxChange, first);
			first = false;
		}

		String afterDestroyedChange = aceEditorComponent.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, aceEditorComponent, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = aceEditorComponent.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, aceEditorComponent, afterDisabledChange, first);
			first = false;
		}

		String afterFocusedChange = aceEditorComponent.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, aceEditorComponent, afterFocusedChange, first);
			first = false;
		}

		String afterHeightChange = aceEditorComponent.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, aceEditorComponent, afterHeightChange, first);
			first = false;
		}

		String afterHighlightActiveLineChange = aceEditorComponent.getAfterHighlightActiveLineChange();

		if (afterHighlightActiveLineChange != null) {

			encodeAfterHighlightActiveLineChange(responseWriter, aceEditorComponent, afterHighlightActiveLineChange, first);
			first = false;
		}

		String afterIdChange = aceEditorComponent.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, aceEditorComponent, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = aceEditorComponent.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, aceEditorComponent, afterInitializedChange, first);
			first = false;
		}

		String afterLocaleChange = aceEditorComponent.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, aceEditorComponent, afterLocaleChange, first);
			first = false;
		}

		String afterModeChange = aceEditorComponent.getAfterModeChange();

		if (afterModeChange != null) {

			encodeAfterModeChange(responseWriter, aceEditorComponent, afterModeChange, first);
			first = false;
		}

		String afterReadOnlyChange = aceEditorComponent.getAfterReadOnlyChange();

		if (afterReadOnlyChange != null) {

			encodeAfterReadOnlyChange(responseWriter, aceEditorComponent, afterReadOnlyChange, first);
			first = false;
		}

		String afterRenderChange = aceEditorComponent.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, aceEditorComponent, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = aceEditorComponent.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, aceEditorComponent, afterRenderedChange, first);
			first = false;
		}

		String afterShowPrintMarginChange = aceEditorComponent.getAfterShowPrintMarginChange();

		if (afterShowPrintMarginChange != null) {

			encodeAfterShowPrintMarginChange(responseWriter, aceEditorComponent, afterShowPrintMarginChange, first);
			first = false;
		}

		String afterSrcNodeChange = aceEditorComponent.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, aceEditorComponent, afterSrcNodeChange, first);
			first = false;
		}

		String afterStringsChange = aceEditorComponent.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, aceEditorComponent, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = aceEditorComponent.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, aceEditorComponent, afterTabIndexChange, first);
			first = false;
		}

		String afterTabSizeChange = aceEditorComponent.getAfterTabSizeChange();

		if (afterTabSizeChange != null) {

			encodeAfterTabSizeChange(responseWriter, aceEditorComponent, afterTabSizeChange, first);
			first = false;
		}

		String afterUseSoftTabsChange = aceEditorComponent.getAfterUseSoftTabsChange();

		if (afterUseSoftTabsChange != null) {

			encodeAfterUseSoftTabsChange(responseWriter, aceEditorComponent, afterUseSoftTabsChange, first);
			first = false;
		}

		String afterUseWrapModeChange = aceEditorComponent.getAfterUseWrapModeChange();

		if (afterUseWrapModeChange != null) {

			encodeAfterUseWrapModeChange(responseWriter, aceEditorComponent, afterUseWrapModeChange, first);
			first = false;
		}

		String afterValueChange = aceEditorComponent.getAfterValueChange();

		if (afterValueChange != null) {

			encodeAfterValueChange(responseWriter, aceEditorComponent, afterValueChange, first);
			first = false;
		}

		String afterVisibleChange = aceEditorComponent.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, aceEditorComponent, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = aceEditorComponent.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, aceEditorComponent, afterWidthChange, first);
			first = false;
		}
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.COMMA);
		responseWriter.write(StringPool.NEW_LINE);

		responseWriter.write(ON);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);

		first = true;

		String onBoundingBoxChange = aceEditorComponent.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, aceEditorComponent, onBoundingBoxChange, first);
			first = false;
		}

		String onContentBoxChange = aceEditorComponent.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, aceEditorComponent, onContentBoxChange, first);
			first = false;
		}

		String onDestroyedChange = aceEditorComponent.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, aceEditorComponent, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = aceEditorComponent.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, aceEditorComponent, onDisabledChange, first);
			first = false;
		}

		String onFocusedChange = aceEditorComponent.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, aceEditorComponent, onFocusedChange, first);
			first = false;
		}

		String onHeightChange = aceEditorComponent.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, aceEditorComponent, onHeightChange, first);
			first = false;
		}

		String onHighlightActiveLineChange = aceEditorComponent.getOnHighlightActiveLineChange();

		if (onHighlightActiveLineChange != null) {

			encodeOnHighlightActiveLineChange(responseWriter, aceEditorComponent, onHighlightActiveLineChange, first);
			first = false;
		}

		String onIdChange = aceEditorComponent.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, aceEditorComponent, onIdChange, first);
			first = false;
		}

		String onInitializedChange = aceEditorComponent.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, aceEditorComponent, onInitializedChange, first);
			first = false;
		}

		String onLocaleChange = aceEditorComponent.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, aceEditorComponent, onLocaleChange, first);
			first = false;
		}

		String onModeChange = aceEditorComponent.getOnModeChange();

		if (onModeChange != null) {

			encodeOnModeChange(responseWriter, aceEditorComponent, onModeChange, first);
			first = false;
		}

		String onReadOnlyChange = aceEditorComponent.getOnReadOnlyChange();

		if (onReadOnlyChange != null) {

			encodeOnReadOnlyChange(responseWriter, aceEditorComponent, onReadOnlyChange, first);
			first = false;
		}

		String onRenderChange = aceEditorComponent.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, aceEditorComponent, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = aceEditorComponent.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, aceEditorComponent, onRenderedChange, first);
			first = false;
		}

		String onShowPrintMarginChange = aceEditorComponent.getOnShowPrintMarginChange();

		if (onShowPrintMarginChange != null) {

			encodeOnShowPrintMarginChange(responseWriter, aceEditorComponent, onShowPrintMarginChange, first);
			first = false;
		}

		String onSrcNodeChange = aceEditorComponent.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, aceEditorComponent, onSrcNodeChange, first);
			first = false;
		}

		String onStringsChange = aceEditorComponent.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, aceEditorComponent, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = aceEditorComponent.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, aceEditorComponent, onTabIndexChange, first);
			first = false;
		}

		String onTabSizeChange = aceEditorComponent.getOnTabSizeChange();

		if (onTabSizeChange != null) {

			encodeOnTabSizeChange(responseWriter, aceEditorComponent, onTabSizeChange, first);
			first = false;
		}

		String onUseSoftTabsChange = aceEditorComponent.getOnUseSoftTabsChange();

		if (onUseSoftTabsChange != null) {

			encodeOnUseSoftTabsChange(responseWriter, aceEditorComponent, onUseSoftTabsChange, first);
			first = false;
		}

		String onUseWrapModeChange = aceEditorComponent.getOnUseWrapModeChange();

		if (onUseWrapModeChange != null) {

			encodeOnUseWrapModeChange(responseWriter, aceEditorComponent, onUseWrapModeChange, first);
			first = false;
		}

		String onValueChange = aceEditorComponent.getOnValueChange();

		if (onValueChange != null) {

			encodeOnValueChange(responseWriter, aceEditorComponent, onValueChange, first);
			first = false;
		}

		String onVisibleChange = aceEditorComponent.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, aceEditorComponent, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = aceEditorComponent.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, aceEditorComponent, onWidthChange, first);
			first = false;
		}
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(RETURN);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
		responseWriter.write(LIFERAY_COMPONENT);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(widgetVar);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.NEW_LINE);
	}

	protected List<String> getModules() {

		List<String> modules = super.getModules();
		modules.remove(0);
		modules.add(AUI_MODULE_NAME);

		return modules;
	}

	protected void encodeAceEditorDisabled(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean aceEditorDisabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.ACE_EDITOR_DISABLED, aceEditorDisabled, first);
	}

	protected void encodeAceEditorId(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String aceEditorId, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorComponent.ACE_EDITOR_ID, aceEditorId, first);
	}

	protected void encodeAceEditorRendered(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean aceEditorRendered, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.ACE_EDITOR_RENDERED, aceEditorRendered, first);
	}

	protected void encodeAceEditorValue(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String aceEditorValue, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorComponent.ACE_EDITOR_VALUE, aceEditorValue, first);
	}

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterHighlightActiveLineChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterHighlightActiveLineChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIGHLIGHT_ACTIVE_LINE_CHANGE, afterHighlightActiveLineChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterModeChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MODE_CHANGE, afterModeChange, first);
	}

	protected void encodeAfterReadOnlyChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterReadOnlyChange, boolean first) throws IOException {
		encodeEvent(responseWriter, READ_ONLY_CHANGE, afterReadOnlyChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterShowPrintMarginChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterShowPrintMarginChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_PRINT_MARGIN_CHANGE, afterShowPrintMarginChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterTabSizeChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterTabSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_SIZE_CHANGE, afterTabSizeChange, first);
	}

	protected void encodeAfterUseSoftTabsChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterUseSoftTabsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_SOFT_TABS_CHANGE, afterUseSoftTabsChange, first);
	}

	protected void encodeAfterUseWrapModeChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterUseWrapModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_WRAP_MODE_CHANGE, afterUseWrapModeChange, first);
	}

	protected void encodeAfterValueChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, afterValueChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorComponent.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorComponent.CONTENT_BOX, contentBox, first);
	}

	protected void encodeDestroyed(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean destroyed, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.DESTROYED, destroyed, first);
	}

	protected void encodeFocused(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean focused, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.FOCUSED, focused, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Object height, boolean first) throws IOException {
		encodeNumber(responseWriter, AceEditorComponent.HEIGHT, height, first);
	}

	protected void encodeHighlightActiveLine(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean highlightActiveLine, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.HIGHLIGHT_ACTIVE_LINE, highlightActiveLine, first);
	}

	protected void encodeInitialized(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean initialized, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.INITIALIZED, initialized, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String locale, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorComponent.LOCALE, locale, first);
	}

	protected void encodeMode(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String mode, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorComponent.MODE, mode, first);
	}

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnHighlightActiveLineChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onHighlightActiveLineChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIGHLIGHT_ACTIVE_LINE_CHANGE, onHighlightActiveLineChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnModeChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MODE_CHANGE, onModeChange, first);
	}

	protected void encodeOnReadOnlyChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onReadOnlyChange, boolean first) throws IOException {
		encodeEvent(responseWriter, READ_ONLY_CHANGE, onReadOnlyChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnShowPrintMarginChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onShowPrintMarginChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_PRINT_MARGIN_CHANGE, onShowPrintMarginChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnTabSizeChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onTabSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_SIZE_CHANGE, onTabSizeChange, first);
	}

	protected void encodeOnUseSoftTabsChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onUseSoftTabsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_SOFT_TABS_CHANGE, onUseSoftTabsChange, first);
	}

	protected void encodeOnUseWrapModeChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onUseWrapModeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_WRAP_MODE_CHANGE, onUseWrapModeChange, first);
	}

	protected void encodeOnValueChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, onValueChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
	}

	protected void encodeReadOnly(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean readOnly, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.READ_ONLY, readOnly, first);
	}

	protected void encodeRender(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Object render, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorComponent.RENDER, render, first);
	}

	protected void encodeShowPrintMargin(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean showPrintMargin, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.SHOW_PRINT_MARGIN, showPrintMargin, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, AceEditorComponent.SRC_NODE, srcNode, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, AceEditorComponent.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, AceEditorComponent.TAB_INDEX, tabIndex, first);
	}

	protected void encodeTabSize(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Object tabSize, boolean first) throws IOException {
		encodeNumber(responseWriter, AceEditorComponent.TAB_SIZE, tabSize, first);
	}

	protected void encodeUseSoftTabs(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean useSoftTabs, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.USE_SOFT_TABS, useSoftTabs, first);
	}

	protected void encodeUseWrapMode(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean useWrapMode, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.USE_WRAP_MODE, useWrapMode, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, AceEditorComponent.VISIBLE, visible, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Object width, boolean first) throws IOException {
		encodeNumber(responseWriter, AceEditorComponent.WIDTH, width, first);
	}
}
//J+
