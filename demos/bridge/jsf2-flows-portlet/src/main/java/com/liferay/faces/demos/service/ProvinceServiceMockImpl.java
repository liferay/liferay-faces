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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.liferay.faces.demos.dto.Country;
import com.liferay.faces.demos.dto.Province;


/**
 * @author  Neil Griffin
 */
@Named("provinceService")
@ApplicationScoped
public class ProvinceServiceMockImpl implements ProvinceService {

	@Inject
	private CountryService countryService;

	// Private Data Members
	private List<Province> provinces;
	private Map<Long, List<Province>> countryProvinceMap;

	public ProvinceServiceMockImpl() {
		this.countryProvinceMap = new HashMap<Long, List<Province>>();
		this.provinces = new ArrayList<Province>();
	}

	@PostConstruct
	public void postConstruct() {

		Country unitedStates = countryService.findByAbbreviation("US");
		long countryId = unitedStates.getCountryId();
		provinces.add(new Province(1, countryId, "Alabama", "AL"));
		provinces.add(new Province(2, countryId, "Alaska", "AK"));
		provinces.add(new Province(3, countryId, "Arizona", "AZ"));
		provinces.add(new Province(4, countryId, "Arkansas", "AR"));
		provinces.add(new Province(5, countryId, "California", "CA"));
		provinces.add(new Province(6, countryId, "Colorado", "CO"));
		provinces.add(new Province(7, countryId, "Connecticut", "CT"));
		provinces.add(new Province(8, countryId, "Delaware", "DE"));
		provinces.add(new Province(9, countryId, "District of Columbia", "DC"));
		provinces.add(new Province(10, countryId, "Florida", "FL"));
		provinces.add(new Province(11, countryId, "Georgia", "GA"));
		provinces.add(new Province(12, countryId, "Hawaii", "HI"));
		provinces.add(new Province(13, countryId, "Idaho", "ID"));
		provinces.add(new Province(14, countryId, "Illinois", "IL"));
		provinces.add(new Province(15, countryId, "Indiana", "IN"));
		provinces.add(new Province(16, countryId, "Iowa", "IA"));
		provinces.add(new Province(17, countryId, "Kansas", "KS"));
		provinces.add(new Province(18, countryId, "Kentucky", "KY"));
		provinces.add(new Province(19, countryId, "Louisiana", "LA"));
		provinces.add(new Province(20, countryId, "Maine", "ME"));
		provinces.add(new Province(21, countryId, "Montana", "MT"));
		provinces.add(new Province(22, countryId, "Nebraska", "NE"));
		provinces.add(new Province(23, countryId, "Nevada", "NV"));
		provinces.add(new Province(24, countryId, "New Hampshire", "NH"));
		provinces.add(new Province(25, countryId, "New Jersey", "NJ"));
		provinces.add(new Province(26, countryId, "New Mexico", "NM"));
		provinces.add(new Province(27, countryId, "New York", "NY"));
		provinces.add(new Province(28, countryId, "North Carolina", "NC"));
		provinces.add(new Province(29, countryId, "North Dakota", "ND"));
		provinces.add(new Province(30, countryId, "Ohio", "OH"));
		provinces.add(new Province(31, countryId, "Oklahoma", "OK"));
		provinces.add(new Province(32, countryId, "Oregon", "OR"));
		provinces.add(new Province(33, countryId, "Maryland", "MD"));
		provinces.add(new Province(34, countryId, "Massachusetts", "MA"));
		provinces.add(new Province(35, countryId, "Michigan", "MI"));
		provinces.add(new Province(36, countryId, "Minnesota", "MN"));
		provinces.add(new Province(37, countryId, "Mississippi", "MS"));
		provinces.add(new Province(38, countryId, "Missouri", "MO"));
		provinces.add(new Province(39, countryId, "Pennsylvania", "PA"));
		provinces.add(new Province(40, countryId, "Rhode Island", "RI"));
		provinces.add(new Province(41, countryId, "South Carolina", "SC"));
		provinces.add(new Province(42, countryId, "South Dakota", "SD"));
		provinces.add(new Province(43, countryId, "Tennessee", "TN"));
		provinces.add(new Province(44, countryId, "Texas", "TX"));
		provinces.add(new Province(45, countryId, "Utah", "UT"));
		provinces.add(new Province(46, countryId, "Vermont", "VT"));
		provinces.add(new Province(47, countryId, "Virginia", "VA"));
		provinces.add(new Province(48, countryId, "Washington", "WA"));
		provinces.add(new Province(49, countryId, "West Virginia", "WV"));
		provinces.add(new Province(50, countryId, "Wisconsin", "WI"));
		provinces.add(new Province(51, countryId, "Wyoming", "WY"));

		Country canada = countryService.findByAbbreviation("CA");
		countryId = canada.getCountryId();
		provinces.add(new Province(52, countryId, "Alberta", "AB"));
		provinces.add(new Province(53, countryId, "British Columbia", "BC"));
		provinces.add(new Province(54, countryId, "Manitoba", "MB"));
		provinces.add(new Province(55, countryId, "New Brunswick", "NB"));
		provinces.add(new Province(56, countryId, "Newfoundland and Labrador", "NL"));
		provinces.add(new Province(57, countryId, "Northwest Territories", "NT"));
		provinces.add(new Province(58, countryId, "Nova Scotia", "NS"));
		provinces.add(new Province(59, countryId, "Nunavut", "NU"));
		provinces.add(new Province(60, countryId, "Ontario", "ON"));
		provinces.add(new Province(61, countryId, "Prince Edward Island", "PE"));
		provinces.add(new Province(62, countryId, "Quebec", "QC"));
		provinces.add(new Province(63, countryId, "Saskatchewan", "SK"));
		provinces.add(new Province(64, countryId, "Yukon", "YT"));
	}

	@Override
	public List<Province> getProvinces(long countryId) {

		List<Province> countryProvinces = countryProvinceMap.get(countryId);

		if (countryProvinces == null) {

			synchronized (countryProvinceMap) {

				countryProvinces = new ArrayList<Province>();

				for (Province province : provinces) {

					if (province.getCountryId() == countryId) {
						countryProvinces.add(province);
					}
				}

				countryProvinceMap.put(countryId, countryProvinces);
			}
		}

		return countryProvinces;
	}
}
