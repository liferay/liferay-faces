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
package com.liferay.faces.alloy.component.body.internal;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.render.internal.AlloyRendererUtil;
import com.liferay.faces.util.ContentTypes;
import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * @author  Neil Griffin
 */
public class BodyResponseWriter extends DelegationResponseWriterBase {

	// Private Data Members
	private boolean ajaxRequest;
	private UIComponent uiComponent;
	private BrowserSniffer browserSniffer;

	public BodyResponseWriter(ResponseWriter responseWriter, BrowserSniffer browserSniffer, boolean ajaxRequest) {
		super(responseWriter);
		this.browserSniffer = browserSniffer;
		this.ajaxRequest = ajaxRequest;
	}

	@Override
	public void endElement(String name) throws IOException {

		if (StringPool.BODY.equals(name) && !ajaxRequest) {

			super.startElement(StringPool.SCRIPT, uiComponent);
			super.writeAttribute(StringPool.TYPE, ContentTypes.TEXT_JAVASCRIPT, null);

			FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
			List<Script> scripts = facesRequestContext.getScripts();
			AlloyRendererUtil.writeScripts(this, scripts, browserSniffer);
			super.endElement(StringPool.SCRIPT);
		}

		super.endElement(name);
	}

	@Override
	public void startElement(String name, UIComponent uiComponent) throws IOException {

		this.uiComponent = uiComponent;
		super.startElement(name, uiComponent);
	}
}
