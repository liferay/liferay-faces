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
 * This class defines the usage of a {@link ThreadLocal} singleton that exists alongside the {@link
 * javax.faces.context.FacesContext}.
 *
 * @author  Kyle Stiemann
 */
public abstract class FacesRequestContext {

	// Private Static Data Members
	private static ThreadLocal<FacesRequestContext> instance = new ThreadLocal<FacesRequestContext>();

	public static FacesRequestContext getCurrentInstance() {
		return instance.get();
	}

	/**
	 * Sets or removes the value of the {@link ThreadLocal} singleton instance.
	 *
	 * @param  facesRequestContext  If a non-null value is specified, then it will become the singleton value. If null
	 *                              is specified, then singleton value is removed from the ThreadLocal. is removed.
	 */
	public static void setCurrentInstance(FacesRequestContext facesRequestContext) {

		if (facesRequestContext == null) {
			instance.remove();
		}
		else {
			instance.set(facesRequestContext);
		}
	}

	/**
	 * Adds the specified {@link Script} to the list of scripts that are to be executed on the client.
	 */
	public abstract void addScript(Script script);

	/**
	 * Adds the specified script to the list of scripts that are to be executed on the client.
	 */
	public abstract void addScript(String script);

	/**
	 * Releases any resources that are associated with this {@link FacesRequestContext} instance.
	 */
	public abstract void release();

	/**
	 * Returns an immutable list of scripts that were added via the {@link #addScript(Script)} or {@link
	 * #addScript(String)} method.
	 */
	public abstract List<Script> getScripts();
}
