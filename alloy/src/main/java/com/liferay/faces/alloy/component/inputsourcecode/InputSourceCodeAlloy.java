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
package com.liferay.faces.alloy.component.inputsourcecode;
//J-

import javax.annotation.Generated;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface InputSourceCodeAlloy {

	// Public Constants
	public static final String BOUNDING_BOX = "boundingBox";
	public static final String CONTENT_BOX = "contentBox";
	public static final String DISABLED = "disabled";
	public static final String HEIGHT = "height";
	public static final String HIGHLIGHT_ACTIVE_LINE = "highlightActiveLine";
	public static final String LOCALE = "locale";
	public static final String MODE = "mode";
	public static final String READ_ONLY = "readOnly";
	public static final String SHOW_PRINT_MARGIN = "showPrintMargin";
	public static final String SRC_NODE = "srcNode";
	public static final String STRINGS = "strings";
	public static final String TAB_INDEX = "tabIndex";
	public static final String TAB_SIZE = "tabSize";
	public static final String USE_SOFT_TABS = "useSoftTabs";
	public static final String USE_WRAP_MODE = "useWrapMode";
	public static final String VALUE = "value";
	public static final String VISIBLE = "visible";
	public static final String WIDGET_ID = "id";
	public static final String WIDGET_RENDER = "render";
	public static final String WIDTH = "width";

	public String getBoundingBox();

	public void setBoundingBox(String boundingBox);

	public String getContentBox();

	public void setContentBox(String contentBox);

	public boolean isDisabled();

	public void setDisabled(boolean disabled);

	public Object getHeight();

	public void setHeight(Object height);

	public Boolean isHighlightActiveLine();

	public void setHighlightActiveLine(Boolean highlightActiveLine);

	public String getLocale();

	public void setLocale(String locale);

	public String getMode();

	public void setMode(String mode);

	public Boolean isReadOnly();

	public void setReadOnly(Boolean readOnly);

	public Boolean isShowPrintMargin();

	public void setShowPrintMargin(Boolean showPrintMargin);

	public String getSrcNode();

	public void setSrcNode(String srcNode);

	public Object getStrings();

	public void setStrings(Object strings);

	public Object getTabIndex();

	public void setTabIndex(Object tabIndex);

	public Object getTabSize();

	public void setTabSize(Object tabSize);

	public Boolean isUseSoftTabs();

	public void setUseSoftTabs(Boolean useSoftTabs);

	public Boolean isUseWrapMode();

	public void setUseWrapMode(Boolean useWrapMode);

	public Object getValue();

	public void setValue(Object value);

	public Boolean isVisible();

	public void setVisible(Boolean visible);

	public String getWidgetId();

	public void setWidgetId(String widgetId);

	public Boolean isWidgetRender();

	public void setWidgetRender(Boolean widgetRender);

	public Object getWidth();

	public void setWidth(Object width);
}
//J+
