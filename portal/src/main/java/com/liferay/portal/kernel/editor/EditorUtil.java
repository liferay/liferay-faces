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
package com.liferay.portal.kernel.editor;

import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;


/**
 * This class is a backport of the EditorUtil class which was introduced in Liferay Portal 6.0.12. It's main purpose is
 * to facilitate keeping the {@link InputEditorInternalRenderer} source the same on all branches of the Liferay Faces
 * source.
 *
 * @author  Julio Camarero
 * @author  Neil Griffin
 */
public class EditorUtil {

	private static final String _EDITOR_WYSIWYG_DEFAULT = PropsUtil.get(PropsKeys.EDITOR_WYSIWYG_DEFAULT);

	public static String getEditorValue(HttpServletRequest request, String editorImpl) {

		if (Validator.isNotNull(editorImpl)) {
			editorImpl = PropsUtil.get(editorImpl);
		}

		if (!BrowserSnifferUtil.isRtf(request)) {

			if (BrowserSnifferUtil.isSafari(request) && BrowserSnifferUtil.isMobile(request)) {

				editorImpl = "simple";
			}
			else if (BrowserSnifferUtil.isSafari(request) && !editorImpl.contains("simple")) {

				editorImpl = "tinymce_simple";
			}
			else {
				editorImpl = "simple";
			}
		}

		if (Validator.isNull(editorImpl)) {
			editorImpl = _EDITOR_WYSIWYG_DEFAULT;
		}

		return editorImpl;
	}

}
