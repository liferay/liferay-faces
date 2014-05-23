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
package com.liferay.faces.alloy.component.selectstarrating;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.DelegatingAlloyRendererBase;
import com.liferay.faces.alloy.util.AlloyConstants;
import com.liferay.faces.util.lang.StringPool;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class SelectStarRatingRendererBase extends DelegatingAlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "SelectStarRating";
	private static final String ALLOY_MODULE_NAME = "aui-rating";
	private static final String BOUNDING_BOX_CHANGE = "boundingBoxChange";
	private static final String CAN_RESET_CHANGE = "canResetChange";
	private static final String CONTENT_BOX_CHANGE = "contentBoxChange";
	private static final String CSS_CLASS_CHANGE = "cssClassChange";
	private static final String CSS_CLASSES_CHANGE = "cssClassesChange";
	private static final String DEFAULT_SELECTED_CHANGE = "defaultSelectedChange";
	private static final String DESTROYED_CHANGE = "destroyedChange";
	private static final String DISABLED_CHANGE = "disabledChange";
	private static final String ELEMENTS_CHANGE = "elementsChange";
	private static final String FOCUSED_CHANGE = "focusedChange";
	private static final String HEIGHT_CHANGE = "heightChange";
	private static final String HIDDEN_INPUT_CHANGE = "hiddenInputChange";
	private static final String HIDE_CLASS_CHANGE = "hideClassChange";
	private static final String ID_CHANGE = "idChange";
	private static final String INITIALIZED_CHANGE = "initializedChange";
	private static final String INPUT_NAME_CHANGE = "inputNameChange";
	private static final String LABEL_CHANGE = "labelChange";
	private static final String LABEL_NODE_CHANGE = "labelNodeChange";
	private static final String LOCALE_CHANGE = "localeChange";
	private static final String RENDER_CHANGE = "renderChange";
	private static final String RENDERED_CHANGE = "renderedChange";
	private static final String SELECTED_INDEX_CHANGE = "selectedIndexChange";
	private static final String SHOW_TITLE_CHANGE = "showTitleChange";
	private static final String SIZE_CHANGE = "sizeChange";
	private static final String SRC_NODE_CHANGE = "srcNodeChange";
	private static final String STRINGS_CHANGE = "stringsChange";
	private static final String TAB_INDEX_CHANGE = "tabIndexChange";
	private static final String TITLE_CHANGE = "titleChange";
	private static final String USE_ARIACHANGE = "useARIAChange";
	private static final String VALUE_CHANGE = "valueChange";
	private static final String VISIBLE_CHANGE = "visibleChange";
	private static final String WIDTH_CHANGE = "widthChange";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		SelectStarRatingAlloy selectStarRatingAlloy = (SelectStarRatingAlloy) uiComponent;
		boolean first = true;

		String boundingBox = selectStarRatingAlloy.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, selectStarRatingAlloy, boundingBox, first);
			first = false;
		}

		Boolean canReset = selectStarRatingAlloy.isCanReset();

		if (canReset != null) {

			encodeCanReset(responseWriter, selectStarRatingAlloy, canReset, first);
			first = false;
		}

		String contentBox = selectStarRatingAlloy.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, selectStarRatingAlloy, contentBox, first);
			first = false;
		}

		String cssClass = selectStarRatingAlloy.getCssClass();

		if (cssClass != null) {

			encodeCssClass(responseWriter, selectStarRatingAlloy, cssClass, first);
			first = false;
		}

		Object cssClasses = selectStarRatingAlloy.getCssClasses();

		if (cssClasses != null) {

			encodeCssClasses(responseWriter, selectStarRatingAlloy, cssClasses, first);
			first = false;
		}

		Object defaultSelected = selectStarRatingAlloy.getDefaultSelected();

		if (defaultSelected != null) {

			encodeDefaultSelected(responseWriter, selectStarRatingAlloy, defaultSelected, first);
			first = false;
		}

		Boolean disabled = selectStarRatingAlloy.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, selectStarRatingAlloy, disabled, first);
			first = false;
		}

		Object height = selectStarRatingAlloy.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, selectStarRatingAlloy, height, first);
			first = false;
		}

		String hiddenInput = selectStarRatingAlloy.getHiddenInput();

		if (hiddenInput != null) {

			encodeHiddenInput(responseWriter, selectStarRatingAlloy, hiddenInput, first);
			first = false;
		}

		String hideClass = selectStarRatingAlloy.getHideClass();

		if (hideClass != null) {

			encodeHideClass(responseWriter, selectStarRatingAlloy, hideClass, first);
			first = false;
		}

		String inputName = selectStarRatingAlloy.getInputName();

		if (inputName != null) {

			encodeInputName(responseWriter, selectStarRatingAlloy, inputName, first);
			first = false;
		}

		String label = selectStarRatingAlloy.getLabel();

		if (label != null) {

			encodeLabel(responseWriter, selectStarRatingAlloy, label, first);
			first = false;
		}

		String labelNode = selectStarRatingAlloy.getLabelNode();

		if (labelNode != null) {

			encodeLabelNode(responseWriter, selectStarRatingAlloy, labelNode, first);
			first = false;
		}

		String locale = selectStarRatingAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, selectStarRatingAlloy, locale, first);
			first = false;
		}

		Object selectedIndex = selectStarRatingAlloy.getSelectedIndex();

		if (selectedIndex != null) {

			encodeSelectedIndex(responseWriter, selectStarRatingAlloy, selectedIndex, first);
			first = false;
		}

		Boolean showTitle = selectStarRatingAlloy.isShowTitle();

		if (showTitle != null) {

			encodeShowTitle(responseWriter, selectStarRatingAlloy, showTitle, first);
			first = false;
		}

		Object size = selectStarRatingAlloy.getSize();

		if (size != null) {

			encodeSize(responseWriter, selectStarRatingAlloy, size, first);
			first = false;
		}

		String srcNode = selectStarRatingAlloy.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, selectStarRatingAlloy, srcNode, first);
			first = false;
		}

		Object strings = selectStarRatingAlloy.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, selectStarRatingAlloy, strings, first);
			first = false;
		}

		Object tabIndex = selectStarRatingAlloy.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, selectStarRatingAlloy, tabIndex, first);
			first = false;
		}

		String title = selectStarRatingAlloy.getTitle();

		if (title != null) {

			encodeTitle(responseWriter, selectStarRatingAlloy, title, first);
			first = false;
		}

		Boolean useARIA = selectStarRatingAlloy.isUseARIA();

		if (useARIA != null) {

			encodeUseARIA(responseWriter, selectStarRatingAlloy, useARIA, first);
			first = false;
		}

		Object value = selectStarRatingAlloy.getValue();

		if (value != null) {

			encodeValue(responseWriter, selectStarRatingAlloy, value, first);
			first = false;
		}

		Boolean visible = selectStarRatingAlloy.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, selectStarRatingAlloy, visible, first);
			first = false;
		}

		Boolean widgetRender = selectStarRatingAlloy.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, selectStarRatingAlloy, widgetRender, first);
			first = false;
		}

		Object width = selectStarRatingAlloy.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, selectStarRatingAlloy, width, first);
			first = false;
		}

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterBoundingBoxChange = selectStarRatingAlloy.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, selectStarRatingAlloy, afterBoundingBoxChange, first);
			first = false;
		}

		String afterCanResetChange = selectStarRatingAlloy.getAfterCanResetChange();

		if (afterCanResetChange != null) {

			encodeAfterCanResetChange(responseWriter, selectStarRatingAlloy, afterCanResetChange, first);
			first = false;
		}

		String afterContentBoxChange = selectStarRatingAlloy.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, selectStarRatingAlloy, afterContentBoxChange, first);
			first = false;
		}

		String afterCssClassChange = selectStarRatingAlloy.getAfterCssClassChange();

		if (afterCssClassChange != null) {

			encodeAfterCssClassChange(responseWriter, selectStarRatingAlloy, afterCssClassChange, first);
			first = false;
		}

		String afterCssClassesChange = selectStarRatingAlloy.getAfterCssClassesChange();

		if (afterCssClassesChange != null) {

			encodeAfterCssClassesChange(responseWriter, selectStarRatingAlloy, afterCssClassesChange, first);
			first = false;
		}

		String afterDefaultSelectedChange = selectStarRatingAlloy.getAfterDefaultSelectedChange();

		if (afterDefaultSelectedChange != null) {

			encodeAfterDefaultSelectedChange(responseWriter, selectStarRatingAlloy, afterDefaultSelectedChange, first);
			first = false;
		}

		String afterDestroyedChange = selectStarRatingAlloy.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, selectStarRatingAlloy, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = selectStarRatingAlloy.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, selectStarRatingAlloy, afterDisabledChange, first);
			first = false;
		}

		String afterElementsChange = selectStarRatingAlloy.getAfterElementsChange();

		if (afterElementsChange != null) {

			encodeAfterElementsChange(responseWriter, selectStarRatingAlloy, afterElementsChange, first);
			first = false;
		}

		String afterFocusedChange = selectStarRatingAlloy.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, selectStarRatingAlloy, afterFocusedChange, first);
			first = false;
		}

		String afterHeightChange = selectStarRatingAlloy.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, selectStarRatingAlloy, afterHeightChange, first);
			first = false;
		}

		String afterHiddenInputChange = selectStarRatingAlloy.getAfterHiddenInputChange();

		if (afterHiddenInputChange != null) {

			encodeAfterHiddenInputChange(responseWriter, selectStarRatingAlloy, afterHiddenInputChange, first);
			first = false;
		}

		String afterHideClassChange = selectStarRatingAlloy.getAfterHideClassChange();

		if (afterHideClassChange != null) {

			encodeAfterHideClassChange(responseWriter, selectStarRatingAlloy, afterHideClassChange, first);
			first = false;
		}

		String afterIdChange = selectStarRatingAlloy.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, selectStarRatingAlloy, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = selectStarRatingAlloy.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, selectStarRatingAlloy, afterInitializedChange, first);
			first = false;
		}

		String afterInputNameChange = selectStarRatingAlloy.getAfterInputNameChange();

		if (afterInputNameChange != null) {

			encodeAfterInputNameChange(responseWriter, selectStarRatingAlloy, afterInputNameChange, first);
			first = false;
		}

		String afterLabelChange = selectStarRatingAlloy.getAfterLabelChange();

		if (afterLabelChange != null) {

			encodeAfterLabelChange(responseWriter, selectStarRatingAlloy, afterLabelChange, first);
			first = false;
		}

		String afterLabelNodeChange = selectStarRatingAlloy.getAfterLabelNodeChange();

		if (afterLabelNodeChange != null) {

			encodeAfterLabelNodeChange(responseWriter, selectStarRatingAlloy, afterLabelNodeChange, first);
			first = false;
		}

		String afterLocaleChange = selectStarRatingAlloy.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, selectStarRatingAlloy, afterLocaleChange, first);
			first = false;
		}

		String afterRenderChange = selectStarRatingAlloy.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, selectStarRatingAlloy, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = selectStarRatingAlloy.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, selectStarRatingAlloy, afterRenderedChange, first);
			first = false;
		}

		String afterSelectedIndexChange = selectStarRatingAlloy.getAfterSelectedIndexChange();

		if (afterSelectedIndexChange != null) {

			encodeAfterSelectedIndexChange(responseWriter, selectStarRatingAlloy, afterSelectedIndexChange, first);
			first = false;
		}

		String afterShowTitleChange = selectStarRatingAlloy.getAfterShowTitleChange();

		if (afterShowTitleChange != null) {

			encodeAfterShowTitleChange(responseWriter, selectStarRatingAlloy, afterShowTitleChange, first);
			first = false;
		}

		String afterSizeChange = selectStarRatingAlloy.getAfterSizeChange();

		if (afterSizeChange != null) {

			encodeAfterSizeChange(responseWriter, selectStarRatingAlloy, afterSizeChange, first);
			first = false;
		}

		String afterSrcNodeChange = selectStarRatingAlloy.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, selectStarRatingAlloy, afterSrcNodeChange, first);
			first = false;
		}

		String afterStringsChange = selectStarRatingAlloy.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, selectStarRatingAlloy, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = selectStarRatingAlloy.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, selectStarRatingAlloy, afterTabIndexChange, first);
			first = false;
		}

		String afterTitleChange = selectStarRatingAlloy.getAfterTitleChange();

		if (afterTitleChange != null) {

			encodeAfterTitleChange(responseWriter, selectStarRatingAlloy, afterTitleChange, first);
			first = false;
		}

		String afterUseARIAChange = selectStarRatingAlloy.getAfterUseARIAChange();

		if (afterUseARIAChange != null) {

			encodeAfterUseARIAChange(responseWriter, selectStarRatingAlloy, afterUseARIAChange, first);
			first = false;
		}

		String afterValueChange = selectStarRatingAlloy.getAfterValueChange();

		if (afterValueChange != null) {

			encodeAfterValueChange(responseWriter, selectStarRatingAlloy, afterValueChange, first);
			first = false;
		}

		String afterVisibleChange = selectStarRatingAlloy.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, selectStarRatingAlloy, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = selectStarRatingAlloy.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, selectStarRatingAlloy, afterWidthChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onBoundingBoxChange = selectStarRatingAlloy.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, selectStarRatingAlloy, onBoundingBoxChange, first);
			first = false;
		}

		String onCanResetChange = selectStarRatingAlloy.getOnCanResetChange();

		if (onCanResetChange != null) {

			encodeOnCanResetChange(responseWriter, selectStarRatingAlloy, onCanResetChange, first);
			first = false;
		}

		String onContentBoxChange = selectStarRatingAlloy.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, selectStarRatingAlloy, onContentBoxChange, first);
			first = false;
		}

		String onCssClassChange = selectStarRatingAlloy.getOnCssClassChange();

		if (onCssClassChange != null) {

			encodeOnCssClassChange(responseWriter, selectStarRatingAlloy, onCssClassChange, first);
			first = false;
		}

		String onCssClassesChange = selectStarRatingAlloy.getOnCssClassesChange();

		if (onCssClassesChange != null) {

			encodeOnCssClassesChange(responseWriter, selectStarRatingAlloy, onCssClassesChange, first);
			first = false;
		}

		String onDefaultSelectedChange = selectStarRatingAlloy.getOnDefaultSelectedChange();

		if (onDefaultSelectedChange != null) {

			encodeOnDefaultSelectedChange(responseWriter, selectStarRatingAlloy, onDefaultSelectedChange, first);
			first = false;
		}

		String onDestroyedChange = selectStarRatingAlloy.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, selectStarRatingAlloy, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = selectStarRatingAlloy.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, selectStarRatingAlloy, onDisabledChange, first);
			first = false;
		}

		String onElementsChange = selectStarRatingAlloy.getOnElementsChange();

		if (onElementsChange != null) {

			encodeOnElementsChange(responseWriter, selectStarRatingAlloy, onElementsChange, first);
			first = false;
		}

		String onFocusedChange = selectStarRatingAlloy.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, selectStarRatingAlloy, onFocusedChange, first);
			first = false;
		}

		String onHeightChange = selectStarRatingAlloy.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, selectStarRatingAlloy, onHeightChange, first);
			first = false;
		}

		String onHiddenInputChange = selectStarRatingAlloy.getOnHiddenInputChange();

		if (onHiddenInputChange != null) {

			encodeOnHiddenInputChange(responseWriter, selectStarRatingAlloy, onHiddenInputChange, first);
			first = false;
		}

		String onHideClassChange = selectStarRatingAlloy.getOnHideClassChange();

		if (onHideClassChange != null) {

			encodeOnHideClassChange(responseWriter, selectStarRatingAlloy, onHideClassChange, first);
			first = false;
		}

		String onIdChange = selectStarRatingAlloy.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, selectStarRatingAlloy, onIdChange, first);
			first = false;
		}

		String onInitializedChange = selectStarRatingAlloy.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, selectStarRatingAlloy, onInitializedChange, first);
			first = false;
		}

		String onInputNameChange = selectStarRatingAlloy.getOnInputNameChange();

		if (onInputNameChange != null) {

			encodeOnInputNameChange(responseWriter, selectStarRatingAlloy, onInputNameChange, first);
			first = false;
		}

		String onLabelChange = selectStarRatingAlloy.getOnLabelChange();

		if (onLabelChange != null) {

			encodeOnLabelChange(responseWriter, selectStarRatingAlloy, onLabelChange, first);
			first = false;
		}

		String onLabelNodeChange = selectStarRatingAlloy.getOnLabelNodeChange();

		if (onLabelNodeChange != null) {

			encodeOnLabelNodeChange(responseWriter, selectStarRatingAlloy, onLabelNodeChange, first);
			first = false;
		}

		String onLocaleChange = selectStarRatingAlloy.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, selectStarRatingAlloy, onLocaleChange, first);
			first = false;
		}

		String onRenderChange = selectStarRatingAlloy.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, selectStarRatingAlloy, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = selectStarRatingAlloy.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, selectStarRatingAlloy, onRenderedChange, first);
			first = false;
		}

		String onSelectedIndexChange = selectStarRatingAlloy.getOnSelectedIndexChange();

		if (onSelectedIndexChange != null) {

			encodeOnSelectedIndexChange(responseWriter, selectStarRatingAlloy, onSelectedIndexChange, first);
			first = false;
		}

		String onShowTitleChange = selectStarRatingAlloy.getOnShowTitleChange();

		if (onShowTitleChange != null) {

			encodeOnShowTitleChange(responseWriter, selectStarRatingAlloy, onShowTitleChange, first);
			first = false;
		}

		String onSizeChange = selectStarRatingAlloy.getOnSizeChange();

		if (onSizeChange != null) {

			encodeOnSizeChange(responseWriter, selectStarRatingAlloy, onSizeChange, first);
			first = false;
		}

		String onSrcNodeChange = selectStarRatingAlloy.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, selectStarRatingAlloy, onSrcNodeChange, first);
			first = false;
		}

		String onStringsChange = selectStarRatingAlloy.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, selectStarRatingAlloy, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = selectStarRatingAlloy.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, selectStarRatingAlloy, onTabIndexChange, first);
			first = false;
		}

		String onTitleChange = selectStarRatingAlloy.getOnTitleChange();

		if (onTitleChange != null) {

			encodeOnTitleChange(responseWriter, selectStarRatingAlloy, onTitleChange, first);
			first = false;
		}

		String onUseARIAChange = selectStarRatingAlloy.getOnUseARIAChange();

		if (onUseARIAChange != null) {

			encodeOnUseARIAChange(responseWriter, selectStarRatingAlloy, onUseARIAChange, first);
			first = false;
		}

		String onValueChange = selectStarRatingAlloy.getOnValueChange();

		if (onValueChange != null) {

			encodeOnValueChange(responseWriter, selectStarRatingAlloy, onValueChange, first);
			first = false;
		}

		String onVisibleChange = selectStarRatingAlloy.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, selectStarRatingAlloy, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = selectStarRatingAlloy.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, selectStarRatingAlloy, onWidthChange, first);
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

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterCanResetChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterCanResetChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CAN_RESET_CHANGE, afterCanResetChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterCssClassChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterCssClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASS_CHANGE, afterCssClassChange, first);
	}

	protected void encodeAfterCssClassesChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterCssClassesChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASSES_CHANGE, afterCssClassesChange, first);
	}

	protected void encodeAfterDefaultSelectedChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterDefaultSelectedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_SELECTED_CHANGE, afterDefaultSelectedChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterElementsChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterElementsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ELEMENTS_CHANGE, afterElementsChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterHiddenInputChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterHiddenInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDDEN_INPUT_CHANGE, afterHiddenInputChange, first);
	}

	protected void encodeAfterHideClassChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterHideClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDE_CLASS_CHANGE, afterHideClassChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterInputNameChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterInputNameChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_NAME_CHANGE, afterInputNameChange, first);
	}

	protected void encodeAfterLabelChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterLabelChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_CHANGE, afterLabelChange, first);
	}

	protected void encodeAfterLabelNodeChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterLabelNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_NODE_CHANGE, afterLabelNodeChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterSelectedIndexChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterSelectedIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTED_INDEX_CHANGE, afterSelectedIndexChange, first);
	}

	protected void encodeAfterShowTitleChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterShowTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_TITLE_CHANGE, afterShowTitleChange, first);
	}

	protected void encodeAfterSizeChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SIZE_CHANGE, afterSizeChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterTitleChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TITLE_CHANGE, afterTitleChange, first);
	}

	protected void encodeAfterUseARIAChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterUseARIAChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_ARIACHANGE, afterUseARIAChange, first);
	}

	protected void encodeAfterValueChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, afterValueChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeCanReset(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean canReset, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.CAN_RESET, canReset, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.CONTENT_BOX, contentBox, first);
	}

	protected void encodeCssClass(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String cssClass, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.CSS_CLASS, cssClass, first);
	}

	protected void encodeCssClasses(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object cssClasses, boolean first) throws IOException {
		encodeObject(responseWriter, SelectStarRatingAlloy.CSS_CLASSES, cssClasses, first);
	}

	protected void encodeDefaultSelected(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object defaultSelected, boolean first) throws IOException {
		encodeNumber(responseWriter, SelectStarRatingAlloy.DEFAULT_SELECTED, defaultSelected, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.DISABLED, disabled, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object height, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, SelectStarRatingAlloy.HEIGHT, height, first);
	}

	protected void encodeHiddenInput(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String hiddenInput, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.HIDDEN_INPUT, hiddenInput, first);
	}

	protected void encodeHideClass(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String hideClass, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.HIDE_CLASS, hideClass, first);
	}

	protected void encodeInputName(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String inputName, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.INPUT_NAME, inputName, first);
	}

	protected void encodeLabel(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String label, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.LABEL, label, first);
	}

	protected void encodeLabelNode(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String labelNode, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.LABEL_NODE, labelNode, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String locale, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.LOCALE, locale, first);
	}

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnCanResetChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onCanResetChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CAN_RESET_CHANGE, onCanResetChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnCssClassChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onCssClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASS_CHANGE, onCssClassChange, first);
	}

	protected void encodeOnCssClassesChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onCssClassesChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASSES_CHANGE, onCssClassesChange, first);
	}

	protected void encodeOnDefaultSelectedChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onDefaultSelectedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_SELECTED_CHANGE, onDefaultSelectedChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnElementsChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onElementsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ELEMENTS_CHANGE, onElementsChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnHiddenInputChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onHiddenInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDDEN_INPUT_CHANGE, onHiddenInputChange, first);
	}

	protected void encodeOnHideClassChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onHideClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDE_CLASS_CHANGE, onHideClassChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnInputNameChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onInputNameChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_NAME_CHANGE, onInputNameChange, first);
	}

	protected void encodeOnLabelChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onLabelChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_CHANGE, onLabelChange, first);
	}

	protected void encodeOnLabelNodeChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onLabelNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_NODE_CHANGE, onLabelNodeChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnSelectedIndexChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onSelectedIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTED_INDEX_CHANGE, onSelectedIndexChange, first);
	}

	protected void encodeOnShowTitleChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onShowTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_TITLE_CHANGE, onShowTitleChange, first);
	}

	protected void encodeOnSizeChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SIZE_CHANGE, onSizeChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnTitleChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TITLE_CHANGE, onTitleChange, first);
	}

	protected void encodeOnUseARIAChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onUseARIAChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_ARIACHANGE, onUseARIAChange, first);
	}

	protected void encodeOnValueChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, onValueChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
	}

	protected void encodeSelectedIndex(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object selectedIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, SelectStarRatingAlloy.SELECTED_INDEX, selectedIndex, first);
	}

	protected void encodeShowTitle(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean showTitle, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.SHOW_TITLE, showTitle, first);
	}

	protected void encodeSize(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object size, boolean first) throws IOException {
		encodeNumber(responseWriter, SelectStarRatingAlloy.SIZE, size, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.SRC_NODE, srcNode, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, SelectStarRatingAlloy.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, SelectStarRatingAlloy.TAB_INDEX, tabIndex, first);
	}

	protected void encodeTitle(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, String title, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.TITLE, title, first);
	}

	protected void encodeUseARIA(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean useARIA, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.USE_ARIA, useARIA, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object value, boolean first) throws IOException {
		encodeString(responseWriter, SelectStarRatingAlloy.VALUE, value, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.VISIBLE, visible, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, SelectStarRatingAlloy.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, SelectStarRatingAlloy selectStarRatingAlloy, Object width, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, SelectStarRatingAlloy.WIDTH, width, first);
	}
}
//J+
