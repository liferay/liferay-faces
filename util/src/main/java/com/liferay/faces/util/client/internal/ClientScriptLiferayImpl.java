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

import java.io.StringWriter;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.context.ExtFacesContext;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.portal.LiferayPortletUtil;
import com.liferay.faces.util.portal.WebKeys;

import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;


/**
 * @author  Neil Griffin
 */
public class ClientScriptLiferayImpl extends ClientScriptLiferayCompatImpl {

	@Override
	public void append(String content, String use) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();

		if (scriptData == null) {

			scriptData = new ScriptData();
			requestMap.put(WebKeys.AUI_SCRIPT_DATA, scriptData);
		}

		String portletId = StringPool.BLANK;
		Object portlet = requestMap.get(WebKeys.RENDER_PORTLET);

		if (portlet != null) {
			portletId = LiferayPortletUtil.getPortletId(portlet);
		}

		appendScriptData(portletId, content, use);
	}

	@Override
	public void clear() {

		ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
		Map<String, Object> requestMap = extFacesContext.getExternalContext().getRequestMap();
		requestMap.remove(WebKeys.AUI_SCRIPT_DATA);
		scriptData = null;
	}

	@Override
	public String toString() {

		ClientScriptLiferayWriter clientScriptLiferayWriter = new ClientScriptLiferayWriter();

		if (scriptData != null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			writeScriptData(facesContext, clientScriptLiferayWriter);
		}

		return clientScriptLiferayWriter.toString();
	}

	private class ClientScriptLiferayWriter extends StringWriter {

		@Override
		public void write(String string) {

			if (!(string.startsWith("<script") || string.endsWith("script>"))) {
				super.write(string);
			}
		}
	}

}
