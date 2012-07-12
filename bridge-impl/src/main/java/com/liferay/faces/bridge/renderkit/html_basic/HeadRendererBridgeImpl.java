/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.portlet.PortletRequest;
import javax.portlet.faces.component.PortletNamingContainerUIViewRoot;

import com.liferay.faces.bridge.application.ResourceInfo;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.bridge.renderkit.bridge.BridgeRenderer;


/**
 * This class is a JSF renderer that is designed for use with the h:head component tag. Portlets are forbidden from
 * rendering the <head>...</head> section, which is what is done by the JSF implementation's version of this renderer.
 * This renderer avoids rendering the <head>...</head> section and instead delegates that responsibility to the portal.
 *
 * @author  Neil Griffin
 */
public class HeadRendererBridgeImpl extends BridgeRenderer {

	// Private Constants
	private static final String ADDED = UIComponentBase.class.getName() + ".ADDED";
	private static final String EXTENSION_CSS = "css";
	private static final String FIRST_FACET = "first";
	private static final String MIDDLE_FACET = "middle";
	private static final String LAST_FACET = "last";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(HeadRendererBridgeImpl.class);

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Build up a list of components that are intended for the <head> section of the portal page.
		PortletNamingContainerUIViewRoot uiViewRoot = (PortletNamingContainerUIViewRoot) facesContext.getViewRoot();
		List<UIComponent> uiComponentResources = new ArrayList<UIComponent>();

		// Add the list of components that are to appear first.
		List<UIComponent> firstResources = getFirstResources(facesContext, uiComponent);

		if (firstResources != null) {
			uiComponentResources.addAll(firstResources);
		}

		// Sort the components that are in the view root into stylesheets and scripts.
		List<UIComponent> uiViewRootComponentResources = uiViewRoot.getComponentResources(facesContext, TARGET_HEAD);
		List<UIComponent> uiViewRootStyleSheetResources = null;
		List<UIComponent> uiViewRootScriptResources = null;

		for (UIComponent curComponent : uiViewRootComponentResources) {
			String resourceName = (String) curComponent.getAttributes().get("name");

			if ((resourceName != null) && resourceName.endsWith(EXTENSION_CSS)) {

				if (uiViewRootStyleSheetResources == null) {
					uiViewRootStyleSheetResources = new ArrayList<UIComponent>();
				}

				uiViewRootStyleSheetResources.add(curComponent);
			}
			else {

				if (uiViewRootScriptResources == null) {
					uiViewRootScriptResources = new ArrayList<UIComponent>();
				}

				uiViewRootScriptResources.add(curComponent);
			}
		}

		// Add the list of stylesheet components that are in the view root.
		if (uiViewRootStyleSheetResources != null) {
			uiComponentResources.addAll(uiViewRootStyleSheetResources);
		}

		// Add the list of components that are to appear in the middle.
		List<UIComponent> middleResources = getMiddleResources(facesContext, uiComponent);

		if (middleResources != null) {
			uiComponentResources.addAll(middleResources);
		}

		// Add the list of script components that are in the view root.
		if (uiViewRootScriptResources != null) {
			uiComponentResources.addAll(uiViewRootScriptResources);
		}

		// Add the list of components that are to appear last.
		List<UIComponent> lastResources = getLastResources(facesContext, uiComponent);

		if (lastResources != null) {
			uiComponentResources.addAll(lastResources);
		}

		// Initializations
		boolean ajaxRequest = facesContext.getPartialViewContext().isAjaxRequest();
		List<UIComponent> resourcesForAddingToHead = new ArrayList<UIComponent>();
		List<UIComponent> resourcesForRelocatingToBody = new ArrayList<UIComponent>();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletContainer portletContainer = bridgeContext.getPortletContainer();
		boolean portletContainerAbleToAddScriptResourceToHead = portletContainer.isAbleToAddScriptResourceToHead();

		// Note: The HeadManagedBean is a ViewScoped manage-bean that keeps a list of resources that have
		// been added to the <head> section of the portal page.
		HeadManagedBean headManagedBean = HeadManagedBean.getInstance(facesContext);
		Set<String> headResourceIdsFromManagedBean = headManagedBean.getHeadResourceIds();

		// For each resource in the ViewRoot: Determine if it should added to the <head> section of the portal page,
		// or if it should be relocated to the body (which is actually not a <body> element, but a <div> element
		// rendered by the bridge's BodyRenderer).
		for (UIComponent uiComponentResource : uiComponentResources) {

			// If this is taking place during an Ajax request, then
			if (ajaxRequest) {

				// If the resource is already present in the <head> section of the portal page (which would have
				// taken place during the initial page HTTP-GET render), then there is nothing to do. Otherwise,
				// since this is Ajax, it is not possible to add it to the <head> section and the resource has to be
				// relocated to the body.
				ResourceInfo resourceInfo = new ResourceInfo(uiComponentResource);
				boolean alreadyPresentInPortalPageHead = headResourceIdsFromManagedBean.contains(resourceInfo.getId());

				if (alreadyPresentInPortalPageHead) {

					if (logger.isDebugEnabled()) {
						logger.debug(
							"Resource already present in head: name=[{0}] library=[{1}] rendererType=[{2}] value=[{3}] className=[{4}]",
							new Object[] {
								resourceInfo.getName(), resourceInfo.getLibrary(), resourceInfo.getRendererType(),
								resourceInfo.getValue(), resourceInfo.getClassName(),
							});
					}
				}
				else {
					logger.debug(
						"Relocating resource to body (since it was added via Ajax and is not yet present in head): name=[{0}] library=[{1}] rendererType=[{2}] value=[{3}] className=[{4}]",
						new Object[] {
							resourceInfo.getName(), resourceInfo.getLibrary(), resourceInfo.getRendererType(),
							resourceInfo.getValue(), resourceInfo.getClassName(),
						});

					// Add the resource the list of resources that are to be relocated to the body.
					resourcesForRelocatingToBody.add(uiComponentResource);
				}
			}

			// Otherwise,
			else {

				// If the portlet container has the ability to add resources to the <head> section of the portal
				// page, then add it to the list of resources that are to be added to the <head> section.
				if (portletContainerAbleToAddScriptResourceToHead) {
					resourcesForAddingToHead.add(uiComponentResource);
				}

				// Otherwise, we have no choice but to add it to the list of resources that are to be relocated to
				// the body.
				else {
					resourcesForRelocatingToBody.add(uiComponentResource);
				}
			}
		}

		// If the portlet container has the ability to add resources to the <head> section of the portal page, then
		if (portletContainerAbleToAddScriptResourceToHead) {

			// Save a temporary reference to the ResponseWriter provided by the FacesContext.
			ResponseWriter responseWriterBackup = facesContext.getResponseWriter();

			// Replace the ResponseWriter in the FacesContext with a HeadResponseWriter that knows how to write to
			// the <head>...</head> section of the rendered portal page.
			HeadResponseWriter headResponseWriter = (HeadResponseWriter) portletRequest.getAttribute(
					"headResponseWriter");

			if (headResponseWriter == null) {
				headResponseWriter = (HeadResponseWriter) portletContainer.getHeadResponseWriter(responseWriterBackup);
			}

			portletRequest.setAttribute("headResponseWriter", headResponseWriter);
			facesContext.setResponseWriter(headResponseWriter);

			// For each resource:
			for (UIComponent uiComponentResource : resourcesForAddingToHead) {

				// Command the resource to render itself to the HeadResponseWriter
				uiComponentResource.encodeAll(facesContext);

				// Statefully mark the resource as having been added.
				ResourceInfo resourceInfo = new ResourceInfo(uiComponentResource);
				headResourceIdsFromManagedBean.add(resourceInfo.getId());
			}

			// Restore the temporary ResponseWriter reference.
			facesContext.setResponseWriter(responseWriterBackup);
		}

		// Relocate resources to the body if necessary. Note that the "ADDED" attribute has to be set to true
		// in order to prevent events from firing during the relocation process.
		for (UIComponent uiComponentResource : resourcesForRelocatingToBody) {

			uiComponentResource.getAttributes().put(ORIGINAL_TARGET, TARGET_HEAD);
			uiComponentResource.getAttributes().put(ADDED, Boolean.TRUE);
			uiViewRoot.addComponentResource(facesContext, uiComponentResource, TARGET_BODY);

			if (logger.isDebugEnabled()) {
				ResourceInfo resourceInfo = new ResourceInfo(uiComponentResource);

				logger.debug(
					"Relocating resource to body: name=[{0}] library=[{1}] rendererType=[{2}] value=[{3}] className=[{4}]",
					new Object[] {
						resourceInfo.getName(), resourceInfo.getLibrary(), resourceInfo.getRendererType(),
						resourceInfo.getValue(), resourceInfo.getClassName(),
					});
			}
		}

	}

	protected List<UIComponent> getFirstResources(FacesContext facesContext, UIComponent uiComponent) {

		List<UIComponent> resources = null;

		UIComponent firstFacet = uiComponent.getFacet(FIRST_FACET);

		if (firstFacet != null) {
			resources = new ArrayList<UIComponent>();
			resources.add(firstFacet);
		}

		return resources;
	}

	protected List<UIComponent> getLastResources(FacesContext facesContext, UIComponent uiComponent) {

		List<UIComponent> resources = null;

		UIComponent firstFacet = uiComponent.getFacet(LAST_FACET);

		if (firstFacet != null) {
			resources = new ArrayList<UIComponent>();
			resources.add(firstFacet);
		}

		return resources;
	}

	protected List<UIComponent> getMiddleResources(FacesContext facesContext, UIComponent uiComponent) {

		List<UIComponent> resources = null;

		UIComponent firstFacet = uiComponent.getFacet(MIDDLE_FACET);

		if (firstFacet != null) {
			resources = new ArrayList<UIComponent>();
			resources.add(firstFacet);
		}

		return resources;
	}
}
