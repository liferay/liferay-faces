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

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class SAXHandlerFacesConfigPreImpl extends SAXHandlerFacesConfigPre {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SAXHandlerFacesConfigPreImpl.class);

	// Private Constants
	private static final String NAME = "name";

	// Private Data Members
	private String facesConfigName;
	private boolean parsingName = false;

	public SAXHandlerFacesConfigPreImpl(boolean resolveEntities) {
		super(resolveEntities);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (parsingName) {
			facesConfigName = content.toString().trim();
		}

		content = null;
		parsingName = false;

		if (facesConfigName != null) {

			// Abort processing since the <name>...</name> has been parsed.
			throw new SAXParseCompleteException();
		}
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
		throws SAXException {

		logger.trace("localName=[{0}]", localName);

		content = new StringBuilder();

		if (localName.equals(NAME)) {
			parsingName = true;
		}
	}

	@Override
	public String getFacesConfigName() {
		return facesConfigName;
	}
}
