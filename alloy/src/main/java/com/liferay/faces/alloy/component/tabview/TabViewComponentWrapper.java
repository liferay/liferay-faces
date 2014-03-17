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
import javax.faces.FacesWrapper;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public class TabViewComponentWrapper implements TabViewComponent, FacesWrapper<TabViewComponent> {

	// Private Data Members
	private TabViewComponent wrappedTabViewComponent;

	public TabViewComponentWrapper(TabViewComponent tabViewComponent) {
		this.wrappedTabViewComponent = tabViewComponent;
	}

	@Override
	public TabViewComponent getWrapped() {
		return wrappedTabViewComponent;
	}

	@Override
	public Object getActiveDescendant() {
		return getWrapped().getActiveDescendant();
	}

	@Override
	public void setActiveDescendant(Object activeDescendant) {
		getWrapped().setActiveDescendant(activeDescendant);
	}

	@Override
	public String getAfterActiveDescendantChange() {
		return getWrapped().getAfterActiveDescendantChange();
	}

	@Override
	public void setAfterActiveDescendantChange(String afterActiveDescendantChange) {
		getWrapped().setAfterActiveDescendantChange(afterActiveDescendantChange);
	}

	@Override
	public String getAfterBoundingBoxChange() {
		return getWrapped().getAfterBoundingBoxChange();
	}

	@Override
	public void setAfterBoundingBoxChange(String afterBoundingBoxChange) {
		getWrapped().setAfterBoundingBoxChange(afterBoundingBoxChange);
	}

	@Override
	public String getAfterContentBoxChange() {
		return getWrapped().getAfterContentBoxChange();
	}

	@Override
	public void setAfterContentBoxChange(String afterContentBoxChange) {
		getWrapped().setAfterContentBoxChange(afterContentBoxChange);
	}

	@Override
	public String getAfterDefaultChildTypeChange() {
		return getWrapped().getAfterDefaultChildTypeChange();
	}

	@Override
	public void setAfterDefaultChildTypeChange(String afterDefaultChildTypeChange) {
		getWrapped().setAfterDefaultChildTypeChange(afterDefaultChildTypeChange);
	}

	@Override
	public String getAfterDestroyedChange() {
		return getWrapped().getAfterDestroyedChange();
	}

	@Override
	public void setAfterDestroyedChange(String afterDestroyedChange) {
		getWrapped().setAfterDestroyedChange(afterDestroyedChange);
	}

	@Override
	public String getAfterDisabledChange() {
		return getWrapped().getAfterDisabledChange();
	}

	@Override
	public void setAfterDisabledChange(String afterDisabledChange) {
		getWrapped().setAfterDisabledChange(afterDisabledChange);
	}

	@Override
	public String getAfterFocusedChange() {
		return getWrapped().getAfterFocusedChange();
	}

	@Override
	public void setAfterFocusedChange(String afterFocusedChange) {
		getWrapped().setAfterFocusedChange(afterFocusedChange);
	}

	@Override
	public String getAfterHeightChange() {
		return getWrapped().getAfterHeightChange();
	}

	@Override
	public void setAfterHeightChange(String afterHeightChange) {
		getWrapped().setAfterHeightChange(afterHeightChange);
	}

	@Override
	public String getAfterIdChange() {
		return getWrapped().getAfterIdChange();
	}

	@Override
	public void setAfterIdChange(String afterIdChange) {
		getWrapped().setAfterIdChange(afterIdChange);
	}

	@Override
	public String getAfterInitializedChange() {
		return getWrapped().getAfterInitializedChange();
	}

	@Override
	public void setAfterInitializedChange(String afterInitializedChange) {
		getWrapped().setAfterInitializedChange(afterInitializedChange);
	}

	@Override
	public String getAfterLocaleChange() {
		return getWrapped().getAfterLocaleChange();
	}

	@Override
	public void setAfterLocaleChange(String afterLocaleChange) {
		getWrapped().setAfterLocaleChange(afterLocaleChange);
	}

	@Override
	public String getAfterMultipleChange() {
		return getWrapped().getAfterMultipleChange();
	}

	@Override
	public void setAfterMultipleChange(String afterMultipleChange) {
		getWrapped().setAfterMultipleChange(afterMultipleChange);
	}

	@Override
	public String getAfterRenderChange() {
		return getWrapped().getAfterRenderChange();
	}

	@Override
	public void setAfterRenderChange(String afterRenderChange) {
		getWrapped().setAfterRenderChange(afterRenderChange);
	}

	@Override
	public String getAfterRenderedChange() {
		return getWrapped().getAfterRenderedChange();
	}

	@Override
	public void setAfterRenderedChange(String afterRenderedChange) {
		getWrapped().setAfterRenderedChange(afterRenderedChange);
	}

	@Override
	public String getAfterSelectionChange() {
		return getWrapped().getAfterSelectionChange();
	}

	@Override
	public void setAfterSelectionChange(String afterSelectionChange) {
		getWrapped().setAfterSelectionChange(afterSelectionChange);
	}

	@Override
	public String getAfterSrcNodeChange() {
		return getWrapped().getAfterSrcNodeChange();
	}

	@Override
	public void setAfterSrcNodeChange(String afterSrcNodeChange) {
		getWrapped().setAfterSrcNodeChange(afterSrcNodeChange);
	}

	@Override
	public String getAfterStackedChange() {
		return getWrapped().getAfterStackedChange();
	}

	@Override
	public void setAfterStackedChange(String afterStackedChange) {
		getWrapped().setAfterStackedChange(afterStackedChange);
	}

	@Override
	public String getAfterStringsChange() {
		return getWrapped().getAfterStringsChange();
	}

	@Override
	public void setAfterStringsChange(String afterStringsChange) {
		getWrapped().setAfterStringsChange(afterStringsChange);
	}

	@Override
	public String getAfterTabIndexChange() {
		return getWrapped().getAfterTabIndexChange();
	}

	@Override
	public void setAfterTabIndexChange(String afterTabIndexChange) {
		getWrapped().setAfterTabIndexChange(afterTabIndexChange);
	}

	@Override
	public String getAfterTypeChange() {
		return getWrapped().getAfterTypeChange();
	}

	@Override
	public void setAfterTypeChange(String afterTypeChange) {
		getWrapped().setAfterTypeChange(afterTypeChange);
	}

	@Override
	public String getAfterVisibleChange() {
		return getWrapped().getAfterVisibleChange();
	}

	@Override
	public void setAfterVisibleChange(String afterVisibleChange) {
		getWrapped().setAfterVisibleChange(afterVisibleChange);
	}

	@Override
	public String getAfterWidthChange() {
		return getWrapped().getAfterWidthChange();
	}

	@Override
	public void setAfterWidthChange(String afterWidthChange) {
		getWrapped().setAfterWidthChange(afterWidthChange);
	}

	@Override
	public String getBoundingBox() {
		return getWrapped().getBoundingBox();
	}

	@Override
	public void setBoundingBox(String boundingBox) {
		getWrapped().setBoundingBox(boundingBox);
	}

	@Override
	public String getContentBox() {
		return getWrapped().getContentBox();
	}

	@Override
	public void setContentBox(String contentBox) {
		getWrapped().setContentBox(contentBox);
	}

	@Override
	public Object getDefaultChildType() {
		return getWrapped().getDefaultChildType();
	}

	@Override
	public void setDefaultChildType(Object defaultChildType) {
		getWrapped().setDefaultChildType(defaultChildType);
	}

	@Override
	public Boolean isDestroyed() {
		return getWrapped().isDestroyed();
	}

	@Override
	public void setDestroyed(Boolean destroyed) {
		getWrapped().setDestroyed(destroyed);
	}

	@Override
	public Boolean isFocused() {
		return getWrapped().isFocused();
	}

	@Override
	public void setFocused(Boolean focused) {
		getWrapped().setFocused(focused);
	}

	@Override
	public Object getHeight() {
		return getWrapped().getHeight();
	}

	@Override
	public void setHeight(Object height) {
		getWrapped().setHeight(height);
	}

	@Override
	public Boolean isInitialized() {
		return getWrapped().isInitialized();
	}

	@Override
	public void setInitialized(Boolean initialized) {
		getWrapped().setInitialized(initialized);
	}

	@Override
	public String getLocale() {
		return getWrapped().getLocale();
	}

	@Override
	public void setLocale(String locale) {
		getWrapped().setLocale(locale);
	}

	@Override
	public Boolean isMultiple() {
		return getWrapped().isMultiple();
	}

	@Override
	public void setMultiple(Boolean multiple) {
		getWrapped().setMultiple(multiple);
	}

	@Override
	public String getOnActiveDescendantChange() {
		return getWrapped().getOnActiveDescendantChange();
	}

	@Override
	public void setOnActiveDescendantChange(String onActiveDescendantChange) {
		getWrapped().setOnActiveDescendantChange(onActiveDescendantChange);
	}

	@Override
	public String getOnBoundingBoxChange() {
		return getWrapped().getOnBoundingBoxChange();
	}

	@Override
	public void setOnBoundingBoxChange(String onBoundingBoxChange) {
		getWrapped().setOnBoundingBoxChange(onBoundingBoxChange);
	}

	@Override
	public String getOnContentBoxChange() {
		return getWrapped().getOnContentBoxChange();
	}

	@Override
	public void setOnContentBoxChange(String onContentBoxChange) {
		getWrapped().setOnContentBoxChange(onContentBoxChange);
	}

	@Override
	public String getOnDefaultChildTypeChange() {
		return getWrapped().getOnDefaultChildTypeChange();
	}

	@Override
	public void setOnDefaultChildTypeChange(String onDefaultChildTypeChange) {
		getWrapped().setOnDefaultChildTypeChange(onDefaultChildTypeChange);
	}

	@Override
	public String getOnDestroyedChange() {
		return getWrapped().getOnDestroyedChange();
	}

	@Override
	public void setOnDestroyedChange(String onDestroyedChange) {
		getWrapped().setOnDestroyedChange(onDestroyedChange);
	}

	@Override
	public String getOnDisabledChange() {
		return getWrapped().getOnDisabledChange();
	}

	@Override
	public void setOnDisabledChange(String onDisabledChange) {
		getWrapped().setOnDisabledChange(onDisabledChange);
	}

	@Override
	public String getOnFocusedChange() {
		return getWrapped().getOnFocusedChange();
	}

	@Override
	public void setOnFocusedChange(String onFocusedChange) {
		getWrapped().setOnFocusedChange(onFocusedChange);
	}

	@Override
	public String getOnHeightChange() {
		return getWrapped().getOnHeightChange();
	}

	@Override
	public void setOnHeightChange(String onHeightChange) {
		getWrapped().setOnHeightChange(onHeightChange);
	}

	@Override
	public String getOnIdChange() {
		return getWrapped().getOnIdChange();
	}

	@Override
	public void setOnIdChange(String onIdChange) {
		getWrapped().setOnIdChange(onIdChange);
	}

	@Override
	public String getOnInitializedChange() {
		return getWrapped().getOnInitializedChange();
	}

	@Override
	public void setOnInitializedChange(String onInitializedChange) {
		getWrapped().setOnInitializedChange(onInitializedChange);
	}

	@Override
	public String getOnLocaleChange() {
		return getWrapped().getOnLocaleChange();
	}

	@Override
	public void setOnLocaleChange(String onLocaleChange) {
		getWrapped().setOnLocaleChange(onLocaleChange);
	}

	@Override
	public String getOnMultipleChange() {
		return getWrapped().getOnMultipleChange();
	}

	@Override
	public void setOnMultipleChange(String onMultipleChange) {
		getWrapped().setOnMultipleChange(onMultipleChange);
	}

	@Override
	public String getOnRenderChange() {
		return getWrapped().getOnRenderChange();
	}

	@Override
	public void setOnRenderChange(String onRenderChange) {
		getWrapped().setOnRenderChange(onRenderChange);
	}

	@Override
	public String getOnRenderedChange() {
		return getWrapped().getOnRenderedChange();
	}

	@Override
	public void setOnRenderedChange(String onRenderedChange) {
		getWrapped().setOnRenderedChange(onRenderedChange);
	}

	@Override
	public String getOnSelectionChange() {
		return getWrapped().getOnSelectionChange();
	}

	@Override
	public void setOnSelectionChange(String onSelectionChange) {
		getWrapped().setOnSelectionChange(onSelectionChange);
	}

	@Override
	public String getOnSrcNodeChange() {
		return getWrapped().getOnSrcNodeChange();
	}

	@Override
	public void setOnSrcNodeChange(String onSrcNodeChange) {
		getWrapped().setOnSrcNodeChange(onSrcNodeChange);
	}

	@Override
	public String getOnStackedChange() {
		return getWrapped().getOnStackedChange();
	}

	@Override
	public void setOnStackedChange(String onStackedChange) {
		getWrapped().setOnStackedChange(onStackedChange);
	}

	@Override
	public String getOnStringsChange() {
		return getWrapped().getOnStringsChange();
	}

	@Override
	public void setOnStringsChange(String onStringsChange) {
		getWrapped().setOnStringsChange(onStringsChange);
	}

	@Override
	public String getOnTabIndexChange() {
		return getWrapped().getOnTabIndexChange();
	}

	@Override
	public void setOnTabIndexChange(String onTabIndexChange) {
		getWrapped().setOnTabIndexChange(onTabIndexChange);
	}

	@Override
	public String getOnTypeChange() {
		return getWrapped().getOnTypeChange();
	}

	@Override
	public void setOnTypeChange(String onTypeChange) {
		getWrapped().setOnTypeChange(onTypeChange);
	}

	@Override
	public String getOnVisibleChange() {
		return getWrapped().getOnVisibleChange();
	}

	@Override
	public void setOnVisibleChange(String onVisibleChange) {
		getWrapped().setOnVisibleChange(onVisibleChange);
	}

	@Override
	public String getOnWidthChange() {
		return getWrapped().getOnWidthChange();
	}

	@Override
	public void setOnWidthChange(String onWidthChange) {
		getWrapped().setOnWidthChange(onWidthChange);
	}

	@Override
	public Object getRender() {
		return getWrapped().getRender();
	}

	@Override
	public void setRender(Object render) {
		getWrapped().setRender(render);
	}

	@Override
	public Object getSelection() {
		return getWrapped().getSelection();
	}

	@Override
	public void setSelection(Object selection) {
		getWrapped().setSelection(selection);
	}

	@Override
	public String getSrcNode() {
		return getWrapped().getSrcNode();
	}

	@Override
	public void setSrcNode(String srcNode) {
		getWrapped().setSrcNode(srcNode);
	}

	@Override
	public Object getStacked() {
		return getWrapped().getStacked();
	}

	@Override
	public void setStacked(Object stacked) {
		getWrapped().setStacked(stacked);
	}

	@Override
	public Object getStrings() {
		return getWrapped().getStrings();
	}

	@Override
	public void setStrings(Object strings) {
		getWrapped().setStrings(strings);
	}

	@Override
	public Object getTabIndex() {
		return getWrapped().getTabIndex();
	}

	@Override
	public void setTabIndex(Object tabIndex) {
		getWrapped().setTabIndex(tabIndex);
	}

	@Override
	public Boolean isTabViewDisabled() {
		return getWrapped().isTabViewDisabled();
	}

	@Override
	public void setTabViewDisabled(Boolean tabViewDisabled) {
		getWrapped().setTabViewDisabled(tabViewDisabled);
	}

	@Override
	public String getTabViewId() {
		return getWrapped().getTabViewId();
	}

	@Override
	public void setTabViewId(String tabViewId) {
		getWrapped().setTabViewId(tabViewId);
	}

	@Override
	public Boolean isTabViewRendered() {
		return getWrapped().isTabViewRendered();
	}

	@Override
	public void setTabViewRendered(Boolean tabViewRendered) {
		getWrapped().setTabViewRendered(tabViewRendered);
	}

	@Override
	public Object getType() {
		return getWrapped().getType();
	}

	@Override
	public void setType(Object type) {
		getWrapped().setType(type);
	}

	@Override
	public Boolean isVisible() {
		return getWrapped().isVisible();
	}

	@Override
	public void setVisible(Boolean visible) {
		getWrapped().setVisible(visible);
	}

	@Override
	public Object getWidth() {
		return getWrapped().getWidth();
	}

	@Override
	public void setWidth(Object width) {
		getWrapped().setWidth(width);
	}
}
//J+
