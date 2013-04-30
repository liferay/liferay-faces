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
package com.liferay.faces.bridge.tck.container;

import javax.faces.context.ExternalContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.container.pluto.PortletContainerPlutoImpl;


/**
 * This class provides some additional functionality to the Pluto PortletContainer implementation when running in the
 * TCK.
 *
 * @author  Neil Griffin
 */
public class PortletContainerPlutoTCKImpl extends PortletContainerPlutoImpl {

	// serialVersionUID
	private static final long serialVersionUID = 3044933521403224720L;

	// Private Constants
	private static final String[] USER_AGENT_HEADER_VALUES = new String[] {
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:13.0) Gecko/20100101 Firefox/13.0.1"
		};

	public PortletContainerPlutoTCKImpl(PortletRequest portletRequest, BridgeConfig bridgeConfig) {
		super(portletRequest, bridgeConfig);
	}

	/**
	 * The JSF 2.x version of the TCK has a dependency on Trinidad 2.x, which needs to be able to detect the browser
	 * (user-agent) that issued the request in order to determine whether-or-not Partial Page Rendering (PPR) is
	 * enabled/disabled. Specifically, the {@link
	 * org.apache.myfaces.trinidadinternal.agent.AgentFactoryImpl#_getUserAgentHeader(Map<String, String>))} method will
	 * call {@link ExternalContext#getRequestHeaderMap()} in order to get the "User-Agent" header. Liferay provides a
	 * way of determining this header from the underlying HttpServletRequest, but Pluto does not. Therefore in order for
	 * PPR tests like TestPage073 (scopeAfterRedisplayResourcePPRTest) to work with PPR, it is necessary to return a
	 * bogus value here.
	 */
	@Override
	public String[] getHeader(String name) {

		String[] header = super.getHeader(name);

		if ((header == null) && "User-Agent".equalsIgnoreCase(name)) {
			header = USER_AGENT_HEADER_VALUES;
		}

		return header;
	}

}
