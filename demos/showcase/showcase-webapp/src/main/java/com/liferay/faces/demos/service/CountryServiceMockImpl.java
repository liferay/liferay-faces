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
package com.liferay.faces.demos.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
// JSF 2: import javax.faces.bean.ApplicationScoped;
// JSF 2: import javax.faces.bean.ManagedBean;

import javax.faces.model.SelectItem;

import com.liferay.faces.demos.dto.Country;


/**
 * @author  Neil Griffin
 */
// JSF 2: @ApplicationScoped
// JSF 2: @ManagedBean(name = "countryService")
public class CountryServiceMockImpl implements CountryService, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4289537697479875863L;

	// Private Data Members
	private List<Country> countryList;
	private Map<Long, Country> countryMap;

	@PostConstruct
	public void postConstruct() {
		countryMap = new HashMap<Long, Country>();

		Country country = new Country(1, "CN", "China");
		countryMap.put(country.getCountryId(), country);
		country = new Country(2, "CH", "Switzerland");
		countryMap.put(country.getCountryId(), country);
		country = new Country(3, "US", "United States");
		countryMap.put(country.getCountryId(), country);
		country = new Country(4, "UK", "United Kingdom");
		countryMap.put(country.getCountryId(), country);
		country = new Country(5, "VN", "Vietnam");
		countryMap.put(country.getCountryId(), country);
		countryList = new ArrayList<Country>(countryMap.values());
	}

	@Override
	public List<Country> getAllCountries() {
		return countryList;
	}

	@Override
	public List<SelectItem> getAllCountriesSelectItems() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		if (countryList != null) {
			for (Country country : countryList) {
				SelectItem item = new SelectItem();
				item.setLabel(country.getCountryName());
				item.setValue(country);
				items.add(item);
			}
		}
		return items;
	}
	
	@Override
	public Country getCountryByCode(String countryCode) {

		Country country = null;

		for (Country curCountry : countryList) {

			if (curCountry.getCountryCode().equals(countryCode)) {
				country = curCountry;

				break;
			}
		}

		return country;
	}

	@Override
	public Map<Long, Country> getCountryMap() {
		return countryMap;
	}
}
