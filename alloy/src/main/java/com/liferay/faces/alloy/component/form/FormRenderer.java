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
package com.liferay.faces.alloy.component.form;

import java.io.IOException;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.DelegationResponseWriter;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = Form.COMPONENT_FAMILY, rendererType = Form.RENDERER_TYPE)
//J+
public class FormRenderer extends FormRendererBase {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Form form = (Form) uiComponent;
		
		if (form.isIncludeViewParams()) {
			
			UIViewRoot uiViewRoot = facesContext.getViewRoot();
			String viewId = uiViewRoot.getViewId();
			Application application = facesContext.getApplication();
			ViewHandler viewHandler = application.getViewHandler();
			String bookmarkableURL = viewHandler.getBookmarkableURL(facesContext, viewId, null, true);
			String actionURL = viewHandler.getActionURL(facesContext, viewId);
			int questionMarkPos = bookmarkableURL.indexOf(StringPool.QUESTION);
			if (questionMarkPos > 0) {
				actionURL += bookmarkableURL.substring(questionMarkPos);
			}
			ExternalContext externalContext = facesContext.getExternalContext();
			String encodedActionURL = externalContext.encodeActionURL(actionURL);
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			DelegationResponseWriter delegationResponseWriter = new FormResponseWriter(responseWriter, encodedActionURL);
			super.encodeBegin(facesContext, uiComponent, delegationResponseWriter);
		}
		else {
			super.encodeBegin(facesContext, uiComponent);
		}
	}

	@Override
	public String getDelegateComponentFamily() {
		return Form.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return Form.DELEGATE_RENDERER_TYPE;
	}
}
