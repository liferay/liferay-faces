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
package com.liferay.faces.alloy.component.datatable.internal;

import java.util.List;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;

import com.liferay.faces.alloy.component.column.Column;
import com.liferay.faces.alloy.component.datatable.DataTable;


/**
 * @author  Neil Griffin
 */
public class DataTableInfo {

	// Private Data members
	private int totalRenderedColumns;
	private boolean footerFacetPresentInColumn;
	private boolean headerFacetOrTextPresentInColumn;

	public DataTableInfo(DataTable dataTable) {

		List<UIComponent> children = dataTable.getChildren();

		for (UIComponent child : children) {

			if (child instanceof UIColumn) {

				if (!headerFacetOrTextPresentInColumn) {
					headerFacetOrTextPresentInColumn = (child.getFacet("header") != null);

					if (!headerFacetOrTextPresentInColumn) {

						if (child instanceof Column) {

							Column column = (Column) child;
							headerFacetOrTextPresentInColumn = (column.getHeaderText() != null);
						}
					}
				}

				if (!footerFacetPresentInColumn) {
					footerFacetPresentInColumn = (child.getFacet("footer") != null);
				}

				UIColumn uiColumn = (UIColumn) child;

				if (uiColumn.isRendered()) {
					totalRenderedColumns++;
				}
			}
		}
	}

	public boolean isFooterFacetPresentInColumn() {
		return footerFacetPresentInColumn;
	}

	public boolean isHeaderFacetOrTextPresentInColumn() {
		return headerFacetOrTextPresentInColumn;
	}

	public int getTotalRenderedColumns() {
		return totalRenderedColumns;
	}
}
