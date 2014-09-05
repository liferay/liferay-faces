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
package com.liferay.faces.alloy.component.panel;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.panelgroup.PanelGroupBlockLayout;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PanelBase extends PanelGroupBlockLayout implements Styleable {

	// Protected Enumerations
	protected enum PanelPropertyKeys {
		footerText,
		headerText
	}

	public String getFooterText() {
		return (String) getStateHelper().eval(PanelPropertyKeys.footerText, null);
	}

	public void setFooterText(String footerText) {
		getStateHelper().put(PanelPropertyKeys.footerText, footerText);
	}

	public String getHeaderText() {
		return (String) getStateHelper().eval(PanelPropertyKeys.headerText, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(PanelPropertyKeys.headerText, headerText);
	}
}
//J+
