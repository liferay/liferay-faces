/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.servlet;

import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.portal.WebKeys;


/**
 * This class serves as a wrapper around the {@link HttpServletRequest} that lies behind the Liferay Portal {@link
 * PortletRequest} implementation. It provides the ability to intercept get/set/remove calls the {@link
 * WebKeys#AUI_SCRIPT_DATA} request attribute.
 *
 * @author  Neil Griffin
 */
public class ScriptCapturingHttpServletRequest extends NonNamespacedHttpServletRequest {

	// Private Data Members
	private Object auiScriptData;

	public ScriptCapturingHttpServletRequest(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);
	}

	@Override
	public void removeAttribute(String name) {

		if (WebKeys.AUI_SCRIPT_DATA.equals(name)) {
			auiScriptData = null;
		}
		else {
			super.removeAttribute(name);
		}
	}

	@Override
	public Object getAttribute(String name) {

		if (WebKeys.AUI_SCRIPT_DATA.equals(name)) {
			return auiScriptData;
		}
		else {
			return super.getAttribute(name);
		}
	}

	@Override
	public void setAttribute(String name, Object value) {

		if (WebKeys.AUI_SCRIPT_DATA.equals(name)) {
			auiScriptData = value;
		}
		else {
			super.setAttribute(name, value);
		}
	}

}
