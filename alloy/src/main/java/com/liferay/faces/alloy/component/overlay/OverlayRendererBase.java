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

import javax.faces.application.ProjectStage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.dialog.Dialog;
import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.alloy.renderkit.DelegatingAlloyRendererBase;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.DelegationResponseWriter;
import com.liferay.faces.util.render.IdDelegationResponseWriter;


/**
 * @author  Neil Griffin
 */
public abstract class OverlayRendererBase extends DelegatingAlloyRendererBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OverlayRendererBase.class);

	@Override
	public abstract void encodeAlloyAttributes(ResponseWriter respoonseWriter, UIComponent uiComponent)
		throws IOException;

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		// If the developer associated the dialog with a trigger component via the "for" attribute, then
		Overlay overlay = (Overlay) uiComponent;
		String forClientId = getForClientId(facesContext, overlay);

		if (forClientId == null) {

			if ((isForRequired()) && facesContext.isProjectStage(ProjectStage.Development)) {
				logger.error("The 'for' attribute is required for " + overlay.getClass().getSimpleName());
			}
		}
		else {

			// Write out an "onclick" event that will cause the trigger to popup the dialog.
			responseWriter.write(AlloyRendererUtil.A_DOT_ONE);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.APOSTROPHE);

			String escapedForClientId = StringPool.POUND + ComponentUtil.escapeClientId(forClientId);
			responseWriter.write(escapedForClientId);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.PERIOD);
			responseWriter.write(StringPool.ON);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(StringPool.CLICK);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(StringPool.COMMA);
			responseWriter.write(AlloyRendererUtil.FUNCTION_EVENT);
			responseWriter.write(StringPool.OPEN_CURLY_BRACE);

			boolean modal = overlay.isModal();

			if (modal) {

				// Popping up a modal dialog scrolls the parent window. Write out some JavaScript that will remember the
				// location of the parent window prior to popping-up the dialog.
				responseWriter.write("var scrollx=window.scrollX;var scrolly=window.scrollY;");
			}

			// If autoShow=false, then out some JavaScript that will cause the dialog to popup when the trigger is
			// clicked.
			if (!overlay.isAutoShow()) {
				String clientVarName = ComponentUtil.getClientVarName(facesContext, overlay);
				String clientKey = overlay.getClientKey();

				if (clientKey == null) {
					clientKey = clientVarName;
				}

				responseWriter.write(AlloyRendererUtil.LIFERAY_COMPONENT);
				responseWriter.write(StringPool.OPEN_PARENTHESIS);
				responseWriter.write(StringPool.APOSTROPHE);
				responseWriter.write(clientKey);
				responseWriter.write(StringPool.APOSTROPHE);
				responseWriter.write(StringPool.CLOSE_PARENTHESIS);
				responseWriter.write(AlloyRendererUtil.DOT_SHOW);
				responseWriter.write(StringPool.SEMICOLON);
			}

			if (modal) {

				// Write out some JavaScript that will reposition the parent window back to where it was.
				responseWriter.write("window.scrollTo(scrollx,scrolly);");
			}

			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);
		}

		// The outermost <div> (which is the contentBox) was initially styled with "display:none;" in order to prevent
		// blinking when Alloy's JavaScript attempts to hide the contentBox. At this point in JavaScript execution,
		// Alloy is done manipulating the DOM and it is necessary to set the style back to "display:block;" so that the
		// dialog will popup correctly.
		responseWriter.write(AlloyRendererUtil.A_DOT_ONE);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);

		String clientId = overlay.getClientId(facesContext);
		String escapedContentBoxId = StringPool.POUND + ComponentUtil.escapeClientId(clientId);
		responseWriter.write(escapedContentBoxId);
		responseWriter.write("')._node['style'].display='block';");
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Ensure that the "id" attribute is always written on the outermost <div> (which is the contentBox) so that
		// Alloy's JavaScript will be able to locate the contentBox in the DOM.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		DelegationResponseWriter idDelegationResponseWriter = new IdDelegationResponseWriter(responseWriter,
				StringPool.DIV, uiComponent.getClientId());

		super.encodeMarkupBegin(facesContext, uiComponent, idDelegationResponseWriter);
	}

	protected void encodeOverlayHiddenAttributes(ResponseWriter responseWriter, Overlay overlay, boolean first)
		throws IOException {

		// Encode the "contentBox" Alloy attribute.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String clientId = overlay.getClientId(facesContext);
		String escapedContentBoxId = StringPool.POUND + ComponentUtil.escapeClientId(clientId);
		encodeNonEscapedString(responseWriter, AlloyRendererUtil.CONTENT_BOX, escapedContentBoxId, first);

		// Encode the "render: true" Alloy attribute.
		encodeWidgetRender(responseWriter, first);
	}

	protected void encodeOverlayZIndex(ResponseWriter responseWriter, Overlay overlay, Integer zIndex, boolean first)
		throws IOException {

		if (zIndex == Integer.MIN_VALUE) {
			encodeNonEscapedObject(responseWriter, Dialog.Z_INDEX, AlloyRendererUtil.LIFERAY_Z_INDEX_OVERLAY, first);
		}
		else {
			encodeInteger(responseWriter, Dialog.Z_INDEX, zIndex, first);
		}
	}

	@Override
	public abstract String getAlloyClassName();

	protected abstract boolean isForRequired();

	@Override
	public abstract String getDelegateComponentFamily();

	@Override
	public abstract String getDelegateRendererType();

	protected String getForClientId(FacesContext facesContext, Overlay overlay) {

		String forClientId = null;
		String forComponentId = overlay.getFor();

		if (forComponentId != null) {
			UIComponent forComponent = overlay.findComponent(forComponentId);
			forClientId = forComponent.getClientId(facesContext);
		}

		return forClientId;
	}
}
