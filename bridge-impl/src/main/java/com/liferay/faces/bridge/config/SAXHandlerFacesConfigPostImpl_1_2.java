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
package com.liferay.faces.bridge.config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.bridge.application.ViewHandlerFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides a compatibility layer that isolates differences for JSF 1.2.
 *
 * @author  Neil Griffin
 */
public abstract class SAXHandlerFacesConfigPostImpl_1_2 extends SAXHandlerFacesConfigPost {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SAXHandlerFacesConfigPostImpl_1_2.class);

	// Private Constants
	private static final String VIEW_HANDLER_FACTORY = "view-handler-factory";

	// Private Data Members
	private boolean parsingViewHandlerFactory;

	public SAXHandlerFacesConfigPostImpl_1_2(boolean resolveEntities,
		BridgeConfigAttributeMap bridgeConfigAttributeMap) {
		super(resolveEntities, bridgeConfigAttributeMap);
	}

	@Override
	public void endElement(String uri, String localName, String elementName) throws SAXException {

		if (parsingViewHandlerFactory) {
			setupFactory(ViewHandlerFactory.class, content.toString().trim());
		}
		else {
			super.endElement(uri, localName, elementName);
		}

		parsingViewHandlerFactory = false;
	}

	@Override
	public void logMissingElementErrors() {

		if (bridgeConfigAttributeMap.get(ViewHandlerFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, VIEW_HANDLER_FACTORY);
		}
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
		throws SAXException {

		if (localName.equals(VIEW_HANDLER_FACTORY)) {
			parsingViewHandlerFactory = true;
		}
		else {
			super.startElement(uri, localName, elementName, attributes);
		}
	}
}
