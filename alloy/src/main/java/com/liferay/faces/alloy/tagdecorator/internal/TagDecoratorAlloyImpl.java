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
package com.liferay.faces.alloy.tagdecorator.internal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;
import javax.faces.view.facelets.TagDecorator;

import com.liferay.faces.alloy.config.internal.AlloyWebConfigParam;


/**
 * @author  Kyle Stiemann
 */
public class TagDecoratorAlloyImpl implements TagDecorator {

	@Override
	public Tag decorate(Tag tag) {

		Tag decoratedTag = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		boolean alloyTagDecoratorEnabled = AlloyWebConfigParam.AlloyTagDecoratorEnabled.getBooleanValue(
				externalContext);

		if (alloyTagDecoratorEnabled) {

			String elementName = tag.getLocalName();
			TagAttributes attributes = tag.getAttributes();

			if ("element".equals(elementName) && (attributes != null)) {

				TagAttribute elementNameAttribute = attributes.get("http://xmlns.jcp.org/jsf/passthrough",
						Renderer.PASSTHROUGH_RENDERER_LOCALNAME_KEY);

				if (elementNameAttribute != null) {

					elementName = elementNameAttribute.getValue();
					attributes = new TagAttributesAlloyImpl(attributes);
				}
			}

			String namespace = tag.getNamespace();

			if ("video".equals(elementName) && !"http://liferay.com/faces/alloy".equals(namespace)) {
				decoratedTag = new Tag(tag.getLocation(), "http://liferay.com/faces/alloy", "video", "alloy:video",
						attributes);
			}
			else if ("audio".equals(elementName) && !"http://liferay.com/faces/alloy".equals(namespace)) {
				decoratedTag = new Tag(tag.getLocation(), "http://liferay.com/faces/alloy", "audio", "alloy:audio",
						attributes);
			}
		}

		return decoratedTag;
	}
}
