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

	// Protected Enumerations
	protected enum CommandLinkPropertyKeys {
		ajax,
		execute,
		process,
		render,
		update
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

	public String getUpdate() {
		return (String) getStateHelper().eval(CommandLinkPropertyKeys.update, getRender());
	}

	public void setUpdate(String update) {
		getStateHelper().put(CommandLinkPropertyKeys.update, update);
	}
}
//J+
