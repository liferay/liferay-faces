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
package com.liferay.faces.alloy.component.column;

import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = Column.COMPONENT_TYPE)
public class Column extends ColumnBase {

	// Public Constants
	public static final int COLUMNS = 12;
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.column.Column";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.column.internal.ColumnRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-column";

	public Column() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public void setParent(UIComponent parent) {
		super.setParent(parent);

		if ((parent != null) && (parent instanceof HtmlDataTable)) {

			Map<String, UIComponent> facetMap = getFacets();

			if (facetMap != null) {
				UIComponent headerFacet = facetMap.get(StringPool.HEADER);

				if (headerFacet == null) {
					facetMap.put(StringPool.HEADER, new ColumnHeaderFacet());
				}
			}
		}
	}

	@Override
	public Integer getSpan() {
		return (Integer) getStateHelper().eval(ColumnPropertyKeys.span, COLUMNS);
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(ColumnPropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
