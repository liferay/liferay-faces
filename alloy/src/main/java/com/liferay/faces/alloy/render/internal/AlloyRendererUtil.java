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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.application.Application;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.client.AlloyScript;
import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Kyle Stiemann
 */
public class AlloyRendererUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AlloyRendererUtil.class);

	// Private Constants
	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	public static void addDefaultAjaxBehavior(ClientBehaviorHolder clientBehaviorHolder, String execute, String process,
		String defaultExecute, String render, String update, String defaultRender) {

		Map<String, List<ClientBehavior>> clientBehaviorMap = clientBehaviorHolder.getClientBehaviors();
		String defaultEventName = clientBehaviorHolder.getDefaultEventName();
		List<ClientBehavior> clientBehaviors = clientBehaviorMap.get(defaultEventName);

		boolean doAdd = true;

		if (clientBehaviors != null) {

			for (ClientBehavior clientBehavior : clientBehaviors) {

				if (clientBehavior instanceof AjaxBehavior) {
					doAdd = false;

					break;
				}
			}
		}

		if (doAdd) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Application application = facesContext.getApplication();
			AjaxBehavior ajaxBehavior = (AjaxBehavior) application.createBehavior(AjaxBehavior.BEHAVIOR_ID);
			Collection<String> executeIds = getExecuteIds(execute, process, defaultExecute);
			ajaxBehavior.setExecute(executeIds);

			Collection<String> renderIds = getRenderIds(render, update, defaultRender);
			ajaxBehavior.setRender(renderIds);
			clientBehaviorHolder.addClientBehavior(defaultEventName, ajaxBehavior);
		}
	}

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

			String alloyBeginScript = getAlloyBeginScript(allModules.toArray(new String[] {}), browserSniffer);
			responseWriter.write(alloyBeginScript);

			for (AlloyScript alloyScript : alloyScripts) {

				responseWriter.write("(function(){");
				responseWriter.write(alloyScript.getSourceCode());
				responseWriter.write("})();");
			}

			responseWriter.write("});");
		}
	}

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

	private static Collection<String> getExecuteIds(String execute, String process, String defaultValue) {

		// If the values of the execute and process attributes differ, then
		if (!execute.equals(process)) {

			// If the process attribute was specified and the execute attribute was omitted, then use the value of the
			// process attribute.
			if (execute.equals(defaultValue)) {
				execute = process;
			}

			// Otherwise, if both the execute and process attributes were specified with different values, then log a
			// warning indicating that the value of the execute attribute takes precedence.
			else if (!process.equals(defaultValue)) {
				logger.warn(
					"Different values were specified for the execute=[{0}] and process=[{0}]. The value for execute takes precedence.");
			}
		}

		return Arrays.asList(execute.split(" "));
	}

	private static Collection<String> getRenderIds(String render, String update, String defaultValue) {

		// If the values of the render and update attributes differ, then
		if (!render.equals(update)) {

			// If the update attribute was specified and the render attribute was omitted, then use the value of the
			// update attribute.
			if (render.equals(defaultValue)) {
				render = update;
			}

			// Otherwise, if both the render and update attributes were specified with different values, then log a
			// warning indicating that the value of the render attribute takes precedence.
			else if (!update.equals(defaultValue)) {
				logger.warn(
					"Different values were specified for the render=[{0}] and update=[{0}]. The value for render takes precedence.");
			}
		}

		return Arrays.asList(render.split(" "));
	}
}
