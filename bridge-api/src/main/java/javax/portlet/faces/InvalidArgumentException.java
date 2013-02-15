/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package javax.portlet.faces;

import javax.faces.FacesException;

/**
 * @author Mate Thurzo
 */
public class InvalidArgumentException extends FacesException {

	private static final long serialVersionUID = 8403098127834501588L;

	public InvalidArgumentException() {
		super();
	}

	public InvalidArgumentException(String message) {
		super(message);
	}

	public InvalidArgumentException(Exception e) {
		super(e);
	}

	public InvalidArgumentException(Throwable cause) {
		super(cause);
	}

	public InvalidArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

}
