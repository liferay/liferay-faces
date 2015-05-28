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

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.liferay.faces.demos.dto.LiferayBenefit;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "liferayBenefitService", eager = true)
@ApplicationScoped
public class LiferayBenefitServiceMockImpl implements LiferayBenefitService {

	private static final List<LiferayBenefit> LIFERAY_BENEFITS;

	static {
		LIFERAY_BENEFITS = new ArrayList<LiferayBenefit>();
		LIFERAY_BENEFITS.add(new LiferayBenefit(1, "Compatible", "Compatible With Your IT",
				"Liferay lets you reuse the enterprise software and skills you already have in-house.",
				"compatible.png"));
		LIFERAY_BENEFITS.add(new LiferayBenefit(2, "Enterprise Ready", "Enterprise Ready",
				"Liferay is designed for scalability, reliablity, and high performance both on-premise and in the cloud.",
				"enterprise.png"));
		LIFERAY_BENEFITS.add(new LiferayBenefit(3, "Powerful Integration", "Powerful Integration",
				"Liferay is designed for integrating with both enterprise systems and web-based resources.",
				"integration.png"));
		LIFERAY_BENEFITS.add(new LiferayBenefit(4, "Lightweight", "Lightweight and Agile",
				"With Liferay, projects are completed faster and with smaller budgets so you can see immediate results.",
				"lightweight.png"));
		LIFERAY_BENEFITS.add(new LiferayBenefit(5, "Open Source", "Built With Open Source",
				"Our open source community fosters innovation, increase security, and improves quality of the software.",
				"open-source.png"));
	}

	@Override
	public List<LiferayBenefit> getLiferayBenefits() {
		return LIFERAY_BENEFITS;
	}
}
