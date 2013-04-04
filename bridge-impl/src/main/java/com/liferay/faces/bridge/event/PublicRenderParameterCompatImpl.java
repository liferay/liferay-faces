/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class PublicRenderParameterCompatImpl extends PublicRenderParameterBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PublicRenderParameterCompatImpl.class);

	public PublicRenderParameterCompatImpl(String prefix, String originalRequestValue, String originalModelEL,
		String portletName) {
		super(prefix, originalRequestValue, originalModelEL, portletName);
	}

	public boolean injectIntoModel(FacesContext facesContext) {

		try {

			ELContext elContext = facesContext.getELContext();
			ValueExpression valueExpression = facesContext.getApplication().getExpressionFactory()
				.createValueExpression(elContext, modelEL, String.class);
			valueExpression.setValue(elContext, originalRequestValue);

			return true;
		}
		catch (PropertyNotFoundException e) {
			String exceptionMessage = e.getMessage();

			if (exceptionMessage == null) {
				logger.error("javax.el.PropertyNotFoundException: model-el=[{0}]", modelEL);
			}
			else {
				logger.error("javax.el.PropertyNotFoundException: {0}: model-el=[{1}]", exceptionMessage, modelEL);
			}

			return false;
		}
	}

}
