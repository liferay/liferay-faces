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
package com.liferay.faces.bridge.container.liferay;

import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.container.PortletContainerImpl;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriter;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriterLiferayImpl;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public class PortletContainerLiferayCompatImpl extends PortletContainerImpl {

	// serialVersionUID
	private static final long serialVersionUID = 8713570232856573935L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortletContainerLiferayCompatImpl.class);

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

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		// Remove duplicate resources from the LIFERAY_SHARED_PAGE_TOP request attribute. For more information, see:
		// http://issues.liferay.com/browse/FACES-1216
		if (liferaySharedPageTopLength > 0) {

			PortletRequest portletRequest = bridgeContext.getPortletRequest();

			StringBundler pageTop = getPageTop(portletRequest);

			if (pageTop != null) {

				LiferaySharedPageTop liferaySharedPageTop = new LiferaySharedPageTop(pageTop);
				liferaySharedPageTop.removeDuplicates();
				pageTop = liferaySharedPageTop.toStringBundler();

				setPageTop(portletRequest, pageTop);
			}
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
		StringBundler pageTop = getPageTop(portletRequest);

		if (pageTop != null) {
			liferaySharedPageTopLength = pageTop.length();
		}
	}

	@Override
	public HeadResponseWriter getHeadResponseWriter(ResponseWriter wrappableResponseWriter) {
		HeadResponseWriter headResponseWriter = new HeadResponseWriterLiferayImpl(wrappableResponseWriter);

		return headResponseWriter;
	}

	protected StringBundler getPageTop(PortletRequest portletRequest) {

		StringBundler pageTop = null;

		OutputData outputData = (OutputData) portletRequest.getAttribute(WebKeys.OUTPUT_DATA);

		if (outputData != null) {

			pageTop = outputData.getData(null, WebKeys.PAGE_TOP);
		}

		return pageTop;
	}

	protected void setPageTop(PortletRequest portletRequest, StringBundler pageTop) {

		OutputData outputData = (OutputData) portletRequest.getAttribute(WebKeys.OUTPUT_DATA);

		if (outputData != null) {
			outputData.setData(null, WebKeys.PAGE_TOP, pageTop);

			HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
			httpServletRequest.setAttribute(WebKeys.PAGE_TOP, pageTop);
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	protected boolean isPortletRequiresNamespacedParameters(PortletRequest portletRequest, ThemeDisplay themeDisplay) {

		boolean portletRequiresNamespacedParameters = false;

		String portletId = (String) portletRequest.getAttribute(WebKeys.PORTLET_ID);

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), portletId);
			portletRequiresNamespacedParameters = portlet.isRequiresNamespacedParameters();
		}
		catch (SystemException e) {
			logger.error(e);
		}

		return portletRequiresNamespacedParameters;
	}

}
