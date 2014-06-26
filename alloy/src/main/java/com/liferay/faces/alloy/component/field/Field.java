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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;

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
	private static final String CONTROL_GROUP = "control-group";
	private static final String ERROR = "error";
	private static final String INFO = "info";
	private static final String WARNING = "warning";
	private static final String SUCCESS = "success";

	public Field() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	protected List<EditableValueHolder> getEditableValueHoldersRecurse(UIComponent uiComponent) {

		List<EditableValueHolder> editableValueHolders = null;

		if (uiComponent instanceof EditableValueHolder) {
			editableValueHolders = new ArrayList<EditableValueHolder>();
			editableValueHolders.add((EditableValueHolder) uiComponent);
		}
		else {
			List<UIComponent> children = uiComponent.getChildren();

			for (UIComponent child : children) {

				List<EditableValueHolder> childEditableValueHolders = getEditableValueHoldersRecurse(child);

				if (childEditableValueHolders != null) {

					if (editableValueHolders == null) {
						editableValueHolders = childEditableValueHolders;
					}
					else {
						editableValueHolders.addAll(childEditableValueHolders);
					}
				}
			}
		}

		return editableValueHolders;
	}

	protected FacesMessage.Severity getHighestMessageSeverityRecurse(FacesContext facesContext, UIComponent uiComponent,
		FacesMessage.Severity severity) {

		List<UIComponent> children = uiComponent.getChildren();

		if (children != null) {

			// For each child component:
			for (UIComponent child : children) {

				// Otherwise, determine the highest severity of the FacesMessages associated with the current child
				// component.
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
					severity = getHighestMessageSeverityRecurse(facesContext, child, severity);
				}
			}
		}

		return severity;
	}

	@Override
	public String getStyleClass() {

		String controlGroupCssClass = CONTROL_GROUP;

		boolean editableValueHoldersExist = false;
		boolean editableValueHoldersValid = false;

		FacesMessage.Severity severity = null;

		List<EditableValueHolder> editableValueHolders = getEditableValueHoldersRecurse(this);

		if (editableValueHolders != null) {

			editableValueHoldersExist = true;

			for (EditableValueHolder editableValueHolder : editableValueHolders) {

				if (editableValueHolder.isValid()) {

					if (editableValueHolder.isRequired() && editableValueHolder.isLocalValueSet()) {
						editableValueHoldersValid = true;
					}
				}
				else {
					editableValueHoldersValid = false;
					severity = FacesMessage.SEVERITY_FATAL;

					break;
				}
			}
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (severity == null) {
			severity = getHighestMessageSeverityRecurse(facesContext, this, null);
		}

		if (severity == null) {

			PartialViewContext partialViewContext = facesContext.getPartialViewContext();

			if (editableValueHoldersExist && editableValueHoldersValid && partialViewContext.isAjaxRequest()) {
				controlGroupCssClass = controlGroupCssClass + StringPool.SPACE + SUCCESS;
			}
		}
		else {

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

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME, controlGroupCssClass);
	}
}
