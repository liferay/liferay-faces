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
package com.liferay.faces.alloy.component.video;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.media.Media;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class VideoBase extends Media implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.video.Video";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.video.VideoRenderer";

	// Protected Enumerations
	protected enum VideoPropertyKeys {
		height,
		poster,
		styleClass,
		width
	}

	public VideoBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getHeight() {
		return (String) getStateHelper().eval(VideoPropertyKeys.height, null);
	}

	public void setHeight(String height) {
		getStateHelper().put(VideoPropertyKeys.height, height);
	}

	public Object getPoster() {
		return (Object) getStateHelper().eval(VideoPropertyKeys.poster, null);
	}

	public void setPoster(Object poster) {
		getStateHelper().put(VideoPropertyKeys.poster, poster);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(VideoPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(VideoPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-video");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(VideoPropertyKeys.styleClass, styleClass);
	}

	public String getWidth() {
		return (String) getStateHelper().eval(VideoPropertyKeys.width, null);
	}

	public void setWidth(String width) {
		getStateHelper().put(VideoPropertyKeys.width, width);
	}
}
//J+
