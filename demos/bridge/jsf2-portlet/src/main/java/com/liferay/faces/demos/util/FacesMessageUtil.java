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
package com.liferay.faces.demos.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class FacesMessageUtil {

	private static final Logger logger = LoggerFactory.getLogger(FacesMessageUtil.class);
	private static ResourceBundle resourceBundle;

	public static void addErrorMessage(FacesContext facesContext, String clientId, String key, Object[] args) {
		addMessage(facesContext, clientId, FacesMessage.SEVERITY_ERROR, key, args);
	}

	public static void addGlobalErrorMessage(FacesContext facesContext, String key, Object[] args) {
		String clientId = null;
		addErrorMessage(facesContext, clientId, key, args);
	}

	public static void addGlobalInfoMessage(FacesContext facesContext, String key, Object[] args) {
		String clientId = null;
		addInfoMessage(facesContext, clientId, key, args);
	}

	public static void addGlobalInfoMessage(FacesContext facesContext, String key, Object arg) {
		addGlobalInfoMessage(facesContext, key, new Object[] { arg });
	}

	public static void addGlobalSuccessInfoMessage(FacesContext facesContext) {
		String key = "your-request-processed-successfully";
		Object[] args = null;
		addGlobalInfoMessage(facesContext, key, args);
	}

	public static void addGlobalUnexpectedErrorMessage(FacesContext facesContext) {
		String key = "an-unexpected-error-occurred";
		Object[] args = null;
		addGlobalErrorMessage(facesContext, key, args);
	}

	public static void addInfoMessage(FacesContext facesContext, String clientId, String key, Object[] args) {
		addMessage(facesContext, clientId, FacesMessage.SEVERITY_INFO, key, args);
	}

	public static void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String key, Object[] args) {
		String message = getMessage(key, args);
		FacesMessage facesMessage = new FacesMessage(severity, message, message);
		facesContext.addMessage(clientId, facesMessage);
	}

	public static String getMessage(String key, Object[] args) {
		String message = key;

		try {

			if (resourceBundle == null) {
				resourceBundle = ResourceBundle.getBundle("i18n");
			}

			message = resourceBundle.getString(key);

			if (args != null) {
				message = MessageFormat.format(message, args);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return message;
	}
}
