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
import java.util.Map;
import java.util.Set;

import javax.faces.application.ResourceHandler;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.media.Media;


/**
 * This class provides a compatibility layer that isolates differences between 2.2 and earlier.
 *
 * @author  Neil Griffin
 */
public class MediaRendererCompat extends MediaRendererBase {

	protected void encodeJSF22PassthroughAttributes(Media media, ResponseWriter responseWriter) throws IOException {

		Map<String, Object> passThroughAttributesMap = media.getPassThroughAttributes();
		Set<String> passThroughAttributes = passThroughAttributesMap.keySet();

		for (String passThroughAttribute : passThroughAttributes) {
			Object passThroughAttributeValue = passThroughAttributesMap.get(passThroughAttribute);
			responseWriter.writeAttribute(passThroughAttribute, passThroughAttributeValue, passThroughAttribute);
		}
	}

	protected boolean isFacesResourceURL(ResourceHandler resourceHandler, String value) {
		return resourceHandler.isResourceURL(value);
	}
}
