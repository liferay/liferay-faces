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

import javax.portlet.faces.Bridge;


/**
 * @author  Neil Griffin
 */
public interface BridgeURI {

	/**
	 * Returns a string-based representation of the URL.
	 */
	@Override
	public String toString();

	/**
	 * Returns the path component of the URI, relative to the specified context-path.
	 */
	public String getContextRelativePath(String contextPath);

	/**
	 * Determines whether or not the URI is escaped.
	 *
	 * @return  <code>true</code> if all occurrences of the ampersand character appear as &amp; otherwise, returns
	 *          <code>false</code>.
	 */
	public boolean isEscaped();

	/**
	 * Determines whether or not the URI is absolute, meaning it contains a scheme component. Note that according to the
	 * class-level documentation of {@link java.net.URI} an absolute URI is non-relative.
	 *
	 * @return  Returns true if the URI is absolute, otherwise returns false.
	 */
	public boolean isAbsolute();

	/**
	 * Determines whether or not the URL is opaque, meaning it is absolute and its scheme component does not begin with
	 * a forward-slash character. For more information see {@link java.net.URI#isOpaque()}.
	 *
	 * @return  <code>true</code> if the URL is opaque, otherwise <code>false</code>.
	 */
	public boolean isOpaque();

	/**
	 * Determines whether or not the path component of the URL is relative, meaning it does not begin with a
	 * forward-slash character.
	 *
	 * @return  <code>true</code> if the path is relative, otherwise <code>false</code>.
	 */
	public boolean isPathRelative();

	/**
	 * Determines whether or not the URL begins with the "portlet:" scheme.
	 *
	 * @return  <code>true</code> if the URL begins with the "portlet:" scheme, otherwise <code>false</code>.
	 */
	public boolean isPortletScheme();

	/**
	 * Determines whether or not the URI is relative, meaning it does not have a scheme component. Note that according
	 * to the class-level documentation of {@link java.net.URI} a relative URI is non-absolute.
	 *
	 * @return  Returns true if the URI is relative, otherwise returns false.
	 */
	public boolean isRelative();

	/**
	 * Determines whether or not the URL is external.
	 *
	 * @return  <code>true</code> if external, otherwise <code>false</code>.
	 */
	public boolean isExternal();

	/**
	 * Determines whether or not the URL is hierarchical, meaning it is either 1) absolute and the scheme-specific part
	 * begins with a forward-slash character, or 2) is relative.
	 *
	 * @return  <code>true</code> if the URL is hierarchical, otherwise <code>false</code>.
	 */
	public boolean isHierarchical();

	/**
	 * Returns an immutable {@link Map} representing the URI parameters.
	 */
	public Map<String, String[]> getParameterMap();

	/**
	 * Returns the path component of the URI.
	 */
	public String getPath();

	/**
	 * Returns the {@link javax.portlet.faces.Bridge.PortletPhase} associated with this URL. Note that the value will be
	 * null if the URL does not begin with the "portlet:" scheme/prefix.
	 */
	public Bridge.PortletPhase getPortletPhase();

	/**
	 * Returns the query component, meaning all characters after the question-mark of the scheme-specific-part of the
	 * URI.
	 */
	public String getQuery();
}
