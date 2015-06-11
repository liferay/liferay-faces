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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.client.AlloyScript;
import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Kyle Stiemann
 */
public class AlloyScriptUtil {

	// Private Constants
	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	public static void writeScripts(ResponseWriter responseWriter, List<Script> scripts, BrowserSniffer browserSniffer)
		throws IOException {

		Set<String> allModules = new TreeSet<String>();
		List<AlloyScript> alloyScripts = new ArrayList<AlloyScript>();
		List<Script> basicScripts = new ArrayList<Script>();

		for (Script script : scripts) {

			if (script instanceof AlloyScript) {

				AlloyScript alloyScript = (AlloyScript) script;
				final String[] modules = alloyScript.getModules();
				Collections.addAll(allModules, modules);
				alloyScripts.add(alloyScript);
			}
			else {
				basicScripts.add(script);
			}
		}

		for (Script script : basicScripts) {
			responseWriter.write(script.getSourceCode());
		}

		if (!alloyScripts.isEmpty()) {

			String alloyBeginScript = getAlloyBeginScript(browserSniffer, allModules.toArray(new String[] {}));
			responseWriter.write(alloyBeginScript);

			for (AlloyScript alloyScript : alloyScripts) {

				responseWriter.write("(function(){");
				responseWriter.write(alloyScript.getSourceCode());
				responseWriter.write("})();");
			}

			responseWriter.write("});");
		}
	}

	public static String getAlloyBeginScript(BrowserSniffer browserSniffer, String[] modules) {
		return getAlloyBeginScript(browserSniffer, modules, null);
	}

	public static String getAlloyBeginScript(BrowserSniffer browserSniffer, String[] modules, String config) {

		boolean browserIE = false;
		float browserMajorVersion = 1;

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
