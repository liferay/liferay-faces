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
package com.liferay.util.bridges.jsf.common.comparator;

import java.util.Comparator;

import javax.faces.model.SelectItem;


/**
 * @deprecated  This class has been deprecated as of Liferay Faces 2.2 and will not appear in future versions.
 * @author      Neil Griffin
 */
@Deprecated
public class SelectItemComparator implements Comparator<SelectItem> {

	// Private Data Members
	private boolean ascending;

	public SelectItemComparator() {
		this(true);
	}

	public SelectItemComparator(boolean ascending) {
		this.ascending = ascending;
	}

	public int compare(SelectItem selectItem1, SelectItem selectItem2) {
		int value = selectItem1.getLabel().compareTo(selectItem2.getLabel());

		if (ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

}
