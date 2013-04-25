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
package com.liferay.faces.issues.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.util.lang.StringPool;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class TestBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2381428701029953349L;

	// Private Data Members
	private String alpha;
	private String beta;
	private String gamma;
	private String requestedURL;

	public TestBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		this.alpha = portletRequest.getParameter("alpha");
		this.beta = portletRequest.getParameter("beta");
		this.gamma = portletRequest.getParameter("gamma");

		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String portalURL = PortalUtil.getPortalURL(portletRequest);
		this.requestedURL = portalURL + StringPool.FORWARD_SLASH + themeDisplay.getURLCurrent();
	}

	public String getAlpha() {
		return alpha;
	}

	public String getBeta() {
		return beta;
	}

	public String getGamma() {
		return gamma;
	}

	public String getRequestedURL() {
		return requestedURL;
	}
}
