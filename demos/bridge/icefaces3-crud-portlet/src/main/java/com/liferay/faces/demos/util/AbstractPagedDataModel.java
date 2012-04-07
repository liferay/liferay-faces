/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.demos.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.DataModel;


/**
 * A special type of JSF DataModel to allow a datatable and datascroller to page through a large set of data without
 * having to hold the entire set of data in memory at once.
 *
 * <p>Any time a managed bean wants to avoid holding an entire dataset, the managed bean should declare an inner class
 * which extends this class and implements the fetchData method. This method is called as needed when the table requires
 * data that isn't available in the current data page held by this object.</p>
 *
 * This does require the managed bean (and in general the business method that the managed bean uses) to provide the
 * data wrapped in a DataPage object that provides info on the full size of the dataset.
 *
 * @param  <T>  The type of the beans contained in this data model.
 *
 * @see    <a href="http://facestutorials.icefaces.org/tutorial/dataTable-JPA-tutorial.html">
 *         http://facestutorials.icefaces.org/tutorial/dataTable-JPA-tutorial.html</a>
 * @see    <a href="http://wiki.apache.org/myfaces/WorkingWithLargeTables">http://wiki.apache.org/myfaces/WorkingWithLargeTables</a>
 */
public abstract class AbstractPagedDataModel<T> extends DataModel<T> {

	private int pageSize;

	private int rowIndex;

	private DataPage<T> page;

	private String sortColumn;

	private Boolean sortAscending;

	private boolean dirtyData = false;

	private Map<Object, Boolean> selected = new HashMap<Object, Boolean>();

	/**
	 * Create a datamodel.
	 *
	 * @param  pageSize  The number of items to show per page.
	 */
	public AbstractPagedDataModel(int pageSize) {
		super();
		this.pageSize = pageSize;
		this.rowIndex = -1;
		this.page = null;
	}

	/**
	 * Method which must be implemented in cooperation with the managed bean class to fetch data on demand.
	 *
	 * @param   startRow       The number of the first row which will be displayed.
	 * @param   pageSize       The number of rows per page.
	 * @param   sortColumn     The column to sort after.
	 * @param   sortAscending  True, if the data must be sorted ascending. If false, it is sorted descending.
	 *
	 * @return
	 */
	public abstract DataPage<T> fetchPage(int startRow, int pageSize, String sortColumn, Boolean sortAscending);

	/**
	 * Clears the current {@link DataPage}.
	 */
	public void resetPage() {
		page = null;
	}

	/**
	 * Set the 'selected' states of multiple objects to <code>true</code>.
	 *
	 * @param  objs  !=null
	 */
	public void selectObjects(Object... objs) {
		selectObjects(objs);
	}

	/**
	 * Set the 'selected' states of multiple objects to <code>true</code>.
	 *
	 * @param  objs  !=null
	 */
	public void selectObjects(Iterable<Object> objs) {

		for (Object obj : objs) {
			selected.put(obj, true);
		}
	}

	/**
	 * Set the 'selected' state of all objects to <code>false</code>.
	 */
	public void unselectAll() {
		selected.clear();
	}

	/**
	 * Set the 'selected' states of multiple objects to <code>false</code>.
	 *
	 * @param  objs  !=null
	 */
	public void unselectObjects(Object... objs) {
		unselectObjects(objs);
	}

	/**
	 * Set the 'selected' states of multiple objects to <code>false</code>.
	 *
	 * @param  objs  !=null
	 */
	public void unselectObjects(Iterable<Object> objs) {

		for (Object obj : objs) {
			selected.put(obj, false);
		}
	}

	/**
	 * True if data or parameters of the data model have changed and thus the page has to be refetched.
	 *
	 * @return
	 */
	public boolean isDirtyData() {
		return dirtyData;
	}

	/**
	 * Dirties this data model.
	 *
	 * @see  #setDirtyData(boolean)
	 */
	public void setDirtyData() {
		dirtyData = true;
	}

	/**
	 * True if data or parameters of the data model have changed and thus the page has to be refetched.
	 *
	 * @param  dirtyData
	 */
	public void setDirtyData(boolean dirtyData) {
		this.dirtyData = dirtyData;
	}

	/**
	 * Return true if the rowIndex value is currently set to a value that matches some element in the dataset. Note that
	 * it may match a row that is not in the currently cached DataPage; if so then when getRowData is called the
	 * required DataPage will be fetched by calling fetchData.
	 */
	@Override
	public boolean isRowAvailable() {
		DataPage<T> page = getPage();

		if (page == null) {
			return false;
		}

		int rowIndex = getRowIndex();

		if (rowIndex < 0) {
			return false;
		}
		else if (rowIndex >= page.getDatasetSize()) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Returns the number of the last row displayed.
	 *
	 * @return
	 */
	public int getEndRow() {
		return page.getEndRow();
	}

	/**
	 * Return a DataPage object; if one is not currently available then fetch one. Note that this doesn't ensure that
	 * the datapage returned includes the current rowIndex row; see getRowData.
	 */
	private DataPage<T> getPage() {

		if (page != null) {
			return page;
		}

		int rowIndex = getRowIndex();
		int startRow = rowIndex;

		if (rowIndex == -1) {

			// even when no row is selected, we still need a page
			// object so that we know the amount of data available.
			startRow = 0;
		}

		// invoke method on enclosing class
		page = fetchPage(startRow, pageSize, sortColumn, sortAscending);

		return page;
	}

	/**
	 * @return  the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Return the total number of rows of data available (not just the number of rows in the current page!).
	 */
	@Override
	public int getRowCount() {
		return getPage().getDatasetSize();
	}

	/**
	 * Return the object corresponding to the current rowIndex. If the DataPage object currently cached doesn't include
	 * that index then fetchPage is called to retrieve the appropriate page.
	 */
	@Override
	public T getRowData() {

		if (rowIndex < 0) {
			throw new IllegalArgumentException("Invalid rowIndex for AbstractPagedDataModel; not within page");
		}

		boolean pageFetched = false;

		// ensure page exists; if rowIndex is beyond dataset size, then
		// we should still get back a DataPage object with the dataset size
		// in it...
		if ((page == null) || dirtyData) {
			page = fetchPage(rowIndex, pageSize, sortColumn, sortAscending);
			pageFetched = true;
			dirtyData = false;
		}

		int datasetSize = page.getDatasetSize();
		int startRow = page.getStartRow();
		int nRows = page.getData().size();
		int endRow = startRow + nRows;

		if (rowIndex >= datasetSize) {
			throw new IllegalArgumentException("Invalid rowIndex");
		}

		if (!pageFetched) {

			if (rowIndex < startRow) {
				page = fetchPage(rowIndex, pageSize, sortColumn, sortAscending);
				startRow = page.getStartRow();
			}
			else if (rowIndex >= endRow) {
				page = fetchPage(rowIndex, pageSize, sortColumn, sortAscending);
				startRow = page.getStartRow();
			}
		}

		if (page.getData().size() == 0) {
			return null;
		}

		return page.getData().get(rowIndex - startRow);
	}

	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * Specify what the "current row" within the dataset is. Note that the UIData component will repeatedly call this
	 * method followed by getRowData to obtain the objects to render in the table.
	 */
	@Override
	public void setRowIndex(int index) {
		rowIndex = index;
	}

	/**
	 * @return  the selected
	 */
	public Map<Object, Boolean> getSelected() {
		return selected;
	}

	/**
	 * @param  selected  Sets the 'selected' state of an object of this data model.
	 */
	public void setSelected(Map<Object, Boolean> selected) {
		this.selected = selected;
	}

	/**
	 * Returns a list of all selected objects.
	 *
	 * @return  !=null
	 */
	public List<Object> getSelectedObjects() {
		List<Object> selectedObjects = new ArrayList<Object>();

		for (Map.Entry<Object, Boolean> temp : this.selected.entrySet()) {

			if (temp.getValue()) {
				selectedObjects.add(temp.getKey());
			}
		}

		return selectedObjects;
	}

	/**
	 * @return  the sortAscending
	 */
	public Boolean getSortAscending() {
		return sortAscending;
	}

	/**
	 * @param  sortAscending  the sortAscending to set
	 */
	public void setSortAscending(Boolean sortAscending) {

		if (!sortAscending.equals(this.sortAscending)) {
			setDirtyData();
			this.sortAscending = sortAscending;
		}
	}

	/**
	 * @return  the sortColumn
	 */
	public String getSortColumn() {
		return sortColumn;
	}

	/**
	 * @param  sortColumn  the sortColumn to set
	 */
	public void setSortColumn(String sortColumn) {

		if (!sortColumn.equals(this.sortColumn)) {
			setDirtyData();
			this.sortColumn = sortColumn;
		}
	}

	/**
	 * Returns the number of the first row displayed.
	 *
	 * @return
	 */
	public int getStartRow() {
		return page.getStartRow();
	}

	@Override
	public Object getWrappedData() {
		return page.getData();
	}

	/**
	 * Not used in this class; data is fetched via a callback to the fetchData method rather than by explicitly
	 * assigning a list.
	 */
	@Override
	public void setWrappedData(Object o) {
		throw new UnsupportedOperationException("setWrappedData");
	}
}
