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
package com.liferay.faces.alloy.component.form.internal;

import java.io.IOException;
import java.util.Collection;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.view.ViewMetadata;

import com.liferay.faces.alloy.component.form.Form;
import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = Form.COMPONENT_FAMILY, rendererType = Form.RENDERER_TYPE)
//J+
public class FormRenderer extends FormRendererBase {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Form form = (Form) uiComponent;

		if (form.isIncludeViewParams()) {

			Application application = facesContext.getApplication();
			ViewHandler viewHandler = application.getViewHandler();
			UIViewRoot uiViewRoot = facesContext.getViewRoot();
			String viewId = uiViewRoot.getViewId();
			String actionURL = viewHandler.getActionURL(facesContext, viewId);
			StringBuilder actionURLBuilder = new StringBuilder(actionURL);
			Collection<UIViewParameter> viewParameters = ViewMetadata.getViewParameters(uiViewRoot);

			if (viewParameters != null) {

				boolean firstParam = true;

				for (UIViewParameter uiViewParameter : viewParameters) {

					String viewParameterName = uiViewParameter.getName();

					if (viewParameterName != null) {

						if (firstParam) {
							actionURLBuilder.append("?");
							firstParam = false;
						}
						else {
							actionURLBuilder.append("&");
						}

						actionURLBuilder.append(viewParameterName);
						actionURLBuilder.append("=");

						String viewParameterValue = null;
						ValueExpression valueExpression = uiViewParameter.getValueExpression("value");

						if (valueExpression != null) {
							viewParameterValue = uiViewParameter.getStringValueFromModel(facesContext);
						}

						if (viewParameterValue == null) {
							viewParameterValue = uiViewParameter.getStringValue(facesContext);
						}

						if (viewParameterValue != null) {
							actionURLBuilder.append(viewParameterValue);
						}
					}
				}
			}

			ExternalContext externalContext = facesContext.getExternalContext();
			String encodedActionURL = externalContext.encodeActionURL(actionURLBuilder.toString());
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			DelegationResponseWriter delegationResponseWriter = new FormResponseWriter(responseWriter,
					encodedActionURL);
			super.encodeBegin(facesContext, uiComponent, delegationResponseWriter);
		}
		else {
			super.encodeBegin(facesContext, uiComponent);
		}
	}
}
