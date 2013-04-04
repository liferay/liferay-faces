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
package com.liferay.faces.demos.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UICommand;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.portlet.ActionResponse;
import javax.xml.namespace.QName;

import com.liferay.faces.bridge.event.EventPayloadWrapper;
import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "customersBackingBean")
@RequestScoped
public class CustomersBackingBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2920712441012786321L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CustomersBackingBean.class);

	// Injections
	@ManagedProperty(name = "customersViewBean", value = "#{customersViewBean}")
	private CustomersViewBean customersViewBean;

	@PostConstruct
	public void postConstruct() {
		logger.trace("@PostConstruct annotation worked");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("@PreDestroy annotation worked");
	}

	public void selectionListener(ActionEvent actionEvent) {

		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		Customer customer = (Customer) uiCommand.getValue();
		QName qName = new QName("http://liferay.com/events", "ipc.customerSelected");
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ActionResponse actionResponse = (ActionResponse) externalContext.getResponse();

		Serializable eventPayload = customer;

		// FACES-1465: If the user requested a redirect (Liferay Only), then  and redirect to the selected portal page.
		if (customersViewBean.isSendRedirect()) {

			// Wrap the event payload with an EventPayloadWrapper and specify that a redirect taking place. Liferay
			// Faces Bridge will detect the redirect, and maintain the Bridge Request Scope accordingly.
			eventPayload = new EventPayloadWrapper(customer, true);

			// Issue the redirect to the selected portal page.
			try {
				ThemeDisplay themeDisplay = (ThemeDisplay) externalContext.getRequestMap().get(WebKeys.THEME_DISPLAY);
				Layout layout = LayoutLocalServiceUtil.getLayout(customersViewBean.getPortalPageId());
				String portalURL = PortalUtil.getPortalURL(themeDisplay);
				String redirectPortalPageURL = portalURL + PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);
				externalContext.redirect(redirectPortalPageURL);
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		actionResponse.setEvent(qName, eventPayload);
	}

	public void setCustomersViewBean(CustomersViewBean customersViewBean) {

		// Injected via @ManagedProperty annotation
		this.customersViewBean = customersViewBean;
	}
}
