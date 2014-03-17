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

import javax.annotation.Generated;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface AceEditorComponent {

	// Public Constants
	public static final String ACE_EDITOR_DISABLED = "disabled";
	public static final String ACE_EDITOR_ID = "id";
	public static final String ACE_EDITOR_RENDERED = "rendered";
	public static final String ACE_EDITOR_VALUE = "value";
	public static final String AFTER_BOUNDING_BOX_CHANGE = "afterBoundingBoxChange";
	public static final String AFTER_CONTENT_BOX_CHANGE = "afterContentBoxChange";
	public static final String AFTER_DESTROYED_CHANGE = "afterDestroyedChange";
	public static final String AFTER_DISABLED_CHANGE = "afterDisabledChange";
	public static final String AFTER_FOCUSED_CHANGE = "afterFocusedChange";
	public static final String AFTER_HEIGHT_CHANGE = "afterHeightChange";
	public static final String AFTER_HIGHLIGHT_ACTIVE_LINE_CHANGE = "afterHighlightActiveLineChange";
	public static final String AFTER_ID_CHANGE = "afterIdChange";
	public static final String AFTER_INITIALIZED_CHANGE = "afterInitializedChange";
	public static final String AFTER_LOCALE_CHANGE = "afterLocaleChange";
	public static final String AFTER_MODE_CHANGE = "afterModeChange";
	public static final String AFTER_READ_ONLY_CHANGE = "afterReadOnlyChange";
	public static final String AFTER_RENDER_CHANGE = "afterRenderChange";
	public static final String AFTER_RENDERED_CHANGE = "afterRenderedChange";
	public static final String AFTER_SHOW_PRINT_MARGIN_CHANGE = "afterShowPrintMarginChange";
	public static final String AFTER_SRC_NODE_CHANGE = "afterSrcNodeChange";
	public static final String AFTER_STRINGS_CHANGE = "afterStringsChange";
	public static final String AFTER_TAB_INDEX_CHANGE = "afterTabIndexChange";
	public static final String AFTER_TAB_SIZE_CHANGE = "afterTabSizeChange";
	public static final String AFTER_USE_SOFT_TABS_CHANGE = "afterUseSoftTabsChange";
	public static final String AFTER_USE_WRAP_MODE_CHANGE = "afterUseWrapModeChange";
	public static final String AFTER_VALUE_CHANGE = "afterValueChange";
	public static final String AFTER_VISIBLE_CHANGE = "afterVisibleChange";
	public static final String AFTER_WIDTH_CHANGE = "afterWidthChange";
	public static final String BOUNDING_BOX = "boundingBox";
	public static final String CONTENT_BOX = "contentBox";
	public static final String DESTROYED = "destroyed";
	public static final String FOCUSED = "focused";
	public static final String HEIGHT = "height";
	public static final String HIGHLIGHT_ACTIVE_LINE = "highlightActiveLine";
	public static final String INITIALIZED = "initialized";
	public static final String LOCALE = "locale";
	public static final String MODE = "mode";
	public static final String ON_BOUNDING_BOX_CHANGE = "onBoundingBoxChange";
	public static final String ON_CONTENT_BOX_CHANGE = "onContentBoxChange";
	public static final String ON_DESTROYED_CHANGE = "onDestroyedChange";
	public static final String ON_DISABLED_CHANGE = "onDisabledChange";
	public static final String ON_FOCUSED_CHANGE = "onFocusedChange";
	public static final String ON_HEIGHT_CHANGE = "onHeightChange";
	public static final String ON_HIGHLIGHT_ACTIVE_LINE_CHANGE = "onHighlightActiveLineChange";
	public static final String ON_ID_CHANGE = "onIdChange";
	public static final String ON_INITIALIZED_CHANGE = "onInitializedChange";
	public static final String ON_LOCALE_CHANGE = "onLocaleChange";
	public static final String ON_MODE_CHANGE = "onModeChange";
	public static final String ON_READ_ONLY_CHANGE = "onReadOnlyChange";
	public static final String ON_RENDER_CHANGE = "onRenderChange";
	public static final String ON_RENDERED_CHANGE = "onRenderedChange";
	public static final String ON_SHOW_PRINT_MARGIN_CHANGE = "onShowPrintMarginChange";
	public static final String ON_SRC_NODE_CHANGE = "onSrcNodeChange";
	public static final String ON_STRINGS_CHANGE = "onStringsChange";
	public static final String ON_TAB_INDEX_CHANGE = "onTabIndexChange";
	public static final String ON_TAB_SIZE_CHANGE = "onTabSizeChange";
	public static final String ON_USE_SOFT_TABS_CHANGE = "onUseSoftTabsChange";
	public static final String ON_USE_WRAP_MODE_CHANGE = "onUseWrapModeChange";
	public static final String ON_VALUE_CHANGE = "onValueChange";
	public static final String ON_VISIBLE_CHANGE = "onVisibleChange";
	public static final String ON_WIDTH_CHANGE = "onWidthChange";
	public static final String READ_ONLY = "readOnly";
	public static final String RENDER = "render";
	public static final String SHOW_PRINT_MARGIN = "showPrintMargin";
	public static final String SRC_NODE = "srcNode";
	public static final String STRINGS = "strings";
	public static final String TAB_INDEX = "tabIndex";
	public static final String TAB_SIZE = "tabSize";
	public static final String USE_SOFT_TABS = "useSoftTabs";
	public static final String USE_WRAP_MODE = "useWrapMode";
	public static final String VISIBLE = "visible";
	public static final String WIDTH = "width";

	public Boolean isAceEditorDisabled();

	public void setAceEditorDisabled(Boolean aceEditorDisabled);

	public String getAceEditorId();

	public void setAceEditorId(String aceEditorId);

	public Boolean isAceEditorRendered();

	public void setAceEditorRendered(Boolean aceEditorRendered);

	public String getAceEditorValue();

	public void setAceEditorValue(String aceEditorValue);

	public String getAfterBoundingBoxChange();

	public void setAfterBoundingBoxChange(String afterBoundingBoxChange);

	public String getAfterContentBoxChange();

	public void setAfterContentBoxChange(String afterContentBoxChange);

	public String getAfterDestroyedChange();

	public void setAfterDestroyedChange(String afterDestroyedChange);

	public String getAfterDisabledChange();

	public void setAfterDisabledChange(String afterDisabledChange);

	public String getAfterFocusedChange();

	public void setAfterFocusedChange(String afterFocusedChange);

	public String getAfterHeightChange();

	public void setAfterHeightChange(String afterHeightChange);

	public String getAfterHighlightActiveLineChange();

	public void setAfterHighlightActiveLineChange(String afterHighlightActiveLineChange);

	public String getAfterIdChange();

	public void setAfterIdChange(String afterIdChange);

	public String getAfterInitializedChange();

	public void setAfterInitializedChange(String afterInitializedChange);

	public String getAfterLocaleChange();

	public void setAfterLocaleChange(String afterLocaleChange);

	public String getAfterModeChange();

	public void setAfterModeChange(String afterModeChange);

	public String getAfterReadOnlyChange();

	public void setAfterReadOnlyChange(String afterReadOnlyChange);

	public String getAfterRenderChange();

	public void setAfterRenderChange(String afterRenderChange);

	public String getAfterRenderedChange();

	public void setAfterRenderedChange(String afterRenderedChange);

	public String getAfterShowPrintMarginChange();

	public void setAfterShowPrintMarginChange(String afterShowPrintMarginChange);

	public String getAfterSrcNodeChange();

	public void setAfterSrcNodeChange(String afterSrcNodeChange);

	public String getAfterStringsChange();

	public void setAfterStringsChange(String afterStringsChange);

	public String getAfterTabIndexChange();

	public void setAfterTabIndexChange(String afterTabIndexChange);

	public String getAfterTabSizeChange();

	public void setAfterTabSizeChange(String afterTabSizeChange);

	public String getAfterUseSoftTabsChange();

	public void setAfterUseSoftTabsChange(String afterUseSoftTabsChange);

	public String getAfterUseWrapModeChange();

	public void setAfterUseWrapModeChange(String afterUseWrapModeChange);

	public String getAfterValueChange();

	public void setAfterValueChange(String afterValueChange);

	public String getAfterVisibleChange();

	public void setAfterVisibleChange(String afterVisibleChange);

	public String getAfterWidthChange();

	public void setAfterWidthChange(String afterWidthChange);

	public String getBoundingBox();

	public void setBoundingBox(String boundingBox);

	public String getContentBox();

	public void setContentBox(String contentBox);

	public Boolean isDestroyed();

	public void setDestroyed(Boolean destroyed);

	public Boolean isFocused();

	public void setFocused(Boolean focused);

	public Object getHeight();

	public void setHeight(Object height);

	public Boolean isHighlightActiveLine();

	public void setHighlightActiveLine(Boolean highlightActiveLine);

	public Boolean isInitialized();

	public void setInitialized(Boolean initialized);

	public String getLocale();

	public void setLocale(String locale);

	public String getMode();

	public void setMode(String mode);

	public String getOnBoundingBoxChange();

	public void setOnBoundingBoxChange(String onBoundingBoxChange);

	public String getOnContentBoxChange();

	public void setOnContentBoxChange(String onContentBoxChange);

	public String getOnDestroyedChange();

	public void setOnDestroyedChange(String onDestroyedChange);

	public String getOnDisabledChange();

	public void setOnDisabledChange(String onDisabledChange);

	public String getOnFocusedChange();

	public void setOnFocusedChange(String onFocusedChange);

	public String getOnHeightChange();

	public void setOnHeightChange(String onHeightChange);

	public String getOnHighlightActiveLineChange();

	public void setOnHighlightActiveLineChange(String onHighlightActiveLineChange);

	public String getOnIdChange();

	public void setOnIdChange(String onIdChange);

	public String getOnInitializedChange();

	public void setOnInitializedChange(String onInitializedChange);

	public String getOnLocaleChange();

	public void setOnLocaleChange(String onLocaleChange);

	public String getOnModeChange();

	public void setOnModeChange(String onModeChange);

	public String getOnReadOnlyChange();

	public void setOnReadOnlyChange(String onReadOnlyChange);

	public String getOnRenderChange();

	public void setOnRenderChange(String onRenderChange);

	public String getOnRenderedChange();

	public void setOnRenderedChange(String onRenderedChange);

	public String getOnShowPrintMarginChange();

	public void setOnShowPrintMarginChange(String onShowPrintMarginChange);

	public String getOnSrcNodeChange();

	public void setOnSrcNodeChange(String onSrcNodeChange);

	public String getOnStringsChange();

	public void setOnStringsChange(String onStringsChange);

	public String getOnTabIndexChange();

	public void setOnTabIndexChange(String onTabIndexChange);

	public String getOnTabSizeChange();

	public void setOnTabSizeChange(String onTabSizeChange);

	public String getOnUseSoftTabsChange();

	public void setOnUseSoftTabsChange(String onUseSoftTabsChange);

	public String getOnUseWrapModeChange();

	public void setOnUseWrapModeChange(String onUseWrapModeChange);

	public String getOnValueChange();

	public void setOnValueChange(String onValueChange);

	public String getOnVisibleChange();

	public void setOnVisibleChange(String onVisibleChange);

	public String getOnWidthChange();

	public void setOnWidthChange(String onWidthChange);

	public Boolean isReadOnly();

	public void setReadOnly(Boolean readOnly);

	public Object getRender();

	public void setRender(Object render);

	public Boolean isShowPrintMargin();

	public void setShowPrintMargin(Boolean showPrintMargin);

	public String getSrcNode();

	public void setSrcNode(String srcNode);

	public Object getStrings();

	public void setStrings(Object strings);

	public Object getTabIndex();

	public void setTabIndex(Object tabIndex);

	public Object getTabSize();

	public void setTabSize(Object tabSize);

	public Boolean isUseSoftTabs();

	public void setUseSoftTabs(Boolean useSoftTabs);

	public Boolean isUseWrapMode();

	public void setUseWrapMode(Boolean useWrapMode);

	public Boolean isVisible();

	public void setVisible(Boolean visible);

	public Object getWidth();

	public void setWidth(Object width);
}
//J+
