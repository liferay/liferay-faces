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

	// Protected Enumerations
	protected enum CommandButtonPropertyKeys {
		ajax,
		autofocus,
		disabled,
		execute,
		process,
		render,
		type,
		update
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
