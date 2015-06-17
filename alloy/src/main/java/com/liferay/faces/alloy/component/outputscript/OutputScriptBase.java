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
package com.liferay.faces.alloy.component.outputscript;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIOutput;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class OutputScriptBase extends UIOutput {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.outputscript.OutputScript";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.outputscript.OutputScriptRenderer";

	// Protected Enumerations
	protected enum OutputScriptPropertyKeys {
		library,
		name,
		target,
		use
	}

	public OutputScriptBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getLibrary() {
		return (String) getStateHelper().eval(OutputScriptPropertyKeys.library, null);
	}

	public void setLibrary(String library) {
		getStateHelper().put(OutputScriptPropertyKeys.library, library);
	}

	public String getName() {
		return (String) getStateHelper().eval(OutputScriptPropertyKeys.name, null);
	}

	public void setName(String name) {
		getStateHelper().put(OutputScriptPropertyKeys.name, name);
	}

	public String getTarget() {
		return (String) getStateHelper().eval(OutputScriptPropertyKeys.target, null);
	}

	public void setTarget(String target) {
		getStateHelper().put(OutputScriptPropertyKeys.target, target);
	}

	public String getUse() {
		return (String) getStateHelper().eval(OutputScriptPropertyKeys.use, null);
	}

	public void setUse(String use) {
		getStateHelper().put(OutputScriptPropertyKeys.use, use);
	}
}
//J+
