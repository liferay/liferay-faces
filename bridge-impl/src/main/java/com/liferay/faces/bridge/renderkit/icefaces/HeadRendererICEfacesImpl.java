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
package com.liferay.faces.bridge.renderkit.icefaces;

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
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class HeadRendererICEfacesImpl extends HeadRendererBridgeImpl {

	// Private Constants
	private static final String ICEFACES_LIBRARY_NAME_ACE = "icefaces.ace";
	private static final String ICEFACES_THEME_NAME_SAM = "sam";
	private static final String ICEFACES_THEME_NAME_RIME = "rime";
	private static final String ICEFACES_THEME_DEFAULT = ICEFACES_THEME_NAME_SAM;
	private static final String ICEFACES_THEME_PARAM = "org.icefaces.ace.theme";
	private static final String ICEFACES_THEME_NONE = "none";
	private static final String ICEFACES_THEME_PREFIX = "ace-";
	private static final String ICEFACES_THEME_RESOURCE_NAME = "theme.css";
	private static final String ICEFACES_THEME_DIR = "themes";

	@Override
	protected List<UIComponent> getFirstResources(FacesContext facesContext, UIComponent uiComponent) {

		List<UIComponent> resources = super.getFirstResources(facesContext, uiComponent);

		// ICEfaces Theme
		ExternalContext externalContext = facesContext.getExternalContext();
		String iceFacesThemeName = externalContext.getInitParameter(ICEFACES_THEME_PARAM);

		if (iceFacesThemeName != null) {
			ELContext elContext = facesContext.getELContext();
			ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
			ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, iceFacesThemeName,
					String.class);
			iceFacesThemeName = (String) valueExpression.getValue(elContext);

		}
		else {
			iceFacesThemeName = ICEFACES_THEME_DEFAULT;
		}

		if ((iceFacesThemeName != null) && !iceFacesThemeName.equals(ICEFACES_THEME_NONE)) {

			if (resources == null) {
				resources = new ArrayList<UIComponent>();
			}

			String resourceName = ICEFACES_THEME_RESOURCE_NAME;
			String resourceLibrary = ICEFACES_THEME_PREFIX + iceFacesThemeName;

			if (iceFacesThemeName.equals(ICEFACES_THEME_NAME_SAM) ||
					iceFacesThemeName.equals(ICEFACES_THEME_NAME_RIME)) {
				StringBuilder buf = new StringBuilder();
				buf.append(ICEFACES_THEME_DIR);
				buf.append(StringPool.FORWARD_SLASH);
				buf.append(iceFacesThemeName);
				buf.append(StringPool.FORWARD_SLASH);
				buf.append(ICEFACES_THEME_RESOURCE_NAME);
				resourceName = buf.toString();
				resourceLibrary = ICEFACES_LIBRARY_NAME_ACE;
			}

			ResourceComponent iceFacesStyleSheet = new ResourceComponent(facesContext, resourceName, resourceLibrary,
					HeadResponseWriter.ELEMENT_HEAD);
			resources.add(iceFacesStyleSheet);
		}

		return resources;
	}
}
