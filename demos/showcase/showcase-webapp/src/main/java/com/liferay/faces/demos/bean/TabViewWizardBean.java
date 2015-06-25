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

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.liferay.faces.alloy.component.tab.TabSelectEvent;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@ViewScoped
public class TabViewWizardBean implements Serializable {

	private static final long serialVersionUID = 101469870238569865L;

	private Integer selectedTabIndex = 0;
	private String firstName;
	private String lastName;

	public void tabSelectListener(AjaxBehaviorEvent ajaxBehaviorEvent) {

		// When using JSF 2.2, this cast is unnecessary, and the method can take the TabSelectEvent directly.
		TabSelectEvent tabSelectEvent = (TabSelectEvent) ajaxBehaviorEvent;

		// When the user clicks on a tab, skip the PROCESS_VALIDATIONS, UPDATE_MODEL_VAUES, and INVOKE_APPLICATION
		// phases of the JSF lifecycle and jump immediately to RENDER_RESPONSE.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.renderResponse();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public void setSelectedTabIndex(Integer selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}
}
