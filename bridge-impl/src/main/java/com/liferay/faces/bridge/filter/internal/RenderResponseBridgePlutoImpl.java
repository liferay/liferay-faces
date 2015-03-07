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
package com.liferay.faces.bridge.filter.internal;

import javax.portlet.RenderResponse;


/**
 * @author  Neil Griffin
 */
public class RenderResponseBridgePlutoImpl extends RenderResponseBridgeImpl {

	public RenderResponseBridgePlutoImpl(RenderResponse renderResponse) {
		super(renderResponse);
	}

	@Override
	public void setContentType(String type) {

		// If the specified contentType is "application/xhtml+xml" then use "text/html" instead. That's the only value
		// that Pluto's RenderResponseImpl.setContentType(String) will be happy with, even though Pluto's "ACCEPT"
		// header claims it can accept "application/xhtml+xml".
		if ("application/xhtml+xml".equals(type)) {
			super.setContentType("text/html");
		}

		// Otherwise, use the specified contentType.
		else {
			super.setContentType(type);
		}
	}
}
