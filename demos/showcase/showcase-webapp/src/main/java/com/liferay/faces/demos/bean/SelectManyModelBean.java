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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.liferay.faces.demos.dto.LiferayBenefit;
import com.liferay.faces.demos.service.LiferayBenefitService;


/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class SelectManyModelBean {

	private List<Long> favoriteIds;
	private List<Long> benefitIds = Arrays.asList(2L, 4L);
	private List<Date> dates;
	private String phase;

	@ManagedProperty(name = "liferayBenefitService", value = "#{liferayBenefitService}")
	private LiferayBenefitService liferayBenefitService;

	public List<Long> getBenefitIds() {
		return benefitIds;
	}

	public void setBenefitIds(List<Long> benefitIds) {
		this.benefitIds = benefitIds;
	}

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	public List<Long> getFavoriteIds() {
		return favoriteIds;
	}

	public void setFavoriteIds(List<Long> favoriteIds) {
		this.favoriteIds = favoriteIds;
	}

	public List<LiferayBenefit> getLiferayBenefits() {
		return liferayBenefitService.getLiferayBenefits();
	}

	public void setLiferayBenefitService(LiferayBenefitService liferayBenefitService) {
		this.liferayBenefitService = liferayBenefitService;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}
}
