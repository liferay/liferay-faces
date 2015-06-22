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

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.liferay.faces.alloy.component.tab.TabSelectEvent;
import com.liferay.faces.alloy.component.tabview.TabView;
import javax.faces.event.AjaxBehaviorEvent;


/**
 * @author  Vernon Singleton
 */
@RequestScoped
@ManagedBean
public class TabViewBackingBean {

	private TabView tabView;

	public TabViewBackingBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
		String componentUseCase = requestParameterMap.get("componentUseCase");

		if ("selected-tab".equals(componentUseCase)) {
			FacesMessage facesMessage = new FacesMessage("The default value of selectedIndex=" +
					getDefaultSelectedIndex());
			facesContext.addMessage(null, facesMessage);
		}
	}

	public void submit() {

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Integer selectedIndex = tabView.getSelectedIndex();
			FacesMessage facesMessage = new FacesMessage("The client-side state of selectedIndex=" + selectedIndex);
			facesContext.addMessage(null, facesMessage);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tabSelectListener(AjaxBehaviorEvent ajaxBehaviorEvent) {

		TabSelectEvent tabSelectEvent = (TabSelectEvent) ajaxBehaviorEvent;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage("Received 'tabSelectEvent' for tab with header '" +
				tabSelectEvent.getTab().getHeaderText() + "' in the " + tabSelectEvent.getPhaseId().getName() +
				" phase.");
		facesContext.addMessage(null, facesMessage);
	}

	public int getDefaultSelectedIndex() {
		return 2; // The "Powerful Integration" tab is selected by default.
	}

	public TabView getTabView() {
		return tabView;
	}

	public void setTabView(TabView tabView) {

		// Injected via alloy:tabView binding attribute
		this.tabView = tabView;
	}
}
