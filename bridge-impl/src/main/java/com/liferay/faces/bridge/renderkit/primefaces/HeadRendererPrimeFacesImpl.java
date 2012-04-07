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
package com.liferay.faces.bridge.renderkit.primefaces;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.component.ResourceComponent;
import com.liferay.faces.bridge.renderkit.html_basic.HeadRendererBridgeImpl;
import com.liferay.faces.bridge.renderkit.html_basic.HeadResponseWriter;


/**
 * This class replaces the PrimeFaces HeadRenderer because it renders a &lt;head&gt;...&lt;/head&gt; element to the
 * response writer which is forbidden in a portlet environment.
 *
 * @author  Neil Griffin
 */
public class HeadRendererPrimeFacesImpl extends HeadRendererBridgeImpl {

	// Private Constants
	private static final String PRIMEFACES_THEME_DEFAULT = "aristo";
	private static final String PRIMEFACES_THEME_PARAM = "primefaces.THEME";
	private static final String PRIMEFACES_THEME_NONE = "none";
	private static final String PRIMEFACES_THEME_PREFIX = "primefaces-";
	private static final String PRIMEFACES_THEME_RESOURCE_NAME = "theme.css";

	@Override
	protected List<UIComponent> getFirstResources(FacesContext facesContext, UIComponent uiComponent) {

		List<UIComponent> resources = super.getFirstResources(facesContext, uiComponent);

		// PrimeFaces Theme
		ExternalContext externalContext = facesContext.getExternalContext();
		String primeFacesThemeName = externalContext.getInitParameter(PRIMEFACES_THEME_PARAM);

		if (primeFacesThemeName != null) {
			ELContext elContext = facesContext.getELContext();
			ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
			ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, primeFacesThemeName,
					String.class);
			primeFacesThemeName = (String) valueExpression.getValue(elContext);

		}
		else {
			primeFacesThemeName = PRIMEFACES_THEME_DEFAULT;
		}

		if ((primeFacesThemeName != null) && !primeFacesThemeName.equals(PRIMEFACES_THEME_NONE)) {

			if (resources == null) {
				resources = new ArrayList<UIComponent>();
			}

			String resourceLibrary = PRIMEFACES_THEME_PREFIX + primeFacesThemeName;
			ResourceComponent primeFacesStyleSheet = new ResourceComponent(facesContext, PRIMEFACES_THEME_RESOURCE_NAME,
					resourceLibrary, HeadResponseWriter.ELEMENT_HEAD);
			resources.add(primeFacesStyleSheet);
		}

		return resources;
	}
}
