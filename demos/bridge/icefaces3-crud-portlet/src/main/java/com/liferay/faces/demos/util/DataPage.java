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


/**
 * A simple class that represents a "page" of data out of a longer set, ie a list of objects together with info to
 * indicate the starting row and the full size of the dataset. EJBs can return instances of this type when returning
 * subsets of available data.
 *
 * @param  <T>  The type of the beans contained in this data model.
 */
public class DataPage<T> {

	private int datasetSize;

	private int startRow;

	private int endRow;

	private List<T> data;

	/**
	 * Create an object representing a sublist of a dataset.
	 *
	 * @param  datasetSize  is the total number of matching rows available.
	 * @param  startRow     is the index within the complete dataset of the first element in the data list.
	 * @param  endRow       is the index within the complete dataset of the last element in the data list.
	 * @param  data         is a list of consecutive objects from the dataset.
	 */
	public DataPage(int datasetSize, int startRow, int endRow, List<T> data) {
		this.datasetSize = datasetSize;
		this.startRow = startRow;
		this.endRow = endRow;
		this.data = data;
	}

	/**
	 * Return the list of objects held by this object, which is a continuous subset of the full dataset.
	 *
	 * @return
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * Return the number of items in the full dataset.
	 *
	 * @return
	 */
	public int getDatasetSize() {
		return datasetSize;
	}

	/**
	 * @return  the endRow
	 */
	public int getEndRow() {
		return endRow;
	}

	/**
	 * Return the offset within the full dataset of the first element in the list held by this object.
	 *
	 * @return
	 */
	public int getStartRow() {
		return startRow;
	}
}
