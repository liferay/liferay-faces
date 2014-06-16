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
package com.liferay.faces.alloy.component.datatable;

import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@FacesComponent(value = DataTable.COMPONENT_TYPE)
public class DataTable extends DataTableBase {

	// Public Constants
	public static final String COLUMN_CLASSES = "columnClasses";
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.datatable.DataTable";
	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String DELEGATE_RENDERER_TYPE = "javax.faces.Table";
	public static final String HEADER_CLASS = "headerClass";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.datatable.DataTableRenderer";
	public static final String ROW_CLASSES = "rowClasses";
	public static final String STYLE_CLASS_NAME = "yui3-datatable-table alloy-datatable";
	public static final String YUI3_DATATABLE_CELL = "yui3-datatable-cell";
	public static final String YUI3_DATATABLE_EVEN = "yui3-datatable-even";
	public static final String YUI3_DATATABLE_ODD = "yui3-datatable-odd";
	public static final String YUI3_DATATABLE_EVEN_ODD = YUI3_DATATABLE_EVEN + StringPool.COMMA + YUI3_DATATABLE_ODD;
	public static final String YUI3_DATATABLE_HEADER = "yui3-datatable-header";

	public DataTable() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	protected int getTotalChildColumns() {
		
		int totalChildColumns = 0;
		
		List<UIComponent> children = getChildren();
		
		if (children != null) {
			
			for (UIComponent child: children) {
				if (child instanceof UIColumn) {
					totalChildColumns++;
				}
			}
		}
		
		return totalChildColumns;
	}
	@Override
	public String getColumnClasses() {

		String columnClasses = super.getColumnClasses();

		StringBuilder stringBuilder = new StringBuilder();
		int totalChildColumns = getTotalChildColumns();

		if (columnClasses == null) {
			
			for (int i = 0; i < totalChildColumns; i++) {
				if (i > 0) {
					stringBuilder.append(StringPool.COMMA);
				}
				stringBuilder.append(YUI3_DATATABLE_CELL);
			}
		}
		else {
			String[] columnClassArray = columnClasses.split(StringPool.COMMA);

			int i;
			for (i = 0; i < columnClassArray.length; i++) {

				if (i > 0) {
					stringBuilder.append(StringPool.COMMA);
				}

				String columnClass = columnClassArray[i].trim();

				if (columnClass.length() > 0) {
					stringBuilder.append(columnClass);
					stringBuilder.append(StringPool.SPACE);
				}

				stringBuilder.append(YUI3_DATATABLE_CELL);
			}

			for (int j = i; j < totalChildColumns; j++) {
				
				stringBuilder.append(StringPool.COMMA);
				stringBuilder.append(YUI3_DATATABLE_CELL);
			}
			columnClasses = stringBuilder.toString();
		}

		return stringBuilder.toString();
	}

	@Override
	public String getHeaderClass() {

		String headerClass = super.getHeaderClass();

		return ComponentUtil.concatCssClasses(headerClass, YUI3_DATATABLE_HEADER);
	}

	@Override
	public String getRowClasses() {
		String rowClasses = super.getRowClasses();

		if (rowClasses == null) {
			rowClasses = YUI3_DATATABLE_EVEN_ODD;
		}
		else {
			StringBuilder stringBuilder = new StringBuilder();
			String[] rowClassArray = rowClasses.split(StringPool.COMMA);

			for (int i = 0; i < rowClassArray.length; i++) {

				if (i > 0) {
					stringBuilder.append(StringPool.COMMA);
				}

				String rowClass = rowClassArray[i].trim();

				if (rowClass.length() > 0) {
					stringBuilder.append(rowClass);
					stringBuilder.append(StringPool.SPACE);
					if (i % 2 == 1) {
						stringBuilder.append(YUI3_DATATABLE_ODD);
					}
					else {
						stringBuilder.append(YUI3_DATATABLE_EVEN);
					}
					stringBuilder.append(StringPool.SPACE);
				}

				stringBuilder.append(YUI3_DATATABLE_CELL);
			}

			rowClasses = stringBuilder.toString();
		}

		return rowClasses;
	}

	@Override
	public String getStyleClass() {

		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
