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
package com.liferay.taglib.faces.converter;

import javax.faces.convert.Converter;
import javax.faces.webapp.ConverterTag;
import javax.servlet.jsp.JspException;

import com.liferay.taglib.faces.util.JSFTagUtil;


/**
 * @deprecated  This class has been deprecated as of Liferay Faces 2.2 and will not appear in future versions.
 * @author      Neil Griffin
 */
@Deprecated
public class PhoneNumberConverterTag extends ConverterTag {

	// serialVersionUID
	private static final long serialVersionUID = 8545894351668615425L;

	// Private Data Members
	private String unitedStatesFormat = "(###) ###-####";

	public PhoneNumberConverterTag() {
		setConverterId(PhoneNumberConverter.class.getName());
	}

	@Override
	public void release() {
		unitedStatesFormat = null;
	}

	@Override
	protected Converter createConverter() throws JspException {
		PhoneNumberConverter converter = (PhoneNumberConverter) super.createConverter();

		converter.setUnitedStatesFormat(JSFTagUtil.eval(unitedStatesFormat));

		return converter;
	}

	public void setUnitedStatesFormat(String unitedStatesFormat) {
		this.unitedStatesFormat = unitedStatesFormat;
	}
}
