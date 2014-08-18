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
package com.liferay.faces.alloy.component.inputfile;

import com.liferay.faces.alloy.component.inputfile.internal.InputFileDecoder;
import com.liferay.faces.alloy.component.inputfile.internal.InputFileDecoderCommonsImpl;


/**
 * This class isolates differences between JSF 2.2 and JSF 2.1 in order to minimize diffs across branches.
 *
 * @author  Neil Griffin
 */
public abstract class InputFileRendererCompat extends InputFileRendererBase {

	protected InputFileDecoder getInputFileDecoder() {

		// Since running with JSF 2.1 need to use the Apache Commons-FileUpload method of decoding uploaded files.
		return new InputFileDecoderCommonsImpl();
	}
}
