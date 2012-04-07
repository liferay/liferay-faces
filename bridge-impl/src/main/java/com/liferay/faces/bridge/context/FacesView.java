/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context;

import javax.portlet.faces.Bridge;


/**
 * This interface represents a JSF View from the perspective of how it is mapped to the {@link
 * javax.faces.webapp.FacesServlet}.
 *
 * @author  Neil Griffin
 */
public interface FacesView {

	/**
	 * Flag indicating whether or not the view is mapped to the {@link javax.faces.webapp.FacesServlet} via
	 * extension-mapping (such as *.faces) or some other extension.
	 *
	 * @return  <code>true</code> if extension-mapped, otherwise <code>false</code>.
	 */
	boolean isExtensionMapped();

	/**
	 * Flag indicating whether or not the view is mapped to the {@link javax.faces.webapp.FacesServlet} via path-mapping
	 * (such as /faces/views/*) or some other extension.
	 *
	 * @return  <code>true</code> if extension-mapped, otherwise <code>false</code>.
	 */
	boolean isPathMapped();

	/**
	 * Determines the extension (such as *.faces) used by the extension-mapped servlet-mapping.
	 *
	 * @return  If the view is extension-mapped, returns the extension. Otherwise, returns <code>null</code>.
	 */
	String getExtension();

	/**
	 * Returns the query-string which may contain navigation parameters such as {@link Bridge#PORTLET_MODE_PARAMETER},
	 * {@link Bridge#NONFACES_TARGET_PATH_PARAMETER}, {@link Bridge#PORTLET_SECURE_PARAMETER}, {@link
	 * Bridge#PORTLET_WINDOWSTATE_PARAMETER}, {@link Bridge#FACES_VIEW_ID_PARAMETER}, or {@link
	 * Bridge#FACES_VIEW_PATH_PARAMETER}. Note that "navigation" does not refer to JSF navigation-rules, but rather
	 * changes in {@link PortletMode}, {@link WindowState}, etc. It could also contain user-define name=value parameters
	 * specified in a {@link Bridge#VIEW_ID} request attribute.
	 */
	String getQueryString();

	/**
	 * Determines the servlet-path (such as /faces/views) used by the path-mapped servlet-mapping.
	 *
	 * @return  If the view is path-mapped, returns the path. Otherwise, returns <code>null</code>.
	 */
	String getServletPath();

	/**
	 * Returns the viewId associated with this view.
	 */
	String getViewId();

}
