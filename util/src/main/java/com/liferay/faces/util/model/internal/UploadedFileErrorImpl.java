/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.model.internal;

/**
 * @author  Neil Griffin
 */
public class UploadedFileErrorImpl extends UploadedFileImpl {

	// serialVersionUID
	private static final long serialVersionUID = 2524509050866448224L;

	public UploadedFileErrorImpl(String message) {
		this(message, Status.ERROR);
	}

	public UploadedFileErrorImpl(String message, Status status) {
		super(null, null, null, null, null, null, message, null, 0L, status);
	}
}
