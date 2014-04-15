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

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface StarRatingAlloy {

	// Public Constants
	public static final String AFTER_BOUNDING_BOX_CHANGE = "afterBoundingBoxChange";
	public static final String AFTER_CAN_RESET_CHANGE = "afterCanResetChange";
	public static final String AFTER_CONTENT_BOX_CHANGE = "afterContentBoxChange";
	public static final String AFTER_CSS_CLASS_CHANGE = "afterCssClassChange";
	public static final String AFTER_CSS_CLASSES_CHANGE = "afterCssClassesChange";
	public static final String AFTER_DEFAULT_SELECTED_CHANGE = "afterDefaultSelectedChange";
	public static final String AFTER_DESTROYED_CHANGE = "afterDestroyedChange";
	public static final String AFTER_DISABLED_CHANGE = "afterDisabledChange";
	public static final String AFTER_ELEMENTS_CHANGE = "afterElementsChange";
	public static final String AFTER_FOCUSED_CHANGE = "afterFocusedChange";
	public static final String AFTER_HEIGHT_CHANGE = "afterHeightChange";
	public static final String AFTER_HIDDEN_INPUT_CHANGE = "afterHiddenInputChange";
	public static final String AFTER_HIDE_CLASS_CHANGE = "afterHideClassChange";
	public static final String AFTER_ID_CHANGE = "afterIdChange";
	public static final String AFTER_INITIALIZED_CHANGE = "afterInitializedChange";
	public static final String AFTER_INPUT_NAME_CHANGE = "afterInputNameChange";
	public static final String AFTER_LABEL_CHANGE = "afterLabelChange";
	public static final String AFTER_LABEL_NODE_CHANGE = "afterLabelNodeChange";
	public static final String AFTER_LOCALE_CHANGE = "afterLocaleChange";
	public static final String AFTER_RENDER_CHANGE = "afterRenderChange";
	public static final String AFTER_RENDERED_CHANGE = "afterRenderedChange";
	public static final String AFTER_SELECTED_INDEX_CHANGE = "afterSelectedIndexChange";
	public static final String AFTER_SHOW_TITLE_CHANGE = "afterShowTitleChange";
	public static final String AFTER_SIZE_CHANGE = "afterSizeChange";
	public static final String AFTER_SRC_NODE_CHANGE = "afterSrcNodeChange";
	public static final String AFTER_STRINGS_CHANGE = "afterStringsChange";
	public static final String AFTER_TAB_INDEX_CHANGE = "afterTabIndexChange";
	public static final String AFTER_TITLE_CHANGE = "afterTitleChange";
	public static final String AFTER_USE_ARIACHANGE = "afterUseARIAChange";
	public static final String AFTER_VALUE_CHANGE = "afterValueChange";
	public static final String AFTER_VISIBLE_CHANGE = "afterVisibleChange";
	public static final String AFTER_WIDTH_CHANGE = "afterWidthChange";
	public static final String BOUNDING_BOX = "boundingBox";
	public static final String CAN_RESET = "canReset";
	public static final String CONTENT_BOX = "contentBox";
	public static final String CSS_CLASS = "cssClass";
	public static final String CSS_CLASSES = "cssClasses";
	public static final String DEFAULT_SELECTED = "defaultSelected";
	public static final String DESTROYED = "destroyed";
	public static final String DISABLED = "disabled";
	public static final String ELEMENTS = "elements";
	public static final String FOCUSED = "focused";
	public static final String HEIGHT = "height";
	public static final String HIDDEN_INPUT = "hiddenInput";
	public static final String HIDE_CLASS = "hideClass";
	public static final String ID = "id";
	public static final String INITIALIZED = "initialized";
	public static final String INPUT_NAME = "inputName";
	public static final String LABEL = "label";
	public static final String LABEL_NODE = "labelNode";
	public static final String LOCALE = "locale";
	public static final String ON_BOUNDING_BOX_CHANGE = "onBoundingBoxChange";
	public static final String ON_CAN_RESET_CHANGE = "onCanResetChange";
	public static final String ON_CONTENT_BOX_CHANGE = "onContentBoxChange";
	public static final String ON_CSS_CLASS_CHANGE = "onCssClassChange";
	public static final String ON_CSS_CLASSES_CHANGE = "onCssClassesChange";
	public static final String ON_DEFAULT_SELECTED_CHANGE = "onDefaultSelectedChange";
	public static final String ON_DESTROYED_CHANGE = "onDestroyedChange";
	public static final String ON_DISABLED_CHANGE = "onDisabledChange";
	public static final String ON_ELEMENTS_CHANGE = "onElementsChange";
	public static final String ON_FOCUSED_CHANGE = "onFocusedChange";
	public static final String ON_HEIGHT_CHANGE = "onHeightChange";
	public static final String ON_HIDDEN_INPUT_CHANGE = "onHiddenInputChange";
	public static final String ON_HIDE_CLASS_CHANGE = "onHideClassChange";
	public static final String ON_ID_CHANGE = "onIdChange";
	public static final String ON_INITIALIZED_CHANGE = "onInitializedChange";
	public static final String ON_INPUT_NAME_CHANGE = "onInputNameChange";
	public static final String ON_LABEL_CHANGE = "onLabelChange";
	public static final String ON_LABEL_NODE_CHANGE = "onLabelNodeChange";
	public static final String ON_LOCALE_CHANGE = "onLocaleChange";
	public static final String ON_RENDER_CHANGE = "onRenderChange";
	public static final String ON_RENDERED_CHANGE = "onRenderedChange";
	public static final String ON_SELECTED_INDEX_CHANGE = "onSelectedIndexChange";
	public static final String ON_SHOW_TITLE_CHANGE = "onShowTitleChange";
	public static final String ON_SIZE_CHANGE = "onSizeChange";
	public static final String ON_SRC_NODE_CHANGE = "onSrcNodeChange";
	public static final String ON_STRINGS_CHANGE = "onStringsChange";
	public static final String ON_TAB_INDEX_CHANGE = "onTabIndexChange";
	public static final String ON_TITLE_CHANGE = "onTitleChange";
	public static final String ON_USE_ARIACHANGE = "onUseARIAChange";
	public static final String ON_VALUE_CHANGE = "onValueChange";
	public static final String ON_VISIBLE_CHANGE = "onVisibleChange";
	public static final String ON_WIDTH_CHANGE = "onWidthChange";
	public static final String RENDERED = "rendered";
	public static final String SELECTED_INDEX = "selectedIndex";
	public static final String SHOW_TITLE = "showTitle";
	public static final String SIZE = "size";
	public static final String SRC_NODE = "srcNode";
	public static final String STRINGS = "strings";
	public static final String TAB_INDEX = "tabIndex";
	public static final String TITLE = "title";
	public static final String USE_ARIA = "useARIA";
	public static final String VISIBLE = "visible";
	public static final String WIDGET_RENDER = "render";
	public static final String WIDTH = "width";

	public String getAfterBoundingBoxChange();

	public void setAfterBoundingBoxChange(String afterBoundingBoxChange);

	public String getAfterCanResetChange();

	public void setAfterCanResetChange(String afterCanResetChange);

	public String getAfterContentBoxChange();

	public void setAfterContentBoxChange(String afterContentBoxChange);

	public String getAfterCssClassChange();

	public void setAfterCssClassChange(String afterCssClassChange);

	public String getAfterCssClassesChange();

	public void setAfterCssClassesChange(String afterCssClassesChange);

	public String getAfterDefaultSelectedChange();

	public void setAfterDefaultSelectedChange(String afterDefaultSelectedChange);

	public String getAfterDestroyedChange();

	public void setAfterDestroyedChange(String afterDestroyedChange);

	public String getAfterDisabledChange();

	public void setAfterDisabledChange(String afterDisabledChange);

	public String getAfterElementsChange();

	public void setAfterElementsChange(String afterElementsChange);

	public String getAfterFocusedChange();

	public void setAfterFocusedChange(String afterFocusedChange);

	public String getAfterHeightChange();

	public void setAfterHeightChange(String afterHeightChange);

	public String getAfterHiddenInputChange();

	public void setAfterHiddenInputChange(String afterHiddenInputChange);

	public String getAfterHideClassChange();

	public void setAfterHideClassChange(String afterHideClassChange);

	public String getAfterIdChange();

	public void setAfterIdChange(String afterIdChange);

	public String getAfterInitializedChange();

	public void setAfterInitializedChange(String afterInitializedChange);

	public String getAfterInputNameChange();

	public void setAfterInputNameChange(String afterInputNameChange);

	public String getAfterLabelChange();

	public void setAfterLabelChange(String afterLabelChange);

	public String getAfterLabelNodeChange();

	public void setAfterLabelNodeChange(String afterLabelNodeChange);

	public String getAfterLocaleChange();

	public void setAfterLocaleChange(String afterLocaleChange);

	public String getAfterRenderChange();

	public void setAfterRenderChange(String afterRenderChange);

	public String getAfterRenderedChange();

	public void setAfterRenderedChange(String afterRenderedChange);

	public String getAfterSelectedIndexChange();

	public void setAfterSelectedIndexChange(String afterSelectedIndexChange);

	public String getAfterShowTitleChange();

	public void setAfterShowTitleChange(String afterShowTitleChange);

	public String getAfterSizeChange();

	public void setAfterSizeChange(String afterSizeChange);

	public String getAfterSrcNodeChange();

	public void setAfterSrcNodeChange(String afterSrcNodeChange);

	public String getAfterStringsChange();

	public void setAfterStringsChange(String afterStringsChange);

	public String getAfterTabIndexChange();

	public void setAfterTabIndexChange(String afterTabIndexChange);

	public String getAfterTitleChange();

	public void setAfterTitleChange(String afterTitleChange);

	public String getAfterUseARIAChange();

	public void setAfterUseARIAChange(String afterUseARIAChange);

	public String getAfterValueChange();

	public void setAfterValueChange(String afterValueChange);

	public String getAfterVisibleChange();

	public void setAfterVisibleChange(String afterVisibleChange);

	public String getAfterWidthChange();

	public void setAfterWidthChange(String afterWidthChange);

	public String getBoundingBox();

	public void setBoundingBox(String boundingBox);

	public Boolean isCanReset();

	public void setCanReset(Boolean canReset);

	public String getContentBox();

	public void setContentBox(String contentBox);

	public String getCssClass();

	public void setCssClass(String cssClass);

	public Object getCssClasses();

	public void setCssClasses(Object cssClasses);

	public Object getDefaultSelected();

	public void setDefaultSelected(Object defaultSelected);

	public Boolean isDestroyed();

	public void setDestroyed(Boolean destroyed);

	public boolean isDisabled();

	public void setDisabled(boolean disabled);

	public String getElements();

	public void setElements(String elements);

	public Boolean isFocused();

	public void setFocused(Boolean focused);

	public Object getHeight();

	public void setHeight(Object height);

	public String getHiddenInput();

	public void setHiddenInput(String hiddenInput);

	public String getHideClass();

	public void setHideClass(String hideClass);

	public String getId();

	public void setId(String id);

	public Boolean isInitialized();

	public void setInitialized(Boolean initialized);

	public String getInputName();

	public void setInputName(String inputName);

	public String getLabel();

	public void setLabel(String label);

	public String getLabelNode();

	public void setLabelNode(String labelNode);

	public String getLocale();

	public void setLocale(String locale);

	public String getOnBoundingBoxChange();

	public void setOnBoundingBoxChange(String onBoundingBoxChange);

	public String getOnCanResetChange();

	public void setOnCanResetChange(String onCanResetChange);

	public String getOnContentBoxChange();

	public void setOnContentBoxChange(String onContentBoxChange);

	public String getOnCssClassChange();

	public void setOnCssClassChange(String onCssClassChange);

	public String getOnCssClassesChange();

	public void setOnCssClassesChange(String onCssClassesChange);

	public String getOnDefaultSelectedChange();

	public void setOnDefaultSelectedChange(String onDefaultSelectedChange);

	public String getOnDestroyedChange();

	public void setOnDestroyedChange(String onDestroyedChange);

	public String getOnDisabledChange();

	public void setOnDisabledChange(String onDisabledChange);

	public String getOnElementsChange();

	public void setOnElementsChange(String onElementsChange);

	public String getOnFocusedChange();

	public void setOnFocusedChange(String onFocusedChange);

	public String getOnHeightChange();

	public void setOnHeightChange(String onHeightChange);

	public String getOnHiddenInputChange();

	public void setOnHiddenInputChange(String onHiddenInputChange);

	public String getOnHideClassChange();

	public void setOnHideClassChange(String onHideClassChange);

	public String getOnIdChange();

	public void setOnIdChange(String onIdChange);

	public String getOnInitializedChange();

	public void setOnInitializedChange(String onInitializedChange);

	public String getOnInputNameChange();

	public void setOnInputNameChange(String onInputNameChange);

	public String getOnLabelChange();

	public void setOnLabelChange(String onLabelChange);

	public String getOnLabelNodeChange();

	public void setOnLabelNodeChange(String onLabelNodeChange);

	public String getOnLocaleChange();

	public void setOnLocaleChange(String onLocaleChange);

	public String getOnRenderChange();

	public void setOnRenderChange(String onRenderChange);

	public String getOnRenderedChange();

	public void setOnRenderedChange(String onRenderedChange);

	public String getOnSelectedIndexChange();

	public void setOnSelectedIndexChange(String onSelectedIndexChange);

	public String getOnShowTitleChange();

	public void setOnShowTitleChange(String onShowTitleChange);

	public String getOnSizeChange();

	public void setOnSizeChange(String onSizeChange);

	public String getOnSrcNodeChange();

	public void setOnSrcNodeChange(String onSrcNodeChange);

	public String getOnStringsChange();

	public void setOnStringsChange(String onStringsChange);

	public String getOnTabIndexChange();

	public void setOnTabIndexChange(String onTabIndexChange);

	public String getOnTitleChange();

	public void setOnTitleChange(String onTitleChange);

	public String getOnUseARIAChange();

	public void setOnUseARIAChange(String onUseARIAChange);

	public String getOnValueChange();

	public void setOnValueChange(String onValueChange);

	public String getOnVisibleChange();

	public void setOnVisibleChange(String onVisibleChange);

	public String getOnWidthChange();

	public void setOnWidthChange(String onWidthChange);

	public boolean isRendered();

	public void setRendered(boolean rendered);

	public Object getSelectedIndex();

	public void setSelectedIndex(Object selectedIndex);

	public Boolean isShowTitle();

	public void setShowTitle(Boolean showTitle);

	public Object getSize();

	public void setSize(Object size);

	public String getSrcNode();

	public void setSrcNode(String srcNode);

	public Object getStrings();

	public void setStrings(Object strings);

	public Object getTabIndex();

	public void setTabIndex(Object tabIndex);

	public String getTitle();

	public void setTitle(String title);

	public Boolean isUseARIA();

	public void setUseARIA(Boolean useARIA);

	public Boolean isVisible();

	public void setVisible(Boolean visible);

	public Boolean isWidgetRender();

	public void setWidgetRender(Boolean widgetRender);

	public Object getWidth();

	public void setWidth(Object width);
}
//J+
