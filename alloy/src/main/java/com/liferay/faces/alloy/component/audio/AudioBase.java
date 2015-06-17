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
package com.liferay.faces.alloy.component.audio;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.media.Media;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class AudioBase extends Media implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.audio.Audio";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.audio.AudioRenderer";

	// Protected Enumerations
	protected enum AudioPropertyKeys {
		styleClass,
		volume
	}

	public AudioBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(AudioPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(AudioPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-audio");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(AudioPropertyKeys.styleClass, styleClass);
	}

	public String getVolume() {
		return (String) getStateHelper().eval(AudioPropertyKeys.volume, null);
	}

	public void setVolume(String volume) {
		getStateHelper().put(AudioPropertyKeys.volume, volume);
	}
}
//J+
