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
package com.liferay.faces.alloy.component.inputtime;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.inputdatetime.InputDateTime;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputTimeBase extends InputDateTime implements Styleable, ClientComponent {

	// Protected Enumerations
	protected enum InputTimePropertyKeys {
		activateFirstItem,
		circular,
		filterType,
		height,
		highlighterType,
		maxResults,
		maxTime,
		minTime,
		queryDelay,
		scrollIntoView,
		step,
		timePattern,
		timeSelectListener
	}

	public Boolean isActivateFirstItem() {
		return (Boolean) getStateHelper().eval(InputTimePropertyKeys.activateFirstItem, true);
	}

	public void setActivateFirstItem(Boolean activateFirstItem) {
		getStateHelper().put(InputTimePropertyKeys.activateFirstItem, activateFirstItem);
	}

	public Boolean isCircular() {
		return (Boolean) getStateHelper().eval(InputTimePropertyKeys.circular, null);
	}

	public void setCircular(Boolean circular) {
		getStateHelper().put(InputTimePropertyKeys.circular, circular);
	}

	public String getFilterType() {
		return (String) getStateHelper().eval(InputTimePropertyKeys.filterType, null);
	}

	public void setFilterType(String filterType) {
		getStateHelper().put(InputTimePropertyKeys.filterType, filterType);
	}

	public String getHeight() {
		return (String) getStateHelper().eval(InputTimePropertyKeys.height, null);
	}

	public void setHeight(String height) {
		getStateHelper().put(InputTimePropertyKeys.height, height);
	}

	public String getHighlighterType() {
		return (String) getStateHelper().eval(InputTimePropertyKeys.highlighterType, null);
	}

	public void setHighlighterType(String highlighterType) {
		getStateHelper().put(InputTimePropertyKeys.highlighterType, highlighterType);
	}

	public Integer getMaxResults() {
		return (Integer) getStateHelper().eval(InputTimePropertyKeys.maxResults, null);
	}

	public void setMaxResults(Integer maxResults) {
		getStateHelper().put(InputTimePropertyKeys.maxResults, maxResults);
	}

	public String getMaxTime() {
		return (String) getStateHelper().eval(InputTimePropertyKeys.maxTime, "23:59:59");
	}

	public void setMaxTime(String maxTime) {
		getStateHelper().put(InputTimePropertyKeys.maxTime, maxTime);
	}

	public String getMinTime() {
		return (String) getStateHelper().eval(InputTimePropertyKeys.minTime, "00:00:00");
	}

	public void setMinTime(String minTime) {
		getStateHelper().put(InputTimePropertyKeys.minTime, minTime);
	}

	public Integer getQueryDelay() {
		return (Integer) getStateHelper().eval(InputTimePropertyKeys.queryDelay, null);
	}

	public void setQueryDelay(Integer queryDelay) {
		getStateHelper().put(InputTimePropertyKeys.queryDelay, queryDelay);
	}

	public Boolean isScrollIntoView() {
		return (Boolean) getStateHelper().eval(InputTimePropertyKeys.scrollIntoView, null);
	}

	public void setScrollIntoView(Boolean scrollIntoView) {
		getStateHelper().put(InputTimePropertyKeys.scrollIntoView, scrollIntoView);
	}

	public Integer getStep() {
		return (Integer) getStateHelper().eval(InputTimePropertyKeys.step, 3600);
	}

	public void setStep(Integer step) {
		getStateHelper().put(InputTimePropertyKeys.step, step);
	}

	public String getTimePattern() {
		return (String) getStateHelper().eval(InputTimePropertyKeys.timePattern, "hh:mm a");
	}

	public void setTimePattern(String timePattern) {
		getStateHelper().put(InputTimePropertyKeys.timePattern, timePattern);
	}

	public javax.el.MethodExpression getTimeSelectListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputTimePropertyKeys.timeSelectListener, null);
	}

	public void setTimeSelectListener(javax.el.MethodExpression timeSelectListener) {
		getStateHelper().put(InputTimePropertyKeys.timeSelectListener, timeSelectListener);
	}
}
//J+
