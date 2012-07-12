/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.context;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public abstract class MessageContext {

	protected static MessageContext instance = new MessageContextImpl();

	public static MessageContext getInstance() {
		return instance;
	}

	public static void setInstance(MessageContext messageContext) {
		instance = messageContext;
	}

	public abstract FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId);

	public abstract FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String messageId);

	public abstract FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId,
		Object... arguments);

	public abstract FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String messageId,
		Object... arguments);

	public abstract String getMessage(Locale locale, String messageId);

	public abstract String getMessage(Locale locale, String messageId, Object... arguments);

}
