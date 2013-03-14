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
package com.liferay.faces.bridge.config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.bridge.bean.BeanManagerFactory;
import com.liferay.faces.bridge.context.flash.BridgeFlashFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public abstract class SAXHandlerFacesConfigPostImpl_2_0 extends SAXHandlerFacesConfigPostImpl_1_2 {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SAXHandlerFacesConfigPostImpl_2_0.class);

	// Private Constants
	private static final String BRIDGE_FLASH_FACTORY = "bridge-flash-factory";

	// Private Data Members
	private boolean parsingBridgeFlashFactory;

	public SAXHandlerFacesConfigPostImpl_2_0(boolean resolveEntities,
		BridgeConfigAttributeMap bridgeConfigAttributeMap) {
		super(resolveEntities, bridgeConfigAttributeMap);
	}

	@Override
	public void endElement(String uri, String localName, String elementName) throws SAXException {

		if (parsingBridgeFlashFactory) {
			setupFactory(BridgeFlashFactory.class, content.toString().trim());
		}
		else {
			super.endElement(uri, localName, elementName);
		}

		parsingBridgeFlashFactory = false;
	}

	@Override
	public void logMissingElementErrors() {

		if (bridgeConfigAttributeMap.get(BeanManagerFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_FLASH_FACTORY);
		}
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
		throws SAXException {

		if (localName.equals(BRIDGE_FLASH_FACTORY)) {
			parsingBridgeFlashFactory = true;
		}
		else {
			super.startElement(uri, localName, elementName, attributes);
		}
	}

}
