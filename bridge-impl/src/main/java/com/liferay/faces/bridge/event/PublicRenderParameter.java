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
package com.liferay.faces.bridge.event;

import javax.el.ELContext;
import javax.el.PropertyNotFoundException;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class PublicRenderParameter {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PublicRenderParameter.class);

	// Private Data Members
	private FacesContext facesContext;
	private boolean forThisPortlet;
	private String modelValue;
	private Boolean modelValueChanged;
	private boolean modelValueRetrieved;
	private String modifiedModelEL;
	private String originalRequestValue;

	public PublicRenderParameter(FacesContext facesContext, String originalRequestValue, String originalModelEL,
		String portletName) {

		this.facesContext = facesContext;
		this.originalRequestValue = originalRequestValue;
		this.modifiedModelEL = originalModelEL;

		// Assume that the parameter name is not prefixed with a portlet name, and that we are to process the model
		// expression.
		this.forThisPortlet = true;

		// If the parameter name is indeed prefixed, then according to section 5.3.1 the value should only be injected
		// into the model if the prefix matches the current portlet name.
		int colonPos = originalModelEL.indexOf(':');

		if (colonPos > 0) {
			this.forThisPortlet = false;

			int openCurlyBracePos = originalModelEL.indexOf('{');

			if (openCurlyBracePos > 0) {
				String prefixedPortletName = originalModelEL.substring(openCurlyBracePos + 1, colonPos);
				logger.trace("portletName=[{0}] prefixedPortletName=[{1}]", portletName, prefixedPortletName);

				if ((prefixedPortletName != null) && (prefixedPortletName.length() > 0)) {

					this.modifiedModelEL = originalModelEL.substring(0, openCurlyBracePos + 1) +
						originalModelEL.substring(colonPos + 1);

					logger.trace(
						"Stripped prefixedPortletName=[{0}] from originalModelEL=[{1}] so that modifiedModelEL=[{2}]",
						prefixedPortletName, originalModelEL, modifiedModelEL);

					if (portletName.equals(prefixedPortletName)) {
						this.forThisPortlet = true;
					}
				}
			}
		}
	}

	public boolean injectIntoModel() {

		try {

			ELContext elContext = facesContext.getELContext();
			ValueExpression valueExpression = facesContext.getApplication().getExpressionFactory()
				.createValueExpression(elContext, modifiedModelEL, String.class);
			valueExpression.setValue(elContext, originalRequestValue);

			return true;
		}
		catch (PropertyNotFoundException e) {
			String exceptionMessage = e.getMessage();

			if (exceptionMessage == null) {
				logger.error("javax.el.PropertyNotFoundException: model-el=[{0}]", modifiedModelEL);
			}
			else {
				logger.error("javax.el.PropertyNotFoundException: {0}: model-el=[{1}]", exceptionMessage,
					modifiedModelEL);
			}

			return false;
		}
	}

	public boolean isModelValueChanged() {

		if (modelValueChanged == null) {

			String retrievedModelValue = getModelValue();

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

	public String getModelValue() {

		if (!modelValueRetrieved) {

			try {

				ELContext elContext = facesContext.getELContext();
				ValueExpression valueExpression = facesContext.getApplication().getExpressionFactory()
					.createValueExpression(elContext, modifiedModelEL, String.class);
				modelValue = (String) valueExpression.getValue(elContext);
			}
			catch (PropertyNotFoundException e) {
				String exceptionMessage = e.getMessage();

				if (exceptionMessage == null) {
					logger.error("javax.el.PropertyNotFoundException: model-el=[{0}]", modifiedModelEL);
				}
				else {
					logger.error("javax.el.PropertyNotFoundException: {0}: model-el=[{1}]", exceptionMessage,
						modifiedModelEL);
				}
			}

			modelValueRetrieved = true;
		}

		return modelValue;
	}

	public String getModifiedModelEL() {
		return modifiedModelEL;
	}

	public boolean isForThisPortlet() {
		return forThisPortlet;
	}
}
