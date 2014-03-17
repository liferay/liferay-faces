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
//J-

import javax.annotation.Generated;
import javax.faces.FacesWrapper;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public class TabComponentWrapper implements TabComponent, FacesWrapper<TabComponent> {

	// Private Data Members
	private TabComponent wrappedTabComponent;

	public TabComponentWrapper(TabComponent tabComponent) {
		this.wrappedTabComponent = tabComponent;
	}

	@Override
	public TabComponent getWrapped() {
		return wrappedTabComponent;
	}

	@Override
	public String getLabel() {
		return getWrapped().getLabel();
	}

	@Override
	public void setLabel(String label) {
		getWrapped().setLabel(label);
	}
}
//J+
