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
package com.liferay.faces.alloy.component.datatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;

import com.liferay.faces.util.helper.IntegerHelper;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = DataTable.COMPONENT_TYPE)
public class DataTable extends DataTableBase implements ClientBehaviorHolder {

	// Private Constants
	private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList(
				RowSelectEvent.ROW_SELECT, RowSelectRangeEvent.ROW_SELECT_RANGE, RowDeselectEvent.ROW_DESELECT,
				RowDeselectRangeEvent.ROW_DESELECT_RANGE));

	@Override
	public void queueEvent(FacesEvent facesEvent) {

		// This method is called by the AjaxBehavior renderer's decode() method. If the specified event is an ajax
		// behavior event, then
		if (facesEvent instanceof AjaxBehaviorEvent) {

			// Determine the client-side state of the selected row index.
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
			String eventName = requestParameterMap.get("javax.faces.behavior.event");

			// If the AjaxBehaviorEvent indicates a row being selected/deselected, then
			if (RowSelectEvent.ROW_SELECT.equals(eventName) || RowDeselectEvent.ROW_DESELECT.equals(eventName)) {

				// Queue a row selection/deselection event rather than the specified faces event.
				AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) facesEvent;
				Behavior behavior = behaviorEvent.getBehavior();
				String clientId = getClientId(facesContext);
				int originalRowIndex = getRowIndex();
				int selectedRowIndex = IntegerHelper.toInteger(requestParameterMap.get(clientId + "_rowIndex"));
				setRowIndex(selectedRowIndex);

				Object rowData = getRowData();
				setRowIndex(originalRowIndex);

				if (RowSelectEvent.ROW_SELECT.equals(eventName)) {
					facesEvent = new RowSelectEvent(this, behavior, selectedRowIndex, rowData);
				}
				else {
					facesEvent = new RowDeselectEvent(this, behavior, selectedRowIndex, rowData);
				}
			}

			// If the AjaxBehaviorEvent indicates a row range being selected/deselected, then
			else if (RowSelectRangeEvent.ROW_SELECT_RANGE.equals(eventName) ||
					RowDeselectRangeEvent.ROW_DESELECT_RANGE.equals(eventName)) {

				// Queue a row range selection/deselection event rather than the specified faces event.
				AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) facesEvent;
				Behavior behavior = behaviorEvent.getBehavior();
				String clientId = getClientId(facesContext);
				String rowIndexRange = requestParameterMap.get(clientId + "_rowIndexRange");
				int[] rowIndexArray = toIntArray(rowIndexRange);

				if (RowSelectRangeEvent.ROW_SELECT_RANGE.equals(eventName)) {
					facesEvent = new RowSelectRangeEvent(this, behavior, rowIndexArray, getRowDataList(rowIndexArray));
				}
				else {
					facesEvent = new RowDeselectRangeEvent(this, behavior, rowIndexArray,
							getRowDataList(rowIndexArray));
				}
			}
		}

		super.queueEvent(facesEvent);
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		super.restoreState(context, state);
		getAttributes().put("oldRows", getRows());
	}

	public final int[] toIntArray(String commaDelimitedValue) {

		int[] intArray = null;

		if ((commaDelimitedValue != null) && (commaDelimitedValue.length() > 0)) {

			String[] stringArray = commaDelimitedValue.split(",");
			intArray = new int[stringArray.length];

			for (int i = 0; i < stringArray.length; i++) {

				try {
					intArray[i] = Integer.parseInt(stringArray[i]);
				}
				catch (NumberFormatException e) {
					throw new FacesException(e);
				}
			}
		}

		return intArray;
	}

	@Override
	public String getDefaultEventName() {
		return RowSelectEvent.ROW_SELECT;
	}

	@Override
	public Collection<String> getEventNames() {
		return EVENT_NAMES;
	}

	public List<Object> getRowDataList(int[] rowIndexes) {

		List<Object> rowDataList = null;

		if (rowIndexes != null) {

			int originalRowIndex = getRowIndex();
			rowDataList = new ArrayList<Object>(rowIndexes.length);

			for (int rowIndex : rowIndexes) {
				setRowIndex(rowIndex);
				rowDataList.add(getRowData());
			}

			setRowIndex(originalRowIndex);
		}

		return rowDataList;
	}

	@Override
	public void setValueExpression(String name, ValueExpression binding) {
		super.setValueExpression(name, binding);
	}
}
