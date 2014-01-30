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
package com.liferay.faces.issues;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@ViewScoped
public class ModelManagedBean {

	private List<Item> items;

	public List<Item> getItems() {

		if (items == null) {
			items = new ArrayList<Item>();
			items.add(new Item(1, "First-Item"));
			items.add(new Item(2, "Second-Item"));
			items.add(new Item(3, "Third-Item"));
		}

		return items;
	}

}
