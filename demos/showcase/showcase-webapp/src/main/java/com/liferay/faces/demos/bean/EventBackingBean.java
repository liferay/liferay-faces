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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@RequestScoped
public class EventBackingBean {

	private StringBuilder text = new StringBuilder();

	public void preRenderComponent(ComponentSystemEvent event) throws AbortProcessingException {
		text.append("Component with id " + event.getComponent().getId() + " generated an event.<br/>");
	}

	public String getText() {
		return text.toString();
	}
}
