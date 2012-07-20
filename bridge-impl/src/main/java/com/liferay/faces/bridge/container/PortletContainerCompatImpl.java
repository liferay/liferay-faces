/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.container;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import javax.faces.context.ResponseWriter;
import javax.portlet.ResourceResponse;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriter;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriterImpl;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class PortletContainerCompatImpl implements PortletContainer {

	protected void redirectJSF2PartialResponse(FacesContext facesContext, ResourceResponse resourceResponse, String url)
		throws IOException {
		resourceResponse.setContentType("text/xml");
		resourceResponse.setCharacterEncoding("UTF-8");

		PartialResponseWriter partialResponseWriter;
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (responseWriter instanceof PartialResponseWriter) {
			partialResponseWriter = (PartialResponseWriter) responseWriter;
		}
		else {
			partialResponseWriter = facesContext.getPartialViewContext().getPartialResponseWriter();
		}

		partialResponseWriter.startDocument();
		partialResponseWriter.redirect(url);
		partialResponseWriter.endDocument();
		facesContext.responseComplete();
	}

	public HeadResponseWriter getHeadResponseWriter(ResponseWriter wrappableResponseWriter) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		HeadResponseWriter headResponseWriter = new HeadResponseWriterImpl(wrappableResponseWriter,
				bridgeContext.getPortletResponse());

		return headResponseWriter;
	}

	protected boolean isJSF2PartialRequest(FacesContext facesContext) {
		return facesContext.getPartialViewContext().isPartialRequest();
	}
}
