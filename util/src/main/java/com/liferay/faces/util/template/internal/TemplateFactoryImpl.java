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
package com.liferay.faces.util.template.internal;

import java.io.IOException;
import java.net.URL;

import com.liferay.faces.util.io.TextResource;
import com.liferay.faces.util.io.TextResourceUtil;
import com.liferay.faces.util.render.ContentTypes;
import com.liferay.faces.util.template.Template;
import com.liferay.faces.util.template.TemplateFactory;


/**
 * @author  Neil Griffin
 */
public class TemplateFactoryImpl extends TemplateFactory {

	protected String getContentType(String filename) {

		String contentType = null;

		if (filename.endsWith(".js")) {
			contentType = ContentTypes.TEXT_JAVASCRIPT;
		}
		else if (filename.endsWith(".html")) {
			contentType = ContentTypes.TEXT_HTML;
		}

		return contentType;
	}

	@Override
	public Template getTemplate(String text, String contentType) {
		return new TemplateImpl(text);
	}

	@Override
	public Template getTemplate(Class<?> relativeClass, String resourcePath, boolean minified) throws IOException {

		String contentType = getContentType(resourcePath);

		if (minified && contentType.equals(ContentTypes.TEXT_JAVASCRIPT)) {
			int pos = resourcePath.lastIndexOf(".js");
			resourcePath = resourcePath.substring(0, pos) + "-min.js";
		}

		URL resourceURL = relativeClass.getResource(resourcePath);

		if (resourceURL != null) {
			TextResource textResource = TextResourceUtil.read(resourceURL);
			String templateText = textResource.getText();

			return getTemplate(templateText, contentType);
		}
		else {
			throw new IOException("Unable to read file " + resourcePath);
		}
	}

	@Override
	public TemplateFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
