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
package com.liferay.faces.alloy.context.internal;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

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
	private Map<String, String> messageMap;

	public MessageContextAlloyImpl(MessageContext messageContext) {
		this.wrappedMessageContext = messageContext;
		this.messageMap = new ConcurrentHashMap<String, String>();
	}

	@Override
	public String getMessage(Locale locale, String messageId) {

		String message = null;
		String key = messageId;

		if (locale != null) {
			key = locale.toString() + messageId;
		}

		if (messageMap.containsKey(key)) {
			message = messageMap.get(key);

			if ("".equals(message)) {
				message = null;
			}
		}
		else {

			ResourceBundle resourceBundle = null;

			try {
				resourceBundle = ResourceBundle.getBundle("aui-i18n", locale);
			}
			catch (MissingResourceException e) {

				if (!initialized) {
					logger.error(e);
				}
			}

			initialized = true;

			if (resourceBundle != null) {

				try {
					message = resourceBundle.getString(messageId);
					messageMap.put(key, message);
				}
				catch (MissingResourceException e) {
					messageMap.put(key, "");
				}
			}
		}

		if (message == null) {
			message = super.getMessage(locale, messageId);
		}

		return message;
	}

	@Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		String message = getMessage(locale, messageId);

		if (message != null) {
			message = MessageFormat.format(message, arguments);
		}

		return message;
	}

	@Override
	public MessageContext getWrapped() {
		return wrappedMessageContext;
	}
}
