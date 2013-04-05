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
package com.liferay.faces.demos.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.liferay.faces.demos.dto.City;
import com.liferay.faces.demos.dto.Province;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "listModelBean")
@SessionScoped
public class ListModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4433667773050051612L;

	// Private Data Members
	private List<City> cities;
	private List<SelectItem> filteredCities;
	private List<Province> provinces;
// private List<SelectItem> provinceSelectItems;

	public void filterSelectItems(String cityNameStartsWith) {
		filteredCities = new ArrayList<SelectItem>();

		List<City> cities = getCities();

		for (City city : cities) {
			boolean addCity = false;

			if (cityNameStartsWith == null) {
				addCity = true;
			}
			else {

				if (city.getCityName().toLowerCase().startsWith(cityNameStartsWith.toLowerCase())) {
					addCity = true;
				}
			}

			if (addCity) {
				SelectItem selectItem = new SelectItem(city, city.getCityName());
				filteredCities.add(selectItem);
			}
		}
	}

	public List<City> getCities() {

		if (cities == null) {
			long cityId = 1;
			cities = new ArrayList<City>();

			City city = new City(cityId++, getProvinceId("DE"), "Wilmington", "19806");
			cities.add(city);
			city = new City(cityId++, getProvinceId("GA"), "Atlanta", "30329");
			cities.add(city);
			city = new City(cityId++, getProvinceId("FL"), "Orlando", "32801");
			cities.add(city);
			city = new City(cityId++, getProvinceId("MD"), "Baltimore", "21224");
			cities.add(city);
			city = new City(cityId++, getProvinceId("NC"), "Charlotte", "28202");
			cities.add(city);
			city = new City(cityId++, getProvinceId("NJ"), "Hoboken", "07030");
			cities.add(city);
			city = new City(cityId++, getProvinceId("NY"), "Albany", "12205");
			cities.add(city);
			city = new City(cityId++, getProvinceId("SC"), "Columbia", "29201");
			cities.add(city);
			city = new City(cityId++, getProvinceId("VA"), "Roanoke", "24013");
			cities.add(city);
		}

		return cities;
	}

	public City getCityByName(String cityName) {
		List<City> cities = getCities();

		for (City city : cities) {

			if (city.getCityName().equals(cityName)) {
				return city;
			}
		}

		return null;
	}

	public City getCityByPostalCode(String postalCode) {
		List<City> cities = getCities();

		for (City city : cities) {

			if (city.getPostalCode().equals(postalCode)) {
				return city;
			}
		}

		return null;
	}

	public List<SelectItem> getFilteredCities() {
		return filteredCities;
	}

	public long getProvinceId(String provinceName) {
		long provinceId = 0;
		List<Province> provinces = getProvinces();

		for (Province province : provinces) {

			if (province.getProvinceName().equals(provinceName)) {
				provinceId = province.getProvinceId();

				break;
			}
		}

		return provinceId;
	}

	/**
	 * The getProvinceSelectItems() method is only necessary when using ice:selectOneMenu which is currently commented
	 * out in the applicant.xhtml composition via ui:remove
	 */
// public List<SelectItem> getProvinceSelectItems() {
//
// if (provinceSelectItems == null) {
// provinceSelectItems = new ArrayList<SelectItem>();
// for (Province province: getProvinces()) {
// SelectItem selectItem = new SelectItem(province.getProvinceId(), province.getProvinceName());
// provinceSelectItems.add(selectItem);
// }
// }
// return provinceSelectItems;
// }
	public List<Province> getProvinces() {

		if (provinces == null) {
			long provinceId = 1;
			provinces = new ArrayList<Province>();

			Province province = new Province(provinceId++, "DE");
			provinces.add(province);
			province = new Province(provinceId++, "GA");
			provinces.add(province);
			province = new Province(provinceId++, "FL");
			provinces.add(province);
			province = new Province(provinceId++, "MD");
			provinces.add(province);
			province = new Province(provinceId++, "NC");
			provinces.add(province);
			province = new Province(provinceId++, "NJ");
			provinces.add(province);
			province = new Province(provinceId++, "NY");
			provinces.add(province);
			province = new Province(provinceId++, "SC");
			provinces.add(province);
			province = new Province(provinceId++, "VA");
			provinces.add(province);
		}

		return this.provinces;
	}
}
