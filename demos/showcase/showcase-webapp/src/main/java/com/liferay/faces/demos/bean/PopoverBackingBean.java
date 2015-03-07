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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class PopoverBackingBean {

	private static final Logger logger = LoggerFactory.getLogger(PopoverBackingBean.class);

	@ManagedProperty(name = "popoverModelBean", value = "#{popoverModelBean}")
	private PopoverModelBean popoverModelBean;

	public void submit() {
		logger.info("submit: text1 = " + popoverModelBean.getText1());
		logger.info("submit: text2 = " + popoverModelBean.getText2());
	}

	public void setPopoverModelBean(PopoverModelBean popoverModelBean) {
		this.popoverModelBean = popoverModelBean;
	}
}
