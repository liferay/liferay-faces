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
package com.liferay.faces.util.template.internal;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.template.Template;


/**
 * @author  Neil Griffin
 */
public class TemplateImpl implements Template {

	// Private Data Members
	private String template;

	public TemplateImpl(String template) {
		this.template = template;
	}

	public String formatTokens(String[] tokens, Object[] replacements) {

		String formattedTemplate = template;

		if ((tokens != null) && (replacements != null)) {

			if (tokens.length != replacements.length) {
				throw new java.lang.IllegalArgumentException("Number of tokens and replacements must be the same.");
			}
			else {

				for (int i = 0; i < tokens.length; i++) {

					Object curReplacement = replacements[i];

					if (curReplacement == null) {
						curReplacement = StringPool.BLANK;
					}

					formattedTemplate = formattedTemplate.replace(tokens[i], curReplacement.toString());
				}
			}
		}

		return formattedTemplate;
	}

	@Override
	public String toString() {
		return template;
	}
}
