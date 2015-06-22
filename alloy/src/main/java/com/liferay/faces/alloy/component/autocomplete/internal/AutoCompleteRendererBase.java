/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.alloy.component.autocomplete.internal;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.autocomplete.AutoComplete;

import com.liferay.faces.alloy.render.internal.DelegatingAlloyRendererBase;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class AutoCompleteRendererBase extends DelegatingAlloyRendererBase {

	// Protected Constants
	protected static final String ACTIVATE_FIRST_ITEM = "activateFirstItem";
	protected static final String CIRCULAR = "circular";
	protected static final String CLIENT_FILTER_TYPE = "clientFilterType";
	protected static final String CLIENT_KEY = "clientKey";
	protected static final String HEIGHT = "height";
	protected static final String LIST_ITEM_REQUIRED = "listItemRequired";
	protected static final String MAX_RESULTS = "maxResults";
	protected static final String MIN_QUERY_LENGTH = "minQueryLength";
	protected static final String QUERY_DELAY = "queryDelay";
	protected static final String QUERY_DELIMITER = "queryDelimiter";
	protected static final String RESULT_FILTERS = "resultFilters";
	protected static final String RESULT_HIGHLIGHTER = "resultHighlighter";
	protected static final String SCROLL_INTO_VIEW = "scrollIntoView";
	protected static final String SERVER_CUSTOM_FILTER = "serverCustomFilter";
	protected static final String SERVER_FILTER_TYPE = "serverFilterType";
	protected static final String STYLE_CLASS = "styleClass";
	protected static final String TAB_SELECT = "tabSelect";
	protected static final String WIDTH = "width";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "AutoComplete";
	private static final String ALLOY_MODULE_NAME = "autocomplete";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		AutoComplete autoComplete = (AutoComplete) uiComponent;
		boolean first = true;

		Boolean activateFirstItem = autoComplete.isActivateFirstItem();

		if (activateFirstItem != null) {

			encodeActivateFirstItem(responseWriter, autoComplete, activateFirstItem, first);
			first = false;
		}

		Boolean autoScroll = autoComplete.getAutoScroll();

		if (autoScroll != null) {

			encodeScrollIntoView(responseWriter, autoComplete, autoScroll, first);
			first = false;
		}

		Boolean circular = autoComplete.getCircular();

		if (circular != null) {

			encodeCircular(responseWriter, autoComplete, circular, first);
			first = false;
		}

		String clientCustomFilter = autoComplete.getClientCustomFilter();

		if (clientCustomFilter != null) {

			encodeResultFilters(responseWriter, autoComplete, clientCustomFilter, first);
			first = false;
		}

		String clientFilterType = autoComplete.getClientFilterType();

		if (clientFilterType != null) {

			encodeClientFilterType(responseWriter, autoComplete, clientFilterType, first);
			first = false;
		}

		Integer delay = autoComplete.getDelay();

		if (delay != null) {

			encodeQueryDelay(responseWriter, autoComplete, delay, first);
			first = false;
		}

		String delimiter = autoComplete.getDelimiter();

		if (delimiter != null) {

			encodeQueryDelimiter(responseWriter, autoComplete, delimiter, first);
			first = false;
		}

		String height = autoComplete.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, autoComplete, height, first);
			first = false;
		}

		String highlighterType = autoComplete.getHighlighterType();

		if (highlighterType != null) {

			encodeResultHighlighter(responseWriter, autoComplete, highlighterType, first);
			first = false;
		}

		Boolean listItemRequired = autoComplete.isListItemRequired();

		if (listItemRequired != null) {

			encodeListItemRequired(responseWriter, autoComplete, listItemRequired, first);
			first = false;
		}

		Integer maxItems = autoComplete.getMaxItems();

		if (maxItems != null) {

			encodeMaxResults(responseWriter, autoComplete, maxItems, first);
			first = false;
		}

		Integer minChars = autoComplete.getMinChars();

		if (minChars != null) {

			encodeMinQueryLength(responseWriter, autoComplete, minChars, first);
			first = false;
		}

		Boolean tabSelect = autoComplete.isTabSelect();

		if (tabSelect != null) {

			encodeTabSelect(responseWriter, autoComplete, tabSelect, first);
			first = false;
		}

		String width = autoComplete.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, autoComplete, width, first);
			first = false;
		}

		encodeHiddenAttributes(facesContext, responseWriter, autoComplete, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	protected void encodeActivateFirstItem(ResponseWriter responseWriter, AutoComplete autoComplete, Boolean activateFirstItem, boolean first) throws IOException {
		encodeBoolean(responseWriter, ACTIVATE_FIRST_ITEM, activateFirstItem, first);
	}

	protected void encodeScrollIntoView(ResponseWriter responseWriter, AutoComplete autoComplete, Boolean autoScroll, boolean first) throws IOException {
		encodeBoolean(responseWriter, SCROLL_INTO_VIEW, autoScroll, first);
	}

	protected void encodeCircular(ResponseWriter responseWriter, AutoComplete autoComplete, Boolean circular, boolean first) throws IOException {
		encodeBoolean(responseWriter, CIRCULAR, circular, first);
	}

	protected void encodeResultFilters(ResponseWriter responseWriter, AutoComplete autoComplete, String clientCustomFilter, boolean first) throws IOException {
		encodeString(responseWriter, RESULT_FILTERS, clientCustomFilter, first);
	}

	protected void encodeClientFilterType(ResponseWriter responseWriter, AutoComplete autoComplete, String clientFilterType, boolean first) throws IOException {
		encodeString(responseWriter, CLIENT_FILTER_TYPE, clientFilterType, first);
	}

	protected void encodeQueryDelay(ResponseWriter responseWriter, AutoComplete autoComplete, Integer delay, boolean first) throws IOException {
		encodeInteger(responseWriter, QUERY_DELAY, delay, first);
	}

	protected void encodeQueryDelimiter(ResponseWriter responseWriter, AutoComplete autoComplete, String delimiter, boolean first) throws IOException {
		encodeString(responseWriter, QUERY_DELIMITER, delimiter, first);
	}

	protected void encodeHeight(ResponseWriter responseWriter, AutoComplete autoComplete, String height, boolean first) throws IOException {
		encodeString(responseWriter, HEIGHT, height, first);
	}

	protected void encodeResultHighlighter(ResponseWriter responseWriter, AutoComplete autoComplete, String highlighterType, boolean first) throws IOException {
		encodeString(responseWriter, RESULT_HIGHLIGHTER, highlighterType, first);
	}

	protected void encodeListItemRequired(ResponseWriter responseWriter, AutoComplete autoComplete, Boolean listItemRequired, boolean first) throws IOException {
		encodeBoolean(responseWriter, LIST_ITEM_REQUIRED, listItemRequired, first);
	}

	protected void encodeMaxResults(ResponseWriter responseWriter, AutoComplete autoComplete, Integer maxItems, boolean first) throws IOException {
		encodeInteger(responseWriter, MAX_RESULTS, maxItems, first);
	}

	protected void encodeMinQueryLength(ResponseWriter responseWriter, AutoComplete autoComplete, Integer minChars, boolean first) throws IOException {
		encodeInteger(responseWriter, MIN_QUERY_LENGTH, minChars, first);
	}

	protected void encodeTabSelect(ResponseWriter responseWriter, AutoComplete autoComplete, Boolean tabSelect, boolean first) throws IOException {
		encodeBoolean(responseWriter, TAB_SELECT, tabSelect, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, AutoComplete autoComplete, String width, boolean first) throws IOException {
		encodeString(responseWriter, WIDTH, width, first);
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, AutoComplete autoComplete, boolean first) throws IOException {
		// no-op
	}

	@Override
	public String getDelegateComponentFamily() {
		return AutoComplete.COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return "javax.faces.Text";
	}
}
//J+
