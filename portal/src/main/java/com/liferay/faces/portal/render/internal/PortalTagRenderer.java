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
package com.liferay.faces.portal.render.internal;

import java.io.IOException;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.util.jsp.PageContextAdapter;
import com.liferay.faces.util.jsp.StringJspWriter;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.portal.util.PortalUtil;


/**
 * @author  Neil Griffin
 */
public abstract class PortalTagRenderer<U extends UIComponent, T extends Tag> extends Renderer {

	/**
	 * Casts a UIComponent to a concrete instance of UIComponent.
	 */
	public abstract U cast(UIComponent uiComponent);

	/**
	 * Copy attributes from the JSF component to the JSP tag that are common between JSF and JSP.
	 */
	public abstract void copyFrameworkAttributes(U u, T t);

	/**
	 * Copy attributes from the JSF component to the JSP tag that are not common between JSF and JSP.
	 */
	public abstract void copyNonFrameworkAttributes(U u, T t);

	/**
	 * Creates a new instance of the JSP tag.
	 */
	public abstract T newTag();

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		try {
			String preChildMarkup = getTagOutput(facesContext, uiComponent);
			System.err.println("---------------");
			System.err.println(preChildMarkup);
			System.err.println("---------------");
			String childInsertionMarker = getChildInsertionMarker();

			if (childInsertionMarker != null) {

				int pos = preChildMarkup.indexOf(childInsertionMarker);

				if (pos > 0) {
					String fqcn = getClass().getName();
					String postChildMarkup = preChildMarkup.substring(pos).trim();
					facesContext.getAttributes().put(fqcn, postChildMarkup);
					preChildMarkup = preChildMarkup.substring(0, pos).trim();
				}
			}

			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.write(preChildMarkup);
		}
		catch (JspException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String fqcn = getClass().getName();
		String postChildMarkup = (String) facesContext.getAttributes().remove(fqcn);

		if (postChildMarkup != null) {
			responseWriter.write(postChildMarkup);
		}
	}

	public String getChildInsertionMarker() {
		return null;
	}

	protected String getTagOutput(FacesContext facesContext, UIComponent uiComponent) throws JspException {

		// Setup the Facelet -> JSP tag adapter.
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
		ELContext elContext = facesContext.getELContext();
		StringJspWriter stringJspWriter = new StringJspWriter();
		PageContextAdapter pageContextAdapter = new PageContextAdapter(httpServletRequest, httpServletResponse,
				elContext, stringJspWriter);

		// Create an instance of the JSP tag that corresponds to the JSF component.
		T tag = newTag();
		tag.setPageContext(pageContextAdapter);
		copyFrameworkAttributes(cast(uiComponent), tag);
		copyNonFrameworkAttributes(cast(uiComponent), tag);

		// Invoke the JSP tag lifecycle directly (rather than using the tag from a JSP).
		tag.doStartTag();
		tag.doEndTag();

		String tagOutput = pageContextAdapter.getOut().toString();
		if (tagOutput != null) {
			tagOutput = tagOutput.trim();
			tagOutput = tagOutput.replace(StringPool.NEW_LINE, StringPool.BLANK);
		}

		return tagOutput;
	}
}
