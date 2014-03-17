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

import javax.annotation.Generated;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface TabViewComponent {

	// Public Constants
	public static final String ACTIVE_DESCENDANT = "activeDescendant";
	public static final String AFTER_ACTIVE_DESCENDANT_CHANGE = "afterActiveDescendantChange";
	public static final String AFTER_BOUNDING_BOX_CHANGE = "afterBoundingBoxChange";
	public static final String AFTER_CONTENT_BOX_CHANGE = "afterContentBoxChange";
	public static final String AFTER_DEFAULT_CHILD_TYPE_CHANGE = "afterDefaultChildTypeChange";
	public static final String AFTER_DESTROYED_CHANGE = "afterDestroyedChange";
	public static final String AFTER_DISABLED_CHANGE = "afterDisabledChange";
	public static final String AFTER_FOCUSED_CHANGE = "afterFocusedChange";
	public static final String AFTER_HEIGHT_CHANGE = "afterHeightChange";
	public static final String AFTER_ID_CHANGE = "afterIdChange";
	public static final String AFTER_INITIALIZED_CHANGE = "afterInitializedChange";
	public static final String AFTER_LOCALE_CHANGE = "afterLocaleChange";
	public static final String AFTER_MULTIPLE_CHANGE = "afterMultipleChange";
	public static final String AFTER_RENDER_CHANGE = "afterRenderChange";
	public static final String AFTER_RENDERED_CHANGE = "afterRenderedChange";
	public static final String AFTER_SELECTION_CHANGE = "afterSelectionChange";
	public static final String AFTER_SRC_NODE_CHANGE = "afterSrcNodeChange";
	public static final String AFTER_STACKED_CHANGE = "afterStackedChange";
	public static final String AFTER_STRINGS_CHANGE = "afterStringsChange";
	public static final String AFTER_TAB_INDEX_CHANGE = "afterTabIndexChange";
	public static final String AFTER_TYPE_CHANGE = "afterTypeChange";
	public static final String AFTER_VISIBLE_CHANGE = "afterVisibleChange";
	public static final String AFTER_WIDTH_CHANGE = "afterWidthChange";
	public static final String BOUNDING_BOX = "boundingBox";
	public static final String CONTENT_BOX = "contentBox";
	public static final String DEFAULT_CHILD_TYPE = "defaultChildType";
	public static final String DESTROYED = "destroyed";
	public static final String FOCUSED = "focused";
	public static final String HEIGHT = "height";
	public static final String INITIALIZED = "initialized";
	public static final String LOCALE = "locale";
	public static final String MULTIPLE = "multiple";
	public static final String ON_ACTIVE_DESCENDANT_CHANGE = "onActiveDescendantChange";
	public static final String ON_BOUNDING_BOX_CHANGE = "onBoundingBoxChange";
	public static final String ON_CONTENT_BOX_CHANGE = "onContentBoxChange";
	public static final String ON_DEFAULT_CHILD_TYPE_CHANGE = "onDefaultChildTypeChange";
	public static final String ON_DESTROYED_CHANGE = "onDestroyedChange";
	public static final String ON_DISABLED_CHANGE = "onDisabledChange";
	public static final String ON_FOCUSED_CHANGE = "onFocusedChange";
	public static final String ON_HEIGHT_CHANGE = "onHeightChange";
	public static final String ON_ID_CHANGE = "onIdChange";
	public static final String ON_INITIALIZED_CHANGE = "onInitializedChange";
	public static final String ON_LOCALE_CHANGE = "onLocaleChange";
	public static final String ON_MULTIPLE_CHANGE = "onMultipleChange";
	public static final String ON_RENDER_CHANGE = "onRenderChange";
	public static final String ON_RENDERED_CHANGE = "onRenderedChange";
	public static final String ON_SELECTION_CHANGE = "onSelectionChange";
	public static final String ON_SRC_NODE_CHANGE = "onSrcNodeChange";
	public static final String ON_STACKED_CHANGE = "onStackedChange";
	public static final String ON_STRINGS_CHANGE = "onStringsChange";
	public static final String ON_TAB_INDEX_CHANGE = "onTabIndexChange";
	public static final String ON_TYPE_CHANGE = "onTypeChange";
	public static final String ON_VISIBLE_CHANGE = "onVisibleChange";
	public static final String ON_WIDTH_CHANGE = "onWidthChange";
	public static final String RENDER = "render";
	public static final String SELECTION = "selection";
	public static final String SRC_NODE = "srcNode";
	public static final String STACKED = "stacked";
	public static final String STRINGS = "strings";
	public static final String TAB_INDEX = "tabIndex";
	public static final String TAB_VIEW_DISABLED = "disabled";
	public static final String TAB_VIEW_ID = "id";
	public static final String TAB_VIEW_RENDERED = "rendered";
	public static final String TYPE = "type";
	public static final String VISIBLE = "visible";
	public static final String WIDTH = "width";

	public Object getActiveDescendant();

	public void setActiveDescendant(Object activeDescendant);

	public String getAfterActiveDescendantChange();

	public void setAfterActiveDescendantChange(String afterActiveDescendantChange);

	public String getAfterBoundingBoxChange();

	public void setAfterBoundingBoxChange(String afterBoundingBoxChange);

	public String getAfterContentBoxChange();

	public void setAfterContentBoxChange(String afterContentBoxChange);

	public String getAfterDefaultChildTypeChange();

	public void setAfterDefaultChildTypeChange(String afterDefaultChildTypeChange);

	public String getAfterDestroyedChange();

	public void setAfterDestroyedChange(String afterDestroyedChange);

	public String getAfterDisabledChange();

	public void setAfterDisabledChange(String afterDisabledChange);

	public String getAfterFocusedChange();

	public void setAfterFocusedChange(String afterFocusedChange);

	public String getAfterHeightChange();

	public void setAfterHeightChange(String afterHeightChange);

	public String getAfterIdChange();

	public void setAfterIdChange(String afterIdChange);

	public String getAfterInitializedChange();

	public void setAfterInitializedChange(String afterInitializedChange);

	public String getAfterLocaleChange();

	public void setAfterLocaleChange(String afterLocaleChange);

	public String getAfterMultipleChange();

	public void setAfterMultipleChange(String afterMultipleChange);

	public String getAfterRenderChange();

	public void setAfterRenderChange(String afterRenderChange);

	public String getAfterRenderedChange();

	public void setAfterRenderedChange(String afterRenderedChange);

	public String getAfterSelectionChange();

	public void setAfterSelectionChange(String afterSelectionChange);

	public String getAfterSrcNodeChange();

	public void setAfterSrcNodeChange(String afterSrcNodeChange);

	public String getAfterStackedChange();

	public void setAfterStackedChange(String afterStackedChange);

	public String getAfterStringsChange();

	public void setAfterStringsChange(String afterStringsChange);

	public String getAfterTabIndexChange();

	public void setAfterTabIndexChange(String afterTabIndexChange);

	public String getAfterTypeChange();

	public void setAfterTypeChange(String afterTypeChange);

	public String getAfterVisibleChange();

	public void setAfterVisibleChange(String afterVisibleChange);

	public String getAfterWidthChange();

	public void setAfterWidthChange(String afterWidthChange);

	public String getBoundingBox();

	public void setBoundingBox(String boundingBox);

	public String getContentBox();

	public void setContentBox(String contentBox);

	public Object getDefaultChildType();

	public void setDefaultChildType(Object defaultChildType);

	public Boolean isDestroyed();

	public void setDestroyed(Boolean destroyed);

	public Boolean isFocused();

	public void setFocused(Boolean focused);

	public Object getHeight();

	public void setHeight(Object height);

	public Boolean isInitialized();

	public void setInitialized(Boolean initialized);

	public String getLocale();

	public void setLocale(String locale);

	public Boolean isMultiple();

	public void setMultiple(Boolean multiple);

	public String getOnActiveDescendantChange();

	public void setOnActiveDescendantChange(String onActiveDescendantChange);

	public String getOnBoundingBoxChange();

	public void setOnBoundingBoxChange(String onBoundingBoxChange);

	public String getOnContentBoxChange();

	public void setOnContentBoxChange(String onContentBoxChange);

	public String getOnDefaultChildTypeChange();

	public void setOnDefaultChildTypeChange(String onDefaultChildTypeChange);

	public String getOnDestroyedChange();

	public void setOnDestroyedChange(String onDestroyedChange);

	public String getOnDisabledChange();

	public void setOnDisabledChange(String onDisabledChange);

	public String getOnFocusedChange();

	public void setOnFocusedChange(String onFocusedChange);

	public String getOnHeightChange();

	public void setOnHeightChange(String onHeightChange);

	public String getOnIdChange();

	public void setOnIdChange(String onIdChange);

	public String getOnInitializedChange();

	public void setOnInitializedChange(String onInitializedChange);

	public String getOnLocaleChange();

	public void setOnLocaleChange(String onLocaleChange);

	public String getOnMultipleChange();

	public void setOnMultipleChange(String onMultipleChange);

	public String getOnRenderChange();

	public void setOnRenderChange(String onRenderChange);

	public String getOnRenderedChange();

	public void setOnRenderedChange(String onRenderedChange);

	public String getOnSelectionChange();

	public void setOnSelectionChange(String onSelectionChange);

	public String getOnSrcNodeChange();

	public void setOnSrcNodeChange(String onSrcNodeChange);

	public String getOnStackedChange();

	public void setOnStackedChange(String onStackedChange);

	public String getOnStringsChange();

	public void setOnStringsChange(String onStringsChange);

	public String getOnTabIndexChange();

	public void setOnTabIndexChange(String onTabIndexChange);

	public String getOnTypeChange();

	public void setOnTypeChange(String onTypeChange);

	public String getOnVisibleChange();

	public void setOnVisibleChange(String onVisibleChange);

	public String getOnWidthChange();

	public void setOnWidthChange(String onWidthChange);

	public Object getRender();

	public void setRender(Object render);

	public Object getSelection();

	public void setSelection(Object selection);

	public String getSrcNode();

	public void setSrcNode(String srcNode);

	public Object getStacked();

	public void setStacked(Object stacked);

	public Object getStrings();

	public void setStrings(Object strings);

	public Object getTabIndex();

	public void setTabIndex(Object tabIndex);

	public Boolean isTabViewDisabled();

	public void setTabViewDisabled(Boolean tabViewDisabled);

	public String getTabViewId();

	public void setTabViewId(String tabViewId);

	public Boolean isTabViewRendered();

	public void setTabViewRendered(Boolean tabViewRendered);

	public Object getType();

	public void setType(Object type);

	public Boolean isVisible();

	public void setVisible(Boolean visible);

	public Object getWidth();

	public void setWidth(Object width);
}
//J+
