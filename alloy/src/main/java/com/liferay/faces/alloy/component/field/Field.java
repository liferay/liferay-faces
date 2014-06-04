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
package com.liferay.faces.alloy.component.field;

import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = Field.COMPONENT_TYPE)
public class Field extends FieldBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.field.Field";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.field.FieldRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-field";

	// Private Constants
	private static final String BLOCK = "block";
	private static final String CONTROL_GROUP = "control-group";
	private static final String ERROR = "error";
	private static final String INFO = "info";
	private static final String LAYOUT = "layout";
	private static final String WARNING = "warning";

	public Field() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	protected FacesMessage.Severity getHighestSeverityRecurse(UIComponent uiComponent) {
		return getHighestSeverityRecurse(uiComponent, null);
	}

	protected FacesMessage.Severity getHighestSeverityRecurse(UIComponent uiComponent, FacesMessage.Severity severity) {

		if (uiComponent.getChildCount() > 0) {

			List<UIComponent> children = uiComponent.getChildren();
			FacesContext facesContext = FacesContext.getCurrentInstance();

			for (UIComponent child : children) {

				if (child instanceof EditableValueHolder) {

					EditableValueHolder editableValueHolder = (EditableValueHolder) child;

					if (!editableValueHolder.isValid()) {
						severity = FacesMessage.SEVERITY_FATAL;

						break;
					}
				}

				Iterator<FacesMessage> messages = facesContext.getMessages(child.getClientId());

				while (messages.hasNext()) {

					FacesMessage facesMessage = messages.next();
					FacesMessage.Severity currentSeverity = facesMessage.getSeverity();

					if ((currentSeverity == FacesMessage.SEVERITY_ERROR) ||
							(currentSeverity == FacesMessage.SEVERITY_FATAL)) {

						severity = FacesMessage.SEVERITY_FATAL;

						break;
					}
					else if ((severity == null) || (severity.getOrdinal() < currentSeverity.getOrdinal())) {
						severity = currentSeverity;
					}
				}

				if ((severity != null) && (severity == FacesMessage.SEVERITY_FATAL)) {
					break;
				}
				else {
					severity = getHighestSeverityRecurse(child, severity);
				}
			}
		}

		return severity;
	}

	@Override
	public String getLayout() {
		return (String) getStateHelper().eval(LAYOUT, BLOCK);
	}

	@Override
	public void setLayout(String layout) {
		getStateHelper().put(LAYOUT, layout);
	}

	@Override
	public String getStyleClass() {

		String styleClass = (String) getStateHelper().eval(STYLE_CLASS, null);

		String controlGroupCssClass = CONTROL_GROUP;

		FacesMessage.Severity severity = getHighestSeverityRecurse(this);

		if (severity != null) {

			if ((severity == FacesMessage.SEVERITY_FATAL) || (severity == FacesMessage.SEVERITY_ERROR)) {
				controlGroupCssClass = controlGroupCssClass + StringPool.SPACE + ERROR;
			}
			else if (severity == FacesMessage.SEVERITY_WARN) {
				controlGroupCssClass = controlGroupCssClass + StringPool.SPACE + WARNING;
			}
			else if (severity == FacesMessage.SEVERITY_INFO) {
				controlGroupCssClass = controlGroupCssClass + StringPool.SPACE + INFO;
			}
		}

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME, controlGroupCssClass);
	}
}
