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
package com.liferay.faces.bridge.container.liferay.internal;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.MimeResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.theme.ThemeDisplay;


/**
 * @author  Neil Griffin
 */
public class PortletContainerLiferayImpl extends PortletContainerLiferayCompatImpl {

	// serialVersionUID
	private static final long serialVersionUID = 4751433245905676075L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortletContainerLiferayImpl.class);

	// Private Data Members
	private boolean friendlyURLMapperEnabled;
	private LiferayURLFactory liferayURLFactory;
	private LiferayPortletRequest liferayPortletRequest;
	private String requestURL;

	public PortletContainerLiferayImpl(PortletRequest portletRequest, PortletResponse portletResponse,
		PortletContext portletContext) {

		try {
			this.liferayPortletRequest = new LiferayPortletRequest(portletRequest);
			;
			this.friendlyURLMapperEnabled = (liferayPortletRequest.getPortlet().getFriendlyURLMapperInstance() != null);
			this.liferayURLFactory = (LiferayURLFactory) BridgeFactoryFinder.getFactory(LiferayURLFactory.class);
			logger.debug("User-Agent requested URL=[{0}]", getRequestURL());
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public PortletURL createRedirectURL(String fromURL, Map<String, List<String>> parameters)
		throws MalformedURLException {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		LiferayPortletResponse liferayPortletResponse = new LiferayPortletResponse(bridgeContext.getPortletResponse());

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		copyRequestParameters(fromURL, redirectURL);

		if (parameters != null) {
			Set<String> parameterNames = parameters.keySet();

			for (String parameterName : parameterNames) {
				List<String> parameterValues = parameters.get(parameterName);
				String[] parameterValuesArray = parameterValues.toArray(new String[parameterValues.size()]);
				redirectURL.setParameter(parameterName, parameterValuesArray);
			}
		}

		return redirectURL;
	}

	@Override
	protected PortletURL createActionURL(MimeResponse mimeResponse) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return liferayURLFactory.getLiferayActionURL(bridgeContext, mimeResponse, mimeResponse.getNamespace(),
				friendlyURLMapperEnabled);
	}

	@Override
	protected PortletURL createRenderURL(MimeResponse mimeResponse) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return liferayURLFactory.getLiferayRenderURL(bridgeContext, mimeResponse, mimeResponse.getNamespace(),
				friendlyURLMapperEnabled);
	}

	@Override
	protected ResourceURL createResourceURL(MimeResponse mimeResponse) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return liferayURLFactory.getLiferayResourceURL(bridgeContext, mimeResponse, mimeResponse.getNamespace(),
				friendlyURLMapperEnabled);
	}

	@Override
	public String[] getHeader(String name) {
		return liferayPortletRequest.getHeader(name);
	}

	@Override
	public long getHttpServletRequestDateHeader(String name) {
		return liferayPortletRequest.getDateHeader(name);
	}

	@Override
	public String getRequestURL() {

		if (requestURL == null) {
			StringBuilder buf = new StringBuilder();
			ThemeDisplay themeDisplay = liferayPortletRequest.getThemeDisplay();
			buf.append(themeDisplay.getURLPortal());
			buf.append(themeDisplay.getURLCurrent());
			requestURL = buf.toString();
		}

		return requestURL;
	}
}
