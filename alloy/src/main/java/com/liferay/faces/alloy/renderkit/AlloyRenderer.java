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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;


/**
 * @author  Kyle Stiemann
 */
public interface AlloyRenderer {

	public void encodeAlloyAttributes(ResponseWriter respoonseWriter, UIComponent uiComponent) throws IOException;

	public void encodeArray(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException;

	public void encodeBoolean(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException;

	public void encodeComplexBoolean(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException;

	public void encodeComplexNumber(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException;

	public void encodeEvent(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException;

	public void encodeLang(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException;

	public void encodeLiferayComponent(ResponseWriter responseWriter, String clientKey) throws IOException;

	public void encodeLiferayComponentVar(ResponseWriter responseWriter, String clientVarName, String clientKey)
		throws IOException;

	public void encodeMap(ResponseWriter responseWriter, String attributeName, Map<String, String> attributeValues,
		boolean first) throws IOException;

	public void encodeNumber(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException;

	public void encodeObject(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException;

	public void encodeString(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException;

	public String getAlloyClassName();
}
