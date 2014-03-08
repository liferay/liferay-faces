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
package com.liferay.faces.util.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.xml.SAXHandlerBase;
import com.liferay.faces.util.xml.SAXParseCompleteException;


/**
 * @author  Neil Griffin
 */
public class FacesConfigDescriptorParserImpl extends SAXHandlerBase implements FacesConfigDescriptorParser {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesConfigDescriptorParserImpl.class);

	// Private Constants
	private static final String NAME = "name";

	// Private Data Members
	private String facesConfigName;
	private boolean parsingName = false;
	private SAXParser saxParser;

	public FacesConfigDescriptorParserImpl(SAXParser saxParser, boolean resolveEntities) {
		super(resolveEntities);
		this.saxParser = saxParser;
	}

	@Override
	public void endElement(String uri, String localName, String elementName) throws SAXException {

		if (parsingName) {
			facesConfigName = content.toString().trim();
			parsingName = false;

			if (facesConfigName.length() > 0) {
				throw new SAXParseCompleteException();
			}
		}
		else {
			super.endElement(uri, localName, elementName);
		}

		content = null;
	}

	public FacesConfigDescriptor parse(InputStream inputStream, URL url) throws IOException {

		try {

			try {
				saxParser.parse(inputStream, this);
			}
			catch (SAXParseCompleteException e) {
				// ignore -- this indicates cessation of processing when the facesConfigName is discovered.
			}

			FacesConfigDescriptor facesConfigDescriptor = new FacesConfigDescriptorImpl(facesConfigName, url);
			saxParser.reset();

			return facesConfigDescriptor;
		}
		catch (SAXException e) {
			logger.error(e);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
		throws SAXException {

		content = new StringBuilder();

		if (localName.equals(NAME)) {
			parsingName = true;
		}
		else {
			super.startElement(uri, localName, elementName, attributes);
		}
	}
}
