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
package com.liferay.taglib.faces.validator;

import javax.faces.webapp.ValidatorTag;


/**
 * @deprecated  This class has been deprecated as of Liferay Faces 2.2 and will not appear in future versions.
 * @author      Neil Griffin
 */
@Deprecated
public class EmailAddressValidatorTag extends ValidatorTag {

	private static final long serialVersionUID = 2896249444084212490L;

	public EmailAddressValidatorTag() {
		setValidatorId(EmailAddressValidator.class.getName());
	}

}
