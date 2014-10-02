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
package com.liferay.faces.alloy.component.body.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.client.ClientScriptFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.internal.ContentTypes;
import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * @author  Neil Griffin
 */
public class BodyResponseWriter extends DelegationResponseWriterBase {

	// Private Data Members
	private UIComponent uiComponent;

	public BodyResponseWriter(ResponseWriter responseWriter, boolean browserIE, float browserMajorVersion) {
		super(responseWriter);
	}

	@Override
	public void endElement(String name) throws IOException {

		if (StringPool.BODY.equals(name)) {
			super.startElement(StringPool.SCRIPT, uiComponent);
			super.writeAttribute(StringPool.TYPE, ContentTypes.TEXT_JAVASCRIPT, null);

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			ClientScriptFactory clientScriptFactory = (ClientScriptFactory) FactoryExtensionFinder.getFactory(
					ClientScriptFactory.class);
			ClientScript clientScript = clientScriptFactory.getClientScript(externalContext);
			super.write(clientScript.toString());
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
