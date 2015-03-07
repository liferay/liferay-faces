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
import java.util.Map;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;


/**
 * @author  Neil Griffin
 */
public class PrimeFacesInlineScript extends UIComponentBase {

	// Private Data Members
	private String script;

	public PrimeFacesInlineScript() {
		// Zero-arg constructor is necessary to support restoration of saved state by the state manager.
	}

	public PrimeFacesInlineScript(String script) {
		this.script = script;

		Map<String, Object> attributes = getAttributes();
		attributes.put("name", "primefacesinlinescript" + script.hashCode());
		attributes.put("library", "primefaces");
	}

	@Override
	public void encodeBegin(FacesContext facesContext) throws IOException {
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("script", this);
		responseWriter.writeAttribute("type", "text/javascript", null);
		responseWriter.writeText(script, null);
	}

	@Override
	public void encodeEnd(FacesContext facesContext) throws IOException {
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("script");
	}

	@Override
	public String getFamily() {
		return "facelets.LiteralText";
	}
}
