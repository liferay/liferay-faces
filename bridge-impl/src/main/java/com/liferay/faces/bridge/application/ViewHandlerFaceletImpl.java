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
package com.liferay.faces.bridge.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.portlet.MimeResponse;

import com.sun.facelets.FaceletViewHandler;


/**
 * This class is a portlet-compatible implementation of the Facelets {@link ViewHandler}.
 *
 * @author  Neil Griffin
 */
public class ViewHandlerFaceletImpl extends FaceletViewHandler {

	// Private Constants
	private static final String FACELETS_CONTENT_TYPE = "facelets.ContentType";
	private static final String FACELETS_ENCODING = "facelets.Encoding";

	public ViewHandlerFaceletImpl(ViewHandler parent) {
		super(parent);
	}

	@Override
	protected ResponseWriter createResponseWriter(FacesContext facesContext) throws IOException, FacesException {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		String contentType = (String) requestMap.get(FACELETS_CONTENT_TYPE);

		if (contentType == null) {
			contentType = externalContext.getResponseContentType();
		}

		String encoding = (String) requestMap.get(FACELETS_ENCODING);

		if (encoding == null) {
			encoding = externalContext.getResponseCharacterEncoding();
		}

		MimeResponse mimeResponse = (MimeResponse) externalContext.getResponse();
		PrintWriter printWriter = mimeResponse.getWriter();
		RenderKit renderKit = facesContext.getRenderKit();
		ResponseWriter responseWriter = renderKit.createResponseWriter(printWriter, contentType, encoding);

		return responseWriter;
	}

}
