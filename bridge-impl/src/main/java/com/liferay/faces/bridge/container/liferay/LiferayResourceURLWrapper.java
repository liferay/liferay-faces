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
package com.liferay.faces.bridge.container.liferay;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.portlet.PortletSecurityException;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class LiferayResourceURLWrapper implements LiferayResourceURL, Wrapper<LiferayResourceURL> {

	public void addProperty(String key, String value) {
		getWrapped().addProperty(key, value);
	}

	public void write(Writer out) throws IOException {
		getWrapped().write(out);
	}

	public void write(Writer out, boolean escapeXML) throws IOException {
		getWrapped().write(out, escapeXML);
	}

	public String getCacheability() {
		return getWrapped().getCacheability();
	}

	public void setCacheability(String cacheLevel) {
		getWrapped().setCacheability(cacheLevel);
	}

	public void setParameter(String name, String value) {
		getWrapped().setParameter(name, value);
	}

	public void setParameter(String name, String[] values) {
		getWrapped().setParameter(name, values);
	}

	public Map<String, String[]> getParameterMap() {
		return getWrapped().getParameterMap();
	}

	public void setParameters(Map<String, String[]> parameters) {
		getWrapped().setParameters(parameters);
	}

	public void setProperty(String key, String value) {
		getWrapped().setProperty(key, value);
	}

	public void setResourceID(String resourceID) {
		getWrapped().setResourceID(resourceID);
	}

	public void setSecure(boolean secure) throws PortletSecurityException {
		getWrapped().setSecure(secure);
	}

	public abstract LiferayResourceURL getWrapped();
}
