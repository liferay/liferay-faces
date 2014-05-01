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

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.AlloyRendererBase;
import com.liferay.faces.alloy.util.AlloyConstants;
import com.liferay.faces.util.lang.StringPool;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class TabViewRendererBase extends AlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "TabView";
	private static final String ALLOY_MODULE_NAME = "aui-tabview";
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

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		TabViewAlloy tabViewAlloy = (TabViewAlloy) uiComponent;
		boolean first = true;

		String activeDescendant = tabViewAlloy.getActiveDescendant();

		if (activeDescendant != null) {

			encodeActiveDescendant(responseWriter, tabViewAlloy, activeDescendant, first);
			first = false;
		}

		String boundingBox = tabViewAlloy.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, tabViewAlloy, boundingBox, first);
			first = false;
		}

		String contentBox = tabViewAlloy.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, tabViewAlloy, contentBox, first);
			first = false;
		}

		String defaultChildType = tabViewAlloy.getDefaultChildType();

		if (defaultChildType != null) {

			encodeDefaultChildType(responseWriter, tabViewAlloy, defaultChildType, first);
			first = false;
		}

		Boolean destroyed = tabViewAlloy.isDestroyed();

		if (destroyed != null) {

			encodeDestroyed(responseWriter, tabViewAlloy, destroyed, first);
			first = false;
		}

		Boolean disabled = tabViewAlloy.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, tabViewAlloy, disabled, first);
			first = false;
		}

		Boolean focused = tabViewAlloy.isFocused();

		if (focused != null) {

			encodeFocused(responseWriter, tabViewAlloy, focused, first);
			first = false;
		}

		Object height = tabViewAlloy.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, tabViewAlloy, height, first);
			first = false;
		}

		String id = tabViewAlloy.getId();

		if (id != null) {

			encodeId(responseWriter, tabViewAlloy, id, first);
			first = false;
		}

		Boolean initialized = tabViewAlloy.isInitialized();

		if (initialized != null) {

			encodeInitialized(responseWriter, tabViewAlloy, initialized, first);
			first = false;
		}

		String locale = tabViewAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, tabViewAlloy, locale, first);
			first = false;
		}

		Boolean multiple = tabViewAlloy.isMultiple();

		if (multiple != null) {

			encodeMultiple(responseWriter, tabViewAlloy, multiple, first);
			first = false;
		}

		Boolean rendered = tabViewAlloy.isRendered();

		if (rendered != null) {

			encodeRendered(responseWriter, tabViewAlloy, rendered, first);
			first = false;
		}

		Object selection = tabViewAlloy.getSelection();

		if (selection != null) {

			encodeSelection(responseWriter, tabViewAlloy, selection, first);
			first = false;
		}

		String srcNode = tabViewAlloy.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, tabViewAlloy, srcNode, first);
			first = false;
		}

		Boolean stacked = tabViewAlloy.isStacked();

		if (stacked != null) {

			encodeStacked(responseWriter, tabViewAlloy, stacked, first);
			first = false;
		}

		Object strings = tabViewAlloy.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, tabViewAlloy, strings, first);
			first = false;
		}

		Object tabIndex = tabViewAlloy.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, tabViewAlloy, tabIndex, first);
			first = false;
		}

		String type = tabViewAlloy.getType();

		if (type != null) {

			encodeType(responseWriter, tabViewAlloy, type, first);
			first = false;
		}

		Boolean visible = tabViewAlloy.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, tabViewAlloy, visible, first);
			first = false;
		}

		Boolean widgetRender = tabViewAlloy.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, tabViewAlloy, widgetRender, first);
			first = false;
		}

		Object width = tabViewAlloy.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, tabViewAlloy, width, first);
			first = false;
		}

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterActiveDescendantChange = tabViewAlloy.getAfterActiveDescendantChange();

		if (afterActiveDescendantChange != null) {

			encodeAfterActiveDescendantChange(responseWriter, tabViewAlloy, afterActiveDescendantChange, first);
			first = false;
		}

		String afterBoundingBoxChange = tabViewAlloy.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, tabViewAlloy, afterBoundingBoxChange, first);
			first = false;
		}

		String afterContentBoxChange = tabViewAlloy.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, tabViewAlloy, afterContentBoxChange, first);
			first = false;
		}

		String afterDefaultChildTypeChange = tabViewAlloy.getAfterDefaultChildTypeChange();

		if (afterDefaultChildTypeChange != null) {

			encodeAfterDefaultChildTypeChange(responseWriter, tabViewAlloy, afterDefaultChildTypeChange, first);
			first = false;
		}

		String afterDestroyedChange = tabViewAlloy.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, tabViewAlloy, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = tabViewAlloy.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, tabViewAlloy, afterDisabledChange, first);
			first = false;
		}

		String afterFocusedChange = tabViewAlloy.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, tabViewAlloy, afterFocusedChange, first);
			first = false;
		}

		String afterHeightChange = tabViewAlloy.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, tabViewAlloy, afterHeightChange, first);
			first = false;
		}

		String afterIdChange = tabViewAlloy.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, tabViewAlloy, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = tabViewAlloy.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, tabViewAlloy, afterInitializedChange, first);
			first = false;
		}

		String afterLocaleChange = tabViewAlloy.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, tabViewAlloy, afterLocaleChange, first);
			first = false;
		}

		String afterMultipleChange = tabViewAlloy.getAfterMultipleChange();

		if (afterMultipleChange != null) {

			encodeAfterMultipleChange(responseWriter, tabViewAlloy, afterMultipleChange, first);
			first = false;
		}

		String afterRenderChange = tabViewAlloy.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, tabViewAlloy, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = tabViewAlloy.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, tabViewAlloy, afterRenderedChange, first);
			first = false;
		}

		String afterSelectionChange = tabViewAlloy.getAfterSelectionChange();

		if (afterSelectionChange != null) {

			encodeAfterSelectionChange(responseWriter, tabViewAlloy, afterSelectionChange, first);
			first = false;
		}

		String afterSrcNodeChange = tabViewAlloy.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, tabViewAlloy, afterSrcNodeChange, first);
			first = false;
		}

		String afterStackedChange = tabViewAlloy.getAfterStackedChange();

		if (afterStackedChange != null) {

			encodeAfterStackedChange(responseWriter, tabViewAlloy, afterStackedChange, first);
			first = false;
		}

		String afterStringsChange = tabViewAlloy.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, tabViewAlloy, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = tabViewAlloy.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, tabViewAlloy, afterTabIndexChange, first);
			first = false;
		}

		String afterTypeChange = tabViewAlloy.getAfterTypeChange();

		if (afterTypeChange != null) {

			encodeAfterTypeChange(responseWriter, tabViewAlloy, afterTypeChange, first);
			first = false;
		}

		String afterVisibleChange = tabViewAlloy.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, tabViewAlloy, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = tabViewAlloy.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, tabViewAlloy, afterWidthChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onActiveDescendantChange = tabViewAlloy.getOnActiveDescendantChange();

		if (onActiveDescendantChange != null) {

			encodeOnActiveDescendantChange(responseWriter, tabViewAlloy, onActiveDescendantChange, first);
			first = false;
		}

		String onBoundingBoxChange = tabViewAlloy.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, tabViewAlloy, onBoundingBoxChange, first);
			first = false;
		}

		String onContentBoxChange = tabViewAlloy.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, tabViewAlloy, onContentBoxChange, first);
			first = false;
		}

		String onDefaultChildTypeChange = tabViewAlloy.getOnDefaultChildTypeChange();

		if (onDefaultChildTypeChange != null) {

			encodeOnDefaultChildTypeChange(responseWriter, tabViewAlloy, onDefaultChildTypeChange, first);
			first = false;
		}

		String onDestroyedChange = tabViewAlloy.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, tabViewAlloy, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = tabViewAlloy.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, tabViewAlloy, onDisabledChange, first);
			first = false;
		}

		String onFocusedChange = tabViewAlloy.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, tabViewAlloy, onFocusedChange, first);
			first = false;
		}

		String onHeightChange = tabViewAlloy.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, tabViewAlloy, onHeightChange, first);
			first = false;
		}

		String onIdChange = tabViewAlloy.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, tabViewAlloy, onIdChange, first);
			first = false;
		}

		String onInitializedChange = tabViewAlloy.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, tabViewAlloy, onInitializedChange, first);
			first = false;
		}

		String onLocaleChange = tabViewAlloy.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, tabViewAlloy, onLocaleChange, first);
			first = false;
		}

		String onMultipleChange = tabViewAlloy.getOnMultipleChange();

		if (onMultipleChange != null) {

			encodeOnMultipleChange(responseWriter, tabViewAlloy, onMultipleChange, first);
			first = false;
		}

		String onRenderChange = tabViewAlloy.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, tabViewAlloy, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = tabViewAlloy.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, tabViewAlloy, onRenderedChange, first);
			first = false;
		}

		String onSelectionChange = tabViewAlloy.getOnSelectionChange();

		if (onSelectionChange != null) {

			encodeOnSelectionChange(responseWriter, tabViewAlloy, onSelectionChange, first);
			first = false;
		}

		String onSrcNodeChange = tabViewAlloy.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, tabViewAlloy, onSrcNodeChange, first);
			first = false;
		}

		String onStackedChange = tabViewAlloy.getOnStackedChange();

		if (onStackedChange != null) {

			encodeOnStackedChange(responseWriter, tabViewAlloy, onStackedChange, first);
			first = false;
		}

		String onStringsChange = tabViewAlloy.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, tabViewAlloy, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = tabViewAlloy.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, tabViewAlloy, onTabIndexChange, first);
			first = false;
		}

		String onTypeChange = tabViewAlloy.getOnTypeChange();

		if (onTypeChange != null) {

			encodeOnTypeChange(responseWriter, tabViewAlloy, onTypeChange, first);
			first = false;
		}

		String onVisibleChange = tabViewAlloy.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, tabViewAlloy, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = tabViewAlloy.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, tabViewAlloy, onWidthChange, first);
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

	protected void encodeActiveDescendant(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String activeDescendant, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.ACTIVE_DESCENDANT, activeDescendant, first);
	}

	protected void encodeAfterActiveDescendantChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterActiveDescendantChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ACTIVE_DESCENDANT_CHANGE, afterActiveDescendantChange, first);
	}

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterDefaultChildTypeChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterDefaultChildTypeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_CHILD_TYPE_CHANGE, afterDefaultChildTypeChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterMultipleChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterMultipleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MULTIPLE_CHANGE, afterMultipleChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterSelectionChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterSelectionChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTION_CHANGE, afterSelectionChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStackedChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterStackedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STACKED_CHANGE, afterStackedChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterTypeChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterTypeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TYPE_CHANGE, afterTypeChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.CONTENT_BOX, contentBox, first);
	}

	protected void encodeDefaultChildType(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String defaultChildType, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.DEFAULT_CHILD_TYPE, defaultChildType, first);
	}

	protected void encodeDestroyed(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean destroyed, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.DESTROYED, destroyed, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.DISABLED, disabled, first);
	}

	protected void encodeFocused(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean focused, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.FOCUSED, focused, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Object height, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, TabViewAlloy.HEIGHT, height, first);
	}

	protected void encodeId(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String id, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.ID, id, first);
	}

	protected void encodeInitialized(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean initialized, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.INITIALIZED, initialized, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String locale, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.LOCALE, locale, first);
	}

	protected void encodeMultiple(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean multiple, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.MULTIPLE, multiple, first);
	}

	protected void encodeOnActiveDescendantChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onActiveDescendantChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ACTIVE_DESCENDANT_CHANGE, onActiveDescendantChange, first);
	}

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnDefaultChildTypeChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onDefaultChildTypeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_CHILD_TYPE_CHANGE, onDefaultChildTypeChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnMultipleChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onMultipleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MULTIPLE_CHANGE, onMultipleChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnSelectionChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onSelectionChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTION_CHANGE, onSelectionChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStackedChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onStackedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STACKED_CHANGE, onStackedChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnTypeChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onTypeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TYPE_CHANGE, onTypeChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
	}

	protected void encodeRendered(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean rendered, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.RENDERED, rendered, first);
	}

	protected void encodeSelection(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Object selection, boolean first) throws IOException {
		encodeObject(responseWriter, TabViewAlloy.SELECTION, selection, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.SRC_NODE, srcNode, first);
	}

	protected void encodeStacked(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean stacked, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.STACKED, stacked, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, TabViewAlloy.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, TabViewAlloy.TAB_INDEX, tabIndex, first);
	}

	protected void encodeType(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String type, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.TYPE, type, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.VISIBLE, visible, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Object width, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, TabViewAlloy.WIDTH, width, first);
	}
}
//J+
