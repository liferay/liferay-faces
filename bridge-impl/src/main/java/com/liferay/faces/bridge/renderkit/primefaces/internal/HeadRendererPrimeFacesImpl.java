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
package com.liferay.faces.bridge.renderkit.primefaces.internal;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.component.internal.ResourceComponent;
import com.liferay.faces.bridge.renderkit.html_basic.internal.HeadRendererBridgeImpl;
import com.liferay.faces.bridge.util.internal.URLUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class replaces the PrimeFaces HeadRenderer because it renders a &lt;head&gt;...&lt;/head&gt; element to the
 * response writer which is forbidden in a portlet environment.
 *
 * @author  Neil Griffin
 */
public class HeadRendererPrimeFacesImpl extends HeadRendererBridgeImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(HeadRendererPrimeFacesImpl.class);

	// Private Constants
	private static final String PRIMEFACES_THEME_DEFAULT = "aristo";
	private static final String PRIMEFACES_THEME_PARAM = "primefaces.THEME";
	private static final String PRIMEFACES_THEME_NONE = "none";
	private static final String PRIMEFACES_THEME_PREFIX = "primefaces-";
	private static final String PRIMEFACES_THEME_RESOURCE_NAME = "theme.css";
	private static final Renderer PRIMEFACES_HEAD_RENDERER;

	static {

		Renderer primeFacesHeadRenderer = null;

		try {
			Class<?> headRendererClass = Class.forName("org.primefaces.renderkit.HeadRenderer");
			primeFacesHeadRenderer = (Renderer) headRendererClass.newInstance();
		}
		catch (Exception e) {
			logger.error(e);
		}

		PRIMEFACES_HEAD_RENDERER = primeFacesHeadRenderer;
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Invoke the PrimeFaces HeadRenderer so that it has the opportunity to add css and/or script resources to the
		// view root. However, the PrimeFaces HeadRenderer must be captured (and thus prevented from actually rendering
		// any resources) so that they can instead be rendered by the superclass.
		FacesContext primeFacesContext = new FacesContextPrimeFacesHeadImpl(facesContext);
		ResponseWriter origResponseWriter = primeFacesContext.getResponseWriter();
		PrimeFacesHeadResponseWriter primeFacesHeadResponseWriter = new PrimeFacesHeadResponseWriter();
		primeFacesContext.setResponseWriter(primeFacesHeadResponseWriter);

		UIViewRoot originalUIViewRoot = facesContext.getViewRoot();
		ResourceCapturingUIViewRoot resourceCapturingUIViewRoot = new ResourceCapturingUIViewRoot();
		primeFacesContext.setViewRoot(resourceCapturingUIViewRoot);
		PRIMEFACES_HEAD_RENDERER.encodeBegin(primeFacesContext, uiComponent);
		primeFacesContext.setViewRoot(originalUIViewRoot);
		primeFacesContext.setResponseWriter(origResponseWriter);

		// Get the list of captured resources.
		List<UIComponent> capturedResources = resourceCapturingUIViewRoot.getCapturedComponentResources("head");

		// The PrimeFaces 5.1+ HeadRenderer properly adds resources like "validation/validation.js" to the view root,
		// which makes it possible to easily capture the resources that it wants to add to the head. However, the
		// PrimeFaces 5.0/4.0 HeadRenderer does not add resources to the view root. Instead, it encodes a <script>
		// element to the response writer with a "src" attribute containing a URL (an external script). When this
		// occurs, it is necessary to reverse-engineer the URL of each external script in order to determine the
		// name/library of the corresponding JSF2 resource.
		List<String> externalScriptURLs = primeFacesHeadResponseWriter.getExternalScriptURLs();

		// For each external script URL:
		if (externalScriptURLs.size() > 0) {

			ExternalContext externalContext = facesContext.getExternalContext();
			PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
			String namespace = portletResponse.getNamespace();
			String resourceNameParam = namespace + "javax.faces.resource";
			String libraryNameParam = namespace + "ln";

			for (String externalScriptURL : externalScriptURLs) {

				// Determine the value of the "javax.faces.resource" and "ln" parameters from the URL.
				String resourceName = null;
				String libraryName = null;
				String decodedExternalScriptURL = URLDecoder.decode(externalScriptURL, StringPool.UTF8);
				Map<String, String[]> parsedParameterMapValuesArray = URLUtil.parseParameterMapValuesArray(
						decodedExternalScriptURL);

				if (parsedParameterMapValuesArray != null) {
					String[] resourceNameParamValues = parsedParameterMapValuesArray.get(resourceNameParam);

					if ((resourceNameParamValues != null) && (resourceNameParamValues.length > 0)) {
						resourceName = resourceNameParamValues[0];
					}

					String[] libraryNameParamValues = parsedParameterMapValuesArray.get(libraryNameParam);

					if ((libraryNameParamValues != null) && (libraryNameParamValues.length > 0)) {
						libraryName = libraryNameParamValues[0];
					}
				}

				// If the "javax.faces.resource" and "ln" parameters were found, then create the corresponding JSF2
				// resource and add it to the view root.
				if ((resourceName != null) && (libraryName != null)) {
					Application application = facesContext.getApplication();
					ResourceHandler resourceHandler = application.getResourceHandler();
					UIComponent outputScript = application.createComponent(UIOutput.COMPONENT_TYPE);
					String rendererType = resourceHandler.getRendererTypeForResourceName(resourceName);
					outputScript.setRendererType(rendererType);
					outputScript.setTransient(true);
					outputScript.getAttributes().put("name", resourceName);
					outputScript.getAttributes().put("library", libraryName);
					outputScript.getAttributes().put("target", "head");
					capturedResources.add(outputScript);
				}
			}
		}

		// Add each component resources that was captured to the real view root so that they will be rendered by the
		// superclass.
		for (UIComponent componentResource : capturedResources) {
			originalUIViewRoot.addComponentResource(facesContext, componentResource, "head");
		}

		// FACES-2061: If the PrimeFaces HeadRenderer attempted to render an inline script (as is the case when
		// PrimeFaces client side validation is activated) then add a component that can render the script to the view
		// root.
		String inlineScriptText = primeFacesHeadResponseWriter.toString();

		if ((inlineScriptText != null) && (inlineScriptText.length() > 0)) {
			PrimeFacesInlineScript primeFacesInlineScript = new PrimeFacesInlineScript(inlineScriptText);
			originalUIViewRoot.addComponentResource(facesContext, primeFacesInlineScript, "head");
		}

		// Delegate rendering to the superclass so that it can write resources found in the view root to the head
		// section of the portal page.
		super.encodeBegin(facesContext, uiComponent);
	}

	@Override
	protected List<UIComponent> getFirstResources(FacesContext facesContext, UIComponent uiComponent) {

		List<UIComponent> resources = super.getFirstResources(facesContext, uiComponent);

		// PrimeFaces Theme
		ExternalContext externalContext = facesContext.getExternalContext();
		String primeFacesThemeName = externalContext.getInitParameter(PRIMEFACES_THEME_PARAM);

		if (primeFacesThemeName != null) {
			ELContext elContext = facesContext.getELContext();
			ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
			ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, primeFacesThemeName,
					String.class);
			primeFacesThemeName = (String) valueExpression.getValue(elContext);

		}
		else {
			primeFacesThemeName = PRIMEFACES_THEME_DEFAULT;
		}

		if ((primeFacesThemeName != null) && !primeFacesThemeName.equals(PRIMEFACES_THEME_NONE)) {

			if (resources == null) {
				resources = new ArrayList<UIComponent>();
			}

			String resourceLibrary = PRIMEFACES_THEME_PREFIX + primeFacesThemeName;
			ResourceComponent primeFacesStyleSheet = new ResourceComponent(facesContext, PRIMEFACES_THEME_RESOURCE_NAME,
					resourceLibrary, "head");
			resources.add(primeFacesStyleSheet);
		}

		return resources;
	}
}
