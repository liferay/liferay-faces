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
package com.liferay.faces.portal.render.internal;

/**
 * @author  Neil Griffin
 */
public class PortalTagOutputImpl implements PortalTagOutput {

	// Private Data Members
	private String markup;
	private String scripts;

	public PortalTagOutputImpl(String markup, String scripts) {
		this.markup = markup;
		this.scripts = scripts;
	}

	@Override
	public String getMarkup() {
		return markup;
	}

	@Override
	public String getScripts() {
		return scripts;
	}
}
