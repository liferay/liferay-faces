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
package com.liferay.faces.demos.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@Named
@FlowScoped("survey")
public class SurveyFlowModelBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SurveyFlowModelBean.class);

	@Inject
	ScopeTrackingBean scopeTrackingBean;

	// Private Data Members
	private String answer1;
	private String answer2;
	private Customer customer;

	@PostConstruct
	public void postConstruct() {
		logger.debug("SurveyFlowModelBean initialized!");
		scopeTrackingBean.setSurveyFlowModelBeanInScope(true);
	}

	@PreDestroy
	public void preDestroy() {
		logger.debug("SurveyFlowModelBean going out of scope!");
		scopeTrackingBean.setSurveyFlowModelBeanInScope(false);
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
