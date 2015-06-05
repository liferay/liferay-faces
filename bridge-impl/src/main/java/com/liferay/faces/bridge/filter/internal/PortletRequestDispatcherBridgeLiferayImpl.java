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

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.filter.PortletRequestWrapper;

import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class PortletRequestDispatcherBridgeLiferayImpl extends PortletRequestDispatcherWrapper {

	// Private Constants
	private static final Product LIFERAY_PORTAL = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);
	private static final int LIFERAY_PORTAL_MAJOR_VERSION = LIFERAY_PORTAL.getMajorVersion();
	private static final int LIFERAY_PORTAL_MINOR_VERSION = LIFERAY_PORTAL.getMinorVersion();
	private static final int LIFERAY_PORTAL_REVISION_VERSION = LIFERAY_PORTAL.getRevisionVersion();

	// Private Data Members
	private PortletRequestDispatcher wrappedPortletRequestDispatcher;

	public PortletRequestDispatcherBridgeLiferayImpl(PortletRequestDispatcher portletRequestDispatcher) {
		this.wrappedPortletRequestDispatcher = portletRequestDispatcher;
	}

	@Override
	public void forward(PortletRequest portletRequest, PortletResponse portletResponse) throws PortletException,
		IOException {

		// Liferay Portal's implementation of PortletRequestDispatcher.forward(PortletRequest,PortletResponse) is not
		// compatible with the requirements of the JSF Portlet Bridge and causes failures in the Bridge TCK
		// dispatchUsesForwardTest and bridgeSetsContentTypeTest. As a workaround, call
		// PortletRequestDispatcher.include(PortletRequest,PortletResponse).
		include(portletRequest, portletResponse);
	}

	@Override
	public void include(PortletRequest portletRequest, PortletResponse portletResponse) throws PortletException,
		IOException {

		boolean unwrapRequest = false;

		// Versions of Liferay Portal older than 6.0.0 throw a ClassCastException when RenderRequestWrapper is used.
		// For more info, see https://issues.liferay.com/browse/LPS-3311
		if (LIFERAY_PORTAL_MAJOR_VERSION < 6) {
			unwrapRequest = true;
		}

		// Versions of Liferay Portal older than 6.1.2 (CE) and 6.1.30 (EE) will throw a ClassCastException when a
		// PortletRequestWrapper is specified. For more info, see https://issues.liferay.com/browse/LPS-36713 and
		// https://github.com/liferay/liferay-portal/commit/093dabbb252e2bba5404cddbcb600d787ef0b010
		else if (LIFERAY_PORTAL_MAJOR_VERSION == 6) {

			if (LIFERAY_PORTAL_MINOR_VERSION == 0) {
				unwrapRequest = true;
			}
			else if (LIFERAY_PORTAL_MINOR_VERSION == 1) {

				// CE
				if (LIFERAY_PORTAL_REVISION_VERSION < 10) {

					unwrapRequest = (LIFERAY_PORTAL_REVISION_VERSION < 2);
				}

				// EE
				else if (LIFERAY_PORTAL_REVISION_VERSION < 30) {
					unwrapRequest = true;
				}
			}
		}

		if (unwrapRequest) {
			super.include(getLiferayPortletRequest(portletRequest), portletResponse);
		}
		else {
			super.include(portletRequest, portletResponse);
		}
	}

	protected PortletRequest getLiferayPortletRequest(PortletRequest portletRequest) {

		if (portletRequest instanceof PortletRequestWrapper) {

			PortletRequestWrapper portletRequestWrapper = (PortletRequestWrapper) portletRequest;

			PortletRequest wrappedPortletRequest = portletRequestWrapper.getRequest();
			portletRequest = getLiferayPortletRequest(wrappedPortletRequest);
		}

		return portletRequest;
	}

	@Override
	public PortletRequestDispatcher getWrapped() {
		return wrappedPortletRequestDispatcher;
	}
}
