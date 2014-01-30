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
package javax.portlet.faces;

/**
 * @author  Neil Griffin
 */
public class BridgeUninitializedException extends BridgeException {

	private static final long serialVersionUID = 5011392390692503483L;

	public BridgeUninitializedException() {
		super();
	}

	public BridgeUninitializedException(String message) {
		super(message);
	}

	public BridgeUninitializedException(Exception e) {
		super(e);
	}

	public BridgeUninitializedException(Throwable cause) {
		super(cause);
	}

	public BridgeUninitializedException(String message, Throwable cause) {
		super(message, cause);
	}
}
