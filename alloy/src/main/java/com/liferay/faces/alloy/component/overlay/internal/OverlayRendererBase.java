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
package com.liferay.faces.alloy.component.overlay.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.render.internal.DelegatingAlloyRendererBase;
import com.liferay.faces.util.render.internal.DelegationResponseWriter;
import com.liferay.faces.util.render.internal.IdDelegationResponseWriter;


/**
 * @author  Vernon Singleton
 */
public abstract class OverlayRendererBase extends DelegatingAlloyRendererBase implements NamingContainer {

	// Protected Constants
	protected static final String Z_INDEX = "zIndex";
	protected static final String CONTENT_BOX_SUFFIX = "_contentBox";
	protected static final String OVERLAY_BODY_SUFFIX = "_overlayBody";

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// NOTE: This is currently only used by Dialog and Popover. Tooltip overrides this method.

		// Encode the opening boundingBox <div> tag via delegation. Ensure that the "id" attribute is always written so
		// that Alloy's JavaScript will be able to locate the boundingBox in the DOM.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String clientId = uiComponent.getClientId(facesContext);
		DelegationResponseWriter idDelegationResponseWriter = new IdDelegationResponseWriter(responseWriter, "div",
				clientId);
		super.encodeMarkupBegin(facesContext, uiComponent, idDelegationResponseWriter);

		// Encode the opening contentBox <div> tag with a unique id.
		String contentBoxClientId = clientId.concat(CONTENT_BOX_SUFFIX);
		responseWriter.startElement("div", null);
		responseWriter.writeAttribute("id", contentBoxClientId, null);

		// Encode the opening overlayBody <div> tag with a unique id.
		String overlayBodyClientId = clientId.concat(OVERLAY_BODY_SUFFIX);
		responseWriter.startElement("div", null);
		responseWriter.writeAttribute("id", overlayBodyClientId, null);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// NOTE: This is currently only used by Dialog and Popover. Tooltip overrides this method.

		// Encode the closing overlayBody </div> tag.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");

		// Encode the closing contentBox </div> tag.
		responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");

		// Encode the closing boundingBox </div> tag via delegation.
		super.encodeMarkupEnd(facesContext, uiComponent);
	}

	public void encodeOverlayJavaScriptCustom(ResponseWriter responseWriter, FacesContext facesContext,
		UIComponent overlay) throws IOException {

		// The outermost <div> (which is the boundingBox) was initially styled with "display:none;" in order to prevent
		// blinking when Alloy's JavaScript attempts to hide the boundingBox. At this point in JavaScript execution,
		// Alloy is done manipulating the DOM and it is necessary to remove the "display:none;" so that the
		// dialog will popup correctly.
		responseWriter.write("A.one('#");

		String clientId = overlay.getClientId(facesContext);
		String escapedBoundingBoxClientId = escapeClientId(clientId);
		responseWriter.write(escapedBoundingBoxClientId);
		responseWriter.write("').setStyle('display',null);");
	}

	protected void encodeOverlayDismissible(ResponseWriter responseWriter, UIComponent overlay, String clientKey)
		throws IOException {

		responseWriter.write("var ");
		responseWriter.write(clientKey);
		responseWriter.write("_switched=false;");
		responseWriter.write("Liferay.component('");
		responseWriter.write(clientKey);
		responseWriter.write("').get('boundingBox').on('clickoutside',function(event){if(");
		responseWriter.write(clientKey);
		responseWriter.write("_switched){");
		responseWriter.write(clientKey);
		responseWriter.write("_switched=false;}else{if(Liferay.component('");
		responseWriter.write(clientKey);
		responseWriter.write("').get('visible')){Liferay.component('");
		responseWriter.write(clientKey);
		responseWriter.write("').hide();}}});");
		responseWriter.write("A.Do.after(function(stuff){if(Liferay.component('");
		responseWriter.write(clientKey);
		responseWriter.write("').get('visible')){");
		responseWriter.write(clientKey);
		responseWriter.write("_switched=true;}},Liferay.component('");
		responseWriter.write(clientKey);
		responseWriter.write("'),'toggle');");
		responseWriter.write("A.Do.after(function(stuff){if(Liferay.component('");
		responseWriter.write(clientKey);
		responseWriter.write("').get('visible')){");
		responseWriter.write(clientKey);
		responseWriter.write("_switched=true;}},Liferay.component('");
		responseWriter.write(clientKey);
		responseWriter.write("'),'show');");
	}

	protected void encodeOverlayHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter,
		UIComponent overlay, boolean first) throws IOException {

		// Encode the "contentBox" Alloy hidden attribute.
		String clientId = overlay.getClientId(facesContext);
		encodeClientId(responseWriter, BOUNDING_BOX, clientId, first);

		String contentBoxClientId = clientId.concat(CONTENT_BOX_SUFFIX);
		encodeClientId(responseWriter, CONTENT_BOX, contentBoxClientId, first);

		first = false;

		// Encode the "headerContent" Alloy hidden attribute.
		Map<String, Object> attributes = overlay.getAttributes();
		String headerText = (String) attributes.get("headerText");

		if (headerText != null) {
			headerText = "<span class=\"alloy-overlay-title\">" + headerText + "</span>";
			encodeString(responseWriter, "headerContent", headerText, first);
		}

		encodeString(responseWriter, "bodyContent", "", first);

		// Encode the "render: true" Alloy hidden attribute.
		encodeWidgetRender(responseWriter, first);

		// Encode the "visible" Alloy hidden attribute.
		Boolean autoShow = (Boolean) attributes.get("autoShow");

		if (autoShow != null) {
			encodeBoolean(responseWriter, "visible", autoShow, first);
		}
	}

	protected void encodeOverlayZIndex(ResponseWriter responseWriter, UIComponent overlay, Integer zIndex,
		String defaultZIndex, boolean first) throws IOException {

		if (zIndex.equals(Integer.MIN_VALUE)) {
			encodeNonEscapedObject(responseWriter, Z_INDEX, defaultZIndex, first);
		}
		else {
			encodeInteger(responseWriter, Z_INDEX, zIndex, first);
		}
	}
}
