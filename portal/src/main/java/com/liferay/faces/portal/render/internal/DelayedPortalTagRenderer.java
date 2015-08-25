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
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This abstract class serves as a generic JSF {@link Renderer} that invokes the JSP tag lifecycle of a Liferay Portal
 * JSP tag and encodes the output. Unlike its parent {@link PortalTagRenderer}, encoding of the JSP tag output is
 * delayed so that child JSP tags have an opportunity to influence rendering of their parent JSP tag.
 *
 * @author  Juan Gonzalez
 */
public abstract class DelayedPortalTagRenderer<U extends UIComponent, T extends Tag> extends PortalTagRenderer<U, T> {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DelayedPortalTagRenderer.class);

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Create an instance of the JSP tag that corresponds to the JSF component.
		T tag = newTag();
		copyAttributes(facesContext, cast(uiComponent), tag);
		uiComponent.getAttributes().put(CORRESPONDING_JSP_TAG, tag);
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode each of the children using a writer that can capture the child markup as a string.
		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		RenderKit renderKit = facesContext.getRenderKit();
		StringWriter bufferedChildrenMarkupWriter = new StringWriter();
		ResponseWriter stringResponseWriter = renderKit.createResponseWriter(bufferedChildrenMarkupWriter, null,
				"UTF-8");
		facesContext.setResponseWriter(stringResponseWriter);

		List<UIComponent> children = uiComponent.getChildren();

		for (UIComponent child : children) {
			child.encodeAll(facesContext);
		}

		facesContext.setResponseWriter(originalResponseWriter);

		// Get the output of the JSP tag (and all child JSP tags).
		Map<String, Object> componentAttributes = uiComponent.getAttributes();
		Tag tag = (Tag) componentAttributes.get(CORRESPONDING_JSP_TAG);
		PortalTagOutput portalTagOutput;

		try {
			portalTagOutput = getPortalTagOutput(facesContext, uiComponent, tag);

			String preChildMarkup = portalTagOutput.getMarkup();
			String postChildMarkup = null;

			// Determine the point at which children should be inserted into the markup.
			String childInsertionMarker = getChildInsertionMarker();

			if (childInsertionMarker != null) {

				int pos = preChildMarkup.indexOf(childInsertionMarker);

				if (pos > 0) {
					postChildMarkup = preChildMarkup.substring(pos).trim();
					preChildMarkup = preChildMarkup.substring(0, pos).trim();
				}
			}

			// Encode the output of the JSP tag up until the point at which children should be inserted.
			StringBuilder markup = new StringBuilder(3);

			markup.append(preChildMarkup);

			// Ensure that scripts are rendered at the bottom of the page.
			String scripts = portalTagOutput.getScripts();

			if ((scripts != null) && (scripts.length() > 0)) {

				logger.debug("Scripts before transformation:{0}", scripts);

				String processedScripts = getScripts(uiComponent, scripts);
				logger.debug("Scripts after transformation:{0}", processedScripts);

				FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
				facesRequestContext.addScript(processedScripts);
			}

			// Encode the children markup.
			String childrenMarkup = bufferedChildrenMarkupWriter.toString();

			if (childrenMarkup != null) {
				markup.append(childrenMarkup);
			}

			// Encode the output of the JSP tag that is to appear after the children.
			if (postChildMarkup != null) {
				markup.append(postChildMarkup);
			}

			ResponseWriter responseWriter = facesContext.getResponseWriter();
			logger.debug("Markup before transformation:{0}", markup);

			StringBuilder processedMarkup = getMarkup(uiComponent, markup);
			logger.debug("Markup after transformation:{0}", processedMarkup);
			responseWriter.write(processedMarkup.toString());
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		uiComponent.getAttributes().remove(CORRESPONDING_JSP_TAG);
	}

	protected StringBuilder getMarkup(UIComponent uiComponent, StringBuilder markup) throws Exception {
		return markup;
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

	protected String getScripts(UIComponent uiComponent, String scripts) throws Exception {
		return scripts;
	}
}
