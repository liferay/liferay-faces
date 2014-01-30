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
package com.liferay.faces.demos.dto;

import java.io.Serializable;


/**
 * @author  Neil Griffin
 */
public class PaymentType implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 6008653575825839159L;

	// Private Data Members
	private long paymentTypeId;
	private String name;

	public PaymentType(long paymentTypeId, String name) {
		this.paymentTypeId = paymentTypeId;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
}
