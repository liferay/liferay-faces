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
package com.liferay.faces.bridge.renderkit.html_basic;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

import com.liferay.faces.bridge.util.FacesURLEncoder;
import com.liferay.faces.util.lang.StringPool;


/**
 * The purpose of this class is to bypass the Mojarra/MyFaces encoding of the URL found in the "src" attribute of a
 * &lt;script&gt; element or the "href" attribute of a &lt;link&gt; element. For more info, see: <a
 * href="http://issues.liferay.com/browse/FACES-1236">FACES-1236</a>.
 *
 * @author  Neil Griffin
 */
public class ResponseWriterResourceImpl extends ResponseWriterWrapper {

	// Private Constants
	private static final String REGEX_AMPERSAND = "[&]amp;";

	// Private Data Members
	private ResponseWriter wrappedResponseWriter;

	public ResponseWriterResourceImpl(ResponseWriter responseWriter) {
		this.wrappedResponseWriter = responseWriter;
	}

	@Override
	public void endElement(String name) throws IOException {
		write(StringPool.GREATER_THAN);
		write(StringPool.LESS_THAN);
		write(StringPool.FORWARD_SLASH);
		write(name);
		write(StringPool.GREATER_THAN);
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {
		write(StringPool.LESS_THAN);
		write(name);
	}

	@Override
	public void write(String str) throws IOException {
		super.write(str);
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {
		write(StringPool.SPACE);
		write(name);
		write(StringPool.EQUAL);
		write(StringPool.QUOTE);
		write((String) value);
		write(StringPool.QUOTE);
	}

	@Override
	public void writeURIAttribute(String name, Object value, String property) throws IOException {

		if ((value != null) && (value instanceof String)) {
			String encoding = wrappedResponseWriter.getCharacterEncoding();
			String encodedURI = FacesURLEncoder.encode((String) value, encoding);

			if (encodedURI != null) {

				// Remove all the encoded ampersands. See: http://issues.liferay.com/browse/FACES-1236
				encodedURI = encodedURI.replaceAll(REGEX_AMPERSAND, StringPool.AMPERSAND);
			}

			writeAttribute(name, encodedURI, property);
		}
		else {
			writeAttribute(name, value, property);
		}
	}

	@Override
	public ResponseWriter getWrapped() {
		return wrappedResponseWriter;
	}
}
