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
import java.lang.reflect.Method;
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
import com.liferay.faces.util.jsp.PageContextAdapter;
import com.liferay.faces.util.jsp.StringJspWriter;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;

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

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PartialViewContextLiferayImpl.class);

	// Private Constants
	private static final String FLUSH_SCRIPT_DATA = "flushScriptData";
	private static final Method FLUSH_SCRIPT_DATA_METHOD;
	private static final String SCRIPT_TAG_FQCN = "com.liferay.taglib.aui.ScriptTag";

	static {

		Product liferayPortal = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);

		if (liferayPortal.isDetected()) {

			int buildNumber = liferayPortal.getBuildId();

			if (buildNumber >= 6011) {

				Method flushScriptDataMethod = null;

				try {
					Class<?> scriptTagClass = Class.forName(SCRIPT_TAG_FQCN);
					flushScriptDataMethod = scriptTagClass.getMethod(FLUSH_SCRIPT_DATA,
							new Class[] { PageContext.class });
				}
				catch (Exception e) {
					logger.error(e);
				}

				FLUSH_SCRIPT_DATA_METHOD = flushScriptDataMethod;
			}
			else {
				FLUSH_SCRIPT_DATA_METHOD = null;
			}
		}
		else {
			FLUSH_SCRIPT_DATA_METHOD = null;
		}
	}

	// Private Data Members
	private FacesContext facesContext;
	private PartialResponseWriter partialResponseWriter;
	private PartialViewContext wrappedPartialViewContext;

	public PartialViewContextLiferayImpl(PartialViewContext partialViewContext, FacesContext facesContext) {
		this.wrappedPartialViewContext = partialViewContext;
		this.facesContext = facesContext;
	}

	private static void flushScriptData(PageContext pageContext) {

		try {

			if (FLUSH_SCRIPT_DATA_METHOD != null) {
				FLUSH_SCRIPT_DATA_METHOD.invoke(null, new Object[] { pageContext });
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
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
			ScriptDataWriter scriptDataWriter = new ScriptDataWriter();
			PageContextAdapter pageContextAdapter = new PageContextAdapter(httpServletRequest, httpServletResponse,
					elContext, scriptDataWriter);
			flushScriptData(pageContextAdapter);
			requestMap.put(WebKeys.AUI_SCRIPT_DATA, savedScriptData);
			responseWriter.write(scriptDataWriter.toString());
		}

		private class ScriptDataWriter extends StringJspWriter {

			@Override
			public void write(String string) throws IOException {

				if (!(string.startsWith("<script") || string.endsWith("script>"))) {
					super.write(string);
				}
			}
		}
	}
}
