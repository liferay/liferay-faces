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
package com.liferay.faces.alloy.renderkit;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;


/**
 * @author  Kyle Stiemann
 */
public interface AlloyRenderer {

	public void encodeAlloyAttributes(ResponseWriter respoonseWriter, UIComponent uiComponent) throws IOException;

	public void encodeBoolean(ResponseWriter responseWriter, String attributeName, Boolean attributeValue,
		boolean first) throws IOException;

	public void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId, boolean first)
		throws IOException;

	public void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId,
		UIComponent uiComponent, boolean first) throws IOException;

	public void encodeInteger(ResponseWriter responseWriter, String attributeName, Integer attributeValue,
		boolean first) throws IOException;

	public void encodeLang(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException;

	public void encodeLiferayComponent(ResponseWriter responseWriter, String clientKey) throws IOException;

	public void encodeLiferayComponentVar(ResponseWriter responseWriter, String clientVarName, String clientKey)
		throws IOException;

	public void encodeNonEscapedObject(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException;

	public void encodeString(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException;

	public void encodeWidgetRender(ResponseWriter responseWriter, boolean first) throws IOException;

	public String getAlloyClassName();
}
