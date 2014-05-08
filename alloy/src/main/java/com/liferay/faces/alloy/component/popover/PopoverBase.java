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

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.outputtext.OutputText;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PopoverBase extends OutputText implements Styleable, ClientComponent, PopoverAlloy {

	@Override
	public String getAfterAlignChange() {
		return (String) getStateHelper().eval(AFTER_ALIGN_CHANGE, null);
	}

	@Override
	public void setAfterAlignChange(String afterAlignChange) {
		getStateHelper().put(AFTER_ALIGN_CHANGE, afterAlignChange);
	}

	@Override
	public String getAfterAlignOnChange() {
		return (String) getStateHelper().eval(AFTER_ALIGN_ON_CHANGE, null);
	}

	@Override
	public void setAfterAlignOnChange(String afterAlignOnChange) {
		getStateHelper().put(AFTER_ALIGN_ON_CHANGE, afterAlignOnChange);
	}

	@Override
	public String getAfterBodyContentChange() {
		return (String) getStateHelper().eval(AFTER_BODY_CONTENT_CHANGE, null);
	}

	@Override
	public void setAfterBodyContentChange(String afterBodyContentChange) {
		getStateHelper().put(AFTER_BODY_CONTENT_CHANGE, afterBodyContentChange);
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
	public String getAfterCenteredChange() {
		return (String) getStateHelper().eval(AFTER_CENTERED_CHANGE, null);
	}

	@Override
	public void setAfterCenteredChange(String afterCenteredChange) {
		getStateHelper().put(AFTER_CENTERED_CHANGE, afterCenteredChange);
	}

	@Override
	public String getAfterConstrainChange() {
		return (String) getStateHelper().eval(AFTER_CONSTRAIN_CHANGE, null);
	}

	@Override
	public void setAfterConstrainChange(String afterConstrainChange) {
		getStateHelper().put(AFTER_CONSTRAIN_CHANGE, afterConstrainChange);
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
	public String getAfterFillHeightChange() {
		return (String) getStateHelper().eval(AFTER_FILL_HEIGHT_CHANGE, null);
	}

	@Override
	public void setAfterFillHeightChange(String afterFillHeightChange) {
		getStateHelper().put(AFTER_FILL_HEIGHT_CHANGE, afterFillHeightChange);
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
	public String getAfterFocusOnChange() {
		return (String) getStateHelper().eval(AFTER_FOCUS_ON_CHANGE, null);
	}

	@Override
	public void setAfterFocusOnChange(String afterFocusOnChange) {
		getStateHelper().put(AFTER_FOCUS_ON_CHANGE, afterFocusOnChange);
	}

	@Override
	public String getAfterFooterContentChange() {
		return (String) getStateHelper().eval(AFTER_FOOTER_CONTENT_CHANGE, null);
	}

	@Override
	public void setAfterFooterContentChange(String afterFooterContentChange) {
		getStateHelper().put(AFTER_FOOTER_CONTENT_CHANGE, afterFooterContentChange);
	}

	@Override
	public String getAfterHeaderContentChange() {
		return (String) getStateHelper().eval(AFTER_HEADER_CONTENT_CHANGE, null);
	}

	@Override
	public void setAfterHeaderContentChange(String afterHeaderContentChange) {
		getStateHelper().put(AFTER_HEADER_CONTENT_CHANGE, afterHeaderContentChange);
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
	public String getAfterHideOnChange() {
		return (String) getStateHelper().eval(AFTER_HIDE_ON_CHANGE, null);
	}

	@Override
	public void setAfterHideOnChange(String afterHideOnChange) {
		getStateHelper().put(AFTER_HIDE_ON_CHANGE, afterHideOnChange);
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
	public String getAfterMaskNodeChange() {
		return (String) getStateHelper().eval(AFTER_MASK_NODE_CHANGE, null);
	}

	@Override
	public void setAfterMaskNodeChange(String afterMaskNodeChange) {
		getStateHelper().put(AFTER_MASK_NODE_CHANGE, afterMaskNodeChange);
	}

	@Override
	public String getAfterModalChange() {
		return (String) getStateHelper().eval(AFTER_MODAL_CHANGE, null);
	}

	@Override
	public void setAfterModalChange(String afterModalChange) {
		getStateHelper().put(AFTER_MODAL_CHANGE, afterModalChange);
	}

	@Override
	public String getAfterPreventOverlapChange() {
		return (String) getStateHelper().eval(AFTER_PREVENT_OVERLAP_CHANGE, null);
	}

	@Override
	public void setAfterPreventOverlapChange(String afterPreventOverlapChange) {
		getStateHelper().put(AFTER_PREVENT_OVERLAP_CHANGE, afterPreventOverlapChange);
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
	public String getAfterShimChange() {
		return (String) getStateHelper().eval(AFTER_SHIM_CHANGE, null);
	}

	@Override
	public void setAfterShimChange(String afterShimChange) {
		getStateHelper().put(AFTER_SHIM_CHANGE, afterShimChange);
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
	public String getAfterToolbarPositionChange() {
		return (String) getStateHelper().eval(AFTER_TOOLBAR_POSITION_CHANGE, null);
	}

	@Override
	public void setAfterToolbarPositionChange(String afterToolbarPositionChange) {
		getStateHelper().put(AFTER_TOOLBAR_POSITION_CHANGE, afterToolbarPositionChange);
	}

	@Override
	public String getAfterToolbarsChange() {
		return (String) getStateHelper().eval(AFTER_TOOLBARS_CHANGE, null);
	}

	@Override
	public void setAfterToolbarsChange(String afterToolbarsChange) {
		getStateHelper().put(AFTER_TOOLBARS_CHANGE, afterToolbarsChange);
	}

	@Override
	public String getAfterTriggerToggleEventChange() {
		return (String) getStateHelper().eval(AFTER_TRIGGER_TOGGLE_EVENT_CHANGE, null);
	}

	@Override
	public void setAfterTriggerToggleEventChange(String afterTriggerToggleEventChange) {
		getStateHelper().put(AFTER_TRIGGER_TOGGLE_EVENT_CHANGE, afterTriggerToggleEventChange);
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
	public String getAfterXChange() {
		return (String) getStateHelper().eval(AFTER_XCHANGE, null);
	}

	@Override
	public void setAfterXChange(String afterXChange) {
		getStateHelper().put(AFTER_XCHANGE, afterXChange);
	}

	@Override
	public String getAfterXyChange() {
		return (String) getStateHelper().eval(AFTER_XY_CHANGE, null);
	}

	@Override
	public void setAfterXyChange(String afterXyChange) {
		getStateHelper().put(AFTER_XY_CHANGE, afterXyChange);
	}

	@Override
	public String getAfterYChange() {
		return (String) getStateHelper().eval(AFTER_YCHANGE, null);
	}

	@Override
	public void setAfterYChange(String afterYChange) {
		getStateHelper().put(AFTER_YCHANGE, afterYChange);
	}

	@Override
	public String getAfterZIndexChange() {
		return (String) getStateHelper().eval(AFTER_ZINDEX_CHANGE, null);
	}

	@Override
	public void setAfterZIndexChange(String afterZIndexChange) {
		getStateHelper().put(AFTER_ZINDEX_CHANGE, afterZIndexChange);
	}

	@Override
	public Object getAlign() {
		return (Object) getStateHelper().eval(ALIGN, null);
	}

	@Override
	public void setAlign(Object align) {
		getStateHelper().put(ALIGN, align);
	}

	@Override
	public Object getAlignOn() {
		return (Object) getStateHelper().eval(ALIGN_ON, null);
	}

	@Override
	public void setAlignOn(Object alignOn) {
		getStateHelper().put(ALIGN_ON, alignOn);
	}

	@Override
	public Boolean isAnimate() {
		return (Boolean) getStateHelper().eval(ANIMATE, null);
	}

	@Override
	public void setAnimate(Boolean animate) {
		getStateHelper().put(ANIMATE, animate);
	}

	@Override
	public String getBodyContent() {
		return (String) getStateHelper().eval(BODY_CONTENT, null);
	}

	@Override
	public void setBodyContent(String bodyContent) {
		getStateHelper().put(BODY_CONTENT, bodyContent);
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
	public Boolean isCentered() {
		return (Boolean) getStateHelper().eval(CENTERED, null);
	}

	@Override
	public void setCentered(Boolean centered) {
		getStateHelper().put(CENTERED, centered);
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
	public Boolean isConstrain() {
		return (Boolean) getStateHelper().eval(CONSTRAIN, null);
	}

	@Override
	public void setConstrain(Boolean constrain) {
		getStateHelper().put(CONSTRAIN, constrain);
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
	public Boolean isDisabled() {
		return (Boolean) getStateHelper().eval(DISABLED, null);
	}

	@Override
	public void setDisabled(Boolean disabled) {
		getStateHelper().put(DISABLED, disabled);
	}

	@Override
	public String getFillHeight() {
		return (String) getStateHelper().eval(FILL_HEIGHT, null);
	}

	@Override
	public void setFillHeight(String fillHeight) {
		getStateHelper().put(FILL_HEIGHT, fillHeight);
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
	public Object getFocusOn() {
		return (Object) getStateHelper().eval(FOCUS_ON, null);
	}

	@Override
	public void setFocusOn(Object focusOn) {
		getStateHelper().put(FOCUS_ON, focusOn);
	}

	@Override
	public String getFooterContent() {
		return (String) getStateHelper().eval(FOOTER_CONTENT, null);
	}

	@Override
	public void setFooterContent(String footerContent) {
		getStateHelper().put(FOOTER_CONTENT, footerContent);
	}

	@Override
	public String getFor() {
		return (String) getStateHelper().eval(FOR, null);
	}

	@Override
	public void setFor(String for_) {
		getStateHelper().put(FOR, for_);
	}

	@Override
	public String getHeaderContent() {
		return (String) getStateHelper().eval(HEADER_CONTENT, null);
	}

	@Override
	public void setHeaderContent(String headerContent) {
		getStateHelper().put(HEADER_CONTENT, headerContent);
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
	public Object getHideOn() {
		return (Object) getStateHelper().eval(HIDE_ON, null);
	}

	@Override
	public void setHideOn(Object hideOn) {
		getStateHelper().put(HIDE_ON, hideOn);
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
	public String getMaskNode() {
		return (String) getStateHelper().eval(MASK_NODE, null);
	}

	@Override
	public void setMaskNode(String maskNode) {
		getStateHelper().put(MASK_NODE, maskNode);
	}

	@Override
	public Boolean isModal() {
		return (Boolean) getStateHelper().eval(MODAL, null);
	}

	@Override
	public void setModal(Boolean modal) {
		getStateHelper().put(MODAL, modal);
	}

	@Override
	public String getOnAlignChange() {
		return (String) getStateHelper().eval(ON_ALIGN_CHANGE, null);
	}

	@Override
	public void setOnAlignChange(String onAlignChange) {
		getStateHelper().put(ON_ALIGN_CHANGE, onAlignChange);
	}

	@Override
	public String getOnAlignOnChange() {
		return (String) getStateHelper().eval(ON_ALIGN_ON_CHANGE, null);
	}

	@Override
	public void setOnAlignOnChange(String onAlignOnChange) {
		getStateHelper().put(ON_ALIGN_ON_CHANGE, onAlignOnChange);
	}

	@Override
	public String getOnBodyContentChange() {
		return (String) getStateHelper().eval(ON_BODY_CONTENT_CHANGE, null);
	}

	@Override
	public void setOnBodyContentChange(String onBodyContentChange) {
		getStateHelper().put(ON_BODY_CONTENT_CHANGE, onBodyContentChange);
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
	public String getOnCenteredChange() {
		return (String) getStateHelper().eval(ON_CENTERED_CHANGE, null);
	}

	@Override
	public void setOnCenteredChange(String onCenteredChange) {
		getStateHelper().put(ON_CENTERED_CHANGE, onCenteredChange);
	}

	@Override
	public String getOnConstrainChange() {
		return (String) getStateHelper().eval(ON_CONSTRAIN_CHANGE, null);
	}

	@Override
	public void setOnConstrainChange(String onConstrainChange) {
		getStateHelper().put(ON_CONSTRAIN_CHANGE, onConstrainChange);
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
	public String getOnFillHeightChange() {
		return (String) getStateHelper().eval(ON_FILL_HEIGHT_CHANGE, null);
	}

	@Override
	public void setOnFillHeightChange(String onFillHeightChange) {
		getStateHelper().put(ON_FILL_HEIGHT_CHANGE, onFillHeightChange);
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
	public String getOnFocusOnChange() {
		return (String) getStateHelper().eval(ON_FOCUS_ON_CHANGE, null);
	}

	@Override
	public void setOnFocusOnChange(String onFocusOnChange) {
		getStateHelper().put(ON_FOCUS_ON_CHANGE, onFocusOnChange);
	}

	@Override
	public String getOnFooterContentChange() {
		return (String) getStateHelper().eval(ON_FOOTER_CONTENT_CHANGE, null);
	}

	@Override
	public void setOnFooterContentChange(String onFooterContentChange) {
		getStateHelper().put(ON_FOOTER_CONTENT_CHANGE, onFooterContentChange);
	}

	@Override
	public String getOnHeaderContentChange() {
		return (String) getStateHelper().eval(ON_HEADER_CONTENT_CHANGE, null);
	}

	@Override
	public void setOnHeaderContentChange(String onHeaderContentChange) {
		getStateHelper().put(ON_HEADER_CONTENT_CHANGE, onHeaderContentChange);
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
	public String getOnHideOnChange() {
		return (String) getStateHelper().eval(ON_HIDE_ON_CHANGE, null);
	}

	@Override
	public void setOnHideOnChange(String onHideOnChange) {
		getStateHelper().put(ON_HIDE_ON_CHANGE, onHideOnChange);
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
	public String getOnMaskNodeChange() {
		return (String) getStateHelper().eval(ON_MASK_NODE_CHANGE, null);
	}

	@Override
	public void setOnMaskNodeChange(String onMaskNodeChange) {
		getStateHelper().put(ON_MASK_NODE_CHANGE, onMaskNodeChange);
	}

	@Override
	public String getOnModalChange() {
		return (String) getStateHelper().eval(ON_MODAL_CHANGE, null);
	}

	@Override
	public void setOnModalChange(String onModalChange) {
		getStateHelper().put(ON_MODAL_CHANGE, onModalChange);
	}

	@Override
	public String getOnPreventOverlapChange() {
		return (String) getStateHelper().eval(ON_PREVENT_OVERLAP_CHANGE, null);
	}

	@Override
	public void setOnPreventOverlapChange(String onPreventOverlapChange) {
		getStateHelper().put(ON_PREVENT_OVERLAP_CHANGE, onPreventOverlapChange);
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
	public String getOnShimChange() {
		return (String) getStateHelper().eval(ON_SHIM_CHANGE, null);
	}

	@Override
	public void setOnShimChange(String onShimChange) {
		getStateHelper().put(ON_SHIM_CHANGE, onShimChange);
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
	public String getOnToolbarPositionChange() {
		return (String) getStateHelper().eval(ON_TOOLBAR_POSITION_CHANGE, null);
	}

	@Override
	public void setOnToolbarPositionChange(String onToolbarPositionChange) {
		getStateHelper().put(ON_TOOLBAR_POSITION_CHANGE, onToolbarPositionChange);
	}

	@Override
	public String getOnToolbarsChange() {
		return (String) getStateHelper().eval(ON_TOOLBARS_CHANGE, null);
	}

	@Override
	public void setOnToolbarsChange(String onToolbarsChange) {
		getStateHelper().put(ON_TOOLBARS_CHANGE, onToolbarsChange);
	}

	@Override
	public String getOnTriggerToggleEventChange() {
		return (String) getStateHelper().eval(ON_TRIGGER_TOGGLE_EVENT_CHANGE, null);
	}

	@Override
	public void setOnTriggerToggleEventChange(String onTriggerToggleEventChange) {
		getStateHelper().put(ON_TRIGGER_TOGGLE_EVENT_CHANGE, onTriggerToggleEventChange);
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
	public String getOnXChange() {
		return (String) getStateHelper().eval(ON_XCHANGE, null);
	}

	@Override
	public void setOnXChange(String onXChange) {
		getStateHelper().put(ON_XCHANGE, onXChange);
	}

	@Override
	public String getOnXyChange() {
		return (String) getStateHelper().eval(ON_XY_CHANGE, null);
	}

	@Override
	public void setOnXyChange(String onXyChange) {
		getStateHelper().put(ON_XY_CHANGE, onXyChange);
	}

	@Override
	public String getOnYChange() {
		return (String) getStateHelper().eval(ON_YCHANGE, null);
	}

	@Override
	public void setOnYChange(String onYChange) {
		getStateHelper().put(ON_YCHANGE, onYChange);
	}

	@Override
	public String getOnZIndexChange() {
		return (String) getStateHelper().eval(ON_ZINDEX_CHANGE, null);
	}

	@Override
	public void setOnZIndexChange(String onZIndexChange) {
		getStateHelper().put(ON_ZINDEX_CHANGE, onZIndexChange);
	}

	@Override
	public Object getPlugins() {
		return (Object) getStateHelper().eval(PLUGINS, null);
	}

	@Override
	public void setPlugins(Object plugins) {
		getStateHelper().put(PLUGINS, plugins);
	}

	@Override
	public String getPosition() {
		return (String) getStateHelper().eval(POSITION, null);
	}

	@Override
	public void setPosition(String position) {
		getStateHelper().put(POSITION, position);
	}

	@Override
	public Boolean isPreventOverlap() {
		return (Boolean) getStateHelper().eval(PREVENT_OVERLAP, null);
	}

	@Override
	public void setPreventOverlap(Boolean preventOverlap) {
		getStateHelper().put(PREVENT_OVERLAP, preventOverlap);
	}

	@Override
	public Boolean isShim() {
		return (Boolean) getStateHelper().eval(SHIM, null);
	}

	@Override
	public void setShim(Boolean shim) {
		getStateHelper().put(SHIM, shim);
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
	public Object getToolbarPosition() {
		return (Object) getStateHelper().eval(TOOLBAR_POSITION, null);
	}

	@Override
	public void setToolbarPosition(Object toolbarPosition) {
		getStateHelper().put(TOOLBAR_POSITION, toolbarPosition);
	}

	@Override
	public Object getToolbars() {
		return (Object) getStateHelper().eval(TOOLBARS, null);
	}

	@Override
	public void setToolbars(Object toolbars) {
		getStateHelper().put(TOOLBARS, toolbars);
	}

	@Override
	public String getTriggerToggleEvent() {
		return (String) getStateHelper().eval(TRIGGER_TOGGLE_EVENT, null);
	}

	@Override
	public void setTriggerToggleEvent(String triggerToggleEvent) {
		getStateHelper().put(TRIGGER_TOGGLE_EVENT, triggerToggleEvent);
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

	@Override
	public Object getX() {
		return (Object) getStateHelper().eval(X, null);
	}

	@Override
	public void setX(Object x) {
		getStateHelper().put(X, x);
	}

	@Override
	public Object getXy() {
		return (Object) getStateHelper().eval(XY, null);
	}

	@Override
	public void setXy(Object xy) {
		getStateHelper().put(XY, xy);
	}

	@Override
	public Object getY() {
		return (Object) getStateHelper().eval(Y, null);
	}

	@Override
	public void setY(Object y) {
		getStateHelper().put(Y, y);
	}

	@Override
	public Object getzIndex() {
		return (Object) getStateHelper().eval(Z_INDEX, null);
	}

	@Override
	public void setzIndex(Object zIndex) {
		getStateHelper().put(Z_INDEX, zIndex);
	}
}
//J+
