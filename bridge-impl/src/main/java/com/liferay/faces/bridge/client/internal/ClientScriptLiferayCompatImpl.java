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
package com.liferay.faces.bridge.client.internal;

import java.io.StringWriter;

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

	protected void appendScriptData(ScriptData scriptData, String portletId, String content, String use) {
		scriptData.append(portletId, content, use);
	}

	protected String getScriptText(FacesContext facesContext, ScriptData scriptData) {

		ClientScriptWriter clientScriptWriter = new ClientScriptWriter();

		if (scriptData != null) {

			ExternalContext externalContext = facesContext.getExternalContext();
			HttpServletRequest httpServletRequest = PortalUtil.getHttpServeletRequest(externalContext);

			try {
				scriptData.writeTo(httpServletRequest, clientScriptWriter);
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return clientScriptWriter.toString();
	}

	private class ClientScriptWriter extends StringWriter {

		@Override
		public void write(String string) {

			if (!(string.startsWith("<script") || string.endsWith("script>"))) {
				super.write(string);
			}
		}
	}
}
