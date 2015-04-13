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
@ManagedBean(name = "videoService")
public class VideoServiceMockImpl implements VideoService, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4289537697479123863L;

	// Private Data Members
	private List<FacesResource> videoList;

	@PostConstruct
	public void postConstruct() {
		videoList = new ArrayList<FacesResource>();
		videoList.add(new FacesResource("videos", "over-the-rainbow.mp4"));
		videoList.add(new FacesResource("videos", "over-the-rainbow.webm"));
		videoList.add(new FacesResource("videos", "over-the-rainbow.ogv"));
	}

	@Override
	public List<FacesResource> getAllVideos() {
		return videoList;
	}
}
