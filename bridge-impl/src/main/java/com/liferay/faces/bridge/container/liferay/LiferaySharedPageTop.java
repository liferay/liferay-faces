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
package com.liferay.faces.bridge.container.liferay;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResource;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.StringBundler;


/**
 * This class provides the ability to parse the {@link StringBundler} value of the LIFERAY_SHARED_PAGE_TOP request
 * attribute and remove duplicate CSS and/or JavaScript resources that are meant to be rendered in the <head>...</head>
 * section of the Liferay Portal page.
 *
 * @author  Neil Griffin
 */
public class LiferaySharedPageTop {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferaySharedPageTop.class);

	// Private Constants
	private static final String XML_DOCUMENT_DECLARATION = "<?xml version=\"1.0\"?>";

	// Private Data Members
	private List<HeadResource> headResources;

	public LiferaySharedPageTop(StringBundler stringBundler) {

		try {
			headResources = new ArrayList<HeadResource>();

			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			boolean validating = false;
			saxParserFactory.setValidating(validating);
			saxParserFactory.setNamespaceAware(true);

			// Obtain a SAX Parser from the factory.
			SAXParser saxParser = saxParserFactory.newSAXParser();
			SharedPageTopHandler sharedPageTopHandler = new SharedPageTopHandler();
			StringBundler xmlDocument = new StringBundler();
			xmlDocument.append(XML_DOCUMENT_DECLARATION);
			xmlDocument.append(StringPool.LESS_THAN);
			xmlDocument.append(LiferayConstants.LIFERAY_SHARED_PAGE_TOP);
			xmlDocument.append(StringPool.GREATER_THAN);
			xmlDocument.append(stringBundler);
			xmlDocument.append(StringPool.LESS_THAN);
			xmlDocument.append(StringPool.FORWARD_SLASH);
			xmlDocument.append(LiferayConstants.LIFERAY_SHARED_PAGE_TOP);
			xmlDocument.append(StringPool.GREATER_THAN);

			String xmlDocumentAsString = xmlDocument.toString().replaceAll(BridgeConstants.REGEX_AMPERSAND_DELIMITER,
					StringPool.AMPERSAND_ENCODED);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlDocumentAsString.getBytes());
			saxParser.parse(byteArrayInputStream, sharedPageTopHandler);
			byteArrayInputStream.close();
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public void removeDuplicates() {

		int totalHeadResources = headResources.size();

		for (int i = 1; i < totalHeadResources; i++) {

			HeadResource headResource1 = headResources.get(i);

			for (int j = 0; j < i; j++) {
				HeadResource headResource2 = headResources.get(j);

				if (!headResource2.isDuplicate() && headResource1.equals(headResource2)) {
					headResource2.setDuplicate(true);

					break;
				}
			}
		}

		Iterator<HeadResource> itr = headResources.iterator();

		while (itr.hasNext()) {
			HeadResource headResource = itr.next();

			if (headResource.isDuplicate()) {
				itr.remove();
			}
		}

	}

	public StringBundler toStringBundler() {
		StringBundler stringBundler = new StringBundler();

		for (HeadResource headResource : headResources) {
			stringBundler.append(headResource.toString());
		}

		return stringBundler;
	}

	protected class SharedPageTopHandler extends DefaultHandler {

		// Private Data Members
		HeadResource headResource;

		@Override
		public void characters(char[] chars, int start, int length) throws SAXException {

			if (headResource != null) {
				String text = headResource.getText();

				if (text == null) {
					text = new String(chars, start, length);
				}
				else {
					text = text + new String(chars, start, length);
				}

				headResource.setText(text);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			headResource = null;
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {

			if (!LiferayConstants.LIFERAY_SHARED_PAGE_TOP.equals(localName)) {
				headResource = new HeadResource(localName, attributes);
				headResources.add(headResource);
			}
		}
	}
}
