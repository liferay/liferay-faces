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
package com.liferay.faces.bridge.renderkit.primefaces;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.liferay.faces.bridge.component.primefaces.PrimeFacesFileUpload;
import com.liferay.faces.util.render.RendererWrapper;


/**
 * This class is a workaround for a bug in PrimeFaces such that the p:fileUpload component uses the value of the form
 * "action" attribute as the postback URL, rather than the "javax.faces.encodedURL" hidden field.
 *
 * @see     http://code.google.com/p/primefaces/issues/detail?id=2905
 * @author  Neil Griffin
 */
public class FormRendererPrimeFacesImpl extends RendererWrapper {

	// Private Constants
	private static final String ENCTYPE = "enctype";
	private static final String MULTIPART = "multipart";

	// Private Data Members
	private Renderer wrappedRenderer;

	public FormRendererPrimeFacesImpl(Renderer renderer) {
		this.wrappedRenderer = renderer;
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if (isMultiPartForm(uiComponent)) {
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
		else {
			super.encodeBegin(facesContext, uiComponent);
		}
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

		String enctype = (String) uiComponent.getAttributes().get(ENCTYPE);

		if ((enctype != null) && (enctype.toLowerCase().indexOf(MULTIPART) >= 0)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Renderer getWrapped() {
		return wrappedRenderer;
	}

}
