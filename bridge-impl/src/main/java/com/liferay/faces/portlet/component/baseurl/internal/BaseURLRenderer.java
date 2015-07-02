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
package com.liferay.faces.portlet.component.baseurl.internal;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.portlet.BaseURL;
import javax.portlet.PortletSecurityException;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.portlet.component.param.Param;
import com.liferay.faces.portlet.component.property.Property;


/**
 * @author  Kyle Stiemann
 */
public abstract class BaseURLRenderer extends BaseURLRendererBase {

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		// no-op
	}

	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		// no-op
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		com.liferay.faces.portlet.component.baseurl.BaseURL baseURLComponent =
			(com.liferay.faces.portlet.component.baseurl.BaseURL) uiComponent;
		BaseURL baseURL = getBaseURL(facesContext, uiComponent);
		Boolean secure = baseURLComponent.getSecure();

		if (secure != null) {

			try {
				baseURL.setSecure(secure);
			}
			catch (PortletSecurityException e) {
				throw new IOException(e);
			}
		}

		List<UIComponent> children = baseURLComponent.getChildren();
		Map<String, String[]> parameterMap = new HashMap<String, String[]>(baseURL.getParameterMap());
		Map<String, String[]> initialParameterMap = new HashMap<String, String[]>(parameterMap);
		final boolean RESOURCE_PHASE = BridgeUtil.getPortletRequestPhase().equals(Bridge.PortletPhase.RESOURCE_PHASE);

		for (UIComponent child : children) {

			if (child instanceof Param) {

				Param param = (Param) child;
				String name = param.getName();
				String value = param.getValue();

				if (parameterMap.containsKey(name)) {

					// According to the Java Portlet Specification (version 2.0 (2008-01-11)), when rendering a resource
					// URL, parameters set by the portlet container should not be removed.
					final boolean PARAM_SET_BY_PORTLET_CONTAINER = initialParameterMap.containsKey(name);
					final boolean REMOVE_EMPTY_PARAMETER = (!RESOURCE_PHASE || !PARAM_SET_BY_PORTLET_CONTAINER);

					if ("".equals(value) && REMOVE_EMPTY_PARAMETER) {
						parameterMap.remove(name);
					}
					else if ("".equals(value) && !REMOVE_EMPTY_PARAMETER) {
						// do nothing
					}
					else {

						String[] paramValueArray = parameterMap.get(name);
						String[] newParamValueArray = new String[paramValueArray.length + 1];
						System.arraycopy(paramValueArray, 0, newParamValueArray, 0, paramValueArray.length);
						newParamValueArray[newParamValueArray.length - 1] = value;
						parameterMap.put(name, newParamValueArray);
					}
				}
				else {
					parameterMap.put(name, new String[] { value });
				}
			}
			else if (child instanceof Property) {

				Property property = (Property) child;
				String value = property.getValue();

				if (value != null) {
					baseURL.addProperty(property.getName(), value);
				}
			}
		}

		baseURL.setParameters(parameterMap);

		String varName = baseURLComponent.getVar();
		String url = baseURL.toString();

		if (baseURLComponent.isEscapeXml()) {
			url = escapeXML(url);
		}

		// If the user didn't specify a value for the "var" attribute, then write the URL to the response.
		if (varName == null) {
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.write(url);
		}

		// Otherwise, place the url into the request scope so that it can be resolved via EL with the name
		// specified in the "var" attribute.
		else {

			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put(varName, url);
		}
	}

	/**
	 * Escapes the text so that it is safe to use in an HTML context.
	 *
	 * @param   text  the text to escape
	 *
	 * @return  the escaped HTML text, or <code>null</code> if the text is <code>null</code>
	 */
	private String escapeXML(String text) {

		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return "";
		}

		// Escape using XSS recommendations from
		// http://www.owasp.org/index.php/Cross_Site_Scripting
		// #How_to_Protect_Yourself

		StringBuilder sb = null;

		int lastReplacementIndex = 0;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			String replacement = null;

			switch (c) {

			case '<': {
				replacement = "&lt;";

				break;
			}

			case '>': {
				replacement = "&gt;";

				break;
			}

			case '&': {
				replacement = "&amp;";

				break;
			}

			case '"': {
				replacement = "&#034;";

				break;
			}

			case '\'': {
				replacement = "&#039;";

				break;
			}

			case '\u00bb': {

				// '�'
				replacement = "&#187;";

				break;
			}

			case '\u2013': {
				replacement = "&#x2013;";

				break;
			}

			case '\u2014': {
				replacement = "&#x2014;";

				break;
			}
			}

			if (replacement != null) {

				if (sb == null) {
					sb = new StringBuilder();
				}

				if (i > lastReplacementIndex) {
					sb.append(text.substring(lastReplacementIndex, i));
				}

				sb.append(replacement);

				lastReplacementIndex = i + 1;
			}
		}

		if (sb == null) {
			return text;
		}

		if (lastReplacementIndex < text.length()) {
			sb.append(text.substring(lastReplacementIndex));
		}

		return sb.toString();
	}

	protected abstract BaseURL getBaseURL(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
