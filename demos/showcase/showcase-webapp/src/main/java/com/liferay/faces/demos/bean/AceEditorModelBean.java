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
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class AceEditorModelBean {

	private String mode;
	private String text;

	public String getMode() {

		if (mode == null) {
			mode = "xml";
		}

		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getText() {

		if (text == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			if (!facesContext.isPostback() && getMode().equals("xml")) {

				text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE xmldoc>\n<xmldoc>\n" +
					"\t<xmlelement>This is an editable XML document.</xmlElement>\n</xmldoc>";
			}
		}

		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
