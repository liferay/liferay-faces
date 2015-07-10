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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@RequestScoped
public class ResetValuesBackingBean {

	private String requiredText1;
	private String requiredText2;

	public void actionListener() {
		postConstruct();
	}

	@PostConstruct
	public void postConstruct() {
		this.requiredText1 = null;
		this.requiredText2 = null;
	}

	public String getRequiredText1() {
		return requiredText1;
	}

	public void setRequiredText1(String requiredText) {
		this.requiredText1 = requiredText;
	}

	public String getRequiredText2() {
		return requiredText2;
	}

	public void setRequiredText2(String nonRequiredText) {
		this.requiredText2 = nonRequiredText;
	}

}
