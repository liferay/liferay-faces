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
package com.liferay.faces.bridge.component.inputfile;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import com.liferay.faces.bridge.event.FileUploadEvent;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = InputFile.COMPONENT_TYPE)
public class InputFile extends InputFileBase {

	@Override
	public void broadcast(FacesEvent facesEvent) throws AbortProcessingException {
		super.broadcast(facesEvent);

		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			MethodExpression methodExpression = getFileUploadListener();

			if ((methodExpression != null) && (facesEvent instanceof FileUploadEvent)) {
				methodExpression.invoke(facesContext.getELContext(), new Object[] { facesEvent });
			}
		}
		catch (Exception e) {
			throw new AbortProcessingException(e);
		}
	}

	private String concatCssClasses(String... classNames) {

		StringBuilder cssClassBuilder = new StringBuilder();
		boolean first = true;

		for (String className : classNames) {

			if (className != null) {

				if (!first) {
					cssClassBuilder.append(" ");
				}

				cssClassBuilder.append(className);
				first = false;
			}
		}

		String allClasses = cssClassBuilder.toString();

		if (allClasses.length() == 0) {
			allClasses = null;
		}

		return allClasses;
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return concatCssClasses(styleClass, "bridge-input-file");
	}
}
