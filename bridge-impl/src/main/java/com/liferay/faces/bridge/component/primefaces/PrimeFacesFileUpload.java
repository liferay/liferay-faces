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
package com.liferay.faces.bridge.component.primefaces;

import java.lang.reflect.Method;

import javax.faces.component.UIInput;

import com.liferay.faces.bridge.component.UIInputWrapper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This is a wrapper around the org.primefaces.component.fileupload.FileUpload component that has decorator methods that
 * call the wrapped component via reflection in order to avoid a compile-time dependency.
 *
 * @author  Neil Griffin
 */
public class PrimeFacesFileUpload extends UIInputWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PrimeFacesFileUpload.class);

	// Public Constants
	public static final String AJAX_FILE_UPLOAD = "ajax.file.upload";
	public static final String MODE_SIMPLE = "simple";
	public static final String RENDERER_TYPE = "org.primefaces.component.FileUploadRenderer";

	// Private Constants
	private static final String METHOD_GET_MODE = "getMode";

	// Private Data Members
	private UIInput wrappedUIInput;

	public PrimeFacesFileUpload(UIInput uiInput) {
		this.wrappedUIInput = uiInput;
	}

	public String getMode() {

		String mode = MODE_SIMPLE;
		Class<?> clazz = wrappedUIInput.getClass();

		try {
			Method method = clazz.getMethod(METHOD_GET_MODE, (Class[]) null);
			mode = (String) method.invoke(wrappedUIInput, (Object[]) null);
		}
		catch (Exception e) {
			logger.error(e);
		}

		return mode;
	}

	@Override
	public UIInput getWrapped() {
		return wrappedUIInput;
	}
}
