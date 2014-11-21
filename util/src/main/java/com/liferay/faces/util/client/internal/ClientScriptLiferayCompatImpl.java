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
package com.liferay.faces.util.client.internal;

import java.io.Writer;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.portal.PortalUtil;

import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;


/**
 * @author  Neil Griffin
 */
public abstract class ClientScriptLiferayCompatImpl implements ClientScript {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ClientScriptLiferayCompatImpl.class);

	// Protected Data Members
	protected ScriptData scriptData;

	protected void appendScriptData(String portletId, String content, String use) {
		scriptData.append(portletId, content, use);
	}

	protected void writeScriptData(FacesContext facesContext, Writer writer) {

		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest httpServeletRequest = PortalUtil.getHttpServeletRequest(externalContext);

		try {
			scriptData.writeTo(httpServeletRequest, writer);
		}
		catch (Exception e) {
			logger.error(e);
		}
	}
}
