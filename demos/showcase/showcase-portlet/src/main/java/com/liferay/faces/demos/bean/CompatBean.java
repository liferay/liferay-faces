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
package com.liferay.faces.demos.bean;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeURL;


/**
 * @author  Neil Griffin
 */
public class CompatBean {

	public String getComponentURL(String componentPrefix, String componentName, String componentUseCase) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		String actionURL = viewHandler.getActionURL(facesContext, "/WEB-INF/views/component.xhtml");
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		BridgeURL bookmarkableURL = bridgeContext.encodeBookmarkableURL(actionURL, null);
		bookmarkableURL.setParameter("componentPrefix", componentPrefix);
		bookmarkableURL.setParameter("componentName", componentName);
		bookmarkableURL.setParameter("componentUseCase", componentUseCase);

		return bookmarkableURL.toString();
	}
}
