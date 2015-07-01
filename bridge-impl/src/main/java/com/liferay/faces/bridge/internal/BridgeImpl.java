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
package com.liferay.faces.bridge.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeException;
import javax.portlet.faces.BridgeUninitializedException;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.BridgePhase;
import com.liferay.faces.bridge.BridgePhaseFactory;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManager;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManagerFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeImpl implements Bridge {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeImpl.class);

	// Private Data Members
	private boolean initialized = false;
	private PortletConfig portletConfig;

	public void destroy() {
		initialized = false;

		// FACES-1450: Surround with try/catch block in order to prevent hot re-deploys from failing in Liferay Portal.
		try {

			BridgeRequestScopeManagerFactory bridgeRequestScopeManagerFactory = (BridgeRequestScopeManagerFactory)
				BridgeFactoryFinder.getFactory(BridgeRequestScopeManagerFactory.class);
			BridgeRequestScopeManager bridgeRequestScopeManager =
				bridgeRequestScopeManagerFactory.getBridgeRequestScopeManager();
			bridgeRequestScopeManager.removeBridgeRequestScopesByPortlet(portletConfig);
		}
		catch (Throwable t) {
			logger.warn(t.getMessage());
		}
	}

	public void doFacesRequest(ActionRequest actionRequest, ActionResponse actionResponse)
		throws BridgeDefaultViewNotSpecifiedException, BridgeUninitializedException, BridgeException {

		checkNull(actionRequest, actionResponse);

		if (initialized) {

			BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
					BridgeConfigFactory.class);
			BridgeConfig bridgeConfig = bridgeConfigFactory.getBridgeConfig(portletConfig);
			PortletConfig wrappedPortletConfig = bridgeConfigFactory.getPortletConfig(portletConfig);
			BridgePhaseFactory bridgePhaseFactory = (BridgePhaseFactory) BridgeFactoryFinder.getFactory(
					BridgePhaseFactory.class);
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeActionPhase(actionRequest, actionResponse,
					wrappedPortletConfig, bridgeConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}
	}

	public void doFacesRequest(EventRequest eventRequest, EventResponse eventResponse)
		throws BridgeUninitializedException, BridgeException {

		checkNull(eventRequest, eventResponse);

		if (initialized) {
			BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
					BridgeConfigFactory.class);
			BridgeConfig bridgeConfig = bridgeConfigFactory.getBridgeConfig(portletConfig);
			PortletConfig wrappedPortletConfig = bridgeConfigFactory.getPortletConfig(portletConfig);
			BridgePhaseFactory bridgePhaseFactory = (BridgePhaseFactory) BridgeFactoryFinder.getFactory(
					BridgePhaseFactory.class);
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeEventPhase(eventRequest, eventResponse,
					wrappedPortletConfig, bridgeConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}

	}

	public void doFacesRequest(RenderRequest renderRequest, RenderResponse renderResponse)
		throws BridgeDefaultViewNotSpecifiedException, BridgeUninitializedException, BridgeException {

		checkNull(renderRequest, renderResponse);

		if (initialized) {
			BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
					BridgeConfigFactory.class);
			BridgeConfig bridgeConfig = bridgeConfigFactory.getBridgeConfig(portletConfig);
			PortletConfig wrappedPortletConfig = bridgeConfigFactory.getPortletConfig(portletConfig);
			BridgePhaseFactory bridgePhaseFactory = (BridgePhaseFactory) BridgeFactoryFinder.getFactory(
					BridgePhaseFactory.class);
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeRenderPhase(renderRequest, renderResponse,
					wrappedPortletConfig, bridgeConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}

	}

	public void doFacesRequest(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws BridgeUninitializedException, BridgeException {

		checkNull(resourceRequest, resourceResponse);

		if (initialized) {
			BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
					BridgeConfigFactory.class);
			BridgeConfig bridgeConfig = bridgeConfigFactory.getBridgeConfig(portletConfig);
			PortletConfig wrappedPortletConfig = bridgeConfigFactory.getPortletConfig(portletConfig);
			BridgePhaseFactory bridgePhaseFactory = (BridgePhaseFactory) BridgeFactoryFinder.getFactory(
					BridgePhaseFactory.class);
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeResourcePhase(resourceRequest, resourceResponse,
					wrappedPortletConfig, bridgeConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}

	}

	public void init(PortletConfig portletConfig) throws BridgeException {
		StringBuilder logMessage = new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss,SSS");
		Calendar calendar = new GregorianCalendar();
		String timestamp = dateFormat.format(calendar.getTime());
		logMessage.append(timestamp);
		logMessage.append(" INFO  [BridgeImpl] Initializing ");
		logMessage.append(getTitle());
		logMessage.append(" ");
		logMessage.append(getVersion());
		System.out.println(logMessage.toString());
		this.initialized = true;
		this.portletConfig = portletConfig;
	}

	protected void checkNull(PortletRequest portletRequest, PortletResponse portletResponse) {

		// Null check required by the TCK.
		if (portletRequest == null) {
			throw new NullPointerException("portletRequest was null");
		}

		// Null check required by the TCK.
		if (portletResponse == null) {
			throw new NullPointerException("portletResponse was null");
		}
	}

	public String getTitle() {
		return BridgeImpl.class.getPackage().getImplementationTitle();
	}

	public String getVersion() {
		return BridgeImpl.class.getPackage().getImplementationVersion();
	}
}
