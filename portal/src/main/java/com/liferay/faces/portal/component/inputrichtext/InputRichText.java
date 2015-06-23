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
package com.liferay.faces.portal.component.inputrichtext;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;

import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.text.RichText;
import com.liferay.faces.util.text.RichTextFactory;

import com.liferay.portal.kernel.util.PropsUtil;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = InputRichText.COMPONENT_TYPE)
public class InputRichText extends InputRichTextBase implements ClientBehaviorHolder {

	// Private Constants
	private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur",
				"change", "valueChange", "focus"));

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputRichText.class);

	@Override
	protected void validateValue(FacesContext facesContext, Object newValue) {

		super.validateValue(facesContext, newValue);

		if (isValid()) {

			String editorType = null;
			String editorKey = getEditorKey();

			if (editorKey != null) {
				editorType = PropsUtil.get(editorKey);
			}

			RichText.Type richTextType;

			if ("ckeditor_bbcode".equals(editorType)) {
				richTextType = RichText.Type.BBCODE;
			}
			else if ("ckeditor_creole".equals(editorType)) {
				richTextType = RichText.Type.CREOLE;
			}
			else {
				richTextType = RichText.Type.HTML;
			}

			RichTextFactory richTextFactory = (RichTextFactory) FactoryExtensionFinder.getFactory(
					RichTextFactory.class);
			RichText richText = richTextFactory.getRichText(richTextType, newValue.toString());
			int length = richText.getPlainTextLength();
			int minimum = getMinPlainTextChars();
			int maximum = getMaxPlainTextChars();

			logger.debug("length=[{0}] minimum=[{1}] maximum=[{2}]", length, minimum, maximum);

			if ((minimum > 0) && (length < minimum)) {

				Object label = getAttributes().get("label");
				Locale locale = facesContext.getViewRoot().getLocale();
				FacesMessage facesMessage = getMessageContext().newFacesMessage(locale, FacesMessage.SEVERITY_ERROR,
						LengthValidator.MINIMUM_MESSAGE_ID, minimum, label);
				facesContext.addMessage(getClientId(), facesMessage);
				setValid(false);
			}

			if ((maximum > 0) && (length > maximum)) {

				Object label = getAttributes().get("label");
				Locale locale = facesContext.getViewRoot().getLocale();
				FacesMessage facesMessage = getMessageContext().newFacesMessage(locale, FacesMessage.SEVERITY_ERROR,
						LengthValidator.MAXIMUM_MESSAGE_ID, maximum, label);
				facesContext.addMessage(getClientId(), facesMessage);
				setValid(false);
			}
		}
	}

	@Override
	public String getDefaultEventName() {
		return "valueChange";
	}

	@Override
	public Collection<String> getEventNames() {
		return EVENT_NAMES;
	}

	protected MessageContext getMessageContext() {

		MessageContextFactory messageContextFactory = (MessageContextFactory) FactoryExtensionFinder.getFactory(
				MessageContextFactory.class);

		return messageContextFactory.getMessageContext();
	}
}
