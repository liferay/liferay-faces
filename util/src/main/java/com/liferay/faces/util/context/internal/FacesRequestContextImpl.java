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
package com.liferay.faces.util.context.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.client.ScriptFactory;
import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 */
public class FacesRequestContextImpl extends FacesRequestContext {

	// Private Members
	private List<Script> scripts;

	public FacesRequestContextImpl() {
		scripts = new ArrayList<Script>();
	}

	@Override
	public void addScript(Script script) {
		scripts.add(script);
	}

	@Override
	public void addScript(String script) {

		ScriptFactory scriptFactory = (ScriptFactory) FactoryExtensionFinder.getFactory(ScriptFactory.class);
		scripts.add(scriptFactory.getScript(script));
	}

	@Override
	public void release() {
		scripts = null;
	}

	@Override
	public List<Script> getScripts() {
		return Collections.unmodifiableList(scripts);
	}
}
