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
package com.liferay.portal.kernel.editor;

import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.portal.renderkit.InputEditorInternalRenderer;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;


/**
 * This class is a pseudo-backport of the EditorUtil class which was introduced in Liferay Portal 6.1.x. It's main
 * purpose is to facilitate keeping the {@link InputEditorInternalRenderer} source the same on all branches of the
 * Liferay Faces source.
 *
 * @author  Julio Camarero
 * @author  Neil Griffin
 */
public class EditorUtil {

	// Private Constants
	private static final String EDITOR_WYSIWYG_DEFAULT = "editor.wysiwyg.default";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(EditorUtil.class);

	public static String getEditorValue(HttpServletRequest request, String editorImpl) {

		if (Validator.isNull(editorImpl)) {

			try {
				editorImpl = PropsUtil.get(EDITOR_WYSIWYG_DEFAULT);
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return editorImpl;
	}

}
