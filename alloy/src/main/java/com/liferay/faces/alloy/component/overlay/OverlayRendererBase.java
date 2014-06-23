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
package com.liferay.faces.alloy.component.overlay;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.alloy.renderkit.DelegatingAlloyRendererBase;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.DelegationResponseWriter;
import com.liferay.faces.util.render.IdDelegationResponseWriter;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Vernon Singleton
 */
public abstract class OverlayRendererBase extends DelegatingAlloyRendererBase {

	// Protected Constants
	protected static final String Z_INDEX = "zIndex";

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Ensure that the "id" attribute is always written on the outermost <div> (which is the contentBox) so that
		// Alloy's JavaScript will be able to locate the contentBox in the DOM.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		DelegationResponseWriter idDelegationResponseWriter = new IdDelegationResponseWriter(responseWriter,
				StringPool.DIV, uiComponent.getClientId(facesContext));

		super.encodeMarkupBegin(facesContext, uiComponent, idDelegationResponseWriter);
	}

	public void encodeOverlayJavaScriptCustom(ResponseWriter responseWriter, FacesContext facesContext, Overlay overlay)
		throws IOException {

		// The outermost <div> (which is the contentBox) was initially styled with "display:none;" in order to prevent
		// blinking when Alloy's JavaScript attempts to hide the contentBox. At this point in JavaScript execution,
		// Alloy is done manipulating the DOM and it is necessary to set the style back to "display:block;" so that the
		// dialog will popup correctly.
		responseWriter.write(AlloyRendererUtil.A_DOT_ONE);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);

		String clientId = overlay.getClientId(facesContext);
		String escapedContentBoxId = StringPool.POUND + RendererUtil.escapeClientId(clientId);
		responseWriter.write(escapedContentBoxId);
		responseWriter.write("')._node['style'].display='block';");
	}

	protected void encodeOverlayDismissable(ResponseWriter responseWriter, Overlay overlay, String clientKey)
		throws IOException {

		if (!overlay.isModal() && overlay.isDismissable()) {
			responseWriter.write("var " + clientKey + "_switched = false;");
			responseWriter.write("Liferay.component('" + clientKey +
				"').get('boundingBox').on('clickoutside', function(event) { if (" + clientKey + "_switched) { " +
				clientKey + "_switched = false; } else { if (Liferay.component('" + clientKey +
				"').get('visible')) { Liferay.component('" + clientKey + "').hide(); } } });");
			responseWriter.write("A.Do.after(function(stuff) { if (Liferay.component('" + clientKey +
				"').get('visible')) { " + clientKey + "_switched = true; } }, Liferay.component('" + clientKey +
				"'), 'toggle');");
			responseWriter.write("A.Do.after(function(stuff) { if (Liferay.component('" + clientKey +
				"').get('visible')) { " + clientKey + "_switched = true; } }, Liferay.component('" + clientKey +
				"'), 'show');");
		}

	}

	protected void encodeOverlayHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter,
		Overlay overlay, boolean first) throws IOException {

		// Encode the "contentBox" Alloy attribute.
		String clientId = overlay.getClientId(facesContext);
		encodeClientId(responseWriter, AlloyRendererUtil.CONTENT_BOX, clientId, first);

		first = false;

		// headerContent
		String headerText = overlay.getHeaderText();

		if (headerText != null) {
			encodeString(responseWriter, AlloyRendererUtil.HEADER_CONTENT, headerText, first);
		}

		// Encode the "render: true" Alloy attribute.
		encodeWidgetRender(responseWriter, first);

		// visible
		Boolean autoShow = overlay.isAutoShow();

		if (autoShow != null) {
			encodeBoolean(responseWriter, AlloyRendererUtil.VISIBLE, autoShow, first);
		}
	}

	protected void encodeOverlayZIndex(ResponseWriter responseWriter, Overlay overlay, String zIndex, boolean first)
		throws IOException {

		if (zIndex.equals(AlloyRendererUtil.LIFERAY_Z_INDEX_OVERLAY)) {
			encodeNonEscapedObject(responseWriter, Z_INDEX, zIndex, first);
		}
		else {
			encodeString(responseWriter, Z_INDEX, zIndex, first);
		}
	}

}
