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
package com.liferay.portal;

/**
 * This class is a backport from Liferay 6.0.x that helps to minimize source differences in different branches of
 * Liferay Faces.
 *
 * @author  Brian Wing Shun Chan
 */
public class CompanyMaxUsersException extends PortalException {

	// serialVersionUID
	private static final long serialVersionUID = 7558477840649876196L;

	public CompanyMaxUsersException() {
		super();
	}

	public CompanyMaxUsersException(String msg) {
		super(msg);
	}

	public CompanyMaxUsersException(Throwable cause) {
		super(cause);
	}

	public CompanyMaxUsersException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
