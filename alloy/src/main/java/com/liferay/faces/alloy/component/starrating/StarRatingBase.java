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
package com.liferay.faces.alloy.component.starrating;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlSelectOneRadio;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class StarRatingBase extends HtmlSelectOneRadio implements Styleable, ClientComponent, StarRatingAlloy {

	@Override
	public String getAfterBoundingBoxChange() {
		return (String) getStateHelper().eval(AFTER_BOUNDING_BOX_CHANGE, null);
	}

	@Override
	public void setAfterBoundingBoxChange(String afterBoundingBoxChange) {
		getStateHelper().put(AFTER_BOUNDING_BOX_CHANGE, afterBoundingBoxChange);
	}

	@Override
	public String getAfterCanResetChange() {
		return (String) getStateHelper().eval(AFTER_CAN_RESET_CHANGE, null);
	}

	@Override
	public void setAfterCanResetChange(String afterCanResetChange) {
		getStateHelper().put(AFTER_CAN_RESET_CHANGE, afterCanResetChange);
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
	public String getAfterCssClassChange() {
		return (String) getStateHelper().eval(AFTER_CSS_CLASS_CHANGE, null);
	}

	@Override
	public void setAfterCssClassChange(String afterCssClassChange) {
		getStateHelper().put(AFTER_CSS_CLASS_CHANGE, afterCssClassChange);
	}

	@Override
	public String getAfterCssClassesChange() {
		return (String) getStateHelper().eval(AFTER_CSS_CLASSES_CHANGE, null);
	}

	@Override
	public void setAfterCssClassesChange(String afterCssClassesChange) {
		getStateHelper().put(AFTER_CSS_CLASSES_CHANGE, afterCssClassesChange);
	}

	@Override
	public String getAfterDefaultSelectedChange() {
		return (String) getStateHelper().eval(AFTER_DEFAULT_SELECTED_CHANGE, null);
	}

	@Override
	public void setAfterDefaultSelectedChange(String afterDefaultSelectedChange) {
		getStateHelper().put(AFTER_DEFAULT_SELECTED_CHANGE, afterDefaultSelectedChange);
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
	public String getAfterElementsChange() {
		return (String) getStateHelper().eval(AFTER_ELEMENTS_CHANGE, null);
	}

	@Override
	public void setAfterElementsChange(String afterElementsChange) {
		getStateHelper().put(AFTER_ELEMENTS_CHANGE, afterElementsChange);
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
	public String getAfterHiddenInputChange() {
		return (String) getStateHelper().eval(AFTER_HIDDEN_INPUT_CHANGE, null);
	}

	@Override
	public void setAfterHiddenInputChange(String afterHiddenInputChange) {
		getStateHelper().put(AFTER_HIDDEN_INPUT_CHANGE, afterHiddenInputChange);
	}

	@Override
	public String getAfterHideClassChange() {
		return (String) getStateHelper().eval(AFTER_HIDE_CLASS_CHANGE, null);
	}

	@Override
	public void setAfterHideClassChange(String afterHideClassChange) {
		getStateHelper().put(AFTER_HIDE_CLASS_CHANGE, afterHideClassChange);
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
	public String getAfterInputNameChange() {
		return (String) getStateHelper().eval(AFTER_INPUT_NAME_CHANGE, null);
	}

	@Override
	public void setAfterInputNameChange(String afterInputNameChange) {
		getStateHelper().put(AFTER_INPUT_NAME_CHANGE, afterInputNameChange);
	}

	@Override
	public String getAfterLabelChange() {
		return (String) getStateHelper().eval(AFTER_LABEL_CHANGE, null);
	}

	@Override
	public void setAfterLabelChange(String afterLabelChange) {
		getStateHelper().put(AFTER_LABEL_CHANGE, afterLabelChange);
	}

	@Override
	public String getAfterLabelNodeChange() {
		return (String) getStateHelper().eval(AFTER_LABEL_NODE_CHANGE, null);
	}

	@Override
	public void setAfterLabelNodeChange(String afterLabelNodeChange) {
		getStateHelper().put(AFTER_LABEL_NODE_CHANGE, afterLabelNodeChange);
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
	public String getAfterSelectedIndexChange() {
		return (String) getStateHelper().eval(AFTER_SELECTED_INDEX_CHANGE, null);
	}

	@Override
	public void setAfterSelectedIndexChange(String afterSelectedIndexChange) {
		getStateHelper().put(AFTER_SELECTED_INDEX_CHANGE, afterSelectedIndexChange);
	}

	@Override
	public String getAfterShowTitleChange() {
		return (String) getStateHelper().eval(AFTER_SHOW_TITLE_CHANGE, null);
	}

	@Override
	public void setAfterShowTitleChange(String afterShowTitleChange) {
		getStateHelper().put(AFTER_SHOW_TITLE_CHANGE, afterShowTitleChange);
	}

	@Override
	public String getAfterSizeChange() {
		return (String) getStateHelper().eval(AFTER_SIZE_CHANGE, null);
	}

	@Override
	public void setAfterSizeChange(String afterSizeChange) {
		getStateHelper().put(AFTER_SIZE_CHANGE, afterSizeChange);
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
	public String getAfterTitleChange() {
		return (String) getStateHelper().eval(AFTER_TITLE_CHANGE, null);
	}

	@Override
	public void setAfterTitleChange(String afterTitleChange) {
		getStateHelper().put(AFTER_TITLE_CHANGE, afterTitleChange);
	}

	@Override
	public String getAfterUseARIAChange() {
		return (String) getStateHelper().eval(AFTER_USE_ARIACHANGE, null);
	}

	@Override
	public void setAfterUseARIAChange(String afterUseARIAChange) {
		getStateHelper().put(AFTER_USE_ARIACHANGE, afterUseARIAChange);
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
	public Boolean isCanReset() {
		return (Boolean) getStateHelper().eval(CAN_RESET, null);
	}

	@Override
	public void setCanReset(Boolean canReset) {
		getStateHelper().put(CAN_RESET, canReset);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(CLIENT_KEY, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(CLIENT_KEY, clientKey);
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
	public String getCssClass() {
		return (String) getStateHelper().eval(CSS_CLASS, null);
	}

	@Override
	public void setCssClass(String cssClass) {
		getStateHelper().put(CSS_CLASS, cssClass);
	}

	@Override
	public Object getCssClasses() {
		return (Object) getStateHelper().eval(CSS_CLASSES, null);
	}

	@Override
	public void setCssClasses(Object cssClasses) {
		getStateHelper().put(CSS_CLASSES, cssClasses);
	}

	@Override
	public Object getDefaultSelected() {
		return (Object) getStateHelper().eval(DEFAULT_SELECTED, null);
	}

	@Override
	public void setDefaultSelected(Object defaultSelected) {
		getStateHelper().put(DEFAULT_SELECTED, defaultSelected);
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
	public String getElements() {
		return (String) getStateHelper().eval(ELEMENTS, null);
	}

	@Override
	public void setElements(String elements) {
		getStateHelper().put(ELEMENTS, elements);
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
	public String getHiddenInput() {
		return (String) getStateHelper().eval(HIDDEN_INPUT, null);
	}

	@Override
	public void setHiddenInput(String hiddenInput) {
		getStateHelper().put(HIDDEN_INPUT, hiddenInput);
	}

	@Override
	public String getHideClass() {
		return (String) getStateHelper().eval(HIDE_CLASS, null);
	}

	@Override
	public void setHideClass(String hideClass) {
		getStateHelper().put(HIDE_CLASS, hideClass);
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
	public String getInputName() {
		return (String) getStateHelper().eval(INPUT_NAME, null);
	}

	@Override
	public void setInputName(String inputName) {
		getStateHelper().put(INPUT_NAME, inputName);
	}

	@Override
	public String getLabelNode() {
		return (String) getStateHelper().eval(LABEL_NODE, null);
	}

	@Override
	public void setLabelNode(String labelNode) {
		getStateHelper().put(LABEL_NODE, labelNode);
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
	public String getOnBoundingBoxChange() {
		return (String) getStateHelper().eval(ON_BOUNDING_BOX_CHANGE, null);
	}

	@Override
	public void setOnBoundingBoxChange(String onBoundingBoxChange) {
		getStateHelper().put(ON_BOUNDING_BOX_CHANGE, onBoundingBoxChange);
	}

	@Override
	public String getOnCanResetChange() {
		return (String) getStateHelper().eval(ON_CAN_RESET_CHANGE, null);
	}

	@Override
	public void setOnCanResetChange(String onCanResetChange) {
		getStateHelper().put(ON_CAN_RESET_CHANGE, onCanResetChange);
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
	public String getOnCssClassChange() {
		return (String) getStateHelper().eval(ON_CSS_CLASS_CHANGE, null);
	}

	@Override
	public void setOnCssClassChange(String onCssClassChange) {
		getStateHelper().put(ON_CSS_CLASS_CHANGE, onCssClassChange);
	}

	@Override
	public String getOnCssClassesChange() {
		return (String) getStateHelper().eval(ON_CSS_CLASSES_CHANGE, null);
	}

	@Override
	public void setOnCssClassesChange(String onCssClassesChange) {
		getStateHelper().put(ON_CSS_CLASSES_CHANGE, onCssClassesChange);
	}

	@Override
	public String getOnDefaultSelectedChange() {
		return (String) getStateHelper().eval(ON_DEFAULT_SELECTED_CHANGE, null);
	}

	@Override
	public void setOnDefaultSelectedChange(String onDefaultSelectedChange) {
		getStateHelper().put(ON_DEFAULT_SELECTED_CHANGE, onDefaultSelectedChange);
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
	public String getOnElementsChange() {
		return (String) getStateHelper().eval(ON_ELEMENTS_CHANGE, null);
	}

	@Override
	public void setOnElementsChange(String onElementsChange) {
		getStateHelper().put(ON_ELEMENTS_CHANGE, onElementsChange);
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
	public String getOnHiddenInputChange() {
		return (String) getStateHelper().eval(ON_HIDDEN_INPUT_CHANGE, null);
	}

	@Override
	public void setOnHiddenInputChange(String onHiddenInputChange) {
		getStateHelper().put(ON_HIDDEN_INPUT_CHANGE, onHiddenInputChange);
	}

	@Override
	public String getOnHideClassChange() {
		return (String) getStateHelper().eval(ON_HIDE_CLASS_CHANGE, null);
	}

	@Override
	public void setOnHideClassChange(String onHideClassChange) {
		getStateHelper().put(ON_HIDE_CLASS_CHANGE, onHideClassChange);
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
	public String getOnInputNameChange() {
		return (String) getStateHelper().eval(ON_INPUT_NAME_CHANGE, null);
	}

	@Override
	public void setOnInputNameChange(String onInputNameChange) {
		getStateHelper().put(ON_INPUT_NAME_CHANGE, onInputNameChange);
	}

	@Override
	public String getOnLabelChange() {
		return (String) getStateHelper().eval(ON_LABEL_CHANGE, null);
	}

	@Override
	public void setOnLabelChange(String onLabelChange) {
		getStateHelper().put(ON_LABEL_CHANGE, onLabelChange);
	}

	@Override
	public String getOnLabelNodeChange() {
		return (String) getStateHelper().eval(ON_LABEL_NODE_CHANGE, null);
	}

	@Override
	public void setOnLabelNodeChange(String onLabelNodeChange) {
		getStateHelper().put(ON_LABEL_NODE_CHANGE, onLabelNodeChange);
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
	public String getOnSelectedIndexChange() {
		return (String) getStateHelper().eval(ON_SELECTED_INDEX_CHANGE, null);
	}

	@Override
	public void setOnSelectedIndexChange(String onSelectedIndexChange) {
		getStateHelper().put(ON_SELECTED_INDEX_CHANGE, onSelectedIndexChange);
	}

	@Override
	public String getOnShowTitleChange() {
		return (String) getStateHelper().eval(ON_SHOW_TITLE_CHANGE, null);
	}

	@Override
	public void setOnShowTitleChange(String onShowTitleChange) {
		getStateHelper().put(ON_SHOW_TITLE_CHANGE, onShowTitleChange);
	}

	@Override
	public String getOnSizeChange() {
		return (String) getStateHelper().eval(ON_SIZE_CHANGE, null);
	}

	@Override
	public void setOnSizeChange(String onSizeChange) {
		getStateHelper().put(ON_SIZE_CHANGE, onSizeChange);
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
	public String getOnTitleChange() {
		return (String) getStateHelper().eval(ON_TITLE_CHANGE, null);
	}

	@Override
	public void setOnTitleChange(String onTitleChange) {
		getStateHelper().put(ON_TITLE_CHANGE, onTitleChange);
	}

	@Override
	public String getOnUseARIAChange() {
		return (String) getStateHelper().eval(ON_USE_ARIACHANGE, null);
	}

	@Override
	public void setOnUseARIAChange(String onUseARIAChange) {
		getStateHelper().put(ON_USE_ARIACHANGE, onUseARIAChange);
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
	public Object getSelectedIndex() {
		return (Object) getStateHelper().eval(SELECTED_INDEX, null);
	}

	@Override
	public void setSelectedIndex(Object selectedIndex) {
		getStateHelper().put(SELECTED_INDEX, selectedIndex);
	}

	@Override
	public Boolean isShowTitle() {
		return (Boolean) getStateHelper().eval(SHOW_TITLE, null);
	}

	@Override
	public void setShowTitle(Boolean showTitle) {
		getStateHelper().put(SHOW_TITLE, showTitle);
	}

	@Override
	public Object getSize() {
		return (Object) getStateHelper().eval(SIZE, null);
	}

	@Override
	public void setSize(Object size) {
		getStateHelper().put(SIZE, size);
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
	public String getStyle() {
		return (String) getStateHelper().eval(STYLE, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(STYLE, style);
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
	public Boolean isUseARIA() {
		return (Boolean) getStateHelper().eval(USE_ARIA, null);
	}

	@Override
	public void setUseARIA(Boolean useARIA) {
		getStateHelper().put(USE_ARIA, useARIA);
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
	public Boolean isWidgetRender() {
		return (Boolean) getStateHelper().eval(WIDGET_RENDER, null);
	}

	@Override
	public void setWidgetRender(Boolean widgetRender) {
		getStateHelper().put(WIDGET_RENDER, widgetRender);
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
