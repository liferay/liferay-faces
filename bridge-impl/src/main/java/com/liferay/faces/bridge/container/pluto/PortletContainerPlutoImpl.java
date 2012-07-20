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
package com.liferay.faces.bridge.container.pluto;

import java.net.MalformedURLException;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.container.PortletContainerImpl;


/**
 * @author  Neil Griffin
 */
public class PortletContainerPlutoImpl extends PortletContainerImpl {

	// serialVersionUID
	private static final long serialVersionUID = 2488317466257094615L;

	// Private Constants
	private static final String CONTENT_TYPE_APPLICATION_XHTML_XML = "application/xhtml+xml";
	private static final String CONTENT_TYPE_TEXT_HTML = "text/html";

	public PortletContainerPlutoImpl(PortletRequest portletRequest, BridgeConfig bridgeConfig) {
		super(portletRequest, bridgeConfig);
	}

	/**
	 * This method overrides the default implementation in order wrap Pluto's ResourceURL implementation with a wrapper
	 * that overrides the toString() method.
	 */
	@Override
	public ResourceURL createResourceURL(String fromURL) throws MalformedURLException {
		return new PlutoResourceURL(super.createResourceURL(fromURL));
	}

	@Override
	public void setMimeResponseContentType(MimeResponse mimeResponse, String contentType) {

		String contentTypeForPluto = null;

		// If the specified contentType is "application/xhtml+xml" then use "text/html" instead. That's the only value
		// that Pluto's RenderResponseImpl.setContentType(String) will be happy with, even though Pluto's "ACCEPT"
		// header claims it can accept "application/xhtml+xml".
		if (CONTENT_TYPE_APPLICATION_XHTML_XML.equals(contentType)) {
			contentTypeForPluto = CONTENT_TYPE_TEXT_HTML;
		}

		// Otherwise, use the specified contentType.
		else {
			contentTypeForPluto = contentType;
		}

		mimeResponse.setContentType(contentTypeForPluto);
	}
}
