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
package com.liferay.faces.alloy.component.charcounter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
public class CharCounterUtil {

	// Returns a default id, escaped and ready for use by alloy.
	public static String getDefaultCounterEscaped(FacesContext facesContext, UIComponent uiComponent) {
		String counterClientId = getDefaultCounterSpanId(facesContext, uiComponent);

		return StringPool.POUND + ComponentUtil.escapeClientId(counterClientId);
	}

	// Returns a default id (unescaped).
	public static String getDefaultCounterSpanId(FacesContext facesContext, UIComponent uiComponent) {
		return uiComponent.getClientId(facesContext) + ":counter";
	}
	
	protected static boolean isCounterSpecified(FacesContext facesContext, CharCounterAlloy charCounterAlloy) {
		String counter = charCounterAlloy.getCounter();
		String defaultCounterEscaped = CharCounterUtil.getDefaultCounterEscaped(facesContext,
				(UIComponent) charCounterAlloy);

		return !defaultCounterEscaped.equals(counter);
	}
}
