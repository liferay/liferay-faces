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
package com.liferay.faces.bridge.context.url;

import java.util.Map;

import javax.portlet.PortletURL;


/**
 * This interface represents a bridge URL, meaning a URL that has convenience methods for representing URLs according to
 * Section 6.1.3.1 of the Bridge Spec.
 *
 * @author  Neil Griffin
 */
public interface BridgeURL {

	/**
	 * Returns a string-based representation of the URL.
	 */
	@Override
	public String toString();

	/**
	 * Flag indicating whether or not the URL is secure. For more information, see {@link
	 * PortletURL#setSecure(boolean)}.
	 *
	 * @return  <code>true</code> if the URL is secure, otherwise <code>false</code>.
	 */
	public boolean isSecure();

	/**
	 * Determines whether or not the URL is self-referencing, meaning, it targets the current Faces view.
	 *
	 * @return  <code>true</code> if self-referencing, otherwise <code>false</code>.
	 */
	public boolean isSelfReferencing();

	/**
	 * Returns the first value of the underlying {@link BridgeURL#getParameterMap()} with the specified <code>
	 * name</code>.
	 */
	public String getParameter(String name);

	/**
	 * Sets the <code>value</code> of the underlying {@link BridgeURL#getParameterMap()} according to the specified
	 * <code>name</code>.
	 */
	public void setParameter(String name, String value);

	/**
	 * Sets the <code>value</code> of the underlying {@link BridgeURL#getParameterMap()} according to the specified
	 * <code>name</code>.
	 */
	public void setParameter(String name, String[] value);

	/**
	 * Returns an mutable {@link Map} representing the URL parameters.
	 */
	public Map<String, String[]> getParameterMap();

	/**
	 * Sets the flag indicating whether or not the URL is secure.
	 *
	 * @param  secure  <code>true</code> if secure, otherwise <code>false</code>.
	 */
	public void setSecure(boolean secure);

	/**
	 * Sets the flag indicating whether or not the URL is self-referencing, meaning, whether or not it targets the
	 * current Faces view.
	 *
	 * @param  selfReferencing  <code>true</code> if self-referencing, otherwise <code>false</code>.
	 */
	public void setSelfReferencing(boolean selfReferencing);

	/**
	 * Determines whether or not the URL targets a Faces View.
	 *
	 * @return  <code>true</code> if the URL targets a Faces View, otherwise <code>false</code>
	 */
	public boolean isFacesViewTarget();
}
