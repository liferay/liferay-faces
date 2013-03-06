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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 * This class serves as a bean that remembers the rendered/non-rendered state of the list and form in the users.xhtml
 * Facelet view.
 *
 * @author  Neil Griffin
 */
@ManagedBean(name = "usersViewBean")
@ViewScoped
public class UsersViewBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 351876134442958472L;

	// Private Data Members
	private boolean formRendered = false;

	public boolean isFormRendered() {
		return formRendered;
	}

	public void setFormRendered(boolean formRendered) {
		this.formRendered = formRendered;
	}

}
