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

import java.util.List;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * This abstract class provides the ability to supply JSF UI components with a {@link javax.faces.model.DataModel} that
 * loads data in a lazy (on-demand) manner. It also provides for the ability to mark underlying rows for deletion.
 *
 * @author  Neil Griffin
 */
public abstract class LazyDataModel<E> extends RowMarkerDataModel<E> implements Paginated, Sortable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LazyDataModel.class);

	// Private Data Members
	private int rowCount = -1;
	private int rowIndex = -1;
	private int rowsPerPage;
	private boolean sortAscending = true;
	private String sortColumn;
	private List<E> wrappedData;
	private int wrappedDataStartRowIndex = -1;
	private int wrappedDataFinishRowIndex = -1;

	/**
	 * Returns the total number of rows. Note that this method is called only when necessary, and so the return value
	 * should not be cached in anyway.
	 */
	public abstract int countRows();

	/**
	 * Returns a list of rows that is a subset of the entire list of rows.
	 *
	 * @param  startRow   The starting row index.
	 * @param  finishRow  The finishing row index.
	 */
	public abstract List<E> findRows(int startRow, int finishRow);

	/**
	 * @see  {@link RowMarkerDataModel#reset()}
	 */
	@Override
	public void reset() {
		setRowCount(-1);
		setWrappedData(null);
		setWrappedDataStartRowIndex(-1);
		setWrappedDataFinishRowIndex(-1);
		setRowMarks(null);
	}

	/**
	 * @see  {@link javax.faces.model.DataModel#isRowAvailable()}
	 */
	@Override
	public boolean isRowAvailable() {
		int rowIndex = getRowIndex();

		return (rowIndex >= 0) && (rowIndex < getRowCount());
	}

	/**
	 * @see  {@link Sortable#isSortAscending()}
	 */
	public boolean isSortAscending() {
		return sortAscending;
	}

	/**
	 * @see  {@link javax.faces.model.DataModel#getRowCount()}
	 */
	@Override
	public int getRowCount() {

		if (rowCount == -1) {
			rowCount = countRows();
		}

		return rowCount;
	}

	/**
	 * Sets the rowCount to the specified value.
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @see  {@link javax.faces.model.DataModel#getRowData()}
	 */
	@Override
	public E getRowData() {

		if (getRowIndex() >= 0) {
			int adjustedRowIndex = getRowIndex() % getRowsPerPage();
			List<E> wrappedData = getWrappedData();

			if (adjustedRowIndex >= wrappedData.size()) {
				logger.error("adjustedRowIndex=[{0}] higher than wrappedData.size=[{1}]", adjustedRowIndex,
					wrappedData.size());

				return null;
			}

			return getWrappedData().get(adjustedRowIndex);
		}
		else {
			return null;
		}
	}

	/**
	 * @see  {@link javax.faces.model.DataModel#getRowIndex()}
	 */
	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @see  {@link javax.faces.model.DataModel#setRowIndex(int)}
	 */
	@Override
	public void setRowIndex(int rowIndex) {

		// If the specified rowIndex is outside the range of cached rows, then clear the cache so that the
		// findRows(int startRow, int finishRow) method will be called in order to load the set of rows
		// associated with the specified rowIndex.
		if (rowIndex >= 0) {

			int wrappedDataStartRowIndex = getWrappedDataStartRowIndex();
			int wrappedDataFinishRowIndex = getWrappedDataFinishRowIndex();

			if ((wrappedDataStartRowIndex >= 0) && (wrappedDataFinishRowIndex >= 0)) {

				int wrappedDataMaxFinishRowIndex = wrappedDataStartRowIndex + getRowsPerPage() - 1;

				if ((rowIndex < wrappedDataStartRowIndex) || (rowIndex > wrappedDataMaxFinishRowIndex)) {

					if (logger.isDebugEnabled()) {
						logger.debug("rowIndex=[" + rowIndex + "] outside the range of cached rows so clearing cache");
					}

					reset();
				}
			}
		}

		this.rowIndex = rowIndex;
	}

	/**
	 * @see  {@link Paginated#getRowsPerPage()}
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * @see  {@link Paginated#setRowsPerPage(int)}
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * @see  {@link Sortable#setSortAscending(boolean)}
	 */
	public void setSortAscending(boolean sortAscending) {

		if (this.sortAscending != sortAscending) {
			reset();
		}

		this.sortAscending = sortAscending;
	}

	/**
	 * @see  {@link Sortable#getSortColumn()}
	 */
	public String getSortColumn() {
		return sortColumn;
	}

	/**
	 * @see  {@link Sortable#setSortColumn(String)}
	 */
	public void setSortColumn(String sortColumn) {

		if ((this.sortColumn != null) && !this.sortColumn.equals(sortColumn)) {
			reset();
		}

		this.sortColumn = sortColumn;
	}

	@Override
	public boolean isReset() {
		return (wrappedData == null);
	}

	/**
	 * @see  {@link javax.faces.model.DataModel#getWrappedData()}
	 */
	@Override
	public List<E> getWrappedData() {

		if (wrappedData == null) {

			int wrappedDataStartRowIndex = rowIndex;
			int wrappedDataFinishRowIndex = Math.min(rowIndex + getRowsPerPage() - 1, getRowCount() - 1);

			logger.debug("finding new wrappedDataStartRowIndex=[{0}] wrappedDataFinishRowIndex=[{1}]",
				wrappedDataStartRowIndex, wrappedDataFinishRowIndex);

			setWrappedData(findRows(wrappedDataStartRowIndex, wrappedDataFinishRowIndex));
			setWrappedDataFinishRowIndex(wrappedDataFinishRowIndex);
			setWrappedDataStartRowIndex(wrappedDataStartRowIndex);
		}

		return wrappedData;
	}

	/**
	 * @see  {@link javax.faces.model.DataModel#setWrappedData(Object)}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setWrappedData(Object wrappedData) {

		if (wrappedData == null) {
			this.wrappedData = null;
		}
		else {
			this.wrappedData = (List<E>) wrappedData;
		}
	}

	/**
	 * Returns the index of the finishing row associated with the underlying wrapped data.
	 */
	public int getWrappedDataFinishRowIndex() {
		return wrappedDataFinishRowIndex;
	}

	/**
	 * Sets the finishing row associated with the underlying wrapped data.
	 */
	public void setWrappedDataFinishRowIndex(int wrappedDataFinishRowIndex) {
		this.wrappedDataFinishRowIndex = wrappedDataFinishRowIndex;
	}

	/**
	 * Returns the index of the starting row associated with the underlying wrapped data.
	 */
	public int getWrappedDataStartRowIndex() {
		return wrappedDataStartRowIndex;
	}

	/**
	 * Sets the starting row associated with the underlying wrapped data.
	 */
	public void setWrappedDataStartRowIndex(int wrappedDataStartRowIndex) {
		this.wrappedDataStartRowIndex = wrappedDataStartRowIndex;
	}

	/**
	 * Searches through the cached/wrapped data for the row that corresponds to the specified primaryKey and returns the
	 * row.
	 */
	public E getWrappedRow(Object primaryKey) {

		E wrappedRow = null;

		for (E row : wrappedData) {

			if (getPrimaryKey(row).equals(primaryKey)) {
				wrappedRow = row;

				break;
			}
		}

		return wrappedRow;
	}

}
