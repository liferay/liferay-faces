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

import java.math.BigDecimal;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.liferay.faces.demos.dto.Booking;


/**
 * @author  Neil Griffin
 */
@Named
@ViewScoped
public class CartModelBean {

	@Inject
	BookingFlowModelBean bookingFlowModelBean;

	// Private Data Members
	private BigDecimal totalPrice;

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
