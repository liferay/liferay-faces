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
package com.liferay.faces.bridge.application;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ViewHandlerImpl extends ViewHandlerCompatImpl {

	// Private Constants
	private static final String DOT_REPLACEMENT = "_DOT_";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ViewHandlerImpl.class);

	// Private Data Members
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
	public UIViewRoot createView(FacesContext facesContext, String viewId) {

		logger.debug("Creating view for viewId=[{0}]", viewId);

		String queryString = null;

		if (viewId != null) {

			viewId = evaluateExpressionJSF1(facesContext, viewId);

			int pos = viewId.indexOf(StringPool.QUESTION);

			if (pos > 0) {
				queryString = viewId.substring(pos);
				logger.debug("Temporarily removed query-string from viewId=[{0}]", viewId);
				viewId = viewId.substring(0, pos);
			}
		}

		UIViewRoot uiViewRoot = super.createView(facesContext, viewId);

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
	 * The purpose of this method is to provide a workaround for an incompatibility with the Mojarra implementation of
	 * JSF. Specifically, the Mojarra {@link MultiViewHandler#getActionURL(FacesContext, String)} method does not
	 * properly handle viewId values that contain dot characters as part of the query-string. For example, if the
	 * specified viewId is "/view.xhtml?javax.portlet.faces.PortletMode=edit" then Mojarra will think the filename
	 * extension is ".PortletMode" instead of ".xhtml". This method works around the problem by temporarily substituting
	 * all dot characters in the viewId query-string with a token before delegating to the Mojarra method. After
	 * delegation, the dot characters are replaced.
	 */
	@Override
	public String getActionURL(FacesContext facesContext, String viewId) {

		String actionURL = null;

		if (viewId != null) {
			boolean replacedDotChars = false;
			int questionMarkPos = viewId.indexOf(StringPool.QUESTION);

			if (questionMarkPos > 0) {

				int dotPos = viewId.indexOf(StringPool.PERIOD, questionMarkPos);

				if (dotPos > 0) {
					String queryString = viewId.substring(questionMarkPos);
					queryString = queryString.replaceAll(BridgeConstants.REGEX_DOT_DELIMITER, DOT_REPLACEMENT);
					viewId = viewId.substring(0, questionMarkPos) + queryString;
					replacedDotChars = true;
				}

			}

			actionURL = super.getActionURL(facesContext, viewId);

			if (replacedDotChars) {
				actionURL = actionURL.replaceAll(DOT_REPLACEMENT, StringPool.PERIOD);
			}
		}

		return actionURL;
	}

	@Override
	public ViewHandler getWrapped() {
		return wrappedViewHandler;
	}

}
