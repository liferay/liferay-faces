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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class InputSourceCodeBackingBean {

	private static final Logger logger = LoggerFactory.getLogger(InputSourceCodeBackingBean.class);

	@ManagedProperty(name = "inputSourceCodeModelBean", value = "#{inputSourceCodeModelBean}")
	private InputSourceCodeModelBean inputSourceCodeModelBean;

	public void submit() {

		String source = inputSourceCodeModelBean.getSourceCode();

		if (source == null) {
			source = inputSourceCodeModelBean.getSourceText();
		}

		logger.info("You entered text: " + source);
	}

	public void setInputSourceCodeModelBean(InputSourceCodeModelBean inputSourceCodeModelBean) {
		this.inputSourceCodeModelBean = inputSourceCodeModelBean;
	}
}
