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
package com.liferay.faces.bridge.renderkit.html_basic;

import java.io.Writer;

import javax.faces.component.UIForm;
import javax.faces.component.UIOutput;
import javax.faces.component.UIPanel;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitWrapper;
import javax.faces.render.Renderer;

import com.liferay.faces.bridge.component.icefaces.DataPaginator;
import com.liferay.faces.bridge.component.primefaces.PrimeFacesFileUpload;
import com.liferay.faces.bridge.renderkit.bridge.ResponseWriterBridgeImpl;
import com.liferay.faces.bridge.renderkit.icefaces.DataPaginatorRenderer;
import com.liferay.faces.bridge.renderkit.icefaces.HeadRendererICEfacesImpl;
import com.liferay.faces.bridge.renderkit.primefaces.FileUploadRendererPrimeFacesImpl;
import com.liferay.faces.bridge.renderkit.primefaces.FormRendererPrimeFacesImpl;
import com.liferay.faces.bridge.renderkit.primefaces.HeadRendererPrimeFacesImpl;
import com.liferay.faces.bridge.renderkit.richfaces.FileUploadRendererRichFacesImpl;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class RenderKitBridgeImpl extends RenderKitWrapper {

	// Private Constants
	private static final String JAVAX_FACES_FORM = "javax.faces.Form";
	private static final String JAVAX_FACES_HEAD = "javax.faces.Head";
	private static final String JAVAX_FACES_OUTPUT = UIOutput.COMPONENT_FAMILY;
	private static final Object ICEFACES_HEAD_RENDERER = "org.icefaces.ace.renderkit.HeadRenderer";
	private static final String PRIMEFACES_FAMILY = "org.primefaces.component";
	private static final String PRIMEFACES_HEAD_RENDERER = "org.primefaces.renderkit.HeadRenderer";
	private static final String RICHFACES_FILE_UPLOAD_FAMILY = "org.richfaces.FileUpload";
	private static final String RICHFACES_FILE_UPLOAD_RENDERER_TYPE = "org.richfaces.FileUploadRenderer";
	private static final String SCRIPT_RENDERER_TYPE = "javax.faces.resource.Script";

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
		ResponseWriter responseWriter = new ResponseWriterBridgeImpl(wrappedResponseWriter);

		return responseWriter;
	}

	@Override
	public Renderer getRenderer(String family, String rendererType) {

		Renderer renderer = super.getRenderer(family, rendererType);

		if (JAVAX_FACES_OUTPUT.equals(family) && JAVAX_FACES_HEAD.equals(rendererType)) {

			// For some reason Mojarra will cause the ICEfaces and PrimeFaces HeadRenderer instances to win, even though
			// the bridge's faces-config has <ordering><before><others/></before></ordering>. Need to override them with
			// renderers that are compatible with a portlet environment.
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
		else if (UIForm.COMPONENT_FAMILY.equals(family) && JAVAX_FACES_FORM.equals(rendererType)) {

			// If the PrimeFaces p:fileUpload should be forced to use a ResourceURL, then return a special
			// form renderer. http://issues.liferay.com/browse/FACES-1194
			Product primeFaces = ProductMap.getInstance().get(ProductConstants.PRIMEFACES);

			if (primeFaces.isDetected() && (primeFaces.getMajorVersion() == 3) && (primeFaces.getMinorVersion() < 3)) {
				renderer = new FormRendererPrimeFacesImpl(renderer);
			}
		}
		else if (UIOutput.COMPONENT_FAMILY.equals(family) && SCRIPT_RENDERER_TYPE.equals(rendererType)) {
			renderer = new ScriptRendererBridgeImpl(renderer);
		}
		else if (UIPanel.COMPONENT_FAMILY.equals(family) && DataPaginator.RENDERER_TYPE.equals(rendererType)) {

			// Workaround for: http://jira.icesoft.org/browse/ICE-6398
			renderer = new DataPaginatorRenderer(renderer);
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
