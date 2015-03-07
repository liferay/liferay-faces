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

import java.io.IOException;

import javax.faces.context.ResponseWriter;
import javax.portlet.MimeResponse;
import javax.portlet.PortletResponse;

import org.w3c.dom.Element;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * Custom {@link ResponseWriter} that has the ability to write to the <head>...</head> section of the portal page via
 * the standard Portlet 2.0 MimeResponse.MARKUP_HEAD_ELEMENT mechanism.
 *
 * @author  Neil Griffin
 */
public class HeadResponseWriterImpl extends HeadResponseWriterBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(HeadResponseWriterImpl.class);

	// Private Data Members
	private PortletResponse portletResponse;

	public HeadResponseWriterImpl(ResponseWriter wrappedResponseWriter, PortletResponse portletResponse) {
		super(wrappedResponseWriter);
		this.portletResponse = portletResponse;
	}

	@Override
	public Element createElement(String name) {
		return portletResponse.createElement(name);
	}

	@Override
	protected void addResourceToHeadSection(Element element, String nodeName) throws IOException {

		// NOTE: The Portlet 2.0 Javadocs for the addProperty method indicate that if the key already exists,
		// then the element will be added to any existing elements under that key name. There is a risk that
		// multiple portlet instances on the same portal page could cause multiple <script /> elements to be
		// added to the <head>...</head> section of the rendered portal page. See:
		// http://portals.apache.org/pluto/portlet-2.0-apidocs/javax/portlet/PortletResponse.html#addProperty(java.lang.String,
		// org.w3c.dom.Element)
		portletResponse.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, element);
		logger.debug(ADDED_RESOURCE_TO_HEAD, "portal", nodeName);
	}
}
