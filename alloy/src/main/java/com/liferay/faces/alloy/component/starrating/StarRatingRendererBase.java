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
public abstract class StarRatingRendererBase extends AlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "StarRating";
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

		StarRatingAlloy starRatingAlloy = (StarRatingAlloy) uiComponent;
		boolean first = true;

		String boundingBox = starRatingAlloy.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, starRatingAlloy, boundingBox, first);
			first = false;
		}

		Boolean canReset = starRatingAlloy.isCanReset();

		if (canReset != null) {

			encodeCanReset(responseWriter, starRatingAlloy, canReset, first);
			first = false;
		}

		String contentBox = starRatingAlloy.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, starRatingAlloy, contentBox, first);
			first = false;
		}

		String cssClass = starRatingAlloy.getCssClass();

		if (cssClass != null) {

			encodeCssClass(responseWriter, starRatingAlloy, cssClass, first);
			first = false;
		}

		Object cssClasses = starRatingAlloy.getCssClasses();

		if (cssClasses != null) {

			encodeCssClasses(responseWriter, starRatingAlloy, cssClasses, first);
			first = false;
		}

		Object defaultSelected = starRatingAlloy.getDefaultSelected();

		if (defaultSelected != null) {

			encodeDefaultSelected(responseWriter, starRatingAlloy, defaultSelected, first);
			first = false;
		}

		Boolean destroyed = starRatingAlloy.isDestroyed();

		if (destroyed != null) {

			encodeDestroyed(responseWriter, starRatingAlloy, destroyed, first);
			first = false;
		}

		Boolean disabled = starRatingAlloy.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, starRatingAlloy, disabled, first);
			first = false;
		}

		String elements = starRatingAlloy.getElements();

		if (elements != null) {

			encodeElements(responseWriter, starRatingAlloy, elements, first);
			first = false;
		}

		Boolean focused = starRatingAlloy.isFocused();

		if (focused != null) {

			encodeFocused(responseWriter, starRatingAlloy, focused, first);
			first = false;
		}

		Object height = starRatingAlloy.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, starRatingAlloy, height, first);
			first = false;
		}

		String hiddenInput = starRatingAlloy.getHiddenInput();

		if (hiddenInput != null) {

			encodeHiddenInput(responseWriter, starRatingAlloy, hiddenInput, first);
			first = false;
		}

		String hideClass = starRatingAlloy.getHideClass();

		if (hideClass != null) {

			encodeHideClass(responseWriter, starRatingAlloy, hideClass, first);
			first = false;
		}

		String id = starRatingAlloy.getId();

		if (id != null) {

			encodeId(responseWriter, starRatingAlloy, id, first);
			first = false;
		}

		Boolean initialized = starRatingAlloy.isInitialized();

		if (initialized != null) {

			encodeInitialized(responseWriter, starRatingAlloy, initialized, first);
			first = false;
		}

		String inputName = starRatingAlloy.getInputName();

		if (inputName != null) {

			encodeInputName(responseWriter, starRatingAlloy, inputName, first);
			first = false;
		}

		String label = starRatingAlloy.getLabel();

		if (label != null) {

			encodeLabel(responseWriter, starRatingAlloy, label, first);
			first = false;
		}

		String labelNode = starRatingAlloy.getLabelNode();

		if (labelNode != null) {

			encodeLabelNode(responseWriter, starRatingAlloy, labelNode, first);
			first = false;
		}

		String locale = starRatingAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, starRatingAlloy, locale, first);
			first = false;
		}

		Boolean rendered = starRatingAlloy.isRendered();

		if (rendered != null) {

			encodeRendered(responseWriter, starRatingAlloy, rendered, first);
			first = false;
		}

		Object selectedIndex = starRatingAlloy.getSelectedIndex();

		if (selectedIndex != null) {

			encodeSelectedIndex(responseWriter, starRatingAlloy, selectedIndex, first);
			first = false;
		}

		Boolean showTitle = starRatingAlloy.isShowTitle();

		if (showTitle != null) {

			encodeShowTitle(responseWriter, starRatingAlloy, showTitle, first);
			first = false;
		}

		Object size = starRatingAlloy.getSize();

		if (size != null) {

			encodeSize(responseWriter, starRatingAlloy, size, first);
			first = false;
		}

		String srcNode = starRatingAlloy.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, starRatingAlloy, srcNode, first);
			first = false;
		}

		Object strings = starRatingAlloy.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, starRatingAlloy, strings, first);
			first = false;
		}

		Object tabIndex = starRatingAlloy.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, starRatingAlloy, tabIndex, first);
			first = false;
		}

		String title = starRatingAlloy.getTitle();

		if (title != null) {

			encodeTitle(responseWriter, starRatingAlloy, title, first);
			first = false;
		}

		Boolean useARIA = starRatingAlloy.isUseARIA();

		if (useARIA != null) {

			encodeUseARIA(responseWriter, starRatingAlloy, useARIA, first);
			first = false;
		}

		Object value = starRatingAlloy.getValue();

		if (value != null) {

			encodeValue(responseWriter, starRatingAlloy, value, first);
			first = false;
		}

		Boolean visible = starRatingAlloy.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, starRatingAlloy, visible, first);
			first = false;
		}

		Boolean widgetRender = starRatingAlloy.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, starRatingAlloy, widgetRender, first);
			first = false;
		}

		Object width = starRatingAlloy.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, starRatingAlloy, width, first);
			first = false;
		}

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterBoundingBoxChange = starRatingAlloy.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, starRatingAlloy, afterBoundingBoxChange, first);
			first = false;
		}

		String afterCanResetChange = starRatingAlloy.getAfterCanResetChange();

		if (afterCanResetChange != null) {

			encodeAfterCanResetChange(responseWriter, starRatingAlloy, afterCanResetChange, first);
			first = false;
		}

		String afterContentBoxChange = starRatingAlloy.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, starRatingAlloy, afterContentBoxChange, first);
			first = false;
		}

		String afterCssClassChange = starRatingAlloy.getAfterCssClassChange();

		if (afterCssClassChange != null) {

			encodeAfterCssClassChange(responseWriter, starRatingAlloy, afterCssClassChange, first);
			first = false;
		}

		String afterCssClassesChange = starRatingAlloy.getAfterCssClassesChange();

		if (afterCssClassesChange != null) {

			encodeAfterCssClassesChange(responseWriter, starRatingAlloy, afterCssClassesChange, first);
			first = false;
		}

		String afterDefaultSelectedChange = starRatingAlloy.getAfterDefaultSelectedChange();

		if (afterDefaultSelectedChange != null) {

			encodeAfterDefaultSelectedChange(responseWriter, starRatingAlloy, afterDefaultSelectedChange, first);
			first = false;
		}

		String afterDestroyedChange = starRatingAlloy.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, starRatingAlloy, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = starRatingAlloy.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, starRatingAlloy, afterDisabledChange, first);
			first = false;
		}

		String afterElementsChange = starRatingAlloy.getAfterElementsChange();

		if (afterElementsChange != null) {

			encodeAfterElementsChange(responseWriter, starRatingAlloy, afterElementsChange, first);
			first = false;
		}

		String afterFocusedChange = starRatingAlloy.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, starRatingAlloy, afterFocusedChange, first);
			first = false;
		}

		String afterHeightChange = starRatingAlloy.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, starRatingAlloy, afterHeightChange, first);
			first = false;
		}

		String afterHiddenInputChange = starRatingAlloy.getAfterHiddenInputChange();

		if (afterHiddenInputChange != null) {

			encodeAfterHiddenInputChange(responseWriter, starRatingAlloy, afterHiddenInputChange, first);
			first = false;
		}

		String afterHideClassChange = starRatingAlloy.getAfterHideClassChange();

		if (afterHideClassChange != null) {

			encodeAfterHideClassChange(responseWriter, starRatingAlloy, afterHideClassChange, first);
			first = false;
		}

		String afterIdChange = starRatingAlloy.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, starRatingAlloy, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = starRatingAlloy.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, starRatingAlloy, afterInitializedChange, first);
			first = false;
		}

		String afterInputNameChange = starRatingAlloy.getAfterInputNameChange();

		if (afterInputNameChange != null) {

			encodeAfterInputNameChange(responseWriter, starRatingAlloy, afterInputNameChange, first);
			first = false;
		}

		String afterLabelChange = starRatingAlloy.getAfterLabelChange();

		if (afterLabelChange != null) {

			encodeAfterLabelChange(responseWriter, starRatingAlloy, afterLabelChange, first);
			first = false;
		}

		String afterLabelNodeChange = starRatingAlloy.getAfterLabelNodeChange();

		if (afterLabelNodeChange != null) {

			encodeAfterLabelNodeChange(responseWriter, starRatingAlloy, afterLabelNodeChange, first);
			first = false;
		}

		String afterLocaleChange = starRatingAlloy.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, starRatingAlloy, afterLocaleChange, first);
			first = false;
		}

		String afterRenderChange = starRatingAlloy.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, starRatingAlloy, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = starRatingAlloy.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, starRatingAlloy, afterRenderedChange, first);
			first = false;
		}

		String afterSelectedIndexChange = starRatingAlloy.getAfterSelectedIndexChange();

		if (afterSelectedIndexChange != null) {

			encodeAfterSelectedIndexChange(responseWriter, starRatingAlloy, afterSelectedIndexChange, first);
			first = false;
		}

		String afterShowTitleChange = starRatingAlloy.getAfterShowTitleChange();

		if (afterShowTitleChange != null) {

			encodeAfterShowTitleChange(responseWriter, starRatingAlloy, afterShowTitleChange, first);
			first = false;
		}

		String afterSizeChange = starRatingAlloy.getAfterSizeChange();

		if (afterSizeChange != null) {

			encodeAfterSizeChange(responseWriter, starRatingAlloy, afterSizeChange, first);
			first = false;
		}

		String afterSrcNodeChange = starRatingAlloy.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, starRatingAlloy, afterSrcNodeChange, first);
			first = false;
		}

		String afterStringsChange = starRatingAlloy.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, starRatingAlloy, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = starRatingAlloy.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, starRatingAlloy, afterTabIndexChange, first);
			first = false;
		}

		String afterTitleChange = starRatingAlloy.getAfterTitleChange();

		if (afterTitleChange != null) {

			encodeAfterTitleChange(responseWriter, starRatingAlloy, afterTitleChange, first);
			first = false;
		}

		String afterUseARIAChange = starRatingAlloy.getAfterUseARIAChange();

		if (afterUseARIAChange != null) {

			encodeAfterUseARIAChange(responseWriter, starRatingAlloy, afterUseARIAChange, first);
			first = false;
		}

		String afterValueChange = starRatingAlloy.getAfterValueChange();

		if (afterValueChange != null) {

			encodeAfterValueChange(responseWriter, starRatingAlloy, afterValueChange, first);
			first = false;
		}

		String afterVisibleChange = starRatingAlloy.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, starRatingAlloy, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = starRatingAlloy.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, starRatingAlloy, afterWidthChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onBoundingBoxChange = starRatingAlloy.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, starRatingAlloy, onBoundingBoxChange, first);
			first = false;
		}

		String onCanResetChange = starRatingAlloy.getOnCanResetChange();

		if (onCanResetChange != null) {

			encodeOnCanResetChange(responseWriter, starRatingAlloy, onCanResetChange, first);
			first = false;
		}

		String onContentBoxChange = starRatingAlloy.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, starRatingAlloy, onContentBoxChange, first);
			first = false;
		}

		String onCssClassChange = starRatingAlloy.getOnCssClassChange();

		if (onCssClassChange != null) {

			encodeOnCssClassChange(responseWriter, starRatingAlloy, onCssClassChange, first);
			first = false;
		}

		String onCssClassesChange = starRatingAlloy.getOnCssClassesChange();

		if (onCssClassesChange != null) {

			encodeOnCssClassesChange(responseWriter, starRatingAlloy, onCssClassesChange, first);
			first = false;
		}

		String onDefaultSelectedChange = starRatingAlloy.getOnDefaultSelectedChange();

		if (onDefaultSelectedChange != null) {

			encodeOnDefaultSelectedChange(responseWriter, starRatingAlloy, onDefaultSelectedChange, first);
			first = false;
		}

		String onDestroyedChange = starRatingAlloy.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, starRatingAlloy, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = starRatingAlloy.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, starRatingAlloy, onDisabledChange, first);
			first = false;
		}

		String onElementsChange = starRatingAlloy.getOnElementsChange();

		if (onElementsChange != null) {

			encodeOnElementsChange(responseWriter, starRatingAlloy, onElementsChange, first);
			first = false;
		}

		String onFocusedChange = starRatingAlloy.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, starRatingAlloy, onFocusedChange, first);
			first = false;
		}

		String onHeightChange = starRatingAlloy.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, starRatingAlloy, onHeightChange, first);
			first = false;
		}

		String onHiddenInputChange = starRatingAlloy.getOnHiddenInputChange();

		if (onHiddenInputChange != null) {

			encodeOnHiddenInputChange(responseWriter, starRatingAlloy, onHiddenInputChange, first);
			first = false;
		}

		String onHideClassChange = starRatingAlloy.getOnHideClassChange();

		if (onHideClassChange != null) {

			encodeOnHideClassChange(responseWriter, starRatingAlloy, onHideClassChange, first);
			first = false;
		}

		String onIdChange = starRatingAlloy.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, starRatingAlloy, onIdChange, first);
			first = false;
		}

		String onInitializedChange = starRatingAlloy.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, starRatingAlloy, onInitializedChange, first);
			first = false;
		}

		String onInputNameChange = starRatingAlloy.getOnInputNameChange();

		if (onInputNameChange != null) {

			encodeOnInputNameChange(responseWriter, starRatingAlloy, onInputNameChange, first);
			first = false;
		}

		String onLabelChange = starRatingAlloy.getOnLabelChange();

		if (onLabelChange != null) {

			encodeOnLabelChange(responseWriter, starRatingAlloy, onLabelChange, first);
			first = false;
		}

		String onLabelNodeChange = starRatingAlloy.getOnLabelNodeChange();

		if (onLabelNodeChange != null) {

			encodeOnLabelNodeChange(responseWriter, starRatingAlloy, onLabelNodeChange, first);
			first = false;
		}

		String onLocaleChange = starRatingAlloy.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, starRatingAlloy, onLocaleChange, first);
			first = false;
		}

		String onRenderChange = starRatingAlloy.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, starRatingAlloy, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = starRatingAlloy.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, starRatingAlloy, onRenderedChange, first);
			first = false;
		}

		String onSelectedIndexChange = starRatingAlloy.getOnSelectedIndexChange();

		if (onSelectedIndexChange != null) {

			encodeOnSelectedIndexChange(responseWriter, starRatingAlloy, onSelectedIndexChange, first);
			first = false;
		}

		String onShowTitleChange = starRatingAlloy.getOnShowTitleChange();

		if (onShowTitleChange != null) {

			encodeOnShowTitleChange(responseWriter, starRatingAlloy, onShowTitleChange, first);
			first = false;
		}

		String onSizeChange = starRatingAlloy.getOnSizeChange();

		if (onSizeChange != null) {

			encodeOnSizeChange(responseWriter, starRatingAlloy, onSizeChange, first);
			first = false;
		}

		String onSrcNodeChange = starRatingAlloy.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, starRatingAlloy, onSrcNodeChange, first);
			first = false;
		}

		String onStringsChange = starRatingAlloy.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, starRatingAlloy, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = starRatingAlloy.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, starRatingAlloy, onTabIndexChange, first);
			first = false;
		}

		String onTitleChange = starRatingAlloy.getOnTitleChange();

		if (onTitleChange != null) {

			encodeOnTitleChange(responseWriter, starRatingAlloy, onTitleChange, first);
			first = false;
		}

		String onUseARIAChange = starRatingAlloy.getOnUseARIAChange();

		if (onUseARIAChange != null) {

			encodeOnUseARIAChange(responseWriter, starRatingAlloy, onUseARIAChange, first);
			first = false;
		}

		String onValueChange = starRatingAlloy.getOnValueChange();

		if (onValueChange != null) {

			encodeOnValueChange(responseWriter, starRatingAlloy, onValueChange, first);
			first = false;
		}

		String onVisibleChange = starRatingAlloy.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, starRatingAlloy, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = starRatingAlloy.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, starRatingAlloy, onWidthChange, first);
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

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterCanResetChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterCanResetChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CAN_RESET_CHANGE, afterCanResetChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterCssClassChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterCssClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASS_CHANGE, afterCssClassChange, first);
	}

	protected void encodeAfterCssClassesChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterCssClassesChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASSES_CHANGE, afterCssClassesChange, first);
	}

	protected void encodeAfterDefaultSelectedChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterDefaultSelectedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_SELECTED_CHANGE, afterDefaultSelectedChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterElementsChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterElementsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ELEMENTS_CHANGE, afterElementsChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterHiddenInputChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterHiddenInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDDEN_INPUT_CHANGE, afterHiddenInputChange, first);
	}

	protected void encodeAfterHideClassChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterHideClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDE_CLASS_CHANGE, afterHideClassChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterInputNameChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterInputNameChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_NAME_CHANGE, afterInputNameChange, first);
	}

	protected void encodeAfterLabelChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterLabelChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_CHANGE, afterLabelChange, first);
	}

	protected void encodeAfterLabelNodeChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterLabelNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_NODE_CHANGE, afterLabelNodeChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterSelectedIndexChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterSelectedIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTED_INDEX_CHANGE, afterSelectedIndexChange, first);
	}

	protected void encodeAfterShowTitleChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterShowTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_TITLE_CHANGE, afterShowTitleChange, first);
	}

	protected void encodeAfterSizeChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SIZE_CHANGE, afterSizeChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterTitleChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TITLE_CHANGE, afterTitleChange, first);
	}

	protected void encodeAfterUseARIAChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterUseARIAChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_ARIACHANGE, afterUseARIAChange, first);
	}

	protected void encodeAfterValueChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, afterValueChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeCanReset(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Boolean canReset, boolean first) throws IOException {
		encodeBoolean(responseWriter, StarRatingAlloy.CAN_RESET, canReset, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.CONTENT_BOX, contentBox, first);
	}

	protected void encodeCssClass(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String cssClass, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.CSS_CLASS, cssClass, first);
	}

	protected void encodeCssClasses(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Object cssClasses, boolean first) throws IOException {
		encodeObject(responseWriter, StarRatingAlloy.CSS_CLASSES, cssClasses, first);
	}

	protected void encodeDefaultSelected(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Object defaultSelected, boolean first) throws IOException {
		encodeNumber(responseWriter, StarRatingAlloy.DEFAULT_SELECTED, defaultSelected, first);
	}

	protected void encodeDestroyed(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Boolean destroyed, boolean first) throws IOException {
		encodeBoolean(responseWriter, StarRatingAlloy.DESTROYED, destroyed, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, StarRatingAlloy.DISABLED, disabled, first);
	}

	protected void encodeElements(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String elements, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.ELEMENTS, elements, first);
	}

	protected void encodeFocused(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Boolean focused, boolean first) throws IOException {
		encodeBoolean(responseWriter, StarRatingAlloy.FOCUSED, focused, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Object height, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, StarRatingAlloy.HEIGHT, height, first);
	}

	protected void encodeHiddenInput(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String hiddenInput, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.HIDDEN_INPUT, hiddenInput, first);
	}

	protected void encodeHideClass(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String hideClass, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.HIDE_CLASS, hideClass, first);
	}

	protected void encodeId(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String id, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.ID, id, first);
	}

	protected void encodeInitialized(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Boolean initialized, boolean first) throws IOException {
		encodeBoolean(responseWriter, StarRatingAlloy.INITIALIZED, initialized, first);
	}

	protected void encodeInputName(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String inputName, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.INPUT_NAME, inputName, first);
	}

	protected void encodeLabel(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String label, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.LABEL, label, first);
	}

	protected void encodeLabelNode(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String labelNode, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.LABEL_NODE, labelNode, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String locale, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.LOCALE, locale, first);
	}

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnCanResetChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onCanResetChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CAN_RESET_CHANGE, onCanResetChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnCssClassChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onCssClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASS_CHANGE, onCssClassChange, first);
	}

	protected void encodeOnCssClassesChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onCssClassesChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CSS_CLASSES_CHANGE, onCssClassesChange, first);
	}

	protected void encodeOnDefaultSelectedChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onDefaultSelectedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DEFAULT_SELECTED_CHANGE, onDefaultSelectedChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnElementsChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onElementsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ELEMENTS_CHANGE, onElementsChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnHiddenInputChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onHiddenInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDDEN_INPUT_CHANGE, onHiddenInputChange, first);
	}

	protected void encodeOnHideClassChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onHideClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDE_CLASS_CHANGE, onHideClassChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnInputNameChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onInputNameChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INPUT_NAME_CHANGE, onInputNameChange, first);
	}

	protected void encodeOnLabelChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onLabelChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_CHANGE, onLabelChange, first);
	}

	protected void encodeOnLabelNodeChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onLabelNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LABEL_NODE_CHANGE, onLabelNodeChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnSelectedIndexChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onSelectedIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SELECTED_INDEX_CHANGE, onSelectedIndexChange, first);
	}

	protected void encodeOnShowTitleChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onShowTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHOW_TITLE_CHANGE, onShowTitleChange, first);
	}

	protected void encodeOnSizeChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onSizeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SIZE_CHANGE, onSizeChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnTitleChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onTitleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TITLE_CHANGE, onTitleChange, first);
	}

	protected void encodeOnUseARIAChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onUseARIAChange, boolean first) throws IOException {
		encodeEvent(responseWriter, USE_ARIACHANGE, onUseARIAChange, first);
	}

	protected void encodeOnValueChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onValueChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_CHANGE, onValueChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
	}

	protected void encodeRendered(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Boolean rendered, boolean first) throws IOException {
		encodeBoolean(responseWriter, StarRatingAlloy.RENDERED, rendered, first);
	}

	protected void encodeSelectedIndex(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Object selectedIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, StarRatingAlloy.SELECTED_INDEX, selectedIndex, first);
	}

	protected void encodeShowTitle(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Boolean showTitle, boolean first) throws IOException {
		encodeBoolean(responseWriter, StarRatingAlloy.SHOW_TITLE, showTitle, first);
	}

	protected void encodeSize(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Object size, boolean first) throws IOException {
		encodeNumber(responseWriter, StarRatingAlloy.SIZE, size, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.SRC_NODE, srcNode, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, StarRatingAlloy.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, StarRatingAlloy.TAB_INDEX, tabIndex, first);
	}

	protected void encodeTitle(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, String title, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.TITLE, title, first);
	}

	protected void encodeUseARIA(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Boolean useARIA, boolean first) throws IOException {
		encodeBoolean(responseWriter, StarRatingAlloy.USE_ARIA, useARIA, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Object value, boolean first) throws IOException {
		encodeString(responseWriter, StarRatingAlloy.VALUE, value, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, StarRatingAlloy.VISIBLE, visible, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, StarRatingAlloy.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, StarRatingAlloy starRatingAlloy, Object width, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, StarRatingAlloy.WIDTH, width, first);
	}
}
//J+
