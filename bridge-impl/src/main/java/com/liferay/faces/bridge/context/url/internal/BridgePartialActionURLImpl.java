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

import javax.portlet.BaseURL;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeURI;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgePartialActionURLImpl extends BridgeURLBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePartialActionURLImpl.class);

	// Private Data Members
	private String uri;
	private String viewIdResourceParameterName;

	public BridgePartialActionURLImpl(BridgeContext bridgeContext, BridgeURI bridgeURI, String viewId) {

		super(bridgeContext, bridgeURI, viewId);
		this.uri = bridgeURI.toString();

		BridgeConfig bridgeConfig = bridgeContext.getBridgeConfig();
		this.viewIdResourceParameterName = bridgeConfig.getViewIdResourceParameterName();
	}

	@Override
	protected BaseURL toBaseURL() throws MalformedURLException {

		BaseURL baseURL = null;

		if (uri != null) {

			if (uri.startsWith("http")) {
				baseURL = new BaseURLNonEncodedStringImpl(uri, getParameterMap());
				logger.debug("URL starts with http so assuming that it has already been encoded: url=[{0}]", uri);
			}
			else {
				String urlWithModifiedParameters = _toString(false);
				baseURL = createPartialActionURL(urlWithModifiedParameters);
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
