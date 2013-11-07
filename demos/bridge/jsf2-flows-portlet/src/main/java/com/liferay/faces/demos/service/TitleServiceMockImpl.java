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
package com.liferay.faces.demos.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.liferay.faces.demos.dto.Title;


/**
 * @author  Neil Griffin
 */
@Named("titleService")
@ApplicationScoped
public class TitleServiceMockImpl implements TitleService {

	// Private Data Members
	private List<Title> titles;

	public TitleServiceMockImpl() {
		this.titles = new ArrayList<Title>();
		this.titles.add(new Title(1, "Mr."));
		this.titles.add(new Title(2, "Mrs."));
		this.titles.add(new Title(3, "Ms."));
		this.titles.add(new Title(4, "Dr."));
	}

	@Override
	public List<Title> getTitles() {
		return titles;
	}

}
