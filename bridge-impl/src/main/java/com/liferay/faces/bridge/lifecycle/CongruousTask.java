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
package com.liferay.faces.bridge.lifecycle;

/**
 * @author  Neil Griffin
 */
public enum CongruousTask {
	RESPONSE_FLUSH_BUFFER, RESPONSE_RESET, SET_REQUEST_CHARACTER_ENCODING, SET_RESPONSE_BUFFER_SIZE,
	SET_RESPONSE_CHARACTER_ENCODING, SET_RESPONSE_CONTENT_LENGTH, SET_RESPONSE_CONTENT_TYPE,
	WRITE_RESPONSE_OUTPUT_WRITER, WRITE_RESPONSE_OUTPUT_STREAM
}
