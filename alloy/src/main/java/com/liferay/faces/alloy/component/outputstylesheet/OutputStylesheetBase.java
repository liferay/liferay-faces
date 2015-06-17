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
package com.liferay.faces.alloy.component.outputstylesheet;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIOutput;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class OutputStylesheetBase extends UIOutput {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.outputstylesheet.OutputStylesheet";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.outputstylesheet.OutputStylesheetRenderer";

	// Protected Enumerations
	protected enum OutputStylesheetPropertyKeys {
		library,
		media,
		name
	}

	public OutputStylesheetBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getLibrary() {
		return (String) getStateHelper().eval(OutputStylesheetPropertyKeys.library, null);
	}

	public void setLibrary(String library) {
		getStateHelper().put(OutputStylesheetPropertyKeys.library, library);
	}

	public String getMedia() {
		return (String) getStateHelper().eval(OutputStylesheetPropertyKeys.media, null);
	}

	public void setMedia(String media) {
		getStateHelper().put(OutputStylesheetPropertyKeys.media, media);
	}

	public String getName() {
		return (String) getStateHelper().eval(OutputStylesheetPropertyKeys.name, null);
	}

	public void setName(String name) {
		getStateHelper().put(OutputStylesheetPropertyKeys.name, name);
	}
}
//J+
