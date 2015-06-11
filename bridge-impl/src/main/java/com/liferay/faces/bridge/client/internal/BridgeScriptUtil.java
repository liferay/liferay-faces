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

import java.io.IOException;
import java.util.List;

import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.client.Script;


/**
 * @author  Kyle Stiemann
 */
public class BridgeScriptUtil {

	public static void writeScripts(ResponseWriter responseWriter, List<Script> scripts) throws IOException {

		for (Script script : scripts) {

			responseWriter.write("(function(){");
			responseWriter.write(script.getSourceCode());
			responseWriter.write("})();");
		}
	}
}
