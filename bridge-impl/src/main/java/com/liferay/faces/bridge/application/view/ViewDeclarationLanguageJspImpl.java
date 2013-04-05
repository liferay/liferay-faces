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
package com.liferay.faces.bridge.application.view;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ViewDeclarationLanguageJspImpl extends ViewDeclarationLanguageWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ViewDeclarationLanguageJspImpl.class);

	// Private Data Members
	private ViewDeclarationLanguage wrappedViewDeclarationLanguage;

	public ViewDeclarationLanguageJspImpl(ViewDeclarationLanguage viewDeclarationLanguage) {
		this.wrappedViewDeclarationLanguage = viewDeclarationLanguage;
	}

	@Override
	public void buildView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException {

		// Set a flag on the BridgeContext indicating that JSP AFTER_VIEW_CONTENT processing has been activated. The
		// flag is referenced by {@link ExternalContextImpl#getRequest()} and {@link ExternalContextImpl#getResponse()}
		// and is unset by {@link ExternalContextImpl#dispatch(String)}.
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		bridgeContext.setProcessingAfterViewContent(true);

		logger.debug("Activated JSP AFTER_VIEW_CONTENT feature");

		// Have the wrapped VDL build the view.
		super.buildView(facesContext, uiViewRoot);
	}

	@Override
	public void renderView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException {

		// This code is required by the spec in order to support a JSR 301 legacy feature to support usage of a
		// servlet filter to capture the AFTER_VIEW_CONTENT. In reality it will likely never be used.
		Map<String, Object> attributes = facesContext.getExternalContext().getRequestMap();
		attributes.put(Bridge.RENDER_CONTENT_AFTER_VIEW, Boolean.TRUE);
		super.renderView(facesContext, uiViewRoot);
		attributes.remove(Bridge.RENDER_CONTENT_AFTER_VIEW);

		// TCK TestPage201: renderContentAfterViewTest
		Object afterViewContent = facesContext.getExternalContext().getRequestMap().get(Bridge.AFTER_VIEW_CONTENT);

		if (afterViewContent != null) {

			if (afterViewContent instanceof char[]) {
				facesContext.getResponseWriter().write((char[]) afterViewContent);
			}
			else if (afterViewContent instanceof byte[]) {
				facesContext.getResponseWriter().write(new String((byte[]) afterViewContent));
			}
			else {
				logger.error("Invalid type for {0}={1}", Bridge.AFTER_VIEW_CONTENT, afterViewContent.getClass());
			}
		}
	}

	@Override
	public ViewDeclarationLanguage getWrapped() {
		return wrappedViewDeclarationLanguage;
	}

}
