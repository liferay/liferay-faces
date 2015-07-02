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

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Assert;
import org.junit.Test;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.xml.internal.SAXParserImpl;


/**
 * @author  Neil Griffin
 */
public class SAXParserTest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SAXParserTest.class);

	@Test
	public void testFaceletComposition() {

		try {
			SAXParserFactory saxParserFactory = ConcurrentSAXParserFactory.newInstance();

			if (saxParserFactory != null) {
				SAXParser saxParser = saxParserFactory.newSAXParser();

				if (saxParser != null) {

					if (saxParser instanceof SAXParserImpl) {

						URL url = getClass().getClassLoader().getResource("applicant.xhtml");
						TestHandler testHandler = new TestHandler();
						saxParser.parse(url.openStream(), testHandler);
						Assert.assertEquals(1, testHandler.getTotalComments());
						Assert.assertEquals(4, testHandler.getTotalPlainText());
						Assert.assertEquals(91, testHandler.getTotalStartElements());
						Assert.assertEquals(91, testHandler.getTotalEndElements());
						Assert.assertEquals(144, testHandler.getTotalAttributes());
						Assert.assertEquals(2, testHandler.getTotalInlineScripts());
						Assert.assertEquals(1, testHandler.getTotalSpanText());
						Assert.assertEquals(1, testHandler.getTotalHeadingText());
					}
					else {
						Assert.fail();
					}
				}
				else {
					Assert.fail();
				}
			}
			else {
				Assert.fail();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	protected class TestHandler extends DefaultHandler implements LexicalHandler {

		// Private Data Members
		private int totalAttributes;
		private int totalComments;
		private int totalEndElements;

		private int totalHeadingText;
		private int totalInlineScripts;
		private int totalPlainText;
		private int totalSpanText;
		private int totalStartElements;

		@Override
		public void characters(char[] chars, int start, int length) throws SAXException {
			String text = new String(chars, start, length);

			if (text.equals("text inside span")) {
				totalSpanText++;
			}
			else if (text.equals("#{i18n['attachments']}")) {
				totalHeadingText++;
			}
			else if (text.equals("alert('hello world');") || (text.equals("console.log('hello world');"))) {
				totalInlineScripts++;
			}
			else {
				Assert.fail();
			}

			totalPlainText++;

			logger.debug("text->" + text + "<-");
		}

		public void comment(char[] ch, int start, int length) throws SAXException {
			String comment = new String(ch, start, length);
			Assert.assertEquals(" Test 5.4.2 Encoding PortletMode changes in Faces navigation ", comment);
			totalComments++;
			logger.debug("comment->" + comment + "<-");
		}

		public void endCDATA() throws SAXException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void endDocument() throws SAXException {
			logger.debug("endDocument");
		}

		public void endDTD() throws SAXException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			testURI(uri, localName);
			totalEndElements++;
			logger.debug("endElement uri=" + uri + " localName=" + localName + " qName=" + qName);
		}

		public void endEntity(String name) throws SAXException {
			throw new UnsupportedOperationException();
		}

		public void startCDATA() throws SAXException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void startDocument() throws SAXException {
			logger.debug("startDocument");
		}

		public void startDTD(String name, String publicId, String systemId) throws SAXException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {

			testURI(uri, localName);

			if (localName.equals("selectOneMenu")) {
				Assert.assertEquals(attributes.getLength(), 3);
				Assert.assertEquals("id", attributes.getLocalName(0));
				Assert.assertEquals("provinceId", attributes.getValue(0));
				Assert.assertEquals("required", attributes.getLocalName(1));
				Assert.assertEquals("true", attributes.getValue(1));
				Assert.assertEquals("value", attributes.getLocalName(2));
				Assert.assertEquals("#{applicantModelBean.provinceId}", attributes.getValue(2));
			}

			totalStartElements++;
			totalAttributes += attributes.getLength();

			logger.debug("startElement uri=" + uri + " localName=" + localName + " qName=" + qName);

			int totalAttributes = attributes.getLength();

			for (int i = 0; i < totalAttributes; i++) {
				logger.debug("   attribute uri=" + attributes.getURI(i) + " qName=" + attributes.getQName(i) +
					" localName=" + attributes.getLocalName(i) + " value=" + attributes.getValue(i));
			}
		}

		public void startEntity(String name) throws SAXException {
			throw new UnsupportedOperationException();
		}

		protected void testURI(String uri, String localName) {

			if (localName.equals("composition")) {
				Assert.assertEquals(uri, "http://xmlns.jcp.org/jsf/facelets");
			}
			else if (localName.equals("layout")) {
				Assert.assertEquals(uri, "http://liferay.com/faces/aui");
			}
			else if (localName.equals("clipboard")) {
				Assert.assertEquals(uri, "http://xmlns.jcp.org/jsf/composite/example-cc");
			}
			else if (localName.equals("form")) {
				Assert.assertEquals(uri, "http://xmlns.jcp.org/jsf/html");
			}
			else if (localName.equals("ajax")) {
				Assert.assertEquals(uri, "http://xmlns.jcp.org/jsf/core");
			}
			else if (localName.equals("inputFile")) {
				Assert.assertEquals(uri, "http://liferay.com/faces/bridge");
			}
			else if (localName.equals("hr")) {
				Assert.assertEquals(uri, "");
			}
		}

		public int getTotalAttributes() {
			return totalAttributes;
		}

		public int getTotalComments() {
			return totalComments;
		}

		public int getTotalEndElements() {
			return totalEndElements;
		}

		public int getTotalHeadingText() {
			return totalHeadingText;
		}

		public int getTotalInlineScripts() {
			return totalInlineScripts;
		}

		public int getTotalPlainText() {
			return totalPlainText;
		}

		public int getTotalSpanText() {
			return totalSpanText;
		}

		public int getTotalStartElements() {
			return totalStartElements;
		}
	}
}
