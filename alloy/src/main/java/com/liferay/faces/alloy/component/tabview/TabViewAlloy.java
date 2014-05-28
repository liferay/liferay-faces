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

import javax.annotation.Generated;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface TabViewAlloy {

	// Public Constants
	public static final String BOUNDING_BOX = "boundingBox";
	public static final String CONTENT_BOX = "contentBox";
	public static final String DEFAULT_CHILD_TYPE = "defaultChildType";
	public static final String DISABLED = "disabled";
	public static final String HEIGHT = "height";
	public static final String LOCALE = "locale";
	public static final String MULTIPLE = "multiple";
	public static final String SRC_NODE = "srcNode";
	public static final String STACKED = "stacked";
	public static final String STRINGS = "strings";
	public static final String TAB_INDEX = "tabIndex";
	public static final String TYPE = "type";
	public static final String VISIBLE = "visible";
	public static final String WIDGET_ID = "id";
	public static final String WIDGET_RENDER = "render";
	public static final String WIDTH = "width";

	public String getBoundingBox();

	public void setBoundingBox(String boundingBox);

	public String getContentBox();

	public void setContentBox(String contentBox);

	public String getDefaultChildType();

	public void setDefaultChildType(String defaultChildType);

	public Boolean isDisabled();

	public void setDisabled(Boolean disabled);

	public Object getHeight();

	public void setHeight(Object height);

	public String getLocale();

	public void setLocale(String locale);

	public Boolean isMultiple();

	public void setMultiple(Boolean multiple);

	public String getSrcNode();

	public void setSrcNode(String srcNode);

	public Boolean isStacked();

	public void setStacked(Boolean stacked);

	public Object getStrings();

	public void setStrings(Object strings);

	public Object getTabIndex();

	public void setTabIndex(Object tabIndex);

	public String getType();

	public void setType(String type);

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
