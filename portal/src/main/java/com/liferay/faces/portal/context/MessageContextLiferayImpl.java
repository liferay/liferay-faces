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
package com.liferay.faces.portal.context;

import java.util.Locale;

import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextWrapper;

import com.liferay.portal.kernel.language.LanguageUtil;


/**
 * @author  Neil Griffin
 */
public class MessageContextLiferayImpl extends MessageContextWrapper {

	// Private Data Members
	private MessageContext wrappedMessageContext;

	public MessageContextLiferayImpl(MessageContext messageContext) {
		this.wrappedMessageContext = messageContext;
	}

	@Override
	public String getMessage(Locale locale, String messageId) {
		String value = LanguageUtil.get(locale, messageId);

		if ((value == null) || value.equals(messageId)) {
			value = super.getMessage(locale, messageId);
		}

		return value;
	}

	@Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		String value = LanguageUtil.format(locale, messageId, arguments);

		if ((value == null) || value.equals(messageId)) {
			value = super.getMessage(locale, messageId, arguments);
		}

		return value;
	}

	@Override
	public MessageContext getWrapped() {
		return wrappedMessageContext;
	}
}
