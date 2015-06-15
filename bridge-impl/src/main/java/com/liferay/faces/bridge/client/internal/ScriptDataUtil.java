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

import java.util.List;
import java.util.Map;

import com.liferay.faces.util.client.AlloyScript;
import com.liferay.faces.util.client.Script;

import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;


/**
 * @author  Kyle Stiemann
 */
public class ScriptDataUtil extends ScriptDataUtilCompat {

	public static void scriptDataAppendScripts(ScriptData scriptData, Map<String, Object> requestMap,
		List<Script> scripts) {

		String portletId = "";
		Object portletObject = requestMap.get(WebKeys.RENDER_PORTLET);

		if ((portletObject != null) && (portletObject instanceof Portlet)) {

			Portlet portlet = (Portlet) portletObject;
			portletId = portlet.getPortletId();
		}

		StringBuilder modulesStringBuilder = new StringBuilder();
		boolean firstModule = true;

		for (Script script : scripts) {

			if (script instanceof AlloyScript) {

				AlloyScript alloyScript = (AlloyScript) script;
				final String[] modules = alloyScript.getModules();

				for (String module : modules) {

					if (!firstModule) {
						modulesStringBuilder.append(",");
					}

					modulesStringBuilder.append(module);
					firstModule = false;
				}

				scriptDataAppend(scriptData, portletId, alloyScript.getSourceCode(), modulesStringBuilder.toString());
			}
			else {
				scriptDataAppend(scriptData, portletId, script.getSourceCode(), null);
			}
		}
	}
}
