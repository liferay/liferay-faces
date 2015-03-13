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
package com.liferay.faces.util.client;

import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class ClientScriptWrapper implements ClientScript, FacesWrapper<ClientScript> {

	// Java 1.6+: @Override
	public void append(String content) {
		getWrapped().append(content);
	}

	// Java 1.6+ @Override
	public void append(String content, String use) {
		getWrapped().append(content, use);
	}

	// Java 1.6+: @Override
	public void clear() {
		getWrapped().clear();
	}

	@Override
	public String toString() {
		return getWrapped().toString();
	}

	// Java 1.6+ @Override
	public abstract ClientScript getWrapped();
}
