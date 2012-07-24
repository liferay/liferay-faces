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
package com.liferay.faces.bridge.context;

import java.io.IOException;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class IncongruityContextImpl extends IncongruityContextCompatImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(IncongruityContextImpl.class);

	@Override
	public void makeCongruous(FacesContext facesContext) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();

		Set<IncongruousAction> incongruousActions = getIncongruousActions();

		for (IncongruousAction incongruousAction : incongruousActions) {

			if (incongruousAction == IncongruousAction.SET_REQUEST_CHARACTER_ENCODING) {
				String requestCharacterEncoding = getRequestCharacterEncoding();
				logger.debug("setRequestCharacterEncoding(\"{0}\")", requestCharacterEncoding);
				externalContext.setRequestCharacterEncoding(requestCharacterEncoding);
			}
			else if (incongruousAction == IncongruousAction.SET_RESPONSE_CHARACTER_ENCODING) {
				String responseCharacterEncoding = getResponseCharacterEncoding();
				logger.debug("setResponseCharacterEncoding(\"{0}\")", responseCharacterEncoding);
				externalContext.setResponseCharacterEncoding(responseCharacterEncoding);
			}
			else {
				makeCongruousJSF2(externalContext, incongruousAction);
			}
		}
	}
}
