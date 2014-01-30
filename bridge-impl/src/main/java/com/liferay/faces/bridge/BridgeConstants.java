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
package com.liferay.faces.bridge;

import com.liferay.faces.util.application.ResourceConstants;


/**
 * @author  Neil Griffin
 */
public class BridgeConstants {

	// Regular Expressions
	public static final String REGEX_AMPERSAND_DELIMITER = "[&]";
	public static final String REGEX_DOT_DELIMITER = "[.]";

	// Request Attribute Names
	public static final String RENDER_PORTLET_RESOURCE = "RENDER_PORTLET_RESOURCE";
	public static final String REQ_ATTR_PATH_INFO = "javax.servlet.include.path_info";
	public static final String REQ_ATTR_SERVLET_PATH = "javax.servlet.include.servlet_path";

	// WSRP Constants
	public static final String WSRP = "wsrp";
	public static final String WSRP_REWRITE = "wsrp_rewrite";

	// Miscellaneous Constants
	public static final String MULTIPART_CONTENT_TYPE_PREFIX = "multipart/";
	public static final String SRC = "src";

	/**
	 * @deprecated  Replaced by {@link ResourceConstants#JAVAX_FACES_RESOURCE}
	 */
	@Deprecated
	public static final String JAVAX_FACES_RESOURCE = ResourceConstants.JAVAX_FACES_RESOURCE;

	/**
	 * @deprecated  Replaced by {@link ResourceConstants#LN}
	 */
	@Deprecated
	public static final String LN = ResourceConstants.LN;

}
