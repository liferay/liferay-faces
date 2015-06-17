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
package com.liferay.faces.alloy.component.commandlink;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlCommandLink;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class CommandLinkBase extends HtmlCommandLink implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.commandlink.CommandLink";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.commandlink.CommandLinkRenderer";

	// Protected Enumerations
	protected enum CommandLinkPropertyKeys {
		ajax,
		execute,
		process,
		render,
		styleClass,
		update
	}

	public CommandLinkBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isAjax() {
		return (Boolean) getStateHelper().eval(CommandLinkPropertyKeys.ajax, true);
	}

	public void setAjax(boolean ajax) {
		getStateHelper().put(CommandLinkPropertyKeys.ajax, ajax);
	}

	public String getExecute() {
		return (String) getStateHelper().eval(CommandLinkPropertyKeys.execute, "@all");
	}

	public void setExecute(String execute) {
		getStateHelper().put(CommandLinkPropertyKeys.execute, execute);
	}

	public String getProcess() {
		return (String) getStateHelper().eval(CommandLinkPropertyKeys.process, getExecute());
	}

	public void setProcess(String process) {
		getStateHelper().put(CommandLinkPropertyKeys.process, process);
	}

	public String getRender() {
		return (String) getStateHelper().eval(CommandLinkPropertyKeys.render, "@none");
	}

	public void setRender(String render) {
		getStateHelper().put(CommandLinkPropertyKeys.render, render);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(CommandLinkPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(CommandLinkPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-command-link");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(CommandLinkPropertyKeys.styleClass, styleClass);
	}

	public String getUpdate() {
		return (String) getStateHelper().eval(CommandLinkPropertyKeys.update, getRender());
	}

	public void setUpdate(String update) {
		getStateHelper().put(CommandLinkPropertyKeys.update, update);
	}
}
//J+
