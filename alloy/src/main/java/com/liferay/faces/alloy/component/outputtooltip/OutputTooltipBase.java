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
package com.liferay.faces.alloy.component.outputtooltip;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.outputtext.OutputText;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class OutputTooltipBase extends OutputText implements Styleable, ClientComponent {

	// Protected Enumerations
	protected enum OutputTooltipPropertyKeys {
		autoShow,
		clientKey,
		for_,
		headerText,
		opacity,
		position,
		zIndex
	}

	public Boolean isAutoShow() {
		return (Boolean) getStateHelper().eval(OutputTooltipPropertyKeys.autoShow, null);
	}

	public void setAutoShow(Boolean autoShow) {
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
		return (String) getStateHelper().eval(OutputTooltipPropertyKeys.position, null);
	}

	public void setPosition(String position) {
		getStateHelper().put(OutputTooltipPropertyKeys.position, position);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(OutputTooltipPropertyKeys.zIndex, null);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(OutputTooltipPropertyKeys.zIndex, zIndex);
	}
}
//J+
