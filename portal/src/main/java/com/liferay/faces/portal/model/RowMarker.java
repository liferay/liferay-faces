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
package com.liferay.faces.portal.model;

import java.io.IOException;


/**
 * This interface defines a contract for marking and deleting rows, intended to be implemented by a class that extends
 * {@link javax.faces.model.DataModel}
 *
 * @author  Neil Griffin
 */
public interface RowMarker {

	/**
	 * Deletes the rows that are marked according to the value of {@link #getRowMarks()} and returns the number of
	 * deleted rows.
	 *
	 * @throws  IOException  When any kind of exception occurs during the delete process.
	 */
	public int deleteMarkedRows() throws IOException;

	/**
	 * Sets the flag indicating whether or not all rows are marked, according to the specified value.
	 */
	public void setAllRowsMarked(boolean allRowsMarked);

	/**
	 * Returns a boolean flag indicating whether or not all rows are marked.
	 */
	public boolean isAllRowsMarked();

	/**
	 * Returns an instance of {@link RowMarks} that represents rows that are marked.
	 */
	public RowMarks getRowMarks();

	/**
	 * Sets an instance of {@link RowMarks} that represents rows that are marked.
	 */
	public void setRowMarks(RowMarks rowMarks);

}
