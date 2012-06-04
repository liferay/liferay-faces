/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


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

		// Set an attribute on the FacesContext that will be picked up by ExternalContextImpl#getResponse() as a flag
		// indicating that the JSP AFTER_VIEW_CONTENT feature needs to be activated.
		facesContext.getAttributes().put(Bridge.AFTER_VIEW_CONTENT, Boolean.TRUE);

		logger.debug("Activated JSP AFTER_VIEW_CONTENT feature");

		// Have the wrapped VDL build the view.
		super.buildView(facesContext, uiViewRoot);

		// Remove the attribute now that the JSP AFTER_VIEW_CONTENT feature is to be deactivated.
		facesContext.getAttributes().remove(Bridge.AFTER_VIEW_CONTENT);

		logger.debug("De-activated JSP AFTER_VIEW_CONTENT");
	}

	@Override
	public void renderView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException {

		// This code is required by the spec in order to support a JSR 301 legacy feature to support usage of a
		// servlet filter to capture the AFTER_VIEW_CONTENT. In reality it will likely never be used.
		Map<String, Object> attributes = facesContext.getExternalContext().getRequestMap();
		attributes.put(Bridge.RENDER_CONTENT_AFTER_VIEW, Boolean.TRUE);
		super.renderView(facesContext, uiViewRoot);
		attributes.remove(Bridge.RENDER_CONTENT_AFTER_VIEW);
	}

	@Override
	public ViewDeclarationLanguage getWrapped() {
		return wrappedViewDeclarationLanguage;
	}

}
