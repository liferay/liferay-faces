/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.config;

/**
 * @author  Neil Griffin
 */
public class BridgeConfigConstants {

	/**
	 * Boolean indicating whether or not methods annotated with the &#064;PreDestroy annotation are preferably invoked
	 * over the &#064;BridgePreDestroy annotation. Default value is true. The reason why, is because local portals like
	 * Liferay don't have a problem with PreDestroy. It really only comes into play for remote portals like Oracle
	 * WebCenter. For more info, see: http://issues.liferay.com/browse/FACES-146
	 */
	public static final String PARAM_PREFER_PRE_DESTROY1 = "com.liferay.faces.bridge.preferPreDestroy";
	public static final String PARAM_PREFER_PRE_DESTROY2 = "org.portletfaces.bridge.preferPreDestroy";

}
