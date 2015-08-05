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
package com.liferay.faces.demos.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.liferay.faces.util.application.FacesResource;


/**
 * @author  Kyle Stiemann
 */
@ApplicationScoped
@ManagedBean(name = "audioService")
public class AudioServiceMockImpl implements AudioService, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4289537697479123863L;

	// Private Data Members
	private List<FacesResource> audioList;

	@PostConstruct
	public void postConstruct() {
		audioList = new ArrayList<FacesResource>();
		audioList.add(new FacesResource("audios", "over-the-rainbow.mp3"));
		audioList.add(new FacesResource("audios", "over-the-rainbow.m4a"));
		audioList.add(new FacesResource("audios", "over-the-rainbow.wav"));
		audioList.add(new FacesResource("audios", "over-the-rainbow.webm"));
		audioList.add(new FacesResource("audios", "over-the-rainbow.ogg"));
	}

	@Override
	public List<FacesResource> getAllAudios() {
		return audioList;
	}
}
