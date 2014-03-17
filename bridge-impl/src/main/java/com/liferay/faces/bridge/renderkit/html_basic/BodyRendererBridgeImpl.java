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
package com.liferay.faces.bridge.renderkit.html_basic;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.portlet.faces.component.PortletNamingContainerUIViewRoot;

import com.liferay.faces.bridge.component.ComponentResource;
import com.liferay.faces.bridge.component.ComponentResourceFactory;
import com.liferay.faces.bridge.component.ComponentResourceUtil;
import com.liferay.faces.bridge.renderkit.bridge.BridgeRenderer;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class is a JSF renderer that is designed for use with the h:body component tag. Portlets are forbidden from
 * rendering the &lt;body&gt; and &lt;/body&gt; elements, which is what is done by the JSF implementation's version of
 * this renderer. This class will render &lt;div&gt; and &lt;/div&gt; elements instead.
 *
 * @author  Neil Griffin
 */
public class BodyRendererBridgeImpl extends BridgeRenderer {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BodyRendererBridgeImpl.class);

	// Private Constants
	private static final String ATTR_STYLE_CLASS = "styleClass";
	private static final String ELEMENT_DIV = "div";
	private static final String[] BODY_PASS_THRU_ATTRIBUTES = new String[] {
			"onclick", "ondblclick", "onkeydown", "onkeypress", "onkeyup", "onload", "onmousedown", "onmousemove",
			"onmouseout", "onmouseover", "onmouseup", "onunload", ATTR_STYLE_CLASS, "title"
		};
	private static final String STYLE_CLASS_PORTLET_BODY = "liferay-faces-bridge-body";

	/**
	 * It is forbidden for a portlet to render the &amp;&lt;body&amp;&gt; element, so instead, render a
	 * &amp;&lt;div&amp;&gt;element.
	 *
	 * @see  Renderer#encodeBegin(FacesContext, UIComponent)
	 */
	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Render the opening <div> tag.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(ELEMENT_DIV, uiComponent);

		PortletNamingContainerUIViewRoot viewRoot = (PortletNamingContainerUIViewRoot) facesContext.getViewRoot();
		String id = viewRoot.getContainerClientId(facesContext);
		responseWriter.writeAttribute("id", id, null);

		// Render the HTML "pass-thru" attributes on the <div> tag.
		for (int i = 0; i < BODY_PASS_THRU_ATTRIBUTES.length; i++) {
			String attributeName = BODY_PASS_THRU_ATTRIBUTES[i];
			String renderedName = attributeName;
			Object attributeValue = uiComponent.getAttributes().get(attributeName);

			if (attributeName.equals(ATTR_STYLE_CLASS)) {
				renderedName = "class";

				// Add a special CSS class name in order to clue-in the developer who might be examining the rendered
				// markup that a <div> was rendered instead of <body>.
				if (attributeValue == null) {
					attributeValue = STYLE_CLASS_PORTLET_BODY;
				}
				else {
					attributeValue = attributeValue.toString() + " " + STYLE_CLASS_PORTLET_BODY;
				}
			}

			if (attributeValue != null) {
				responseWriter.writeAttribute(renderedName, attributeValue, attributeName);
			}
		}

		// Render all of the stylesheet resources, since they often need to be loaded as close to the top as possible.
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		List<UIComponent> uiComponentResources = uiViewRoot.getComponentResources(facesContext, TARGET_BODY);

		if (uiComponentResources != null) {

			ComponentResourceFactory componentResourceFactory = (ComponentResourceFactory) FactoryExtensionFinder
				.getFactory(ComponentResourceFactory.class);

			for (UIComponent uiComponentResource : uiComponentResources) {

				String originalTarget = (String) uiComponentResource.getAttributes().get(ORIGINAL_TARGET);

				if (TARGET_HEAD.equals(originalTarget)) {

					ComponentResource componentResource = componentResourceFactory.getComponentResource(
							uiComponentResource);

					if (componentResource.isRenderable()) {
						uiComponentResource.encodeAll(facesContext);

						if (logger.isDebugEnabled()) {

							if (logger.isDebugEnabled()) {

								logger.debug(
									"Rendering resource just after opening liferay-faces-bridge-body <div> name=[{0}] library=[{1}] rendererType=[{2}] value=[{3}] className=[{4}]",
									new Object[] {
										componentResource.getName(), componentResource.getLibrary(),
										uiComponentResource.getRendererType(),
										ComponentResourceUtil.getComponentValue(uiComponentResource),
										uiComponentResource.getClass().getName(),
									});
							}
						}
					}
					else {
						logger.debug("Skipped rendering componentResourceId=[{0}]", componentResource.getId());
					}
				}
			}
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Render all of the non-stylesheet resources.
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		List<UIComponent> uiComponentResources = uiViewRoot.getComponentResources(facesContext, TARGET_BODY);

		if (uiComponentResources != null) {

			ComponentResourceFactory componentResourceFactory = (ComponentResourceFactory) FactoryExtensionFinder
				.getFactory(ComponentResourceFactory.class);

			for (UIComponent uiComponentResource : uiComponentResources) {

				String originalTarget = (String) uiComponentResource.getAttributes().get(ORIGINAL_TARGET);

				if (!TARGET_HEAD.equals(originalTarget)) {

					ComponentResource componentResource = componentResourceFactory.getComponentResource(
							uiComponentResource);

					if (componentResource.isRenderable()) {
						uiComponentResource.encodeAll(facesContext);

						if (logger.isDebugEnabled()) {

							logger.debug(
								"Rendering resource just before closing liferay-faces-bridge-body </div> name=[{0}] library=[{1}] rendererType=[{2}] value=[{3}] className=[{4}]",
								new Object[] {
									componentResource.getName(), componentResource.getLibrary(),
									uiComponentResource.getRendererType(),
									ComponentResourceUtil.getComponentValue(uiComponentResource),
									uiComponentResource.getClass().getName(),
								});
						}
					}
					else {
						logger.debug("Skipped rendering componentResourceId=[{0}]", componentResource.getId());
					}
				}
			}
		}

		// Render the closing </div> tag.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(ELEMENT_DIV);
	}

}
