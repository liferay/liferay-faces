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
package com.liferay.faces.bridge.context.internal;

import javax.portlet.PortalContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;


/**
 * @author  Neil Griffin
 */
public class BridgePortalContextLiferayImpl extends BridgePortalContextLiferayCompatImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePortalContextLiferayImpl.class);

	// Private Data Members
	private String ableToAddScriptResourceToHead;
	private String ableToAddScriptTextToHead;
	private String ableToAddStyleSheetResourceToHead;
	private String ableToSetHttpStatusCode;
	private String liferayAbleToSetHttpStatusCode;
	private String namespacedParametersRequired;

	public BridgePortalContextLiferayImpl(PortalContext portalContext, PortletRequest portletRequest,
		Product liferayPortal) {

		super(portalContext);

		// Determine whether or not the portlet was added via $theme.runtime(...)
		Boolean renderPortletResource = (Boolean) portletRequest.getAttribute("RENDER_PORTLET_RESOURCE");
		boolean runtimePortlet = (renderPortletResource != null) && renderPortletResource;

		// If this is a runtime portlet, then it is not possible to add resources to the head section since
		// top_head.jsp is included prior to the runtime portlet being invoked.
		if (runtimePortlet) {
			this.ableToAddScriptResourceToHead = null;
			this.ableToAddScriptTextToHead = null;
			this.ableToAddStyleSheetResourceToHead = null;
		}

		// Otherwise,
		else {

			// If this portlet is running via WSRP, then it is not possible to add resources to the head section
			// because Liferay doesn't support that feature with WSRP.
			if (BooleanHelper.isTrueToken(portletRequest.getParameter("wsrp"))) {
				this.ableToAddScriptResourceToHead = null;
				this.ableToAddScriptTextToHead = null;
				this.ableToAddStyleSheetResourceToHead = null;
			}

			// Otherwise, Liferay is able to add resources to the head section, albeit with a vendor-specific
			// (non-standard) way.
			else {
				this.ableToAddScriptResourceToHead = "true";
				this.ableToAddScriptTextToHead = "true";
				this.ableToAddStyleSheetResourceToHead = "true";
			}
		}

		// Determine the Liferay version number.
		int liferayBuildNumber = liferayPortal.getBuildId();

		if (logger.isDebugEnabled()) {
			logger.debug("Detected Liferay build number {0}", Long.toString(liferayBuildNumber));
		}

		// Note that Liferay didn't support the Portlet 2.0 ResourceResponse.HTTP_STATUS_CODE feature until Liferay
		// 6.0.6 CE and 6.0.11 EE. See: http://issues.liferay.com/browse/LPS-9145
		// Also note that Liferay 6.0 EE version numbering begins with 6.0.10 (6010).
		if ((liferayBuildNumber >= 6011) || ((liferayBuildNumber >= 6005) && (liferayBuildNumber <= 6010))) {
			liferayAbleToSetHttpStatusCode = "true";
		}

		if (isLiferayNamingspacingParameters(portletRequest)) {
			this.namespacedParametersRequired = "true";
		}
	}

	@Override
	protected String getAddScriptResourceToHead() {
		return ableToAddScriptResourceToHead;
	}

	@Override
	protected String getAddScriptTextToHead() {
		return ableToAddScriptTextToHead;
	}

	@Override
	protected String getAddStyleSheetResourceToHead() {
		return ableToAddStyleSheetResourceToHead;
	}

	@Override
	public String getCreateRenderUrlDuringActionPhase() {
		return "true";
	}

	@Override
	protected String getMarkupHeadElementSupported() {

		// Liferay Portal added support for this feature in v6.0.3 but a bug prevented it from working, even in v6.0.5.
		// For the sake of consistency across all versions of Liferay, return null.
		// http://issues.liferay.com/browse/LPE-2729
		// http://issues.liferay.com/browse/LPS-11767
		return null;
	}

	@Override
	protected String getNamespacedParametersRequired() {
		return namespacedParametersRequired;
	}

	@Override
	protected String getPostRedirectGetSupported() {

		// Liferay Portal does not implement the POST-REDIRECT-GET design pattern. Rather, the ACTION_PHASE and
		// RENDER_PHASE are both part of a single HTTP POST request.
		return null;
	}

	@Override
	protected String getSetHttpStatusCode() {

		if (ableToSetHttpStatusCode == null) {

			ableToSetHttpStatusCode = super.getSetHttpStatusCode();

			if (ableToSetHttpStatusCode == null) {
				ableToSetHttpStatusCode = liferayAbleToSetHttpStatusCode;
			}
		}

		return ableToSetHttpStatusCode;
	}
}
