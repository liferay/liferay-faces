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
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.Bridge.BridgeRenderPolicy;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class ViewHandlerCompatImpl extends ViewHandlerWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ViewHandlerCompatImpl.class);

	// Private Constants
	private static final String EL_EXPRESSION_PREFIX = "#{";

	@Override
	public void renderView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException, FacesException {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String initParam = externalContext.getInitParameter(Bridge.RENDER_POLICY);

		// If the developer has specified ALWAYS_DELEGATE in the WEB-INF/web.xml descriptor, then execute
		// the Mojarra/MyFaces ViewDeclarationLanguage.
		if (BridgeRenderPolicy.ALWAYS_DELEGATE.toString().equals(initParam)) {
			super.renderView(facesContext, uiViewRoot);
		}

		// Otherwise, if the developer specified NEVER_DELEGATE or didn't specify a value, then execute the
		// bridge's JSP ViewDeclarationLanguage implementation. Note that the spec indicates that if the
		// developer doesn't specify a value, then the bridge first delegates to Mojarra/MyFaces but then
		// tries its own JSP ViewDeclarationLanguage if an exception is thrown. This is non-performant
		// because Mojarra/MyFaces will always throw the exception. This bridge implementation avoids this
		// expensive operation because ViewDeclarationLanguageJspImpl wraps the Mojarra/MyFaces
		// implementation in such a way that Mojarra/MyFaces can execute without throwing an exception.
		else {

			// Emulate the JSF 2.x distinction between buildView/renderView.
			_buildView(facesContext, uiViewRoot);
			_renderView(facesContext, uiViewRoot);
		}
	}

	@Override
	public UIViewRoot restoreView(FacesContext facesContext, String viewId) {
		logger.debug("Restoring view for viewId=[{0}]", viewId);

		return super.restoreView(facesContext, viewId);
	}

	protected void _buildView(FacesContext facesContext, UIViewRoot uiViewRoot) {

		// Set a flag on the BridgeContext indicating that JSP AFTER_VIEW_CONTENT processing has been activated. The
		// flag is referenced by {@link ExternalContextImpl#getRequest()} and {@link ExternalContextImpl#getResponse()}
		// and is unset by {@link ExternalContextImpl#dispatch(String)}.
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		bridgeContext.setProcessingAfterViewContent(true);

		logger.debug("Activated JSP AFTER_VIEW_CONTENT feature");
	}

	protected void _renderView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException, FacesException {

		// This code is required by the spec in order to support a JSR 301 legacy feature to support usage of a
		// servlet filter to capture the AFTER_VIEW_CONTENT. In reality it will likely never be used.
		Map<String, Object> attributes = facesContext.getExternalContext().getRequestMap();
		attributes.put(Bridge.RENDER_CONTENT_AFTER_VIEW, Boolean.TRUE);

		try {
			super.renderView(facesContext, uiViewRoot);
		}
		catch (FacesException e) {
			ViewHandlerFactory viewHandlerFactory = (ViewHandlerFactory) BridgeFactoryFinder.getFactory(
					ViewHandlerFactory.class);
			ViewHandler viewHandler = viewHandlerFactory.getViewHandler();
			viewHandler.renderView(facesContext, uiViewRoot);
		}

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

	/**
	 * Mojarra 1.x does not have the ability to process faces-config navigation-rule entries with to-view-id containing
	 * EL-expressions. This method compensates for that shortcoming by evaluating the EL-expression that may be present
	 * in the specified viewId.
	 *
	 * @param   facesContext  The current FacesContext.
	 * @param   viewId        The viewId that may contain an EL expression.
	 *
	 * @return  If an EL-expression was present in the specified viewId, then returns the evaluated expression.
	 *          Otherwise, returns the specified viewId unchanged.
	 */
	protected String evaluateExpressionJSF1(FacesContext facesContext, String viewId) {

		int pos = viewId.indexOf(EL_EXPRESSION_PREFIX);

		if (pos > 0) {
			ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
			ELContext elContext = facesContext.getELContext();
			ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, viewId, String.class);
			viewId = (String) valueExpression.getValue(elContext);

			if ((viewId != null) && !viewId.startsWith(StringPool.FORWARD_SLASH)) {
				viewId = StringPool.FORWARD_SLASH + viewId;
			}
		}

		return viewId;
	}
}
