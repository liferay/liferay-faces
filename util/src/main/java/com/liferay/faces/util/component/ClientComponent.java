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
package com.liferay.faces.util.component;

/**
 * This interface should be implemented by classes that extend {@link UIComponent} if they provide the ability to access
 * the value of a client-side UI component instance (Liferay Component) via JavaScript.
 *
 * @author  Neil Griffin
 */
public interface ClientComponent {

	public static final String CLIENT_KEY = "clientKey";

	public String getClientId();

	public String getClientKey();

	public void setClientKey(String clientKey);
}
