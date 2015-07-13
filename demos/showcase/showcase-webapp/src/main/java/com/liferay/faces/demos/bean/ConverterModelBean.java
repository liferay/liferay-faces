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

import javax.annotation.PostConstruct;
// JSF 2+ import javax.faces.bean.ManagedBean;
// JSF 2+ import javax.faces.bean.ManagedProperty;
// JSF 2+ import javax.faces.bean.RequestScoped;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.dto.Country;
import com.liferay.faces.demos.service.CountryService;
import com.liferay.faces.demos.service.CustomerService;


/**
 * @author  Juan Gonzalez
 */
// JSF 2+ @ManagedBean
// JSF 2+ @RequestScoped
public class ConverterModelBean {

	// JSF 2+ @ManagedProperty(value = "#{countryService}")
	private CountryService countryService;

	// Private properties
	private Country country;

	@PostConstruct
	public void postConstruct() {
		this.country = getCountryService().getAllCountries().get(0);
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public CountryService getCountryService() {
		if (countryService == null) {
			countryService = getCService(FacesContext.getCurrentInstance());
		}

		return countryService;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	protected CountryService getCService(FacesContext facesContext) {

		Application application = facesContext.getApplication();
		ELResolver elResolver = application.getELResolver();
		ELContext elContext = facesContext.getELContext();

		return (CountryService) elResolver.getValue(elContext, null, "countryService");
	}

}
