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
package com.liferay.faces.bridge.tck.filter;

import javax.portlet.RenderRequest;
import javax.portlet.filter.RenderRequestWrapper;

import com.liferay.faces.bridge.BridgeConstants;


/**
 * This class implements a workaround for <a href="http://issues.liferay.com/browse/FACES-1629">FACES-1629</a> by
 * providing a wrapper around the Liferay Portal {@link RenderRequest} implementation.
 *
 * @author  Neil Griffin
 */
public class RenderRequestResinImpl extends RenderRequestWrapper {

	public RenderRequestResinImpl(RenderRequest renderRequest) {
		super(renderRequest);
	}

	@Override
	public Object getAttribute(String name) {

		if (BridgeConstants.REQ_ATTR_SERVLET_PATH.equals(name)) {

			// Workaround for FACES-1629
			return null;
		}
		else {
			return super.getAttribute(name);
		}
	}

}
