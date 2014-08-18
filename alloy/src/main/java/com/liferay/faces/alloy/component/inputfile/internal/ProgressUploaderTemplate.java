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
package com.liferay.faces.alloy.component.inputfile.internal;

import java.io.IOException;
import java.util.Locale;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.template.Template;
import com.liferay.faces.util.template.TemplateFactory;


/**
 * @author  Neil Griffin
 */
public class ProgressUploaderTemplate {

	// Private Data Members
	private Template template;

	public ProgressUploaderTemplate(boolean minified) throws IOException {
		TemplateFactory templateFactory = (TemplateFactory) FactoryExtensionFinder.getFactory(TemplateFactory.class);
		this.template = templateFactory.getTemplate(this.getClass(), "progress-uploader.js", minified);
	}

	public String format(Locale locale, String clientVarName, String clientKey, String clientId, String formClientId,
		String execute, String render, String partialActionURL, String namingContainerId, boolean auto,
		String contentTypes, long maxFileSize) throws IOException {

		String[] tokens = new String[] {
				"_clientVarName_", "_clientKey_", "_clientId_", "_escapedClientId_", "_formClientId_",
				"_escapedFormClientId_", "_execute_", "_render_", "_partialActionURL_", "_namingContainerId_",
				"_notStartedMessage_", "_auto_", "_contentTypes_", "_maxFileSize_"
			};
		String escapedClientId = ComponentUtil.escapeClientId(clientId);
		String escapedFormClientId = ComponentUtil.escapeClientId(formClientId);
		MessageContext messageContext = MessageContext.getInstance();
		String notStartedMessage = messageContext.getMessage(locale, "not-started");

		if (contentTypes == null) {
			contentTypes = "[]";
		}
		else {
			contentTypes = InputFileTemplateUtil.toJavaScriptArray(contentTypes.split(StringPool.COMMA));
		}

		Object[] replacements = new Object[] {
				clientVarName, clientKey, clientId, escapedClientId, formClientId, escapedFormClientId, execute, render,
				partialActionURL, namingContainerId, notStartedMessage, auto, contentTypes, maxFileSize
			};

		return template.formatTokens(tokens, replacements);
	}
}
