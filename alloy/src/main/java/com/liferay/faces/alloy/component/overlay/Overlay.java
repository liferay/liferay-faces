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
package com.liferay.faces.alloy.component.overlay;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ClientComponent;


/**
 * @author  Neil Griffin
 */
public interface Overlay extends ClientComponent {

	public UIComponent findComponent(String id);

	public String getClientId(FacesContext facesContext);

	public String getFor();

	public String getHeaderText();

	public Boolean isModal();

	public Boolean isAutoShow();
}
