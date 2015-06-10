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
package com.liferay.faces.util.context;

import java.util.List;

import com.liferay.faces.util.client.Script;


/**
 * @author  Kyle Stiemann
 */
public abstract class FacesRequestContext {

	// Private Static Data Members
	private static ThreadLocal<FacesRequestContext> instance = new ThreadLocal<FacesRequestContext>();

	public static FacesRequestContext getCurrentInstance() {
		return instance.get();
	}

	public static void setCurrentInstance(FacesRequestContext facesRequestContext) {

		if (facesRequestContext == null) {
			instance.remove();
		}
		else {
			instance.set(facesRequestContext);
		}
	}

	public abstract void addScript(Script script);

	public abstract void addScript(String script);

	public abstract void addScripts(List<Script> scripts);

	public abstract void release();

	public abstract List<Script> getScripts();
}
