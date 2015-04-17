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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.liferay.portal.kernel.captcha.CaptchaUtil;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@RequestScoped
public class CaptchaBackingBean {

	private String captchaText;

	public String validateAction() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		FacesMessage facesMessage = new FacesMessage("All validations passed. You entered right captcha");
		facesContext.addMessage(null, facesMessage);

		return null;
	}

	public String getCaptchaImpl() {
		return CaptchaUtil.getCaptcha().getClass().getName();
	}

	public String getCaptchaText() {
		return captchaText;
	}

	public void setCaptchaText(String captchaText) {
		this.captchaText = captchaText;
	}
}
