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
package com.liferay.faces.bridge.context.url.internal;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.BaseURL;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeURI;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeBookmarkableURLImpl extends BridgeURLBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeBookmarkableURLImpl.class);

	// Private Data Members
	private String uri;
	private String viewIdRenderParameterName;

	public BridgeBookmarkableURLImpl(BridgeContext bridgeContext, BridgeURI bridgeURI,
		Map<String, List<String>> parameters, String viewId) {

		super(bridgeContext, bridgeURI, viewId);
		this.uri = bridgeURI.toString();
		this.viewIdRenderParameterName = bridgeContext.getBridgeConfig().getViewIdRenderParameterName();

		if (uri != null) {

			if (parameters != null) {

				Map<String, String[]> parameterMap = getParameterMap();
				Set<Map.Entry<String, List<String>>> entrySet = parameters.entrySet();

				for (Map.Entry<String, List<String>> mapEntry : entrySet) {

					String key = mapEntry.getKey();
					String[] valueArray = null;
					List<String> valueList = mapEntry.getValue();

					if (valueList != null) {
						valueArray = valueList.toArray(new String[valueList.size()]);
					}

					parameterMap.put(key, valueArray);
				}
			}
		}
	}

	@Override
	protected BaseURL toBaseURL() throws MalformedURLException {

		BaseURL baseURL = null;

		// If this is executing during the RENDER_PHASE or RESOURCE_PHASE of the portlet lifecycle, then
		Bridge.PortletPhase portletRequestPhase = BridgeUtil.getPortletRequestPhase();

		if ((portletRequestPhase == Bridge.PortletPhase.RENDER_PHASE) ||
				(portletRequestPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {

			baseURL = createRenderURL(uri);
			baseURL.setParameter(viewIdRenderParameterName, viewId);
			baseURL.setParameters(getParameterMap());
		}

		// Otherwise, log an error.
		else {
			logger.error("Unable to encode bookmarkable URL during Bridge.PortletPhase.[{0}].", portletRequestPhase);
		}

		return baseURL;
	}
}
