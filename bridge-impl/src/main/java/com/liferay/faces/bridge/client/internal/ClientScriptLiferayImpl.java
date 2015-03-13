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

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.context.ExtFacesContext;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.portal.LiferayPortletUtil;
import com.liferay.faces.util.portal.ScriptData;
import com.liferay.faces.util.portal.WebKeys;



/**
 * @author  Neil Griffin
 */
public class ClientScriptLiferayImpl extends ClientScriptLiferayCompatImpl {

	// Private Data Members
	private ScriptData scriptData;

	// Java 1.6+ @Override
	public void append(String content) {
		append(content, null);
	}

	// Java 1.6+ @Override
	public void append(String content, String options) {

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

		appendScriptData(scriptData, portletId, content, options);
	}

	// Java 1.6+ @Override
	public void clear() {

		ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
		Map<String, Object> requestMap = extFacesContext.getExternalContext().getRequestMap();
		requestMap.remove(WebKeys.AUI_SCRIPT_DATA);
		scriptData = null;
	}

	@Override
	public String toString() {
		return getScriptText(FacesContext.getCurrentInstance(), scriptData);
	}
}
