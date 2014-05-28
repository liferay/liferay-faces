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
package com.liferay.faces.alloy.component.tabview;
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
public abstract class TabViewRendererBase extends AlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "TabView";
	private static final String ALLOY_MODULE_NAME = "aui-tabview";
	private static final String ACTIVE_DESCENDANT_CHANGE = "activeDescendantChange";
	private static final String BOUNDING_BOX_CHANGE = "boundingBoxChange";
	private static final String CONTENT_BOX_CHANGE = "contentBoxChange";
	private static final String DEFAULT_CHILD_TYPE_CHANGE = "defaultChildTypeChange";
	private static final String DESTROYED_CHANGE = "destroyedChange";
	private static final String DISABLED_CHANGE = "disabledChange";
	private static final String FOCUSED_CHANGE = "focusedChange";
	private static final String HEIGHT_CHANGE = "heightChange";
	private static final String ID_CHANGE = "idChange";
	private static final String INITIALIZED_CHANGE = "initializedChange";
	private static final String LOCALE_CHANGE = "localeChange";
	private static final String MULTIPLE_CHANGE = "multipleChange";
	private static final String RENDER_CHANGE = "renderChange";
	private static final String RENDERED_CHANGE = "renderedChange";
	private static final String SELECTION_CHANGE = "selectionChange";
	private static final String SRC_NODE_CHANGE = "srcNodeChange";
	private static final String STACKED_CHANGE = "stackedChange";
	private static final String STRINGS_CHANGE = "stringsChange";
	private static final String TAB_INDEX_CHANGE = "tabIndexChange";
	private static final String TYPE_CHANGE = "typeChange";
	private static final String VISIBLE_CHANGE = "visibleChange";
	private static final String WIDTH_CHANGE = "widthChange";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		TabViewAlloy tabViewAlloy = (TabViewAlloy) uiComponent;
		boolean first = true;

		String boundingBox = tabViewAlloy.getBoundingBox();

		if (boundingBox != null) {

			encodeBoundingBox(responseWriter, tabViewAlloy, boundingBox, first);
			first = false;
		}

		String contentBox = tabViewAlloy.getContentBox();

		if (contentBox != null) {

			encodeContentBox(responseWriter, tabViewAlloy, contentBox, first);
			first = false;
		}

		String defaultChildType = tabViewAlloy.getDefaultChildType();

		if (defaultChildType != null) {

			encodeDefaultChildType(responseWriter, tabViewAlloy, defaultChildType, first);
			first = false;
		}

		Boolean disabled = tabViewAlloy.isDisabled();

		if (disabled != null) {

			encodeDisabled(responseWriter, tabViewAlloy, disabled, first);
			first = false;
		}

		Object height = tabViewAlloy.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, tabViewAlloy, height, first);
			first = false;
		}

		String locale = tabViewAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, tabViewAlloy, locale, first);
			first = false;
		}

		Boolean multiple = tabViewAlloy.isMultiple();

		if (multiple != null) {

			encodeMultiple(responseWriter, tabViewAlloy, multiple, first);
			first = false;
		}

		String srcNode = tabViewAlloy.getSrcNode();

		if (srcNode != null) {

			encodeSrcNode(responseWriter, tabViewAlloy, srcNode, first);
			first = false;
		}

		Boolean stacked = tabViewAlloy.isStacked();

		if (stacked != null) {

			encodeStacked(responseWriter, tabViewAlloy, stacked, first);
			first = false;
		}

		Object strings = tabViewAlloy.getStrings();

		if (strings != null) {

			encodeStrings(responseWriter, tabViewAlloy, strings, first);
			first = false;
		}

		Object tabIndex = tabViewAlloy.getTabIndex();

		if (tabIndex != null) {

			encodeTabIndex(responseWriter, tabViewAlloy, tabIndex, first);
			first = false;
		}

		String type = tabViewAlloy.getType();

		if (type != null) {

			encodeType(responseWriter, tabViewAlloy, type, first);
			first = false;
		}

		Boolean visible = tabViewAlloy.isVisible();

		if (visible != null) {

			encodeVisible(responseWriter, tabViewAlloy, visible, first);
			first = false;
		}

		String widgetId = tabViewAlloy.getWidgetId();

		if (widgetId != null) {

			encodeWidgetId(responseWriter, tabViewAlloy, widgetId, first);
			first = false;
		}

		Boolean widgetRender = tabViewAlloy.isWidgetRender();

		if (widgetRender != null) {

			encodeWidgetRender(responseWriter, tabViewAlloy, widgetRender, first);
			first = false;
		}

		Object width = tabViewAlloy.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, tabViewAlloy, width, first);
			first = false;
		}
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeBoundingBox(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String boundingBox, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.BOUNDING_BOX, boundingBox, first);
	}

	protected void encodeContentBox(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String contentBox, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.CONTENT_BOX, contentBox, first);
	}

	protected void encodeDefaultChildType(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String defaultChildType, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.DEFAULT_CHILD_TYPE, defaultChildType, first);
	}

	protected void encodeDisabled(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean disabled, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.DISABLED, disabled, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Object height, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, TabViewAlloy.HEIGHT, height, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String locale, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.LOCALE, locale, first);
	}

	protected void encodeMultiple(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean multiple, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.MULTIPLE, multiple, first);
	}

	protected void encodeSrcNode(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String srcNode, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.SRC_NODE, srcNode, first);
	}

	protected void encodeStacked(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean stacked, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.STACKED, stacked, first);
	}

	protected void encodeStrings(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Object strings, boolean first) throws IOException {
		encodeObject(responseWriter, TabViewAlloy.STRINGS, strings, first);
	}

	protected void encodeTabIndex(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Object tabIndex, boolean first) throws IOException {
		encodeNumber(responseWriter, TabViewAlloy.TAB_INDEX, tabIndex, first);
	}

	protected void encodeType(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String type, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.TYPE, type, first);
	}

	protected void encodeVisible(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean visible, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.VISIBLE, visible, first);
	}

	protected void encodeWidgetId(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, String widgetId, boolean first) throws IOException {
		encodeString(responseWriter, TabViewAlloy.WIDGET_ID, widgetId, first);
	}

	protected void encodeWidgetRender(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Boolean widgetRender, boolean first) throws IOException {
		encodeBoolean(responseWriter, TabViewAlloy.WIDGET_RENDER, widgetRender, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, TabViewAlloy tabViewAlloy, Object width, boolean first) throws IOException {
		encodeComplexNumber(responseWriter, TabViewAlloy.WIDTH, width, first);
	}
}
//J+
