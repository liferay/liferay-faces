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
package com.liferay.faces.demos.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.service.AudioService;
import com.liferay.faces.util.application.FacesResource;


/**
 * @author  Kyle Stiemann
 */
@RequestScoped
@ManagedBean
public class AudioBacking {

	// Injections
	@ManagedProperty(value = "#{audioService}")
	private AudioService audioService;

	private String encodedMp3ResourceURL;
	private FacesResource oggAudio;
	private List<FacesResource> audios;

	@PostConstruct
	public void postConstruct() {
		audios = audioService.getAllAudios();
	}

	public List<FacesResource> getAudios() {
		return audios;
	}

	public void setAudioService(AudioService audioService) {
		this.audioService = audioService;
	}

	public String getEncodedMp3ResourceURL() throws UnsupportedEncodingException {

		if (encodedMp3ResourceURL == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			Application application = facesContext.getApplication();
			ResourceHandler resourceHandler = application.getResourceHandler();
			Resource mp3AudioResource = resourceHandler.createResource("over-the-rainbow.mp3", "audios");
			String requestPath = mp3AudioResource.getRequestPath();
			ExternalContext externalContext = facesContext.getExternalContext();
			String mp3ResourceURL = externalContext.encodeResourceURL(requestPath);
			encodedMp3ResourceURL = URLEncoder.encode(mp3ResourceURL, "UTF-8");
		}

		return encodedMp3ResourceURL;
	}

	public FacesResource getOggAudio() {

		if (oggAudio == null) {
			oggAudio = new FacesResource("audios", "over-the-rainbow.ogg");
		}

		return oggAudio;
	}
}
