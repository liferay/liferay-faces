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
package com.liferay.faces.bridge.application;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ViewHandlerImpl extends ViewHandlerWrapper {

	private static final Logger logger = LoggerFactory.getLogger(ViewHandlerImpl.class);

	private ViewHandler wrappedViewHandler;

	public ViewHandlerImpl(ViewHandler viewHandler) {
		this.wrappedViewHandler = viewHandler;
	}

	/**
	 * The purpose of overriding this method is to work-around a problem in the Mojarra
	 * com.sun.faces.application.view.MultiViewHanderl#derivePhysicalViewId(FacesContext, String, boolean) method.
	 * Specifically, the method does not expect a query-string (like ?javax.portlet.faces.PortletMode=edit) in the
	 * viewId. This is expected actually, because the JSF spec does not provide for query-strings. However, it is indeed
	 * a Bridge Spec feature. This method temporarily removes the query-string before asking Mojarra to create the view,
	 * and then adds it back.
	 */
	@Override
	public UIViewRoot createView(FacesContext context, String viewId) {

		logger.debug("Creating view for viewId=[{0}]", viewId);
		String queryString = null;

		if (viewId != null) {
			int pos = viewId.indexOf(BridgeConstants.CHAR_QUESTION_MARK);

			if (pos > 0) {
				queryString = viewId.substring(pos);
				logger.debug("Temporarily removed query-string from viewId=[{0}]", viewId);
				viewId = viewId.substring(0, pos);
			}
		}

		UIViewRoot uiViewRoot = super.createView(context, viewId);

		if (queryString != null) {
			logger.debug("Adding back query-string viewId=[{0}]", viewId);
			uiViewRoot.setViewId(viewId + queryString);
		}

		return uiViewRoot;
	}

	@Override
	public UIViewRoot restoreView(FacesContext facesContext, String viewId) {
		logger.debug("Restoring view for viewId=[{0}]", viewId);
		return super.restoreView(facesContext, viewId);
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
