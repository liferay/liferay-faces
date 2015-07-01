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
package com.liferay.faces.alloy.component.inputfile.internal;

import java.util.Arrays;
import java.util.Collection;

import javax.faces.component.behavior.AjaxBehavior;


/**
 * @author  Neil Griffin
 */
public class AjaxBehaviorMockImpl extends AjaxBehavior {

	// Private Data Members
	private Collection<String> render;
	private Collection<String> execute;

	public AjaxBehaviorMockImpl(String execute, String render) {
		this.execute = Arrays.asList(execute.split(" "));
		this.render = Arrays.asList(render.split(" "));
	}

	@Override
	public Collection<String> getExecute() {
		return execute;
	}

	@Override
	public Collection<String> getRender() {
		return render;
	}
}
