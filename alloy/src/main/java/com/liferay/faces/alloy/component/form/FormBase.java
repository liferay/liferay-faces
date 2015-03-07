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
package com.liferay.faces.alloy.component.form;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlForm;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class FormBase extends HtmlForm implements Styleable {

	// Protected Enumerations
	protected enum FormPropertyKeys {
		includeViewParams
	}

	public boolean isIncludeViewParams() {
		return (Boolean) getStateHelper().eval(FormPropertyKeys.includeViewParams, false);
	}

	public void setIncludeViewParams(boolean includeViewParams) {
		getStateHelper().put(FormPropertyKeys.includeViewParams, includeViewParams);
	}
}
//J+
