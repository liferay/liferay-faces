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

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;


/**
 * @author  Neil Griffin
 */
public class RowDeselectRangeEvent extends AjaxBehaviorEvent {

	public static final String ROW_DESELECT_RANGE = "rowDeselectRange";

	// serialVersionUID
	private static final long serialVersionUID = 2311477773355522349L;

	// Private Data Members
	private List<Object> rowDataList;
	private int[] rowIndexes;

	public RowDeselectRangeEvent(UIComponent component, Behavior behavior, int[] rowIndexes, List<Object> rowDataList) {
		super(component, behavior);
		this.rowIndexes = rowIndexes;
		this.rowDataList = rowDataList;
		setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
	}

	public List<Object> getRowDataList() {
		return rowDataList;
	}

	public int[] getRowIndexes() {
		return rowIndexes;
	}
}
