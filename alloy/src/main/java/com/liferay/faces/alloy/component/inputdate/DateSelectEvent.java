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
package com.liferay.faces.alloy.component.inputdate;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;


/**
 * @author  Kyle Stiemann
 */
public class DateSelectEvent extends AjaxBehaviorEvent {

	// Public Constants
	public static final String DATE_SELECT = "dateSelect";

	/// serialVersionUID
	private static final long serialVersionUID = 7578789532730713582L;

	// Private Members
	private Date date;

	public DateSelectEvent(UIComponent uiComponent, Behavior behavior, Date date) {
		super(uiComponent, behavior);
		this.date = date;
		setPhaseId(PhaseId.PROCESS_VALIDATIONS);
	}

	public Date getDate() {
		return date;
	}
}
