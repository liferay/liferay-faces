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
package com.liferay.faces.portal.component.inputrichtext.internal;

import java.io.IOException;

import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import com.liferay.faces.portal.component.inputrichtext.InputRichText;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.template.Template;
import com.liferay.faces.util.template.TemplateFactory;


/**
 * @author  Neil Griffin
 */
public class WYSIWYGTemplate {

	// Private Data Members
	private Template template;

	public WYSIWYGTemplate(boolean minified) throws IOException {
		TemplateFactory templateFactory = (TemplateFactory) FactoryExtensionFinder.getFactory(TemplateFactory.class);
		this.template = templateFactory.getTemplate(this.getClass(), "wysiwyg.js", minified);
	}

	public String format(FacesContext facesContext, InputRichText inputRichText) throws IOException {

		String[] tokens = new String[] { "_clientId_", "_functionNamespace_" };
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String clientId = inputRichText.getClientId();
		String functionNamespace = clientId.replace(separatorChar, '_');
		String[] replacements = new String[] { clientId, functionNamespace };

		return template.formatTokens(tokens, replacements);
	}
}
