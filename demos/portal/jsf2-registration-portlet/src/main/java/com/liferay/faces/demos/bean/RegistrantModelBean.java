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

import com.liferay.faces.demos.model.Registrant;
import com.liferay.faces.portal.context.LiferayFacesContext;


/**
 * This is a model managed bean that represents a user that is registering for an account.
 *
 * @author  "Neil Griffin"
 */
@ManagedBean(name = "registrantModelBean")
@ViewScoped
public class RegistrantModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 7459438254337818761L;

	// Private Data Members
	private Registrant registrant;

	public RegistrantModelBean() {
		registrant = new Registrant(LiferayFacesContext.getInstance().getCompanyId());
	}

	public Registrant getRegistrant() {
		return registrant;
	}
}
