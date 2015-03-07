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
package com.liferay.faces.alloy.component.video.internal;

import java.io.IOException;
import java.net.URLEncoder;

import javax.faces.application.Application;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.media.Media;
import com.liferay.faces.alloy.component.video.Video;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = Video.COMPONENT_FAMILY, rendererType = Video.RENDERER_TYPE)
//J+
public class VideoRenderer extends VideoRendererBase {

	@Override
	protected void encodeCustomMediaAttributes(FacesContext facesContext, ResponseWriter responseWriter, Media media)
		throws IOException {

		Video video = (Video) media;
		Object poster = video.getPoster();

		if (poster != null) {

			Application application = facesContext.getApplication();
			ResourceHandler resourceHandler = application.getResourceHandler();
			String posterResourceURL = getEncodedResourceURL(facesContext, resourceHandler, application, poster);
			responseWriter.writeAttribute(POSTER, posterResourceURL, POSTER);
		}
	}

	@Override
	protected void encodeFlashPlayerChildren(FacesContext facesContext, ResponseWriter responseWriter, Media media,
		String mediaResourceURL, ResourceHandler resourceHandler, Application application, boolean defaultFlashPlayer)
		throws IOException {

		Video video = (Video) media;
		Object poster = video.getPoster();
		String posterResourceURL = null;

		if (poster != null) {
			posterResourceURL = getEncodedResourceURL(facesContext, resourceHandler, application, poster);
		}

		// If the developer has not specified a Flash player, then configure the default Flash player.
		if (defaultFlashPlayer) {

			responseWriter.startElement("param", null);
			responseWriter.writeAttribute("name", "flashVars", null);

			StringBuilder stringBuilder = new StringBuilder();
			boolean controls = video.isControls();

			if (controls) {
				stringBuilder.append("controls=true&");
			}

			if (posterResourceURL != null) {

				stringBuilder.append("poster=");

				String encodedPosterResourceURL = URLEncoder.encode(posterResourceURL);
				stringBuilder.append(encodedPosterResourceURL);
				stringBuilder.append("&");
			}

			stringBuilder.append("src=");

			String encodedVideoResourceURL = URLEncoder.encode(mediaResourceURL, "UTF-8");
			stringBuilder.append(encodedVideoResourceURL);
			responseWriter.writeAttribute("value", stringBuilder.toString(), null);
			responseWriter.endElement("param");
		}

		UIComponent uiComponent = video.getFacet("flash");

		if (uiComponent != null) {
			uiComponent.encodeAll(facesContext);
		}

		if (posterResourceURL != null) {

			responseWriter.startElement("img", video);
			responseWriter.writeAttribute("src", posterResourceURL, null);
			responseWriter.writeAttribute("alt", "", null);
		}
	}

	@Override
	protected String getDefaultFlashPlayerName() {
		return "aui/video/player.swf";
	}

	@Override
	protected String getHeight(Media media) {
		Video video = (Video) media;

		return video.getHeight();
	}

	@Override
	protected String getMediaType() {
		return "video";
	}

	@Override
	protected String getWidth(Media media) {
		Video video = (Video) media;

		return video.getWidth();
	}
}
