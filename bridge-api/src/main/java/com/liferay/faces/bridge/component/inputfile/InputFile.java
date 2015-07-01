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

//JSF 2.0+: import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import com.liferay.faces.bridge.event.FileUploadEvent;
import com.liferay.faces.util.component.ComponentStateHelper;
import com.liferay.faces.util.component.StateHelper;


/**
 * @author  Neil Griffin
 */
// JSF 2.0+: @FacesComponent(value = InputFile.COMPONENT_TYPE)
public class InputFile extends InputFileBase {

	// Private Data Members
	private StateHelper stateHelper;

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

	@Override
	public void restoreState(FacesContext facesContext, Object state) {

		Object[] values = (Object[]) state;
		super.restoreState(facesContext, values[0]);
		getStateHelper().restoreState(facesContext, values[1]);
	}

	@Override
	public Object saveState(FacesContext facesContext) {

		Object[] values = new Object[2];
		values[0] = super.saveState(facesContext);
		values[1] = getStateHelper().saveState(facesContext);

		return values;
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
	public StateHelper getStateHelper() {

		if (stateHelper == null) {
			stateHelper = new ComponentStateHelper(this);
		}

		return stateHelper;
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return concatCssClasses(styleClass, "bridge-input-file");
	}

}
