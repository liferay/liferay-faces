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
package com.liferay.faces.bridge.renderkit.primefaces.internal;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;


/**
 * This class is part of a workaround for FACES-2061 and is responsible for capturing inline scripts that are encoded by
 * the PrimeFaces HeadRenderer.
 *
 * @author  Neil Griffin
 */
public class PrimeFacesHeadResponseWriter extends ResponseWriter {

	// Private Data Members
	private List<String> externalResourceURLs;
	private boolean inlineScript;
	private StringWriter stringWriter;
	private boolean writingScript;
	private boolean writingCss;
	private boolean writingLink;
	private String potentialExternalCssURL;

	public PrimeFacesHeadResponseWriter() {

		this.externalResourceURLs = new ArrayList<String>();
		this.inlineScript = true;
		this.stringWriter = new StringWriter();
	}

	@Override
	public ResponseWriter cloneWithWriter(Writer writer) {
		return null;
	}

	@Override
	public void close() throws IOException {
		stringWriter.close();
	}

	@Override
	public void endDocument() throws IOException {
	}

	@Override
	public void endElement(String name) throws IOException {

		if ("script".equals(name)) {

			writingScript = false;
			inlineScript = true;
		}
		else if ("link".equals(name)) {

			potentialExternalCssURL = null;
			writingCss = false;
			writingLink = false;
		}
	}

	@Override
	public void flush() throws IOException {
		stringWriter.flush();
	}

	@Override
	public void startDocument() throws IOException {
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		if ("script".equals(name)) {
			writingScript = true;
		}
		else if ("link".equals(name)) {
			writingLink = true;
		}
	}

	@Override
	public String toString() {
		return stringWriter.toString();
	}

	@Override
	public void write(char[] text, int off, int len) throws IOException {

		if (writingScript && inlineScript) {
			stringWriter.write(text, off, len);
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (writingScript && "src".equals(name)) {

			inlineScript = false;

			if (value != null) {

				String externalScriptURL = value.toString();
				externalResourceURLs.add(externalScriptURL);
			}
		}
		else if (writingLink && "type".equals(name) && "text/css".equals(value)) {

			// If a resource URL has been saved while writing this link, then it is a CSS resource URL.
			if (potentialExternalCssURL != null) {
				externalResourceURLs.add(potentialExternalCssURL);
			}
			else {
				writingCss = true;
			}
		}
		else if (writingLink && "href".equals(name) && (value != null)) {

			String externalResourceURL = value.toString();

			if (writingCss) {
				externalResourceURLs.add(externalResourceURL);
			}

			// Otherwise, the resource may or may not be a CSS resource URL, so save it until it's type can be
			// determined.
			else {
				potentialExternalCssURL = externalResourceURL;
			}
		}
	}

	@Override
	public void writeComment(Object comment) throws IOException {

		if (writingScript && inlineScript && (comment != null)) {
			stringWriter.write(comment.toString());
		}
	}

	@Override
	public void writeText(Object text, String property) throws IOException {

		if (writingScript && inlineScript && (text != null)) {
			stringWriter.write(text.toString());
		}
	}

	@Override
	public void writeText(char[] text, int off, int len) throws IOException {

		if (writingScript && inlineScript && (text != null)) {
			stringWriter.write(text, off, len);
		}
	}

	@Override
	public void writeURIAttribute(String name, Object value, String property) throws IOException {
		writeAttribute(name, value, property);
	}

	@Override
	public String getCharacterEncoding() {
		return null;
	}

	@Override
	public String getContentType() {
		return null;
	}

	public List<String> getExternalResourceURLs() {
		return externalResourceURLs;
	}
}
