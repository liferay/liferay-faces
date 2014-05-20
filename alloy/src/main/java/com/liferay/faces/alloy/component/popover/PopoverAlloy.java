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

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface PopoverAlloy {

	// Public Constants
	public static final String AFTER_ALIGN_CHANGE = "afterAlignChange";
	public static final String AFTER_ALIGN_ON_CHANGE = "afterAlignOnChange";
	public static final String AFTER_BODY_CONTENT_CHANGE = "afterBodyContentChange";
	public static final String AFTER_BOUNDING_BOX_CHANGE = "afterBoundingBoxChange";
	public static final String AFTER_CENTERED_CHANGE = "afterCenteredChange";
	public static final String AFTER_CONSTRAIN_CHANGE = "afterConstrainChange";
	public static final String AFTER_CONTENT_BOX_CHANGE = "afterContentBoxChange";
	public static final String AFTER_DESTROYED_CHANGE = "afterDestroyedChange";
	public static final String AFTER_DISABLED_CHANGE = "afterDisabledChange";
	public static final String AFTER_FILL_HEIGHT_CHANGE = "afterFillHeightChange";
	public static final String AFTER_FOCUS_ON_CHANGE = "afterFocusOnChange";
	public static final String AFTER_FOCUSED_CHANGE = "afterFocusedChange";
	public static final String AFTER_FOOTER_CONTENT_CHANGE = "afterFooterContentChange";
	public static final String AFTER_HEADER_CONTENT_CHANGE = "afterHeaderContentChange";
	public static final String AFTER_HEIGHT_CHANGE = "afterHeightChange";
	public static final String AFTER_HIDE_ON_CHANGE = "afterHideOnChange";
	public static final String AFTER_ID_CHANGE = "afterIdChange";
	public static final String AFTER_INITIALIZED_CHANGE = "afterInitializedChange";
	public static final String AFTER_LOCALE_CHANGE = "afterLocaleChange";
	public static final String AFTER_MASK_NODE_CHANGE = "afterMaskNodeChange";
	public static final String AFTER_MODAL_CHANGE = "afterModalChange";
	public static final String AFTER_PREVENT_OVERLAP_CHANGE = "afterPreventOverlapChange";
	public static final String AFTER_RENDER_CHANGE = "afterRenderChange";
	public static final String AFTER_RENDERED_CHANGE = "afterRenderedChange";
	public static final String AFTER_SHIM_CHANGE = "afterShimChange";
	public static final String AFTER_SRC_NODE_CHANGE = "afterSrcNodeChange";
	public static final String AFTER_STRINGS_CHANGE = "afterStringsChange";
	public static final String AFTER_TAB_INDEX_CHANGE = "afterTabIndexChange";
	public static final String AFTER_TOOLBAR_POSITION_CHANGE = "afterToolbarPositionChange";
	public static final String AFTER_TOOLBARS_CHANGE = "afterToolbarsChange";
	public static final String AFTER_TRIGGER_TOGGLE_EVENT_CHANGE = "afterTriggerToggleEventChange";
	public static final String AFTER_VISIBLE_CHANGE = "afterVisibleChange";
	public static final String AFTER_WIDTH_CHANGE = "afterWidthChange";
	public static final String AFTER_XCHANGE = "afterXChange";
	public static final String AFTER_XY_CHANGE = "afterXyChange";
	public static final String AFTER_YCHANGE = "afterYChange";
	public static final String AFTER_ZINDEX_CHANGE = "afterZIndexChange";
	public static final String ALIGN = "align";
	public static final String ALIGN_ON = "alignOn";
	public static final String BODY_CONTENT = "bodyContent";
	public static final String BOUNDING_BOX = "boundingBox";
	public static final String CENTERED = "centered";
	public static final String CONSTRAIN = "constrain";
	public static final String CONTENT_BOX = "contentBox";
	public static final String DISABLED = "disabled";
	public static final String FILL_HEIGHT = "fillHeight";
	public static final String FOCUS_ON = "focusOn";
	public static final String FOOTER_CONTENT = "footerContent";
	public static final String FOR = "for";
	public static final String HEADER_CONTENT = "headerContent";
	public static final String HEIGHT = "height";
	public static final String HIDE_ON = "hideOn";
	public static final String LOCALE = "locale";
	public static final String MASK_NODE = "maskNode";
	public static final String MODAL = "modal";
	public static final String ON_ALIGN_CHANGE = "onAlignChange";
	public static final String ON_ALIGN_ON_CHANGE = "onAlignOnChange";
	public static final String ON_BODY_CONTENT_CHANGE = "onBodyContentChange";
	public static final String ON_BOUNDING_BOX_CHANGE = "onBoundingBoxChange";
	public static final String ON_CENTERED_CHANGE = "onCenteredChange";
	public static final String ON_CONSTRAIN_CHANGE = "onConstrainChange";
	public static final String ON_CONTENT_BOX_CHANGE = "onContentBoxChange";
	public static final String ON_DESTROYED_CHANGE = "onDestroyedChange";
	public static final String ON_DISABLED_CHANGE = "onDisabledChange";
	public static final String ON_FILL_HEIGHT_CHANGE = "onFillHeightChange";
	public static final String ON_FOCUS_ON_CHANGE = "onFocusOnChange";
	public static final String ON_FOCUSED_CHANGE = "onFocusedChange";
	public static final String ON_FOOTER_CONTENT_CHANGE = "onFooterContentChange";
	public static final String ON_HEADER_CONTENT_CHANGE = "onHeaderContentChange";
	public static final String ON_HEIGHT_CHANGE = "onHeightChange";
	public static final String ON_HIDE_ON_CHANGE = "onHideOnChange";
	public static final String ON_ID_CHANGE = "onIdChange";
	public static final String ON_INITIALIZED_CHANGE = "onInitializedChange";
	public static final String ON_LOCALE_CHANGE = "onLocaleChange";
	public static final String ON_MASK_NODE_CHANGE = "onMaskNodeChange";
	public static final String ON_MODAL_CHANGE = "onModalChange";
	public static final String ON_PREVENT_OVERLAP_CHANGE = "onPreventOverlapChange";
	public static final String ON_RENDER_CHANGE = "onRenderChange";
	public static final String ON_RENDERED_CHANGE = "onRenderedChange";
	public static final String ON_SHIM_CHANGE = "onShimChange";
	public static final String ON_SRC_NODE_CHANGE = "onSrcNodeChange";
	public static final String ON_STRINGS_CHANGE = "onStringsChange";
	public static final String ON_TAB_INDEX_CHANGE = "onTabIndexChange";
	public static final String ON_TOOLBAR_POSITION_CHANGE = "onToolbarPositionChange";
	public static final String ON_TOOLBARS_CHANGE = "onToolbarsChange";
	public static final String ON_TRIGGER_TOGGLE_EVENT_CHANGE = "onTriggerToggleEventChange";
	public static final String ON_VISIBLE_CHANGE = "onVisibleChange";
	public static final String ON_WIDTH_CHANGE = "onWidthChange";
	public static final String ON_XCHANGE = "onXChange";
	public static final String ON_XY_CHANGE = "onXyChange";
	public static final String ON_YCHANGE = "onYChange";
	public static final String ON_ZINDEX_CHANGE = "onZIndexChange";
	public static final String PLUGINS = "plugins";
	public static final String POSITION = "position";
	public static final String PREVENT_OVERLAP = "preventOverlap";
	public static final String SHIM = "shim";
	public static final String SRC_NODE = "srcNode";
	public static final String STRINGS = "strings";
	public static final String TAB_INDEX = "tabIndex";
	public static final String TOOLBAR_POSITION = "toolbarPosition";
	public static final String TOOLBARS = "toolbars";
	public static final String TRIGGER_TOGGLE_EVENT = "triggerToggleEvent";
	public static final String VISIBLE = "visible";
	public static final String WIDGET_ID = "id";
	public static final String WIDGET_RENDER = "render";
	public static final String WIDTH = "width";
	public static final String X = "x";
	public static final String XY = "xy";
	public static final String Y = "y";
	public static final String Z_INDEX = "zIndex";

	public String getAfterAlignChange();

	public void setAfterAlignChange(String afterAlignChange);

	public String getAfterAlignOnChange();

	public void setAfterAlignOnChange(String afterAlignOnChange);

	public String getAfterBodyContentChange();

	public void setAfterBodyContentChange(String afterBodyContentChange);

	public String getAfterBoundingBoxChange();

	public void setAfterBoundingBoxChange(String afterBoundingBoxChange);

	public String getAfterCenteredChange();

	public void setAfterCenteredChange(String afterCenteredChange);

	public String getAfterConstrainChange();

	public void setAfterConstrainChange(String afterConstrainChange);

	public String getAfterContentBoxChange();

	public void setAfterContentBoxChange(String afterContentBoxChange);

	public String getAfterDestroyedChange();

	public void setAfterDestroyedChange(String afterDestroyedChange);

	public String getAfterDisabledChange();

	public void setAfterDisabledChange(String afterDisabledChange);

	public String getAfterFillHeightChange();

	public void setAfterFillHeightChange(String afterFillHeightChange);

	public String getAfterFocusedChange();

	public void setAfterFocusedChange(String afterFocusedChange);

	public String getAfterFocusOnChange();

	public void setAfterFocusOnChange(String afterFocusOnChange);

	public String getAfterFooterContentChange();

	public void setAfterFooterContentChange(String afterFooterContentChange);

	public String getAfterHeaderContentChange();

	public void setAfterHeaderContentChange(String afterHeaderContentChange);

	public String getAfterHeightChange();

	public void setAfterHeightChange(String afterHeightChange);

	public String getAfterHideOnChange();

	public void setAfterHideOnChange(String afterHideOnChange);

	public String getAfterIdChange();

	public void setAfterIdChange(String afterIdChange);

	public String getAfterInitializedChange();

	public void setAfterInitializedChange(String afterInitializedChange);

	public String getAfterLocaleChange();

	public void setAfterLocaleChange(String afterLocaleChange);

	public String getAfterMaskNodeChange();

	public void setAfterMaskNodeChange(String afterMaskNodeChange);

	public String getAfterModalChange();

	public void setAfterModalChange(String afterModalChange);

	public String getAfterPreventOverlapChange();

	public void setAfterPreventOverlapChange(String afterPreventOverlapChange);

	public String getAfterRenderChange();

	public void setAfterRenderChange(String afterRenderChange);

	public String getAfterRenderedChange();

	public void setAfterRenderedChange(String afterRenderedChange);

	public String getAfterShimChange();

	public void setAfterShimChange(String afterShimChange);

	public String getAfterSrcNodeChange();

	public void setAfterSrcNodeChange(String afterSrcNodeChange);

	public String getAfterStringsChange();

	public void setAfterStringsChange(String afterStringsChange);

	public String getAfterTabIndexChange();

	public void setAfterTabIndexChange(String afterTabIndexChange);

	public String getAfterToolbarPositionChange();

	public void setAfterToolbarPositionChange(String afterToolbarPositionChange);

	public String getAfterToolbarsChange();

	public void setAfterToolbarsChange(String afterToolbarsChange);

	public String getAfterTriggerToggleEventChange();

	public void setAfterTriggerToggleEventChange(String afterTriggerToggleEventChange);

	public String getAfterVisibleChange();

	public void setAfterVisibleChange(String afterVisibleChange);

	public String getAfterWidthChange();

	public void setAfterWidthChange(String afterWidthChange);

	public String getAfterXChange();

	public void setAfterXChange(String afterXChange);

	public String getAfterXyChange();

	public void setAfterXyChange(String afterXyChange);

	public String getAfterYChange();

	public void setAfterYChange(String afterYChange);

	public String getAfterZIndexChange();

	public void setAfterZIndexChange(String afterZIndexChange);

	public Object getAlign();

	public void setAlign(Object align);

	public Object getAlignOn();

	public void setAlignOn(Object alignOn);

	public String getBodyContent();

	public void setBodyContent(String bodyContent);

	public String getBoundingBox();

	public void setBoundingBox(String boundingBox);

	public Boolean isCentered();

	public void setCentered(Boolean centered);

	public Boolean isConstrain();

	public void setConstrain(Boolean constrain);

	public String getContentBox();

	public void setContentBox(String contentBox);

	public Boolean isDisabled();

	public void setDisabled(Boolean disabled);

	public String getFillHeight();

	public void setFillHeight(String fillHeight);

	public Object getFocusOn();

	public void setFocusOn(Object focusOn);

	public String getFooterContent();

	public void setFooterContent(String footerContent);

	public String getFor();

	public void setFor(String for_);

	public String getHeaderContent();

	public void setHeaderContent(String headerContent);

	public Object getHeight();

	public void setHeight(Object height);

	public Object getHideOn();

	public void setHideOn(Object hideOn);

	public String getLocale();

	public void setLocale(String locale);

	public String getMaskNode();

	public void setMaskNode(String maskNode);

	public Boolean isModal();

	public void setModal(Boolean modal);

	public String getOnAlignChange();

	public void setOnAlignChange(String onAlignChange);

	public String getOnAlignOnChange();

	public void setOnAlignOnChange(String onAlignOnChange);

	public String getOnBodyContentChange();

	public void setOnBodyContentChange(String onBodyContentChange);

	public String getOnBoundingBoxChange();

	public void setOnBoundingBoxChange(String onBoundingBoxChange);

	public String getOnCenteredChange();

	public void setOnCenteredChange(String onCenteredChange);

	public String getOnConstrainChange();

	public void setOnConstrainChange(String onConstrainChange);

	public String getOnContentBoxChange();

	public void setOnContentBoxChange(String onContentBoxChange);

	public String getOnDestroyedChange();

	public void setOnDestroyedChange(String onDestroyedChange);

	public String getOnDisabledChange();

	public void setOnDisabledChange(String onDisabledChange);

	public String getOnFillHeightChange();

	public void setOnFillHeightChange(String onFillHeightChange);

	public String getOnFocusedChange();

	public void setOnFocusedChange(String onFocusedChange);

	public String getOnFocusOnChange();

	public void setOnFocusOnChange(String onFocusOnChange);

	public String getOnFooterContentChange();

	public void setOnFooterContentChange(String onFooterContentChange);

	public String getOnHeaderContentChange();

	public void setOnHeaderContentChange(String onHeaderContentChange);

	public String getOnHeightChange();

	public void setOnHeightChange(String onHeightChange);

	public String getOnHideOnChange();

	public void setOnHideOnChange(String onHideOnChange);

	public String getOnIdChange();

	public void setOnIdChange(String onIdChange);

	public String getOnInitializedChange();

	public void setOnInitializedChange(String onInitializedChange);

	public String getOnLocaleChange();

	public void setOnLocaleChange(String onLocaleChange);

	public String getOnMaskNodeChange();

	public void setOnMaskNodeChange(String onMaskNodeChange);

	public String getOnModalChange();

	public void setOnModalChange(String onModalChange);

	public String getOnPreventOverlapChange();

	public void setOnPreventOverlapChange(String onPreventOverlapChange);

	public String getOnRenderChange();

	public void setOnRenderChange(String onRenderChange);

	public String getOnRenderedChange();

	public void setOnRenderedChange(String onRenderedChange);

	public String getOnShimChange();

	public void setOnShimChange(String onShimChange);

	public String getOnSrcNodeChange();

	public void setOnSrcNodeChange(String onSrcNodeChange);

	public String getOnStringsChange();

	public void setOnStringsChange(String onStringsChange);

	public String getOnTabIndexChange();

	public void setOnTabIndexChange(String onTabIndexChange);

	public String getOnToolbarPositionChange();

	public void setOnToolbarPositionChange(String onToolbarPositionChange);

	public String getOnToolbarsChange();

	public void setOnToolbarsChange(String onToolbarsChange);

	public String getOnTriggerToggleEventChange();

	public void setOnTriggerToggleEventChange(String onTriggerToggleEventChange);

	public String getOnVisibleChange();

	public void setOnVisibleChange(String onVisibleChange);

	public String getOnWidthChange();

	public void setOnWidthChange(String onWidthChange);

	public String getOnXChange();

	public void setOnXChange(String onXChange);

	public String getOnXyChange();

	public void setOnXyChange(String onXyChange);

	public String getOnYChange();

	public void setOnYChange(String onYChange);

	public String getOnZIndexChange();

	public void setOnZIndexChange(String onZIndexChange);

	public Object getPlugins();

	public void setPlugins(Object plugins);

	public String getPosition();

	public void setPosition(String position);

	public Boolean isPreventOverlap();

	public void setPreventOverlap(Boolean preventOverlap);

	public Boolean isShim();

	public void setShim(Boolean shim);

	public String getSrcNode();

	public void setSrcNode(String srcNode);

	public Object getStrings();

	public void setStrings(Object strings);

	public Object getTabIndex();

	public void setTabIndex(Object tabIndex);

	public Object getToolbarPosition();

	public void setToolbarPosition(Object toolbarPosition);

	public Object getToolbars();

	public void setToolbars(Object toolbars);

	public String getTriggerToggleEvent();

	public void setTriggerToggleEvent(String triggerToggleEvent);

	public Boolean isVisible();

	public void setVisible(Boolean visible);

	public String getWidgetId();

	public void setWidgetId(String widgetId);

	public Boolean isWidgetRender();

	public void setWidgetRender(Boolean widgetRender);

	public Object getWidth();

	public void setWidth(Object width);

	public Object getX();

	public void setX(Object x);

	public Object getXy();

	public void setXy(Object xy);

	public Object getY();

	public void setY(Object y);

	public Object getzIndex();

	public void setzIndex(Object zIndex);
}
//J+
