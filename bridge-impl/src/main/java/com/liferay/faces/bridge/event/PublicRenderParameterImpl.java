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
package com.liferay.faces.bridge.event;

import javax.el.ELContext;
import javax.el.PropertyNotFoundException;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class PublicRenderParameterImpl extends PublicRenderParameterCompatImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PublicRenderParameter.class);

	// Private Data Members
	protected String modelValue;
	protected Boolean modelValueChanged;
	protected boolean modelValueRetrieved;

	public PublicRenderParameterImpl(String prefix, String originalRequestValue, String originalModelEL,
		String portletName) {
		super(prefix, originalRequestValue, originalModelEL, portletName);
	}

	public boolean isModelValueChanged(FacesContext facesContext) {

		if (modelValueChanged == null) {

			String retrievedModelValue = getModelValue(facesContext);

			if ((retrievedModelValue != null) && (originalRequestValue != null)) {
				modelValueChanged = !retrievedModelValue.equals(originalRequestValue);
			}
			else if (retrievedModelValue == null) {
				modelValueChanged = (originalRequestValue != null);
			}
			else if (originalRequestValue == null) {
				modelValueChanged = (retrievedModelValue != null);
			}
			else {
				modelValueChanged = Boolean.FALSE;
			}
		}

		return modelValueChanged;
	}

	public String getModelValue(FacesContext facesContext) {

		if (!modelValueRetrieved) {

			try {

				ELContext elContext = facesContext.getELContext();
				ValueExpression valueExpression = facesContext.getApplication().getExpressionFactory()
					.createValueExpression(elContext, modelEL, String.class);
				modelValue = (String) valueExpression.getValue(elContext);
			}
			catch (PropertyNotFoundException e) {
				String exceptionMessage = e.getMessage();

				if (exceptionMessage == null) {
					logger.error("javax.el.PropertyNotFoundException: model-el=[{0}]", modelEL);
				}
				else {
					logger.error("javax.el.PropertyNotFoundException: {0}: model-el=[{1}]", exceptionMessage, modelEL);
				}
			}

			modelValueRetrieved = true;
		}

		return modelValue;
	}

	public String getModifiedModelEL() {
		return modelEL;
	}

	public boolean isForThisPortlet() {
		return forThisPortlet;
	}
}
