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
package com.liferay.faces.portal.validator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.ValidatorException;

import com.liferay.faces.portal.component.UIComponentHelper;
import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.StringPool;


/**
 * Validator that is meant to be used in conjunction with the liferay-ui:input-editor component in order to validate
 * length on the server-side.
 *
 * @author  Neil Griffin
 * @author  Joe Ssemwogerere
 */
public class RichTextLengthValidator extends LengthValidator {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RichTextLengthValidator.class);

	// Private Constants
	private static final Pattern TAG_PATTERN = Pattern.compile("<.+?>");
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

	public RichTextLengthValidator() {
		super();
	}

	public RichTextLengthValidator(int maximum) {
		super(maximum);
	}

	public RichTextLengthValidator(int maximum, int minimum) {
		super(maximum, minimum);
	}

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {

		if ((facesContext == null) || (uiComponent == null)) {
			throw new NullPointerException();
		}

		if (value != null) {

			int length = getPlainTextStringLength(value);
			int minimum = getMinimum();
			int maximum = getMaximum();

			logger.debug("length=[{0}] minimum=[{1}] maximum=[{2}]", length, minimum, maximum);

			if ((minimum > 0) && (length < minimum)) {
				Object label = UIComponentHelper.getLabel(facesContext, uiComponent);
				LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
				Locale locale = liferayFacesContext.getLocale();
				MessageContext messageContext = MessageContext.getInstance();
				FacesMessage facesMessage = messageContext.newFacesMessage(locale, FacesMessage.SEVERITY_ERROR,
						MINIMUM_MESSAGE_ID, maximum, label);
				throw new ValidatorException(facesMessage);
			}

			if ((maximum > 0) && (length > maximum)) {
				Object label = UIComponentHelper.getLabel(facesContext, uiComponent);
				LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
				Locale locale = liferayFacesContext.getLocale();
				MessageContext messageContext = MessageContext.getInstance();
				FacesMessage facesMessage = messageContext.newFacesMessage(locale, FacesMessage.SEVERITY_ERROR,
						MAXIMUM_MESSAGE_ID, maximum, label);
				throw new ValidatorException(facesMessage);
			}
		}
	}

	protected int getPlainTextStringLength(Object value) {

		int cleanStringLenth = 0;

		if (value != null) {
			String cleanString = null;

			if (value instanceof String) {
				cleanString = (String) value;
			}
			else {
				cleanString = value.toString();
			}

			Matcher tagMatcher = TAG_PATTERN.matcher(cleanString);

			cleanString = tagMatcher.replaceAll(StringPool.BLANK);
			cleanString = cleanString.replaceAll("&nbsp;", StringPool.SPACE);
			cleanString = WHITESPACE_PATTERN.matcher(cleanString).replaceAll(StringPool.SPACE);
			cleanString = cleanString.trim();
			cleanStringLenth = cleanString.length();
		}

		return cleanStringLenth;
	}

}
