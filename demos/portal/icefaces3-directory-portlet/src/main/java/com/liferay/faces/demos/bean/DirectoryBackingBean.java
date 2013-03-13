/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import javax.faces.component.UIData;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.liferay.faces.portal.context.LiferayFacesContext;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "directoryBackingBean")
@RequestScoped
public class DirectoryBackingBean {

	// Injections
	@ManagedProperty(name = "directoryModelBean", value = "#{directoryModelBean}")
	private DirectoryModelBean directoryModelBean;

	// Private Data Members
	private SearchActionListener searchActionListener = new SearchActionListener();

	public void setDirectoryModelBean(DirectoryModelBean directoryModelBean) {

		// Injected via ManagedProperty annotation
		this.directoryModelBean = directoryModelBean;
	}

	public SearchActionListener getSearchActionListener() {
		return searchActionListener;
	}

	protected class SearchActionListener implements ActionListener {
		
		public void processAction(ActionEvent event) throws AbortProcessingException {

			directoryModelBean.forceListReload();
			
			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
			UIData uiData = (UIData) liferayFacesContext.matchComponentInViewRoot("users");
			uiData.setFirst(0);
		}

	}

}
