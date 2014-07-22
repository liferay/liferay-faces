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
package com.liferay.faces.alloy.component.accordion;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;


/**
 * @author  Neil Griffin
 */
public class AccordionBehaviorListener implements AjaxBehaviorListener {

	@Override
	public void processAjaxBehavior(AjaxBehaviorEvent ajaxBehaviorEvent) throws AbortProcessingException {

		AccordionTabEvent accordionTabEvent = (AccordionTabEvent) ajaxBehaviorEvent;
		Accordion accordion = (Accordion) accordionTabEvent.getComponent();

		try {
			MethodExpression methodExpression = accordion.getTabEventListener();

			if (methodExpression != null) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				methodExpression.invoke(facesContext.getELContext(), new Object[] { accordionTabEvent });
			}
		}
		catch (Exception e) {
			throw new AbortProcessingException(e);
		}
	}
}
