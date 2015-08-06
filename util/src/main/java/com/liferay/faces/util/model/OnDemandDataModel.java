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
package com.liferay.faces.util.model;

import java.util.Collection;
import java.util.List;

import javax.faces.model.DataModel;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public abstract class OnDemandDataModel<E> extends DataModel<E> implements Paginated, Sortable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OnDemandDataModel.class);

	// Private Data Members
	private int finishRowIndex = -1;
	private int rowCount = -1;
	private int rowIndex = -1;
	private int rowsPerPage;
	private List<SortCriterion> sortCriteria;

	private int startRowIndex = -1;
	private List<E> wrappedData;

	/**
	 * Returns the total number of rows. Note that this method is called only when necessary, and so the return value
	 * should not be cached in anyway.
	 */
	public abstract int countRows();

	/**
	 * Returns a list of rows that is a subset of the entire list of rows.
	 *
	 * @param  startRow       The starting row index.
	 * @param  finishRow      The finishing row index.
	 * @param  sortCritieria  The sort criteria that is to be applied to the order of the results.
	 */
	public abstract Collection<E> findRows(int startRow, int finishRow, List<SortCriterion> sortCritieria);

	/**
	 * Resets (clears) the underlying wrapped data.
	 */
	public void reset() {
		setRowCount(-1);
		setWrappedData(null);
		setStartRowIndex(-1);
		setFinishRowIndex(-1);
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
	 * Returns the index of the finishing row associated with the underlying wrapped data.
	 */
	public int getFinishRowIndex() {
		return finishRowIndex;
	}

	/**
	 * Sets the finishing row associated with the underlying wrapped data.
	 */
	public void setFinishRowIndex(int finishRowIndex) {
		this.finishRowIndex = finishRowIndex;
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
			Collection<E> wrappedData = getWrappedData();

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

			int startRowIndex = getStartRowIndex();
			int finishRowIndex = getFinishRowIndex();

			if ((startRowIndex >= 0) && (finishRowIndex >= 0)) {

				int maxFinishRowIndex = startRowIndex + getRowsPerPage() - 1;

				if ((rowIndex < startRowIndex) || (rowIndex > maxFinishRowIndex)) {

					if (logger.isDebugEnabled()) {
						logger.debug("Clearing cache since rowIndex=[{0}] is outside the range of cached rows.",
							rowIndex);
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
	 * @see  {@link Sortable#getSortCriteria()}
	 */
	@Override
	public List<SortCriterion> getSortCriteria() {
		return sortCriteria;
	}

	/**
	 * @see  {@link Sortable#setSortCriteria(java.util.List)}
	 */
	@Override
	public void setSortCriteria(List<SortCriterion> sortCriteria) {
		this.sortCriteria = sortCriteria;
		reset();
	}

	/**
	 * Returns the index of the starting row associated with the underlying wrapped data.
	 */
	public int getStartRowIndex() {
		return startRowIndex;
	}

	/**
	 * Sets the starting row associated with the underlying wrapped data.
	 */
	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	/**
	 * @see  {@link javax.faces.model.DataModel#getWrappedData()}
	 */
	@Override
	public List<E> getWrappedData() {

		if (wrappedData == null) {

			int startRowIndex = rowIndex;
			int finishRowIndex = Math.min(rowIndex + getRowsPerPage() - 1, getRowCount() - 1);

			logger.debug("finding new startRowIndex=[{0}] finishRowIndex=[{1}]", startRowIndex, finishRowIndex);

			setWrappedData(findRows(startRowIndex, finishRowIndex, sortCriteria));
			setFinishRowIndex(finishRowIndex);
			setStartRowIndex(startRowIndex);
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
}
