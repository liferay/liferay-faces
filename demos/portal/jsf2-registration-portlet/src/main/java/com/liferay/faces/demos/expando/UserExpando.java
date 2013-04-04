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
package com.liferay.faces.demos.expando;

import com.liferay.portal.model.User;

import com.liferay.portlet.expando.model.ExpandoColumnConstants;


/**
 * This is an enumeration of attributes that fortify/enhance the Liferay User entity.
 *
 * @author  Neil Griffin
 */
public enum UserExpando {

	// Public Enumeration
	COMPANY_NAME("companyName"), FAVORITE_COLOR("favoriteColor");

	// Private Constants
	private static final String MODEL_CLASS_NAME = User.class.getName();

	// Private Data Members
	private int expandoType;
	private boolean indexable;
	private String name;

	private UserExpando(String name) {
		this(name, ExpandoColumnConstants.STRING, true);
	}

	private UserExpando(String name, int expandoType, boolean indexable) {
		this.name = name;
		this.expandoType = expandoType;
		this.indexable = indexable;
	}

	public boolean isIndexable() {
		return indexable;
	}

	public int getExpandoType() {
		return expandoType;
	}

	public String getModelClassName() {
		return MODEL_CLASS_NAME;
	}

	public String getName() {
		return name;
	}
}
