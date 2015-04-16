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
package com.liferay.faces.bridge.context.url.internal.liferay;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.BaseURL;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeURI;
import com.liferay.faces.bridge.context.url.internal.BridgeRedirectURLImpl;


/**
 * @author  Neil Griffin
 */
public class BridgeRedirectURLLiferayImpl extends BridgeRedirectURLImpl {

	// Private Data Members
	private PortletResponse portletResponse;
	private String uri;
	private String viewIdRenderParameterName;

	public BridgeRedirectURLLiferayImpl(BridgeContext bridgeContext, BridgeURI bridgeURI,
		Map<String, List<String>> parameters, String viewId) {

		super(bridgeContext, bridgeURI, parameters, viewId);
		this.uri = bridgeURI.toString();
		this.portletResponse = bridgeContext.getPortletResponse();
		this.viewIdRenderParameterName = bridgeContext.getBridgeConfig().getViewIdRenderParameterName();
	}

	@Override
	protected PortletURL createRenderURL(String fromURL) throws MalformedURLException {

		LiferayPortletResponse liferayPortletResponse = new LiferayPortletResponse(portletResponse);

		return liferayPortletResponse.createRenderURL();
	}

	@Override
	protected BaseURL toBaseURL() throws MalformedURLException {

		BaseURL baseURL;

		if (BridgeUtil.getPortletRequestPhase() == Bridge.PortletPhase.ACTION_PHASE) {

			baseURL = createRenderURL(uri);
			baseURL.setParameter(viewIdRenderParameterName, viewId);

			Map<String, String[]> parameterMap = getParameterMap();
			Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();

			for (Map.Entry<String, String[]> mapEntry : entrySet) {

				String parameterName = mapEntry.getKey();
				String[] parameterValues = mapEntry.getValue();

				baseURL.setParameter(parameterName, parameterValues);
			}
		}
		else {
			baseURL = super.toBaseURL();
		}

		return baseURL;
	}
}
