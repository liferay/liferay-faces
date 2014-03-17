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
package com.liferay.faces.alloy.component.tab;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.model.DataModel;
import javax.faces.render.Renderer;

import com.liferay.faces.alloy.component.tabview.TabView;


/**
 * This is the {@link UIComponent} class associated with the aui:tab component tag. The intended usage is for the
 * developer to specify aui:tab as a child element of aui:tabView. For example:
 *
 * <pre>
    {@code
    <aui:tabView value="#{modelBean.items}" var="item">
        <aui:tab label="#{item.label}" />
    </aui:tabView>
    }
 * </pre>
 *
 * Note that this class ultimately extends {@link UIColumn} because the {@link TabView} class ultimately extends {@link
 * UIData} (which handles children of type {@link UIColumn} in a special manner). In fact, the JavaDoc description for
 * the {@link UIData} class states that _ONLY_ children of type {@link UIColumn} should be processed by associated
 * {@link Renderer} classes. One of the most important benefits of extending {@link UIColumn} is that the {@link
 * UIData#getClientId()} method will append the rowIndex during iteration over a {@link DataModel}, ensuring that each
 * rendered {@link Tab} will have a unique clientId.
 *
 * @author  Neil Griffin
 */
@FacesComponent(value = Tab.COMPONENT_TYPE)
public class Tab extends TabBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.alloy.component.tab";
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.tab.Tab";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.tab.TabRenderer";

	public Tab() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
}
