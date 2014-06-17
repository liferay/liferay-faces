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
package com.liferay.faces.alloy.component.selectmanymenu;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import com.liferay.faces.alloy.component.AlloyComponentUtil;
import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = SelectManyMenu.COMPONENT_TYPE)
public class SelectManyMenu extends SelectManyMenuBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.selectmanymenu.SelectManyMenu";
	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String DELEGATE_RENDERER_TYPE = "javax.faces.Menu";
	public static final String RENDERER_TYPE =
		"com.liferay.faces.alloy.component.selectmanymenu.SelectManyMenuRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-select-many-menu";

	public SelectManyMenu() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getLabel() {

		String label = (String) getStateHelper().get(PropertyKeys.label);

		if (label == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();

			if (facesContext.getCurrentPhaseId() == PhaseId.PROCESS_VALIDATIONS) {
				label = AlloyComponentUtil.getComponentLabel(this);
			}
		}

		return label;
	}

	@Override
	public String getStyleClass() {

		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
