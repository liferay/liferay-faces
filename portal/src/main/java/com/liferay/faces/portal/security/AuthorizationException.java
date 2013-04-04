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
package com.liferay.faces.portal.security;

/**
 * @author  Neil Griffin
 */
public class AuthorizationException extends RuntimeException {

	// serialVersionUID
	private static final long serialVersionUID = 5578098108378245889L;

	public AuthorizationException() {
	}

	public AuthorizationException(String arg0) {
		super(arg0);
	}

	public AuthorizationException(Throwable arg0) {
		super(arg0);
	}

	public AuthorizationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
