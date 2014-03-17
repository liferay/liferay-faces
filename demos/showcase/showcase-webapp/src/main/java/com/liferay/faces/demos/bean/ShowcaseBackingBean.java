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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class ShowcaseBackingBean {

	// Injections
	@ManagedProperty(value = "#{showcaseModelBean}")
	private ShowcaseModelBean showcaseModelBean;

	public void setShowcaseModelBean(ShowcaseModelBean showcaseModelBean) {
		this.showcaseModelBean = showcaseModelBean;
	}
}
