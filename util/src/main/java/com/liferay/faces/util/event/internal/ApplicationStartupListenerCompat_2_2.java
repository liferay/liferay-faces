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
package com.liferay.faces.util.event.internal;

import javax.faces.context.ExternalContext;


/**
 * This class provides a compatibility layer that isolates differences related to JSF 2.2.
 *
 * @author  Neil Griffin
 */
public abstract class ApplicationStartupListenerCompat_2_2 extends ApplicationStartupListenerCompat {

	@Override
	protected String getApplicationContextPath(ExternalContext externalContext) {
		return externalContext.getApplicationContextPath();
	}
}
