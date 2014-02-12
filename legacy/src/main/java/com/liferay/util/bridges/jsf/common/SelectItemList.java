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
package com.liferay.util.bridges.jsf.common;

import java.util.ArrayList;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;


/**
 * <p>This class provides a convenient way of building lists of JSF SelectItem objects, and convenience method for
 * operating against them.</p>
 *
 * @deprecated  This class has been deprecated as of Liferay Faces 2.2 and will not appear in future versions.
 * @author      Neil Griffin
 */
@Deprecated
public class SelectItemList extends ArrayList<SelectItem> {

	// serialVersionUID
	private static final long serialVersionUID = 4236180678137107319L;

	public void prependEmptySelectItem(FacesContext facesContext) {
		Locale locale = facesContext.getExternalContext().getRequestLocale();

		Object value = StringPool.BLANK;
		String label = LanguageUtil.get(locale, "select");

		add(0, new SelectItem(value, label));
	}

}
