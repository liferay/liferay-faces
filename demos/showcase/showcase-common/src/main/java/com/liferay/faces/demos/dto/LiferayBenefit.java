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
package com.liferay.faces.demos.dto;

import java.io.Serializable;


/**
 * @author  Neil Griffin
 */
public class LiferayBenefit implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 6398796698902377951L;

	// Private Data Members
	private long id;
	private String description;
	private String imageName;
	private String label;
	private String title;

	public LiferayBenefit(long id, String label, String title, String description, String imageName) {
		this.id = id;
		this.label = label;
		this.title = title;
		this.description = description;
		this.imageName = imageName;
	}

	public String getDescription() {
		return description;
	}

	public long getId() {
		return id;
	}

	public String getImageName() {
		return imageName;
	}

	public String getLabel() {
		return label;
	}

	public String getTitle() {
		return title;
	}
}
