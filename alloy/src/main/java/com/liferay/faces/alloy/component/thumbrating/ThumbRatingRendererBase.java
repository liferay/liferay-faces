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
package com.liferay.faces.alloy.component.thumbrating;
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
public abstract class ThumbRatingRendererBase extends AlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "ThumbRating";
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

		ThumbRatingComponent thumbRatingComponent = (ThumbRatingComponent) uiComponent;
		boolean first = true;

		String boundingBox = thumbRatingComponent.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, thumbRatingComponent, boundingBox, first);
			first = false;
		}

		Boolean canReset = thumbRatingComponent.isCanReset();

		if (canReset != null) {

			encodeCanReset(responseWriter, thumbRatingComponent, canReset, first);
			first = false;
		}

		String contentBox = thumbRatingComponent.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, thumbRatingComponent, contentBox, first);
			first = false;
		}

		String cssClass = thumbRatingComponent.getCssClass();

		if (cssClass != null) {

			encodeCssClass(responseWriter, thumbRatingComponent, cssClass, first);
			first = false;
		}

		Object cssClasses = thumbRatingComponent.getCssClasses();

		if (cssClasses != null) {

			encodeCssClasses(responseWriter, thumbRatingComponent, cssClasses, first);
			first = false;
		}

		Object defaultSelected = thumbRatingComponent.getDefaultSelected();

		if (defaultSelected != null) {

			encodeDefaultSelected(responseWriter, thumbRatingComponent, defaultSelected, first);
			first = false;
		}

		Boolean destroyed = thumbRatingComponent.isDestroyed();

		if (destroyed != null) {

			encodeDestroyed(responseWriter, thumbRatingComponent, destroyed, first);
			first = false;
		}

		Boolean disabled = thumbRatingComponent.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, thumbRatingComponent, disabled, first);
			first = false;
		}

		String elements = thumbRatingComponent.getElements();

		if (elements != null) {

			encodeElements(responseWriter, thumbRatingComponent, elements, first);
			first = false;
		}

		Boolean focused = thumbRatingComponent.isFocused();

		if (focused != null) {

			encodeFocused(responseWriter, thumbRatingComponent, focused, first);
			first = false;
		}

		String height = thumbRatingComponent.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, thumbRatingComponent, height, first);
			first = false;
		}

		String hiddenInput = thumbRatingComponent.getHiddenInput();

		if (hiddenInput != null) {

			encodeHiddenInput(responseWriter, thumbRatingComponent, hiddenInput, first);
			first = false;
		}

		String hideClass = thumbRatingComponent.getHideClass();

		if (hideClass != null) {

			encodeHideClass(responseWriter, thumbRatingComponent, hideClass, first);
			first = false;
		}

		String id = thumbRatingComponent.getId();

		if (id != null) {

			encodeId(responseWriter, thumbRatingComponent, id, first);
			first = false;
		}

		Boolean initialized = thumbRatingComponent.isInitialized();

		if (initialized != null) {

			encodeInitialized(responseWriter, thumbRatingComponent, initialized, first);
			first = false;
		}

		String inputName = thumbRatingComponent.getInputName();

		if (inputName != null) {

			encodeInputName(responseWriter, thumbRatingComponent, inputName, first);
			first = false;
		}

		String label = thumbRatingComponent.getLabel();

		if (label != null) {

			encodeLabel(responseWriter, thumbRatingComponent, label, first);
			first = false;
		}

		String labelNode = thumbRatingComponent.getLabelNode();

		if (labelNode != null) {

			encodeLabelNode(responseWriter, thumbRatingComponent, labelNode, first);
			first = false;
		}

		String locale = thumbRatingComponent.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, thumbRatingComponent, locale, first);
			first = false;
		}

		Boolean rendered = thumbRatingComponent.isRendered();

		if (rendered != null) {

			encodeRendered(responseWriter, thumbRatingComponent, rendered, first);
			first = false;
		}

		Object selectedIndex = thumbRatingComponent.getSelectedIndex();

		if (selectedIndex != null) {

			encodeSelectedIndex(responseWriter, thumbRatingComponent, selectedIndex, first);
			first = false;
		}

		Boolean showTitle = thumbRatingComponent.isShowTitle();

		if (showTitle != null) {

			encodeShowTitle(responseWriter, thumbRatingComponent, showTitle, first);
			first = false;
		}

		Object size = thumbRatingComponent.getSize();

		if (size != null) {

			encodeSize(responseWriter, thumbRatingComponent, size, first);
			first = false;
		}

		String srcNode = thumbRatingComponent.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, thumbRatingComponent, srcNode, first);
			first = false;
		}

		Object strings = thumbRatingComponent.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, thumbRatingComponent, strings, first);
			first = false;
		}

		Object tabIndex = thumbRatingComponent.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, thumbRatingComponent, tabIndex, first);
			first = false;
		}

		String title = thumbRatingComponent.getTitle();

		if (title != null) {

			encodeTitle(responseWriter, thumbRatingComponent, title, first);
			first = false;
		}

		Boolean useARIA = thumbRatingComponent.isUseARIA();

		if (useARIA != null) {

			encodeUseARIA(responseWriter, thumbRatingComponent, useARIA, first);
			first = false;
		}

		Object value = thumbRatingComponent.getValue();

		if (value != null) {

			encodeValue(responseWriter, thumbRatingComponent, value, first);
			first = false;
		}

		Boolean visible = thumbRatingComponent.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, thumbRatingComponent, visible, first);
			first = false;
		}

		Boolean widgetRender = thumbRatingComponent.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, thumbRatingComponent, widgetRender, first);
			first = false;
		}

		String width = thumbRatingComponent.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, thumbRatingComponent, width, first);
			first = false;
		}

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterBoundingBoxChange = thumbRatingComponent.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, thumbRatingComponent, afterBoundingBoxChange, first);
			first = false;
		}

		String afterCanResetChange = thumbRatingComponent.getAfterCanResetChange();

		if (afterCanResetChange != null) {

			encodeAfterCanResetChange(responseWriter, thumbRatingComponent, afterCanResetChange, first);
			first = false;
		}

		String afterContentBoxChange = thumbRatingComponent.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, thumbRatingComponent, afterContentBoxChange, first);
			first = false;
		}

		String afterCssClassChange = thumbRatingComponent.getAfterCssClassChange();

		if (afterCssClassChange != null) {

			encodeAfterCssClassChange(responseWriter, thumbRatingComponent, afterCssClassChange, first);
			first = false;
		}

		String afterCssClassesChange = thumbRatingComponent.getAfterCssClassesChange();

		if (afterCssClassesChange != null) {

			encodeAfterCssClassesChange(responseWriter, thumbRatingComponent, afterCssClassesChange, first);
			first = false;
		}

		String afterDefaultSelectedChange = thumbRatingComponent.getAfterDefaultSelectedChange();

		if (afterDefaultSelectedChange != null) {

			encodeAfterDefaultSelectedChange(responseWriter, thumbRatingComponent, afterDefaultSelectedChange, first);
			first = false;
		}

		String afterDestroyedChange = thumbRatingComponent.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, thumbRatingComponent, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = thumbRatingComponent.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, thumbRatingComponent, afterDisabledChange, first);
			first = false;
		}

		String afterElementsChange = thumbRatingComponent.getAfterElementsChange();

		if (afterElementsChange != null) {

			encodeAfterElementsChange(responseWriter, thumbRatingComponent, afterElementsChange, first);
			first = false;
		}

		String afterFocusedChange = thumbRatingComponent.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, thumbRatingComponent, afterFocusedChange, first);
			first = false;
		}

		String afterHeightChange = thumbRatingComponent.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, thumbRatingComponent, afterHeightChange, first);
			first = false;
		}

		String afterHiddenInputChange = thumbRatingComponent.getAfterHiddenInputChange();

		if (afterHiddenInputChange != null) {

			encodeAfterHiddenInputChange(responseWriter, thumbRatingComponent, afterHiddenInputChange, first);
			first = false;
		}

		String afterHideClassChange = thumbRatingComponent.getAfterHideClassChange();

		if (afterHideClassChange != null) {

			encodeAfterHideClassChange(responseWriter, thumbRatingComponent, afterHideClassChange, first);
			first = false;
		}

		String afterIdChange = thumbRatingComponent.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, thumbRatingComponent, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = thumbRatingComponent.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, thumbRatingComponent, afterInitializedChange, first);
			first = false;
		}

		String afterInputNameChange = thumbRatingComponent.getAfterInputNameChange();

		if (afterInputNameChange != null) {

			encodeAfterInputNameChange(responseWriter, thumbRatingComponent, afterInputNameChange, first);
			first = false;
		}

		String afterLabelChange = thumbRatingComponent.getAfterLabelChange();

		if (afterLabelChange != null) {

			encodeAfterLabelChange(responseWriter, thumbRatingComponent, afterLabelChange, first);
			first = false;
		}

		String afterLabelNodeChange = thumbRatingComponent.getAfterLabelNodeChange();

		if (afterLabelNodeChange != null) {

			encodeAfterLabelNodeChange(responseWriter, thumbRatingComponent, afterLabelNodeChange, first);
			first = false;
		}

		String afterLocaleChange = thumbRatingComponent.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, thumbRatingComponent, afterLocaleChange, first);
			first = false;
		}

		String afterRenderChange = thumbRatingComponent.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, thumbRatingComponent, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = thumbRatingComponent.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, thumbRatingComponent, afterRenderedChange, first);
			first = false;
		}

		String afterSelectedIndexChange = thumbRatingComponent.getAfterSelectedIndexChange();

		if (afterSelectedIndexChange != null) {

			encodeAfterSelectedIndexChange(responseWriter, thumbRatingComponent, afterSelectedIndexChange, first);
			first = false;
		}

		String afterShowTitleChange = thumbRatingComponent.getAfterShowTitleChange();

		if (afterShowTitleChange != null) {

			encodeAfterShowTitleChange(responseWriter, thumbRatingComponent, afterShowTitleChange, first);
			first = false;
		}

		String afterSizeChange = thumbRatingComponent.getAfterSizeChange();

		if (afterSizeChange != null) {

			encodeAfterSizeChange(responseWriter, thumbRatingComponent, afterSizeChange, first);
			first = false;
		}

		String afterSrcNodeChange = thumbRatingComponent.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, thumbRatingComponent, afterSrcNodeChange, first);
			first = false;
		}

		String afterStringsChange = thumbRatingComponent.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, thumbRatingComponent, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = thumbRatingComponent.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, thumbRatingComponent, afterTabIndexChange, first);
			first = false;
		}

		String afterTitleChange = thumbRatingComponent.getAfterTitleChange();

		if (afterTitleChange != null) {

			encodeAfterTitleChange(responseWriter, thumbRatingComponent, afterTitleChange, first);
			first = false;
		}

		String afterUseARIAChange = thumbRatingComponent.getAfterUseARIAChange();

		if (afterUseARIAChange != null) {

			encodeAfterUseARIAChange(responseWriter, thumbRatingComponent, afterUseARIAChange, first);
			first = false;
		}

		String afterValueChange = thumbRatingComponent.getAfterValueChange();

		if (afterValueChange != null) {

			encodeAfterValueChange(responseWriter, thumbRatingComponent, afterValueChange, first);
			first = false;
		}

		String afterVisibleChange = thumbRatingComponent.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, thumbRatingComponent, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = thumbRatingComponent.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, thumbRatingComponent, afterWidthChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onBoundingBoxChange = thumbRatingComponent.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, thumbRatingComponent, onBoundingBoxChange, first);
			first = false;
		}

		String onCanResetChange = thumbRatingComponent.getOnCanResetChange();

		if (onCanResetChange != null) {

			encodeOnCanResetChange(responseWriter, thumbRatingComponent, onCanResetChange, first);
			first = false;
		}

		String onContentBoxChange = thumbRatingComponent.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, thumbRatingComponent, onContentBoxChange, first);
			first = false;
		}

		String onCssClassChange = thumbRatingComponent.getOnCssClassChange();

		if (onCssClassChange != null) {

			encodeOnCssClassChange(responseWriter, thumbRatingComponent, onCssClassChange, first);
			first = false;
		}

		String onCssClassesChange = thumbRatingComponent.getOnCssClassesChange();

		if (onCssClassesChange != null) {

			encodeOnCssClassesChange(responseWriter, thumbRatingComponent, onCssClassesChange, first);
			first = false;
		}

		String onDefaultSelectedChange = thumbRatingComponent.getOnDefaultSelectedChange();

		if (onDefaultSelectedChange != null) {

			encodeOnDefaultSelectedChange(responseWriter, thumbRatingComponent, onDefaultSelectedChange, first);
			first = false;
		}

		String onDestroyedChange = thumbRatingComponent.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, thumbRatingComponent, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = thumbRatingComponent.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, thumbRatingComponent, onDisabledChange, first);
			first = false;
		}

		String onElementsChange = thumbRatingComponent.getOnElementsChange();

		if (onElementsChange != null) {

			encodeOnElementsChange(responseWriter, thumbRatingComponent, onElementsChange, first);
			first = false;
		}

		String onFocusedChange = thumbRatingComponent.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, thumbRatingComponent, onFocusedChange, first);
			first = false;
		}

		String onHeightChange = thumbRatingComponent.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, thumbRatingComponent, onHeightChange, first);
			first = false;
		}

		String onHiddenInputChange = thumbRatingComponent.getOnHiddenInputChange();

		if (onHiddenInputChange != null) {

			encodeOnHiddenInputChange(responseWriter, thumbRatingComponent, onHiddenInputChange, first);
			first = false;
		}

		String onHideClassChange = thumbRatingComponent.getOnHideClassChange();

		if (onHideClassChange != null) {

			encodeOnHideClassChange(responseWriter, thumbRatingComponent, onHideClassChange, first);
			first = false;
		}

		String onIdChange = thumbRatingComponent.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, thumbRatingComponent, onIdChange, first);
			first = false;
		}

		String onInitializedChange = thumbRatingComponent.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, thumbRatingComponent, onInitializedChange, first);
			first = false;
		}

		String onInputNameChange = thumbRatingComponent.getOnInputNameChange();

		if (onInputNameChange != null) {

			encodeOnInputNameChange(responseWriter, thumbRatingComponent, onInputNameChange, first);
			first = false;
		}

		String onLabelChange = thumbRatingComponent.getOnLabelChange();

		if (onLabelChange != null) {

			encodeOnLabelChange(responseWriter, thumbRatingComponent, onLabelChange, first);
			first = false;
		}

		String onLabelNodeChange = thumbRatingComponent.getOnLabelNodeChange();

		if (onLabelNodeChange != null) {

			encodeOnLabelNodeChange(responseWriter, thumbRatingComponent, onLabelNodeChange, first);
			first = false;
		}

		String onLocaleChange = thumbRatingComponent.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, thumbRatingComponent, onLocaleChange, first);
			first = false;
		}

		String onRenderChange = thumbRatingComponent.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, thumbRatingComponent, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = thumbRatingComponent.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, thumbRatingComponent, onRenderedChange, first);
			first = false;
		}

		String onSelectedIndexChange = thumbRatingComponent.getOnSelectedIndexChange();

		if (onSelectedIndexChange != null) {

			encodeOnSelectedIndexChange(responseWriter, thumbRatingComponent, onSelectedIndexChange, first);
			first = false;
		}

		String onShowTitleChange = thumbRatingComponent.getOnShowTitleChange();

		if (onShowTitleChange != null) {

			encodeOnShowTitleChange(responseWriter, thumbRatingComponent, onShowTitleChange, first);
			first = false;
		}

		String onSizeChange = thumbRatingComponent.getOnSizeChange();

		if (onSizeChange != null) {

			encodeOnSizeChange(responseWriter, thumbRatingComponent, onSizeChange, first);
			first = false;
		}

		String onSrcNodeChange = thumbRatingComponent.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, thumbRatingComponent, onSrcNodeChange, first);
			first = false;
		}

		String onStringsChange = thumbRatingComponent.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, thumbRatingComponent, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = thumbRatingComponent.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, thumbRatingComponent, onTabIndexChange, first);
			first = false;
		}

		String onTitleChange = thumbRatingComponent.getOnTitleChange();

		if (onTitleChange != null) {

			encodeOnTitleChange(responseWriter, thumbRatingComponent, onTitleChange, first);
			first = false;
		}

		String onUseARIAChange = thumbRatingComponent.getOnUseARIAChange();

		if (onUseARIAChange != null) {

			encodeOnUseARIAChange(responseWriter, thumbRatingComponent, onUseARIAChange, first);
			first = false;
		}

		String onValueChange = thumbRatingComponent.getOnValueChange();

		if (onValueChange != null) {

			encodeOnValueChange(responseWriter, thumbRatingComponent, onValueChange, first);
			first = false;
		}

		String onVisibleChange = thumbRatingComponent.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, thumbRatingComponent, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = thumbRatingComponent.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, thumbRatingComponent, onWidthChange, first);
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

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterCanResetChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterCanResetChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CAN_RESET_CHANGE, afterCanResetChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterCssClassChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterCssClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASS_CHANGE, afterCssClassChange, first);
	}

	protected void encodeAfterCssClassesChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterCssClassesChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASSES_CHANGE, afterCssClassesChange, first);
	}

	protected void encodeAfterDefaultSelectedChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterDefaultSelectedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_SELECTED_CHANGE, afterDefaultSelectedChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterElementsChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterElementsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ELEMENTS_CHANGE, afterElementsChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterHiddenInputChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterHiddenInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDDEN_INPUT_CHANGE, afterHiddenInputChange, first);
	}

	protected void encodeAfterHideClassChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterHideClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDE_CLASS_CHANGE, afterHideClassChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterInputNameChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterInputNameChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_NAME_CHANGE, afterInputNameChange, first);
	}

	protected void encodeAfterLabelChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterLabelChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_CHANGE, afterLabelChange, first);
	}

	protected void encodeAfterLabelNodeChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterLabelNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_NODE_CHANGE, afterLabelNodeChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterSelectedIndexChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterSelectedIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTED_INDEX_CHANGE, afterSelectedIndexChange, first);
	}

	protected void encodeAfterShowTitleChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterShowTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_TITLE_CHANGE, afterShowTitleChange, first);
	}

	protected void encodeAfterSizeChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SIZE_CHANGE, afterSizeChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterTitleChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TITLE_CHANGE, afterTitleChange, first);
	}

	protected void encodeAfterUseARIAChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterUseARIAChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_ARIACHANGE, afterUseARIAChange, first);
	}

	protected void encodeAfterValueChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, afterValueChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeCanReset(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Boolean canReset, boolean first) throws IOException {
		encodeBoolean(responseWriter, ThumbRatingComponent.CAN_RESET, canReset, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.CONTENT_BOX, contentBox, first);
	}

	protected void encodeCssClass(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String cssClass, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.CSS_CLASS, cssClass, first);
	}

	protected void encodeCssClasses(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Object cssClasses, boolean first) throws IOException {
		encodeObject(responseWriter, ThumbRatingComponent.CSS_CLASSES, cssClasses, first);
	}

	protected void encodeDefaultSelected(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Object defaultSelected, boolean first) throws IOException {
		encodeNumber(responseWriter, ThumbRatingComponent.DEFAULT_SELECTED, defaultSelected, first);
	}

	protected void encodeDestroyed(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Boolean destroyed, boolean first) throws IOException {
		encodeBoolean(responseWriter, ThumbRatingComponent.DESTROYED, destroyed, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, ThumbRatingComponent.DISABLED, disabled, first);
	}

	protected void encodeElements(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String elements, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.ELEMENTS, elements, first);
	}

	protected void encodeFocused(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Boolean focused, boolean first) throws IOException {
		encodeBoolean(responseWriter, ThumbRatingComponent.FOCUSED, focused, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String height, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.HEIGHT, height, first);
	}

	protected void encodeHiddenInput(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String hiddenInput, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.HIDDEN_INPUT, hiddenInput, first);
	}

	protected void encodeHideClass(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String hideClass, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.HIDE_CLASS, hideClass, first);
	}

	protected void encodeId(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String id, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.ID, id, first);
	}

	protected void encodeInitialized(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Boolean initialized, boolean first) throws IOException {
		encodeBoolean(responseWriter, ThumbRatingComponent.INITIALIZED, initialized, first);
	}

	protected void encodeInputName(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String inputName, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.INPUT_NAME, inputName, first);
	}

	protected void encodeLabel(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String label, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.LABEL, label, first);
	}

	protected void encodeLabelNode(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String labelNode, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.LABEL_NODE, labelNode, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String locale, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.LOCALE, locale, first);
	}

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnCanResetChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onCanResetChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CAN_RESET_CHANGE, onCanResetChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnCssClassChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onCssClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASS_CHANGE, onCssClassChange, first);
	}

	protected void encodeOnCssClassesChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onCssClassesChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASSES_CHANGE, onCssClassesChange, first);
	}

	protected void encodeOnDefaultSelectedChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onDefaultSelectedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_SELECTED_CHANGE, onDefaultSelectedChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnElementsChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onElementsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ELEMENTS_CHANGE, onElementsChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnHiddenInputChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onHiddenInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDDEN_INPUT_CHANGE, onHiddenInputChange, first);
	}

	protected void encodeOnHideClassChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onHideClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDE_CLASS_CHANGE, onHideClassChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnInputNameChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onInputNameChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_NAME_CHANGE, onInputNameChange, first);
	}

	protected void encodeOnLabelChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onLabelChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_CHANGE, onLabelChange, first);
	}

	protected void encodeOnLabelNodeChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onLabelNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_NODE_CHANGE, onLabelNodeChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnSelectedIndexChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onSelectedIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTED_INDEX_CHANGE, onSelectedIndexChange, first);
	}

	protected void encodeOnShowTitleChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onShowTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_TITLE_CHANGE, onShowTitleChange, first);
	}

	protected void encodeOnSizeChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SIZE_CHANGE, onSizeChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnTitleChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TITLE_CHANGE, onTitleChange, first);
	}

	protected void encodeOnUseARIAChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onUseARIAChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_ARIACHANGE, onUseARIAChange, first);
	}

	protected void encodeOnValueChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, onValueChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
	}

	protected void encodeRendered(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Boolean rendered, boolean first) throws IOException {
		encodeBoolean(responseWriter, ThumbRatingComponent.RENDERED, rendered, first);
	}

	protected void encodeSelectedIndex(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Object selectedIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, ThumbRatingComponent.SELECTED_INDEX, selectedIndex, first);
	}

	protected void encodeShowTitle(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Boolean showTitle, boolean first) throws IOException {
		encodeBoolean(responseWriter, ThumbRatingComponent.SHOW_TITLE, showTitle, first);
	}

	protected void encodeSize(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Object size, boolean first) throws IOException {
		encodeNumber(responseWriter, ThumbRatingComponent.SIZE, size, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.SRC_NODE, srcNode, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, ThumbRatingComponent.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, ThumbRatingComponent.TAB_INDEX, tabIndex, first);
	}

	protected void encodeTitle(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String title, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.TITLE, title, first);
	}

	protected void encodeUseARIA(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Boolean useARIA, boolean first) throws IOException {
		encodeBoolean(responseWriter, ThumbRatingComponent.USE_ARIA, useARIA, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Object value, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.VALUE, value, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, ThumbRatingComponent.VISIBLE, visible, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, ThumbRatingComponent.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, ThumbRatingComponent thumbRatingComponent, String width, boolean first) throws IOException {
		encodeString(responseWriter, ThumbRatingComponent.WIDTH, width, first);
	}
}
//J+
