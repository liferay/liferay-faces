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
package com.liferay.faces.bridge.renderkit.bridge.internal;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;
import javax.faces.render.ResponseStateManager;
import javax.portlet.PortalContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.BridgePortalContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public abstract class ResponseWriterBridgeCompat_2_0_Impl extends ResponseWriterWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResponseWriterBridgeCompat_2_0_Impl.class);

	// Protected Constants
	protected static final String ATTRIBUTE_AUTOCOMPLETE = "autocomplete";
	protected static final String DOCTYPE_MARKER = "<!DOCTYPE";
	protected static final String VALUE_OFF = "off";
	protected static final String VIEW_STATE_MARKER = PartialResponseWriter.VIEW_STATE_MARKER;
	protected static final String XML_MARKER = "<?xml";

	// Private Constants
	private static final boolean JSF_RUNTIME_SUPPORTS_NAMESPACING_VIEWSTATE;

	static {
		boolean jsfRuntimeNamespacesViewState = true;
		Product jsf = ProductMap.getInstance().get(ProductConstants.JSF);

		if (jsf.getTitle().equals(ProductConstants.MOJARRA)) {

			if (jsf.getMajorVersion() == 2) {

				if (jsf.getMinorVersion() == 1) {
					jsfRuntimeNamespacesViewState = (jsf.getRevisionVersion() >= 27);
				}
				else if (jsf.getMinorVersion() == 2) {
					jsfRuntimeNamespacesViewState = (jsf.getRevisionVersion() >= 4);
				}
			}
		}

		JSF_RUNTIME_SUPPORTS_NAMESPACING_VIEWSTATE = jsfRuntimeNamespacesViewState;

		logger.debug("JSF runtime [{0}] version [{1}].[{2}].[{3}] supports namespacing [{4}]: [{5}]", jsf.getTitle(),
			jsf.getMajorVersion(), jsf.getMinorVersion(), jsf.getRevisionVersion(),
			ResponseStateManager.VIEW_STATE_PARAM, JSF_RUNTIME_SUPPORTS_NAMESPACING_VIEWSTATE);
	}

	// Protected Data Members
	protected boolean namespacedParameters;

	public ResponseWriterBridgeCompat_2_0_Impl(ResponseWriter wrappedResponseWriter) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletRequest portletRequest = bridgeContext.getPortletRequest();
		PortalContext portalContext = portletRequest.getPortalContext();
		String namespacedParametersSupport = portalContext.getProperty(
				BridgePortalContext.STRICT_NAMESPACED_PARAMETERS_SUPPORT);
		this.namespacedParameters = (namespacedParametersSupport != null) && JSF_RUNTIME_SUPPORTS_NAMESPACING_VIEWSTATE;
	}

	/**
	 * <p>The main purpose of this method is to solve the jsf.js limitation #1 as described in the class header
	 * comments.</p>
	 *
	 * <p>The Mojarra JSF implementation has a vendor-specific com.sun.faces.facelets.compiler.UIInstructions class that
	 * will render the following markers:</p>
	 *
	 * <ul>
	 *   <li>&lt;?xml version="1.0" encoding="UTF-8"?&gt;</li>
	 *   <li>&lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 *     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</li>
	 * </ul>
	 *
	 * <p>This method will ensure that such markers are not rendered to the response, as they should not be rendered as
	 * part of a portlet, since portlets are simply HTML fragment that are aggregated together into a single HTML
	 * document by the portlet container.</p>
	 */
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {

		if (len > 0) {

			String data = new String(cbuf, off, len);

			if (data.startsWith(XML_MARKER) || data.startsWith(DOCTYPE_MARKER)) {

				logger.trace("filtering marker");

				int greaterThanPos = data.indexOf(">");

				if (greaterThanPos > 0) {
					len -= (greaterThanPos + 1);
					off += (greaterThanPos + 1);
				}
			}

			if (len > 0) {

				if (logger.isTraceEnabled()) {
					String value = new String(cbuf, off, len);
					logger.trace("writing value=[{0}]", value);
				}

				getWrapped().write(cbuf, off, len);
			}
		}
	}

	protected void writeViewStateHiddenField() throws IOException {

		startElement("input", null);
		writeAttribute("type", "hidden", null);

		String viewStateName = PartialResponseWriter.VIEW_STATE_MARKER;

		if (namespacedParameters) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			String namingContainerId = facesContext.getViewRoot().getContainerClientId(facesContext);
			viewStateName = namingContainerId + viewStateName;
		}

		writeAttribute("name", viewStateName, null);

		// TODO: The following line is a workaround and needs to be fixed in FACES-1797.
		writeAttribute("id", viewStateName, null);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		String viewState = facesContext.getApplication().getStateManager().getViewState(facesContext);
		writeAttribute("value", viewState, null);
		writeAttribute(ATTRIBUTE_AUTOCOMPLETE, VALUE_OFF, null);
		endElement("input");
	}
}
