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
package com.liferay.faces.demos.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * @author  Neil Griffin
 */
public class CustomersViewBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CustomersViewBean.class);

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	// Private Data Members
	private boolean sendRedirect = false;
	private List<SelectItem> portalPages;
	private long portalPageId;

	public long getPortalPageId() {
		return portalPageId;
	}

	public void setPortalPageId(long portalPageId) {
		this.portalPageId = portalPageId;
	}

	public List<SelectItem> getPortalPages() {

		if ((portalPages == null) && LIFERAY_PORTAL_DETECTED) {

			portalPages = new ArrayList<SelectItem>();

			try {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ThemeDisplay themeDisplay = (ThemeDisplay) facesContext.getExternalContext().getRequestMap().get(
						WebKeys.THEME_DISPLAY);
				long groupId = themeDisplay.getScopeGroupId();
				boolean publicLayout = themeDisplay.getLayout().isPublicLayout();
				Locale locale = themeDisplay.getLocale();
				List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, !publicLayout);

				for (Layout layout : layouts) {
					long portalPageId = layout.getPlid();
					String portalPageName = layout.getHTMLTitle(locale);
					SelectItem selectItem = new SelectItem(portalPageId, portalPageName);
					portalPages.add(selectItem);
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return portalPages;
	}

	public void setSendRedirect(boolean sendRedirect) {
		this.sendRedirect = sendRedirect;
	}

	public boolean isSendRedirect() {
		return sendRedirect;
	}
}
