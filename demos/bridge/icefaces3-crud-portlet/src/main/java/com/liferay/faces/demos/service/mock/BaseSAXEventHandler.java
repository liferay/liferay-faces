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
package com.liferay.faces.demos.service.mock;

import java.io.IOException;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author  Neil Griffin
 */
public class BaseSAXEventHandler extends DefaultHandler {

	private StringBuilder content;

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		if ((content != null) && (ch != null) && (length > 0)) {
			content.append(ch, start, length);
		}
	}

	/**
	 * This method is overridden so that the SAX event handler will not download DTDs and XML Schemas. It has the effect
	 * of a small performance improvement.
	 */
	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
		InputSource inputSource = new InputSource(new StringReader(""));

		return inputSource;
	}

	public StringBuilder getContent() {
		return content;
	}

	public void setContent(StringBuilder content) {
		this.content = content;
	}

}
