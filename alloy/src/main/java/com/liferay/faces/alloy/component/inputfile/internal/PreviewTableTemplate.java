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
package com.liferay.faces.alloy.component.inputfile.internal;

import java.io.IOException;
import java.util.Locale;

import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.template.Template;
import com.liferay.faces.util.template.TemplateFactory;


/**
 * @author  Neil Griffin
 */
public class PreviewTableTemplate {

	// Private Data Members
	private Template template;

	public PreviewTableTemplate(boolean minified) throws IOException {
		TemplateFactory templateFactory = (TemplateFactory) FactoryExtensionFinder.getFactory(TemplateFactory.class);
		this.template = templateFactory.getTemplate(this.getClass(), "preview-table.html", minified);
	}

	public String format(Locale locale, String clientId, boolean auto) throws IOException {
		String[] tokens = new String[] {
				"${clientId}", "${i18n['file-name']}", "${i18n['file-type']}", "${i18n['file-size']}",
				"${i18n['progress']}", "${i18n['no-files-selected']}", "${i18n['upload-files']}",
				"${uploadFilesButtonClass}"
			};
		MessageContextFactory messageContextFactory = (MessageContextFactory) FactoryExtensionFinder.getFactory(
				MessageContextFactory.class);
		MessageContext messageContext = messageContextFactory.getMessageContext();
		String i18nFileName = messageContext.getMessage(locale, "file-name");
		String i18nFileType = messageContext.getMessage(locale, "file-type");
		String i18nFileSize = messageContext.getMessage(locale, "file-size");
		String i18nProgress = messageContext.getMessage(locale, "progress");
		String i18nNoFilesSelected = messageContext.getMessage(locale, "no-files-selected");
		String i18nUploadFiles = messageContext.getMessage(locale, "upload-files");
		String uploadFilesButtonClass = StringPool.BLANK;

		if (auto) {
			uploadFilesButtonClass = "alloy-input-file-button-hidden";
		}

		String[] replacements = new String[] {
				clientId, i18nFileName, i18nFileType, i18nFileSize, i18nProgress, i18nNoFilesSelected, i18nUploadFiles,
				uploadFilesButtonClass
			};

		return getTemplate().formatTokens(tokens, replacements);
	}

	protected Template getTemplate() {
		return template;
	}
}
