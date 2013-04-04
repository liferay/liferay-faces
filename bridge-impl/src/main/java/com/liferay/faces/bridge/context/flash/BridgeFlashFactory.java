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
package com.liferay.faces.bridge.context.flash;

import com.liferay.faces.bridge.FactoryWrapper;


/**
 * NOTE: PROPOSED-FOR-JSR344-API (FACTORY FOR THE JSF 2.2 API) See:
 * http://java.net/jira/browse/JAVASERVERFACES_SPEC_PUBLIC-1071
 *
 * @author  Neil Griffin
 */
public abstract class BridgeFlashFactory implements FactoryWrapper<BridgeFlashFactory> {

	public abstract BridgeFlash getBridgeFlash();
}
