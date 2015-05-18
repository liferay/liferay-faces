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

package com.liferay.faces.alloy.client.internal;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.client.ClientScriptFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;
import javax.faces.context.FacesContext;


/**
 * @author  Kyle Stiemann
 */
public class AlloyClientScriptUtil {

	// Private Constants
	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	public static void renderScript(String script, String use) {

		ClientScriptFactory clientScriptFactory = (ClientScriptFactory) FactoryExtensionFinder.getFactory(
				ClientScriptFactory.class);
		ClientScript clientScript = clientScriptFactory.getClientScript();
		clientScript.append(script, use);
	}

	public static String getAlloyBeginScript(FacesContext facesContext, String[] modules) {
		return getAlloyBeginScript(facesContext, modules, null);
	}

	public static String getAlloyBeginScript(FacesContext facesContext, String[] modules, String config) {

		boolean browserIE = false;
		float browserMajorVersion = 1;

		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());

		if (LIFERAY_PORTAL_DETECTED) {
			browserIE = browserSniffer.isIe();
			browserMajorVersion = browserSniffer.getMajorVersion();
		}
		else if (LIFERAY_FACES_BRIDGE_DETECTED) {
			// no-op because there is no way to obtain the underlying HttpServletRequest.
		}
		else {
			browserIE = browserSniffer.isIe();
			browserMajorVersion = browserSniffer.getMajorVersion();
		}

		return getAlloyBeginScript(modules, config, browserMajorVersion, browserIE);
	}

	public static String getAlloyBeginScript(String[] modules, float browserMajorVersion, boolean browserIE) {
		return getAlloyBeginScript(modules, null, browserMajorVersion, browserIE);
	}

	private static String getAlloyBeginScript(String[] modules, String config, float browserMajorVersion,
		boolean browserIE) {

		StringBuilder stringBuilder = new StringBuilder();
		String loadMethod = "use";

		if (browserIE && (browserMajorVersion < 8)) {
			loadMethod = "ready";
		}

		// If there is config render a YUI sandbox to avoid using the preconfigured AUI sandbox in Liferay Portal.
		if ((config != null) && (config.length() > 0)) {

			stringBuilder.append("YUI(");
			stringBuilder.append(config);
		}
		else {

			stringBuilder.append("AUI(");
		}

		stringBuilder.append(").");
		stringBuilder.append(loadMethod);
		stringBuilder.append("(");

		if (modules != null) {

			for (String module : modules) {
				stringBuilder.append("'");
				stringBuilder.append(module.trim());
				stringBuilder.append("',");
			}
		}

		stringBuilder.append("function(A){");

		return stringBuilder.toString();
	}
}
