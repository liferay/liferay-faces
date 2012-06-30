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

/**
 * @author  Neil Griffin
 */
public class BridgeInvalidViewPathException extends BridgeException {

	private static final long serialVersionUID = 8192388822641501588L;

	public BridgeInvalidViewPathException() {
		super();
	}

	public BridgeInvalidViewPathException(String message) {
		super(message);
	}

	public BridgeInvalidViewPathException(Exception e) {
		super(e);
	}

	public BridgeInvalidViewPathException(Throwable cause) {
		super(cause);
	}

	public BridgeInvalidViewPathException(String message, Throwable cause) {
		super(message, cause);
	}
}
