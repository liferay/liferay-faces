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
package com.liferay.faces.util.client;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Neil Griffin
 */
public class ClientScriptImpl implements ClientScript, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 875641899489661794L;

	// Private Data Members
	private boolean browserIE;
	private float browserMajorVersion;
	private StringBuilder callbackSB;
	private StringBuilder rawSB;
	private Set<String> useSet;

	public ClientScriptImpl(boolean browserIE, float browserMajorVersion) {
		this.browserIE = browserIE;
		this.browserMajorVersion = browserMajorVersion;
		this.callbackSB = new StringBuilder();
		this.rawSB = new StringBuilder();
		this.useSet = new TreeSet<String>();
	}

	public void append(String content, String use) {

		if ((use == null) || (use.trim().length() == 0)) {
			rawSB.append(content);
		}
		else {
			callbackSB.append("(function() {");
			callbackSB.append(content);
			callbackSB.append("})();");

			String[] useArray = use.split(StringPool.COMMA);

			for (int i = 0; i < useArray.length; i++) {
				useSet.add(useArray[i]);
			}
		}
	}

	public void append(String portletId, String content, String use) {
		append(content, use);
	}

	@Override
	public String toString() {

		StringBuilder value = new StringBuilder();

		value.append(rawSB.toString());

		if (callbackSB.length() > 0) {

			String[] useArray = new String[useSet.size()];
			String alloyBeginScript = RendererUtil.getAlloyBeginScript(useSet.toArray(useArray), browserMajorVersion,
					browserIE);
			value.append(alloyBeginScript);
			value.append(callbackSB.toString());
			value.append(RendererUtil.ALLOY_END_SCRIPT);
		}

		return value.toString();
	}
}
