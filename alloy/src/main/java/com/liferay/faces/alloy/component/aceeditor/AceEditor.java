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
package com.liferay.faces.alloy.component.aceeditor;

import javax.faces.component.FacesComponent;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = AceEditor.COMPONENT_TYPE)
public class AceEditor extends AceEditorBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.alloy.component.aceeditor";
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.aceeditor.AceEditor";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.aceeditor.AceEditorRenderer";

	public AceEditor() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getBoundingBox() {

		String boundingBox = super.getBoundingBox();

		if (boundingBox == null) {
			String defaultValue = StringPool.POUND + ComponentUtil.escapeClientId(getClientId());
			boundingBox = (String) getStateHelper().eval(BOUNDING_BOX, defaultValue);
		}

		return boundingBox;
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public Object getRender() {
		return (Object) getStateHelper().eval(RENDER, true);
	}
}
