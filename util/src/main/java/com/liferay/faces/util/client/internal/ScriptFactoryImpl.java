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
package com.liferay.faces.util.client.internal;

import com.liferay.faces.util.client.AlloyScript;
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.client.ScriptFactory;


/**
 * @author  Kyle Stiemann
 */
public class ScriptFactoryImpl extends ScriptFactory {

	@Override
	public AlloyScript getAlloyScript(String content, String[] modules) {
		return new AlloyScriptImpl(content, modules);
	}

	@Override
	public Script getScript(String content) {
		return new ScriptImpl(content);
	}

	@Override
	public ScriptFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
