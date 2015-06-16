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
package com.liferay.faces.alloy.render.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;


/**
 * @author  Kyle Stiemann
 */
public interface AlloyRenderer extends ClientComponentRenderer {

	// Public Constants
	public static final String A_DOT_ONE = "A.one";
	public static final String BOUNDING_BOX = "boundingBox";
	public static final String CONTENT_BOX = "contentBox";
	public static final String LIFERAY_COMPONENT = "Liferay.component";
	public static final String LIFERAY_Z_INDEX_OVERLAY = "Liferay.zIndex.OVERLAY";
	public static final String LIFERAY_Z_INDEX_TOOLTIP = "Liferay.zIndex.TOOLTIP";

	public void decodeClientBehaviors(FacesContext facesContext, UIComponent uiComponent);

	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter respoonseWriter,
		UIComponent uiComponent) throws IOException;

	public void encodeBoolean(ResponseWriter responseWriter, String attributeName, Boolean attributeValue,
		boolean first) throws IOException;

	public void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId, boolean first)
		throws IOException;

	public void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId,
		UIComponent uiComponent, boolean first) throws IOException;

	public void encodeEventCallback(ResponseWriter responseWriter, String varName, String methodName, String eventName,
		String callback) throws IOException;

	public void encodeFunctionCall(ResponseWriter responseWriter, String functionName, Object... parameters)
		throws IOException;

	public void encodeInteger(ResponseWriter responseWriter, String attributeName, Integer attributeValue,
		boolean first) throws IOException;

	public void encodeLiferayComponentVar(ResponseWriter responseWriter, String clientVarName, String clientKey)
		throws IOException;

	public void encodeNonEscapedObject(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException;

	public void encodeString(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException;

	public void encodeWidgetRender(ResponseWriter responseWriter, boolean first) throws IOException;

	public String escapeClientId(String clientId);

	public String escapeJavaScript(String javaScript);

	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent);

	public String getYUIConfig(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException;
}
