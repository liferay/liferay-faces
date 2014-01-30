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
package com.liferay.faces.util.listener;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.DataModel;

import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.el.FaceletUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.RowMarker;
import com.liferay.faces.util.model.RowMarks;


/**
 * This class serves as an ActionListener that is designed to listen for user clicks on dataTable tool bar buttons like
 * check-all, uncheck-all, and delete-checked.
 *
 * @author  Neil Griffin
 */
public class RowMarksActionListener implements ActionListener {

	// Private Constants
	private static final String PARAM_NAME_FOR = "for";
	private static final String COMP_ID_MARK_ALL = "row-marks-mark-all";
	private static final String COMP_ID_UNMARK_ALL = "row-marks-unmark-all";
	private static final String COMP_ID_DELETE = "row-marks-delete";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RowMarksActionListener.class);

	public void processAction(ActionEvent actionEvent) throws AbortProcessingException {

		UIComponent uiComponent = actionEvent.getComponent();
		String componentId = uiComponent.getId();

		List<UIComponent> uiComponentChildren = uiComponent.getChildren();
		String forClientId = null;
		DataModel<?> dataModel = null;

		for (UIComponent uiComponentChild : uiComponentChildren) {

			if (uiComponentChild instanceof UIParameter) {
				UIParameter uiParameter = (UIParameter) uiComponentChild;
				String uiParameterName = uiParameter.getName();

				if ((uiParameterName != null) && uiParameterName.equals(PARAM_NAME_FOR)) {
					forClientId = (String) uiParameter.getValue();
					dataModel = FaceletUtil.findDataModel(forClientId);

					break;
				}
			}
		}

		if (dataModel != null) {

			if (dataModel instanceof RowMarker) {

				RowMarker rowMarker = (RowMarker) dataModel;

				RowMarks rowMarks = rowMarker.getRowMarks();

				if (rowMarks != null) {

					if (componentId.equals(COMP_ID_MARK_ALL) || componentId.equals(COMP_ID_UNMARK_ALL)) {
						boolean checked = componentId.equals(COMP_ID_MARK_ALL);

						if (checked) {
							rowMarks.markAll();
						}
						else {
							rowMarks.unmarkAll();
						}
					}
					else if (componentId.equals(COMP_ID_DELETE)) {

						try {
							int totalDeletedRows = rowMarker.deleteMarkedRows();
							logger.debug("Deleted {0} rows.", totalDeletedRows);
							addGlobalSuccessInfoMessage();
						}
						catch (Exception e) {
							logger.error(e.getMessage(), e);
							addGlobalUnexpectedErrorMessage();
						}
					}
				}
			}
			else {
				logger.error(
					"Unable to perform action for componentId=[{0}] since dataModel=[{1}] does not implement the RowMarker interface.");
			}
		}
	}

	protected void addGlobalSuccessInfoMessage() {

		MessageContext.getInstance().newFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO,
			"your-request-processed-successfully");
	}

	protected void addGlobalUnexpectedErrorMessage() {
		MessageContext.getInstance().newFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO,
			"an-unexpected-error-occurred");
	}
}
