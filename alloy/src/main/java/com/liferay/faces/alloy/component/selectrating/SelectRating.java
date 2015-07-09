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
package com.liferay.faces.alloy.component.selectrating;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ClientComponent;


/**
 * @author  Vernon Singleton
 */
public abstract class SelectRating extends SelectRatingBase implements ClientComponent {

	@Override
	protected void validateValue(FacesContext context, Object value) {

		// AlloyUI sets the initial value of its hidden input to -1. But -1 has been modified by this point in the
		// lifecycle to be the empty string in order to be compatible with JSF. Since the empty string it is not in the
		// list of rating options, it is technically an invalid option. As a workaround, consider an empty string value
		// to be null (which will enable JSF validation processing to proceed).
		if (value instanceof String) {
			String valueString = (String) value;

			if ("".equals(valueString)) {
				value = null;
			}
		}

		super.validateValue(context, value);
	}
}
