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
package com.liferay.faces.bridge.config.internal;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.PortletRequestWrapper;


/**
 * This class is part of a workaround in the bridge for LPS-3311 and LPS-8355 (both fixed in Liferay Portal 6.0).
 *
 * @author  Neil Griffin
 */
public class PortletRequestDispatcherBridgeLiferayImpl implements PortletRequestDispatcher {

	// Private Data Members
	private PortletRequestDispatcher wrappedPortletRequestDispatcher;

	public PortletRequestDispatcherBridgeLiferayImpl(PortletRequestDispatcher portletRequestDispatcher) {
		this.wrappedPortletRequestDispatcher = portletRequestDispatcher;
	}

	public void forward(PortletRequest portletRequest, PortletResponse portletResponse) throws PortletException,
		IOException {

		portletRequest = getLiferayPortletRequest(portletRequest);
		wrappedPortletRequestDispatcher.forward(portletRequest, portletResponse);
	}

	public void include(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		renderRequest = (RenderRequest) getLiferayPortletRequest(renderRequest);
		wrappedPortletRequestDispatcher.include(renderRequest, renderResponse);
	}

	public void include(PortletRequest portletRequest, PortletResponse portletResponse) throws PortletException,
		IOException {

		portletRequest = getLiferayPortletRequest(portletRequest);
		wrappedPortletRequestDispatcher.include(portletRequest, portletResponse);
	}

	protected PortletRequest getLiferayPortletRequest(PortletRequest portletRequest) {

		if (portletRequest instanceof PortletRequestWrapper) {

			PortletRequestWrapper portletRequestWrapper = (PortletRequestWrapper) portletRequest;

			return getLiferayPortletRequest(portletRequestWrapper.getRequest());
		}
		else {
			return portletRequest;
		}
	}
}
