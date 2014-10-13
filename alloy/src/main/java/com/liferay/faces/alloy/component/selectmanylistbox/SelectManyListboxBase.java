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
package com.liferay.faces.alloy.component.selectmanylistbox;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlSelectManyListbox;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class SelectManyListboxBase extends HtmlSelectManyListbox implements Styleable {

	// Protected Enumerations
	protected enum SelectManyListboxPropertyKeys {
		responsive
	}

	public boolean isResponsive() {
		return (Boolean) getStateHelper().eval(SelectManyListboxPropertyKeys.responsive, true);
	}

	public void setResponsive(boolean responsive) {
		getStateHelper().put(SelectManyListboxPropertyKeys.responsive, responsive);
	}
}
//J+
