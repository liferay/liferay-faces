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
package com.liferay.faces.alloy.component.link;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlOutcomeTargetLink;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class LinkBase extends HtmlOutcomeTargetLink implements Styleable {

	// Protected Enumerations
	protected enum LinkPropertyKeys {
		disabled,
		type
	}

	public boolean isDisabled() {
		return (Boolean) getStateHelper().eval(LinkPropertyKeys.disabled, false);
	}

	public void setDisabled(boolean disabled) {
		getStateHelper().put(LinkPropertyKeys.disabled, disabled);
	}

	public String getType() {
		return (String) getStateHelper().eval(LinkPropertyKeys.type, null);
	}

	public void setType(String type) {
		getStateHelper().put(LinkPropertyKeys.type, type);
	}
}
//J+
