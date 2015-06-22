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
package com.liferay.faces.alloy.component.progressbar;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ProgressBarBase extends UIComponentBase implements Styleable, ClientComponent {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.progressbar.ProgressBar";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.progressbar.ProgressBarRenderer";

	// Protected Enumerations
	protected enum ProgressBarPropertyKeys {
		clientKey,
		height,
		label,
		layout,
		maxProgress,
		minProgress,
		oncomplete,
		pollingDelay,
		style,
		styleClass,
		value,
		width
	}

	public ProgressBarBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(ProgressBarPropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(ProgressBarPropertyKeys.clientKey, clientKey);
	}

	public String getHeight() {
		return (String) getStateHelper().eval(ProgressBarPropertyKeys.height, null);
	}

	public void setHeight(String height) {
		getStateHelper().put(ProgressBarPropertyKeys.height, height);
	}

	public String getLabel() {
		return (String) getStateHelper().eval(ProgressBarPropertyKeys.label, null);
	}

	public void setLabel(String label) {
		getStateHelper().put(ProgressBarPropertyKeys.label, label);
	}

	public String getLayout() {
		return (String) getStateHelper().eval(ProgressBarPropertyKeys.layout, null);
	}

	public void setLayout(String layout) {
		getStateHelper().put(ProgressBarPropertyKeys.layout, layout);
	}

	public Integer getMaxProgress() {
		return (Integer) getStateHelper().eval(ProgressBarPropertyKeys.maxProgress, null);
	}

	public void setMaxProgress(Integer maxProgress) {
		getStateHelper().put(ProgressBarPropertyKeys.maxProgress, maxProgress);
	}

	public Integer getMinProgress() {
		return (Integer) getStateHelper().eval(ProgressBarPropertyKeys.minProgress, null);
	}

	public void setMinProgress(Integer minProgress) {
		getStateHelper().put(ProgressBarPropertyKeys.minProgress, minProgress);
	}

	public String getOncomplete() {
		return (String) getStateHelper().eval(ProgressBarPropertyKeys.oncomplete, null);
	}

	public void setOncomplete(String oncomplete) {
		getStateHelper().put(ProgressBarPropertyKeys.oncomplete, oncomplete);
	}

	public Integer getPollingDelay() {
		return (Integer) getStateHelper().eval(ProgressBarPropertyKeys.pollingDelay, 3000);
	}

	public void setPollingDelay(Integer pollingDelay) {
		getStateHelper().put(ProgressBarPropertyKeys.pollingDelay, pollingDelay);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(ProgressBarPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(ProgressBarPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(ProgressBarPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(ProgressBarPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-progress-bar");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(ProgressBarPropertyKeys.styleClass, styleClass);
	}

	public Integer getValue() {
		return (Integer) getStateHelper().eval(ProgressBarPropertyKeys.value, 0);
	}

	public void setValue(Integer value) {
		getStateHelper().put(ProgressBarPropertyKeys.value, value);
	}

	public String getWidth() {
		return (String) getStateHelper().eval(ProgressBarPropertyKeys.width, null);
	}

	public void setWidth(String width) {
		getStateHelper().put(ProgressBarPropertyKeys.width, width);
	}
}
//J+
