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
package com.liferay.faces.demos.service;

import java.util.ArrayList;
import java.util.List;

import com.liferay.faces.demos.dto.PaymentType;


/**
 * @author  Neil Griffin
 */
public class PaymentTypeServiceMockImpl implements PaymentTypeService {

	// Private Data Members
	private List<PaymentType> paymentTypes;

	public PaymentTypeServiceMockImpl() {

		this.paymentTypes = new ArrayList<PaymentType>();
		this.paymentTypes.add(new PaymentType(1, "visa"));
		this.paymentTypes.add(new PaymentType(1, "mastercard"));
		this.paymentTypes.add(new PaymentType(1, "amex"));
		this.paymentTypes.add(new PaymentType(1, "discover"));
		this.paymentTypes.add(new PaymentType(1, "paypal"));
	}

	@Override
	public List<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}
}
