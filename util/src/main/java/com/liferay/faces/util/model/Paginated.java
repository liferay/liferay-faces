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

/**
 * Defines a contract for paginated data.
 */
public interface Paginated {

	/**
	 * Returns the number of rows-per-page associated with the pagination.
	 */
	public int getRowsPerPage();

	/**
	 * Sets the number of rows-per-page associated with the pagination.
	 */
	public void setRowsPerPage(int rowsPerPage);
}
