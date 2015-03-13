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
package com.liferay.faces.util.client.internal;

import java.io.Serializable;

import com.liferay.faces.util.client.ClientScript;


/**
 * @author  Kyle Stiemann
 */
public class ClientScriptImpl implements ClientScript, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 875641899489661794L;

	// Private Members
	private StringBuilder scripts;

	public ClientScriptImpl() {
		scripts = new StringBuilder();
	}

	// Java 1.6+ @Override
	public void append(String content) {

		scripts.append("(function() {");
		scripts.append(content);
		scripts.append("})();");
	}

	// Java 1.6+ @Override
	public void append(String content, String options) {
		append(content);
	}

	// Java 1.6+ @Override
	public void clear() {
		scripts.setLength(0);
	}

	@Override
	public String toString() {
		return scripts.toString();
	}
}
