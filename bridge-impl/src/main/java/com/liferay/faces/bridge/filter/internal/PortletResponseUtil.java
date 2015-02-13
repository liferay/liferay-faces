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
package com.liferay.faces.bridge.filter.internal;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.config.internal.PortletConfigParam;
import com.liferay.faces.bridge.container.liferay.internal.LiferayPortalUtil;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.internal.BridgeConstants;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
class PortletResponseUtil {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();
	private static final String NON_NUMERIC_NAMESPACE_PREFIX = "A";

	public static String getNamespace(PortletResponse portletResponse) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletConfig portletConfig = bridgeContext.getPortletConfig();

		boolean optimizePortletNamespace = PortletConfigParam.OptimizePortletNamespace.getBooleanValue(portletConfig);

		// Initialize the response namespace.
		String responseNamespace = portletResponse.getNamespace();

		if (responseNamespace.startsWith(BridgeConstants.WSRP_REWRITE)) {

			if (LIFERAY_PORTAL_DETECTED) {
				PortletRequest portletRequest = bridgeContext.getPortletRequest();
				responseNamespace = LiferayPortalUtil.getPortletId(portletRequest);
			}
			else {
				String portletName = portletConfig.getPortletName();
				PortletContext portletContext = bridgeContext.getPortletContext();
				String portletContextName = portletContext.getPortletContextName();
				responseNamespace = portletName + portletContextName;
			}
		}

		if (optimizePortletNamespace) {

			// Since the namespace is going to appear in every single clientId and name attribute of the rendered
			// view, this needs to be shortened as much as possible -- four characters should be enough to keep the
			// namespace unique.
			int hashCode = responseNamespace.hashCode();

			if (hashCode < 0) {
				hashCode = hashCode * -1;
			}

			String namespaceHashCode = Integer.toString(hashCode);
			int namespaceHashCodeLength = namespaceHashCode.length();

			if (namespaceHashCodeLength > 4) {

				// FACES-67: Taking the last four characters is more likely to force uniqueness than the first four.
				namespaceHashCode = namespaceHashCode.substring(namespaceHashCodeLength - 4);
			}

			if (namespaceHashCode.length() < responseNamespace.length()) {

				// Note that unless we prepend the hash namespace with some non-numeric string, IE might encounter
				// JavaScript problems with ICEfaces. http://issues.liferay.com/browse/FACES-12
				responseNamespace = NON_NUMERIC_NAMESPACE_PREFIX + namespaceHashCode;
			}
		}

		return responseNamespace;
	}
}
