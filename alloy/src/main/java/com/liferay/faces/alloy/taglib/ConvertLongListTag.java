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
package com.liferay.faces.alloy.taglib;

import javax.faces.convert.Converter;
import javax.faces.webapp.ConverterELTag;
import javax.servlet.jsp.JspException;

import com.liferay.faces.alloy.converter.LongListConverter;


/**
 * @author  Neil Griffin
 */
public class ConvertLongListTag extends ConverterELTag {

	// serialVersionUID
	private static final long serialVersionUID = 290891524301023584L;

	@Override
	protected Converter createConverter() throws JspException {
		return new LongListConverter();
	}

}
