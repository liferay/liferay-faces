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
package com.liferay.faces.bridge.renderkit.html_basic;

import java.io.IOException;
import java.util.EmptyStackException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.portlet.PortletRequest;

import org.w3c.dom.Element;

import com.liferay.faces.bridge.container.liferay.StringBundler;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.WebKeys;


/**
 * Custom {@link ResponseWriter} that has the ability to write to the <head>...</head> section of the portal page via
 * the Liferay vendor-specific mechanism.
 *
 * @author  Neil Griffin
 */
public class HeadResponseWriterLiferayImpl extends HeadResponseWriter {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(HeadResponseWriterLiferayImpl.class);

	// Private Data Members
	private BridgeContext bridgeContext;

	public HeadResponseWriterLiferayImpl(BridgeContext bridgeContext, ResponseWriter wrappedResponseWriter) {
		super(wrappedResponseWriter);
		this.bridgeContext = bridgeContext;
	}

	@Override
	public Element createElement(String name) {
		return new ElementImpl(name);
	}

	@Override
	public void endElement(String name) throws IOException {

		try {
			ElementWriter elementWriter = elementWriterStack.pop();
			Element element = elementWriter.getElement();
			String nodeName = element.getNodeName();
			logger.trace("POPPED element name=[{0}]", nodeName);

			if (!ELEMENT_HEAD.equals(element.getNodeName())) {
				String elementAsString = element.toString();

				PortletRequest portletRequest = bridgeContext.getPortletRequest();
				StringBundler stringBundler = new StringBundler(portletRequest.getAttribute(WebKeys.PAGE_TOP));

				stringBundler.append(elementAsString);

				portletRequest.setAttribute(WebKeys.PAGE_TOP, stringBundler.getWrapped());

				logger.debug("Added resource to Liferay's <head>...</head> section, element=[{0}]", elementAsString);
			}
		}
		catch (EmptyStackException e) {
			throw new IOException(EmptyStackException.class.getSimpleName());
		}
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		Element element = createElement(name);
		ElementWriter elementWriter = new ElementWriter(element);
		elementWriterStack.push(elementWriter);
		logger.trace("PUSHED element name=[{0}]", name);
	}

}
