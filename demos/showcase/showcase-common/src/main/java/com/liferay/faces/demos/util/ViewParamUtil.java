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
package com.liferay.faces.demos.util;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.bean.ShowcaseModelBean;
import com.liferay.faces.demos.bean.ShowcaseModelBean.ViewParameters;


/**
 * @author  Neil Griffin
 */
public class ViewParamUtil {

	public static final String getUsage() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ELResolver elResolver = application.getELResolver();
		ELContext elContext = facesContext.getELContext();
		ShowcaseModelBean showcaseModelBean = (ShowcaseModelBean) elResolver.getValue(elContext, null,
				"showcaseModelBean");
		ViewParameters viewParameters = showcaseModelBean.getViewParameters();
		String usage = viewParameters.getComponentUseCase();

		return usage;
	}
}
