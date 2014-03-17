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
package com.liferay.faces.alloy.component.tabview;

import javax.faces.component.FacesComponent;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = TabView.COMPONENT_TYPE)
public class TabView extends TabViewBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.alloy.component.tabview";
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.tabview.TabView";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.tabview.TabViewRenderer";

	public TabView() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public Object getRender() {
		return (Object) getStateHelper().eval(RENDER, true);
	}

	@Override
	public String getSrcNode() {

		String srcNode = super.getSrcNode();

		if (srcNode == null) {
			String defaultValue = StringPool.POUND + ComponentUtil.escapeClientId(getClientId());
			srcNode = (java.lang.String) getStateHelper().eval(SRC_NODE, defaultValue);
		}

		return srcNode;
	}
}
