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
package com.liferay.faces.demos.event;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.liferay.faces.demos.bean.ShowcaseModelBean;


/**
 * @author  Neil Griffin
 */
public class ViewParamsPhaseListener implements PhaseListener {
	@Override
	public void afterPhase(PhaseEvent phaseEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String viewState = requestParameterMap.get("javax.faces.ViewState");

		if (viewState == null) {
			ShowcaseModelBean showcaseModelBean = getShowcaseModelBean(facesContext);
			showcaseModelBean.setSelectedComponent(null);

			ShowcaseModelBean.ViewParameters viewParameters = showcaseModelBean.getViewParameters();
			viewParameters.setComponentPrefix(requestParameterMap.get("componentPrefix"));
			viewParameters.setComponentName(requestParameterMap.get("componentName"));
			viewParameters.setComponentUseCase(requestParameterMap.get("componentUseCase"));
		}
	}

	@Override
	public void beforePhase(PhaseEvent phaseEvent) {
		// no-op
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	protected ShowcaseModelBean getShowcaseModelBean(FacesContext facesContext) {

		Application application = facesContext.getApplication();
		ELResolver elResolver = application.getELResolver();
		ELContext elContext = facesContext.getELContext();

		return (ShowcaseModelBean) elResolver.getValue(elContext, null, "showcaseModelBean");
	}
}
