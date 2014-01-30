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
package com.liferay.faces.bridge.application.view;

import java.util.Collection;

import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.RenderResponse;
import javax.servlet.ServletResponse;


/**
 * @author  Neil Griffin
 */
public class BridgeWriteBehindResponseRenderImpl extends BridgeWriteBehindResponseMimeImpl implements RenderResponse {

	public BridgeWriteBehindResponseRenderImpl(RenderResponse renderResponse, ServletResponse servletResponse) {
		super((MimeResponse) renderResponse, servletResponse);
	}

	public void setNextPossiblePortletModes(Collection<PortletMode> portletModes) {
		getResponse().setNextPossiblePortletModes(portletModes);
	}

	@Override
	public RenderResponse getResponse() {
		return (RenderResponse) super.getResponse();
	}

	public void setTitle(String title) {
		getResponse().setTitle(title);
	}

}
