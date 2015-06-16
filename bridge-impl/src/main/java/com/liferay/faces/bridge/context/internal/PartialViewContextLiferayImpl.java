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
package com.liferay.faces.bridge.context.internal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextWrapper;
import javax.faces.context.ResponseWriter;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.liferay.faces.bridge.client.internal.ScriptDataUtil;
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.context.PartialResponseWriterWrapper;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.jsp.JspAdapterFactory;
import com.liferay.faces.util.jsp.StringJspWriter;
import com.liferay.faces.util.jsp.StringJspWriterWrapper;

import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;


/**
 * This class is a wrapper around the {@link PartialViewContext}. Its purpose is to wrap the {@link
 * PartialResponseWriter} with a {@link PartialResponseWriterAlloyImpl} which writes {@link Script}s from {@link
 * FacesRequestContext} to the &lt;eval&gt; section of the partial response.
 *
 * @author  Neil Griffin
 */
public class PartialViewContextLiferayImpl extends PartialViewContextWrapper {

	// Private Data Members
	private FacesContext facesContext;
	private PartialResponseWriter partialResponseWriter;
	private PartialViewContext wrappedPartialViewContext;

	public PartialViewContextLiferayImpl(PartialViewContext partialViewContext, FacesContext facesContext) {
		this.wrappedPartialViewContext = partialViewContext;
		this.facesContext = facesContext;
	}

	/**
	 * This method is missing from the {@link PartialViewContextWrapper} class so it must be implemented here.
	 */
	@Override
	public void setPartialRequest(boolean isPartialRequest) {
		wrappedPartialViewContext.setPartialRequest(isPartialRequest);
	}

	@Override
	public PartialResponseWriter getPartialResponseWriter() {

		if (partialResponseWriter == null) {
			partialResponseWriter = new PartialResponseWriterLiferayImpl(super.getPartialResponseWriter(),
					facesContext);
		}

		return partialResponseWriter;
	}

	@Override
	public PartialViewContext getWrapped() {
		return wrappedPartialViewContext;
	}

	/**
	 * This class serves as a wrapper around the {@link PartialResponseWriter} that will encode JavaScript within an
	 * <eval>...</eval> section just before the end of the partial-response document.
	 *
	 * @author  Kyle Stiemann
	 */
	protected class PartialResponseWriterLiferayImpl extends PartialResponseWriterWrapper {

		// Private Data Members
		private FacesContext facesContext;
		private boolean wroteEval;

		public PartialResponseWriterLiferayImpl(PartialResponseWriter partialResponseWriter,
			FacesContext facesContext) {
			super(partialResponseWriter);
			this.facesContext = facesContext;
		}

		@Override
		public void endDocument() throws IOException {

			if (!wroteEval) {

				FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
				List<Script> scripts = facesRequestContext.getScripts();

				if (!scripts.isEmpty()) {

					super.startEval();
					writeScripts(facesContext, partialResponseWriter, scripts);
					super.endEval();
				}
			}

			super.endDocument();
		}

		@Override
		public void endEval() throws IOException {

			FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
			List<Script> scripts = facesRequestContext.getScripts();

			if (!scripts.isEmpty()) {
				writeScripts(facesContext, partialResponseWriter, scripts);
			}

			super.endEval();
			wroteEval = true;
		}

		protected void writeScripts(FacesContext facesContext, ResponseWriter responseWriter, List<Script> scripts)
			throws IOException {

			ScriptData scriptData = new ScriptData();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			ScriptDataUtil.scriptDataAppendScripts(scriptData, requestMap, scripts);

			ScriptData savedScriptData = (ScriptData) requestMap.get(WebKeys.AUI_SCRIPT_DATA);
			requestMap.put(WebKeys.AUI_SCRIPT_DATA, scriptData);

			PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
			HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
			PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
			HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
			ELContext elContext = facesContext.getELContext();
			JspAdapterFactory jspAdapterFactory = (JspAdapterFactory) FactoryExtensionFinder.getFactory(
					JspAdapterFactory.class);
			StringJspWriter stringJspWriter = jspAdapterFactory.getStringJspWriter();
			ScriptDataWriter scriptDataWriter = new ScriptDataWriter(stringJspWriter);
			PageContext pageContext = jspAdapterFactory.getPageContext(httpServletRequest, httpServletResponse,
					elContext, scriptDataWriter);
			ScriptTagUtil.flushScriptData(pageContext);
			requestMap.put(WebKeys.AUI_SCRIPT_DATA, savedScriptData);
			responseWriter.write(scriptDataWriter.toString());
		}

		private class ScriptDataWriter extends StringJspWriterWrapper {

			// Private Data Members
			private StringJspWriter wrappedStringJspWriter;

			public ScriptDataWriter(StringJspWriter stringJspWriter) {
				super(0, true);
				this.wrappedStringJspWriter = stringJspWriter;
			}

			@Override
			public void write(String string) throws IOException {

				if (!(string.startsWith("<script") || string.endsWith("script>"))) {
					super.write(string);
				}
			}

			@Override
			public StringJspWriter getWrapped() {
				return wrappedStringJspWriter;
			}
		}
	}
}
