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
package com.liferay.faces.bridge.renderkit.primefaces.internal;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.ViewHandler;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionListener;
import javax.faces.render.Renderer;
import javax.faces.render.RendererWrapper;

import com.liferay.faces.bridge.component.primefaces.internal.PrimeFacesFileUpload;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeURL;
import com.liferay.faces.bridge.internal.BridgeExt;


/**
 * This class provides a workarounds for FACES-1194 and FACES-1513.
 *
 * @author  Neil Griffin
 */
public class FormRendererPrimeFacesImpl extends RendererWrapper {

	// Private Constants
	private static final String P_DATA_EXPORTER_FQCN = "org.primefaces.component.export.DataExporter";
	private static final String P_FILE_DOWNLOAD_FQCN =
		"org.primefaces.component.filedownload.FileDownloadActionListener";
	private static final String PE_EXPORTER_FQCN = "org.primefaces.extensions.component.exporter.DataExporter";

	// Private Data Members
	private int majorVersion;
	private int minorVersion;
	private Renderer wrappedRenderer;

	public FormRendererPrimeFacesImpl(int majorVersion, int minorVersion, Renderer renderer) {
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.wrappedRenderer = renderer;
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// FACES-1194: If running PrimeFaces 3.2 or older, then p:fileUpload must be forced to use a ResourceURL. This
		// is because the component uses the value of the form "action" attribute as the postback URL, rather than the
		// "javax.faces.encodedURL" hidden field.
		if ((majorVersion == 3) && (minorVersion < 3) && isMultiPartForm(uiComponent)) {

			boolean hasPrimeFacesAjaxFileUploadChild = false;
			UIComponent childComponent = getChildWithRendererType(uiComponent, PrimeFacesFileUpload.RENDERER_TYPE);

			if (childComponent != null) {
				PrimeFacesFileUpload primeFacesFileUpload = new PrimeFacesFileUpload((UIInput) childComponent);

				if (!primeFacesFileUpload.getMode().equals(PrimeFacesFileUpload.MODE_SIMPLE)) {
					hasPrimeFacesAjaxFileUploadChild = true;
					facesContext.getAttributes().put(PrimeFacesFileUpload.AJAX_FILE_UPLOAD, Boolean.TRUE);
				}
			}

			// Continue encoding with the wrapped FormRenderer. When it comes time to call
			// ExternalContext.encodeActionURL(String), the bridge will check for the
			// PrimeFacesFileUpload.AJAX_FILE_UPLOAD attribute. If found, then it will return a PartialActionURL
			// suitable for Ajax requests.
			super.encodeBegin(facesContext, uiComponent);

			if (hasPrimeFacesAjaxFileUploadChild) {
				facesContext.getAttributes().remove(PrimeFacesFileUpload.AJAX_FILE_UPLOAD);
			}
		}

		// Otherwise, if the specified form has a non-Ajax action listener child like p:dataExporter or p:fileDownload,
		// then ensure that the value of "action" attribute of the rendered form is a javax.portlet.ResourceURL that
		// will invoke the RESOURCE_PHASE portlet lifecycle rather than a default javax.portlet.ActionURL that invokes
		// the ACTION_PHASE of the portlet lifecycle.
		else if (hasNonAjaxActionListener(uiComponent)) {
			ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
			String viewId = facesContext.getViewRoot().getViewId();
			String facesActionURL = viewHandler.getActionURL(facesContext, viewId);
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			BridgeURL partialActionURL = bridgeContext.encodePartialActionURL(facesActionURL);
			partialActionURL.getParameterMap().remove(BridgeExt.FACES_AJAX_PARAMETER);

			String nonAjaxPartialActionURL = partialActionURL.toString();
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			ResponseWriter primeFacesResponseWriter = new ResponseWriterPrimeFacesBodyImpl(responseWriter,
					nonAjaxPartialActionURL);
			facesContext.setResponseWriter(primeFacesResponseWriter);
			super.encodeBegin(facesContext, uiComponent);
			facesContext.setResponseWriter(responseWriter);
		}

		// Otherwise, delegate encoding to the wrapped renderer.
		else {
			super.encodeBegin(facesContext, uiComponent);
		}
	}

	protected boolean hasNonAjaxActionListener(UIComponent uiComponent) {

		boolean nonAjaxActionListener = false;

		Iterator<UIComponent> facetsAndChildren = uiComponent.getFacetsAndChildren();

		if (facetsAndChildren != null) {

			while (facetsAndChildren.hasNext()) {

				UIComponent facetOrChild = facetsAndChildren.next();

				if (facetOrChild instanceof ActionSource) {
					ActionSource actionSource = (ActionSource) facetOrChild;
					ActionListener[] actionListeners = actionSource.getActionListeners();

					if (actionListeners != null) {

						for (ActionListener actionListener : actionListeners) {

							String actionListenerFQCN = actionListener.getClass().getName();

							if (P_DATA_EXPORTER_FQCN.equals(actionListenerFQCN) ||
									P_FILE_DOWNLOAD_FQCN.equals(actionListenerFQCN) ||
									PE_EXPORTER_FQCN.equals(actionListenerFQCN)) {
								nonAjaxActionListener = true;

								break;
							}
						}
					}
				}

				if (!nonAjaxActionListener) {
					nonAjaxActionListener = hasNonAjaxActionListener(facetOrChild);
				}
			}
		}

		return nonAjaxActionListener;
	}

	protected UIComponent getChildWithRendererType(UIComponent uiComponent, String rendererType) {

		UIComponent childWithRendererType = null;

		List<UIComponent> children = uiComponent.getChildren();

		if (children != null) {

			for (UIComponent uiComponentChild : children) {

				if (rendererType.equals(uiComponentChild.getRendererType())) {

					childWithRendererType = uiComponentChild;

					break;
				}
				else {
					childWithRendererType = getChildWithRendererType(uiComponentChild, rendererType);

					if (childWithRendererType != null) {
						break;
					}
				}
			}
		}

		return childWithRendererType;
	}

	protected boolean isMultiPartForm(UIComponent uiComponent) {

		String enctype = (String) uiComponent.getAttributes().get("enctype");

		return (enctype != null) && (enctype.toLowerCase().contains("multipart"));
	}

	@Override
	public Renderer getWrapped() {
		return wrappedRenderer;
	}

}
