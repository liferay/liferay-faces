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
package com.liferay.faces.bridge.renderkit.portlet;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;

import com.liferay.faces.bridge.component.PortletRenderURL;


/**
 * This is the default renderer for the {@link PortletRenderURL} component. It conforms as closely as possible to the
 * requirements set forth in section PLT.26.3 of the JSR 286 Portlet Specification, Version 2.0.
 *
 * @author  Neil Griffin
 */
public class RenderURLRenderer extends PortletURLRenderer {

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		ExternalContext externalContext = facesContext.getExternalContext();
		MimeResponse mimeResponse = (MimeResponse) externalContext.getResponse();
		PortletURL renderURL = mimeResponse.createRenderURL();
		super.encodeEnd(facesContext, uiComponent, renderURL);
	}

}
