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
package com.liferay.faces.alloy.component.rating;
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
public abstract class RatingRendererBase extends AlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "Rating";
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
	protected void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		RatingComponent ratingComponent = (RatingComponent) uiComponent;
		boolean first = true;

		String boundingBox = ratingComponent.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, ratingComponent, boundingBox, first);
			first = false;
		}

		Boolean canReset = ratingComponent.isCanReset();

		if (canReset != null) {

			encodeCanReset(responseWriter, ratingComponent, canReset, first);
			first = false;
		}

		String contentBox = ratingComponent.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, ratingComponent, contentBox, first);
			first = false;
		}

		String cssClass = ratingComponent.getCssClass();

		if (cssClass != null) {

			encodeCssClass(responseWriter, ratingComponent, cssClass, first);
			first = false;
		}

		Object cssClasses = ratingComponent.getCssClasses();

		if (cssClasses != null) {

			encodeCssClasses(responseWriter, ratingComponent, cssClasses, first);
			first = false;
		}

		Object defaultSelected = ratingComponent.getDefaultSelected();

		if (defaultSelected != null) {

			encodeDefaultSelected(responseWriter, ratingComponent, defaultSelected, first);
			first = false;
		}

		Boolean destroyed = ratingComponent.isDestroyed();

		if (destroyed != null) {

			encodeDestroyed(responseWriter, ratingComponent, destroyed, first);
			first = false;
		}

		Boolean disabled = ratingComponent.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, ratingComponent, disabled, first);
			first = false;
		}

		String elements = ratingComponent.getElements();

		if (elements != null) {

			encodeElements(responseWriter, ratingComponent, elements, first);
			first = false;
		}

		Boolean focused = ratingComponent.isFocused();

		if (focused != null) {

			encodeFocused(responseWriter, ratingComponent, focused, first);
			first = false;
		}

		Object height = ratingComponent.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, ratingComponent, height, first);
			first = false;
		}

		String hiddenInput = ratingComponent.getHiddenInput();

		if (hiddenInput != null) {

			encodeHiddenInput(responseWriter, ratingComponent, hiddenInput, first);
			first = false;
		}

		String hideClass = ratingComponent.getHideClass();

		if (hideClass != null) {

			encodeHideClass(responseWriter, ratingComponent, hideClass, first);
			first = false;
		}

		String id = ratingComponent.getId();

		if (id != null) {

			encodeId(responseWriter, ratingComponent, id, first);
			first = false;
		}

		Boolean initialized = ratingComponent.isInitialized();

		if (initialized != null) {

			encodeInitialized(responseWriter, ratingComponent, initialized, first);
			first = false;
		}

		String inputName = ratingComponent.getInputName();

		if (inputName != null) {

			encodeInputName(responseWriter, ratingComponent, inputName, first);
			first = false;
		}

		String label = ratingComponent.getLabel();

		if (label != null) {

			encodeLabel(responseWriter, ratingComponent, label, first);
			first = false;
		}

		String labelNode = ratingComponent.getLabelNode();

		if (labelNode != null) {

			encodeLabelNode(responseWriter, ratingComponent, labelNode, first);
			first = false;
		}

		String locale = ratingComponent.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, ratingComponent, locale, first);
			first = false;
		}

		Boolean rendered = ratingComponent.isRendered();

		if (rendered != null) {

			encodeRendered(responseWriter, ratingComponent, rendered, first);
			first = false;
		}

		Object selectedIndex = ratingComponent.getSelectedIndex();

		if (selectedIndex != null) {

			encodeSelectedIndex(responseWriter, ratingComponent, selectedIndex, first);
			first = false;
		}

		Boolean showTitle = ratingComponent.isShowTitle();

		if (showTitle != null) {

			encodeShowTitle(responseWriter, ratingComponent, showTitle, first);
			first = false;
		}

		Object size = ratingComponent.getSize();

		if (size != null) {

			encodeSize(responseWriter, ratingComponent, size, first);
			first = false;
		}

		String srcNode = ratingComponent.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, ratingComponent, srcNode, first);
			first = false;
		}

		Object strings = ratingComponent.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, ratingComponent, strings, first);
			first = false;
		}

		Object tabIndex = ratingComponent.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, ratingComponent, tabIndex, first);
			first = false;
		}

		String title = ratingComponent.getTitle();

		if (title != null) {

			encodeTitle(responseWriter, ratingComponent, title, first);
			first = false;
		}

		Boolean useARIA = ratingComponent.isUseARIA();

		if (useARIA != null) {

			encodeUseARIA(responseWriter, ratingComponent, useARIA, first);
			first = false;
		}

		Object value = ratingComponent.getValue();

		if (value != null) {

			encodeValue(responseWriter, ratingComponent, value, first);
			first = false;
		}

		Boolean visible = ratingComponent.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, ratingComponent, visible, first);
			first = false;
		}

		Boolean widgetRender = ratingComponent.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, ratingComponent, widgetRender, first);
			first = false;
		}

		Object width = ratingComponent.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, ratingComponent, width, first);
			first = false;
		}

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterBoundingBoxChange = ratingComponent.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, ratingComponent, afterBoundingBoxChange, first);
			first = false;
		}

		String afterCanResetChange = ratingComponent.getAfterCanResetChange();

		if (afterCanResetChange != null) {

			encodeAfterCanResetChange(responseWriter, ratingComponent, afterCanResetChange, first);
			first = false;
		}

		String afterContentBoxChange = ratingComponent.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, ratingComponent, afterContentBoxChange, first);
			first = false;
		}

		String afterCssClassChange = ratingComponent.getAfterCssClassChange();

		if (afterCssClassChange != null) {

			encodeAfterCssClassChange(responseWriter, ratingComponent, afterCssClassChange, first);
			first = false;
		}

		String afterCssClassesChange = ratingComponent.getAfterCssClassesChange();

		if (afterCssClassesChange != null) {

			encodeAfterCssClassesChange(responseWriter, ratingComponent, afterCssClassesChange, first);
			first = false;
		}

		String afterDefaultSelectedChange = ratingComponent.getAfterDefaultSelectedChange();

		if (afterDefaultSelectedChange != null) {

			encodeAfterDefaultSelectedChange(responseWriter, ratingComponent, afterDefaultSelectedChange, first);
			first = false;
		}

		String afterDestroyedChange = ratingComponent.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, ratingComponent, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = ratingComponent.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, ratingComponent, afterDisabledChange, first);
			first = false;
		}

		String afterElementsChange = ratingComponent.getAfterElementsChange();

		if (afterElementsChange != null) {

			encodeAfterElementsChange(responseWriter, ratingComponent, afterElementsChange, first);
			first = false;
		}

		String afterFocusedChange = ratingComponent.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, ratingComponent, afterFocusedChange, first);
			first = false;
		}

		String afterHeightChange = ratingComponent.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, ratingComponent, afterHeightChange, first);
			first = false;
		}

		String afterHiddenInputChange = ratingComponent.getAfterHiddenInputChange();

		if (afterHiddenInputChange != null) {

			encodeAfterHiddenInputChange(responseWriter, ratingComponent, afterHiddenInputChange, first);
			first = false;
		}

		String afterHideClassChange = ratingComponent.getAfterHideClassChange();

		if (afterHideClassChange != null) {

			encodeAfterHideClassChange(responseWriter, ratingComponent, afterHideClassChange, first);
			first = false;
		}

		String afterIdChange = ratingComponent.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, ratingComponent, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = ratingComponent.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, ratingComponent, afterInitializedChange, first);
			first = false;
		}

		String afterInputNameChange = ratingComponent.getAfterInputNameChange();

		if (afterInputNameChange != null) {

			encodeAfterInputNameChange(responseWriter, ratingComponent, afterInputNameChange, first);
			first = false;
		}

		String afterLabelChange = ratingComponent.getAfterLabelChange();

		if (afterLabelChange != null) {

			encodeAfterLabelChange(responseWriter, ratingComponent, afterLabelChange, first);
			first = false;
		}

		String afterLabelNodeChange = ratingComponent.getAfterLabelNodeChange();

		if (afterLabelNodeChange != null) {

			encodeAfterLabelNodeChange(responseWriter, ratingComponent, afterLabelNodeChange, first);
			first = false;
		}

		String afterLocaleChange = ratingComponent.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, ratingComponent, afterLocaleChange, first);
			first = false;
		}

		String afterRenderChange = ratingComponent.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, ratingComponent, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = ratingComponent.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, ratingComponent, afterRenderedChange, first);
			first = false;
		}

		String afterSelectedIndexChange = ratingComponent.getAfterSelectedIndexChange();

		if (afterSelectedIndexChange != null) {

			encodeAfterSelectedIndexChange(responseWriter, ratingComponent, afterSelectedIndexChange, first);
			first = false;
		}

		String afterShowTitleChange = ratingComponent.getAfterShowTitleChange();

		if (afterShowTitleChange != null) {

			encodeAfterShowTitleChange(responseWriter, ratingComponent, afterShowTitleChange, first);
			first = false;
		}

		String afterSizeChange = ratingComponent.getAfterSizeChange();

		if (afterSizeChange != null) {

			encodeAfterSizeChange(responseWriter, ratingComponent, afterSizeChange, first);
			first = false;
		}

		String afterSrcNodeChange = ratingComponent.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, ratingComponent, afterSrcNodeChange, first);
			first = false;
		}

		String afterStringsChange = ratingComponent.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, ratingComponent, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = ratingComponent.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, ratingComponent, afterTabIndexChange, first);
			first = false;
		}

		String afterTitleChange = ratingComponent.getAfterTitleChange();

		if (afterTitleChange != null) {

			encodeAfterTitleChange(responseWriter, ratingComponent, afterTitleChange, first);
			first = false;
		}

		String afterUseARIAChange = ratingComponent.getAfterUseARIAChange();

		if (afterUseARIAChange != null) {

			encodeAfterUseARIAChange(responseWriter, ratingComponent, afterUseARIAChange, first);
			first = false;
		}

		String afterValueChange = ratingComponent.getAfterValueChange();

		if (afterValueChange != null) {

			encodeAfterValueChange(responseWriter, ratingComponent, afterValueChange, first);
			first = false;
		}

		String afterVisibleChange = ratingComponent.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, ratingComponent, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = ratingComponent.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, ratingComponent, afterWidthChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onBoundingBoxChange = ratingComponent.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, ratingComponent, onBoundingBoxChange, first);
			first = false;
		}

		String onCanResetChange = ratingComponent.getOnCanResetChange();

		if (onCanResetChange != null) {

			encodeOnCanResetChange(responseWriter, ratingComponent, onCanResetChange, first);
			first = false;
		}

		String onContentBoxChange = ratingComponent.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, ratingComponent, onContentBoxChange, first);
			first = false;
		}

		String onCssClassChange = ratingComponent.getOnCssClassChange();

		if (onCssClassChange != null) {

			encodeOnCssClassChange(responseWriter, ratingComponent, onCssClassChange, first);
			first = false;
		}

		String onCssClassesChange = ratingComponent.getOnCssClassesChange();

		if (onCssClassesChange != null) {

			encodeOnCssClassesChange(responseWriter, ratingComponent, onCssClassesChange, first);
			first = false;
		}

		String onDefaultSelectedChange = ratingComponent.getOnDefaultSelectedChange();

		if (onDefaultSelectedChange != null) {

			encodeOnDefaultSelectedChange(responseWriter, ratingComponent, onDefaultSelectedChange, first);
			first = false;
		}

		String onDestroyedChange = ratingComponent.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, ratingComponent, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = ratingComponent.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, ratingComponent, onDisabledChange, first);
			first = false;
		}

		String onElementsChange = ratingComponent.getOnElementsChange();

		if (onElementsChange != null) {

			encodeOnElementsChange(responseWriter, ratingComponent, onElementsChange, first);
			first = false;
		}

		String onFocusedChange = ratingComponent.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, ratingComponent, onFocusedChange, first);
			first = false;
		}

		String onHeightChange = ratingComponent.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, ratingComponent, onHeightChange, first);
			first = false;
		}

		String onHiddenInputChange = ratingComponent.getOnHiddenInputChange();

		if (onHiddenInputChange != null) {

			encodeOnHiddenInputChange(responseWriter, ratingComponent, onHiddenInputChange, first);
			first = false;
		}

		String onHideClassChange = ratingComponent.getOnHideClassChange();

		if (onHideClassChange != null) {

			encodeOnHideClassChange(responseWriter, ratingComponent, onHideClassChange, first);
			first = false;
		}

		String onIdChange = ratingComponent.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, ratingComponent, onIdChange, first);
			first = false;
		}

		String onInitializedChange = ratingComponent.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, ratingComponent, onInitializedChange, first);
			first = false;
		}

		String onInputNameChange = ratingComponent.getOnInputNameChange();

		if (onInputNameChange != null) {

			encodeOnInputNameChange(responseWriter, ratingComponent, onInputNameChange, first);
			first = false;
		}

		String onLabelChange = ratingComponent.getOnLabelChange();

		if (onLabelChange != null) {

			encodeOnLabelChange(responseWriter, ratingComponent, onLabelChange, first);
			first = false;
		}

		String onLabelNodeChange = ratingComponent.getOnLabelNodeChange();

		if (onLabelNodeChange != null) {

			encodeOnLabelNodeChange(responseWriter, ratingComponent, onLabelNodeChange, first);
			first = false;
		}

		String onLocaleChange = ratingComponent.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, ratingComponent, onLocaleChange, first);
			first = false;
		}

		String onRenderChange = ratingComponent.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, ratingComponent, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = ratingComponent.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, ratingComponent, onRenderedChange, first);
			first = false;
		}

		String onSelectedIndexChange = ratingComponent.getOnSelectedIndexChange();

		if (onSelectedIndexChange != null) {

			encodeOnSelectedIndexChange(responseWriter, ratingComponent, onSelectedIndexChange, first);
			first = false;
		}

		String onShowTitleChange = ratingComponent.getOnShowTitleChange();

		if (onShowTitleChange != null) {

			encodeOnShowTitleChange(responseWriter, ratingComponent, onShowTitleChange, first);
			first = false;
		}

		String onSizeChange = ratingComponent.getOnSizeChange();

		if (onSizeChange != null) {

			encodeOnSizeChange(responseWriter, ratingComponent, onSizeChange, first);
			first = false;
		}

		String onSrcNodeChange = ratingComponent.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, ratingComponent, onSrcNodeChange, first);
			first = false;
		}

		String onStringsChange = ratingComponent.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, ratingComponent, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = ratingComponent.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, ratingComponent, onTabIndexChange, first);
			first = false;
		}

		String onTitleChange = ratingComponent.getOnTitleChange();

		if (onTitleChange != null) {

			encodeOnTitleChange(responseWriter, ratingComponent, onTitleChange, first);
			first = false;
		}

		String onUseARIAChange = ratingComponent.getOnUseARIAChange();

		if (onUseARIAChange != null) {

			encodeOnUseARIAChange(responseWriter, ratingComponent, onUseARIAChange, first);
			first = false;
		}

		String onValueChange = ratingComponent.getOnValueChange();

		if (onValueChange != null) {

			encodeOnValueChange(responseWriter, ratingComponent, onValueChange, first);
			first = false;
		}

		String onVisibleChange = ratingComponent.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, ratingComponent, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = ratingComponent.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, ratingComponent, onWidthChange, first);
			first = false;
		}

		// End encoding "on" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterCanResetChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterCanResetChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CAN_RESET_CHANGE, afterCanResetChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterCssClassChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterCssClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASS_CHANGE, afterCssClassChange, first);
	}

	protected void encodeAfterCssClassesChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterCssClassesChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASSES_CHANGE, afterCssClassesChange, first);
	}

	protected void encodeAfterDefaultSelectedChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterDefaultSelectedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_SELECTED_CHANGE, afterDefaultSelectedChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterElementsChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterElementsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ELEMENTS_CHANGE, afterElementsChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterHiddenInputChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterHiddenInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDDEN_INPUT_CHANGE, afterHiddenInputChange, first);
	}

	protected void encodeAfterHideClassChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterHideClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDE_CLASS_CHANGE, afterHideClassChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterInputNameChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterInputNameChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_NAME_CHANGE, afterInputNameChange, first);
	}

	protected void encodeAfterLabelChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterLabelChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_CHANGE, afterLabelChange, first);
	}

	protected void encodeAfterLabelNodeChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterLabelNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_NODE_CHANGE, afterLabelNodeChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterSelectedIndexChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterSelectedIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTED_INDEX_CHANGE, afterSelectedIndexChange, first);
	}

	protected void encodeAfterShowTitleChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterShowTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_TITLE_CHANGE, afterShowTitleChange, first);
	}

	protected void encodeAfterSizeChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SIZE_CHANGE, afterSizeChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterTitleChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TITLE_CHANGE, afterTitleChange, first);
	}

	protected void encodeAfterUseARIAChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterUseARIAChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_ARIACHANGE, afterUseARIAChange, first);
	}

	protected void encodeAfterValueChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, afterValueChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, RatingComponent ratingComponent, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeCanReset(ResponseWriter responseWriter, RatingComponent ratingComponent, Boolean canReset, boolean first) throws IOException {
		encodeBoolean(responseWriter, RatingComponent.CAN_RESET, canReset, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, RatingComponent ratingComponent, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.CONTENT_BOX, contentBox, first);
	}

	protected void encodeCssClass(ResponseWriter responseWriter, RatingComponent ratingComponent, String cssClass, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.CSS_CLASS, cssClass, first);
	}

	protected void encodeCssClasses(ResponseWriter responseWriter, RatingComponent ratingComponent, Object cssClasses, boolean first) throws IOException {
		encodeObject(responseWriter, RatingComponent.CSS_CLASSES, cssClasses, first);
	}

	protected void encodeDefaultSelected(ResponseWriter responseWriter, RatingComponent ratingComponent, Object defaultSelected, boolean first) throws IOException {
		encodeNumber(responseWriter, RatingComponent.DEFAULT_SELECTED, defaultSelected, first);
	}

	protected void encodeDestroyed(ResponseWriter responseWriter, RatingComponent ratingComponent, Boolean destroyed, boolean first) throws IOException {
		encodeBoolean(responseWriter, RatingComponent.DESTROYED, destroyed, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, RatingComponent ratingComponent, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, RatingComponent.DISABLED, disabled, first);
	}

	protected void encodeElements(ResponseWriter responseWriter, RatingComponent ratingComponent, String elements, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.ELEMENTS, elements, first);
	}

	protected void encodeFocused(ResponseWriter responseWriter, RatingComponent ratingComponent, Boolean focused, boolean first) throws IOException {
		encodeBoolean(responseWriter, RatingComponent.FOCUSED, focused, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, RatingComponent ratingComponent, Object height, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, RatingComponent.HEIGHT, height, first);
	}

	protected void encodeHiddenInput(ResponseWriter responseWriter, RatingComponent ratingComponent, String hiddenInput, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.HIDDEN_INPUT, hiddenInput, first);
	}

	protected void encodeHideClass(ResponseWriter responseWriter, RatingComponent ratingComponent, String hideClass, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.HIDE_CLASS, hideClass, first);
	}

	protected void encodeId(ResponseWriter responseWriter, RatingComponent ratingComponent, String id, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.ID, id, first);
	}

	protected void encodeInitialized(ResponseWriter responseWriter, RatingComponent ratingComponent, Boolean initialized, boolean first) throws IOException {
		encodeBoolean(responseWriter, RatingComponent.INITIALIZED, initialized, first);
	}

	protected void encodeInputName(ResponseWriter responseWriter, RatingComponent ratingComponent, String inputName, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.INPUT_NAME, inputName, first);
	}

	protected void encodeLabel(ResponseWriter responseWriter, RatingComponent ratingComponent, String label, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.LABEL, label, first);
	}

	protected void encodeLabelNode(ResponseWriter responseWriter, RatingComponent ratingComponent, String labelNode, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.LABEL_NODE, labelNode, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, RatingComponent ratingComponent, String locale, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.LOCALE, locale, first);
	}

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnCanResetChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onCanResetChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CAN_RESET_CHANGE, onCanResetChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnCssClassChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onCssClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASS_CHANGE, onCssClassChange, first);
	}

	protected void encodeOnCssClassesChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onCssClassesChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASSES_CHANGE, onCssClassesChange, first);
	}

	protected void encodeOnDefaultSelectedChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onDefaultSelectedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_SELECTED_CHANGE, onDefaultSelectedChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnElementsChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onElementsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ELEMENTS_CHANGE, onElementsChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnHiddenInputChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onHiddenInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDDEN_INPUT_CHANGE, onHiddenInputChange, first);
	}

	protected void encodeOnHideClassChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onHideClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDE_CLASS_CHANGE, onHideClassChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnInputNameChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onInputNameChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_NAME_CHANGE, onInputNameChange, first);
	}

	protected void encodeOnLabelChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onLabelChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_CHANGE, onLabelChange, first);
	}

	protected void encodeOnLabelNodeChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onLabelNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_NODE_CHANGE, onLabelNodeChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnSelectedIndexChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onSelectedIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTED_INDEX_CHANGE, onSelectedIndexChange, first);
	}

	protected void encodeOnShowTitleChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onShowTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_TITLE_CHANGE, onShowTitleChange, first);
	}

	protected void encodeOnSizeChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SIZE_CHANGE, onSizeChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnTitleChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TITLE_CHANGE, onTitleChange, first);
	}

	protected void encodeOnUseARIAChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onUseARIAChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_ARIACHANGE, onUseARIAChange, first);
	}

	protected void encodeOnValueChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, onValueChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, RatingComponent ratingComponent, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
	}

	protected void encodeRendered(ResponseWriter responseWriter, RatingComponent ratingComponent, Boolean rendered, boolean first) throws IOException {
		encodeBoolean(responseWriter, RatingComponent.RENDERED, rendered, first);
	}

	protected void encodeSelectedIndex(ResponseWriter responseWriter, RatingComponent ratingComponent, Object selectedIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, RatingComponent.SELECTED_INDEX, selectedIndex, first);
	}

	protected void encodeShowTitle(ResponseWriter responseWriter, RatingComponent ratingComponent, Boolean showTitle, boolean first) throws IOException {
		encodeBoolean(responseWriter, RatingComponent.SHOW_TITLE, showTitle, first);
	}

	protected void encodeSize(ResponseWriter responseWriter, RatingComponent ratingComponent, Object size, boolean first) throws IOException {
		encodeNumber(responseWriter, RatingComponent.SIZE, size, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, RatingComponent ratingComponent, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.SRC_NODE, srcNode, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, RatingComponent ratingComponent, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, RatingComponent.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, RatingComponent ratingComponent, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, RatingComponent.TAB_INDEX, tabIndex, first);
	}

	protected void encodeTitle(ResponseWriter responseWriter, RatingComponent ratingComponent, String title, boolean first) throws IOException {
		encodeString(responseWriter, RatingComponent.TITLE, title, first);
	}

	protected void encodeUseARIA(ResponseWriter responseWriter, RatingComponent ratingComponent, Boolean useARIA, boolean first) throws IOException {
		encodeBoolean(responseWriter, RatingComponent.USE_ARIA, useARIA, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, RatingComponent ratingComponent, Object value, boolean first) throws IOException {
		encodeObject(responseWriter, RatingComponent.VALUE, value, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, RatingComponent ratingComponent, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, RatingComponent.VISIBLE, visible, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, RatingComponent ratingComponent, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, RatingComponent.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, RatingComponent ratingComponent, Object width, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, RatingComponent.WIDTH, width, first);
	}
}
//J+
