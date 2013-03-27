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
package com.liferay.faces.issues.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name="as7LeakSessionScopeInjectedBean")
@SessionScoped
public class AS7LeakSessionScopeInjectedBean implements Serializable {

	private static final long serialVersionUID = 7840946974464938467L;

	// Injections
	@ManagedProperty(name = "applicationScopeBean", value = "#{applicationScopeBean}")
	private ApplicationScopeBean applicationScopeBean;

	// Private Data Members
	private String foo = this.toString();

	public void setApplicationScopeBean(ApplicationScopeBean applicationScopeBean) {
		this.applicationScopeBean = applicationScopeBean;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

}
