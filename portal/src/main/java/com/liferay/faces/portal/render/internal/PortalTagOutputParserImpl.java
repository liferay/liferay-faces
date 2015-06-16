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
package com.liferay.faces.portal.render.internal;

import com.liferay.faces.util.jsp.StringJspWriter;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.ContentTypes;


/**
 * @author  Neil Griffin
 */
public class PortalTagOutputParserImpl implements PortalTagOutputParser {

	// Private Constants
	private static final String COMMENT_CDATA_CLOSE = "// " + StringPool.CDATA_CLOSE;
	private static final String SCRIPT_TAG_BEGIN_TYPE_JS = "<script type=\"" + ContentTypes.TEXT_JAVASCRIPT + "\">";

	@Override
	public PortalTagOutput parse(StringJspWriter stringJspWriter) {

		String markup = stringJspWriter.toString();
		String scriptText = null;
		StringBuilder scriptBuilder = new StringBuilder();

		String scriptSectionMarker = getScriptSectionMarker();
		int scriptMarkerPos = markup.indexOf(scriptSectionMarker);

		if (scriptMarkerPos > 0) {
			scriptText = markup.substring(scriptMarkerPos);
			markup = markup.substring(0, scriptMarkerPos);
		}

		boolean done1 = (scriptText == null);

		while (!done1) {
			int beginPos = scriptText.indexOf(StringPool.SCRIPT_TAG_BEGIN);

			if (beginPos < 0) {
				beginPos = scriptText.indexOf(SCRIPT_TAG_BEGIN_TYPE_JS);
			}

			int endPos = scriptText.indexOf(StringPool.SCRIPT_TAG_END, beginPos);

			if ((beginPos >= 0) && (endPos > beginPos)) {
				String script = scriptText.substring(beginPos, endPos + StringPool.SCRIPT_TAG_END.length());

				boolean done2 = false;

				while (!done2) {
					int cdataOpenPos = script.indexOf(StringPool.CDATA_OPEN);

					if (cdataOpenPos > 0) {
						script = script.substring(cdataOpenPos + StringPool.CDATA_OPEN.length());

						int cdataClosePos = script.indexOf(COMMENT_CDATA_CLOSE);

						if (cdataClosePos > 0) {
							script = script.substring(0, cdataClosePos);
						}
						else {
							cdataClosePos = script.indexOf(StringPool.CDATA_CLOSE);

							if (cdataClosePos > 0) {
								script = script.substring(0, cdataClosePos);
							}
						}
					}
					else {
						done2 = true;
					}
				}

				// Remove all the "<![CDATA[" and "]]>" tokens since they will interfere with the JSF
				// partial-response.
				String[] tokensToRemove = new String[] { StringPool.CDATA_OPEN, StringPool.CDATA_CLOSE };

				for (String token : tokensToRemove) {
					int pos = script.indexOf(token);

					while (pos >= 0) {
						script = script.substring(0, pos) + script.substring(pos + token.length());
						pos = script.indexOf(token);
					}
				}

				scriptBuilder.append(script.trim());
				scriptText = scriptText.substring(0, beginPos) +
					scriptText.substring(endPos + StringPool.SCRIPT_TAG_END.length());
			}
			else {
				done1 = true;
			}
		}

		return new PortalTagOutputImpl(markup.trim(), scriptBuilder.toString());
	}

	@Override
	public String getScriptSectionMarker() {
		return "<hr id=\"scripts\"/>";
	}
}
