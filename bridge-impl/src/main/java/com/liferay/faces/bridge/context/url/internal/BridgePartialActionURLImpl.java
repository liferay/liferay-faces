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
package com.liferay.faces.bridge.context.url.internal;

import java.net.MalformedURLException;

import javax.portlet.BaseURL;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgePartialActionURL;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgePartialActionURLImpl extends BridgeURLBaseImpl implements BridgePartialActionURL {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePartialActionURLImpl.class);

	// Private Data Members
	private String viewIdResourceParameterName;

	public BridgePartialActionURLImpl(BridgeContext bridgeContext, String url, String viewId) {
		super(bridgeContext, url, viewId);

		BridgeConfig bridgeConfig = bridgeContext.getBridgeConfig();
		this.viewIdResourceParameterName = bridgeConfig.getViewIdResourceParameterName();
	}

	@Override
	protected BaseURL toBaseURL() throws MalformedURLException {

		BaseURL baseURL = null;

		if (url != null) {

			if (url.startsWith("http")) {
				baseURL = new BaseURLNonEncodedStringImpl(url, getParameterMap());
				logger.debug("URL starts with http so assuming that it has already been encoded: url=[{0}]", url);
			}
			else {
				String urlWithModifiedParameters = _toString(false);
				baseURL = bridgeContext.getPortletContainer().createPartialActionURL(urlWithModifiedParameters);
			}
		}
		else {
			logger.warn("Unable to encode PartialActionURL for url=[null]");
		}

		return baseURL;
	}

	@Override
	protected String getViewIdParameterName() {
		return viewIdResourceParameterName;
	}
}
