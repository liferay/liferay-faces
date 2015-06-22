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
package com.liferay.faces.alloy.component.accordion.internal;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.accordion.Accordion;

import com.liferay.faces.alloy.render.internal.AlloyRendererBase;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class AccordionRendererBase extends AlloyRendererBase {

	// Protected Constants
	protected static final String CLIENT_KEY = "clientKey";
	protected static final String CLOSE_ALL_ON_EXPAND = "closeAllOnExpand";
	protected static final String SELECTED_INDEX = "selectedIndex";
	protected static final String STYLE = "style";
	protected static final String STYLE_CLASS = "styleClass";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "TogglerDelegate";
	private static final String ALLOY_MODULE_NAME = "aui-toggler";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		Accordion accordion = (Accordion) uiComponent;
		boolean first = true;

		encodeHiddenAttributes(facesContext, responseWriter, accordion, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, Accordion accordion, boolean first) throws IOException {
		// no-op
	}
}
//J+
