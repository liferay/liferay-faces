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
package com.liferay.faces.bridge.application;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.Bridge.PortletPhase;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class ViewHandlerCompatImpl extends ViewHandlerWrapper {

	// Public Constants
	public static final String RESPONSE_CHARACTER_ENCODING = "com.liferay.faces.bridge.responseCharacterEncoding";

	// Private Constants
	private static final boolean MOJARRA_DETECTED = ProductMap.getInstance().get(ProductConstants.JSF).getTitle()
		.equals(ProductConstants.MOJARRA);

	@Override
	public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {

		// This method has overridden behavior for JSF 1 but is simply a pass-through for JSF 2
		super.renderView(context, viewToRender);
	}

	/**
	 * Mojarra 1.x does not have the ability to process faces-config navigation-rule entries with to-view-id containing
	 * EL-expressions. This method compensates for that shortcoming by evaluating the EL-expression that may be present
	 * in the specified viewId.
	 *
	 * @param   facesContext  The current FacesContext.
	 * @param   viewId        The viewId that may contain an EL expression.
	 *
	 * @return  If an EL-expression was present in the specified viewId, then returns the evaluated expression.
	 *          Otherwise, returns the specified viewId unchanged.
	 */
	protected String evaluateExpressionJSF1(FacesContext facesContext, String viewId) {

		// This method has overridden behavior for JSF 1 but simply returns the specified viewId for JSF 2
		return viewId;
	}

	@Override
	public String getRedirectURL(FacesContext facesContext, String viewId, Map<String, List<String>> parameters,
		boolean includeViewParams) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletPhase portletRequestPhase = BridgeUtil.getPortletRequestPhase();

		// Determine whether or not it is necessary to work-around the patch applied to Mojarra in JAVASERVERFACES-3023
		boolean workaroundMojarra = (MOJARRA_DETECTED) &&
			((portletRequestPhase == Bridge.PortletPhase.ACTION_PHASE) ||
				(portletRequestPhase == Bridge.PortletPhase.EVENT_PHASE));

		if (workaroundMojarra) {
			bridgeContext.getAttributes().put(RESPONSE_CHARACTER_ENCODING, StringPool.UTF8);
		}

		String redirectURL = super.getRedirectURL(facesContext, viewId, parameters, includeViewParams);

		if (workaroundMojarra) {
			bridgeContext.getAttributes().remove(RESPONSE_CHARACTER_ENCODING);
		}

		return redirectURL;
	}
}
