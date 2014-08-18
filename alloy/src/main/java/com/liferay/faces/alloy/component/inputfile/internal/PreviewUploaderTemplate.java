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

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.template.Template;
import com.liferay.faces.util.template.TemplateFactory;


/**
 * @author  Neil Griffin
 */
public class PreviewUploaderTemplate {

	// Private Data Members
	private Template template;

	public PreviewUploaderTemplate(boolean minified) throws IOException {
		TemplateFactory templateFactory = (TemplateFactory) FactoryExtensionFinder.getFactory(TemplateFactory.class);
		this.template = templateFactory.getTemplate(this.getClass(), "preview-uploader.js", minified);
	}

	public String format(String clientId, String contentTypes, long maxFileSize) throws IOException {

		String[] tokens = new String[] { "_escapedClientId_", "_contentTypes_", "_maxFileSize_" };
		String escapedClientId = ComponentUtil.escapeClientId(clientId);

		if (contentTypes == null) {
			contentTypes = "[]";
		}
		else {
			contentTypes = InputFileTemplateUtil.toJavaScriptArray(contentTypes.split(StringPool.COMMA));
		}

		Object[] replacements = new Object[] { escapedClientId, contentTypes, maxFileSize };

		return template.formatTokens(tokens, replacements);
	}
}
