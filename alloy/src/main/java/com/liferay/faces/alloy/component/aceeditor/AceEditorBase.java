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
import javax.faces.component.UIInput;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.Widget;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class AceEditorBase extends UIInput implements Styleable, Widget, AceEditorComponent {

	@Override
	public Boolean isAceEditorDisabled() {
		return (Boolean) getStateHelper().eval(ACE_EDITOR_DISABLED, null);
	}

	@Override
	public void setAceEditorDisabled(Boolean aceEditorDisabled) {
		getStateHelper().put(ACE_EDITOR_DISABLED, aceEditorDisabled);
	}

	@Override
	public String getAceEditorId() {
		return (String) getStateHelper().eval(ACE_EDITOR_ID, null);
	}

	@Override
	public void setAceEditorId(String aceEditorId) {
		getStateHelper().put(ACE_EDITOR_ID, aceEditorId);
	}

	@Override
	public Boolean isAceEditorRendered() {
		return (Boolean) getStateHelper().eval(ACE_EDITOR_RENDERED, null);
	}

	@Override
	public void setAceEditorRendered(Boolean aceEditorRendered) {
		getStateHelper().put(ACE_EDITOR_RENDERED, aceEditorRendered);
	}

	@Override
	public String getAceEditorValue() {
		return (String) getStateHelper().eval(ACE_EDITOR_VALUE, null);
	}

	@Override
	public void setAceEditorValue(String aceEditorValue) {
		getStateHelper().put(ACE_EDITOR_VALUE, aceEditorValue);
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
	public String getAfterHighlightActiveLineChange() {
		return (String) getStateHelper().eval(AFTER_HIGHLIGHT_ACTIVE_LINE_CHANGE, null);
	}

	@Override
	public void setAfterHighlightActiveLineChange(String afterHighlightActiveLineChange) {
		getStateHelper().put(AFTER_HIGHLIGHT_ACTIVE_LINE_CHANGE, afterHighlightActiveLineChange);
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
	public String getAfterModeChange() {
		return (String) getStateHelper().eval(AFTER_MODE_CHANGE, null);
	}

	@Override
	public void setAfterModeChange(String afterModeChange) {
		getStateHelper().put(AFTER_MODE_CHANGE, afterModeChange);
	}

	@Override
	public String getAfterReadOnlyChange() {
		return (String) getStateHelper().eval(AFTER_READ_ONLY_CHANGE, null);
	}

	@Override
	public void setAfterReadOnlyChange(String afterReadOnlyChange) {
		getStateHelper().put(AFTER_READ_ONLY_CHANGE, afterReadOnlyChange);
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
	public String getAfterShowPrintMarginChange() {
		return (String) getStateHelper().eval(AFTER_SHOW_PRINT_MARGIN_CHANGE, null);
	}

	@Override
	public void setAfterShowPrintMarginChange(String afterShowPrintMarginChange) {
		getStateHelper().put(AFTER_SHOW_PRINT_MARGIN_CHANGE, afterShowPrintMarginChange);
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
	public String getAfterTabSizeChange() {
		return (String) getStateHelper().eval(AFTER_TAB_SIZE_CHANGE, null);
	}

	@Override
	public void setAfterTabSizeChange(String afterTabSizeChange) {
		getStateHelper().put(AFTER_TAB_SIZE_CHANGE, afterTabSizeChange);
	}

	@Override
	public String getAfterUseSoftTabsChange() {
		return (String) getStateHelper().eval(AFTER_USE_SOFT_TABS_CHANGE, null);
	}

	@Override
	public void setAfterUseSoftTabsChange(String afterUseSoftTabsChange) {
		getStateHelper().put(AFTER_USE_SOFT_TABS_CHANGE, afterUseSoftTabsChange);
	}

	@Override
	public String getAfterUseWrapModeChange() {
		return (String) getStateHelper().eval(AFTER_USE_WRAP_MODE_CHANGE, null);
	}

	@Override
	public void setAfterUseWrapModeChange(String afterUseWrapModeChange) {
		getStateHelper().put(AFTER_USE_WRAP_MODE_CHANGE, afterUseWrapModeChange);
	}

	@Override
	public String getAfterValueChange() {
		return (String) getStateHelper().eval(AFTER_VALUE_CHANGE, null);
	}

	@Override
	public void setAfterValueChange(String afterValueChange) {
		getStateHelper().put(AFTER_VALUE_CHANGE, afterValueChange);
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
	public Boolean isHighlightActiveLine() {
		return (Boolean) getStateHelper().eval(HIGHLIGHT_ACTIVE_LINE, null);
	}

	@Override
	public void setHighlightActiveLine(Boolean highlightActiveLine) {
		getStateHelper().put(HIGHLIGHT_ACTIVE_LINE, highlightActiveLine);
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
	public String getMode() {
		return (String) getStateHelper().eval(MODE, null);
	}

	@Override
	public void setMode(String mode) {
		getStateHelper().put(MODE, mode);
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
	public String getOnHighlightActiveLineChange() {
		return (String) getStateHelper().eval(ON_HIGHLIGHT_ACTIVE_LINE_CHANGE, null);
	}

	@Override
	public void setOnHighlightActiveLineChange(String onHighlightActiveLineChange) {
		getStateHelper().put(ON_HIGHLIGHT_ACTIVE_LINE_CHANGE, onHighlightActiveLineChange);
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
	public String getOnModeChange() {
		return (String) getStateHelper().eval(ON_MODE_CHANGE, null);
	}

	@Override
	public void setOnModeChange(String onModeChange) {
		getStateHelper().put(ON_MODE_CHANGE, onModeChange);
	}

	@Override
	public String getOnReadOnlyChange() {
		return (String) getStateHelper().eval(ON_READ_ONLY_CHANGE, null);
	}

	@Override
	public void setOnReadOnlyChange(String onReadOnlyChange) {
		getStateHelper().put(ON_READ_ONLY_CHANGE, onReadOnlyChange);
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
	public String getOnShowPrintMarginChange() {
		return (String) getStateHelper().eval(ON_SHOW_PRINT_MARGIN_CHANGE, null);
	}

	@Override
	public void setOnShowPrintMarginChange(String onShowPrintMarginChange) {
		getStateHelper().put(ON_SHOW_PRINT_MARGIN_CHANGE, onShowPrintMarginChange);
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
	public String getOnTabSizeChange() {
		return (String) getStateHelper().eval(ON_TAB_SIZE_CHANGE, null);
	}

	@Override
	public void setOnTabSizeChange(String onTabSizeChange) {
		getStateHelper().put(ON_TAB_SIZE_CHANGE, onTabSizeChange);
	}

	@Override
	public String getOnUseSoftTabsChange() {
		return (String) getStateHelper().eval(ON_USE_SOFT_TABS_CHANGE, null);
	}

	@Override
	public void setOnUseSoftTabsChange(String onUseSoftTabsChange) {
		getStateHelper().put(ON_USE_SOFT_TABS_CHANGE, onUseSoftTabsChange);
	}

	@Override
	public String getOnUseWrapModeChange() {
		return (String) getStateHelper().eval(ON_USE_WRAP_MODE_CHANGE, null);
	}

	@Override
	public void setOnUseWrapModeChange(String onUseWrapModeChange) {
		getStateHelper().put(ON_USE_WRAP_MODE_CHANGE, onUseWrapModeChange);
	}

	@Override
	public String getOnValueChange() {
		return (String) getStateHelper().eval(ON_VALUE_CHANGE, null);
	}

	@Override
	public void setOnValueChange(String onValueChange) {
		getStateHelper().put(ON_VALUE_CHANGE, onValueChange);
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
	public Boolean isReadOnly() {
		return (Boolean) getStateHelper().eval(READ_ONLY, null);
	}

	@Override
	public void setReadOnly(Boolean readOnly) {
		getStateHelper().put(READ_ONLY, readOnly);
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
	public Boolean isShowPrintMargin() {
		return (Boolean) getStateHelper().eval(SHOW_PRINT_MARGIN, null);
	}

	@Override
	public void setShowPrintMargin(Boolean showPrintMargin) {
		getStateHelper().put(SHOW_PRINT_MARGIN, showPrintMargin);
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
	public Object getTabSize() {
		return (Object) getStateHelper().eval(TAB_SIZE, null);
	}

	@Override
	public void setTabSize(Object tabSize) {
		getStateHelper().put(TAB_SIZE, tabSize);
	}

	@Override
	public Boolean isUseSoftTabs() {
		return (Boolean) getStateHelper().eval(USE_SOFT_TABS, null);
	}

	@Override
	public void setUseSoftTabs(Boolean useSoftTabs) {
		getStateHelper().put(USE_SOFT_TABS, useSoftTabs);
	}

	@Override
	public Boolean isUseWrapMode() {
		return (Boolean) getStateHelper().eval(USE_WRAP_MODE, null);
	}

	@Override
	public void setUseWrapMode(Boolean useWrapMode) {
		getStateHelper().put(USE_WRAP_MODE, useWrapMode);
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
