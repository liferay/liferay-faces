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
package com.liferay.faces.bridge.container.liferay;

import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.portlet.PortletRequest;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.container.PortletContainerImpl;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriter;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriterLiferayImpl;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public class PortletContainerLiferayCompatImpl extends PortletContainerImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortletContainerLiferayCompatImpl.class);

	// Private Constants
	private static final String RENDER_PORTLET = "RENDER_PORTLET";

	// serialVersionUID
	private static final long serialVersionUID = 8713570232856573935L;

	// Private Data Members
	private int liferaySharedPageTopLength;

	public PortletContainerLiferayCompatImpl(PortletRequest portletRequest, BridgeConfig bridgeConfig) {
		super(portletRequest, bridgeConfig);
	}

	/**
	 * This method is called after the {@link PhaseId#RENDER_RESPONSE} phase of the JSF lifecycle.
	 */
	@Override
	public void afterPhase(PhaseEvent phaseEvent) {

		// Remove duplicate resources from the LIFERAY_SHARED_PAGE_TOP request attribute. For more information, see:
		// http://issues.liferay.com/browse/FACES-1216
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		if (liferaySharedPageTopLength > 0) {

			PortletRequest portletRequest = bridgeContext.getPortletRequest();

			StringBundler stringBundler = new StringBundler(portletRequest.getAttribute(WebKeys.PAGE_TOP));

			if (stringBundler != null) {

				LiferaySharedPageTop liferaySharedPageTop = new LiferaySharedPageTop(stringBundler);
				liferaySharedPageTop.removeDuplicates();
				stringBundler = liferaySharedPageTop.toStringBundler();
				portletRequest.setAttribute(WebKeys.PAGE_TOP, stringBundler.getWrapped());
			}
		}

		// Remove the "RENDER_PORTLET" attribute that may have been added by the {@link #beforePhase(PhaseEvent)}
		// method.
		if (bridgeContext.getPortletRequestPhase() == Bridge.PortletPhase.RESOURCE_PHASE) {

			PortletRequest portletRequest = bridgeContext.getPortletRequest();

			portletRequest.removeAttribute(RENDER_PORTLET);

		}
	}

	/**
	 * This method is called prior to the {@link PhaseId#RENDER_RESPONSE} phase of the JSF lifecycle.
	 */
	@Override
	public void beforePhase(PhaseEvent phaseEvent) {

		// Determine if there are any resources in the LIFERAY_SHARED_PAGE_TOP request attribute, so that execution of
		// the {@link #afterPhase(PhaseEvent)} can be optimized.
		liferaySharedPageTopLength = 0;

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		PortletRequest portletRequest = bridgeContext.getPortletRequest();
		StringBundler stringBundler = new StringBundler(portletRequest.getAttribute(WebKeys.PAGE_TOP));

		liferaySharedPageTopLength = stringBundler.length();

		// If running in the RESOURCE_PHASE of the portlet lifecycle, then
		if (bridgeContext.getPortletRequestPhase() == Bridge.PortletPhase.RESOURCE_PHASE) {

			// During the RENDER_PHASE of the portlet lifecycle, the Liferay PortletImpl.renderPortlet(...) method adds
			// the "RENDER_PORTLET" request attribute, which is consulted by the PortletURLImpl.addPortletAuthToken(...)
			// method when URLs are being constructed. But during the RESOURCE_PHASE, Liferay does not add the
			// "RENDER_PORTLET" attribute, and the PortletURLImpl.addPortletAuthToken(...) method behaves differently
			// and ultimately causes the URL to have an additional "p_p_auth" parameter. Since the URL is different, it
			// will cause ICEfaces to detect a DOM-diff, and will unnecessarily replace markup in the DOM.
			//
			// The workaround is to add the "RENDER_PORTLET" attribute here (before the/ RENDER_RESPONSE phase of the
			// JSF lifecycle executes), and then remove the attribute after the RENDER_RESPONSE phase is complete.
			// For more information, see: http://issues.liferay.com/browse/FACES-1435
			String portletId = (String) portletRequest.getAttribute(WebKeys.PORTLET_ID);
			ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

			try {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), portletId);
				portletRequest.setAttribute(RENDER_PORTLET, portlet);
			}
			catch (SystemException e) {
				logger.error(e);
			}
		}
	}

	@Override
	public HeadResponseWriter getHeadResponseWriter(ResponseWriter wrappableResponseWriter) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		HeadResponseWriter headResponseWriter = new HeadResponseWriterLiferayImpl(bridgeContext,
				wrappableResponseWriter);

		return headResponseWriter;
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
