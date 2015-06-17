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
package com.liferay.faces.alloy.component.commandbutton;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlCommandButton;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class CommandButtonBase extends HtmlCommandButton implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.commandbutton.CommandButton";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.commandbutton.CommandButtonRenderer";

	// Protected Enumerations
	protected enum CommandButtonPropertyKeys {
		ajax,
		autofocus,
		disabled,
		execute,
		process,
		render,
		styleClass,
		type,
		update
	}

	public CommandButtonBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isAjax() {
		return (Boolean) getStateHelper().eval(CommandButtonPropertyKeys.ajax, true);
	}

	public void setAjax(boolean ajax) {
		getStateHelper().put(CommandButtonPropertyKeys.ajax, ajax);
	}

	public Boolean getAutofocus() {
		return (Boolean) getStateHelper().eval(CommandButtonPropertyKeys.autofocus, null);
	}

	public void setAutofocus(Boolean autofocus) {
		getStateHelper().put(CommandButtonPropertyKeys.autofocus, autofocus);
	}

	public boolean isDisabled() {
		return (Boolean) getStateHelper().eval(CommandButtonPropertyKeys.disabled, false);
	}

	public void setDisabled(boolean disabled) {
		getStateHelper().put(CommandButtonPropertyKeys.disabled, disabled);
	}

	public String getExecute() {
		return (String) getStateHelper().eval(CommandButtonPropertyKeys.execute, "@all");
	}

	public void setExecute(String execute) {
		getStateHelper().put(CommandButtonPropertyKeys.execute, execute);
	}

	public String getProcess() {
		return (String) getStateHelper().eval(CommandButtonPropertyKeys.process, getExecute());
	}

	public void setProcess(String process) {
		getStateHelper().put(CommandButtonPropertyKeys.process, process);
	}

	public String getRender() {
		return (String) getStateHelper().eval(CommandButtonPropertyKeys.render, "@none");
	}

	public void setRender(String render) {
		getStateHelper().put(CommandButtonPropertyKeys.render, render);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(CommandButtonPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(CommandButtonPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-command-button");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(CommandButtonPropertyKeys.styleClass, styleClass);
	}

	public String getType() {
		return (String) getStateHelper().eval(CommandButtonPropertyKeys.type, null);
	}

	public void setType(String type) {
		getStateHelper().put(CommandButtonPropertyKeys.type, type);
	}

	public String getUpdate() {
		return (String) getStateHelper().eval(CommandButtonPropertyKeys.update, getRender());
	}

	public void setUpdate(String update) {
		getStateHelper().put(CommandButtonPropertyKeys.update, update);
	}
}
//J+
