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
package com.liferay.faces.bridge.renderkit.html_basic.internal;

import java.io.Writer;

import javax.faces.component.UIForm;
import javax.faces.component.UIOutput;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitWrapper;
import javax.faces.render.Renderer;

import com.liferay.faces.bridge.component.primefaces.internal.PrimeFacesFileUpload;
import com.liferay.faces.bridge.renderkit.bridge.internal.ResponseWriterBridgeImpl;
import com.liferay.faces.bridge.renderkit.icefaces.internal.HeadRendererICEfacesImpl;
import com.liferay.faces.bridge.renderkit.primefaces.internal.BodyRendererPrimeFacesImpl;
import com.liferay.faces.bridge.renderkit.primefaces.internal.FileUploadRendererPrimeFacesImpl;
import com.liferay.faces.bridge.renderkit.primefaces.internal.FormRendererPrimeFacesImpl;
import com.liferay.faces.bridge.renderkit.primefaces.internal.HeadRendererPrimeFacesImpl;
import com.liferay.faces.bridge.renderkit.richfaces.internal.FileUploadRendererRichFacesImpl;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class RenderKitBridgeImpl extends RenderKitWrapper {

	// Private Constants
	private static final String JAVAX_FACES_BODY = "javax.faces.Body";
	private static final String JAVAX_FACES_FORM = "javax.faces.Form";
	private static final String JAVAX_FACES_HEAD = "javax.faces.Head";
	private static final Object ICEFACES_HEAD_RENDERER = "org.icefaces.ace.renderkit.HeadRenderer";
	private static final String PRIMEFACES_FAMILY = "org.primefaces.component";
	private static final String PRIMEFACES_HEAD_RENDERER = "org.primefaces.renderkit.HeadRenderer";
	private static final String RICHFACES_FILE_UPLOAD_FAMILY = "org.richfaces.FileUpload";
	private static final String RICHFACES_FILE_UPLOAD_RENDERER_TYPE = "org.richfaces.FileUploadRenderer";
	private static final String SCRIPT_RENDERER_TYPE = "javax.faces.resource.Script";
	private static final String STYLESHEET_RENDERER_TYPE = "javax.faces.resource.Stylesheet";
	private static final Product PRIMEFACES = ProductMap.getInstance().get(ProductConstants.PRIMEFACES);

	// Private Data Members
	private RenderKit wrappedRenderKit;

	public RenderKitBridgeImpl(RenderKit wrappedRenderKit) {
		this.wrappedRenderKit = wrappedRenderKit;
	}

	/**
	 * Provides the bridge with the ability to wrap the HTML_BASIC ResponseWriter provided by the JSF implementation.
	 */
	@Override
	public ResponseWriter createResponseWriter(Writer writer, String contentTypeList, String characterEncoding) {
		ResponseWriter wrappedResponseWriter = wrappedRenderKit.createResponseWriter(writer, contentTypeList,
				characterEncoding);

		return new ResponseWriterBridgeImpl(wrappedResponseWriter);
	}

	@Override
	public Renderer getRenderer(String family, String rendererType) {

		Renderer renderer = super.getRenderer(family, rendererType);

		if (UIOutput.COMPONENT_FAMILY.equals(family)) {

			if (JAVAX_FACES_HEAD.equals(rendererType)) {

				// The ICEfaces and PrimeFaces HeadRenderer instances wrap/decorate the one supplied by the JSF runtime.
				// But since the bridge's faces-config has <ordering><before><others/></before></ordering>, it is not
				// possible for the bridge to decorate with renderers that are compatible with a portlet environment.
				// Therefore it is necessary to have the bridge intercede at the RenderKit level.
				String rendererClassName = renderer.getClass().getName();

				if (ICEFACES_HEAD_RENDERER.equals(rendererClassName)) {
					renderer = new HeadRendererICEfacesImpl();
				}
				else if (PRIMEFACES_HEAD_RENDERER.equals(rendererClassName)) {
					renderer = new HeadRendererPrimeFacesImpl();
				}
				else {
					renderer = new HeadRendererBridgeImpl();
				}
			}
			else if (JAVAX_FACES_BODY.equals(rendererType) && PRIMEFACES.isDetected() &&
					(PRIMEFACES.getMajorVersion() >= 5)) {
				renderer = new BodyRendererPrimeFacesImpl();
			}
			else if (SCRIPT_RENDERER_TYPE.equals(rendererType) || STYLESHEET_RENDERER_TYPE.equals(rendererType)) {
				renderer = new ResourceRendererBridgeImpl(renderer);
			}
		}
		else if (UIForm.COMPONENT_FAMILY.equals(family) && JAVAX_FACES_FORM.equals(rendererType) &&
				PRIMEFACES.isDetected()) {

			renderer = new FormRendererPrimeFacesImpl(PRIMEFACES.getMajorVersion(), PRIMEFACES.getMinorVersion(),
					renderer);
		}
		else if (PRIMEFACES_FAMILY.equals(family) && PrimeFacesFileUpload.RENDERER_TYPE.equals(rendererType)) {
			renderer = new FileUploadRendererPrimeFacesImpl(renderer);
		}
		else if (RICHFACES_FILE_UPLOAD_FAMILY.equals(family) &&
				RICHFACES_FILE_UPLOAD_RENDERER_TYPE.equals(rendererType)) {
			renderer = new FileUploadRendererRichFacesImpl(renderer);
		}

		return renderer;
	}

	@Override
	public RenderKit getWrapped() {
		return wrappedRenderKit;
	}
}
