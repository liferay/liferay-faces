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
package com.liferay.faces.bridge.renderkit.html_basic;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;
import javax.faces.render.Renderer;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.util.FacesURLEncoder;


/**
 * This class wraps the ScriptRenderer provided by the Mojarra/MyFaces implementation.
 *
 * @author  Neil Griffin
 */
public class ScriptRendererBridgeImpl extends RendererWrapper {

	// Private Constants
	private static final String ATTR_SRC = "src";

	// Private Data Members
	private Renderer wrappedRenderer;

	public ScriptRendererBridgeImpl(Renderer renderer) {
		this.wrappedRenderer = renderer;
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// If the BodyRenderer has indicated that the h:body component is being rendered, then
		if (facesContext.getAttributes().get(BodyRenderer.ATTR_RENDERING_BODY) != null) {

			// Ask the wrapped renderer to encode the script to a custom ResponseWriter.
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			facesContext.setResponseWriter(new ScriptResponseWriter(responseWriter));
			super.encodeEnd(facesContext, uiComponent);
			facesContext.setResponseWriter(responseWriter);
		}

		// Otherwise, since the h:head component is being rendered, simply delegate to the wrapped renderer.
		else {
			super.encodeEnd(facesContext, uiComponent);
		}
	}

	@Override
	public Renderer getWrapped() {
		return wrappedRenderer;
	}

	/**
	 * The purpose of this inner-class is to bypass the Mojarra/MyFaces encoding of the URL found in the "src" attribute
	 * of the script. See: http://issues.liferay.com/browse/FACES-1236
	 *
	 * @author  Neil Griffin
	 */
	protected class ScriptResponseWriter extends ResponseWriterWrapper {

		// Private Data Members
		private ResponseWriter wrappedResponseWriter;

		public ScriptResponseWriter(ResponseWriter responseWriter) {
			this.wrappedResponseWriter = responseWriter;
		}

		@Override
		public void endElement(String name) throws IOException {
			write(BridgeConstants.CHAR_GREATER_THAN);
			write(BridgeConstants.CHAR_LESS_THAN);
			write(BridgeConstants.CHAR_FORWARD_SLASH);
			write(name);
			write(BridgeConstants.CHAR_GREATER_THAN);
		}

		@Override
		public void startElement(String name, UIComponent component) throws IOException {
			write(BridgeConstants.CHAR_LESS_THAN);
			write(name);
		}

		@Override
		public void write(String str) throws IOException {
			super.write(str);
		}

		@Override
		public void writeAttribute(String name, Object value, String property) throws IOException {
			write(BridgeConstants.CHAR_SPACE);
			write(name);
			write(BridgeConstants.CHAR_EQUALS);
			write(BridgeConstants.CHAR_DOUBLE_QUOTE);
			write((String) value);
			write(BridgeConstants.CHAR_DOUBLE_QUOTE);
		}

		@Override
		public void writeURIAttribute(String name, Object value, String property) throws IOException {

			if ((value != null) && (value instanceof String) && ATTR_SRC.equals(name)) {
				String encoding = wrappedResponseWriter.getCharacterEncoding();
				String encodedURI = FacesURLEncoder.encode((String) value, encoding);

				if (encodedURI != null) {

					// Remove all the encoded ampersands. See: http://issues.liferay.com/browse/FACES-1236
					encodedURI = encodedURI.replaceAll("[&]amp;", BridgeConstants.CHAR_AMPERSAND);
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
}
