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
package com.liferay.faces.bridge.container.liferay;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.liferay.faces.bridge.renderkit.html_basic.HeadResource;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;


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

	// FACES-1442: The SAXParserFactory.newSAXParser() method that comes with the JRE suffers from a
	// performance problem. Use the Liferay factory intead.
	private static final com.liferay.faces.util.xml.SAXParserFactory saxParserFactory =
		com.liferay.faces.util.xml.SAXParserFactory.newInstance();

	// Private Data Members
	private List<HeadResource> headResources;

	public LiferaySharedPageTop(StringBundler stringBundler) {

		try {
			headResources = new ArrayList<HeadResource>();

			SharedPageTopHandler sharedPageTopHandler = new SharedPageTopHandler();
			StringBundler xmlDocument = new StringBundler();
			xmlDocument.append(XML_DOCUMENT_DECLARATION);
			xmlDocument.append(StringPool.LESS_THAN);
			xmlDocument.append(WebKeys.PAGE_TOP);
			xmlDocument.append(StringPool.GREATER_THAN);
			xmlDocument.append(stringBundler);
			xmlDocument.append(StringPool.LESS_THAN);
			xmlDocument.append(StringPool.FORWARD_SLASH);
			xmlDocument.append(WebKeys.PAGE_TOP);
			xmlDocument.append(StringPool.GREATER_THAN);

			String xmlDocumentAsString = xmlDocument.toString();

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlDocumentAsString.getBytes());

			SAXParser saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(byteArrayInputStream, sharedPageTopHandler);

			byteArrayInputStream.close();
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public void removeDuplicates() {

		int totalHeadResources = headResources.size();

		for (int i = 0; i < totalHeadResources; i++) {

			HeadResource headResource1 = headResources.get(i);

			for (int j = i + 1; j < totalHeadResources; j++) {
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
			String headResourceMarkup = headResource.toString();

			// One of the by-products of parsing an XML document is that the SAXParser converts "&amp;" to "&" for
			// attribute values. For example, if the XML document specifies a URL as "foo.jsp?x=1&amp;y=2" then the
			// parser will set the attribute value "foo.jsp?x=1&y=2". So in order for the string representation
			// to accurately reflect the values in the original XML document that was parsed, it is necessary to
			// re-encode the ampersands.
			headResourceMarkup = encodeAmpersands(headResourceMarkup);
			stringBundler.append(headResourceMarkup);
		}

		return stringBundler;
	}

	protected String encodeAmpersands(String value) {

		String encodedValue = value;

		int ampersandPos = value.indexOf(StringPool.AMPERSAND);

		if (ampersandPos > 0) {

			int startPos = 0;
			StringBuilder buf = new StringBuilder();

			while (ampersandPos > 0) {

				buf.append(value.substring(startPos, ampersandPos));
				buf.append(StringPool.AMPERSAND_ENCODED);

				startPos = ampersandPos + 1;

				ampersandPos = value.indexOf(StringPool.AMPERSAND, startPos);
			}

			if (startPos < value.length()) {
				buf.append(value.substring(startPos));
			}

			encodedValue = buf.toString();
		}

		return encodedValue;
	}

	protected class SharedPageTopHandler extends DefaultHandler {

		// Private Data Members
		private HeadResource headResource;

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

			if (!WebKeys.PAGE_TOP.equals(qName)) {
				headResource = new HeadResource(qName, attributes);
				headResources.add(headResource);
			}
		}
	}
}
