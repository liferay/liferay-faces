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
package com.liferay.faces.bridge.context;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.context.map.RequestParameterMapProducerImpl;


/**
 * @author  Neil Griffin
 */
public class ExternalContextProducerImpl extends ExternalContextWrapper {

	// Private Constants
	private static final String LIFERAY_NAMESPACE_PREFIX = "lfr_";

	// Private Data Members
	private Map<String, String> requestParameterMap;
	private PortletRequest portletRequest;
	private ExternalContext wrappedExternalContext;

	public ExternalContextProducerImpl(ExternalContext externalContext, PortletRequest portletRequest) {
		this.wrappedExternalContext = externalContext;
		this.portletRequest = portletRequest;
	}

	@Override
	public String encodeNamespace(String name) {
		return LIFERAY_NAMESPACE_PREFIX + super.encodeNamespace(name);
	}

	@Override
	public Map<String, String> getRequestParameterMap() {

		if (requestParameterMap == null) {
			requestParameterMap = new RequestParameterMapProducerImpl(super.getRequestParameterMap(), portletRequest);
		}

		return requestParameterMap;
	}

	@Override
	public ExternalContext getWrapped() {
		return wrappedExternalContext;
	}

}
