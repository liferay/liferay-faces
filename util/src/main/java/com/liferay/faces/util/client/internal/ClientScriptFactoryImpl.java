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

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.client.ClientScriptFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.portal.WebKeys;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;

import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;


/**
 * @author  Neil Griffin
 */
public class ClientScriptFactoryImpl extends ClientScriptFactory {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	private static final String SCRIPT_DATA_FQCN = "com.liferay.portal.kernel.servlet.taglib.aui.ScriptData";

	@Override
	public ClientScript getClientScript(FacesContext facesContext) throws FacesException {

		ClientScript clientScript = null;

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		Object attributeValue = requestMap.get(WebKeys.AUI_SCRIPT_DATA);

		if (LIFERAY_PORTAL_DETECTED && !facesContext.getPartialViewContext().isAjaxRequest()) {

			Object scriptData = null;

			if (attributeValue == null) {

				try {
					Class<?> scriptDataClass = Class.forName(SCRIPT_DATA_FQCN);
					scriptData = scriptDataClass.newInstance();
				}
				catch (Exception e) {
					throw new FacesException(e);
				}

				requestMap.put(WebKeys.AUI_SCRIPT_DATA, scriptData);
			}
			else {
				scriptData = (ScriptData) attributeValue;
			}

			clientScript = new ClientScriptLiferayImpl(scriptData);
		}
		else {

			if (attributeValue == null) {

				BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
						BrowserSnifferFactory.class);
				BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(externalContext);
				boolean browserIE = browserSniffer.isIe();
				float browserMajorVersion = browserSniffer.getMajorVersion();
				clientScript = new ClientScriptImpl(browserIE, browserMajorVersion);
				requestMap.put(WebKeys.AUI_SCRIPT_DATA, clientScript);
			}
			else {
				clientScript = (ClientScript) attributeValue;
			}
		}

		return clientScript;
	}

	@Override
	public ClientScriptFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}

}
