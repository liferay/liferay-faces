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
package com.liferay.faces.demos.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.liferay.faces.demos.dto.Booking;


/**
 * @author  Neil Griffin
 */
@Named
@ViewScoped
public class CartModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 6106226342160723375L;

	@Inject
	BookingFlowModelBean bookingFlowModelBean;

	@Inject
	ScopeTrackingBean scopeTrackingBean;

	// Private Data Members
	private BigDecimal totalPrice;

	@PostConstruct
	public void postConstruct() {
		scopeTrackingBean.setCartModelBeanInScope(true);
	}

	@PreDestroy
	public void preDestroy() {
		scopeTrackingBean.setCartModelBeanInScope(false);
	}

	public BigDecimal getTotalPrice() {

		if (totalPrice == null) {

			totalPrice = new BigDecimal(0L);
			totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

			List<Booking> cartBookings = bookingFlowModelBean.getCartBookings();

			for (Booking booking : cartBookings) {
				totalPrice = totalPrice.add(booking.getPrice());
			}
		}

		return totalPrice;
	}
}
