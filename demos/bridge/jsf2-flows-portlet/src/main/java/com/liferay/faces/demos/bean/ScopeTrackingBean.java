/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 * @author  Neil Griffin
 */
@Named
@SessionScoped
public class ScopeTrackingBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 8440875513428473371L;

	// Private Data Members
	private boolean bookingFlowModelBeanInScope = false;
	private boolean cartModelBeanInScope = false;
	private boolean flightSearchModelBeanInScope = false;
	private boolean surveyFlowModelBeanInScope = false;

	public void setBookingFlowModelBeanInScope(boolean bookingFlowModelBeanInScope) {
		this.bookingFlowModelBeanInScope = bookingFlowModelBeanInScope;
	}

	public void setCartModelBeanInScope(boolean cartModelBeanInScope) {
		this.cartModelBeanInScope = cartModelBeanInScope;
	}

	public boolean isBookingFlowModelBeanInScope() {
		return bookingFlowModelBeanInScope;
	}

	public boolean isCartModelBeanInScope() {
		return cartModelBeanInScope;
	}

	public boolean isFlightSearchModelBeanInScope() {
		return flightSearchModelBeanInScope;
	}

	public boolean isSurveyFlowModelBeanInScope() {
		return surveyFlowModelBeanInScope;
	}

	public void setFlightSearchModelBeanInScope(boolean flightSearchModelBeanInScope) {
		this.flightSearchModelBeanInScope = flightSearchModelBeanInScope;
	}

	public void setSurveyFlowModelBeanInScope(boolean surveyFlowModelBeanInScope) {
		this.surveyFlowModelBeanInScope = surveyFlowModelBeanInScope;
	}
}
