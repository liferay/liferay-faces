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
package com.liferay.faces.util.client.internal;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;


/**
 * @author  Neil Griffin
 */
public class BrowserSnifferFactoryImpl extends BrowserSnifferFactory {

	@Override
	public BrowserSniffer getBrowserSniffer(ExternalContext externalContext) {

		HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();

		return new BrowserSnifferImpl(httpServletRequest);
	}

	@Override
	public BrowserSnifferFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
