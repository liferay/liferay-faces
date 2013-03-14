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


/**
 * This class provides a compatibility layer that isolates differences for JSF 1.2.
 *
 * @author  Neil Griffin
 */
public abstract class SAXHandlerFacesConfigPostImpl_1_2 extends SAXHandlerFacesConfigPost {

	public SAXHandlerFacesConfigPostImpl_1_2(boolean resolveEntities,
		BridgeConfigAttributeMap bridgeConfigAttributeMap) {
		super(resolveEntities, bridgeConfigAttributeMap);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// This is a no-op for JSF 2.
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
		throws SAXException {
		// This is a no-op for JSF 2.
	}
}
