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
package com.liferay.faces.bridge.filter.internal;

import javax.portlet.RenderResponse;
import javax.portlet.filter.RenderResponseWrapper;


/**
 * @author  Neil Griffin
 */
public class RenderResponseBridgeImpl extends RenderResponseWrapper {

	// Private Data Members
	private String namespace;

	public RenderResponseBridgeImpl(RenderResponse renderResponse) {
		super(renderResponse);
	}

	@Override
	public String getNamespace() {

		if (namespace == null) {
			namespace = PortletResponseUtil.getNamespace(getResponse());
		}

		return namespace;
	}

}
