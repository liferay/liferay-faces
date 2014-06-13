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
package com.liferay.faces.alloy.component.dialog;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = Dialog.COMPONENT_FAMILY, rendererType = Dialog.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(
			library = "liferay-faces-alloy", name = "build/aui/aui-min.js"
		), @ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
public class DialogRenderer extends DialogRendererBase {

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		Dialog dialog = (Dialog) uiComponent;
		String clientVarName = ComponentUtil.getClientVarName(facesContext, dialog);

		if (dialog.getFor() != null) {
			responseWriter.write("A.one('" + dialog.getForClientIdEscaped(facesContext) + "')");
			responseWriter.write(StringPool.PERIOD);
			responseWriter.write("on");
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write("click");
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(StringPool.COMMA);
			responseWriter.write("function(event) {");

			// rendering the dialog for the user scrolls the parent window for some unknown reason,
			// so reposition the parent window back to where it was
			responseWriter.write("var scrollx = window.scrollX;");
			responseWriter.write("var scrolly = window.scrollY;");
			responseWriter.write(clientVarName + ".show();");
			responseWriter.write("window.scrollTo(scrollx,scrolly);");
			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);
		}

		// alloy's divs should be in place and hidden by now.
		// so we need to unhide the div we have hidden until now.
		// any chance for blinking should be over by now.
		responseWriter.write("A.one('" + StringPool.POUND +
			ComponentUtil.escapeClientId(dialog.getClientId(facesContext)) + "')._node['style'].display = 'block';");

	}

	@Override
	public void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		Dialog modal = (Dialog) uiComponent;
		String clientVarName = ComponentUtil.getClientVarName(facesContext, modal);
		String clientKey = modal.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		responseWriter.write("var " + clientVarName + " = ");
		super.encodeJavaScriptMain(facesContext, uiComponent);
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Dialog modal = (Dialog) uiComponent;

		// Ensure that an id is written out
		modal.setId(StringPool.UNDERLINE + modal.getId());

		super.encodeMarkupBegin(facesContext, uiComponent);
	}

	@Override
	protected void encodeHiddenAttributes(ResponseWriter responseWriter, Dialog dialog, boolean first)
		throws IOException {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		// centered
		encodeBoolean(responseWriter, "centered", true, first);
		
		first = false;
		
		// contentBox
		String clientId = dialog.getClientId(facesContext);
		String contentBox = StringPool.POUND + ComponentUtil.escapeClientId(clientId);
		encodeString(responseWriter, AlloyRendererUtil.CONTENT_BOX, contentBox, first);

		// headerContent
		String headerText = dialog.getHeaderText();

		if (headerText != null) {
			encodeString(responseWriter, AlloyRendererUtil.HEADER_CONTENT, headerText, first);
		}

		// render : true
		encodeWidgetRender(responseWriter, first);

		// visible
		Boolean autoShow = dialog.isAutoShow();

		if (autoShow != null) {
			encodeBoolean(responseWriter, AlloyRendererUtil.VISIBLE, autoShow, first);
		}
	}

	@Override
	public String getDelegateComponentFamily() {
		return Dialog.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return Dialog.DELEGATE_RENDERER_TYPE;
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

}
