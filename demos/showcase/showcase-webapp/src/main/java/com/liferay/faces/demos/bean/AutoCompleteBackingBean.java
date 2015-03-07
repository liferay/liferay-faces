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
package com.liferay.faces.demos.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
@RequestScoped
@ManagedBean
public class AutoCompleteBackingBean {

	// Case-insensitive phrase match filter
	public List<String> serverCustomFilter(String query, List<String> source) {

		List<String> results = new ArrayList<String>();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = facesContext.getViewRoot().getLocale();
		String lowerCaseQuery = query.toLowerCase(locale);

		for (String option : source) {

			if (option.toLowerCase(locale).contains(lowerCaseQuery)) {
				results.add(option);
			}
		}

		return results;
	}
}
