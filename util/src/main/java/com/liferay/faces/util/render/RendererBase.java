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
package com.liferay.faces.util.render;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.context.ExtFacesContext;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.portal.ScriptDataUtil;
import com.liferay.faces.util.portal.WebKeys;

import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.model.Portlet;


/**
 * This is an abstract class that provides base rendering functionality. It extends normal rendering with methods such
 * as {@link #encodeHTMLBegin(FacesContext, UIComponent)} and {@link #encodeHTMLEnd(FacesContext, UIComponent)} that
 * provide a more fine-grained rendering sequence.
 *
 * @author  Neil Griffin
 */
public abstract class RendererBase extends Renderer {

	// Private Constants
	private static final String TEXT_JAVASCRIPT = "text/javascript";
	private static final String TYPE = "type";

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeBegin(facesContext, uiComponent);

		encodeHTMLBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeEnd(facesContext, uiComponent);

		encodeHTMLEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (hasJavaScript()) {

			if (!isAjax(facesContext) && isForceInline(facesContext, uiComponent)) {

				responseWriter.startElement(StringPool.SCRIPT, uiComponent);
				responseWriter.writeAttribute(TYPE, TEXT_JAVASCRIPT, null);
			}

			encodeJavaScript(facesContext, uiComponent);

			if (!isAjax(facesContext) && isForceInline(facesContext, uiComponent)) {
				responseWriter.endElement(StringPool.SCRIPT);
			}
		}
	}

	protected void encodeClassAttribute(ResponseWriter responseWriter, Styleable styleable, String classNames)
		throws IOException {

		String allCssClasses = ComponentUtil.concatAllCssClasses(styleable, classNames);
		responseWriter.writeAttribute(StringPool.CLASS, allCssClasses, null);
	}

	protected void encodeHTMLBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}

	protected void encodeHTMLEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}

	protected void encodeJavaScript(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter backupResponseWriter = facesContext.getResponseWriter();

		if (isAjax(facesContext) || !isForceInline(facesContext, uiComponent)) {

			BufferedResponseWriter bufferedResponseWriter = new BufferedResponseWriter();
			facesContext.setResponseWriter(bufferedResponseWriter);
		}

		encodeJavaScriptBegin(facesContext, uiComponent);
		encodeJavaScriptMain(facesContext, uiComponent);
		encodeJavaScriptCustom(facesContext, uiComponent);
		encodeJavaScriptEnd(facesContext, uiComponent);

		if (isAjax(facesContext) || !isForceInline(facesContext, uiComponent)) {
			handleBuffer(facesContext, uiComponent);
		}

		facesContext.setResponseWriter(backupResponseWriter);
	}

	protected void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}

	protected void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}

	protected void encodeJavaScriptEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}

	protected void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}

	protected void handleBuffer(FacesContext facesContext, UIComponent uiComponent) {

		BufferedResponseWriter bufferedResponseWriter = (BufferedResponseWriter) facesContext.getResponseWriter();

		// If running in an Ajax request, then it is not possible to render the scripts at the bottom of the
		// portal page. Instead, store the script in the JavaScript map so that PartialViewContextCleanupImpl
		// knows to include it in the <eval>...</eval> section of the JSF partial-response.
		if (isAjax(facesContext)) {
			Map<String, String> javaScriptMap = ExtFacesContext.getInstance().getJavaScriptMap();
			javaScriptMap.put(uiComponent.getClientId(facesContext), bufferedResponseWriter.toString());
		}

		// Otherwise, render the script at the bottom of the portal page by setting the WebKeys.AUI_SCRIPT_DATA
		// request attribute.
		else { // TODO This may be different depending on if component is aui or not. come back to this

			ExternalContext externalContext = facesContext.getExternalContext();
			ScriptData scriptData = (ScriptData) externalContext.getRequestMap().get(WebKeys.AUI_SCRIPT_DATA);

			if (scriptData == null) {
				scriptData = new ScriptData();
				externalContext.getRequestMap().put(WebKeys.AUI_SCRIPT_DATA, scriptData);
			}

			String portletId = StringPool.BLANK;
			Portlet portlet = (Portlet) facesContext.getExternalContext().getRequestMap().get(WebKeys.RENDER_PORTLET);

			if (portlet != null) {
				portletId = portlet.getPortletId();
			}

			String[] moduleArray = getModules();
			String modules = null;

			if (moduleArray != null) {

				StringBuilder stringBuilder = new StringBuilder();

				for (int i = 0; i < moduleArray.length; i++) {

					if (i > 0) {
						stringBuilder.append(StringPool.COMMA);
					}

					stringBuilder.append(moduleArray[i]);
				}

				modules = stringBuilder.toString();
			}

			ScriptDataUtil.append(scriptData, portletId, bufferedResponseWriter.toString(), modules);
		}
	}

	protected boolean hasJavaScript() {
		return false;
	}

	protected boolean isForceInline(FacesContext facesContext, UIComponent uiComponent) {
		return false;
	}

	protected String[] getModules() {
		return null;
	}

	protected boolean isAjax(FacesContext facesContext) {
		return facesContext.getPartialViewContext().isAjaxRequest();
	}
}
