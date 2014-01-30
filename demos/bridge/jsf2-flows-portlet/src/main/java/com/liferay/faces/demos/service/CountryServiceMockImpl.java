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
package com.liferay.faces.demos.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.liferay.faces.demos.dto.Country;


/**
 * @author  Neil Griffin
 */
@Named("countryService")
@ApplicationScoped
public class CountryServiceMockImpl implements CountryService {

	// Private Data Members
	private List<Country> countries;

	@Override
	public Country findByAbbreviation(String countryAbbreviation) {

		Country country = null;

		getCountries();

		for (Country curCountry : countries) {

			if (curCountry.getCountryAbbreviation().equals(countryAbbreviation)) {
				country = curCountry;

				break;
			}
		}

		return country;
	}

	@Override
	public List<Country> getCountries() {

		if (countries == null) {

			countries = new ArrayList<Country>();
			countries.add(new Country(1, "United States", "US"));
			countries.add(new Country(2, "Canada", "CA"));
		}

		return countries;
	}
}
