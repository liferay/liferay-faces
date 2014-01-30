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
package com.liferay.faces.demos.handler;

import javax.faces.context.FacesContext;
import javax.portlet.faces.BridgePublicRenderParameterHandler;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class CustomerSelectedHandler implements BridgePublicRenderParameterHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomerSelectedHandler.class);

	public void processUpdates(FacesContext facesContext) {
		logger.debug("Here is where you would perform any necessary processing of public render parameters");
	}

}
