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

import java.util.Map;


/**
 * <p>This interface defines a contract for getting portlet bridge configuration values.</p>
 *
 * @author  Neil Griffin
 */
public interface BridgeConfig {

	/**
	 * Returns a map of bridge configuration attributes.
	 */
	public Map<String, Object> getAttributes();
}
