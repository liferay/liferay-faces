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
package javax.portlet.faces;

import javax.faces.FacesException;


/**
 * @author  Neil Griffin
 */
public class BridgeException extends FacesException {

	private static final long serialVersionUID = 5803855762197476832L;

	public BridgeException() {
		super();
	}

	public BridgeException(String message) {
		super(message);
	}

	public BridgeException(Exception e) {
		super(e);
	}

	public BridgeException(Throwable cause) {
		super(cause);
	}

	public BridgeException(String message, Throwable cause) {
		super(message, cause);
	}
}
