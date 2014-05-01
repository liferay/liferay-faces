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
package com.liferay.faces.demos.context;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.liferay.faces.demos.util.ShowcaseUtil;
import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextWrapper;


/**
 * @author  Neil Griffin
 */
public class ShowcaseMessageContext extends MessageContextWrapper {

	// Private Data Members
	private MessageContext wrappedMessageContext;
	private Map<String, String> messageCache = new HashMap<String, String>();

	public ShowcaseMessageContext(MessageContext messageContext) {
		this.wrappedMessageContext = messageContext;
	}

	@Override
	public String getMessage(Locale locale, String messageId) {

		String messageKey = getMessageKey(locale, messageId);

		String message = messageCache.get(messageKey);

		if (message == null) {
			message = super.getMessage(locale, messageId);

			if (message != null) {
				message = ShowcaseUtil.encodeDescription(message);
				messageCache.put(messageKey, message);
			}
		}

		return message;
	}

	protected String getMessageKey(Locale locale, String messageId) {

		StringBuilder messageKey = new StringBuilder();

		if (locale != null) {
			messageKey.append(locale.toString());
		}

		if (messageId != null) {
			messageKey.append(messageId);
		}

		return messageKey.toString();
	}

	@Override
	public MessageContext getWrapped() {
		return wrappedMessageContext;
	}
}
