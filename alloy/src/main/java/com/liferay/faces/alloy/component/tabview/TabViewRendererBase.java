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
package com.liferay.faces.alloy.component.tabview;
//J-

import java.io.IOException;
import java.util.ArrayList;
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
public abstract class TabViewRendererBase extends AUIRendererBase {

	// Private Constants
	private static final String A_CLASS_NAME = "A.TabView";
	private static final String AUI_MODULE_NAME = "aui-tabview";
	private static final String ACTIVE_DESCENDANT_CHANGE = "activeDescendantChange";
	private static final String BOUNDING_BOX_CHANGE = "boundingBoxChange";
	private static final String CONTENT_BOX_CHANGE = "contentBoxChange";
	private static final String DEFAULT_CHILD_TYPE_CHANGE = "defaultChildTypeChange";
	private static final String DESTROYED_CHANGE = "destroyedChange";
	private static final String DISABLED_CHANGE = "disabledChange";
	private static final String FOCUSED_CHANGE = "focusedChange";
	private static final String HEIGHT_CHANGE = "heightChange";
	private static final String ID_CHANGE = "idChange";
	private static final String INITIALIZED_CHANGE = "initializedChange";
	private static final String LOCALE_CHANGE = "localeChange";
	private static final String MULTIPLE_CHANGE = "multipleChange";
	private static final String RENDER_CHANGE = "renderChange";
	private static final String RENDERED_CHANGE = "renderedChange";
	private static final String SELECTION_CHANGE = "selectionChange";
	private static final String SRC_NODE_CHANGE = "srcNodeChange";
	private static final String STACKED_CHANGE = "stackedChange";
	private static final String STRINGS_CHANGE = "stringsChange";
	private static final String TAB_INDEX_CHANGE = "tabIndexChange";
	private static final String TYPE_CHANGE = "typeChange";
	private static final String VISIBLE_CHANGE = "visibleChange";
	private static final String WIDTH_CHANGE = "widthChange";

	protected void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		TabViewComponent tabViewComponent = (TabViewComponent) uiComponent;
		String widgetVar = ComponentUtil.resolveWidgetVar(facesContext, (Widget) tabViewComponent);

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

		Object activeDescendant = tabViewComponent.getActiveDescendant();

		if (activeDescendant != null) {

			encodeActiveDescendant(responseWriter, tabViewComponent, activeDescendant, first);
			first = false;
		}

		String boundingBox = tabViewComponent.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, tabViewComponent, boundingBox, first);
			first = false;
		}

		String contentBox = tabViewComponent.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, tabViewComponent, contentBox, first);
			first = false;
		}

		Object defaultChildType = tabViewComponent.getDefaultChildType();

		if (defaultChildType != null) {

			encodeDefaultChildType(responseWriter, tabViewComponent, defaultChildType, first);
			first = false;
		}

		Boolean destroyed = tabViewComponent.isDestroyed();

		if (destroyed != null) {

			encodeDestroyed(responseWriter, tabViewComponent, destroyed, first);
			first = false;
		}

		Boolean focused = tabViewComponent.isFocused();

		if (focused != null) {

			encodeFocused(responseWriter, tabViewComponent, focused, first);
			first = false;
		}

		Object height = tabViewComponent.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, tabViewComponent, height, first);
			first = false;
		}

		Boolean initialized = tabViewComponent.isInitialized();

		if (initialized != null) {

			encodeInitialized(responseWriter, tabViewComponent, initialized, first);
			first = false;
		}

		String locale = tabViewComponent.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, tabViewComponent, locale, first);
			first = false;
		}

		Boolean multiple = tabViewComponent.isMultiple();

		if (multiple != null) {

			encodeMultiple(responseWriter, tabViewComponent, multiple, first);
			first = false;
		}

		Object render = tabViewComponent.getRender();

		if (render != null) {

			encodeRender(responseWriter, tabViewComponent, render, first);
			first = false;
		}

		Object selection = tabViewComponent.getSelection();

		if (selection != null) {

			encodeSelection(responseWriter, tabViewComponent, selection, first);
			first = false;
		}

		String srcNode = tabViewComponent.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, tabViewComponent, srcNode, first);
			first = false;
		}

		Object stacked = tabViewComponent.getStacked();

		if (stacked != null) {

			encodeStacked(responseWriter, tabViewComponent, stacked, first);
			first = false;
		}

		Object strings = tabViewComponent.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, tabViewComponent, strings, first);
			first = false;
		}

		Object tabIndex = tabViewComponent.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, tabViewComponent, tabIndex, first);
			first = false;
		}

		Boolean tabViewDisabled = tabViewComponent.isTabViewDisabled();

		if (tabViewDisabled != null) {

			encodeTabViewDisabled(responseWriter, tabViewComponent, tabViewDisabled, first);
			first = false;
		}

		String tabViewId = tabViewComponent.getTabViewId();

		if (tabViewId != null) {

			encodeTabViewId(responseWriter, tabViewComponent, tabViewId, first);
			first = false;
		}

		Boolean tabViewRendered = tabViewComponent.isTabViewRendered();

		if (tabViewRendered != null) {

			encodeTabViewRendered(responseWriter, tabViewComponent, tabViewRendered, first);
			first = false;
		}

		Object type = tabViewComponent.getType();

		if (type != null) {

			encodeType(responseWriter, tabViewComponent, type, first);
			first = false;
		}

		Boolean visible = tabViewComponent.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, tabViewComponent, visible, first);
			first = false;
		}

		Object width = tabViewComponent.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, tabViewComponent, width, first);
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

		String afterActiveDescendantChange = tabViewComponent.getAfterActiveDescendantChange();

		if (afterActiveDescendantChange != null) {

			encodeAfterActiveDescendantChange(responseWriter, tabViewComponent, afterActiveDescendantChange, first);
			first = false;
		}

		String afterBoundingBoxChange = tabViewComponent.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, tabViewComponent, afterBoundingBoxChange, first);
			first = false;
		}

		String afterContentBoxChange = tabViewComponent.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, tabViewComponent, afterContentBoxChange, first);
			first = false;
		}

		String afterDefaultChildTypeChange = tabViewComponent.getAfterDefaultChildTypeChange();

		if (afterDefaultChildTypeChange != null) {

			encodeAfterDefaultChildTypeChange(responseWriter, tabViewComponent, afterDefaultChildTypeChange, first);
			first = false;
		}

		String afterDestroyedChange = tabViewComponent.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, tabViewComponent, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = tabViewComponent.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, tabViewComponent, afterDisabledChange, first);
			first = false;
		}

		String afterFocusedChange = tabViewComponent.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, tabViewComponent, afterFocusedChange, first);
			first = false;
		}

		String afterHeightChange = tabViewComponent.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, tabViewComponent, afterHeightChange, first);
			first = false;
		}

		String afterIdChange = tabViewComponent.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, tabViewComponent, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = tabViewComponent.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, tabViewComponent, afterInitializedChange, first);
			first = false;
		}

		String afterLocaleChange = tabViewComponent.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, tabViewComponent, afterLocaleChange, first);
			first = false;
		}

		String afterMultipleChange = tabViewComponent.getAfterMultipleChange();

		if (afterMultipleChange != null) {

			encodeAfterMultipleChange(responseWriter, tabViewComponent, afterMultipleChange, first);
			first = false;
		}

		String afterRenderChange = tabViewComponent.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, tabViewComponent, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = tabViewComponent.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, tabViewComponent, afterRenderedChange, first);
			first = false;
		}

		String afterSelectionChange = tabViewComponent.getAfterSelectionChange();

		if (afterSelectionChange != null) {

			encodeAfterSelectionChange(responseWriter, tabViewComponent, afterSelectionChange, first);
			first = false;
		}

		String afterSrcNodeChange = tabViewComponent.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, tabViewComponent, afterSrcNodeChange, first);
			first = false;
		}

		String afterStackedChange = tabViewComponent.getAfterStackedChange();

		if (afterStackedChange != null) {

			encodeAfterStackedChange(responseWriter, tabViewComponent, afterStackedChange, first);
			first = false;
		}

		String afterStringsChange = tabViewComponent.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, tabViewComponent, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = tabViewComponent.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, tabViewComponent, afterTabIndexChange, first);
			first = false;
		}

		String afterTypeChange = tabViewComponent.getAfterTypeChange();

		if (afterTypeChange != null) {

			encodeAfterTypeChange(responseWriter, tabViewComponent, afterTypeChange, first);
			first = false;
		}

		String afterVisibleChange = tabViewComponent.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, tabViewComponent, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = tabViewComponent.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, tabViewComponent, afterWidthChange, first);
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

		String onActiveDescendantChange = tabViewComponent.getOnActiveDescendantChange();

		if (onActiveDescendantChange != null) {

			encodeOnActiveDescendantChange(responseWriter, tabViewComponent, onActiveDescendantChange, first);
			first = false;
		}

		String onBoundingBoxChange = tabViewComponent.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, tabViewComponent, onBoundingBoxChange, first);
			first = false;
		}

		String onContentBoxChange = tabViewComponent.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, tabViewComponent, onContentBoxChange, first);
			first = false;
		}

		String onDefaultChildTypeChange = tabViewComponent.getOnDefaultChildTypeChange();

		if (onDefaultChildTypeChange != null) {

			encodeOnDefaultChildTypeChange(responseWriter, tabViewComponent, onDefaultChildTypeChange, first);
			first = false;
		}

		String onDestroyedChange = tabViewComponent.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, tabViewComponent, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = tabViewComponent.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, tabViewComponent, onDisabledChange, first);
			first = false;
		}

		String onFocusedChange = tabViewComponent.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, tabViewComponent, onFocusedChange, first);
			first = false;
		}

		String onHeightChange = tabViewComponent.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, tabViewComponent, onHeightChange, first);
			first = false;
		}

		String onIdChange = tabViewComponent.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, tabViewComponent, onIdChange, first);
			first = false;
		}

		String onInitializedChange = tabViewComponent.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, tabViewComponent, onInitializedChange, first);
			first = false;
		}

		String onLocaleChange = tabViewComponent.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, tabViewComponent, onLocaleChange, first);
			first = false;
		}

		String onMultipleChange = tabViewComponent.getOnMultipleChange();

		if (onMultipleChange != null) {

			encodeOnMultipleChange(responseWriter, tabViewComponent, onMultipleChange, first);
			first = false;
		}

		String onRenderChange = tabViewComponent.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, tabViewComponent, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = tabViewComponent.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, tabViewComponent, onRenderedChange, first);
			first = false;
		}

		String onSelectionChange = tabViewComponent.getOnSelectionChange();

		if (onSelectionChange != null) {

			encodeOnSelectionChange(responseWriter, tabViewComponent, onSelectionChange, first);
			first = false;
		}

		String onSrcNodeChange = tabViewComponent.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, tabViewComponent, onSrcNodeChange, first);
			first = false;
		}

		String onStackedChange = tabViewComponent.getOnStackedChange();

		if (onStackedChange != null) {

			encodeOnStackedChange(responseWriter, tabViewComponent, onStackedChange, first);
			first = false;
		}

		String onStringsChange = tabViewComponent.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, tabViewComponent, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = tabViewComponent.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, tabViewComponent, onTabIndexChange, first);
			first = false;
		}

		String onTypeChange = tabViewComponent.getOnTypeChange();

		if (onTypeChange != null) {

			encodeOnTypeChange(responseWriter, tabViewComponent, onTypeChange, first);
			first = false;
		}

		String onVisibleChange = tabViewComponent.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, tabViewComponent, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = tabViewComponent.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, tabViewComponent, onWidthChange, first);
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

		List<String> modules = new ArrayList<String>();
		modules.add(AUI_MODULE_NAME);

		return modules;
	}

	protected void encodeActiveDescendant(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Object activeDescendant, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.ACTIVE_DESCENDANT, activeDescendant, first);
	}

	protected void encodeAfterActiveDescendantChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterActiveDescendantChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ACTIVE_DESCENDANT_CHANGE, afterActiveDescendantChange, first);
	}

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterDefaultChildTypeChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterDefaultChildTypeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_CHILD_TYPE_CHANGE, afterDefaultChildTypeChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterMultipleChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterMultipleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MULTIPLE_CHANGE, afterMultipleChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterSelectionChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterSelectionChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTION_CHANGE, afterSelectionChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStackedChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterStackedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STACKED_CHANGE, afterStackedChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterTypeChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterTypeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TYPE_CHANGE, afterTypeChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.CONTENT_BOX, contentBox, first);
	}

	protected void encodeDefaultChildType(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Object defaultChildType, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.DEFAULT_CHILD_TYPE, defaultChildType, first);
	}

	protected void encodeDestroyed(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Boolean destroyed, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewComponent.DESTROYED, destroyed, first);
	}

	protected void encodeFocused(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Boolean focused, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewComponent.FOCUSED, focused, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Object height, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.HEIGHT, height, first);
	}

	protected void encodeInitialized(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Boolean initialized, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewComponent.INITIALIZED, initialized, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String locale, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.LOCALE, locale, first);
	}

	protected void encodeMultiple(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Boolean multiple, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewComponent.MULTIPLE, multiple, first);
	}

	protected void encodeOnActiveDescendantChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onActiveDescendantChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ACTIVE_DESCENDANT_CHANGE, onActiveDescendantChange, first);
	}

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnDefaultChildTypeChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onDefaultChildTypeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_CHILD_TYPE_CHANGE, onDefaultChildTypeChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnMultipleChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onMultipleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MULTIPLE_CHANGE, onMultipleChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnSelectionChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onSelectionChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTION_CHANGE, onSelectionChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStackedChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onStackedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STACKED_CHANGE, onStackedChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnTypeChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onTypeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TYPE_CHANGE, onTypeChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
	}

	protected void encodeRender(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Object render, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.RENDER, render, first);
	}

	protected void encodeSelection(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Object selection, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.SELECTION, selection, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.SRC_NODE, srcNode, first);
	}

	protected void encodeStacked(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Object stacked, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.STACKED, stacked, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, TabViewComponent.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, TabViewComponent.TAB_INDEX, tabIndex, first);
	}

	protected void encodeTabViewDisabled(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Boolean tabViewDisabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewComponent.TAB_VIEW_DISABLED, tabViewDisabled, first);
	}

	protected void encodeTabViewId(ResponseWriter responseWriter, TabViewComponent tabViewComponent, String tabViewId, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.TAB_VIEW_ID, tabViewId, first);
	}

	protected void encodeTabViewRendered(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Boolean tabViewRendered, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewComponent.TAB_VIEW_RENDERED, tabViewRendered, first);
	}

	protected void encodeType(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Object type, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.TYPE, type, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewComponent.VISIBLE, visible, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, TabViewComponent tabViewComponent, Object width, boolean first) throws IOException {
		encodeString(responseWriter, TabViewComponent.WIDTH, width, first);
	}
}
//J+
