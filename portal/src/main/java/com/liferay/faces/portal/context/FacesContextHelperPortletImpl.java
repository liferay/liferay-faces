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
package com.liferay.faces.portal.context;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import com.liferay.faces.util.context.FacesContextHelperImpl;


/**
 * @author  Neil Griffin
 */
public class FacesContextHelperPortletImpl extends FacesContextHelperImpl implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3890575634255388174L;

	/** JSR-168/286 request attribute that contains an instance of javax.portlet.PortletRequest */
	private static final String REQUEST_ATTR_PORTLET_REQUEST = "javax.portlet.request";

	@Override
	public Object getRequestAttribute(String name) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();

		return portletRequest.getAttribute(name);
	}

	@Override
	public void setRequestAttribute(String name, Object value) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		portletRequest.setAttribute(name, value);
	}

	@Override
	public String getRequestContextPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}

	@Override
	public String getRequestParameter(String name) {
		PortletRequest portletRequest = (PortletRequest) FacesContext.getCurrentInstance().getExternalContext()
			.getRequest();

		return portletRequest.getParameter(name);
	}

	@Override
	public String getRequestQueryString() {

		// Some portlet bridges (like the ICEfaces bridge) wrap the portal's
		// PortletRequest implementation instance (which prevents us from getting
		// the query_string). As a workaround, we can still get it the original
		// PortletRequest instance, because the Portlet spec says it must be
		// stored in the javax.portlet.request attribute.
		String queryString = null;
		Object portletRequestAsObject = getRequestAttribute(REQUEST_ATTR_PORTLET_REQUEST);

		if ((portletRequestAsObject != null) && (portletRequestAsObject instanceof PortletRequest)) {
			PortletRequest portletRequest = (PortletRequest) portletRequestAsObject;
			queryString = (String) portletRequest.getAttribute("javax.servlet.forward.query_string");
		}

		return queryString;
	}

	@Override
	public Object getSessionAttribute(String name) {
		Object value = null;
		PortletSession portletSession = (PortletSession) getSession(false);

		if (portletSession != null) {
			value = portletSession.getAttribute(name);
		}

		return value;
	}

	@Override
	public void setSessionAttribute(String name, Object value) {
		PortletSession portletSession = (PortletSession) getSession(true);

		if (portletSession != null) {
			portletSession.setAttribute(name, value);
		}
	}
}
