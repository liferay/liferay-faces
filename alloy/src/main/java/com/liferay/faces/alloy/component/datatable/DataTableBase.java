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
package com.liferay.faces.alloy.component.datatable;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlDataTable;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class DataTableBase extends HtmlDataTable implements Styleable, ClientComponent {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.datatable.DataTable";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.datatable.DataTableRenderer";

	// Protected Enumerations
	protected enum DataTablePropertyKeys {
		clientKey,
		multiColumnSort,
		selectedRowIndexes,
		selectionMode,
		styleClass
	}

	public DataTableBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(DataTablePropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(DataTablePropertyKeys.clientKey, clientKey);
	}

	public boolean isMultiColumnSort() {
		return (Boolean) getStateHelper().eval(DataTablePropertyKeys.multiColumnSort, false);
	}

	public void setMultiColumnSort(boolean multiColumnSort) {
		getStateHelper().put(DataTablePropertyKeys.multiColumnSort, multiColumnSort);
	}

	public String getSelectedRowIndexes() {
		return (String) getStateHelper().eval(DataTablePropertyKeys.selectedRowIndexes, null);
	}

	public void setSelectedRowIndexes(String selectedRowIndexes) {
		getStateHelper().put(DataTablePropertyKeys.selectedRowIndexes, selectedRowIndexes);
	}

	public String getSelectionMode() {
		return (String) getStateHelper().eval(DataTablePropertyKeys.selectionMode, null);
	}

	public void setSelectionMode(String selectionMode) {
		getStateHelper().put(DataTablePropertyKeys.selectionMode, selectionMode);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(DataTablePropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(DataTablePropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-data-table", "table table-bordered table-hover table-striped");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(DataTablePropertyKeys.styleClass, styleClass);
	}
}
//J+
