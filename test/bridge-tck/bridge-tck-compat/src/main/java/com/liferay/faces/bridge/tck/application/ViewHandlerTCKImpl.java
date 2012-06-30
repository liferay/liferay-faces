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
package com.liferay.faces.bridge.tck.application;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ViewHandlerTCKImpl extends ViewHandlerWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ViewHandlerTCKImpl.class);

	// Private Data Members
	private ViewHandler wrappedViewHandler;

	public ViewHandlerTCKImpl(ViewHandler viewHandler) {
		this.wrappedViewHandler = viewHandler;
	}

	/**
	 * The purpose of overriding this method is to work-around a JSF 1.2 dependency in the TCK. Once the
	 * TestSuiteViewHandlerImpl is changed to be a subclass of ViewDeclarationLanguage this can be removed.
	 */
	@Override
	public void renderView(FacesContext facesContext, UIViewRoot viewToRender) throws IOException, FacesException {

		try {
			super.renderView(facesContext, viewToRender);
		}
		catch (FacesException e) {
			String tckRequestAttribute = (String) facesContext.getExternalContext().getRequestMap().get(
					"javax.portlet.faces.tck.testRenderPolicyPass");

			if (tckRequestAttribute != null) {
				logger.info("Working around JSF 1.2 dependency in the TCK");

				ViewDeclarationLanguage viewDeclarationLanguage = getViewDeclarationLanguage(facesContext,
						viewToRender.getViewId());
				viewDeclarationLanguage.renderView(facesContext, viewToRender);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ViewHandler getWrapped() {
		return wrappedViewHandler;
	}
}
