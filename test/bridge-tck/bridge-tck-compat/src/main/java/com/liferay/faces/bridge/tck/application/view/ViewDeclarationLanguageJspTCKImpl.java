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
package com.liferay.faces.bridge.tck.application.view;

import java.io.IOException;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.application.view.ViewDeclarationLanguageWrapper;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ViewDeclarationLanguageJspTCKImpl extends ViewDeclarationLanguageWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ViewDeclarationLanguageJspTCKImpl.class);

	// Private Data Members
	private ViewDeclarationLanguage wrappedViewDeclarationLanguage;

	public ViewDeclarationLanguageJspTCKImpl(ViewDeclarationLanguage viewDeclarationLanguage) {
		this.wrappedViewDeclarationLanguage = viewDeclarationLanguage;
	}

	@Override
	public void renderView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException {

		super.renderView(facesContext, uiViewRoot);

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
