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
		pattern,
		queryDelay,
		responsive,
		scrollIntoView,
		step,
		timeSelectListener
	}

	public boolean isActivateFirstItem() {
		return (Boolean) getStateHelper().eval(InputTimePropertyKeys.activateFirstItem, true);
	}

	public void setActivateFirstItem(boolean activateFirstItem) {
		getStateHelper().put(InputTimePropertyKeys.activateFirstItem, activateFirstItem);
	}

	public Boolean getCircular() {
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

	public String getPattern() {
		return (String) getStateHelper().eval(InputTimePropertyKeys.pattern, "hh:mm a");
	}

	public void setPattern(String pattern) {
		getStateHelper().put(InputTimePropertyKeys.pattern, pattern);
	}

	public Integer getQueryDelay() {
		return (Integer) getStateHelper().eval(InputTimePropertyKeys.queryDelay, null);
	}

	public void setQueryDelay(Integer queryDelay) {
		getStateHelper().put(InputTimePropertyKeys.queryDelay, queryDelay);
	}

	public boolean isResponsive() {
		return (Boolean) getStateHelper().eval(InputTimePropertyKeys.responsive, true);
	}

	public void setResponsive(boolean responsive) {
		getStateHelper().put(InputTimePropertyKeys.responsive, responsive);
	}

	public Boolean getScrollIntoView() {
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

	public javax.el.MethodExpression getTimeSelectListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputTimePropertyKeys.timeSelectListener, null);
	}

	public void setTimeSelectListener(javax.el.MethodExpression timeSelectListener) {
		getStateHelper().put(InputTimePropertyKeys.timeSelectListener, timeSelectListener);
	}
}
//J+
