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
package com.liferay.faces.alloy.component.accordion;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;

import com.liferay.faces.alloy.component.tab.TabCollapseEvent;
import com.liferay.faces.alloy.component.tab.TabExpandEvent;


/**
 * @author  Neil Griffin
 */
public class AccordionBehaviorListener implements AjaxBehaviorListener {

	@Override
	public void processAjaxBehavior(AjaxBehaviorEvent ajaxBehaviorEvent) throws AbortProcessingException {

		MethodExpression methodExpression = null;

		if (ajaxBehaviorEvent instanceof TabCollapseEvent) {
			TabCollapseEvent tabCollapseEvent = (TabCollapseEvent) ajaxBehaviorEvent;
			Accordion accordion = (Accordion) tabCollapseEvent.getComponent();
			methodExpression = accordion.getTabCollapseListener();
		}
		else if (ajaxBehaviorEvent instanceof TabExpandEvent) {
			TabExpandEvent tabExpandEvent = (TabExpandEvent) ajaxBehaviorEvent;
			Accordion accordion = (Accordion) tabExpandEvent.getComponent();
			methodExpression = accordion.getTabExpandListener();
		}

		if (methodExpression != null) {

			try {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				methodExpression.invoke(facesContext.getELContext(), new Object[] { ajaxBehaviorEvent });
			}
			catch (Exception e) {
				throw new AbortProcessingException(e);
			}
		}
	}
}
