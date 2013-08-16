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
package com.liferay.faces.bridge.scope;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.faces.Bridge;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import com.liferay.faces.bridge.container.liferay.LiferayConstants;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.JavaConstants;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeLiferayImpl extends BridgeRequestScopeImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeLiferayImpl.class);

	// serialVersionUID
	private static final long serialVersionUID = 1814762389345663517L;

	// Private Constants
	private static final String[] LIFERAY_ATTRIBUTE_NAMES;
	private static final String[] PORTLET_REQUEST_ATTRIBUTE_NAMES = new String[] {
			PortletRequest.CCPP_PROFILE, PortletRequest.LIFECYCLE_PHASE, PortletRequest.RENDER_HEADERS,
			PortletRequest.RENDER_MARKUP, PortletRequest.RENDER_PART, PortletRequest.USER_INFO
		};

	static {

		// Set the value of the LIFERAY_ATTRIBUTE_NAMES constant. Need to use reflection in order to determine all of
		// the public constants because different versions of the portal source have different sets of constants. This
		// approach minimizes diffs in the different source branches for the bridge.
		List<String> fieldList = new ArrayList<String>();
		Field[] fields = JavaConstants.class.getFields();

		for (Field field : fields) {

			String fieldName = field.getName();

			if ((fieldName != null) && fieldName.startsWith("JAVAX")) {

				try {
					Object value = field.get(null);

					if ((value != null) && (value instanceof String)) {
						fieldList.add((String) value);
					}
				}
				catch (Exception e) {
					logger.error(e);
				}
			}
		}

		LIFERAY_ATTRIBUTE_NAMES = fieldList.toArray(new String[fieldList.size()]);
	}

	public BridgeRequestScopeLiferayImpl(PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest) {
		super(portletConfig, portletContext, portletRequest);
	}

	/**
	 * This is a method-override that provides specific behavior for Liferay Portal. Specifically, since Liferay Portal
	 * does not implement the POST-REDIRECT-GET design pattern, not all instance types listed in Section 5.1.2 of the
	 * Spec can be candidates for exclusion.
	 */
	@Override
	protected boolean isExcludedRequestAttributeByInstance(String attributeName, Object attributeValue) {

		boolean excluded = false;

		if (attributeValue != null) {

			if ((attributeValue instanceof ExternalContext) || (attributeValue instanceof FacesContext)) {

				// Always safe to exclude when running under Liferay Portal.
				excluded = true;
			}
			else if (attributeValue instanceof PortletConfig) {

				// Liferay Portal includes request attribute named "javax.portlet.config" that must not be excluded. It
				// also includes an attribute named "javax.portlet.portlet" that is the current GenericFacesPortlet
				// (which extends GenericPortlet). But since GenericPortlet implements the PortletConfig interface, need
				// to prevent it from being excluded as well.
				if (!JavaConstants.JAVAX_PORTLET_CONFIG.equals(attributeName) &&
						!JavaConstants.JAVAX_PORTLET_PORTLET.equals(attributeName)) {
					excluded = true;
				}
			}
			else if (attributeValue instanceof PortletRequest) {

				// Liferay Portal includes request attribute named "javax.portlet.request" that must not be excluded.
				if (!JavaConstants.JAVAX_PORTLET_REQUEST.equals(attributeName)) {
					excluded = true;
				}
			}
			else if (attributeValue instanceof PortletResponse) {

				// Liferay Portal includes request attribute named "javax.portlet.response" that must not be excluded.
				if (!JavaConstants.JAVAX_PORTLET_RESPONSE.equals(attributeName)) {
					excluded = true;
				}
			}
			else if ((attributeValue instanceof PortalContext) || (attributeValue instanceof PortletContext) ||
					(attributeValue instanceof PortletPreferences) || (attributeValue instanceof PortletSession)) {

				excluded = true;
			}
			else if ((attributeValue instanceof HttpSession) || (attributeValue instanceof ServletConfig) ||
					(attributeValue instanceof ServletContext) || (attributeValue instanceof ServletRequest) ||
					(attributeValue instanceof ServletResponse)) {

				// Can only exclude attributes that are not Liferay objects. For example, Liferay Portal includes
				// a request attribute named "com.liferay.portal.kernel.servlet.PortletServletRequest" that must not be
				// excluded.
				if (!attributeValue.getClass().getName().startsWith(LiferayConstants.PACKAGE_NAMESPACE)) {
					excluded = true;
				}
			}
		}

		return excluded;
	}

	/**
	 * This is a method-override that provides specific behavior for Liferay Portal. Specifically, since Liferay Portal
	 * does not implement the POST-REDIRECT-GET design pattern, not all instance types listed in Section 5.1.2 of the
	 * Spec can be candidates for exclusion.
	 */
	@Override
	protected boolean isExcludedRequestAttributeByNamespace(String attributeName) {

		boolean excluded = false;

		if (isNamespaceMatch(attributeName, EXCLUDED_NAMESPACE_JAVAX_FACES) ||
				isNamespaceMatch(attributeName, EXCLUCED_NAMESPACE_JAVAX_SERVLET) ||
				isNamespaceMatch(attributeName, EXCLUCED_NAMESPACE_JAVAX_SERVLET_INCLUDE)) {

			// Always safe to exclude when running under Liferay Portal.
			excluded = true;
		}
		else if (isNamespaceMatch(attributeName, EXCLUDED_NAMESPACE_JAVAX_PORTLET_FACES)) {

			if (!Bridge.PORTLET_LIFECYCLE_PHASE.equals(attributeName)) {

				// The "javax.portlet.faces.phase" request attribute must never be excluded, as it is required by {@link
				// BridgeUtil#getPortletRequestPhase()}.
				excluded = true;
			}
		}
		else if (isNamespaceMatch(attributeName, EXCLUDED_NAMESPACE_JAVAX_PORTLET)) {
			excluded = true;
		}

		if (excluded) {

			for (String liferayAttributeName : LIFERAY_ATTRIBUTE_NAMES) {

				if (liferayAttributeName.equals(attributeName)) {
					excluded = false;

					break;
				}
			}
		}

		if (excluded) {

			for (String portletRequestAttributeName : PORTLET_REQUEST_ATTRIBUTE_NAMES) {

				if (portletRequestAttributeName.equals(attributeName)) {
					excluded = false;

					break;
				}
			}
		}

		return excluded;
	}
}
