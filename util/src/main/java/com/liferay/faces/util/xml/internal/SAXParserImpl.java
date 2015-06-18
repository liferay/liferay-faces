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
package com.liferay.faces.util.xml.internal;

import java.util.Map;

import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;


/**
 * This class is a thread-safe implementation of {@link SAXParser}. However, it does not fully implement all of the
 * functionality of the one provided by the JRE.
 *
 * @author  Neil Griffin
 */
public class SAXParserImpl extends SAXParser {

	// Private Data Members
	private boolean namespaceAware;
	private boolean validating;
	private XMLReader xmlReader;

	public SAXParserImpl(boolean namespaceAware, boolean validating, Map<String, Boolean> featureMap) {
		this.namespaceAware = namespaceAware;
		this.validating = validating;
		this.xmlReader = new XMLReaderImpl(featureMap);
	}

	@Override
	public void reset() {
		// This method needs to be overriden in order to prevent the superclass from throwing a
		// UnsupportedOperationException. Since this implementation is thread-safe, it inherently
		// supports the reset functionality.
	}

	@Override
	public boolean isNamespaceAware() {
		return namespaceAware;
	}

	@Override
	public boolean isValidating() {
		return validating;
	}

	@Override
	@SuppressWarnings("deprecation")
	public org.xml.sax.Parser getParser() throws SAXException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {

		try {
			return getXMLReader().getProperty(name);
		}
		catch (SAXException e) {
			throw new SAXNotRecognizedException(e.getMessage());
		}
	}

	@Override
	public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {

		try {
			getXMLReader().setProperty(name, value);
		}
		catch (SAXException e) {
			throw new SAXNotRecognizedException(e.getMessage());
		}
	}

	@Override
	public XMLReader getXMLReader() throws SAXException {
		return xmlReader;
	}

}
