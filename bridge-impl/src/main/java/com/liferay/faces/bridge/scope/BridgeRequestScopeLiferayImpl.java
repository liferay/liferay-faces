/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.scope;

import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeLiferayImpl extends BridgeRequestScopeImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeLiferayImpl.class);

	// serialVersionUID
	private static final long serialVersionUID = 1814762389345663517L;

	public BridgeRequestScopeLiferayImpl(PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, String idPrefix) {
		super(portletConfig, portletContext, portletRequest, idPrefix);
	}

	/**
	 * Unlike Pluto, Liferay will preserve/copy request attributes that were originally set on an {@link ActionRequest}
	 * into the {@link RenderRequest}. However, the Bridge Spec assumes that they will not be preserved. Therefore is
	 * necessary to remove these request attributes when running under Liferay.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void restore(FacesContext facesContext) {

		super.restore(facesContext);

		if (isRedirectOccurred() || isPortletModeChanged()) {

			// TCK TestPage062: eventScopeNotRestoredRedirectTest
			// TCK TestPage063: eventScopeNotRestoredModeChangedTest
			List<RequestAttribute> savedRequestAttributes = (List<RequestAttribute>) getAttribute(
					BRIDGE_REQ_SCOPE_ATTR_REQUEST_ATTRIBUTES);

			ExternalContext externalContext = facesContext.getExternalContext();

			Map<String, Object> currentRequestAttributes = externalContext.getRequestMap();

			for (RequestAttribute requestAttribute : savedRequestAttributes) {
				String name = requestAttribute.getName();
				currentRequestAttributes.remove(name);
				logger.trace(
					"Due to redirect, removed request attribute name=[{0}] that had been preserved in the ACTION_PHASE or EVENT_PHASE");
			}
		}
	}

}
