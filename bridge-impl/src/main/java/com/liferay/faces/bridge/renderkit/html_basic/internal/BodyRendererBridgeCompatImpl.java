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
package com.liferay.faces.bridge.renderkit.html_basic.internal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.bridge.client.internal.BridgeScriptUtil;
import com.liferay.faces.bridge.client.internal.ScriptDataUtil;
import com.liferay.faces.bridge.renderkit.bridge.internal.BridgeRenderer;
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.WebKeys;


/**
 * @author  Kyle Stiemann
 */
public class BodyRendererBridgeCompatImpl extends BridgeRenderer {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	protected void encodeScripts(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {

		FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
		List<Script> scripts = facesRequestContext.getScripts();

		if (LIFERAY_PORTAL_DETECTED) {

			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			ScriptData scriptData = (ScriptData) requestMap.get(WebKeys.AUI_SCRIPT_DATA);

			if (scriptData == null) {

				scriptData = new ScriptData();
				requestMap.put(WebKeys.AUI_SCRIPT_DATA, scriptData);
			}

			ScriptDataUtil.scriptDataAppendScripts(scriptData, requestMap, scripts);
		}
		else {

			responseWriter.startElement("script", uiComponent);
			responseWriter.writeAttribute("type", "text/javascript", null);
			BridgeScriptUtil.writeScripts(responseWriter, scripts);
			responseWriter.endElement("script");
		}
	}
}
