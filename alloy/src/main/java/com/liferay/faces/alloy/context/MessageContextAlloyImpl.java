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
package com.liferay.faces.alloy.context;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextWrapper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class MessageContextAlloyImpl extends MessageContextWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MessageContextAlloyImpl.class);

	// Private Data Members
	private boolean initialized;
	private MessageContext wrappedMessageContext;

	public MessageContextAlloyImpl(MessageContext messageContext) {
		this.wrappedMessageContext = messageContext;
	}

	@Override
	public String getMessage(Locale locale, String messageId) {

		String message = null;
		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = ResourceBundle.getBundle("aui-i18n", locale);
			message = resourceBundle.getString(messageId);
		}
		catch (MissingResourceException e) {

			if (!initialized) {
				logger.error(e);
			}
		}

		initialized = true;

		if (message == null) {
			message = super.getMessage(locale, messageId);
		}

		return message;
	}

	@Override
	public MessageContext getWrapped() {
		return wrappedMessageContext;
	}
}
