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
package com.liferay.faces.bridge.context;

import java.io.IOException;

import javax.faces.context.ExternalContext;


/**
 * @author  Neil Griffin
 */
public abstract class IncongruityContextCompatImpl extends IncongruityContextBaseImpl {

	protected void makeCongruousJSF2(ExternalContext externalContext, IncongruousAction incongruousAction)
		throws IOException {

		// no-op for JSF 1.2
	}

	@Override
	public void setRequestContentLength(int length) {

		// no-op for JSF 1.2
	}

	@Override
	public void setResponseCommitted(boolean committed) {

		// no-op for JSF 1.2
	}

	@Override
	public int getResponseContentLength() {

		// no-op for JSF 1.2
		return 0;
	}

}
