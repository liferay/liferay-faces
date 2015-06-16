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
package com.liferay.faces.alloy.render.internal;

import com.liferay.faces.util.client.BrowserSniffer;


/**
 * @author  Kyle Stiemann
 */
public class AlloyRendererUtil {

	public static String getAlloyBeginScript(String[] modules, BrowserSniffer browserSniffer) {
		return getAlloyBeginScript(modules, null, browserSniffer);
	}

	public static String getAlloyBeginScript(String[] modules, String config, BrowserSniffer browserSniffer) {
		return getAlloyBeginScript(modules, config, browserSniffer.getMajorVersion(), browserSniffer.isIe());
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
