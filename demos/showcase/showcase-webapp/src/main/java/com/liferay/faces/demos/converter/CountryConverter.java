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
package com.liferay.faces.demos.converter;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.liferay.faces.demos.dto.Country;
import com.liferay.faces.demos.service.CountryService;


/**
 * @author  Neil Griffin
 * @author  Juan Gonzalez
 */
@FacesConverter(value = "com.liferay.faces.demos.converter.CountryConverter")
public class CountryConverter implements Converter {

	// Private Data Members
	private static Map<Long, Country> countryMap;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		Object countryObject = null;

		if ((value != null) && !"".equals(value)) {
			Long countryId = Long.parseLong(value);

			countryObject = getCountryMap(facesContext).get(countryId);
		}

		return countryObject;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		String strValue = "";

		if (value != null) {
			Country country = (Country) value;
			strValue = Long.toString(country.getCountryId());
		}

		return strValue;
	}

	protected Map<Long, Country> getCountryMap(FacesContext facesContext) {

		if (countryMap == null) {

			synchronized (this) {

				ELResolver elResolver = facesContext.getApplication().getELResolver();
				ELContext elContext = facesContext.getELContext();
				CountryService countryService = (CountryService) elResolver.getValue(elContext, null, "countryService");
				countryMap = countryService.getCountryMap();
			}
		}

		return countryMap;
	}
}
