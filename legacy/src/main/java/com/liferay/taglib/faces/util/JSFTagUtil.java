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
package com.liferay.taglib.faces.util;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.liferay.portal.kernel.util.Validator;


/**
 * @deprecated  This class has been deprecated as of Liferay Faces 2.2 and will not appear in future versions.
 * @author      Neil Griffin
 */
@Deprecated
public class JSFTagUtil {

	public static String eval(String expr) {

		if (Validator.isNotNull(expr) && javax.faces.webapp.UIComponentTag.isValueReference(expr)) {

			FacesContext facesContext = FacesContext.getCurrentInstance();

			Application application = facesContext.getApplication();
			javax.faces.el.ValueBinding valueBinding = application.createValueBinding(expr);

			expr = String.valueOf(valueBinding.getValue(facesContext));
		}

		return expr;
	}

}
