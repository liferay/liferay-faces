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
package com.liferay.faces.bridge.event.internal.liferay;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.bridge.context.BridgeContext;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public class LiferyPageTopPhaseListenerCompat implements PhaseListener {

	// serialVersionUID
	private static final long serialVersionUID = 8713570232856573935L;

	// Private Data Members
	private int liferaySharedPageTopLength;

	/**
	 * This method is called after the {@link PhaseId#RENDER_RESPONSE} phase of the JSF lifecycle.
	 */
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

	protected StringBundler getPageTop(PortletRequest portletRequest) {

		// Versions of Liferay Portal prior to 6.2 simply reference "LIFERAY_SHARED_PAGE_TOP" attribute.
		return new StringBundler(portletRequest.getAttribute(WebKeys.PAGE_TOP));
	}

	protected void setPageTop(PortletRequest portletRequest, StringBundler pageTop) {

		// The value of the "LIFERAY_SHARED_PAGE_TOP" attribute must be set on the underlying HttpServletRequest to
		// ensure that the attribute name does not get prefixed with the portlet namespace.
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		httpServletRequest.setAttribute(WebKeys.PAGE_TOP, pageTop.getWrapped());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}
