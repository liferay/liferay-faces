/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIParameter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.liferay.faces.util.context.ExtFacesContext;
import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.LazyDataModel;
import com.liferay.faces.util.model.RowMarks;


/**
 * This class serves as an ActionListener that is designed to listen for user clicks on dataTable tool bar buttons like
 * check-all, uncheck-all, and delete-checked.
 *
 * @author  Neil Griffin
 */
public class RowMarksActionListener implements ActionListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RowMarksActionListener.class);

	// Private Constants
	private static final String PARAM_NAME_FOR = "for";
	private static final String COMP_ID_CHECK_ALL = "check-all";
	private static final String COMP_ID_UNCHECK_ALL = "uncheck-all";
	private static final String COMP_ID_DELETE_CHECKED = "delete-checked";

	public void addCheckAllGlobalInfoMessage(int totalRowsChecked) {
		String messageId = "checked-x-items";
		MessageContext messageContext = MessageContext.getInstance();
		ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
		String message = messageContext.getMessage(extFacesContext.getLocale(), messageId, totalRowsChecked);

		if ((message == null) || message.equals(messageId)) {
			message = MessageFormat.format("Checked {0} items", totalRowsChecked);
		}

		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
		extFacesContext.addMessage(null, facesMessage);
	}

	public void addDeletedGlobalInfoMessage(int totalDeletedRows) {
		String messageId = "deleted-x-items";
		MessageContext messageContext = MessageContext.getInstance();
		ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
		String message = messageContext.getMessage(extFacesContext.getLocale(), messageId, totalDeletedRows);

		if ((message == null) || message.equals(messageId)) {
			message = MessageFormat.format("Deleted {0} items", totalDeletedRows);
		}

		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
		extFacesContext.addMessage(null, facesMessage);
	}

	public void addUncheckAllGlobalInfoMessage() {
		String messageId = "checked-all-items";
		MessageContext messageContext = MessageContext.getInstance();
		ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
		String message = messageContext.getMessage(extFacesContext.getLocale(), messageId);

		if ((message == null) || message.equals(messageId)) {
			message = "Unchecked all items";
		}

		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
		extFacesContext.addMessage(null, facesMessage);
	}

	public void processAction(ActionEvent actionEvent) throws AbortProcessingException {

		UIComponent uiComponent = actionEvent.getComponent();
		String componentId = uiComponent.getId();

		List<UIComponent> uiComponentChildren = uiComponent.getChildren();
		String forClientId = null;
		LazyDataModel<?> lazyDataModel = null;

		for (UIComponent uiComponentChild : uiComponentChildren) {

			if (uiComponentChild instanceof UIParameter) {
				UIParameter uiParameter = (UIParameter) uiComponentChild;
				String uiParameterName = uiParameter.getName();

				if ((uiParameterName != null) && uiParameterName.equals(PARAM_NAME_FOR)) {
					forClientId = (String) uiParameter.getValue();
					lazyDataModel = getLazyDataModel(uiComponent, forClientId);
				}
			}
		}

		if (lazyDataModel != null) {

			RowMarks rowMarks = lazyDataModel.getRowMarks();

			if (rowMarks != null) {

				if (componentId.equals(COMP_ID_CHECK_ALL) || componentId.equals(COMP_ID_UNCHECK_ALL)) {
					boolean checked = componentId.equals(COMP_ID_CHECK_ALL);

					if (checked) {
						rowMarks.markAll();
						addCheckAllGlobalInfoMessage(rowMarks.size());
					}
					else {
						rowMarks.unmarkAll();
						addUncheckAllGlobalInfoMessage();
					}
				}
				else if (componentId.equals(COMP_ID_DELETE_CHECKED)) {

					if (lazyDataModel != null) {
						Set<Entry<Object, Boolean>> entrySet = rowMarks.entrySet();
						Iterator<Map.Entry<Object, Boolean>> iterator = entrySet.iterator();

						int totalDeletedRows = 0;

						while (iterator.hasNext()) {
							Map.Entry<Object, Boolean> mapEntry = iterator.next();

							if (mapEntry.getValue()) {

								try {
									logger.debug("Asking LazyDataModle to delete row with key=[{}]", mapEntry.getKey());
									lazyDataModel.deleteRow(mapEntry.getKey());
									totalDeletedRows++;
								}
								catch (Exception e) {
									logger.error(e.getMessage(), e);
									throw new AbortProcessingException(e.getMessage());
								}
							}
						}

						if (totalDeletedRows > 0) {
							logger.debug("Deleted one or more rows, so asking LazyDataModel to clear cache");
							lazyDataModel.setRowCount(-1);
							lazyDataModel.setWrappedData(null);
							lazyDataModel.setWrappedDataStartRowIndex(-1);
							lazyDataModel.setWrappedDataFinishRowIndex(-1);
							lazyDataModel.setRowMarks(null);
							addDeletedGlobalInfoMessage(totalDeletedRows);
						}
					}
				}
			}
		}
	}

	protected LazyDataModel<?> getLazyDataModel(UIComponent uiComponentInView, String forClientId) {

		LazyDataModel<?> lazyDataModel = null;

		if (forClientId != null) {
			ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
			UIComponent forUIComponent = extFacesContext.matchComponentInViewRoot(forClientId);

			if (forUIComponent != null) {

				if (forUIComponent instanceof UIData) {
					UIData uiData = (UIData) forUIComponent;
					Object uiDataValue = uiData.getValue();

					if (uiDataValue != null) {

						if (uiDataValue instanceof LazyDataModel) {
							lazyDataModel = (LazyDataModel<?>) uiDataValue;
						}
						else {
							logger.error("The 'value' attribute for the component [{}] is not a subclass of [{}]",
								uiData.getClass().getName(), LazyDataModel.class.getName());
						}
					}
					else {
						logger.error("The 'value' attribute is not set for the component [{}]",
							uiData.getClass().getName());
					}
				}
				else {
					logger.error(
						"The value 'for' attribute of the checkToolBar is [{}] but the corresponding component in the view [{}] is not a subclass of javax.faces.component.UIData and so the javax.faces.model.DataModel can't be determined.",
						forClientId, forUIComponent.getClass().getName());
				}
			}
			else {
				logger.error(
					"The value 'for' attribute of the checkToolBar is [{}] but there is no component in the view with that clientId",
					forClientId);
			}
		}
		else {
			logger.error("There was no value specified for the 'for' attribute of the checkToolbar tag");
		}

		return lazyDataModel;
	}

}
