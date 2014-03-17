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
import javax.faces.component.UIData;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.Widget;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class TabViewBase extends UIData implements Styleable, Widget, TabViewComponent {

	@Override
	public Object getActiveDescendant() {
		return (Object) getStateHelper().eval(ACTIVE_DESCENDANT, null);
	}

	@Override
	public void setActiveDescendant(Object activeDescendant) {
		getStateHelper().put(ACTIVE_DESCENDANT, activeDescendant);
	}

	@Override
	public String getAfterActiveDescendantChange() {
		return (String) getStateHelper().eval(AFTER_ACTIVE_DESCENDANT_CHANGE, null);
	}

	@Override
	public void setAfterActiveDescendantChange(String afterActiveDescendantChange) {
		getStateHelper().put(AFTER_ACTIVE_DESCENDANT_CHANGE, afterActiveDescendantChange);
	}

	@Override
	public String getAfterBoundingBoxChange() {
		return (String) getStateHelper().eval(AFTER_BOUNDING_BOX_CHANGE, null);
	}

	@Override
	public void setAfterBoundingBoxChange(String afterBoundingBoxChange) {
		getStateHelper().put(AFTER_BOUNDING_BOX_CHANGE, afterBoundingBoxChange);
	}

	@Override
	public String getAfterContentBoxChange() {
		return (String) getStateHelper().eval(AFTER_CONTENT_BOX_CHANGE, null);
	}

	@Override
	public void setAfterContentBoxChange(String afterContentBoxChange) {
		getStateHelper().put(AFTER_CONTENT_BOX_CHANGE, afterContentBoxChange);
	}

	@Override
	public String getAfterDefaultChildTypeChange() {
		return (String) getStateHelper().eval(AFTER_DEFAULT_CHILD_TYPE_CHANGE, null);
	}

	@Override
	public void setAfterDefaultChildTypeChange(String afterDefaultChildTypeChange) {
		getStateHelper().put(AFTER_DEFAULT_CHILD_TYPE_CHANGE, afterDefaultChildTypeChange);
	}

	@Override
	public String getAfterDestroyedChange() {
		return (String) getStateHelper().eval(AFTER_DESTROYED_CHANGE, null);
	}

	@Override
	public void setAfterDestroyedChange(String afterDestroyedChange) {
		getStateHelper().put(AFTER_DESTROYED_CHANGE, afterDestroyedChange);
	}

	@Override
	public String getAfterDisabledChange() {
		return (String) getStateHelper().eval(AFTER_DISABLED_CHANGE, null);
	}

	@Override
	public void setAfterDisabledChange(String afterDisabledChange) {
		getStateHelper().put(AFTER_DISABLED_CHANGE, afterDisabledChange);
	}

	@Override
	public String getAfterFocusedChange() {
		return (String) getStateHelper().eval(AFTER_FOCUSED_CHANGE, null);
	}

	@Override
	public void setAfterFocusedChange(String afterFocusedChange) {
		getStateHelper().put(AFTER_FOCUSED_CHANGE, afterFocusedChange);
	}

	@Override
	public String getAfterHeightChange() {
		return (String) getStateHelper().eval(AFTER_HEIGHT_CHANGE, null);
	}

	@Override
	public void setAfterHeightChange(String afterHeightChange) {
		getStateHelper().put(AFTER_HEIGHT_CHANGE, afterHeightChange);
	}

	@Override
	public String getAfterIdChange() {
		return (String) getStateHelper().eval(AFTER_ID_CHANGE, null);
	}

	@Override
	public void setAfterIdChange(String afterIdChange) {
		getStateHelper().put(AFTER_ID_CHANGE, afterIdChange);
	}

	@Override
	public String getAfterInitializedChange() {
		return (String) getStateHelper().eval(AFTER_INITIALIZED_CHANGE, null);
	}

	@Override
	public void setAfterInitializedChange(String afterInitializedChange) {
		getStateHelper().put(AFTER_INITIALIZED_CHANGE, afterInitializedChange);
	}

	@Override
	public String getAfterLocaleChange() {
		return (String) getStateHelper().eval(AFTER_LOCALE_CHANGE, null);
	}

	@Override
	public void setAfterLocaleChange(String afterLocaleChange) {
		getStateHelper().put(AFTER_LOCALE_CHANGE, afterLocaleChange);
	}

	@Override
	public String getAfterMultipleChange() {
		return (String) getStateHelper().eval(AFTER_MULTIPLE_CHANGE, null);
	}

	@Override
	public void setAfterMultipleChange(String afterMultipleChange) {
		getStateHelper().put(AFTER_MULTIPLE_CHANGE, afterMultipleChange);
	}

	@Override
	public String getAfterRenderChange() {
		return (String) getStateHelper().eval(AFTER_RENDER_CHANGE, null);
	}

	@Override
	public void setAfterRenderChange(String afterRenderChange) {
		getStateHelper().put(AFTER_RENDER_CHANGE, afterRenderChange);
	}

	@Override
	public String getAfterRenderedChange() {
		return (String) getStateHelper().eval(AFTER_RENDERED_CHANGE, null);
	}

	@Override
	public void setAfterRenderedChange(String afterRenderedChange) {
		getStateHelper().put(AFTER_RENDERED_CHANGE, afterRenderedChange);
	}

	@Override
	public String getAfterSelectionChange() {
		return (String) getStateHelper().eval(AFTER_SELECTION_CHANGE, null);
	}

	@Override
	public void setAfterSelectionChange(String afterSelectionChange) {
		getStateHelper().put(AFTER_SELECTION_CHANGE, afterSelectionChange);
	}

	@Override
	public String getAfterSrcNodeChange() {
		return (String) getStateHelper().eval(AFTER_SRC_NODE_CHANGE, null);
	}

	@Override
	public void setAfterSrcNodeChange(String afterSrcNodeChange) {
		getStateHelper().put(AFTER_SRC_NODE_CHANGE, afterSrcNodeChange);
	}

	@Override
	public String getAfterStackedChange() {
		return (String) getStateHelper().eval(AFTER_STACKED_CHANGE, null);
	}

	@Override
	public void setAfterStackedChange(String afterStackedChange) {
		getStateHelper().put(AFTER_STACKED_CHANGE, afterStackedChange);
	}

	@Override
	public String getAfterStringsChange() {
		return (String) getStateHelper().eval(AFTER_STRINGS_CHANGE, null);
	}

	@Override
	public void setAfterStringsChange(String afterStringsChange) {
		getStateHelper().put(AFTER_STRINGS_CHANGE, afterStringsChange);
	}

	@Override
	public String getAfterTabIndexChange() {
		return (String) getStateHelper().eval(AFTER_TAB_INDEX_CHANGE, null);
	}

	@Override
	public void setAfterTabIndexChange(String afterTabIndexChange) {
		getStateHelper().put(AFTER_TAB_INDEX_CHANGE, afterTabIndexChange);
	}

	@Override
	public String getAfterTypeChange() {
		return (String) getStateHelper().eval(AFTER_TYPE_CHANGE, null);
	}

	@Override
	public void setAfterTypeChange(String afterTypeChange) {
		getStateHelper().put(AFTER_TYPE_CHANGE, afterTypeChange);
	}

	@Override
	public String getAfterVisibleChange() {
		return (String) getStateHelper().eval(AFTER_VISIBLE_CHANGE, null);
	}

	@Override
	public void setAfterVisibleChange(String afterVisibleChange) {
		getStateHelper().put(AFTER_VISIBLE_CHANGE, afterVisibleChange);
	}

	@Override
	public String getAfterWidthChange() {
		return (String) getStateHelper().eval(AFTER_WIDTH_CHANGE, null);
	}

	@Override
	public void setAfterWidthChange(String afterWidthChange) {
		getStateHelper().put(AFTER_WIDTH_CHANGE, afterWidthChange);
	}

	@Override
	public String getBoundingBox() {
		return (String) getStateHelper().eval(BOUNDING_BOX, null);
	}

	@Override
	public void setBoundingBox(String boundingBox) {
		getStateHelper().put(BOUNDING_BOX, boundingBox);
	}

	@Override
	public String getContentBox() {
		return (String) getStateHelper().eval(CONTENT_BOX, null);
	}

	@Override
	public void setContentBox(String contentBox) {
		getStateHelper().put(CONTENT_BOX, contentBox);
	}

	@Override
	public Object getDefaultChildType() {
		return (Object) getStateHelper().eval(DEFAULT_CHILD_TYPE, null);
	}

	@Override
	public void setDefaultChildType(Object defaultChildType) {
		getStateHelper().put(DEFAULT_CHILD_TYPE, defaultChildType);
	}

	@Override
	public Boolean isDestroyed() {
		return (Boolean) getStateHelper().eval(DESTROYED, null);
	}

	@Override
	public void setDestroyed(Boolean destroyed) {
		getStateHelper().put(DESTROYED, destroyed);
	}

	@Override
	public Boolean isFocused() {
		return (Boolean) getStateHelper().eval(FOCUSED, null);
	}

	@Override
	public void setFocused(Boolean focused) {
		getStateHelper().put(FOCUSED, focused);
	}

	@Override
	public Object getHeight() {
		return (Object) getStateHelper().eval(HEIGHT, null);
	}

	@Override
	public void setHeight(Object height) {
		getStateHelper().put(HEIGHT, height);
	}

	@Override
	public Boolean isInitialized() {
		return (Boolean) getStateHelper().eval(INITIALIZED, null);
	}

	@Override
	public void setInitialized(Boolean initialized) {
		getStateHelper().put(INITIALIZED, initialized);
	}

	@Override
	public String getLocale() {
		return (String) getStateHelper().eval(LOCALE, null);
	}

	@Override
	public void setLocale(String locale) {
		getStateHelper().put(LOCALE, locale);
	}

	@Override
	public Boolean isMultiple() {
		return (Boolean) getStateHelper().eval(MULTIPLE, null);
	}

	@Override
	public void setMultiple(Boolean multiple) {
		getStateHelper().put(MULTIPLE, multiple);
	}

	@Override
	public String getOnActiveDescendantChange() {
		return (String) getStateHelper().eval(ON_ACTIVE_DESCENDANT_CHANGE, null);
	}

	@Override
	public void setOnActiveDescendantChange(String onActiveDescendantChange) {
		getStateHelper().put(ON_ACTIVE_DESCENDANT_CHANGE, onActiveDescendantChange);
	}

	@Override
	public String getOnBoundingBoxChange() {
		return (String) getStateHelper().eval(ON_BOUNDING_BOX_CHANGE, null);
	}

	@Override
	public void setOnBoundingBoxChange(String onBoundingBoxChange) {
		getStateHelper().put(ON_BOUNDING_BOX_CHANGE, onBoundingBoxChange);
	}

	@Override
	public String getOnContentBoxChange() {
		return (String) getStateHelper().eval(ON_CONTENT_BOX_CHANGE, null);
	}

	@Override
	public void setOnContentBoxChange(String onContentBoxChange) {
		getStateHelper().put(ON_CONTENT_BOX_CHANGE, onContentBoxChange);
	}

	@Override
	public String getOnDefaultChildTypeChange() {
		return (String) getStateHelper().eval(ON_DEFAULT_CHILD_TYPE_CHANGE, null);
	}

	@Override
	public void setOnDefaultChildTypeChange(String onDefaultChildTypeChange) {
		getStateHelper().put(ON_DEFAULT_CHILD_TYPE_CHANGE, onDefaultChildTypeChange);
	}

	@Override
	public String getOnDestroyedChange() {
		return (String) getStateHelper().eval(ON_DESTROYED_CHANGE, null);
	}

	@Override
	public void setOnDestroyedChange(String onDestroyedChange) {
		getStateHelper().put(ON_DESTROYED_CHANGE, onDestroyedChange);
	}

	@Override
	public String getOnDisabledChange() {
		return (String) getStateHelper().eval(ON_DISABLED_CHANGE, null);
	}

	@Override
	public void setOnDisabledChange(String onDisabledChange) {
		getStateHelper().put(ON_DISABLED_CHANGE, onDisabledChange);
	}

	@Override
	public String getOnFocusedChange() {
		return (String) getStateHelper().eval(ON_FOCUSED_CHANGE, null);
	}

	@Override
	public void setOnFocusedChange(String onFocusedChange) {
		getStateHelper().put(ON_FOCUSED_CHANGE, onFocusedChange);
	}

	@Override
	public String getOnHeightChange() {
		return (String) getStateHelper().eval(ON_HEIGHT_CHANGE, null);
	}

	@Override
	public void setOnHeightChange(String onHeightChange) {
		getStateHelper().put(ON_HEIGHT_CHANGE, onHeightChange);
	}

	@Override
	public String getOnIdChange() {
		return (String) getStateHelper().eval(ON_ID_CHANGE, null);
	}

	@Override
	public void setOnIdChange(String onIdChange) {
		getStateHelper().put(ON_ID_CHANGE, onIdChange);
	}

	@Override
	public String getOnInitializedChange() {
		return (String) getStateHelper().eval(ON_INITIALIZED_CHANGE, null);
	}

	@Override
	public void setOnInitializedChange(String onInitializedChange) {
		getStateHelper().put(ON_INITIALIZED_CHANGE, onInitializedChange);
	}

	@Override
	public String getOnLocaleChange() {
		return (String) getStateHelper().eval(ON_LOCALE_CHANGE, null);
	}

	@Override
	public void setOnLocaleChange(String onLocaleChange) {
		getStateHelper().put(ON_LOCALE_CHANGE, onLocaleChange);
	}

	@Override
	public String getOnMultipleChange() {
		return (String) getStateHelper().eval(ON_MULTIPLE_CHANGE, null);
	}

	@Override
	public void setOnMultipleChange(String onMultipleChange) {
		getStateHelper().put(ON_MULTIPLE_CHANGE, onMultipleChange);
	}

	@Override
	public String getOnRenderChange() {
		return (String) getStateHelper().eval(ON_RENDER_CHANGE, null);
	}

	@Override
	public void setOnRenderChange(String onRenderChange) {
		getStateHelper().put(ON_RENDER_CHANGE, onRenderChange);
	}

	@Override
	public String getOnRenderedChange() {
		return (String) getStateHelper().eval(ON_RENDERED_CHANGE, null);
	}

	@Override
	public void setOnRenderedChange(String onRenderedChange) {
		getStateHelper().put(ON_RENDERED_CHANGE, onRenderedChange);
	}

	@Override
	public String getOnSelectionChange() {
		return (String) getStateHelper().eval(ON_SELECTION_CHANGE, null);
	}

	@Override
	public void setOnSelectionChange(String onSelectionChange) {
		getStateHelper().put(ON_SELECTION_CHANGE, onSelectionChange);
	}

	@Override
	public String getOnSrcNodeChange() {
		return (String) getStateHelper().eval(ON_SRC_NODE_CHANGE, null);
	}

	@Override
	public void setOnSrcNodeChange(String onSrcNodeChange) {
		getStateHelper().put(ON_SRC_NODE_CHANGE, onSrcNodeChange);
	}

	@Override
	public String getOnStackedChange() {
		return (String) getStateHelper().eval(ON_STACKED_CHANGE, null);
	}

	@Override
	public void setOnStackedChange(String onStackedChange) {
		getStateHelper().put(ON_STACKED_CHANGE, onStackedChange);
	}

	@Override
	public String getOnStringsChange() {
		return (String) getStateHelper().eval(ON_STRINGS_CHANGE, null);
	}

	@Override
	public void setOnStringsChange(String onStringsChange) {
		getStateHelper().put(ON_STRINGS_CHANGE, onStringsChange);
	}

	@Override
	public String getOnTabIndexChange() {
		return (String) getStateHelper().eval(ON_TAB_INDEX_CHANGE, null);
	}

	@Override
	public void setOnTabIndexChange(String onTabIndexChange) {
		getStateHelper().put(ON_TAB_INDEX_CHANGE, onTabIndexChange);
	}

	@Override
	public String getOnTypeChange() {
		return (String) getStateHelper().eval(ON_TYPE_CHANGE, null);
	}

	@Override
	public void setOnTypeChange(String onTypeChange) {
		getStateHelper().put(ON_TYPE_CHANGE, onTypeChange);
	}

	@Override
	public String getOnVisibleChange() {
		return (String) getStateHelper().eval(ON_VISIBLE_CHANGE, null);
	}

	@Override
	public void setOnVisibleChange(String onVisibleChange) {
		getStateHelper().put(ON_VISIBLE_CHANGE, onVisibleChange);
	}

	@Override
	public String getOnWidthChange() {
		return (String) getStateHelper().eval(ON_WIDTH_CHANGE, null);
	}

	@Override
	public void setOnWidthChange(String onWidthChange) {
		getStateHelper().put(ON_WIDTH_CHANGE, onWidthChange);
	}

	@Override
	public Object getRender() {
		return (Object) getStateHelper().eval(RENDER, null);
	}

	@Override
	public void setRender(Object render) {
		getStateHelper().put(RENDER, render);
	}

	@Override
	public Object getSelection() {
		return (Object) getStateHelper().eval(SELECTION, null);
	}

	@Override
	public void setSelection(Object selection) {
		getStateHelper().put(SELECTION, selection);
	}

	@Override
	public String getSrcNode() {
		return (String) getStateHelper().eval(SRC_NODE, null);
	}

	@Override
	public void setSrcNode(String srcNode) {
		getStateHelper().put(SRC_NODE, srcNode);
	}

	@Override
	public Object getStacked() {
		return (Object) getStateHelper().eval(STACKED, null);
	}

	@Override
	public void setStacked(Object stacked) {
		getStateHelper().put(STACKED, stacked);
	}

	@Override
	public Object getStrings() {
		return (Object) getStateHelper().eval(STRINGS, null);
	}

	@Override
	public void setStrings(Object strings) {
		getStateHelper().put(STRINGS, strings);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}

	@Override
	public Object getTabIndex() {
		return (Object) getStateHelper().eval(TAB_INDEX, null);
	}

	@Override
	public void setTabIndex(Object tabIndex) {
		getStateHelper().put(TAB_INDEX, tabIndex);
	}

	@Override
	public Boolean isTabViewDisabled() {
		return (Boolean) getStateHelper().eval(TAB_VIEW_DISABLED, null);
	}

	@Override
	public void setTabViewDisabled(Boolean tabViewDisabled) {
		getStateHelper().put(TAB_VIEW_DISABLED, tabViewDisabled);
	}

	@Override
	public String getTabViewId() {
		return (String) getStateHelper().eval(TAB_VIEW_ID, null);
	}

	@Override
	public void setTabViewId(String tabViewId) {
		getStateHelper().put(TAB_VIEW_ID, tabViewId);
	}

	@Override
	public Boolean isTabViewRendered() {
		return (Boolean) getStateHelper().eval(TAB_VIEW_RENDERED, null);
	}

	@Override
	public void setTabViewRendered(Boolean tabViewRendered) {
		getStateHelper().put(TAB_VIEW_RENDERED, tabViewRendered);
	}

	@Override
	public Object getType() {
		return (Object) getStateHelper().eval(TYPE, null);
	}

	@Override
	public void setType(Object type) {
		getStateHelper().put(TYPE, type);
	}

	@Override
	public Boolean isVisible() {
		return (Boolean) getStateHelper().eval(VISIBLE, null);
	}

	@Override
	public void setVisible(Boolean visible) {
		getStateHelper().put(VISIBLE, visible);
	}

	@Override
	public String getWidgetVar() {
		return (String) getStateHelper().eval(WIDGET_VAR, null);
	}

	@Override
	public void setWidgetVar(String widgetVar) {
		getStateHelper().put(WIDGET_VAR, widgetVar);
	}

	@Override
	public Object getWidth() {
		return (Object) getStateHelper().eval(WIDTH, null);
	}

	@Override
	public void setWidth(Object width) {
		getStateHelper().put(WIDTH, width);
	}
}
//J+
