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
package com.liferay.faces.alloy.component;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;

import com.liferay.faces.alloy.component.field.Field;


/**
 * @author  Neil Griffin
 */
public class AlloyComponentUtil {

	public static String getComponentLabel(UIComponent uiComponent) {

		String componentLabel = null;

		if (uiComponent != null) {

			componentLabel = getParentFieldLabelValue(uiComponent);

			if (componentLabel == null) {
				componentLabel = getSiblingLabelValue((uiComponent));
			}
		}

		return componentLabel;
	}

	private static String getParentFieldLabelValue(UIComponent uiComponent) {

		String parentFieldLabel = null;

		if (uiComponent != null) {
			UIComponent parent = uiComponent.getParent();

			if (parent != null) {

				if (parent instanceof Field) {

					Field parentField = (Field) parent;
					parentFieldLabel = parentField.getLabel();
				}
				else {
					parentFieldLabel = getParentFieldLabelValue(parent);
				}
			}
		}

		return parentFieldLabel;
	}

	private static String getSiblingLabelValue(UIComponent uiComponent) {

		String siblingLabelValue = null;

		if (uiComponent != null) {

			UIComponent parent = uiComponent.getParent();

			if (parent != null) {

				List<UIComponent> children = parent.getChildren();

				if (children != null) {

					for (UIComponent child : children) {

						if (child instanceof HtmlOutputLabel) {
							HtmlOutputLabel htmlOutputLabel = (HtmlOutputLabel) child;

							if (uiComponent.getId().equals(htmlOutputLabel.getFor())) {
								Object labelValue = htmlOutputLabel.getValue();

								if (labelValue != null) {
									siblingLabelValue = labelValue.toString();
								}
							}
						}
					}
				}
			}
		}

		return siblingLabelValue;
	}
}
