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
package com.liferay.faces.issues.bean;

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

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@ViewScoped
public class BackingBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2947548873495692163L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BackingBean.class);

	// Injections
	@ManagedProperty(value = "#{modelBean}")
	private transient ModelBean modelBean;

	// Properties
	private boolean editor1Rendered = true;
	private boolean editor2Rendered = true;

	public void submit() {

		logger.debug("Submitted form");
		logger.debug("comments1=" + modelBean.getComments1());
		logger.debug("comments2=" + modelBean.getComments2());
		logger.debug("comments3=" + modelBean.getComments3());

		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
		liferayFacesContext.addGlobalSuccessInfoMessage();
	}

	public void toggleEditor1() {
		editor1Rendered = !editor1Rendered;
	}

	public void toggleEditor2() {
		editor2Rendered = !editor2Rendered;
	}

	public boolean isEditor1Rendered() {
		return editor1Rendered;
	}

	public boolean isEditor2Rendered() {
		return editor2Rendered;
	}

	public void setModelBean(ModelBean modelBean) {

		// Injected via @ManagedProperty annotation
		this.modelBean = modelBean;
	}
}
