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
package com.liferay.faces.alloy.component.button.internal;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.UIViewAction;
import javax.faces.component.UIViewParameter;

import com.liferay.faces.util.render.DelegatingRendererBase;


/**
 * This class isolates differences between JSF 2.2 and JSF 2.1 in order to minimize diffs across branches.
 *
 * @author  Neil Griffin
 */
public abstract class ButtonRendererCompat extends DelegatingRendererBase {

	protected boolean isVisualComponent(UIComponent uiComponent) {
		return (!(uiComponent instanceof UIParameter)) || (!(uiComponent instanceof UIViewAction)) ||
			(!(uiComponent instanceof UIViewParameter));
	}
}
