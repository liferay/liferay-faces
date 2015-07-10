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
package com.liferay.faces.demos.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
// JSF 2+ import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


/**
 * @author  Juan Gonzalez
 */
// JSF 2+ @FacesValidator("com.liferay.faces.demos.validator.EmailValidator")
public class EmailValidator implements Validator {

	private static final String EMAIL_REGEX = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String message = "";

		try {
			Pattern pattern = Pattern.compile(EMAIL_REGEX);
			Matcher matcher = pattern.matcher((String) value);

			if (!matcher.matches()) {
				message = ValidatorHelper.getMessage(context, "email-is-not-valid");

				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
				throw new ValidatorException(facesMessage);
			}
		}
		catch (PatternSyntaxException pse) {
			message = ValidatorHelper.getMessage(context, "unexpected-validation-error-ocurred");

			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message);
			throw new ValidatorException(facesMessage);
		}
	}
}
