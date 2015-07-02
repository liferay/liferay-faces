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
package com.liferay.faces.alloy.component.audio.internal;

import java.io.IOException;
import java.net.URLEncoder;

import javax.faces.application.Application;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.audio.Audio;
import com.liferay.faces.alloy.component.media.Media;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = Audio.COMPONENT_FAMILY, rendererType = Audio.RENDERER_TYPE)
//J+
public class AudioRenderer extends AudioRendererBase {

	@Override
	protected void encodeCustomMediaAttributes(FacesContext facesContext, ResponseWriter responseWriter, Media media)
		throws IOException {

		Audio audio = (Audio) media;
		String volume = audio.getVolume();

		if (volume != null) {
			responseWriter.writeAttribute(VOLUME, volume, VOLUME);
		}
	}

	@Override
	protected void encodeFlashPlayerChildren(FacesContext facesContext, ResponseWriter responseWriter, Media media,
		String mediaResourceURL, ResourceHandler resourceHandler, Application application, boolean defaultFlashPlayer)
		throws IOException {

		// If the developer has not specified a Flash player, then configure the default Flash player.
		if (defaultFlashPlayer) {

			responseWriter.startElement("param", null);
			responseWriter.writeAttribute("name", "flashVars", null);

			String encodedVideoResourceURL = URLEncoder.encode(mediaResourceURL, "UTF-8");
			String flashVarsValue = "mp3=".concat(encodedVideoResourceURL);

			responseWriter.writeAttribute("value", flashVarsValue, null);
			responseWriter.endElement("param");
		}

		UIComponent uiComponent = media.getFacet("flash");

		if (uiComponent != null) {
			uiComponent.encodeAll(facesContext);
		}
	}

	@Override
	protected String getDefaultFlashPlayerName() {
		return "aui/audio/player.swf";
	}

	@Override
	protected String getMediaType() {
		return "audio";
	}
}
