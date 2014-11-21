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
package com.liferay.faces.util.client.internal;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.internal.RendererUtil;


/**
 * @author  Neil Griffin
 */
public class ClientScriptImpl implements ClientScript, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 875641899489661794L;

	// Private Data Members
	private StringBuilder alloyJavaScript;
	private StringBuilder javascript;
	private Set<String> useSet;

	public ClientScriptImpl() {
		this.alloyJavaScript = new StringBuilder();
		this.javascript = new StringBuilder();
		this.useSet = new TreeSet<String>();
	}

	// Java 1.6+ @Override
	public void append(String content, String use) {

		if ((use == null) || (use.trim().length() == 0)) {
			javascript.append(content);
		}
		else {
			alloyJavaScript.append("(function() {");
			alloyJavaScript.append(content);
			alloyJavaScript.append("})();");

			String[] useArray = use.split(StringPool.COMMA);
			useSet.addAll(Arrays.asList(useArray));
		}
	}

	// Java 1.6+ @Override
	public void clear() {
		alloyJavaScript.setLength(0);
		javascript.setLength(0);
		useSet.clear();
	}

	@Override
	public String toString() {

		StringBuilder value = new StringBuilder();

		value.append(javascript.toString());

		if (alloyJavaScript.length() > 0) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			String[] useArray = new String[useSet.size()];
			String alloyBeginScript = RendererUtil.getAlloyBeginScript(facesContext, useSet.toArray(useArray));
			value.append(alloyBeginScript);
			value.append(alloyJavaScript.toString());
			value.append(RendererUtil.ALLOY_END_SCRIPT);
		}

		return value.toString();
	}
}
