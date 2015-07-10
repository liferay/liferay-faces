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

// JSF 2+ import javax.faces.bean.ManagedBean;
// JSF 2+ import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;


/**
 * @author  Juan Gonzalez
 */
// JSF 2+ @ManagedBean
// JSF 2+ @RequestScoped
public class JSTLBackingBean {

	private boolean rendered;

	public void toggleRendered(ActionEvent event) {
		this.rendered = !rendered;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

}
