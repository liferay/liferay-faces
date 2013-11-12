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
package com.liferay.faces.bridge.renderkit.bridge;

import java.io.IOException;

import javax.faces.context.ResponseWriter;


/**
 * @author  Neil Griffin
 */
public abstract class ResponseWriterBridgeCompat_2_2_Impl extends ResponseWriterBridgeCompat_2_0_Impl {

	public ResponseWriterBridgeCompat_2_2_Impl(ResponseWriter wrappedResponseWriter) {
		super(wrappedResponseWriter);
	}

	public void writePreamble(String preamble) throws IOException {
		// No-op for portlets: http://java.net/jira/browse/JAVASERVERFACES_SPEC_PUBLIC-1069
	}

	protected void writeClientWindowHiddenField() throws IOException {
		// No-op for JSF 2.1
	}
}
