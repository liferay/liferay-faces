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
package com.liferay.faces.util.context.internal;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
public class MessageContextImpl implements MessageContext {

	private Map<Locale, ResourceBundle> facesResourceBundleMap = new ConcurrentHashMap<Locale, ResourceBundle>();

	@Override
	public FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String key) {

		String messageId = key;
		Locale locale = facesContext.getViewRoot().getLocale();

		return newFacesMessage(locale, severity, messageId);
	}

	@Override
	public FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId) {
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(severity);
		facesMessage.setSummary(messageId);
		facesMessage.setDetail(null);

		MessageContextFactory messageContextFactory = (MessageContextFactory) FactoryExtensionFinder.getFactory(
				MessageContextFactory.class);

		MessageContext messageContext = messageContextFactory.getMessageContext();

		String summary = messageContext.getMessage(locale, messageId);

		if (summary != null) {
			facesMessage.setSummary(summary);

			String detailMessageId = messageId + "_detail";
			String detail = getMessage(locale, detailMessageId);

			if ((detail != null) && (!detailMessageId.equals(detail))) {
				facesMessage.setDetail(detail);
			}
		}

		return facesMessage;
	}

	@Override
	public FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String key, Object... arguments) {

		String messageId = key;
		Locale locale = facesContext.getViewRoot().getLocale();

		return newFacesMessage(locale, severity, messageId, arguments);
	}

	@Override
	public FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId, Object... arguments) {

		FacesMessage facesMessage = newFacesMessage(locale, severity, messageId);

		String summary = facesMessage.getSummary();

		if (summary != null) {
			facesMessage.setSummary(MessageFormat.format(summary, arguments));
		}

		String detail = facesMessage.getDetail();

		if (detail != null) {
			facesMessage.setDetail(MessageFormat.format(detail, arguments));
		}

		return facesMessage;
	}

	protected ResourceBundle getFacesResourceBundle(Locale locale) {
		ResourceBundle facesResourceBundle = facesResourceBundleMap.get(locale);

		if (facesResourceBundle == null) {
			String messageBundle = FacesContext.getCurrentInstance().getApplication().getMessageBundle();

			if (messageBundle == null) {
				messageBundle = FacesMessage.FACES_MESSAGES;
			}

			facesResourceBundle = ResourceBundle.getBundle(messageBundle, locale);
			facesResourceBundleMap.put(locale, facesResourceBundle);
		}

		return facesResourceBundle;
	}

	@Override
	public String getMessage(Locale locale, String messageId) {

		String message = null;

		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = ResourceBundle.getBundle("i18n", locale);
			message = resourceBundle.getString(messageId);
		}
		catch (MissingResourceException e) {
			// ignore
		}

		if (message == null) {
			resourceBundle = getFacesResourceBundle(locale);

			if (resourceBundle != null) {

				try {
					message = resourceBundle.getString(messageId);
				}
				catch (MissingResourceException e) {
					// ignore
				}
			}
		}

		if (message == null) {
			message = messageId;
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

}
