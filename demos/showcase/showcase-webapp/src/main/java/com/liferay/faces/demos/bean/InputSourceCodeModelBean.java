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
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class InputSourceCodeModelBean {

	private String mode;
	private String sourceCode;
	private String sourceText;

	public String getMode() {

		if (mode == null) {
			mode = "java";
		}

		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getSourceText() {

		if (sourceText == null) {

			// This model bean is @RequestScoped (rather than @ViewScoped) for the sake making the Showcase more
			// scalable. In order to provide a default value for a property in a @RequestScoped bean, it is necessary to
			// ensure that the default value is only assigned during the initial render (GET) of the page and not during
			// subsequent POST requests.
			FacesContext facesContext = FacesContext.getCurrentInstance();

			if (!facesContext.isPostback()) {

				sourceText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE xmldoc>\n<xmldoc>\n" +
					"\t<xmlelement>This is an editable XML document.</xmlElement>\n</xmldoc>";
			}
		}

		return sourceText;
	}

	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}
}
