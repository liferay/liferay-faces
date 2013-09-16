/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.renderkit.bridge;

import java.io.IOException;
import java.io.Writer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;
import javax.faces.render.ResponseStateManager;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.renderkit.html_basic.BodyRendererBridgeImpl;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * <p>This class acts as a portlet filter (in a sense), in that it decorates/wraps the Faces implementation {@link
 * ResponseWriter} so that it can transform what is written to the response. The response needs to be filtered because
 * of three limitations in the JSF 2.0 and JSF 2.1 jsf.js JavaScript code). The goal is to fix these limitations in JSF
 * 2.2 so that this class will become unnecessary.</p>
 *
 * <p>The three limitations in the jsf.js JavaScript code are:</p>
 *
 * <p>1. The &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt; and &lt;!DOCTYPE&gt; markers is assumed to
 * be valid to keep in the response because jsf.js assumes a servlet environment in which the rendered JSF view takes up
 * the entire DOM in the userAgent/browser.</p>
 *
 * <p>The workaround for #1 is to simply strip out the offending markers.</p>
 *
 * <p>2. During "partial" updates in which the javax.faces.ViewRoot is being replaced in the DOM (which is basically a
 * full update of the view -- not really partial), jsf.js attempts to replace everything inside the
 * &lt;body&gt;...&lt;/body&gt; elements, which of course is a servlet environment assumption. Instead, it should be the
 * outermost &lt;div&gt; tag rendered by the bridge's {@link BodyRendererBridgeImpl} that should be replaced in the
 * DOM.</p>
 *
 * <p>The workaround for #2 is to substitute the id value of "javax.faces.ViewRoot" with the id of the outermost
 * &lt;div&gt; tag rendered by the bridge's {@link BodyRendererBridgeImpl}.</p>
 *
 * <p>3. Also in the the case of a "partial" update of javax.faces.ViewRoot, jsf.js attempts to dynamically create the
 * javax.faces.ViewState hidden input field if it is not found in the form. The JavaScript code will successfully do
 * this provided it is permitted to replace everything inside the &lt;body&gt;...&lt;/body&gt; elements, but since we
 * can't let that happen in a portlet environment, the hidden field does not get created.</p>
 *
 * <p>The workaround for #3 is to inject the javax.faces.ViewState hidden field into the response if it is not already
 * there.</p>
 *
 * @author  Neil Griffin
 */
public class ResponseWriterBridgeImpl extends ResponseWriterWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResponseWriterBridgeImpl.class);

	// Private Constants
	private static final String ATTRIBUTE_AUTOCOMPLETE = "autocomplete";
	private static final String ATTRIBUTE_ID = "id";
	private static final String ATTRIBUTE_NAME = "name";
	private static final String ATTRIBUTE_TYPE = "type";
	private static final String ATTRIBUTE_VALUE = "value";
	private static final String DOCTYPE_MARKER = "<!DOCTYPE";
	private static final String ELEMENT_CHANGES = "changes";
	private static final String ELEMENT_FORM = "form";
	private static final String ELEMENT_INPUT = "input";
	private static final String ELEMENT_PARTIAL_RESPONSE = "partial-response";
	private static final String ELEMENT_UPDATE = "update";
	private static final String TYPE_HIDDEN = "hidden";
	private static final String VALUE_OFF = "off";
	private static final String XML_MARKER = "<?xml";

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

	// Private Data Members
	private String currentElementName;
	private boolean insidePartialResponse;
	private boolean insideChanges;
	private boolean insideCData;
	private boolean insideInput;
	private boolean insideUpdate;
	private boolean namespacedParameters;
	private boolean viewStateWritten;
	private ResponseWriter wrappedResponseWriter;

	public ResponseWriterBridgeImpl(ResponseWriter wrappedResponseWriter) {
		this.wrappedResponseWriter = wrappedResponseWriter;

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		this.namespacedParameters = bridgeContext.getPortletContainer().isNamespacedParameters();
	}

	@Override
	public ResponseWriter cloneWithWriter(Writer writer) {
		return new ResponseWriterBridgeImpl(wrappedResponseWriter.cloneWithWriter(writer));
	}

	@Override
	public void endCDATA() throws IOException {

		// Set a flag indicating that we're in the CDATA section.
		insideCData = false;

		logger.trace("insideCData=[false]");

		// Ask the superclass method to perform the endCDATA writing, which basically delegates to the Faces
		// implementation writer (or the ICEfaces DOMResponseWriter) in the chain-of-responsibility.
		super.endCDATA();
	}

	@Override
	public void endElement(String elementName) throws IOException {

		logger.trace("elementName=[{0}]", elementName);

		// Clear the "current" element name that we were working with.
		currentElementName = null;

		// If the specified element name is "partial", "changes", or "update" then set flags accordingly.
		if (ELEMENT_PARTIAL_RESPONSE.equals(elementName)) {
			insidePartialResponse = false;
		}
		else if (ELEMENT_CHANGES.equals(elementName)) {
			insideChanges = false;
		}
		else if (ELEMENT_UPDATE.equals(elementName)) {
			insideUpdate = false;
		}
		else if (ELEMENT_INPUT.equals(elementName)) {
			insideInput = false;
		}

		// Otherwise, if the specified element name is "form" then inject the javax.faces.ViewState hidden field if
		// it's not already written to the response.
		else if (ELEMENT_FORM.equals(elementName)) {

			if (insidePartialResponse && insideChanges && insideUpdate && insideCData && !viewStateWritten) {
				startElement(ELEMENT_INPUT, null);
				writeAttribute(ATTRIBUTE_TYPE, TYPE_HIDDEN, null);

				String viewStateName = PartialResponseWriter.VIEW_STATE_MARKER;

				if (namespacedParameters && JSF_RUNTIME_SUPPORTS_NAMESPACING_VIEWSTATE) {

					FacesContext facesContext = FacesContext.getCurrentInstance();
					String namingContainerId = facesContext.getViewRoot().getContainerClientId(facesContext);
					viewStateName = namingContainerId + viewStateName;
				}

				writeAttribute(ATTRIBUTE_NAME, viewStateName, null);
				writeAttribute(ATTRIBUTE_ID, viewStateName, null);

				FacesContext facesContext = FacesContext.getCurrentInstance();
				String viewState = facesContext.getApplication().getStateManager().getViewState(facesContext);
				writeAttribute(ATTRIBUTE_VALUE, viewState, null);
				writeAttribute(ATTRIBUTE_AUTOCOMPLETE, VALUE_OFF, null);
				endElement(ELEMENT_INPUT);
				viewStateWritten = true;
			}
		}

		// Ask the superclass method to perform the endElement writing, which basically delegates to the Faces
		// implementation writer (or the ICEfaces DOMResponseWriter) in the chain-of-responsibility.
		super.endElement(elementName);
	}

	@Override
	public void startCDATA() throws IOException {

		// Set a flag indicating that we're in the CDATA section.
		insideCData = true;

		logger.trace("insideCData=[true]");

		// Ask the superclass method to perform the startCDATA writing, which basically delegates to the Faces
		// implementation writer (or the ICEfaces DOMResponseWriter) in the chain-of-responsibility.
		super.startCDATA();
	}

	@Override
	public void startElement(String elementName, UIComponent uiComponent) throws IOException {

		logger.trace("elementName=[{0}]", elementName);

		// Remember the specified element as the "current" element name we're working with.
		currentElementName = elementName;

		// If the specified element name is "partial", "changes", or "update" then set flags accordingly.
		if (ELEMENT_PARTIAL_RESPONSE.equals(elementName)) {
			insidePartialResponse = true;
		}
		else if (ELEMENT_CHANGES.equals(elementName)) {
			insideChanges = true;
		}
		else if (ELEMENT_UPDATE.equals(elementName)) {
			insideUpdate = true;
		}
		else if (ELEMENT_INPUT.equals(elementName)) {
			insideInput = true;
		}

		// FACES-1424: Otherwise, if the specified element name is "form" then reset the viewStateWritten flag. This
		// ensures that in the case of multiple forms,  that the "javax.faces.ViewState" hidden field will be present in
		// each one.
		else if (ELEMENT_FORM.equals(elementName) && viewStateWritten) {
			viewStateWritten = false;
		}

		// Ask the superclass method to perform the startElement writing, which basically delegates to the Faces
		// implementation writer (or the ICEfaces DOMResponseWriter) in the chain-of-responsibility.
		super.startElement(elementName, uiComponent);
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

				int greaterThanPos = data.indexOf(StringPool.GREATER_THAN);

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

				wrappedResponseWriter.write(cbuf, off, len);
			}
		}
	}

	/**
	 * <p>If the implementation (Mojarra in particular) is trying to do a DOM replacement using markup like the
	 * following:</p>
	 *
	 * <pre>
	       &lt;partial-response&gt;
	         &lt;changes&gt;
	           &lt;update id="javax.faces.ViewRoot"&gt;&lt;![CDATA[...]]]&gt;&lt;/update&gt;
	           ...
	         &lt;/changes&gt;
	         &lt;/partial-response&gt;
	 * </pre>
	 *
	 * <p>... then this will cause the jsf.js JavaScript library to replace the entire &lt;body&gt; element! In order to
	 * avoid this, need to change the id attribute from "javax.faces.ViewRoot" to the clientId of the outer-most div of
	 * the portlet, rendered by the bridge's {@link BodyRendererBridgeImpl}.</p>
	 */
	@Override
	public void writeAttribute(String attributeName, Object attributeValue, String property) throws IOException {

		logger.trace("attributeName=[{0}] attributeValue=[{1}]", attributeName, attributeValue);

		if (attributeName != null) {

			// If the specified attribute name is "id", then
			if (attributeName.equals(ATTRIBUTE_ID)) {

				// If the Faces implementation is trying to update the javax.faces.ViewRoot, then substitute the value
				// of the outermost <div>...</div> (rendered by the bridge's BodyRenderer) for the specified value. This
				// is a workaround for jsf.js limitation #2 as described in the class header comments.
				if (insidePartialResponse && insideChanges && insideUpdate &&
						ELEMENT_UPDATE.equals(currentElementName) &&
						PartialResponseWriter.RENDER_ALL_MARKER.equals(attributeValue)) {

					FacesContext facesContext = FacesContext.getCurrentInstance();
					attributeValue = facesContext.getViewRoot().getContainerClientId(facesContext);
				}

				// Otherwise, if the current element is "input" and the specified attribute value is the
				// "javax.faces.ViewState", then set a flag accordingly.
				else if (insideInput) {

					if (PartialResponseWriter.VIEW_STATE_MARKER.equals(attributeValue)) {
						viewStateWritten = true;
					}
				}
			}
		}

		// Ask the superclass method to write the attribute, which basically delegates to the Faces implementation
		// writer (or the ICEfaces DOMResponseWriter) in the chain-of-responsibility.
		super.writeAttribute(attributeName, attributeValue, property);
	}

	@Override
	public void writeURIAttribute(String name, Object value, String property) throws IOException {

		super.writeURIAttribute(name, value, property);
	}

	@Override
	public ResponseWriter getWrapped() {
		return wrappedResponseWriter;
	}
}
