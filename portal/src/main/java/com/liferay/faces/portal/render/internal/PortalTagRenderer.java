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
package com.liferay.faces.portal.render.internal;

import java.io.IOException;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.jsp.PageContextAdapter;
import com.liferay.faces.util.jsp.StringJspWriter;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;


/**
 * This abstract class serves as a generic JSF {@link Renderer} that invokes the JSP tag lifecycle of a Liferay Portal
 * JSP tag and encodes the output.
 *
 * @author  Neil Griffin
 */
public abstract class PortalTagRenderer<U extends UIComponent, T extends Tag> extends Renderer {

	// Protected Constants
	protected static final String CORRESPONDING_JSP_TAG = "correspondingJspTag";
	protected static final String POST_CHILD_MARKUP = "postChildMarkup";

	// Self-Injections
	private static PortalTagOutputParser portalTagOutputParser = new PortalTagOutputParserImpl();

	/**
	 * Creates a new instance of the JSP tag.
	 */
	public abstract T newTag();

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		try {

			// Create an instance of the JSP tag that corresponds to the JSF component.
			T tag = newTag();
			copyAttributes(facesContext, cast(uiComponent), tag);

			Map<String, Object> componentAttributes = uiComponent.getAttributes();
			componentAttributes.put(CORRESPONDING_JSP_TAG, tag);

			// Get the output of the JSP tag (and all child JSP tags).
			PortalTagOutput portalTagOutput = getPortalTagOutput(facesContext, uiComponent, tag);
			String preChildMarkup = portalTagOutput.getMarkup();

			// Determine the point at which children should be inserted into the markup.
			String childInsertionMarker = getChildInsertionMarker();

			if (childInsertionMarker != null) {

				int pos = preChildMarkup.indexOf(childInsertionMarker);

				if (pos > 0) {
					String postChildMarkup = preChildMarkup.substring(pos).trim();
					componentAttributes.put(POST_CHILD_MARKUP, postChildMarkup);
					preChildMarkup = preChildMarkup.substring(0, pos).trim();
				}
			}

			// Encode the output of the JSP tag up until the point at which children should be inserted.
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.write(preChildMarkup);

			// Ensure that scripts are rendered at the bottom of the page.
			String scripts = portalTagOutput.getScripts();

			if (scripts != null) {

				FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
				facesRequestContext.addScript(scripts);
			}
		}
		catch (JspException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the output of the JSP tag that is to appear after the children.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		Map<String, Object> componentAttributes = uiComponent.getAttributes();
		String postChildMarkup = (String) componentAttributes.remove(POST_CHILD_MARKUP);

		if (postChildMarkup != null) {
			responseWriter.write(postChildMarkup);
		}

		componentAttributes.remove(CORRESPONDING_JSP_TAG);
	}

	/**
	 * Casts a UIComponent to a concrete instance of UIComponent.
	 */
	protected abstract U cast(UIComponent uiComponent);

	/**
	 * Copy attributes from the JSF component to the JSP tag that are common between JSF and JSP.
	 */
	protected abstract void copyFrameworkAttributes(FacesContext facesContext, U u, T t);

	/**
	 * Copy attributes from the JSF component to the JSP tag that are not common between JSF and JSP.
	 */
	protected abstract void copyNonFrameworkAttributes(FacesContext facesContext, U u, T t);

	protected void copyAttributes(FacesContext facesContext, U u, T t) {
		copyFrameworkAttributes(facesContext, u, t);
		copyNonFrameworkAttributes(facesContext, u, t);
		t.setParent(getParentTag(facesContext, u, t));
	}

	public String getChildInsertionMarker() {
		return null;
	}

	protected HttpServletRequest getHttpServletRequest(PortletRequest portletRequest) {
		return new HttpServletRequestTagSafeImpl(PortalUtil.getHttpServletRequest(portletRequest));
	}

	protected HttpServletResponse getHttpServletResponse(PortletResponse portletResponse) {
		return new HttpServletResponseTagSafeImpl(PortalUtil.getHttpServletResponse(portletResponse));
	}

	protected Tag getParentTag(FacesContext facesContext, U u, T t) {

		UIComponent uiComponent = cast(u);
		UIComponent parentComponent = uiComponent.getParent();
		Map<String, Object> parentComponentAttributes = parentComponent.getAttributes();

		return (Tag) parentComponentAttributes.get(CORRESPONDING_JSP_TAG);
	}

	protected PortalTagOutput getPortalTagOutput(FacesContext facesContext, UIComponent uiComponent, Tag tag)
		throws JspException {

		// Setup the Facelet -> JSP tag adapter.
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		HttpServletRequest httpServletRequest = getHttpServletRequest(portletRequest);
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		HttpServletResponse httpServletResponse = getHttpServletResponse(portletResponse);
		ELContext elContext = facesContext.getELContext();
		StringJspWriter stringJspWriter = new StringJspWriter();
		PageContextAdapter pageContextAdapter = new PageContextAdapter(httpServletRequest, httpServletResponse,
				elContext, stringJspWriter);

		// Invoke the JSP tag lifecycle directly (rather than using the tag from a JSP).
		tag.setPageContext(pageContextAdapter);
		tag.doStartTag();
		tag.doEndTag();

		// If executing within an Ajax request, then write all the scripts contained in the AUI_SCRIPT_DATA attribute
		// directly to the tag output.
		PartialViewContext partialViewContext = facesContext.getPartialViewContext();

		if (partialViewContext.isAjaxRequest()) {

			//J-
			// TODO: Possibly need to be concerned about inline scripts written in the <head>...</head> section during Ajax.
			//
			// StringBundler data = HtmlTopTag.getData(httpServletRequest, WebKeys.PAGE_TOP);
			//J+

			Object scriptData = httpServletRequest.getAttribute(WebKeys.AUI_SCRIPT_DATA);

			if (scriptData != null) {
				JspWriter jspWriter = pageContextAdapter.getOut();

				try {
					jspWriter.write(portalTagOutputParser.getScriptSectionMarker());
					ScriptTagUtil.flushScriptData(pageContextAdapter);
				}
				catch (IOException e) {
					throw new JspException(e);
				}
			}
		}

		// Return the tag output.
		return portalTagOutputParser.parse(pageContextAdapter);
	}
}
