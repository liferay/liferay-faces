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

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.liferay.faces.alloy.component.inputdate.DateSelectEvent;


/**
 * @author  Kyle Stiemann
 */
@ManagedBean
@RequestScoped
public class InputDateBackingBean {

	public void dateSelectListener(DateSelectEvent dateSelectEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Date selectedDate = dateSelectEvent.getDate();
		FacesMessage facesMessage = new FacesMessage("Received 'dateSelectEvent' for date with value '" + selectedDate +
				"' in the " + dateSelectEvent.getPhaseId().toString() + " phase.");
		facesContext.addMessage(null, facesMessage);
	}
}
