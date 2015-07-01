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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;


/**
 * This class is a thread-safe implementation of {@link XMLReader}. However, it does not fully implement all of the
 * functionality of the one provided by the JRE.
 *
 * @author  Neil Griffin
 */
public class XMLReaderImpl implements XMLReader {

	// Private Constants
	private static final String XMLNS = "xmlns";

	// Private Data Members
	private ContentHandler contentHandler;
	private LexicalHandler lexicalHandler;
	private DTDHandler dtdHandler;
	private EntityResolver entityResolver;
	private ErrorHandler errorHandler;
	private Map<String, Boolean> featureMap;
	private Map<String, Object> propertyMap;

	public XMLReaderImpl(Map<String, Boolean> featureMap) {
		this.featureMap = featureMap;
	}

	public void parse(InputSource inputSource) throws IOException, SAXException {

		Reader reader = inputSource.getCharacterStream();

		if (reader == null) {
			InputStream byteStream = inputSource.getByteStream();

			if (byteStream != null) {
				String encoding = inputSource.getEncoding();

				if (encoding != null) {
					reader = new InputStreamReader(byteStream, encoding);
				}
				else {
					reader = new InputStreamReader(byteStream);
				}
			}
		}

		if (reader != null) {

			// Note: Wrapping with BufferedReader provides a big speed improvement during parsing.
			reader = new BufferedReader(reader);
			contentHandler.startDocument();
			parse(reader);
			contentHandler.endDocument();
		}
	}

	public void parse(String systemId) throws IOException, SAXException {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is a finite state machine that has the ability to parse the XML contents of the specified {@link
	 * Reader} and invoke callbacks on the registered {@link ContentHandler}.
	 *
	 * @param   reader  The reader that contains the XML markup.
	 *
	 * @throws  IOException   If an error occurs during the read process.
	 * @throws  SAXException  If an error occurs in one of the {@link ContentHandler} callbacks.
	 */
	protected void parse(Reader reader) throws IOException, SAXException {

		// Initialize the finite state machine.
		AttributesImpl attributes = null;
		StringBuilder attributeName = null;
		StringBuilder attributeValue = null;
		StringBuilder comment = null;
		StringBuilder elementName = null;
		boolean openQuote = false;
		boolean parsingAttributeName = false;
		boolean parsingAttributeValue = false;
		boolean parsingComment = false;
		boolean parsingDeclaration = false;
		boolean parsingElementName = false;
		boolean parsingText = false;
		StringBuilder text = null;
		Map<String, String> uriMap = new HashMap<String, String>();

		char prevChar1 = (char) -1;
		char prevChar2 = (char) -1;
		char prevChar3 = (char) -1;

		// While there are more characters to be read:
		int characterAsInt = reader.read();

		while (characterAsInt != -1) {
			char curChar = (char) characterAsInt;

			boolean parsingContent = (parsingAttributeValue || parsingComment || parsingText);

			// If the current character the less-than symbol, then assume that this is the beginning of a new element.
			// i.e.: <span>
			if (curChar == '<') {

				// However, if parsing text, then it's time to finish parsing. i.e.: <span>some text</span>
				if (parsingText) {

					if (text.length() > 0) {
						String value = text.toString();
						contentHandler.characters(value.toCharArray(), 0, value.length());
					}

					parsingText = false;
				}

				parsingAttributeName = false;
				parsingElementName = true;
				elementName = new StringBuilder();
				attributes = new AttributesImpl();
			}

			// Otherwise, if the current character is the greater-than symbol, then
			else if (curChar == '>') {

				// If the previous character is a question-mark symbol, then this is the end of an XML declaration.
				// i.e.: <?xml version="1.0" encoding="UTF-8"?>
				if ((prevChar1 == '?') && parsingDeclaration) {
					parsingDeclaration = false;
				}

				// Otherwise, if the previous characters are "--" then we're at the end of a comment. i.e.: -->
				else if ((parsingComment) && (prevChar1 == '-') && (prevChar2 == '-')) {

					if (lexicalHandler != null) {
						String commentText = comment.toString();
						int pos = commentText.lastIndexOf("--");

						if (pos > 0) {
							commentText = commentText.substring(0, pos);
						}

						lexicalHandler.comment(commentText.toCharArray(), 0, commentText.length());
					}

					parsingComment = false;
				}

				// Otherwise,
				else {

					// Assume that this is the completion of an element name. i.e.: <form>
					String uri = "";
					String qName = elementName.toString();
					boolean startElement = true;
					boolean endElement = false;

					// Unless it is a closing element, i.e.: </form>
					if ((qName.length() > 0) && (qName.charAt(0) == '/')) {
						startElement = false;
						endElement = true;
						qName = qName.substring(1);
					}

					// Or unless it is a self-closing element, i.e.: <form />
					else if (prevChar1 == '/') {
						endElement = true;
					}

					String localName = qName;
					String prefix = null;
					int colonPos = localName.indexOf(':');

					// If there is a namespace prefix for the element name. i.e.: "h:" at the beginning of "h:form"
					if (colonPos > 0) {

						// Determine the URI associated with the namespace prefix.
						prefix = qName.substring(0, colonPos);
						uri = uriMap.get(prefix);

						if (uri == null) {
							uri = "";
						}

						localName = qName.substring(colonPos + 1);
					}

					// If appropriate, inform the content handler about the start of a new element, along with its
					// attributes.
					if (startElement) {
						contentHandler.startElement(uri, localName, qName, attributes);
						parsingElementName = false;
						parsingText = true;
						parsingAttributeName = false;
						parsingAttributeValue = false;
						text = new StringBuilder();
					}

					// If appropriate, inform the content handler about the end of an element.
					if (endElement) {
						contentHandler.endElement(uri, localName, qName);
					}
				}
			}

			// Otherwise, if the current character is a question-mark symbol, then determine if this is the start of
			// an XML declaration. i.e.: <?xml version="1.0" encoding="UTF-8"?>
			else if ((curChar == '?') && !parsingContent) {

				if (prevChar1 == '<') {
					parsingDeclaration = true;
					parsingElementName = false;
				}
			}

			// Otherwise, if the current character is some form of whitespace, then determine if this indicates that
			// all of the characters in the element name have been collected.
			else if (Character.isWhitespace(curChar) && !parsingContent) {

				if (parsingElementName) {
					parsingElementName = false;
					parsingAttributeName = true;
					attributeName = new StringBuilder();
					attributeValue = new StringBuilder();
				}
			}

			// Otherwise, if the current character is the equals symbol, then determine if this indicates that all of
			// the characters in an attribute name have been collected. i.e.: <h:form id="f1">
			else if ((curChar == '=') && !parsingContent) {

				if (parsingAttributeName) {
					parsingAttributeName = false;
					parsingAttributeValue = true;
				}
			}

			// Otherwise, if we've encountered "<!--" then that indicates the beginning of a comment.
			else if ((curChar == '-') && (prevChar1 == '-') && (prevChar2 == '!') && (prevChar3 == '<')) {
				parsingElementName = false;
				parsingComment = true;
				comment = new StringBuilder();
			}

			// Otherwise:
			else {

				// Assume that the current character should not be ignored.
				boolean ignore = false;

				// If the current character is the double-quote symbol:
				if (curChar == '"') {
					openQuote = !openQuote;

					// If this at the end of some quoted text, then that indicates that the attribute value is done
					// being collected. i.e.: <form id="f1"
					if (parsingAttributeValue) {
						ignore = true;

						if (!openQuote) {
							parsingAttributeValue = false;

							String uri = "";
							String qName = attributeName.toString().trim();
							String localName = qName;
							String prefix = null;
							int colonPos = localName.indexOf(':');

							if (colonPos > 0) {
								prefix = qName.substring(0, colonPos);
								uri = uriMap.get(prefix);

								if (uri == null) {
									uri = "";
								}

								localName = qName.substring(colonPos + 1);
							}

							String type = AttributesImpl.TYPE_ENTITY;

							// Substitute all occurrences of "&amp;" with "&" (which is what the JRE parser does).
							StringBuilder buf = null;
							int ampersandPos = attributeValue.indexOf("&amp;");

							if (ampersandPos > 0) {

								int startPos = 0;
								buf = new StringBuilder();

								while (ampersandPos > 0) {

									buf.append(attributeValue.substring(startPos, ampersandPos));
									buf.append("&");

									startPos = ampersandPos + "&amp;".length();

									ampersandPos = attributeValue.indexOf("&amp;", startPos);
								}

								if (startPos < attributeValue.length()) {
									buf.append(attributeValue.substring(startPos));
								}
							}
							else {
								buf = attributeValue;
							}

							String value = buf.toString();
							attributes.add(uri, localName, qName, type, value);

							if (qName.toLowerCase().startsWith(XMLNS)) {

								if (localName.equals(XMLNS)) {
									localName = "";
								}

								uriMap.put(localName, value);
							}

							parsingAttributeName = true;
							attributeName = new StringBuilder();
							attributeValue = new StringBuilder();
						}
					}
				}

				// Otherwise, if the current character is some form of whitespace that is not a space character, then
				// determine whether or not it should be ignored.
				else if ((curChar != ' ') && Character.isWhitespace(curChar)) {
					ignore = true;
				}

				// If the current character is not to be ignored, then append the character to either the attribute
				// name, attribute value, comment, element name, or plain text, according to the current state of this
				// finite state machine.
				if (!ignore) {

					if (parsingAttributeName) {
						attributeName.append(curChar);
					}
					else if (parsingAttributeValue) {
						attributeValue.append(curChar);
					}
					else if (parsingComment) {
						comment.append(curChar);
					}
					else if (parsingElementName) {
						elementName.append(curChar);
					}
					else if (parsingText) {
						text.append(curChar);
					}
				}
			}

			prevChar3 = prevChar2;
			prevChar2 = prevChar1;
			prevChar1 = curChar;
			characterAsInt = reader.read();
		}
	}

	public ContentHandler getContentHandler() {
		return contentHandler;
	}

	public void setContentHandler(ContentHandler contentHandler) {
		this.contentHandler = contentHandler;

		if ((contentHandler != null) && (contentHandler instanceof LexicalHandler)) {
			this.lexicalHandler = (LexicalHandler) contentHandler;
		}
	}

	public DTDHandler getDTDHandler() {
		return dtdHandler;
	}

	public void setDTDHandler(DTDHandler dtdHandler) {
		this.dtdHandler = dtdHandler;
	}

	public EntityResolver getEntityResolver() {
		return entityResolver;
	}

	public void setEntityResolver(EntityResolver entityResolver) {
		this.entityResolver = entityResolver;
	}

	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
		return featureMap.get(name);
	}

	public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
		featureMap.put(name, value);
	}

	public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
		return propertyMap.get(name);
	}

	public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
		propertyMap.put(name, value);
	}

	protected Map<String, Object> getPropertyMap() {

		if (propertyMap == null) {
			propertyMap = new HashMap<String, Object>();
		}

		return propertyMap;
	}

}
