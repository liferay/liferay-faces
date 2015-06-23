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
package com.liferay.faces.alloy.component.media.internal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.media.Media;
import com.liferay.faces.util.application.FacesResource;
import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
public abstract class MediaRenderer extends MediaRendererCompat {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MediaRenderer.class);

	// Private Constants
	private static final String HEIGHT = "height";
	protected static final String WIDTH = "width";
	private static final String RES_NOT_FOUND_ERROR_MSG =
		"Resource handler=[{0}] was unable to create a resource for resourceName=[{1}] libraryName=[{2}]";
	private static final String RES_NOT_FOUND = "RES_NOT_FOUND";
	private static final String[] MEDIA_DOM_EVENTS = {
			"onabort", "onblur", "oncanplay", "oncanplaythrough", "ondurationchange", "onemptied", "onended", "onerror",
			"onfocus", "onloadeddata", "onloadedmetadata", "onloadstart", "onpause", "onplay", "onplaying",
			"onprogress", "onratechange", "onseeked", "onseeking", "onstalled", "onsuspend", "ontimeupdate",
			"onvolumechange", "onwaiting"
		};

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);

		String clientId = uiComponent.getClientId(facesContext);

		responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);
		responseWriter.writeAttribute("name", clientId, null);

		Media media = (Media) uiComponent;
		RendererUtil.encodeStyleable(responseWriter, media);
		responseWriter.startElement(getMediaType(), media);

		boolean autoplay = media.isAutoplay();

		if (autoplay) {
			responseWriter.writeAttribute(AUTOPLAY, "true", AUTOPLAY);
		}

		boolean controls = media.isControls();

		if (controls) {
			responseWriter.writeAttribute(CONTROLS, "true", CONTROLS);
		}

		boolean loop = media.isLoop();

		if (loop) {
			responseWriter.writeAttribute(LOOP, "true", LOOP);
		}

		boolean muted = media.isMuted();

		if (muted) {
			responseWriter.writeAttribute(MUTED, "true", MUTED);
		}

		String preload = media.getPreload();

		if (preload != null) {
			responseWriter.writeAttribute(PRELOAD, preload, PRELOAD);
		}

		encodeMediaSize(responseWriter, media);

		// Encode JSF 2.1 passthrough attributes
		encodePassThroughAttributes(responseWriter, uiComponent, KEYBOARD_DOM_EVENTS);
		encodePassThroughAttributes(responseWriter, uiComponent, MOUSE_DOM_EVENTS);
		encodePassThroughAttributes(responseWriter, uiComponent, MEDIA_DOM_EVENTS);

		// Encode JSF 2.2 passthrough attributes (attributes prefixed with the http://xmlns.jcp.org/jsf/passthrough
		// namespace)
		encodeJSF22PassthroughAttributes(media, responseWriter);

		encodeCustomMediaAttributes(facesContext, responseWriter, media);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Media media = (Media) uiComponent;
		String name = media.getName();
		Object value = media.getValue();
		Application application = facesContext.getApplication();
		ResourceHandler resourceHandler = application.getResourceHandler();
		String firstMediaResourceURL = null;
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (name != null) {

			String library = media.getLibrary();

			if ((name.length() > 0) && (library != null) && (library.length() > 0)) {

				Resource mediaResource = resourceHandler.createResource(name, library);
				String contentType = media.getContentType();

				if (mediaResource != null) {

					String mediaRequestPath = mediaResource.getRequestPath();
					ExternalContext externalContext = facesContext.getExternalContext();
					firstMediaResourceURL = externalContext.encodeResourceURL(mediaRequestPath);

					if (contentType == null) {
						contentType = mediaResource.getContentType();
					}
				}
				else {
					firstMediaResourceURL = RES_NOT_FOUND;
					logger.error(RES_NOT_FOUND_ERROR_MSG, resourceHandler, name, library);
				}

				encodeMediaSource(responseWriter, firstMediaResourceURL, contentType);
			}
		}
		else if (value != null) {

			if ((value instanceof Collection) || (value instanceof FacesResource[])) {

				Collection<FacesResource> facesResources;

				if (value instanceof Collection) {
					facesResources = (Collection<FacesResource>) value;
				}
				else {
					FacesResource[] resourcesAsArray = (FacesResource[]) value;
					facesResources = Arrays.asList(resourcesAsArray);
				}

				for (FacesResource facesResource : facesResources) {

					String facesResourceName = facesResource.getName();
					String facesResourceLibrary = facesResource.getLibrary();
					Resource mediaResource = resourceHandler.createResource(facesResourceName, facesResourceLibrary);
					String mediaResourceURL;
					String contentType = facesResource.getContentType();

					if (mediaResource != null) {

						String mediaRequestPath = mediaResource.getRequestPath();
						ExternalContext externalContext = facesContext.getExternalContext();
						mediaResourceURL = externalContext.encodeResourceURL(mediaRequestPath);

						if (contentType == null) {
							contentType = mediaResource.getContentType();
						}
					}
					else {
						mediaResourceURL = RES_NOT_FOUND;
						logger.error(RES_NOT_FOUND_ERROR_MSG, resourceHandler, facesResourceName, facesResourceLibrary);
					}

					if (firstMediaResourceURL == null) {
						firstMediaResourceURL = mediaResourceURL;
					}

					encodeMediaSource(responseWriter, mediaResourceURL, contentType);
				}
			}
			else {

				String contentType = media.getContentType();

				if (value instanceof FacesResource) {

					FacesResource facesResource = (FacesResource) value;
					String facesResourceName = facesResource.getName();
					String facesResourceLibrary = facesResource.getLibrary();
					Resource mediaResource = resourceHandler.createResource(facesResourceName, facesResourceLibrary);

					if (contentType == null) {
						contentType = facesResource.getContentType();
					}

					if (mediaResource != null) {

						String mediaRequestPath = mediaResource.getRequestPath();
						ExternalContext externalContext = facesContext.getExternalContext();
						firstMediaResourceURL = externalContext.encodeResourceURL(mediaRequestPath);

						if (contentType == null) {
							contentType = mediaResource.getContentType();
						}
					}
					else {
						firstMediaResourceURL = RES_NOT_FOUND;
						logger.error(RES_NOT_FOUND_ERROR_MSG, resourceHandler, facesResourceName, facesResourceLibrary);
					}
				}
				else {

					String valueAsString = value.toString();
					firstMediaResourceURL = getEncodedResourceURL(facesContext, resourceHandler, application,
							valueAsString);
				}

				encodeMediaSource(responseWriter, firstMediaResourceURL, contentType);
			}
		}

		List<UIComponent> children = uiComponent.getChildren();

		for (UIComponent child : children) {

			if (child instanceof com.liferay.faces.alloy.component.resource.Resource) {

				String mediaResourceURL;
				com.liferay.faces.alloy.component.resource.Resource resource =
					(com.liferay.faces.alloy.component.resource.Resource) child;
				String contentType = resource.getContentType();
				String resourceName = resource.getName();
				String resourceLibrary = resource.getLibrary();
				Resource mediaResource = resourceHandler.createResource(resourceName, resourceLibrary);

				if (mediaResource != null) {

					String mediaRequestPath = mediaResource.getRequestPath();
					ExternalContext externalContext = facesContext.getExternalContext();
					mediaResourceURL = externalContext.encodeResourceURL(mediaRequestPath);

					if (contentType == null) {
						contentType = mediaResource.getContentType();
					}
				}
				else {
					mediaResourceURL = RES_NOT_FOUND;
					logger.error(RES_NOT_FOUND_ERROR_MSG, resourceHandler, resourceName, resourceLibrary);
				}

				encodeMediaSource(responseWriter, mediaResourceURL, contentType);

				if (firstMediaResourceURL == null) {
					firstMediaResourceURL = mediaResourceURL;
				}
			}
			else {
				child.encodeAll(facesContext);
			}
		}

		// If no media has been specified, then warn the developer.
		if (firstMediaResourceURL == null) {
			logger.warn("No {0} has been specified for <alloy:{0} id=[{1}] />.", getMediaType(), media.getClientId());
		}

		// Otherwise, if degredation is enabled, encode the flash player as a fallback for HTML4 browsers using the
		// first media URL.
		else if (media.isDegrade()) {
			encodeFlashPlayer(facesContext, responseWriter, media, firstMediaResourceURL);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(getMediaType());
		responseWriter.endElement("div");
	}

	protected abstract void encodeCustomMediaAttributes(FacesContext facesContext, ResponseWriter responseWriter,
		Media media) throws IOException;

	protected abstract void encodeFlashPlayerChildren(FacesContext facesContext, ResponseWriter responseWriter,
		Media media, String mediaResourceURL, ResourceHandler resourceHandler, Application application,
		boolean defaultFlashPlayer) throws IOException;

	protected void encodeFlashPlayer(FacesContext facesContext, ResponseWriter responseWriter, Media media,
		String mediaResourceURL) throws IOException {

		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());
		boolean browserIE = browserSniffer.isIe();
		responseWriter.startElement("object", null);
		encodeMediaSize(responseWriter, media);

		Application application = facesContext.getApplication();
		ResourceHandler resourceHandler = application.getResourceHandler();
		Object flashPlayer = media.getFlashPlayer();
		String flashPlayerURL;

		// If the developer has specified a Flash player, then
		if (flashPlayer != null) {
			flashPlayerURL = getEncodedResourceURL(facesContext, resourceHandler, application, flashPlayer);
		}

		// Otherwise, get the default Alloy Flash player.
		else {

			Resource defaultFlashPlayerResource = resourceHandler.createResource(getDefaultFlashPlayerName(),
					"liferay-faces-alloy");
			String defaultFlashPlayerRequestPath = defaultFlashPlayerResource.getRequestPath();
			ExternalContext externalContext = facesContext.getExternalContext();
			flashPlayerURL = externalContext.encodeResourceURL(defaultFlashPlayerRequestPath);
		}

		if (browserIE) {

			responseWriter.writeAttribute("classid", "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000", null);

			String flashPlayerVersion = media.getFlashPlayerVersion();
			String codebaseURL = "http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=".concat(
					flashPlayerVersion);
			responseWriter.writeAttribute("codebase", codebaseURL, null);

			responseWriter.startElement("param", null);
			responseWriter.writeAttribute("name", "movie", null);
			responseWriter.writeAttribute("value", flashPlayerURL, null);
			responseWriter.endElement("param");
		}
		else {

			responseWriter.writeAttribute("data", flashPlayerURL, null);
			responseWriter.writeAttribute("type", "application/x-shockwave-flash", null);
		}

		encodeFlashPlayerChildren(facesContext, responseWriter, media, mediaResourceURL, resourceHandler, application,
			(flashPlayer == null));
		responseWriter.endElement("object");
	}

	protected void encodeMediaSize(ResponseWriter responseWriter, Media media) throws IOException {

		StringBuilder styleStringBuilder = new StringBuilder();
		String height = getHeight(media);

		if (height == null) {
			styleStringBuilder.append("height:100%;");
		}

		responseWriter.writeAttribute(HEIGHT, height, HEIGHT);

		String width = getWidth(media);

		if (width == null) {
			styleStringBuilder.append("width:100%;");
		}

		responseWriter.writeAttribute(WIDTH, width, WIDTH);

		if (styleStringBuilder.length() > 0) {
			responseWriter.writeAttribute(STYLE, styleStringBuilder, null);
		}
	}

	protected void encodeMediaSource(ResponseWriter responseWriter, String mediaResourceURL, String contentType)
		throws IOException {

		responseWriter.startElement("source", null);
		responseWriter.writeAttribute("src", mediaResourceURL, null);

		if (contentType != null) {
			responseWriter.writeAttribute("type", contentType, null);
		}
		else {
			logger.warn("The contentType of ResourceURL=[{0}] was not specified.", mediaResourceURL);
		}

		responseWriter.endElement("source");
	}

	protected abstract String getDefaultFlashPlayerName();

	protected String getEncodedResourceURL(FacesContext facesContext, ResourceHandler resourceHandler,
		Application application, Object value) {

		String encodedResourceURL;

		if (value instanceof FacesResource) {

			FacesResource facesResource = (FacesResource) value;
			String name = facesResource.getName();
			String library = facesResource.getLibrary();
			Resource resource = resourceHandler.createResource(name, library);

			if (resource != null) {

				String mediaRequestPath = resource.getRequestPath();
				ExternalContext externalContext = facesContext.getExternalContext();
				encodedResourceURL = externalContext.encodeResourceURL(mediaRequestPath);
			}
			else {
				encodedResourceURL = RES_NOT_FOUND;
				logger.error(RES_NOT_FOUND_ERROR_MSG, resourceHandler, name, library);
			}
		}
		else {
			String posterString = value.toString();
			encodedResourceURL = getEncodedResourceURL(facesContext, resourceHandler, application, posterString);
		}

		return encodedResourceURL;
	}

	private String getEncodedResourceURL(FacesContext facesContext, ResourceHandler resourceHandler,
		Application application, String name) {

		String encodedResourceURL = name;

		if (!isFacesResourceURL(resourceHandler, name)) {

			ViewHandler viewHandler = application.getViewHandler();
			String resourceURL = viewHandler.getResourceURL(facesContext, name);
			ExternalContext externalContext = facesContext.getExternalContext();
			encodedResourceURL = externalContext.encodeResourceURL(resourceURL);
		}

		return encodedResourceURL;
	}

	protected String getHeight(Media media) {
		return null;
	}

	protected abstract String getMediaType();

	@Override
	public boolean getRendersChildren() {
		return true;
	}

	protected String getWidth(Media media) {
		return null;
	}
}
