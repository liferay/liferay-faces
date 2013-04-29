/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.faces.model.DataModel;


/**
 * This class extends the {@link javax.faces.model.DataModel} class and provides a convenient base implementation of the
 * {@link RowMarker} interface.
 *
 * @author  Neil Griffin
 */
public abstract class RowMarkerDataModel<E> extends DataModel implements RowMarker {

	// Private Data Members
	protected boolean allRowsMarked = false;
	private RowMarks rowMarks;

	/**
	 * Deletes the row associated with the specified primary key.
	 *
	 * @throws  IOException  When any type of exception occurs during the delete process.
	 */
	public abstract void deleteRow(Object primaryKey) throws IOException;

	/**
	 * Resets (clears) the underlying cache of rows.
	 */
	public abstract void reset();

	/**
	 * @see  {@link RowMarker#deleteMarkedRows()}
	 */
	public int deleteMarkedRows() throws IOException {
		Set<Entry<Object, Boolean>> entrySet = getRowMarks().entrySet();
		Iterator<Map.Entry<Object, Boolean>> iterator = entrySet.iterator();

		int totalDeletedRows = 0;

		while (iterator.hasNext()) {
			Map.Entry<Object, Boolean> mapEntry = iterator.next();

			if (mapEntry.getValue()) {

				try {
					deleteRow(mapEntry.getKey());
					totalDeletedRows++;
				}
				catch (Exception e) {
					throw new IOException(e.getMessage());
				}
			}
		}

		if (totalDeletedRows > 0) {
			reset();
		}

		return totalDeletedRows;
	}

	/**
	 * @see  {@link RowMarker#setAllRowsMarked(boolean)}
	 */
	public void setAllRowsMarked(boolean allRowsMarked) {
		this.allRowsMarked = allRowsMarked;

		Boolean allRowsMarkedAsBoolean = Boolean.valueOf(allRowsMarked);
		Set<Entry<Object, Boolean>> entrySet = getRowMarks().entrySet();
		Iterator<Map.Entry<Object, Boolean>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Object, Boolean> mapEntry = iterator.next();
			mapEntry.setValue(allRowsMarkedAsBoolean);
		}
	}

	/**
	 * @see  {@link RowMarker#isAllRowsMarked()}
	 */
	public boolean isAllRowsMarked() {
		return allRowsMarked;
	}

	/**
	 * Retrieves the primary key from the specified object.
	 */
	public abstract Object getPrimaryKey(E e);

	/**
	 * @see  {@link RowMarker#getRowMarks()}
	 */
	public RowMarks getRowMarks() {

		if (rowMarks == null) {

			@SuppressWarnings("unchecked")
			List<E> wrappedData = (List<E>) getWrappedData();

			if (wrappedData != null) {
				rowMarks = new RowMarks();
				rowMarks.addObserver(new RowMarksObserver());

				for (E e : wrappedData) {
					Object primaryKey = getPrimaryKey(e);
					rowMarks.put(primaryKey, Boolean.FALSE);
				}
			}
		}

		return rowMarks;
	}

	/**
	 * @see  {@link RowMarker#setRowMarks(RowMarks)}
	 */
	public void setRowMarks(RowMarks rowMarks) {
		this.rowMarks = rowMarks;
	}

	/**
	 * Indicates whether or not the underlying cache of rows has been reset/cleared.
	 */
	public abstract boolean isReset();

	protected class RowMarksObserver implements Observer {

		public void update(Observable rowMarks, Object arg) {

			RowMarks.NotificationEvent notificationEvent = (RowMarks.NotificationEvent) arg;

			// If the user checked one of the rows, then the allRowsMarked flag might need to be set to true.
			if (notificationEvent == RowMarks.NotificationEvent.ROW_MARKED) {

				// Assume all rows are marked.
				allRowsMarked = true;

				// If any are not marked, then set the flag to false.
				Set<Entry<Object, Boolean>> entrySet = getRowMarks().entrySet();
				Iterator<Map.Entry<Object, Boolean>> iterator = entrySet.iterator();

				while (iterator.hasNext() && allRowsMarked) {
					Map.Entry<Object, Boolean> mapEntry = iterator.next();
					Boolean value = mapEntry.getValue();

					if ((value != null) && (!value.booleanValue())) {
						allRowsMarked = false;
					}
				}
			}

			// Otherwise, if the user un-checked one of the rows, then the allRowsMarked flag needs to be set to false.
			else if (notificationEvent == RowMarks.NotificationEvent.ROW_UNMARKED) {
				allRowsMarked = false;
			}
		}

	}
}
