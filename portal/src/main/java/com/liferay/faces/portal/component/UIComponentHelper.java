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
package com.liferay.faces.portal.component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


/**
 * Utility methods that operate against UIComponent instances.
 *
 * @author  Neil Griffin
 */
public class UIComponentHelper {

	public static Object getLabel(FacesContext facesContext, UIComponent uiComponent) {

		Object label = null;

		if (uiComponent != null) {

			label = uiComponent.getAttributes().get("label");

			if (label == null) {
				label = uiComponent.getValueExpression("label");
			}

			if (label == null) {
				label = uiComponent.getClientId(facesContext);
			}
		}

		return label;
	}
}
