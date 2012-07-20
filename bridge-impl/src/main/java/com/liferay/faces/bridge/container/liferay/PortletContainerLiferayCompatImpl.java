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
package com.liferay.faces.bridge.container.liferay;

import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.container.PortletContainerImpl;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriter;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriterLiferayImpl;

import com.liferay.portal.kernel.util.StringBundler;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public class PortletContainerLiferayCompatImpl extends PortletContainerImpl {

	// serialVersionUID
	private static final long serialVersionUID = 8713570232856573935L;

	// Private Data Members
	private int liferaySharedPageTopLength;

	public PortletContainerLiferayCompatImpl(PortletRequest portletRequest, BridgeConfig bridgeConfig) {
		super(portletRequest, bridgeConfig);
	}

	/**
	 * This method is called after the {@link PhaseId#RENDER_RESPONSE} phase of the JSF lifecycle. It's purpose is to
	 * remove duplicate resources from the LIFERAY_SHARED_PAGE_TOP request attribute. For more information, see:
	 * http://issues.liferay.com/browse/FACES-1216
	 */
	@Override
	public void afterPhase(PhaseEvent phaseEvent) {

		if (liferaySharedPageTopLength > 0) {
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

			PortletRequest portletRequest = bridgeContext.getPortletRequest();

			StringBundler stringBundler = (StringBundler) portletRequest.getAttribute(
					LiferayConstants.LIFERAY_SHARED_PAGE_TOP);

			if (stringBundler != null) {

				LiferaySharedPageTop liferaySharedPageTop = new LiferaySharedPageTop(stringBundler);
				liferaySharedPageTop.removeDuplicates();
				stringBundler = liferaySharedPageTop.toStringBundler();
				portletRequest.setAttribute(LiferayConstants.LIFERAY_SHARED_PAGE_TOP, stringBundler);
			}
		}
	}

	/**
	 * This method is called prior to the {@link PhaseId#RENDER_RESPONSE} phase of the JSF lifecycle. It's purpose is to
	 * determine if there are any resources in the LIFERAY_SHARED_PAGE_TOP request attribute, so that execution of the
	 * {@link #afterPhase(PhaseEvent)} can be optimized.
	 */
	@Override
	public void beforePhase(PhaseEvent phaseEvent) {
		liferaySharedPageTopLength = 0;

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		PortletRequest portletRequest = bridgeContext.getPortletRequest();
		StringBundler stringBundler = (StringBundler) portletRequest.getAttribute(
				LiferayConstants.LIFERAY_SHARED_PAGE_TOP);

		if (stringBundler != null) {
			liferaySharedPageTopLength = stringBundler.length();
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
