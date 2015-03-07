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
package com.liferay.faces.alloy.component.tab;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;


/**
 * @author  Neil Griffin
 */
public class TabUtil {

	public static List<Tab> getChildTabs(UIData uiData) {

		List<Tab> childTabs = new ArrayList<Tab>();

		List<UIComponent> children = uiData.getChildren();

		for (UIComponent child : children) {

			if (child instanceof Tab) {
				childTabs.add((Tab) child);
			}
		}

		return childTabs;
	}

	public static Tab getFirstChildTab(UIData uiData) {

		Tab prototypeChildType = null;

		List<Tab> childTabs = getChildTabs(uiData);

		if (childTabs.size() > 0) {
			prototypeChildType = childTabs.get(0);
		}

		return prototypeChildType;
	}
}
