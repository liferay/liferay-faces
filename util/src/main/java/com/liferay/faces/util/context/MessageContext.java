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

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public interface MessageContext {

	public FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId);

	public FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String messageId);

	public FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId, Object... arguments);

	public FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String messageId,
		Object... arguments);

	public String getMessage(Locale locale, String messageId);

	public String getMessage(Locale locale, String messageId, Object... arguments);

}
