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
package com.liferay.faces.alloy.component.popover;
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
public abstract class PopoverRendererBase extends AlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "Popover";
	private static final String ALLOY_MODULE_NAME = "aui-popover";
	private static final String ALIGN_CHANGE = "alignChange";
	private static final String ALIGN_ON_CHANGE = "alignOnChange";
	private static final String BODY_CONTENT_CHANGE = "bodyContentChange";
	private static final String BOUNDING_BOX_CHANGE = "boundingBoxChange";
	private static final String CENTERED_CHANGE = "centeredChange";
	private static final String CONSTRAIN_CHANGE = "constrainChange";
	private static final String CONTENT_BOX_CHANGE = "contentBoxChange";
	private static final String DESTROYED_CHANGE = "destroyedChange";
	private static final String DISABLED_CHANGE = "disabledChange";
	private static final String FILL_HEIGHT_CHANGE = "fillHeightChange";
	private static final String FOCUS_ON_CHANGE = "focusOnChange";
	private static final String FOCUSED_CHANGE = "focusedChange";
	private static final String FOOTER_CONTENT_CHANGE = "footerContentChange";
	private static final String HEADER_CONTENT_CHANGE = "headerContentChange";
	private static final String HEIGHT_CHANGE = "heightChange";
	private static final String HIDE_ON_CHANGE = "hideOnChange";
	private static final String ID_CHANGE = "idChange";
	private static final String INITIALIZED_CHANGE = "initializedChange";
	private static final String LOCALE_CHANGE = "localeChange";
	private static final String MASK_NODE_CHANGE = "maskNodeChange";
	private static final String MODAL_CHANGE = "modalChange";
	private static final String PREVENT_OVERLAP_CHANGE = "preventOverlapChange";
	private static final String RENDER_CHANGE = "renderChange";
	private static final String RENDERED_CHANGE = "renderedChange";
	private static final String SHIM_CHANGE = "shimChange";
	private static final String SRC_NODE_CHANGE = "srcNodeChange";
	private static final String STRINGS_CHANGE = "stringsChange";
	private static final String TAB_INDEX_CHANGE = "tabIndexChange";
	private static final String TOOLBAR_POSITION_CHANGE = "toolbarPositionChange";
	private static final String TOOLBARS_CHANGE = "toolbarsChange";
	private static final String TRIGGER_TOGGLE_EVENT_CHANGE = "triggerToggleEventChange";
	private static final String VISIBLE_CHANGE = "visibleChange";
	private static final String WIDTH_CHANGE = "widthChange";
	private static final String X_CHANGE = "xChange";
	private static final String XY_CHANGE = "xyChange";
	private static final String Y_CHANGE = "yChange";
	private static final String Z_INDEX_CHANGE = "zIndexChange";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		PopoverAlloy popoverAlloy = (PopoverAlloy) uiComponent;
		boolean first = true;

		Object align = popoverAlloy.getAlign();

		if (align != null) {

			encodeAlign(responseWriter, popoverAlloy, align, first);
			first = false;
		}

		Object alignOn = popoverAlloy.getAlignOn();

		if (alignOn != null) {

			encodeAlignOn(responseWriter, popoverAlloy, alignOn, first);
			first = false;
		}

		String bodyContent = popoverAlloy.getBodyContent();

		if (bodyContent != null) {

			encodeBodyContent(responseWriter, popoverAlloy, bodyContent, first);
			first = false;
		}

		String boundingBox = popoverAlloy.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, popoverAlloy, boundingBox, first);
			first = false;
		}

		Boolean centered = popoverAlloy.isCentered();

		if (centered != null) {

			encodeCentered(responseWriter, popoverAlloy, centered, first);
			first = false;
		}

		Boolean constrain = popoverAlloy.isConstrain();

		if (constrain != null) {

			encodeConstrain(responseWriter, popoverAlloy, constrain, first);
			first = false;
		}

		String contentBox = popoverAlloy.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, popoverAlloy, contentBox, first);
			first = false;
		}

		Boolean disabled = popoverAlloy.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, popoverAlloy, disabled, first);
			first = false;
		}

		String fillHeight = popoverAlloy.getFillHeight();

		if (fillHeight != null) {

			encodeFillHeight(responseWriter, popoverAlloy, fillHeight, first);
			first = false;
		}

		Object focusOn = popoverAlloy.getFocusOn();

		if (focusOn != null) {

			encodeFocusOn(responseWriter, popoverAlloy, focusOn, first);
			first = false;
		}

		String footerContent = popoverAlloy.getFooterContent();

		if (footerContent != null) {

			encodeFooterContent(responseWriter, popoverAlloy, footerContent, first);
			first = false;
		}

		String for_ = popoverAlloy.getFor();

		if (for_ != null) {

			encodeFor(responseWriter, popoverAlloy, for_, first);
			first = false;
		}

		String headerContent = popoverAlloy.getHeaderContent();

		if (headerContent != null) {

			encodeHeaderContent(responseWriter, popoverAlloy, headerContent, first);
			first = false;
		}

		Object height = popoverAlloy.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, popoverAlloy, height, first);
			first = false;
		}

		Object hideOn = popoverAlloy.getHideOn();

		if (hideOn != null) {

			encodeHideOn(responseWriter, popoverAlloy, hideOn, first);
			first = false;
		}

		String locale = popoverAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, popoverAlloy, locale, first);
			first = false;
		}

		String maskNode = popoverAlloy.getMaskNode();

		if (maskNode != null) {

			encodeMaskNode(responseWriter, popoverAlloy, maskNode, first);
			first = false;
		}

		Boolean modal = popoverAlloy.isModal();

		if (modal != null) {

			encodeModal(responseWriter, popoverAlloy, modal, first);
			first = false;
		}

		Object plugins = popoverAlloy.getPlugins();

		if (plugins != null) {

			encodePlugins(responseWriter, popoverAlloy, plugins, first);
			first = false;
		}

		String position = popoverAlloy.getPosition();

		if (position != null) {

			encodePosition(responseWriter, popoverAlloy, position, first);
			first = false;
		}

		Boolean preventOverlap = popoverAlloy.isPreventOverlap();

		if (preventOverlap != null) {

			encodePreventOverlap(responseWriter, popoverAlloy, preventOverlap, first);
			first = false;
		}

		Boolean shim = popoverAlloy.isShim();

		if (shim != null) {

			encodeShim(responseWriter, popoverAlloy, shim, first);
			first = false;
		}

		String srcNode = popoverAlloy.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, popoverAlloy, srcNode, first);
			first = false;
		}

		Object strings = popoverAlloy.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, popoverAlloy, strings, first);
			first = false;
		}

		Object tabIndex = popoverAlloy.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, popoverAlloy, tabIndex, first);
			first = false;
		}

		Object toolbarPosition = popoverAlloy.getToolbarPosition();

		if (toolbarPosition != null) {

			encodeToolbarPosition(responseWriter, popoverAlloy, toolbarPosition, first);
			first = false;
		}

		Object toolbars = popoverAlloy.getToolbars();

		if (toolbars != null) {

			encodeToolbars(responseWriter, popoverAlloy, toolbars, first);
			first = false;
		}

		String triggerToggleEvent = popoverAlloy.getTriggerToggleEvent();

		if (triggerToggleEvent != null) {

			encodeTriggerToggleEvent(responseWriter, popoverAlloy, triggerToggleEvent, first);
			first = false;
		}

		Boolean visible = popoverAlloy.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, popoverAlloy, visible, first);
			first = false;
		}

		String widgetId = popoverAlloy.getWidgetId();

		if (widgetId != null) {

			encodeWidgetId(responseWriter, popoverAlloy, widgetId, first);
			first = false;
		}

		Boolean widgetRender = popoverAlloy.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, popoverAlloy, widgetRender, first);
			first = false;
		}

		Object width = popoverAlloy.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, popoverAlloy, width, first);
			first = false;
		}

		Object x = popoverAlloy.getX();

		if (x != null) {

			encodeX(responseWriter, popoverAlloy, x, first);
			first = false;
		}

		Object xy = popoverAlloy.getXy();

		if (xy != null) {

			encodeXy(responseWriter, popoverAlloy, xy, first);
			first = false;
		}

		Object y = popoverAlloy.getY();

		if (y != null) {

			encodeY(responseWriter, popoverAlloy, y, first);
			first = false;
		}

		Object zIndex = popoverAlloy.getzIndex();

		if (zIndex != null) {

			encodeZIndex(responseWriter, popoverAlloy, zIndex, first);
			first = false;
		}

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterAlignChange = popoverAlloy.getAfterAlignChange();

		if (afterAlignChange != null) {

			encodeAfterAlignChange(responseWriter, popoverAlloy, afterAlignChange, first);
			first = false;
		}

		String afterAlignOnChange = popoverAlloy.getAfterAlignOnChange();

		if (afterAlignOnChange != null) {

			encodeAfterAlignOnChange(responseWriter, popoverAlloy, afterAlignOnChange, first);
			first = false;
		}

		String afterBodyContentChange = popoverAlloy.getAfterBodyContentChange();

		if (afterBodyContentChange != null) {

			encodeAfterBodyContentChange(responseWriter, popoverAlloy, afterBodyContentChange, first);
			first = false;
		}

		String afterBoundingBoxChange = popoverAlloy.getAfterBoundingBoxChange();

		if (afterBoundingBoxChange != null) {

			encodeAfterBoundingBoxChange(responseWriter, popoverAlloy, afterBoundingBoxChange, first);
			first = false;
		}

		String afterCenteredChange = popoverAlloy.getAfterCenteredChange();

		if (afterCenteredChange != null) {

			encodeAfterCenteredChange(responseWriter, popoverAlloy, afterCenteredChange, first);
			first = false;
		}

		String afterConstrainChange = popoverAlloy.getAfterConstrainChange();

		if (afterConstrainChange != null) {

			encodeAfterConstrainChange(responseWriter, popoverAlloy, afterConstrainChange, first);
			first = false;
		}

		String afterContentBoxChange = popoverAlloy.getAfterContentBoxChange();

		if (afterContentBoxChange != null) {

			encodeAfterContentBoxChange(responseWriter, popoverAlloy, afterContentBoxChange, first);
			first = false;
		}

		String afterDestroyedChange = popoverAlloy.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, popoverAlloy, afterDestroyedChange, first);
			first = false;
		}

		String afterDisabledChange = popoverAlloy.getAfterDisabledChange();

		if (afterDisabledChange != null) {

			encodeAfterDisabledChange(responseWriter, popoverAlloy, afterDisabledChange, first);
			first = false;
		}

		String afterFillHeightChange = popoverAlloy.getAfterFillHeightChange();

		if (afterFillHeightChange != null) {

			encodeAfterFillHeightChange(responseWriter, popoverAlloy, afterFillHeightChange, first);
			first = false;
		}

		String afterFocusedChange = popoverAlloy.getAfterFocusedChange();

		if (afterFocusedChange != null) {

			encodeAfterFocusedChange(responseWriter, popoverAlloy, afterFocusedChange, first);
			first = false;
		}

		String afterFocusOnChange = popoverAlloy.getAfterFocusOnChange();

		if (afterFocusOnChange != null) {

			encodeAfterFocusOnChange(responseWriter, popoverAlloy, afterFocusOnChange, first);
			first = false;
		}

		String afterFooterContentChange = popoverAlloy.getAfterFooterContentChange();

		if (afterFooterContentChange != null) {

			encodeAfterFooterContentChange(responseWriter, popoverAlloy, afterFooterContentChange, first);
			first = false;
		}

		String afterHeaderContentChange = popoverAlloy.getAfterHeaderContentChange();

		if (afterHeaderContentChange != null) {

			encodeAfterHeaderContentChange(responseWriter, popoverAlloy, afterHeaderContentChange, first);
			first = false;
		}

		String afterHeightChange = popoverAlloy.getAfterHeightChange();

		if (afterHeightChange != null) {

			encodeAfterHeightChange(responseWriter, popoverAlloy, afterHeightChange, first);
			first = false;
		}

		String afterHideOnChange = popoverAlloy.getAfterHideOnChange();

		if (afterHideOnChange != null) {

			encodeAfterHideOnChange(responseWriter, popoverAlloy, afterHideOnChange, first);
			first = false;
		}

		String afterIdChange = popoverAlloy.getAfterIdChange();

		if (afterIdChange != null) {

			encodeAfterIdChange(responseWriter, popoverAlloy, afterIdChange, first);
			first = false;
		}

		String afterInitializedChange = popoverAlloy.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, popoverAlloy, afterInitializedChange, first);
			first = false;
		}

		String afterLocaleChange = popoverAlloy.getAfterLocaleChange();

		if (afterLocaleChange != null) {

			encodeAfterLocaleChange(responseWriter, popoverAlloy, afterLocaleChange, first);
			first = false;
		}

		String afterMaskNodeChange = popoverAlloy.getAfterMaskNodeChange();

		if (afterMaskNodeChange != null) {

			encodeAfterMaskNodeChange(responseWriter, popoverAlloy, afterMaskNodeChange, first);
			first = false;
		}

		String afterModalChange = popoverAlloy.getAfterModalChange();

		if (afterModalChange != null) {

			encodeAfterModalChange(responseWriter, popoverAlloy, afterModalChange, first);
			first = false;
		}

		String afterPreventOverlapChange = popoverAlloy.getAfterPreventOverlapChange();

		if (afterPreventOverlapChange != null) {

			encodeAfterPreventOverlapChange(responseWriter, popoverAlloy, afterPreventOverlapChange, first);
			first = false;
		}

		String afterRenderChange = popoverAlloy.getAfterRenderChange();

		if (afterRenderChange != null) {

			encodeAfterRenderChange(responseWriter, popoverAlloy, afterRenderChange, first);
			first = false;
		}

		String afterRenderedChange = popoverAlloy.getAfterRenderedChange();

		if (afterRenderedChange != null) {

			encodeAfterRenderedChange(responseWriter, popoverAlloy, afterRenderedChange, first);
			first = false;
		}

		String afterShimChange = popoverAlloy.getAfterShimChange();

		if (afterShimChange != null) {

			encodeAfterShimChange(responseWriter, popoverAlloy, afterShimChange, first);
			first = false;
		}

		String afterSrcNodeChange = popoverAlloy.getAfterSrcNodeChange();

		if (afterSrcNodeChange != null) {

			encodeAfterSrcNodeChange(responseWriter, popoverAlloy, afterSrcNodeChange, first);
			first = false;
		}

		String afterStringsChange = popoverAlloy.getAfterStringsChange();

		if (afterStringsChange != null) {

			encodeAfterStringsChange(responseWriter, popoverAlloy, afterStringsChange, first);
			first = false;
		}

		String afterTabIndexChange = popoverAlloy.getAfterTabIndexChange();

		if (afterTabIndexChange != null) {

			encodeAfterTabIndexChange(responseWriter, popoverAlloy, afterTabIndexChange, first);
			first = false;
		}

		String afterToolbarPositionChange = popoverAlloy.getAfterToolbarPositionChange();

		if (afterToolbarPositionChange != null) {

			encodeAfterToolbarPositionChange(responseWriter, popoverAlloy, afterToolbarPositionChange, first);
			first = false;
		}

		String afterToolbarsChange = popoverAlloy.getAfterToolbarsChange();

		if (afterToolbarsChange != null) {

			encodeAfterToolbarsChange(responseWriter, popoverAlloy, afterToolbarsChange, first);
			first = false;
		}

		String afterTriggerToggleEventChange = popoverAlloy.getAfterTriggerToggleEventChange();

		if (afterTriggerToggleEventChange != null) {

			encodeAfterTriggerToggleEventChange(responseWriter, popoverAlloy, afterTriggerToggleEventChange, first);
			first = false;
		}

		String afterVisibleChange = popoverAlloy.getAfterVisibleChange();

		if (afterVisibleChange != null) {

			encodeAfterVisibleChange(responseWriter, popoverAlloy, afterVisibleChange, first);
			first = false;
		}

		String afterWidthChange = popoverAlloy.getAfterWidthChange();

		if (afterWidthChange != null) {

			encodeAfterWidthChange(responseWriter, popoverAlloy, afterWidthChange, first);
			first = false;
		}

		String afterXChange = popoverAlloy.getAfterXChange();

		if (afterXChange != null) {

			encodeAfterXChange(responseWriter, popoverAlloy, afterXChange, first);
			first = false;
		}

		String afterXyChange = popoverAlloy.getAfterXyChange();

		if (afterXyChange != null) {

			encodeAfterXyChange(responseWriter, popoverAlloy, afterXyChange, first);
			first = false;
		}

		String afterYChange = popoverAlloy.getAfterYChange();

		if (afterYChange != null) {

			encodeAfterYChange(responseWriter, popoverAlloy, afterYChange, first);
			first = false;
		}

		String afterZIndexChange = popoverAlloy.getAfterZIndexChange();

		if (afterZIndexChange != null) {

			encodeAfterZIndexChange(responseWriter, popoverAlloy, afterZIndexChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onAlignChange = popoverAlloy.getOnAlignChange();

		if (onAlignChange != null) {

			encodeOnAlignChange(responseWriter, popoverAlloy, onAlignChange, first);
			first = false;
		}

		String onAlignOnChange = popoverAlloy.getOnAlignOnChange();

		if (onAlignOnChange != null) {

			encodeOnAlignOnChange(responseWriter, popoverAlloy, onAlignOnChange, first);
			first = false;
		}

		String onBodyContentChange = popoverAlloy.getOnBodyContentChange();

		if (onBodyContentChange != null) {

			encodeOnBodyContentChange(responseWriter, popoverAlloy, onBodyContentChange, first);
			first = false;
		}

		String onBoundingBoxChange = popoverAlloy.getOnBoundingBoxChange();

		if (onBoundingBoxChange != null) {

			encodeOnBoundingBoxChange(responseWriter, popoverAlloy, onBoundingBoxChange, first);
			first = false;
		}

		String onCenteredChange = popoverAlloy.getOnCenteredChange();

		if (onCenteredChange != null) {

			encodeOnCenteredChange(responseWriter, popoverAlloy, onCenteredChange, first);
			first = false;
		}

		String onConstrainChange = popoverAlloy.getOnConstrainChange();

		if (onConstrainChange != null) {

			encodeOnConstrainChange(responseWriter, popoverAlloy, onConstrainChange, first);
			first = false;
		}

		String onContentBoxChange = popoverAlloy.getOnContentBoxChange();

		if (onContentBoxChange != null) {

			encodeOnContentBoxChange(responseWriter, popoverAlloy, onContentBoxChange, first);
			first = false;
		}

		String onDestroyedChange = popoverAlloy.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, popoverAlloy, onDestroyedChange, first);
			first = false;
		}

		String onDisabledChange = popoverAlloy.getOnDisabledChange();

		if (onDisabledChange != null) {

			encodeOnDisabledChange(responseWriter, popoverAlloy, onDisabledChange, first);
			first = false;
		}

		String onFillHeightChange = popoverAlloy.getOnFillHeightChange();

		if (onFillHeightChange != null) {

			encodeOnFillHeightChange(responseWriter, popoverAlloy, onFillHeightChange, first);
			first = false;
		}

		String onFocusedChange = popoverAlloy.getOnFocusedChange();

		if (onFocusedChange != null) {

			encodeOnFocusedChange(responseWriter, popoverAlloy, onFocusedChange, first);
			first = false;
		}

		String onFocusOnChange = popoverAlloy.getOnFocusOnChange();

		if (onFocusOnChange != null) {

			encodeOnFocusOnChange(responseWriter, popoverAlloy, onFocusOnChange, first);
			first = false;
		}

		String onFooterContentChange = popoverAlloy.getOnFooterContentChange();

		if (onFooterContentChange != null) {

			encodeOnFooterContentChange(responseWriter, popoverAlloy, onFooterContentChange, first);
			first = false;
		}

		String onHeaderContentChange = popoverAlloy.getOnHeaderContentChange();

		if (onHeaderContentChange != null) {

			encodeOnHeaderContentChange(responseWriter, popoverAlloy, onHeaderContentChange, first);
			first = false;
		}

		String onHeightChange = popoverAlloy.getOnHeightChange();

		if (onHeightChange != null) {

			encodeOnHeightChange(responseWriter, popoverAlloy, onHeightChange, first);
			first = false;
		}

		String onHideOnChange = popoverAlloy.getOnHideOnChange();

		if (onHideOnChange != null) {

			encodeOnHideOnChange(responseWriter, popoverAlloy, onHideOnChange, first);
			first = false;
		}

		String onIdChange = popoverAlloy.getOnIdChange();

		if (onIdChange != null) {

			encodeOnIdChange(responseWriter, popoverAlloy, onIdChange, first);
			first = false;
		}

		String onInitializedChange = popoverAlloy.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, popoverAlloy, onInitializedChange, first);
			first = false;
		}

		String onLocaleChange = popoverAlloy.getOnLocaleChange();

		if (onLocaleChange != null) {

			encodeOnLocaleChange(responseWriter, popoverAlloy, onLocaleChange, first);
			first = false;
		}

		String onMaskNodeChange = popoverAlloy.getOnMaskNodeChange();

		if (onMaskNodeChange != null) {

			encodeOnMaskNodeChange(responseWriter, popoverAlloy, onMaskNodeChange, first);
			first = false;
		}

		String onModalChange = popoverAlloy.getOnModalChange();

		if (onModalChange != null) {

			encodeOnModalChange(responseWriter, popoverAlloy, onModalChange, first);
			first = false;
		}

		String onPreventOverlapChange = popoverAlloy.getOnPreventOverlapChange();

		if (onPreventOverlapChange != null) {

			encodeOnPreventOverlapChange(responseWriter, popoverAlloy, onPreventOverlapChange, first);
			first = false;
		}

		String onRenderChange = popoverAlloy.getOnRenderChange();

		if (onRenderChange != null) {

			encodeOnRenderChange(responseWriter, popoverAlloy, onRenderChange, first);
			first = false;
		}

		String onRenderedChange = popoverAlloy.getOnRenderedChange();

		if (onRenderedChange != null) {

			encodeOnRenderedChange(responseWriter, popoverAlloy, onRenderedChange, first);
			first = false;
		}

		String onShimChange = popoverAlloy.getOnShimChange();

		if (onShimChange != null) {

			encodeOnShimChange(responseWriter, popoverAlloy, onShimChange, first);
			first = false;
		}

		String onSrcNodeChange = popoverAlloy.getOnSrcNodeChange();

		if (onSrcNodeChange != null) {

			encodeOnSrcNodeChange(responseWriter, popoverAlloy, onSrcNodeChange, first);
			first = false;
		}

		String onStringsChange = popoverAlloy.getOnStringsChange();

		if (onStringsChange != null) {

			encodeOnStringsChange(responseWriter, popoverAlloy, onStringsChange, first);
			first = false;
		}

		String onTabIndexChange = popoverAlloy.getOnTabIndexChange();

		if (onTabIndexChange != null) {

			encodeOnTabIndexChange(responseWriter, popoverAlloy, onTabIndexChange, first);
			first = false;
		}

		String onToolbarPositionChange = popoverAlloy.getOnToolbarPositionChange();

		if (onToolbarPositionChange != null) {

			encodeOnToolbarPositionChange(responseWriter, popoverAlloy, onToolbarPositionChange, first);
			first = false;
		}

		String onToolbarsChange = popoverAlloy.getOnToolbarsChange();

		if (onToolbarsChange != null) {

			encodeOnToolbarsChange(responseWriter, popoverAlloy, onToolbarsChange, first);
			first = false;
		}

		String onTriggerToggleEventChange = popoverAlloy.getOnTriggerToggleEventChange();

		if (onTriggerToggleEventChange != null) {

			encodeOnTriggerToggleEventChange(responseWriter, popoverAlloy, onTriggerToggleEventChange, first);
			first = false;
		}

		String onVisibleChange = popoverAlloy.getOnVisibleChange();

		if (onVisibleChange != null) {

			encodeOnVisibleChange(responseWriter, popoverAlloy, onVisibleChange, first);
			first = false;
		}

		String onWidthChange = popoverAlloy.getOnWidthChange();

		if (onWidthChange != null) {

			encodeOnWidthChange(responseWriter, popoverAlloy, onWidthChange, first);
			first = false;
		}

		String onXChange = popoverAlloy.getOnXChange();

		if (onXChange != null) {

			encodeOnXChange(responseWriter, popoverAlloy, onXChange, first);
			first = false;
		}

		String onXyChange = popoverAlloy.getOnXyChange();

		if (onXyChange != null) {

			encodeOnXyChange(responseWriter, popoverAlloy, onXyChange, first);
			first = false;
		}

		String onYChange = popoverAlloy.getOnYChange();

		if (onYChange != null) {

			encodeOnYChange(responseWriter, popoverAlloy, onYChange, first);
			first = false;
		}

		String onZIndexChange = popoverAlloy.getOnZIndexChange();

		if (onZIndexChange != null) {

			encodeOnZIndexChange(responseWriter, popoverAlloy, onZIndexChange, first);
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

	protected void encodeAfterAlignChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterAlignChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ALIGN_CHANGE, afterAlignChange, first);
	}

	protected void encodeAfterAlignOnChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterAlignOnChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ALIGN_ON_CHANGE, afterAlignOnChange, first);
	}

	protected void encodeAfterBodyContentChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterBodyContentChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BODY_CONTENT_CHANGE, afterBodyContentChange, first);
	}

	protected void encodeAfterBoundingBoxChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, afterBoundingBoxChange, first);
	}

	protected void encodeAfterCenteredChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterCenteredChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CENTERED_CHANGE, afterCenteredChange, first);
	}

	protected void encodeAfterConstrainChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterConstrainChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONSTRAIN_CHANGE, afterConstrainChange, first);
	}

	protected void encodeAfterContentBoxChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, afterContentBoxChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterDisabledChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, afterDisabledChange, first);
	}

	protected void encodeAfterFillHeightChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterFillHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FILL_HEIGHT_CHANGE, afterFillHeightChange, first);
	}

	protected void encodeAfterFocusedChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, afterFocusedChange, first);
	}

	protected void encodeAfterFocusOnChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterFocusOnChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUS_ON_CHANGE, afterFocusOnChange, first);
	}

	protected void encodeAfterFooterContentChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterFooterContentChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOOTER_CONTENT_CHANGE, afterFooterContentChange, first);
	}

	protected void encodeAfterHeaderContentChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterHeaderContentChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEADER_CONTENT_CHANGE, afterHeaderContentChange, first);
	}

	protected void encodeAfterHeightChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, afterHeightChange, first);
	}

	protected void encodeAfterHideOnChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterHideOnChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDE_ON_CHANGE, afterHideOnChange, first);
	}

	protected void encodeAfterIdChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, afterIdChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterLocaleChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, afterLocaleChange, first);
	}

	protected void encodeAfterMaskNodeChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterMaskNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MASK_NODE_CHANGE, afterMaskNodeChange, first);
	}

	protected void encodeAfterModalChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterModalChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MODAL_CHANGE, afterModalChange, first);
	}

	protected void encodeAfterPreventOverlapChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterPreventOverlapChange, boolean first) throws IOException {
		encodeEvent(responseWriter, PREVENT_OVERLAP_CHANGE, afterPreventOverlapChange, first);
	}

	protected void encodeAfterRenderChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, afterRenderChange, first);
	}

	protected void encodeAfterRenderedChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, afterRenderedChange, first);
	}

	protected void encodeAfterShimChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterShimChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHIM_CHANGE, afterShimChange, first);
	}

	protected void encodeAfterSrcNodeChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, afterSrcNodeChange, first);
	}

	protected void encodeAfterStringsChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, afterStringsChange, first);
	}

	protected void encodeAfterTabIndexChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, afterTabIndexChange, first);
	}

	protected void encodeAfterToolbarPositionChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterToolbarPositionChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TOOLBAR_POSITION_CHANGE, afterToolbarPositionChange, first);
	}

	protected void encodeAfterToolbarsChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterToolbarsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TOOLBARS_CHANGE, afterToolbarsChange, first);
	}

	protected void encodeAfterTriggerToggleEventChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterTriggerToggleEventChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TRIGGER_TOGGLE_EVENT_CHANGE, afterTriggerToggleEventChange, first);
	}

	protected void encodeAfterVisibleChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, afterVisibleChange, first);
	}

	protected void encodeAfterWidthChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, afterWidthChange, first);
	}

	protected void encodeAfterXChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterXChange, boolean first) throws IOException {
		encodeEvent(responseWriter, X_CHANGE, afterXChange, first);
	}

	protected void encodeAfterXyChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterXyChange, boolean first) throws IOException {
		encodeEvent(responseWriter, XY_CHANGE, afterXyChange, first);
	}

	protected void encodeAfterYChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterYChange, boolean first) throws IOException {
		encodeEvent(responseWriter, Y_CHANGE, afterYChange, first);
	}

	protected void encodeAfterZIndexChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String afterZIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, Z_INDEX_CHANGE, afterZIndexChange, first);
	}

	protected void encodeAlign(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object align, boolean first) throws IOException {
		encodeObject(responseWriter, PopoverAlloy.ALIGN, align, first);
	}

	protected void encodeAlignOn(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object alignOn, boolean first) throws IOException {
		encodeArray(responseWriter, PopoverAlloy.ALIGN_ON, alignOn, first);
	}

	protected void encodeBodyContent(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String bodyContent, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.BODY_CONTENT, bodyContent, first);
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeCentered(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Boolean centered, boolean first) throws IOException {
		encodeBoolean(responseWriter, PopoverAlloy.CENTERED, centered, first);
	}

	protected void encodeConstrain(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Boolean constrain, boolean first) throws IOException {
		encodeBoolean(responseWriter, PopoverAlloy.CONSTRAIN, constrain, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.CONTENT_BOX, contentBox, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, PopoverAlloy.DISABLED, disabled, first);
	}

	protected void encodeFillHeight(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String fillHeight, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.FILL_HEIGHT, fillHeight, first);
	}

	protected void encodeFocusOn(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object focusOn, boolean first) throws IOException {
		encodeArray(responseWriter, PopoverAlloy.FOCUS_ON, focusOn, first);
	}

	protected void encodeFooterContent(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String footerContent, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.FOOTER_CONTENT, footerContent, first);
	}

	protected void encodeFor(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String for_, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.FOR, for_, first);
	}

	protected void encodeHeaderContent(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String headerContent, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.HEADER_CONTENT, headerContent, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object height, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, PopoverAlloy.HEIGHT, height, first);
	}

	protected void encodeHideOn(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object hideOn, boolean first) throws IOException {
		encodeArray(responseWriter, PopoverAlloy.HIDE_ON, hideOn, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String locale, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.LOCALE, locale, first);
	}

	protected void encodeMaskNode(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String maskNode, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.MASK_NODE, maskNode, first);
	}

	protected void encodeModal(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Boolean modal, boolean first) throws IOException {
		encodeBoolean(responseWriter, PopoverAlloy.MODAL, modal, first);
	}

	protected void encodeOnAlignChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onAlignChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ALIGN_CHANGE, onAlignChange, first);
	}

	protected void encodeOnAlignOnChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onAlignOnChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ALIGN_ON_CHANGE, onAlignOnChange, first);
	}

	protected void encodeOnBodyContentChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onBodyContentChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BODY_CONTENT_CHANGE, onBodyContentChange, first);
	}

	protected void encodeOnBoundingBoxChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onBoundingBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, BOUNDING_BOX_CHANGE, onBoundingBoxChange, first);
	}

	protected void encodeOnCenteredChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onCenteredChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CENTERED_CHANGE, onCenteredChange, first);
	}

	protected void encodeOnConstrainChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onConstrainChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONSTRAIN_CHANGE, onConstrainChange, first);
	}

	protected void encodeOnContentBoxChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onContentBoxChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_BOX_CHANGE, onContentBoxChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnDisabledChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onDisabledChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DISABLED_CHANGE, onDisabledChange, first);
	}

	protected void encodeOnFillHeightChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onFillHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FILL_HEIGHT_CHANGE, onFillHeightChange, first);
	}

	protected void encodeOnFocusedChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onFocusedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUSED_CHANGE, onFocusedChange, first);
	}

	protected void encodeOnFocusOnChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onFocusOnChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOCUS_ON_CHANGE, onFocusOnChange, first);
	}

	protected void encodeOnFooterContentChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onFooterContentChange, boolean first) throws IOException {
		encodeEvent(responseWriter, FOOTER_CONTENT_CHANGE, onFooterContentChange, first);
	}

	protected void encodeOnHeaderContentChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onHeaderContentChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEADER_CONTENT_CHANGE, onHeaderContentChange, first);
	}

	protected void encodeOnHeightChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onHeightChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HEIGHT_CHANGE, onHeightChange, first);
	}

	protected void encodeOnHideOnChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onHideOnChange, boolean first) throws IOException {
		encodeEvent(responseWriter, HIDE_ON_CHANGE, onHideOnChange, first);
	}

	protected void encodeOnIdChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onIdChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ID_CHANGE, onIdChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnLocaleChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onLocaleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, LOCALE_CHANGE, onLocaleChange, first);
	}

	protected void encodeOnMaskNodeChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onMaskNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MASK_NODE_CHANGE, onMaskNodeChange, first);
	}

	protected void encodeOnModalChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onModalChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MODAL_CHANGE, onModalChange, first);
	}

	protected void encodeOnPreventOverlapChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onPreventOverlapChange, boolean first) throws IOException {
		encodeEvent(responseWriter, PREVENT_OVERLAP_CHANGE, onPreventOverlapChange, first);
	}

	protected void encodeOnRenderChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onRenderChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDER_CHANGE, onRenderChange, first);
	}

	protected void encodeOnRenderedChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onRenderedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, RENDERED_CHANGE, onRenderedChange, first);
	}

	protected void encodeOnShimChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onShimChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SHIM_CHANGE, onShimChange, first);
	}

	protected void encodeOnSrcNodeChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onSrcNodeChange, boolean first) throws IOException {
		encodeEvent(responseWriter, SRC_NODE_CHANGE, onSrcNodeChange, first);
	}

	protected void encodeOnStringsChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onStringsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, STRINGS_CHANGE, onStringsChange, first);
	}

	protected void encodeOnTabIndexChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onTabIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TAB_INDEX_CHANGE, onTabIndexChange, first);
	}

	protected void encodeOnToolbarPositionChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onToolbarPositionChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TOOLBAR_POSITION_CHANGE, onToolbarPositionChange, first);
	}

	protected void encodeOnToolbarsChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onToolbarsChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TOOLBARS_CHANGE, onToolbarsChange, first);
	}

	protected void encodeOnTriggerToggleEventChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onTriggerToggleEventChange, boolean first) throws IOException {
		encodeEvent(responseWriter, TRIGGER_TOGGLE_EVENT_CHANGE, onTriggerToggleEventChange, first);
	}

	protected void encodeOnVisibleChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onVisibleChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VISIBLE_CHANGE, onVisibleChange, first);
	}

	protected void encodeOnWidthChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onWidthChange, boolean first) throws IOException {
		encodeEvent(responseWriter, WIDTH_CHANGE, onWidthChange, first);
	}

	protected void encodeOnXChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onXChange, boolean first) throws IOException {
		encodeEvent(responseWriter, X_CHANGE, onXChange, first);
	}

	protected void encodeOnXyChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onXyChange, boolean first) throws IOException {
		encodeEvent(responseWriter, XY_CHANGE, onXyChange, first);
	}

	protected void encodeOnYChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onYChange, boolean first) throws IOException {
		encodeEvent(responseWriter, Y_CHANGE, onYChange, first);
	}

	protected void encodeOnZIndexChange(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String onZIndexChange, boolean first) throws IOException {
		encodeEvent(responseWriter, Z_INDEX_CHANGE, onZIndexChange, first);
	}

	protected void encodePlugins(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object plugins, boolean first) throws IOException {
		encodeObject(responseWriter, PopoverAlloy.PLUGINS, plugins, first);
	}

	protected void encodePosition(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String position, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.POSITION, position, first);
	}

	protected void encodePreventOverlap(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Boolean preventOverlap, boolean first) throws IOException {
		encodeBoolean(responseWriter, PopoverAlloy.PREVENT_OVERLAP, preventOverlap, first);
	}

	protected void encodeShim(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Boolean shim, boolean first) throws IOException {
		encodeBoolean(responseWriter, PopoverAlloy.SHIM, shim, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.SRC_NODE, srcNode, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, PopoverAlloy.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, PopoverAlloy.TAB_INDEX, tabIndex, first);
	}

	protected void encodeToolbarPosition(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object toolbarPosition, boolean first) throws IOException {
		encodeObject(responseWriter, PopoverAlloy.TOOLBAR_POSITION, toolbarPosition, first);
	}

	protected void encodeToolbars(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object toolbars, boolean first) throws IOException {
		encodeObject(responseWriter, PopoverAlloy.TOOLBARS, toolbars, first);
	}

	protected void encodeTriggerToggleEvent(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String triggerToggleEvent, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.TRIGGER_TOGGLE_EVENT, triggerToggleEvent, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, PopoverAlloy.VISIBLE, visible, first);
	}

	protected void encodeWidgetId(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, String widgetId, boolean first) throws IOException {
		encodeString(responseWriter, PopoverAlloy.WIDGET_ID, widgetId, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, PopoverAlloy.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object width, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, PopoverAlloy.WIDTH, width, first);
	}

	protected void encodeX(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object x, boolean first) throws IOException {
		encodeNumber(responseWriter, PopoverAlloy.X, x, first);
	}

	protected void encodeXy(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object xy, boolean first) throws IOException {
		encodeArray(responseWriter, PopoverAlloy.XY, xy, first);
	}

	protected void encodeY(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object y, boolean first) throws IOException {
		encodeNumber(responseWriter, PopoverAlloy.Y, y, first);
	}

	protected void encodeZIndex(ResponseWriter responseWriter, PopoverAlloy popoverAlloy, Object zIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, PopoverAlloy.Z_INDEX, zIndex, first);
	}
}
//J+
