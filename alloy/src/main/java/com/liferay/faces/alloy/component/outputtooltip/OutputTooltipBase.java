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
package com.liferay.faces.alloy.component.outputtooltip;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.outputtext.OutputTextBase;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class OutputTooltipBase extends OutputTextBase implements Styleable, ClientComponent {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.outputtooltip.OutputTooltip";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.outputtooltip.OutputTooltipRenderer";

	// Protected Enumerations
	protected enum OutputTooltipPropertyKeys {
		autoShow,
		clientKey,
		for_,
		headerText,
		opacity,
		position,
		styleClass,
		zIndex
	}

	public OutputTooltipBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isAutoShow() {
		return (Boolean) getStateHelper().eval(OutputTooltipPropertyKeys.autoShow, false);
	}

	public void setAutoShow(boolean autoShow) {
		getStateHelper().put(OutputTooltipPropertyKeys.autoShow, autoShow);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(OutputTooltipPropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(OutputTooltipPropertyKeys.clientKey, clientKey);
	}

	public String getFor() {
		return (String) getStateHelper().eval(OutputTooltipPropertyKeys.for_, null);
	}

	public void setFor(String for_) {
		getStateHelper().put(OutputTooltipPropertyKeys.for_, for_);
	}

	public String getHeaderText() {
		return (String) getStateHelper().eval(OutputTooltipPropertyKeys.headerText, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(OutputTooltipPropertyKeys.headerText, headerText);
	}

	public String getOpacity() {
		return (String) getStateHelper().eval(OutputTooltipPropertyKeys.opacity, null);
	}

	public void setOpacity(String opacity) {
		getStateHelper().put(OutputTooltipPropertyKeys.opacity, opacity);
	}

	public String getPosition() {
		return (String) getStateHelper().eval(OutputTooltipPropertyKeys.position, "right");
	}

	public void setPosition(String position) {
		getStateHelper().put(OutputTooltipPropertyKeys.position, position);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(OutputTooltipPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(OutputTooltipPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-output-tooltip");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(OutputTooltipPropertyKeys.styleClass, styleClass);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(OutputTooltipPropertyKeys.zIndex, Integer.MIN_VALUE);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(OutputTooltipPropertyKeys.zIndex, zIndex);
	}
}
//J+
