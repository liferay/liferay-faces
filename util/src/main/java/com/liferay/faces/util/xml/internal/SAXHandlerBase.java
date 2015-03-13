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

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public abstract class SAXHandlerBase extends DefaultHandler {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SAXHandlerBase.class);

	// Protected Data Members
	protected StringBuilder content;

	// Private Data Members
	private boolean resolveEntities;
	private URL url;

	public SAXHandlerBase(boolean resolveEntities) {
		super();
		this.resolveEntities = resolveEntities;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		if ((content != null) && (ch != null) && (length > 0)) {
			content.append(ch, start, length);
		}
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {

		InputSource inputSource = new InputSource(new StringReader(""));

		if (resolveEntities) {
			inputSource = super.resolveEntity(publicId, systemId);

			if (inputSource == null) {

				try {

					// Note: Not sure why, but following line of code has suffered terrible performance problems.
					// At times, it could take over a minute for the stream to open. This is why the web.xml
					// default for resolving entities is false.
					inputSource = new InputSource(new URL(systemId).openStream());
				}
				catch (IOException ioException) {

					// Don't bother logging this as a warning or an error, because we can't assume connectivity to
					// the Internet to download a public URL.
					logger.trace("Unable to download publicId=[{0}], systemId=[{1}], referenced-in=[{2}]",
						new Object[] { publicId, systemId, url });
				}
			}
		}

		return inputSource;
	}
}
