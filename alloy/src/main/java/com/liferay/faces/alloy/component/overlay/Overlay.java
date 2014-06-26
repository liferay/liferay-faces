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

import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ClientComponent;


/**
 * @author  Vernon Singleton
 */
public interface Overlay extends ClientComponent {

	public Integer getzIndex();

	public String getClientId(FacesContext facesContext);

	public String getHeaderText();

	public boolean isModal();

	public boolean isHideOnBlur();

	public boolean isAutoShow();
}
