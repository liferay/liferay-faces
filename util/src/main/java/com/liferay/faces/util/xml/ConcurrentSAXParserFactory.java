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
package com.liferay.faces.util.xml;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import com.liferay.faces.util.xml.internal.SAXParserImpl;


/**
 * <p>This class serves as an alternative to the {@link SAXParserFactory} that is provided by the JRE and provides the
 * following benefits: 1) The static {@link #newSAXParser()} method does not suffer from the performance problem of
 * obtaining parser implementations from the classpath, and 2) Since {@link #newSAXParser()} can be called without
 * performance concerns, multi-threaded environments (like web applications and portlets) can feel free to call the
 * method as much as required in each request, in order to maintain thread-safety. The drawback is that the {@link
 * SAXParserImpl} class does fully implement all of the functionality of the one provided by the JRE.</p>
 *
 * <p>For more information about the performance issues of the implementation provided by the JRE, see <a
 * href="http://www.ibm.com/developerworks/xml/library/x-perfap2/index.html">Reuse parser instances with the Xerces2 SAX
 * and DOM implementations</a>.</p>
 *
 * @author  Neil Griffin
 */
public class ConcurrentSAXParserFactory extends SAXParserFactory {

	// Private Data Members
	private Map<String, Boolean> featureMap;

	protected ConcurrentSAXParserFactory() {
		super();
		this.featureMap = new ConcurrentHashMap<String, Boolean>();
	}

	public static SAXParserFactory newInstance() {
		return new ConcurrentSAXParserFactory();
	}

	@Override
	public SAXParser newSAXParser() throws ParserConfigurationException, SAXException {
		return new SAXParserImpl(isNamespaceAware(), isValidating(), featureMap);
	}

	@Override
	public boolean getFeature(String name) throws ParserConfigurationException, SAXNotRecognizedException,
		SAXNotSupportedException {
		Boolean feature = featureMap.get(name);

		if (feature == null) {
			feature = Boolean.FALSE;
		}

		return feature;
	}

	@Override
	public void setFeature(String name, boolean value) throws ParserConfigurationException, SAXNotRecognizedException,
		SAXNotSupportedException {
		featureMap.put(name, value);
	}

}
