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
package com.liferay.faces.demos.bean;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;


/**
 * @author  Juan Gonzalez
 */
public class PhaseListenerBean implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		InputTextModelBean inputTextModelBean = getInputTextModelBean(event.getFacesContext());
		inputTextModelBean.setText(inputTextModelBean.getText() + "<br/>PhaseListenerBean in afterPhase of " +
			event.getPhaseId());

	}

	@Override
	public void beforePhase(PhaseEvent event) {
		InputTextModelBean inputTextModelBean = getInputTextModelBean(event.getFacesContext());
		inputTextModelBean.setText(inputTextModelBean.getText() + "<br/>PhaseListenerBean in beforePhase of " +
			event.getPhaseId());

	}

	public InputTextModelBean getInputTextModelBean(FacesContext facesContext) {
		ELResolver elResolver = facesContext.getApplication().getELResolver();
		ELContext elContext = facesContext.getELContext();
		InputTextModelBean inputTextModelBean = (InputTextModelBean) elResolver.getValue(elContext, null,
				"inputTextModelBean");

		if (inputTextModelBean.getText() == null) {
			inputTextModelBean.setText("");
		}

		return inputTextModelBean;
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
