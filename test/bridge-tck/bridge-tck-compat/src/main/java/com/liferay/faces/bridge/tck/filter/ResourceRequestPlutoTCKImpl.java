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
package com.liferay.faces.bridge.tck.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.filter.ResourceRequestWrapper;


/**
 * @author  Neil Griffin
 */
public class ResourceRequestPlutoTCKImpl extends ResourceRequestWrapper {

	// Private Constants
	private static final String[] USER_AGENT_HEADER_VALUES = new String[] {
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:13.0) Gecko/20100101 Firefox/13.0.1"
		};

	public ResourceRequestPlutoTCKImpl(ResourceRequest resourceRequest) {
		super(resourceRequest);
	}

	/**
	 * The JSF 2.x version of the TCK has a dependency on Trinidad 2.x, which needs to be able to detect the browser
	 * (user-agent) that issued the request in order to determine whether-or-not Partial Page Rendering (PPR) is
	 * enabled/disabled. Specifically, the {@link
	 * org.apache.myfaces.trinidadinternal.agent.AgentFactoryImpl#_getUserAgentHeader(Map<String, String>))} method will
	 * call {@link javax.faces.context.ExternalContext#getRequestHeaderMap()} in order to get the "User-Agent" header.
	 * Liferay provides a way of determining this header from the underlying HttpServletRequest, but Pluto does not.
	 * Therefore in order for PPR tests like TestPage073 (scopeAfterRedisplayResourcePPRTest) to work with PPR, it is
	 * necessary to return a bogus value here.
	 */
	@Override
	public Enumeration<String> getProperties(String name) {

		if ("User-Agent".equalsIgnoreCase(name)) {
			List<String> propertyList = new ArrayList<String>();
			propertyList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:13.0) Gecko/20100101 Firefox/13.0.1");

			return Collections.enumeration(propertyList);
		}
		else {
			return super.getProperties(name);
		}
	}
}
