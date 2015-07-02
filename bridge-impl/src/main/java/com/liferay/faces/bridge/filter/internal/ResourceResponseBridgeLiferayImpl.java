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
package com.liferay.faces.bridge.filter.internal;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.filter.liferay.LiferayURLFactory;
import com.liferay.faces.bridge.filter.liferay.internal.LiferayPortalUtil;


/**
 * @author  Neil Griffin
 */
public class ResourceResponseBridgeLiferayImpl extends ResourceResponseBridgeImpl {

	// Private Data Members
	private Boolean friendlyURLMapperEnabled;
	private LiferayURLFactory liferayURLFactory;
	private String responseNamespaceWSRP;

	public ResourceResponseBridgeLiferayImpl(ResourceResponse resourceResponse) {
		super(resourceResponse);
		this.liferayURLFactory = (LiferayURLFactory) BridgeFactoryFinder.getFactory(LiferayURLFactory.class);
	}

	@Override
	public PortletURL createActionURL() throws IllegalStateException {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return liferayURLFactory.getLiferayActionURL(bridgeContext, getResponse(), getNamespace());
	}

	@Override
	public PortletURL createRenderURL() throws IllegalStateException {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return liferayURLFactory.getLiferayRenderURL(bridgeContext, getResponse(), getNamespace(),
				isFriendlyURLMapperEnabled(bridgeContext));
	}

	@Override
	public ResourceURL createResourceURL() throws IllegalStateException {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return liferayURLFactory.getLiferayResourceURL(bridgeContext, getResponse(), getNamespace());
	}

	protected boolean isFriendlyURLMapperEnabled(BridgeContext bridgeContext) {

		if (friendlyURLMapperEnabled == null) {
			PortletRequest portletRequest = bridgeContext.getPortletRequest();
			LiferayPortletRequest liferayPortletRequest = new LiferayPortletRequest(portletRequest);
			friendlyURLMapperEnabled = (liferayPortletRequest.getPortlet().getFriendlyURLMapperInstance() != null);
		}

		return friendlyURLMapperEnabled;
	}

	@Override
	protected String getNamespaceWSRP(BridgeContext bridgeContext) {

		if (responseNamespaceWSRP == null) {

			PortletRequest portletRequest = bridgeContext.getPortletRequest();
			responseNamespaceWSRP = LiferayPortalUtil.getPortletId(portletRequest);
		}

		return responseNamespaceWSRP;
	}
}
