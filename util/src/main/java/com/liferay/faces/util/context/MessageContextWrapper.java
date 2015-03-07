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
package com.liferay.faces.util.context;

import java.util.Locale;

import javax.faces.FacesWrapper;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public abstract class MessageContextWrapper implements MessageContext, FacesWrapper<MessageContext> {

	@Override
	public FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId) {
		return getWrapped().newFacesMessage(locale, severity, messageId);
	}

	@Override
	public FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String messageId) {
		return getWrapped().newFacesMessage(facesContext, severity, messageId);
	}

	@Override
	public FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId, Object... arguments) {
		return getWrapped().newFacesMessage(locale, severity, messageId, arguments);
	}

	@Override
	public FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String messageId,
		Object... arguments) {
		return getWrapped().newFacesMessage(facesContext, severity, messageId, arguments);
	}

	@Override
	public String getMessage(Locale locale, String messageId) {
		return getWrapped().getMessage(locale, messageId);
	}

	@Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return getWrapped().getMessage(locale, messageId, arguments);
	}

	public abstract MessageContext getWrapped();
}
