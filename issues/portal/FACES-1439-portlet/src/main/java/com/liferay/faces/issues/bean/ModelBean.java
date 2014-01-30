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
package com.liferay.faces.issues.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@ViewScoped
public class ModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 8535723765072680249L;

	// Private Data Members
	private String comments1;
	private String comments2;
	private String comments3;

	public ModelBean() {
		clearProperties();
	}

	public void clearProperties() {
		comments1 = null;
		comments2 = null;
		comments3 = null;
	}

	public String getComments1() {
		return comments1;
	}

	public void setComments1(String comments1) {
		this.comments1 = comments1;
	}

	public String getComments2() {
		return comments2;
	}

	public void setComments2(String comments2) {
		this.comments2 = comments2;
	}

	public String getComments3() {
		return comments3;
	}

	public void setComments3(String comments3) {
		this.comments3 = comments3;
	}
}
